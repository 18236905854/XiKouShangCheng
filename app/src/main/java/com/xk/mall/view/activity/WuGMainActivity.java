package com.xk.mall.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.WuGSlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.WuGConfigBean;
import com.xk.mall.model.entity.WuGOrderMoneyBean;
import com.xk.mall.model.eventbean.RefreshWuGEvent;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.WuGMainImpl;
import com.xk.mall.presenter.WuGMainPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.CustomRoundedImageLoader;
import com.xk.mall.utils.EventBusMessage;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareType;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.adapter.ViewPagerAdapter;
import com.xk.mall.view.fragment.WuGGoodsFragment;
import com.xk.mall.view.widget.DialogShare;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.XKBanner;
import com.youth.banner.XKBannerMany;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * ClassName WuGMainActivity
 * Description 吾G购
 * Author
 * Date
 * Version V1.0
 */
public class WuGMainActivity extends BaseActivity<WuGMainPresenter> implements WuGMainImpl {
    private static final String TAG = "WuGMainActivity";
    @BindView(R.id.banner_home)
    XKBannerMany bannerHome;
    @BindView(R.id.tablayout)
    WuGSlidingTabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_wug_money_tip)
    TextView tvWuGMoneyTip;//吾G限额提示
    @BindView(R.id.rl_wug_money)
    RelativeLayout rlWuGMoney;//吾G限额
    @BindView(R.id.iv_wug_money_arrow)
    ImageView ivWuGMoneyArrow;//吾G页面箭头
    @BindView(R.id.ll_wug_total_money)
    LinearLayout llWuGTotalMoney;//吾G总金额布局
    @BindView(R.id.tv_wug_total_money)
    TextView tvWuGTotalMoney;//总金额
    @BindView(R.id.tv_wug_discount_money)
    TextView tvWuGDiscountMoney;//剩余金额
    @BindView(R.id.tv_wug_refresh_time)
    TextView tvWuGRefreshTime;//吾G刷新时间
    @BindView(R.id.appbar_wug)
    AppBarLayout appBarLayout;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.ll_clock)
    LinearLayout llClock;//限时限量父布局
    @BindView(R.id.tv_clock_time)
    TextView tvClockTime;//场次  8点场
    @BindView(R.id.tv_clock_type)
    TextView tvClockType;//距结束/距开始
    @BindView(R.id.cv_clock)
    CountdownView cvClock;//场次倒计时
    boolean isIn = true;
    boolean isScheduleOpen = false;

    private boolean hasData;//是否获取了数据
    private boolean isClickShow;//是否获取了数据

    private String activityId = "";//活动ID
    private ViewPagerAdapter viewPagerAdapter;
    List<String> times = new ArrayList<>();
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected WuGMainPresenter createPresenter() {
        return new WuGMainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wug_buy;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        ImmersionBar.setTitleBar(this, toolbar);
        ImmersionBar.with(this).init();
        setTitle("吾G购");
        setTitleTextColor(Color.WHITE);
        setLeftDrawable(R.drawable.ic_back_white);
        setRightDrawable(R.drawable.ic_activity_share_white);
        setOnRightIconClickListener(v -> {
            getShareContent();
        });
    }

    @Override
    protected boolean isSetStatusBarColor() {
        return false;
    }

    @Override
    protected void initData() {
        setShowDialog(false);
        mPresenter.getScheduleConfig();
//        mPresenter.getActiveSectionData(ActivityType.ACTIVITY_WUG);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            EventBus.getDefault().post(new RefreshWuGEvent(true));
            mPresenter.getScheduleConfig();
        });

        rlWuGMoney.setOnClickListener(v -> checkAndShow());
        if(!XiKouUtils.isNullOrEmpty(MyApplication.userId)){
            mPresenter.getTotalMoney(MyApplication.userId);
        }

        int pointHeight = ScreenUtils.getScreenHeight() * 2 / 5;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, pointHeight, 0, 0);
        rlWuGMoney.setLayoutParams(layoutParams);
        setListener();
    }
    String scheduleId;

    @Override
    public void onGetScheduleConfigSuccess(BaseModel<WuGConfigBean> model) {
        if(model.getData() != null && model.getData().getScheulePartitionSwitch() == 1 &&
            model.getData().getScheuleSwitch() == 1 && model.getData().getScheduleModelList() != null
                && model.getData().getScheduleModelList().size() != 0){
            //显示倒计时
            isScheduleOpen = true;
            llClock.setVisibility(View.VISIBLE);
            long currentTime = System.currentTimeMillis();//当前时间
            Logger.e("当前时间：" + currentTime);
            WuGConfigBean.ScheduleModelListBean modelListBean = null;
            for(WuGConfigBean.ScheduleModelListBean modelListBean1 : model.getData().getScheduleModelList()){
                Logger.e("场次开始时间：" + modelListBean1.getStartTime());
                Logger.e("场次结束时间：" + modelListBean1.getEndTime());
            }
            for(WuGConfigBean.ScheduleModelListBean modelListBean1 : model.getData().getScheduleModelList()){
                if(modelListBean1.getStartTime() <= currentTime && currentTime < modelListBean1.getEndTime()){//进行中
                    modelListBean = modelListBean1;
                    isIn = true;
                    break;
                }else if(modelListBean1.getStartTime() > currentTime){//未开始
                    modelListBean = modelListBean1;
                    isIn = false;
                    break;
                }
            }
            if(modelListBean != null){
                scheduleId = modelListBean.getId();
                Logger.e("显示倒计时和场次");
                String time = TimeUtils.date2String(new Date(modelListBean.getStartTime()), "HH时mm分");
                tvClockTime.setText(time);
                if(isIn){
                    tvClockType.setText("距离结束");
                    cvClock.start(modelListBean.getEndTime() - currentTime);
                }else {
                    tvClockType.setText("距离开始");
                    cvClock.start(modelListBean.getStartTime() - currentTime);
                }
                cvClock.setOnCountdownEndListener(cv -> {
                    //倒计时结束要重新请求数据
                    mPresenter.getScheduleConfig();
                });

            }else {
                Logger.e("场次时间有问题");
                llClock.setVisibility(View.GONE);
            }
        }else {
            llClock.setVisibility(View.GONE);
            isScheduleOpen = false;
        }
        mPresenter.getActiveSectionData(ActivityType.ACTIVITY_WUG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!XiKouUtils.isNullOrEmpty(MyApplication.userId)){
            rlWuGMoney.setVisibility(View.VISIBLE);
        }else {
            rlWuGMoney.setVisibility(View.GONE);
        }
    }

    private void setListener(){
        appBarLayout.addOnOffsetChangedListener((appBarLayout, i) -> {
            if(Math.abs(i) >= ConvertUtils.dp2px(180)){
                Logger.e("改变背景颜色222");
                appBarLayout.setBackgroundResource(R.drawable.bg_wug_top);
                appBarLayout.setPadding(ConvertUtils.dp2px(10), 0, ConvertUtils.dp2px(10), 10);
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) viewPager.getLayoutParams();
                layoutParams.setMargins(ConvertUtils.dp2px(10), 0, ConvertUtils.dp2px(10), 0);
            }else if(Math.abs(i) == 0){
                appBarLayout.setBackgroundResource(R.drawable.bg_wug_top_radius);
                appBarLayout.setPadding(ConvertUtils.dp2px(10), 0, ConvertUtils.dp2px(10), ConvertUtils.dp2px(50));
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) viewPager.getLayoutParams();
                layoutParams.setMargins(ConvertUtils.dp2px(10), -ConvertUtils.dp2px(40), ConvertUtils.dp2px(10), 0);
            }
        });
    }

    @Keep
    @LoginFilter
    private void checkAndShow(){
        isClickShow = true;
        if(hasData){
            if(llWuGTotalMoney.getVisibility() == View.VISIBLE){
                llWuGTotalMoney.setVisibility(View.GONE);
                ivWuGMoneyArrow.setImageResource(R.drawable.ic_global_right);
                tvWuGMoneyTip.setVisibility(View.VISIBLE);
            }else {
                llWuGTotalMoney.setVisibility(View.VISIBLE);
                ivWuGMoneyArrow.setImageResource(R.drawable.ic_global_left);
                tvWuGMoneyTip.setVisibility(View.GONE);
            }
        }else {
            mPresenter.getTotalMoney(MyApplication.userId);
        }
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventGetActivityId(EventBusMessage eventBusMessage){
        if(!TextUtils.isEmpty(eventBusMessage.getMessage())){
            activityId = eventBusMessage.getMessage();
        }
    }

    /**
     * 获取分享内容
     */
    private void getShareContent(){
        if(!TextUtils.isEmpty(activityId)){
            mPresenter.getShareContent(MyApplication.userId, activityId, ShareType.ACTIVITY_WUG);
        }
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        ShareBean shareBean = model.getData();
        if(shareBean != null){
            DialogShare dialogShare = new DialogShare(mContext, shareBean);
            MyApplication.shareType = ShareType.ACTIVITY_WUG;
            dialogShare.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareCallback(ShareSuccessEvent event){
        if(event.shareType == ShareType.ACTIVITY_WUG){
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.ACTIVITY_WUG);
        }
    }

    @Override
    public void onShareCallback(BaseModel model) {

    }

    @Override
    public void onGetMoneySuccess(BaseModel<WuGOrderMoneyBean> moneyBeanBaseModel) {
        if(moneyBeanBaseModel != null && moneyBeanBaseModel.getData() != null){
            if(isClickShow){
                llWuGTotalMoney.setVisibility(View.VISIBLE);
                tvWuGMoneyTip.setVisibility(View.GONE);
                ivWuGMoneyArrow.setImageResource(R.drawable.ic_wug_left);
            }
            isClickShow = false;
            if(moneyBeanBaseModel.getData().getLimitAmount() < 0){
                moneyBeanBaseModel.getData().setLimitAmount(0);
            }
            if(moneyBeanBaseModel.getData().getBalance() < 0){
                moneyBeanBaseModel.getData().setBalance(0);
            }
            hasData = true;
            tvWuGTotalMoney.setText(formatLimit(moneyBeanBaseModel.getData().getLimitAmount()) + "元");
            tvWuGDiscountMoney.setText(formatLimit(moneyBeanBaseModel.getData().getBalance()) + "元");
            tvWuGRefreshTime.setText(XiKouUtils.formartTime(moneyBeanBaseModel.getData().getExpirationTime()));
        }
    }

    private String formatLimit(int limitAmount){
        String result = "";
        if(limitAmount < 1000000){
            result = PriceUtil.dividePrice(limitAmount);
        }else {
            //返回多少万元
            result = PriceUtil.dividePrice(limitAmount / 10000) + "万";
        }
        return result;
    }

    @Override
    public void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model) {
        if(mRefreshLayout != null ){
            mRefreshLayout.finishRefresh();
        }

        if(model.getData() != null){
            bindBanner(model.getData().getBannerList());
            bindTimes(model.getData().getSectionList());
        }
    }

    private void bindTimes(List<ActiveSectionBean.SectionListBean> sectionList) {
        if(sectionList == null || sectionList.size() == 0){
            tablayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
        }else {
            times.clear();
            for(ActiveSectionBean.SectionListBean listBeanX : sectionList){
                times.add(listBeanX.getCategoryName());
            }
            fragmentList.clear();
            for (ActiveSectionBean.SectionListBean time : sectionList) {
                fragmentList.add(WuGGoodsFragment.getInstance(time.getId(), scheduleId, isIn, isScheduleOpen));
            }
            if(viewPagerAdapter != null){
                viewPagerAdapter.notifyDataSetChanged();
            }else {
                viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, times);
                viewPager.setOffscreenPageLimit(times.size());
                viewPager.setAdapter(viewPagerAdapter);
                tablayout.setViewPager(viewPager);
                tablayout.setCurrentTab(0);
            }
        }
    }

    /**
     * 绑定banner
     */
    private void bindBanner(List<BannerBean> bannerList) {
//        if(bannerHome == null){
//            bannerHome = findViewById(R.id.banner_home);
//        }
        bannerHome.setImageLoader(new CustomRoundedImageLoader());
        //设置图片集合
        List<String> images = new ArrayList<>();
        if (bannerList != null && bannerList.size() > 0) {
            for (int i = 0; i < bannerList.size(); i++) {
                if(bannerList.get(i).getPosition() == 1){
                    images.add(bannerList.get(i).getImageUrl());
                }
            }

            bannerHome.setImages(images);
            //设置nearBanner动画效果
            bannerHome.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            bannerHome.isAutoPlay(true);
            //设置指示器
            bannerHome.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
            //设置轮播时间
            bannerHome.setDelayTime(3000);
            //banner设置方法全部调用完毕时最后调用
            bannerHome.start();

            bannerHome.setOnBannerListener(position -> {
                String skipType = bannerList.get(position).getSkipType();
                String targetParams = bannerList.get(position).getTargetParams();
                String webUrl = bannerList.get(position).getTargetUrl1();
                XiKouUtils.parseBannner(mContext,skipType,targetParams,webUrl);
            });
        }else {
            List<Integer> integerList = new ArrayList<>();
            integerList.add(R.drawable.ic_many_head);
            bannerHome.setImages(integerList);
            //设置nearBanner动画效果
            bannerHome.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            bannerHome.isAutoPlay(true);
            //设置指示器
            bannerHome.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
            //设置轮播时间
            bannerHome.setDelayTime(3000);
            //banner设置方法全部调用完毕时最后调用
            bannerHome.start();
        }

    }

    @Override
    public void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model) {

        if(model.getData() != null && model.getData().getResult() != null && model.getData().getResult().size() != 0){
            activityId = model.getData().getResult().get(0).getActivityId();

        }
    }

}
