package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.flyco.tablayout.SlidingChildTabLayout;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.ActivityRoundBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ZeroBean;
import com.xk.mall.model.entity.ZeroCurrentPriceBean;
import com.xk.mall.model.entity.ZeroGoodsBean;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.ZeroViewImpl;
import com.xk.mall.presenter.ZeroPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.EventBusMessage;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareType;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.adapter.ManyBuyViewPagerAdapter;
import com.xk.mall.view.adapter.ManyHotAdapter;
import com.xk.mall.view.adapter.ZeroViewPagerAdapter;
import com.xk.mall.view.fragment.ManyBuyChildFragment;
import com.xk.mall.view.fragment.ZeroFragment;
import com.xk.mall.view.widget.DialogShare;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.countdownview.CountdownView;

/**
 * ClassName ZeroMainActivity
 * Description 0元拍页面
 * Author 卿凯
 * Date 2019/6/22/022
 * Version V1.0
 */
public class ZeroMainActivity extends BaseActivity<ZeroPresenter> implements ZeroViewImpl {
//    @BindView(R.id.banner_zero)
    //使用butterKnife绑定布局刷新时会报空指针，所以改为findViewById形式
    Banner banner;//头部图片
//    @BindView(R.id.cv_zero_countdown)
    CountdownView cvZeroCountdown;//倒计时
//    @BindView(R.id.tv_zero_title_one)
    TextView tvTitleOne;
//    @BindView(R.id.tv_zero_title_two)
    TextView tvTitleTwo;
//    @BindView(R.id.rv_zero_countdown)
    RecyclerView rvZeroCountdown;//倒计时专区数据
//    @BindView(R.id.rv_zero_hot)
//    RecyclerView rvZeroHot;//正在热拍专区
//    @BindView(R.id.tv_juli_over)
    TextView tvJuliOver;
//    @BindView(R.id.rl_zero_count_down)
    RelativeLayout rlZeroCountDown;//倒计时专区布局
//    @BindView(R.id.rl_countdown)
    RelativeLayout rlCountDowm;
//    @BindView(R.id.iv_zero_invite)
    ImageView ivZeroInvite;
//    @BindView(R.id.viewPage_zero)
    ViewPager vpZero;
//    @BindView(R.id.tab_zero)
    SlidingChildTabLayout tabZero;
    private List<ActivityRoundBean> roundBeanList;//活动轮次的数据集合
    private List<String> titles=new ArrayList<>();
    private List<String> childTitles=new ArrayList<>();
    private String activityId = "";//活动ID
    private CountDownAdapter countDownAdapter;//倒计时专区的adapter
    private List<ZeroGoodsBean> countList;//倒计时专区数据

    @Override
    protected ZeroPresenter createPresenter() {
        return new ZeroPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zero;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("0元抢");
        setRightDrawable(R.drawable.ic_activity_share);
        setOnRightIconClickListener(v -> {
            getShareContent();
        });
    }

    @Override
    protected void initData() {
        setShowDialog(false);
        banner = findViewById(R.id.banner_zero);//头部图片
        cvZeroCountdown = findViewById(R.id.cv_zero_countdown);//倒计时
        tvTitleOne = findViewById(R.id.tv_zero_title_one);
        tvTitleTwo = findViewById(R.id.tv_zero_title_two);
        rvZeroCountdown = findViewById(R.id.rv_zero_countdown);//倒计时专区数据
        tvJuliOver = findViewById(R.id.tv_juli_over);
        rlZeroCountDown = findViewById(R.id.rl_zero_count_down);//倒计时专区布局
        rlCountDowm = findViewById(R.id.rl_countdown);
        ivZeroInvite = findViewById(R.id.iv_zero_invite);
        vpZero = findViewById(R.id.viewPage_zero);
        tabZero = findViewById(R.id.tab_zero);
        LinearLayoutManager manager =  new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        countList = new ArrayList<>();
        rvZeroCountdown.setLayoutManager(manager);
        mPresenter.getActiveSectionData(ActivityType.ACTIVITY_ZERO);
        mPresenter.getActivityType(3);
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
            mPresenter.getShareContent(MyApplication.userId, activityId, ShareType.ACTIVITY_ZERO);
        }
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        ShareBean shareBean = model.getData();
        if(shareBean != null){
            DialogShare dialogShare = new DialogShare(mContext, shareBean);
            MyApplication.shareType = ShareType.ACTIVITY_ZERO;
            dialogShare.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareCallback(ShareSuccessEvent event){
        if(event.shareType == ShareType.ACTIVITY_ZERO){
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.ACTIVITY_ZERO);
        }
    }

    private boolean isBind;//是否绑定过时间
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void notifyData(ZeroGoodsBean zeroGoodsBean){
        if(zeroGoodsBean != null && !isBind){
            //1:未开始，2:已开始，3:已结束，4:已取消, 5:已流拍
            if(zeroGoodsBean.getStatus() == 1){
                tvJuliOver.setText("未开始");
                tvJuliOver.setVisibility(View.VISIBLE);
                rlZeroCountDown.setVisibility(View.GONE);
            }else if(zeroGoodsBean.getStatus() == 3){
                tvJuliOver.setText("已结束");
                tvJuliOver.setVisibility(View.VISIBLE);
                rlZeroCountDown.setVisibility(View.GONE);
            }else {
//                long endTime = TimeUtils.string2Millis(zeroGoodsBean.getEndTime());
//                long currentTime = System.currentTimeMillis();
//                long timeValue = endTime - currentTime;
//                if(timeValue > 0){
                    tvJuliOver.setVisibility(View.GONE);
                    rlZeroCountDown.setVisibility(View.VISIBLE);
                    cvZeroCountdown.start(zeroGoodsBean.getCloseTime() * 1000);
                    cvZeroCountdown.setOnCountdownEndListener(cv -> {
                        isBind = false;
                        mPresenter.getActiveSectionData(ActivityType.ACTIVITY_ZERO);
                        mPresenter.getActivityType(3);
                    });
                    isBind = true;
//                }else {
//                    tvJuliOver.setVisibility(View.GONE);
//                    rlZeroCountDown.setVisibility(View.GONE);
//                }
            }
        }
    }

    @Override
    public void onShareCallback(BaseModel model) {

    }

    @Override
    public void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model) {
        if(model != null && model.getData() != null){
            bindBanner(model.getData().getBannerList());
            bindTitle(model.getData().getSectionList());
        }
    }

    /**
     * 绑定分类版块
     */
    private void bindTitle(List<ActiveSectionBean.SectionListBean> sectionList) {
        if(sectionList == null || sectionList.size() == 0){
            return;
        }else {
            if(sectionList.size() >= 2){
                tvTitleTwo.setText(sectionList.get(1).getCategoryName());
                tvTitleOne.setText(sectionList.get(0).getCategoryName());
                mPresenter.getActiveSectionGoods(sectionList.get(0).getId(), ActivityType.ACTIVITY_MANY_BUY,MyApplication.userId, 1, Constant.limit);
            }else{
                tvTitleOne.setText(sectionList.get(0).getCategoryName());
                tvTitleTwo.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 绑定banner
     */
    private void bindBanner(List<BannerBean> bannerList) {
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<String> images = new ArrayList<>();
        BannerBean center = null;//中间图片地址
        if (bannerList != null && bannerList.size() > 0) {
            for (int i = 0; i < bannerList.size(); i++) {
                if(bannerList.get(i).getPosition() == 1){
                    images.add(bannerList.get(i).getImageUrl());
                }
            }

            for(int i = 0; i < bannerList.size(); i++){
                if(bannerList.get(i).getPosition() == 2){
                    center = bannerList.get(i);
                    break;
                }
            }
            banner.setImages(images);
            //设置nearBanner动画效果
            banner.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置指示器
            banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
            //设置轮播时间
            banner.setDelayTime(3000);
            //banner设置方法全部调用完毕时最后调用
            banner.start();

            banner.setOnBannerListener(position -> {
                String skipType = bannerList.get(position).getSkipType();
                String targetParams = bannerList.get(position).getTargetParams();
                String webUrl = bannerList.get(position).getTargetUrl1();
                XiKouUtils.parseBannner(mContext,skipType,targetParams,webUrl);
            });
        }else {
            List<Integer> integerList = new ArrayList<>();
            integerList.add(R.drawable.ic_zero_head);
            banner.setImages(integerList);
            //设置nearBanner动画效果
            banner.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置指示器
            banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
            //设置轮播时间
            banner.setDelayTime(3000);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }

        if(center == null){
            ivZeroInvite.setVisibility(View.GONE);
        }else {
            ivZeroInvite.setVisibility(View.VISIBLE);
            GlideUtil.show(mContext, center.getImageUrl(), ivZeroInvite);
            BannerBean finalCenter = center;
            ivZeroInvite.setOnClickListener(v -> {
                String skipType = finalCenter.getSkipType();
                String targetParams = finalCenter.getTargetParams();
                String webUrl = finalCenter.getTargetUrl1();
                XiKouUtils.parseBannner(mContext,skipType,targetParams,webUrl);
            });
        }
    }

    @Override
    public void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model) {
        //设置adapter
        if(model.getData() != null && model.getData().getResult() != null && model.getData().getResult().size() != 0){
            activityId = model.getData().getResult().get(0).getActivityId();
        }

    }

    /**
     * 获取轮次成功接口
     */
    @Override
    public void onRoundSuccess(BaseModel<List<ActivityRoundBean>> baseModel) {
        titles.clear();
        childTitles.clear();
        roundBeanList = new ArrayList<>();
        roundBeanList.addAll(baseModel.getData());
//        if(baseModel.getData() != null && baseModel.getData().size() != 0){
//            long endTime = TimeUtils.string2Millis(baseModel.getData().get(0).endTime);
//            long currentTime = System.currentTimeMillis();
//            long timeValue = endTime - currentTime;
//            if(timeValue > 0){
//                tvJuliOver.setVisibility(View.GONE);
//                rlZeroCountDown.setVisibility(View.VISIBLE);
//                cvZeroCountdown.start(endTime);
//                cvZeroCountdown.setOnCountdownEndListener(cv -> {
//                    mPresenter.getActiveSectionData(ActivityType.ACTIVITY_ZERO);
//                    mPresenter.getActivityType(3);
//                });
//            }else {
//                tvJuliOver.setVisibility(View.GONE);
//                rlZeroCountDown.setVisibility(View.GONE);
//            }
//        }else{
//            tvJuliOver.setVisibility(View.GONE);
//            rlZeroCountDown.setVisibility(View.GONE);
//        }
        if(roundBeanList.size() != 0){
            mPresenter.getGoodsByRoundId(roundBeanList.get(0).id);
        }

        List<Fragment> fragments = new ArrayList<>();
        for(ActivityRoundBean time : roundBeanList){
            fragments.add(ZeroFragment.getInstance(time.id,time.state));
            titles.add(time.roundTitle);
            if(time.state==1){ //状态（1.未开始 2.失效,3:结束,4:进行中）
                childTitles.add("未开始");
            }else if(time.state==2){
                childTitles.add("已失效");
            }else if(time.state==3){
                childTitles.add("已结束");
            }else if(time.state==4){
                childTitles.add("进行中");
            }
        }
        ZeroViewPagerAdapter zeroViewPagerAdapter = new ZeroViewPagerAdapter(getSupportFragmentManager(), fragments, roundBeanList);
        vpZero.setAdapter(zeroViewPagerAdapter);
        vpZero.setOffscreenPageLimit(roundBeanList.size());
        tabZero.setViewPager(vpZero,titles,childTitles);

        vpZero.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    //倒计时专区 数据成功
    @Override
    public void onGetGoodsByRoundIdSuccess(BaseModel<List<ZeroGoodsBean>> baseModel) {
        if(baseModel.getData() != null && baseModel.getData().size() != 0){
            String activityId = baseModel.getData().get(0).getActivityId();
            EventBus.getDefault().post(new EventBusMessage(activityId));
//            if(baseModel.getData().get(0).getCloseTime() >0){
//                tvJuliOver.setVisibility(View.VISIBLE);
//                cvZeroCountdown.setVisibility(View.VISIBLE);
//                cvZeroCountdown.start(baseModel.getData().get(0).getCloseTime() * 1000);
//            }else{
//                tvJuliOver.setVisibility(View.GONE);
//                cvZeroCountdown.setVisibility(View.GONE);
//            }
            rlCountDowm.setVisibility(View.VISIBLE);
            rvZeroCountdown.setVisibility(View.VISIBLE);
            countList.clear();
            countList.addAll(baseModel.getData());
            countDownAdapter = new CountDownAdapter(mContext, R.layout.zero_countdown_single_item, countList);
            rvZeroCountdown.setAdapter(countDownAdapter);
            countDownAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    //点击进入详情
                    Intent intent = new Intent(mContext, ZeroGoodsDetailActivity.class);
                    intent.putExtra(ZeroGoodsDetailActivity.ACTIVITY_GOODS_ID, baseModel.getData().get(position).getId());
                    ActivityUtils.startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }else {
            rlCountDowm.setVisibility(View.GONE);
            rvZeroCountdown.setVisibility(View.GONE);
        }
        mPresenter.getGoodsCurrentPriceByRoundId(roundBeanList.get(0).id);
    }

    @Override
    public void onGetGoodsCurrentPrice(BaseModel<List<ZeroCurrentPriceBean>> baseModel) {
        List<ZeroCurrentPriceBean> zeroCurrentPriceBeanList = baseModel.getData();
        if(countList != null && countList.size() > 0 && zeroCurrentPriceBeanList.size() > 0){
            for (ZeroGoodsBean zeroGoodsBean : countList) {
                for (ZeroCurrentPriceBean zeroCurrentPriceBean : zeroCurrentPriceBeanList) {
                    if(zeroGoodsBean.getId().equals(zeroCurrentPriceBean.getAuctionCommodityId())){//商品id相同
                        zeroGoodsBean.setSalePrice(zeroCurrentPriceBean.getCurrentPrice());
                        zeroGoodsBean.setCloseTime(zeroCurrentPriceBean.getRemainingTime());
                        zeroGoodsBean.setStatus(zeroCurrentPriceBean.getStatus());
                    }
                }
            }
            countDownAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 倒计时专区的adapter
     */
    class CountDownAdapter extends CommonAdapter<ZeroGoodsBean> {

        public CountDownAdapter(Context context, int layoutId, List<ZeroGoodsBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ZeroGoodsBean globalBuyerBean, int position) {
            ImageView ivLogo = holder.getView(R.id.iv_zero_countdown_logo);
            GlideUtil.showRadius(mContext, globalBuyerBean.getGoodsImageUrl(), 2, ivLogo);
            holder.setText(R.id.tv_zero_countdown_name, globalBuyerBean.getCommodityName());
//            holder.setText(R.id.tv_zero_countdown_price,  PriceUtil.dividePrice(globalBuyerBean.getSalePrice()));

            int status = globalBuyerBean.getStatus();
//            if(status == 1){//1:未开始，2:已开始，3:已结束，4:已取消, 5:已流拍
//                holder.tvState.setVisibility(View.VISIBLE);
//                holder.tvState.setText("未开始");
//                holder.btn_zero_hot.setText("未开始");
//                holder.btn_zero_hot.setEnabled(false);
//                holder.btn_zero_hot.setBackgroundResource(R.drawable.bg_zero_hot_disable);
//            }else if(status == 3){
//                holder.tvState.setVisibility(View.VISIBLE);
//                holder.tvState.setText("已结束");
//                holder.btn_zero_hot.setText("已结束");
//                holder.btn_zero_hot.setEnabled(false);
//                holder.btn_zero_hot.setBackgroundResource(R.drawable.bg_zero_hot_disable);
//            }else if(status == 2){
//                holder.tvState.setVisibility(View.GONE);
//                holder.btn_zero_hot.setText("抢拍");
//                holder.btn_zero_hot.setEnabled(true);
//                holder.btn_zero_hot.setBackgroundResource(R.drawable.bg_zero_hot_enable);
//            }
//            holder.refreshTime(zeroGoodsBean.getCloseTime() *1000);
        }
    }

}
