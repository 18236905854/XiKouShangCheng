package com.xk.mall.view.fragment;

import android.content.Intent;
import android.support.annotation.Keep;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.meiqia.core.MQManager;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.imageloader.MQImage;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;
import com.xk.mall.BuildConfig;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseContent;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.KeyValueBean;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.model.entity.UserItem;
import com.xk.mall.model.entity.UserServerBean;
import com.xk.mall.model.eventbean.HideMoneyBean;
import com.xk.mall.model.eventbean.RefreshMoney;
import com.xk.mall.model.impl.MeViewImpl;
import com.xk.mall.presenter.MePresenter;
import com.xk.mall.utils.AntiShake;
import com.xk.mall.utils.AntiShakeUtils;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.MQGlideImageLoaderFour;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.activity.AddressActivity;
import com.xk.mall.view.activity.AttentionActivity;
import com.xk.mall.view.activity.CouponActivity;
import com.xk.mall.view.activity.DPayActivity;
import com.xk.mall.view.activity.LoginActivity;
import com.xk.mall.view.activity.MakeTaskActivity;
import com.xk.mall.view.activity.MyCommunityFlowActivity;
import com.xk.mall.view.activity.MyPromotionActivity;
import com.xk.mall.view.activity.MyRedBagActivity;
import com.xk.mall.view.activity.OTOLianMActivity;
import com.xk.mall.view.activity.PersonalInfoActivity;
import com.xk.mall.view.activity.ProtocolWebViewActivity;
import com.xk.mall.view.activity.RealNameActivity;
import com.xk.mall.view.activity.SettingActivity;
import com.xk.mall.view.activity.XiKouMaterialActivity;
import com.xk.mall.view.adapter.MeAdapter;
import com.xk.mall.view.adapter.MeViewPagerAdapter;
import com.xk.mall.view.widget.MyViewPager;
import com.xk.mall.view.widget.SlidingLayout;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName MeFragment
 * @Description 我的模块页面
 * @Author 卿凯
 * @Date 2019/6/4/004
 * @Version V1.0
 */
public class MeFragment extends BaseFragment<MePresenter> implements MeViewImpl {
    private static final String TAG = "MeFragment";
    @BindView(R.id.rl_me_user)
    RelativeLayout rlUser;//用户的父布局
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;//头像
    @BindView(R.id.iv_user_vip)
    ImageView ivUserVip;//是否是vip布局
    @BindView(R.id.tv_user_name)
    TextView tvUserName;//用户名
    @BindView(R.id.tv_me_welcome)
    TextView tvWelcome;//初始状态的欢迎提示
    @BindView(R.id.tv_user_authen)
    TextView tvUserAuthen;//认证状态
    @BindView(R.id.ll_me_red_bag)
    LinearLayout llMeRedBag;//我的红包
    @BindView(R.id.tv_me_red_bag)
    TextView tvRedBag;//我的红包余额
    @BindView(R.id.ll_me_coupon)
    LinearLayout llMeCoupon;//我的优惠券
    @BindView(R.id.tv_me_coupon_num)
    TextView tvCouponNum;//我的优惠券张数
    @BindView(R.id.tv_me_vip)
    TextView tvVip;//成为会员文字
    @BindView(R.id.rv_vip)
    RecyclerView rvVip;//vip的权益
//    @BindView(R.id.tab_user)
//    SlidingTabLayout tabUser;// 卖家和买家
    @BindView(R.id.ll_me_tab_one)
    LinearLayout llTabOne;
    @BindView(R.id.tv_me_tab_title)
    TextView tvTabOne;
    @BindView(R.id.line_me_tab_one)
    View lineOne;
    @BindView(R.id.ll_me_tab_two)
    LinearLayout llTabTwo;
    @BindView(R.id.tv_me_tab_title_two)
    TextView tvTabTwo;
    @BindView(R.id.line_me_tab_two)
    View lineTwo;
    @BindView(R.id.view_pager_user)
    MyViewPager viewPagerUser;
    @BindView(R.id.rv_user)
    RecyclerView rvUser;//我的页面
    //    @BindView(R.id.toolBar)
//    Toolbar toolBar;
    @BindView(R.id.iv_me_setting)
    ImageView ivMeSetting;
    @BindView(R.id.ll_user_become_vip)
    LinearLayout llUserVip;
    @BindView(R.id.me_sliding_layout)
    SlidingLayout meSlidingLayout;

    private boolean isLogin = false;//是否登录成功
    private String headUrl = "";//用户头像
    private int balance = 0;//用户红包余额

    public static MeFragment getInstance() {
        MeFragment meFragment = new MeFragment();
        return meFragment;
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    protected MePresenter createPresenter() {
        return new MePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initData() {
        Log.e(TAG, "loadLazyData:=== ");
    }

    @Override
    protected void loadLazyData() {
        if (isLogin) {
            //设置自己的头像和昵称等信息
        } else {
            //设置圆形logo
            Glide.with(this).load(R.drawable.me_head_icon).circleCrop().into(ivUserIcon);
        }
        //设置vip的adapter
        GridLayoutManager vipLayoutManager = new GridLayoutManager(mContext, 4);
        rvVip.setLayoutManager(vipLayoutManager);
        List<UserItem> vipItems = new ArrayList<>();
        vipItems.add(new UserItem(R.drawable.me_vip_one, "全球买手"));
        vipItems.add(new UserItem(R.drawable.me_vip_two, "0元抢"));
        vipItems.add(new UserItem(R.drawable.me_vip_three, "邀请返利"));
        vipItems.add(new UserItem(R.drawable.me_vip_four, "优先寄卖"));
        MeAdapter meVipAdapter = new MeAdapter(mContext, R.layout.user_vip_item, vipItems);
        rvVip.setAdapter(meVipAdapter);

        //设置用户的adapter
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        rvUser.setLayoutManager(gridLayoutManager);
        List<UserItem> meItems = new ArrayList<>();
        meItems.add(new UserItem(R.drawable.me_spread, "我的推广"));
        meItems.add(new UserItem(R.drawable.me_task, "我的任务"));
        meItems.add(new UserItem(R.drawable.me_address, "我的地址"));
        meItems.add(new UserItem(R.drawable.me_guanzhu, "我的关注"));
        meItems.add(new UserItem(R.drawable.me_daifu, "我的代付"));
        meItems.add(new UserItem(R.drawable.me_oto, "O2O消费"));
        meItems.add(new UserItem(R.drawable.me_sucai, "喜扣素材"));
        meItems.add(new UserItem(R.drawable.me_liuliang, "社交流量"));
        meItems.add(new UserItem(R.drawable.me_kefu, "联系客服"));
        MeAdapter meAdapter = new MeAdapter(mContext, R.layout.user_item, meItems);
        rvUser.setAdapter(meAdapter);

        meAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                checkLogin(view, position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        List<String> titles = new ArrayList<>();
        titles.add("我是买家");
        titles.add("我是卖家");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new BuyerFragment());
        fragments.add(new SellFragment());
        MeViewPagerAdapter meViewPagerAdapter = new MeViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewPagerUser.setOffscreenPageLimit(titles.size());
        viewPagerUser.setAdapter(meViewPagerAdapter);
//        tabUser.setViewPager(viewPagerUser);
//        tabUser.setCurrentTab(0);
        llTabOne.setBackground(getResources().getDrawable(R.drawable.bg_me_tab_left_white));
        tvTabOne.setText(titles.get(0));
        tvTabTwo.setText(titles.get(1));
        llTabTwo.setBackground(getResources().getDrawable(R.drawable.bg_me_tab_right_gray));
        llTabOne.setOnClickListener(v -> {
            viewPagerUser.setCurrentItem(0);
        });

        llTabTwo.setOnClickListener(v -> viewPagerUser.setCurrentItem(1));

        viewPagerUser.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                viewPagerUser.resetHeight(i);
                if(i == 0){
                    tvTabOne.setTextSize(16);
                    lineOne.setVisibility(View.VISIBLE);
                    lineTwo.setVisibility(View.GONE);
                    llTabOne.setBackground(getResources().getDrawable(R.drawable.bg_me_tab_left_white));
                    llTabTwo.setBackground(getResources().getDrawable(R.drawable.bg_me_tab_right_gray));
                    tvTabTwo.setTextSize(15);
                }else {
                    tvTabTwo.setTextSize(16);
                    lineOne.setVisibility(View.GONE);
                    lineTwo.setVisibility(View.VISIBLE);
                    llTabTwo.setBackground(getResources().getDrawable(R.drawable.bg_me_tab_right_white));
                    llTabOne.setBackground(getResources().getDrawable(R.drawable.bg_me_tab_left_gray));
                    tvTabOne.setTextSize(15);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        getUserInfoById(MyApplication.userId);
    }

    /**
     * 新增方法处理item点击事件注解登录的问题
     */
    @Keep
    @LoginFilter
    public void checkLogin(View view, int position){
        if (AntiShakeUtils.isInvalidClick(view)) {
            return;
        }
        switch (position) {
            case 0://我的推广
                ActivityUtils.startActivity(MyPromotionActivity.class);
                break;

            case 1://我的任务
                ActivityUtils.startActivity(MakeTaskActivity.class);
                break;

            case 2://我的地址
                ActivityUtils.startActivity(AddressActivity.class);
                break;

            case 3://我的关注
                ActivityUtils.startActivity(AttentionActivity.class);
                break;

            case 4://我的代付
                ActivityUtils.startActivity(DPayActivity.class);
                break;
            case 5://OTO消费
                ActivityUtils.startActivity(OTOLianMActivity.class);
                break;

            case 6://喜扣素材
                ActivityUtils.startActivity(XiKouMaterialActivity.class);
                break;

            case 7://社交流量
                ActivityUtils.startActivity(MyCommunityFlowActivity.class);
                break;

            case 8://联系客服
                initMeiqiaSDK();
                break;
        }
    }

    /**
     * 初始化美洽SDK
     */
    private void initMeiqiaSDK() {
        MQManager.setDebugMode(BuildConfig.DEBUG);

        MQConfig.init(mContext, BaseContent.MQ_KEY, new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
                HashMap<String, String> clientInfo = new HashMap<>();
                clientInfo.put("avatar", headUrl);
                //跳转页面
                MQImage.setImageLoader(new MQGlideImageLoaderFour());
                Intent intent = new MQIntentBuilder(mContext).setClientInfo(clientInfo)
                        .setCustomizedId(MyApplication.userId).build();
                startActivity(intent);
            }

            @Override
            public void onFailure(int code, String message) {
                //启动失败
                ToastUtils.showShort("启动客服失败，请重试");
                if(!XiKouUtils.isNullOrEmpty(message)){
                    MobclickAgent.reportError(mContext, message);
                }
            }
        });
        //改成跟美洽SDK一样
        MQConfig.isShowClientAvatar = true;
        MQConfig.ui.backArrowIconResId = R.drawable.ic_back;
    }

    @LoginFilter
    @OnClick({R.id.rl_me_user, R.id.ll_me_red_bag, R.id.ll_me_coupon, R.id.ll_user_become_vip, R.id.tv_user_authen})
    public void clickView(View view) {
        if (AntiShake.check(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.rl_me_user://点击头像
                ActivityUtils.startActivity(PersonalInfoActivity.class);
                break;

            case R.id.ll_me_red_bag://我的钱包
                ActivityUtils.startActivity(MyRedBagActivity.class);
                break;

            case R.id.ll_me_coupon://我的优惠券
                ActivityUtils.startActivity(CouponActivity.class);
                break;

            case R.id.ll_user_vip://成为会员
                if(!SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)){
                    ActivityUtils.startActivity(LoginActivity.class);
                }
                break;

            case R.id.tv_user_authen:
                ActivityUtils.startActivity(RealNameActivity.class);
                break;
        }
    }

    /**
     * 将设置页面的点击事件提出来，不需要验证登录
     */
    @OnClick(R.id.iv_me_setting)
    public void clickSetting() {
        Intent intent = new Intent(mContext, SettingActivity.class);
        intent.putExtra(Constant.IS_LOGIN, isLogin);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 友盟统计Fragment进入需要添加的方法
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("我的");
        Log.e("KK", "重新获取用户数据");
        getUserInfoById(MyApplication.userId);

    }

    /**
     * 友盟统计Fragment退出需要添加的方法
     */
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的");
    }

    /**
     * 根据用户ID获取用户信息
     */
    private void getUserInfoById(String userId) {
        long lastTime = SPUtils.getInstance().getLong(Constant.SP_LAST_GET_INFO);
        int isRealName = SPUtils.getInstance().getInt(Constant.USER_REAL_NAME, 0);
        if(isRealName == 1 && isLogin){
            tvUserAuthen.setText("已认证");
        }else {
            tvUserAuthen.setText("未认证");
        }
        if(!isLogin || MyApplication.isPaySuccess){
            if (!TextUtils.isEmpty(userId)) {
                mPresenter.getUserInfoById(userId);
                mPresenter.getCouponSum(userId);
            }
        }else if(lastTime == 0 || System.currentTimeMillis() - lastTime >= 5 * 60 * 1000){
            if (!TextUtils.isEmpty(userId)) {
                mPresenter.getUserInfoById(userId);
                mPresenter.getCouponSum(userId);
            }
        }
    }

    /**
     * eventBus接收回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLogin(LoginBean loginBean) {
        Logger.e("接收到消息");
        isLogin = false;
        getUserInfoById(loginBean.userId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logout(UserServerBean serverBean){
        isLogin = false;
        SPUtils.getInstance().put(Constant.IS_LOGIN, false);
        bindUserData(serverBean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void bindLogoAndName(KeyValueBean keyValueBean){
        GlideUtil.showUserCircle(mContext, keyValueBean.value, ivUserIcon);
        tvUserName.setText(keyValueBean.key);
    }

    /**
     * 获取用户信息的回调
     */
    @Override
    public void onGetUserInfoSuccess(BaseModel<UserServerBean> baseModel) {
        isLogin = true;
        MyApplication.isPaySuccess = false;
        //设置并保存用户资料
        SPUtils.getInstance().put(Constant.SP_LAST_GET_INFO, System.currentTimeMillis());
        SPUtils.getInstance().put(Constant.NICK_NAME,baseModel.getData().nickName);
        SPUtils.getInstance().put(Constant.HEAD_URL,baseModel.getData().headUrl);
//        SPUtils.getInstance().put(Constant.IS_LOGIN, true);
        SPUtils.getInstance().put(Constant.USER_MOBILE,baseModel.getData().mobile);
        SPUtils.getInstance().put(Constant.USER_REAL_NAME,baseModel.getData().certification);//是否实名
        SPUtils.getInstance().put(Constant.USER_SET_PWD,baseModel.getData().isPayPassword);//是否设置支付密码
        SPUtils.getInstance().put(Constant.USER_INVITE_CODE,baseModel.getData().extCode);//邀请码
        SPUtils.getInstance().put(Constant.IS_LOGIN, true);
        bindUserData(baseModel.getData());
//        DaoSession daoSession = MySQLiteOpenHelper.getDaoSession(mContext);
//        daoSession.insertOrReplace(baseModel.getData());
    }

    @Override
    public void onGetCouponSumSuccess(BaseModel<Integer> model) {
        if(model != null){
            Integer couponSum = model.getData();
            SPUtils.getInstance().put(Constant.COUPON_SUM, couponSum);
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        //获取用户信息出错之后
        SPUtils.getInstance().put(Constant.IS_LOGIN, false);
    }

    private void bindUserData(UserServerBean userServerBean) {
        if(userServerBean != null){
            headUrl = userServerBean.headUrl;
            GlideUtil.showUserCircle(mContext, userServerBean.headUrl, ivUserIcon);
            tvWelcome.setVisibility(View.GONE);
            tvUserAuthen.setVisibility(View.VISIBLE);
            if (userServerBean.certification == 0) {
                tvUserAuthen.setText("未认证");
            } else {
                tvUserAuthen.setText("已认证");
            }
            if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)) {
                ivUserVip.setVisibility(View.VISIBLE);
                tvVip.setText("会员权益>");
            } else {
                ivUserVip.setVisibility(View.GONE);
                tvVip.setText("成为会员>");
            }
            if(TextUtils.isEmpty(userServerBean.nickName)){
                tvUserName.setText("点击登录/注册");
                tvWelcome.setVisibility(View.VISIBLE);
                tvUserAuthen.setVisibility(View.GONE);
            }else {
                tvUserName.setText(userServerBean.nickName);
                tvWelcome.setVisibility(View.GONE);
                tvUserAuthen.setVisibility(View.VISIBLE);
            }
            tvCouponNum.setText("" + userServerBean.couponNum);
            SPUtils.getInstance().put(Constant.COUPON_NUM, userServerBean.couponNum);
            boolean isHideRed = SPUtils.getInstance().getBoolean(Constant.IS_HIDE_CASH);
            balance = userServerBean.balance;
            if(isLogin && isHideRed){
                tvRedBag.setText("******");
            }else {
                tvRedBag.setText("" + PriceUtil.dividePrice(userServerBean.balance));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showOrHideMoney(HideMoneyBean moneyBean){
        if(moneyBean.isHide){
            tvRedBag.setText("******");
        }else {
            tvRedBag.setText("" + PriceUtil.dividePrice(balance));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getRedBalance(RefreshMoney balance){
        boolean isHideRed = SPUtils.getInstance().getBoolean(Constant.IS_HIDE_CASH);
        if(isLogin && isHideRed){
            tvRedBag.setText("******");
        }else {
            if(balance != null){
                tvRedBag.setText("" + PriceUtil.dividePrice(balance.getBalance()));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
