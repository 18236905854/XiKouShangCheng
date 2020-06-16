package com.xk.mall.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.orhanobut.logger.Logger;
import com.umeng.message.PushAgent;
import com.xk.mall.BuildConfig;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.api.ApiRetrofit;
import com.xk.mall.base.BaseContent;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.model.entity.UserServerBean;
import com.xk.mall.model.impl.SettingViewImpl;
import com.xk.mall.presenter.SettingPresenter;
import com.xk.mall.utils.CleanCacheUtil;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.widget.CommomDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName SettingActivity
 * Description 设置页面
 * Author 卿凯
 * Date 2019/6/10/010
 * Version V1.0
 */
public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingViewImpl {

    @BindView(R.id.rl_setting_about)
    RelativeLayout rlSettingAbout;//关于按钮
    @BindView(R.id.rl_setting_clear)
    RelativeLayout rlSettingClear;//清除缓存按钮
    @BindView(R.id.tv_setting_clear)
    TextView tvSettingClear;//设置缓存的大小
    @BindView(R.id.rl_setting_version)
    RelativeLayout rlSettingVersion;//版本按钮，点击检查版本
    @BindView(R.id.tv_setting_version)
    TextView tvSettingVersion;//版本号文字
    @BindView(R.id.rl_setting_message)
    RelativeLayout rlSettingMessage;//消息按钮
    @BindView(R.id.iv_setting_open_msg)
    ImageView ivSettingOpenMsg;
    @BindView(R.id.tv_setting_logout)
    TextView tvLogout;//退出登录按钮

    private boolean isOpen = false;//是否打开消息通知
    private int level = 1;

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("设置");
    }

    @Override
    protected void initData() {
        //设置版本号
        String url = SPUtils.getInstance().getString(Constant.SP_BASE_URL, ApiRetrofit.BASE_SERVER_URL);
        if(BaseContent.baseDevUrl.equals(url)){
            tvSettingVersion.setText(BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE + "dev");
        }else if(BaseContent.baseTestUrl.equals(url)){
            tvSettingVersion.setText(BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE + "测试");
        }else {
            tvSettingVersion.setText(BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE);
        }
        //获取缓存大小
        tvSettingClear.setText(getCaches());
        boolean isLogin = getIntent().getBooleanExtra(Constant.IS_LOGIN, false);
        if(isLogin){
           tvLogout.setVisibility(View.VISIBLE);
        }else {
            tvLogout.setVisibility(View.GONE);
        }
        isOpen = SPUtils.getInstance().getBoolean(Constant.IS_OPEN_MSG, true);
        openOrCloseMsg();

        rlSettingVersion.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if("local".equals(BuildConfig.FLAVOR)){
                    ActivityUtils.startActivity(WuGMainActivity.class);
                }
                return false;
            }
        });
    }

    /**
     * 获取缓存大小
     */
    private String getCaches(){
        String result = "";
        try {
            result = CleanCacheUtil.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 清除缓存
     */
    private void clearCaches(){
        CleanCacheUtil.clearAllCache(this);
        new CommomDialog(mContext, R.style.mydialog, "清楚缓存成功", (dialog, confirm) -> {
            if (confirm) {
                dialog.dismiss();
            }
        }).show();
        tvSettingClear.setText(getCaches());
        MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().deleteAll();
        mPresenter.getServerAddress(level);
    }

    /**
     * view的点击事件
     */
    @OnClick({R.id.rl_setting_about, R.id.rl_setting_clear,
            R.id.rl_setting_version, R.id.rl_setting_message, R.id.tv_setting_logout, R.id.rl_setting_protocol})
    public void clickView(View view){
        switch (view.getId()){
            case R.id.rl_setting_about://关于
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(Constant.INTENT_URL, Constant.aboutUrl);
                intent.putExtra(Constant.INTENT_TITLE, "关于喜扣");
                ActivityUtils.startActivity(intent);
                break;

            case R.id.rl_setting_clear://清除缓存
                clearCaches();
                break;

            case R.id.rl_setting_version://版本号
                break;

            case R.id.rl_setting_message://消息通知
                openOrCloseMsg();
                break;

            case R.id.tv_setting_logout:
                //退出登录
                logout();
                break;

            case R.id.rl_setting_protocol://我的协议
                ActivityUtils.startActivity(MyProtocolActivity.class);
                break;
        }
    }

    /**
     * 退出登录按钮
     */
    private void logout(){
        new CommomDialog(mContext, R.style.mydialog, "确定退出登录？", (dialog, confirm) -> {
            if (confirm) {
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
                dialog.dismiss();
                EventBus.getDefault().post(new UserServerBean());
                finish();
            }
        }).show();


    }

    /**
     * 关闭或打开按钮
     */
    private void openOrCloseMsg(){
        if(isOpen){//打开的，就关闭
            isOpen = false;
            SPUtils.getInstance().put(Constant.IS_OPEN_MSG, isOpen);
            ivSettingOpenMsg.setImageResource(R.drawable.setting_icon_close_msg);
        }else {
            isOpen = true;
            SPUtils.getInstance().put(Constant.IS_OPEN_MSG, isOpen);
            ivSettingOpenMsg.setImageResource(R.drawable.setting_icon_open_msg);
        }
    }

    @Override
    public void getAddressSuccess(BaseModel<List<AddressServerBean>> listBaseModel) {
        if (listBaseModel.getData() != null) {
            Logger.e("返回的size=" + listBaseModel.getData().size());
            level += 1;
            if (level <= 3) {
                mPresenter.getServerAddress(level);
                SPUtils.getInstance().put(Constant.CONDITION_AREA, System.currentTimeMillis());
            }
            Observable.create((ObservableOnSubscribe<String>) emitter -> {
                for (AddressServerBean serverBean : listBaseModel.getData()) {
                    MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().insertOrReplace(serverBean);
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        Logger.e("请求结束:" + System.currentTimeMillis());
                    });
        }
    }
}
