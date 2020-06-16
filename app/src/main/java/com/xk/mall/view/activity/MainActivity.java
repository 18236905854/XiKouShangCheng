package com.xk.mall.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;
import com.orhanobut.logger.Logger;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;
import com.umeng.message.PushAgent;
import com.xk.mall.BuildConfig;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.model.entity.ChangeToActivityBean;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.model.entity.MainBean;
import com.xk.mall.model.entity.UpdateAppBean;
import com.xk.mall.model.entity.UserServerBean;
import com.xk.mall.model.eventbean.ChangeToNearBean;
import com.xk.mall.model.impl.MainViewImpl;
import com.xk.mall.presenter.MainPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.ViewPagerSlide;
import com.xk.mall.view.fragment.HomeTwoFragment;
import com.xk.mall.view.fragment.MeFragment;
import com.xk.mall.view.fragment.NearFragment;
import com.xk.mall.view.fragment.RankFragment;
import com.xk.mall.view.widget.AppNoticeTipDialog;
import com.xk.mall.view.widget.CommomDialog;
import com.xk.mall.view.widget.OrderTipDialog;
import com.xk.mall.view.widget.TabEntity;
import com.xk.mall.view.widget.UpdateDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity<MainPresenter> implements MainViewImpl {

    @BindView(R.id.tab_main)
    CommonTabLayout commonTabLayout;//首页的TAB
    @BindView(R.id.viewPager)
    ViewPagerSlide viewPager;
    private UpdateDialog updateDialog;
//    @BindView(R.id.ll_main_center)
//    LinearLayout llMainCenter;//喜定制按钮

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int level = 1;//等级,1-省；2-市；3-区；4-街道

    private String[] mTitles = {
            "首页",
            "热排行",
//            "喜定制",
            "附近",
            "我的"
    };
    private int[] mIconUnselectIds = {
            R.drawable.tab_home_unselect,
            R.drawable.tab_rank_unselect,
//            R.drawable.tab_main_center,
            R.drawable.tab_near_unselect,
            R.drawable.tab_me_unselect
    };
    private int[] mIconSelectIds = {
            R.drawable.tab_home_select,
            R.drawable.tab_rank_select,
//            R.drawable.tab_main_center,
            R.drawable.tab_near_select,
            R.drawable.tab_me_select
    };
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
        Logger.e("onRestoreInstanceState");
    }

    private void testUIThreadException() {
        String string = null;
        char[] chars = string.toCharArray();
    }

    @Override
    protected void initData() {
        showLeft(false);
//        testUIThreadException();
        mPresenter.getPaySwitch();
        mPresenter.checkUpdate();
        long lastRefreshTime = SPUtils.getInstance().getLong(Constant.SP_REFRESH_TOKEN_TIME);
        long subRefreshTime = (System.currentTimeMillis() - lastRefreshTime) / 1000;
        long monthTime = Long.valueOf(30 * 24 * 3600);
        long isRefreshTime = subRefreshTime - monthTime;
        Logger.e(subRefreshTime + "请求开始2:" + isRefreshTime);
        //三个月之内不更换行政区域地址
//        if (lastRefreshTime == 0 || isRefreshTime >= 0) {
//            if(!TextUtils.isEmpty(MyApplication.refreshToken)){
//                mPresenter.refreshToken(MyApplication.refreshToken);
//            }
//        }

//        mFragments.add(HomeFragment.getInstance());
        mFragments.add(HomeTwoFragment.getInstance());
        mFragments.add(RankFragment.getInstance());
//        mFragments.add(new Fragment());
        mFragments.add(NearFragment.getInstance());
        mFragments.add(MeFragment.getInstance());
        boolean v2LoginOut = SPUtils.getInstance().getBoolean(Constant.V2_LOGIN_OUT, false);
        if(!v2LoginOut){
            logout();
        }

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        commonTabLayout.setSpecialTab(-1);
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setTextBold(1);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                if(position==2){
//                    Logger.e("点击中间喜定制");
//                    MoreWindow moreWindow = new MoreWindow(MainActivity.this);
//                    moreWindow.showMoreWindow(commonTabLayout, 0);
//                }else{
                if(viewPager != null){
                    viewPager.setCurrentItem(position);
                }
//                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int index) {
                switch (index) {
                    case 0:
                        ImmersionBar.with(MainActivity.this).keyboardEnable(false).statusBarDarkFont(false).statusBarDarkFont(true, 0.2f).navigationBarColor(R.color.black).init();
                        break;
                    case 1:
                        ImmersionBar.with(MainActivity.this).keyboardEnable(false).statusBarDarkFont(true).navigationBarColor(R.color.black).init();
                        break;
                    case 2:
//                        break;
//                    case 3:
                        ImmersionBar.with(MainActivity.this).keyboardEnable(false).statusBarDarkFont(true, 0.2f).navigationBarColor(R.color.black).init();
                        break;
                    case 3:
                        ImmersionBar.with(MainActivity.this).keyboardEnable(false).statusBarDarkFont(false).navigationBarColor(R.color.black).init();
                        break;
                }
                commonTabLayout.setCurrentTab(index);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        viewPager.setCurrentItem(0);

        // 显示未读红点
//        commonTabLayout.showMsg(3, 39);

//        new AppNoticeTipDialog(mContext, R.style.mydialog, "商城公告", getString(R.string.notice_content),
//                "查看详细公告", (dialog, confirm) -> {
//                    if(confirm){
//                        Intent intentUrl = new Intent(mContext, WebViewActivity.class);
//                        intentUrl.putExtra(Constant.INTENT_URL, Constant.noticeUrl);
//                        intentUrl.putExtra(Constant.INTENT_TITLE, "商城公告");
//                        ActivityUtils.startActivity(intentUrl);
//                    }
//                }).show();

        long lastTime = SPUtils.getInstance().getLong(Constant.CONDITION_AREA);
        Logger.e(lastTime + "请求开始:" + System.currentTimeMillis());
        long subTime = (System.currentTimeMillis() - lastTime) / 1000;
        long isTime = subTime - monthTime;
        Logger.e(subTime + "请求开始2:" + isTime);
        //三个月之内不更换行政区域地址
        if (lastTime == 0 || isTime >= 0) {
            MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().deleteAll();
            mPresenter.getServerAddress(1);
        }
    }

    private long exitTime = 0;

    /**
     * 退出登录按钮
     */
    private void logout(){
        SPUtils.getInstance().put(Constant.SP_USER_ID, "");
        SPUtils.getInstance().put(Constant.SP_TOKEN, "");
        SPUtils.getInstance().put(Constant.SP_REFRESH_TOKEN, "");
        SPUtils.getInstance().put(Constant.IS_LOGIN,false);
        SPUtils.getInstance().put(Constant.USER_INVITE_CODE,"");
        SPUtils.getInstance().put(Constant.PAY_SEARCH_PHONE_KEY, "");
        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.deleteAlias("xk", MyApplication.userId, (b, s12) -> {
            Logger.e("移除成功");
        });
        MyApplication.userId = "";
        MyApplication.token = "";
        MyApplication.refreshToken = "";
        EventBus.getDefault().post(new UserServerBean());
        SPUtils.getInstance().put(Constant.V2_LOGIN_OUT, true);
    }

    @Override
    public void onRefreshTokenSuccess(BaseModel<LoginBean> o) {
        if(o != null && o.getData() != null){
            MyApplication.token = o.getData().token;
            MyApplication.userId = o.getData().userId;
            MyApplication.refreshToken = o.getData().refreshToken;
            SPUtils.getInstance().put(Constant.SP_TOKEN, MyApplication.token);
            SPUtils.getInstance().put(Constant.SP_USER_ID, MyApplication.userId);
            SPUtils.getInstance().put(Constant.SP_REFRESH_TOKEN, MyApplication.refreshToken);
            SPUtils.getInstance().put(Constant.SP_REFRESH_TOKEN_TIME, System.currentTimeMillis());
        }
    }

    @Override
    public void checkVersionSuccess(BaseModel<UpdateAppBean> model) {
        if(model != null && model.getData() != null){
            if(Integer.parseInt(model.getData().getForeignVersionNumber()) > BuildConfig.VERSION_CODE){
                //显示更新对话框
                //有新版本更新，显示对话框
                updateDialog = new UpdateDialog(mContext, R.style.mydialog, model.getData());
                updateDialog.show();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
            ToastUtils.showShort("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChangeToNearBean event) {
        if (commonTabLayout != null) {
            commonTabLayout.setCurrentTab(2);
            viewPager.setCurrentItem(2);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChangeToActivityBean event) {
        if (commonTabLayout != null) {
            commonTabLayout.setCurrentTab(1);
            viewPager.setCurrentItem(1);
        }
    }



    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyUpdateManager.unRegister();
    }

    @Override
    public void onMainSuccess(BaseModel<List<MainBean>> o) {
    }

    @Override
    public void getAddressSuccess(BaseModel<List<AddressServerBean>> listBaseModel) {
        if (listBaseModel.getData() != null) {
            Logger.e("返回的size=" + listBaseModel.getData().size());
            level += 1;
            if (level <= 3) {
                mPresenter.getServerAddress(level);
            }
            Observable.create((ObservableOnSubscribe<String>) emitter -> {
                for (AddressServerBean serverBean : listBaseModel.getData()) {
                    MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().insertOrReplace(serverBean);
                }
                if(level >= 3){
                    SPUtils.getInstance().put(Constant.CONDITION_AREA, System.currentTimeMillis());
                }
                Logger.e("请求结束:" + System.currentTimeMillis());
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        if(level >= 3){
                            SPUtils.getInstance().put(Constant.CONDITION_AREA, System.currentTimeMillis());
                        }
                        Logger.e("请求结束:" + System.currentTimeMillis());
                    });
        }
    }
}
