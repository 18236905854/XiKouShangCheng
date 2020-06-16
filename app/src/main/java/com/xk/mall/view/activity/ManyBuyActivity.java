package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.entity.ManyCartsBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.ManyBuyViewImpl;
import com.xk.mall.presenter.ManyBuyPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.GridSpacingItemDecoration;
import com.xk.mall.utils.ShareType;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.adapter.ManyBuyViewPagerAdapter;
import com.xk.mall.view.adapter.ManyHotAdapter;
import com.xk.mall.view.fragment.ManyBuyChildFragment;
import com.xk.mall.view.widget.DialogShare;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.XKBannerMany;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName ManyBuyActivity
 * Description 多买多折页面
 * Author 卿凯
 * Date 2019/6/22/022
 * Version V1.0
 */
public class ManyBuyActivity extends BaseActivity<ManyBuyPresenter> implements ManyBuyViewImpl {
    @BindView(R.id.banner_many_buy)
    XKBannerMany banner;//多买多折头部
    @BindView(R.id.rv_many_hot)
    RecyclerView rvManyHot;//多买多折今日爆款列表
    @BindView(R.id.iv_many_invite)
    ImageView ivManyInvite;//多买多折邀请好友图片
    @BindView(R.id.tab_many_buy)
    SlidingTabLayout tabManyBuy;
    @BindView(R.id.vp_many_buy)
    ViewPager vpManyBuy;
    @BindView(R.id.ll_share)
    FrameLayout flShare;
    @BindView(R.id.tv_many_title_one)
    TextView tvTitleOne;
    @BindView(R.id.tv_many_title_child_one)
    TextView tvTitleChildOne;
    @BindView(R.id.tv_many_title_two)
    TextView tvTitleTwo;
    @BindView(R.id.tv_many_title_child_two)
    TextView tvTitleChildTwo;
    @BindView(R.id.appbar_many)
    AppBarLayout appBarLayout;
    private int page = 1;//页数
    private int limit = 10;//每页的条数
    private String activityId = "";//活动ID

    @Override
    protected ManyBuyPresenter createPresenter() {
        return new ManyBuyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_many_buy;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("多买多折");
        showRightIcon(true);
        setOnRightIconClickListener(v -> {
            goCart();
        });
        flShare.setOnClickListener(v -> {
            //分享
            getShareContent();
        });
    }

    @Keep
    @LoginFilter
    public void goCart(){
        //跳转购物车页面
        ActivityUtils.startActivity(ManyCartActivity.class);
    }

    @Override
    protected void initData() {
//        LinearLayoutManager manager = new LinearLayoutManager(mContext);
//        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        rvManyHot.addItemDecoration(new GridSpacingItemDecoration(3, ConvertUtils.dp2px(12), false));
        rvManyHot.setLayoutManager(manager);
        if(!TextUtils.isEmpty(MyApplication.userId)){
            mPresenter.getCartData(MyApplication.userId);
        }
        mPresenter.getActiveSectionData(ActivityType.ACTIVITY_MANY_BUY);
//        mPresenter.getManyBuyData(page, limit);
    }

    /**
     * 获取分享内容
     */
    private void getShareContent(){
        if(!TextUtils.isEmpty(activityId)){
            mPresenter.getShareContent(MyApplication.userId, activityId, ShareType.ACTIVITY_MANY_BUY);
        }
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        ShareBean shareBean = model.getData();
        if(shareBean != null){
            MyApplication.shareType = ShareType.ACTIVITY_MANY_BUY;
            DialogShare dialogShare = new DialogShare(mContext, shareBean);
            dialogShare.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(MyApplication.userId)){
            mPresenter.getCartData(MyApplication.userId);
        }
    }

    @Override
    public void onGetCartDataSuc(BaseModel<List<ManyCartsBean>> baseModel) {
        Logger.e(TAG + "onGetCartDataSuc");
        hideLoading();
        if(baseModel != null && baseModel.getData() != null && baseModel.getData().size() != 0){
            showCartNum(true);
            int size = 0;
            for (ManyCartsBean manyCartsBean : baseModel.getData()){
                size += manyCartsBean.getList().size();
            }
            tvCartNum.setText(String.valueOf(size));
        }else {
            showCartNum(false);
        }
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareCallback(ShareSuccessEvent event){
        if(event.shareType == ShareType.ACTIVITY_MANY_BUY){
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.ACTIVITY_MANY_BUY);
        }
    }

    @Override
    public void onShareCallback(BaseModel model) {

    }

    @Override
    public void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model) {
        if(model.getData() != null && model.getData().getResult() != null && model.getData().getResult().size() != 0){
            activityId = model.getData().getResult().get(0).getActivityId();
            ManyHotAdapter manyHotAdapter = new ManyHotAdapter(mContext, R.layout.many_buy_hot_item, model.getData().getResult());
            rvManyHot.setAdapter(manyHotAdapter);
            manyHotAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    //点击进入多买多折商品详情页面
                    Intent intent = new Intent(mContext, ManyGoodsDetailActivity.class);
                    ActiveSectionGoodsBean manyBean = model.getData().getResult().get(position);
                    intent.putExtra(ManyGoodsDetailActivity.ACTIVITY_GOODS_ID,manyBean.getActivityGoodsId());
                    ActivityUtils.startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }
    }

    @Override
    public void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model) {
        if(model.getData() != null){
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
                if(!XiKouUtils.isNullOrEmpty(sectionList.get(0).getCategoryChildName())){
                    tvTitleChildOne.setText(sectionList.get(0).getCategoryChildName());
                }
                if(!XiKouUtils.isNullOrEmpty(sectionList.get(1).getCategoryChildName())){
                    tvTitleChildTwo.setText(sectionList.get(1).getCategoryChildName());
                }
                mPresenter.getActiveSectionGoods(sectionList.get(0).getId(), ActivityType.ACTIVITY_MANY_BUY,MyApplication.userId, page, limit);
                bindTimes(sectionList.get(1).getChildSectionList());
            }else{
                tvTitleOne.setText(sectionList.get(0).getCategoryName());
                tvTitleTwo.setVisibility(View.GONE);
                if(!XiKouUtils.isNullOrEmpty(sectionList.get(0).getCategoryChildName())){
                    tvTitleChildOne.setText(sectionList.get(0).getCategoryChildName());
                }
            }
        }
    }

    /**
     * 绑定该页面的时间分类
     */
    private void bindTimes(List<ActiveSectionBean.SectionListBean.ChildSectionListBeanX> childSectionList) {
        if(childSectionList == null || childSectionList.size() == 0){
            tabManyBuy.setVisibility(View.GONE);
            vpManyBuy.setVisibility(View.GONE);
//            AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) appBarLayout.getChildAt(0).getLayoutParams();
//            mParams.setScrollFlags(0);
        }else {
//            AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) appBarLayout.getChildAt(0).getLayoutParams();
//            mParams.setScrollFlags(5);
            List<String> times = new ArrayList<>();
            for(ActiveSectionBean.SectionListBean.ChildSectionListBeanX listBeanX : childSectionList){
                times.add(listBeanX.getCategoryName());
            }
            List<Fragment> fragments = new ArrayList<>();
            for(ActiveSectionBean.SectionListBean.ChildSectionListBeanX time : childSectionList){
                fragments.add(ManyBuyChildFragment.getInstance(time.getId()));
            }
//            if (fragments.size() <= 6) {
//                tabManyBuy.setTabSpaceEqual(true);
//            } else {
//                tabManyBuy.setTabSpaceEqual(false);
//            }
            ManyBuyViewPagerAdapter manyBuyViewPagerAdapter = new ManyBuyViewPagerAdapter(getSupportFragmentManager(), fragments, times);
            vpManyBuy.setAdapter(manyBuyViewPagerAdapter);
            vpManyBuy.setOffscreenPageLimit(times.size());
            tabManyBuy.setViewPager(vpManyBuy);
            tabManyBuy.setCurrentTab(0);
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
            integerList.add(R.drawable.ic_many_head);
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
            ivManyInvite.setVisibility(View.GONE);
        }else {
            ivManyInvite.setVisibility(View.VISIBLE);
            GlideUtil.show(mContext, center.getImageUrl(), ivManyInvite);
            BannerBean finalCenter = center;
            ivManyInvite.setOnClickListener(v -> {
                String skipType = finalCenter.getSkipType();
                String targetParams = finalCenter.getTargetParams();
                String webUrl = finalCenter.getTargetUrl1();
                XiKouUtils.parseBannner(mContext,skipType,targetParams,webUrl);
            });
        }
    }
}
