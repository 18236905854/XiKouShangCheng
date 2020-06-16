package com.xk.mall.view.fragment;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.kennyc.view.MultiStateView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ActivityBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.impl.ActivityViewImpl;
import com.xk.mall.presenter.ActivityPresenter;
import com.xk.mall.utils.BigDecimalUtil;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.GridSpacingItemDecoration;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.activity.CutGoodsDetailActivity;
import com.xk.mall.view.activity.CutMainActivity;
import com.xk.mall.view.activity.GlobalBuyerActivity;
import com.xk.mall.view.activity.GlobalBuyerGoodsDetailActivity;
import com.xk.mall.view.activity.ManyBuyActivity;
import com.xk.mall.view.activity.ZeroMainActivity;
import com.xk.mall.view.adapter.ActivityGlobalBuyerAdapter;
import com.xk.mall.view.widget.CircleProgressBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * ClassName AttentionFragment
 * Description 活动页面
 * Author 卿凯
 * Date 2019/6/15/015
 * Version V1.0
 */
public class ActivityFragment extends BaseFragment<ActivityPresenter> implements ActivityViewImpl {
    private static final String TAG = "ActivityFragment";
    //    @BindView(R.id.toolBar)
//    Toolbar toolBar;
    @BindView(R.id.stateView)
    MultiStateView stateView;
    @BindView(R.id.refresh_activity)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_activity_head)
    ImageView ivActivityHead;//活动头部图片
    @BindView(R.id.iv_activity_zero_head)
    ImageView ivActivityZeroHead;//0元拍头部
    @BindView(R.id.rl_activity_zero)
    RelativeLayout rlActivityZero;//0元拍整理布局
    @BindView(R.id.iv_activity_zero)
    ImageView ivZero;//0元拍图片
    @BindView(R.id.tv_activity_zero_num)
    TextView tvActivityZeroNum;//0元拍商品数
    @BindView(R.id.tv_activity_zero_man)
    TextView tvActivityZeroMan;//0元拍抢拍人数
    @BindView(R.id.ll_activity_many_buy_parent)
    LinearLayout llManyBuyParent;//多买多折父布局
    @BindView(R.id.banner_activity)
    Banner bannerActivity;//多买多折banner
    @BindView(R.id.iv_activity_many_buy_head)
    ImageView ivActivityManyBuyHead;//多买多折头部
    @BindView(R.id.ll_activity_many_buy)
    LinearLayout llActivityManyBuy;//多买多折右边布局
    @BindView(R.id.tv_many_rate_one)
    TextView tvRateOne;
    @BindView(R.id.tv_many_rate_two)
    TextView tvRateTwo;
    @BindView(R.id.tv_many_rate_three)
    TextView tvRateThree;
    @BindView(R.id.iv_activity_cut_head)
    ImageView ivActivityCutHead;//喜立得头部
    @BindView(R.id.rv_activity_cut)
    RecyclerView rvActivityCut;//喜立得的view
    @BindView(R.id.iv_activity_global_head)
    ImageView ivActivityGlobalHead;//全球买手头部
    @BindView(R.id.iv_activity_global_one)
    ImageView ivGlobalOne;//全球买手banner
    @BindView(R.id.rv_activity_global_buyer)
    RecyclerView rvActivityGlobalBuyer;//全球买手布局


    public static ActivityFragment getInstance(String title) {
        ActivityFragment sf = new ActivityFragment();
        return sf;
    }

    @Override
    protected ActivityPresenter createPresenter() {
        return new ActivityPresenter(this);
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_activity;
    }



    @Override
    protected void initData(){
//        mPresenter.getActivityData();
//        mPresenter.getBannerList();
//        refreshLayout.setEnableLoadMore(false);
//        refreshLayout.setOnRefreshListener(refreshLayout -> {
//            mPresenter.getActivityData();
//            mPresenter.getBannerList();
//        });
    }

    @Override
    protected void loadLazyData() {
        Log.e(TAG, "loadLazyData:===== " );
        mPresenter.getActivityData();
        mPresenter.getBannerList();
        refreshLayout.setEnableLoadMore(false);
        Button btnReplay = stateView.findViewById(R.id.btn_replay);
        CircleProgressBar pbLoading = stateView.findViewById(R.id.pb_header_loading);
        ValueAnimator animator = ValueAnimator.ofInt(0, 100).setDuration(2000);
        animator.addUpdateListener(valueAnimator -> pbLoading.setProgress((int) valueAnimator.getAnimatedValue()));
        animator.start();
        btnReplay.setOnClickListener(v -> {
            mPresenter.getActivityData();
            mPresenter.getBannerList();
        });
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.getActivityData();
            mPresenter.getBannerList();
        });
    }

    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        stateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void onGetActivityDataSuccess(BaseModel<ActivityBean> model) {
        stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        refreshLayout.finishRefresh();
        if(model.getData() != null){
            if(model.getData().getAuctionHomeActivityVo() != null){
                rlActivityZero.setVisibility(View.VISIBLE);
                ivActivityZeroHead.setVisibility(View.VISIBLE);
                bindZero(model.getData().getAuctionHomeActivityVo());
            }else {
                rlActivityZero.setVisibility(View.GONE);
                ivActivityZeroHead.setVisibility(View.GONE);
            }
            if(model.getData().getDiscountHomeActivityVo() != null){
                llManyBuyParent.setVisibility(View.VISIBLE);
                bindManyBuy(model.getData().getDiscountHomeActivityVo());
            }else {
                llManyBuyParent.setVisibility(View.GONE);
            }
            if(model.getData().getBargainHomeActivityVo() != null){
                rvActivityCut.setVisibility(View.VISIBLE);
                ivActivityCutHead.setVisibility(View.VISIBLE);
                bindCut(model.getData().getBargainHomeActivityVo());
            }else {
                rvActivityCut.setVisibility(View.GONE);
                ivActivityCutHead.setVisibility(View.GONE);
            }
            if(model.getData().getGlobalBuyerHomeActivityVo() != null){
                rvActivityGlobalBuyer.setVisibility(View.VISIBLE);
                ivGlobalOne.setVisibility(View.VISIBLE);
                ivActivityGlobalHead.setVisibility(View.VISIBLE);
                bindGlobalBuyer(model.getData().getGlobalBuyerHomeActivityVo());
            }else {
                rvActivityGlobalBuyer.setVisibility(View.GONE);
                ivGlobalOne.setVisibility(View.GONE);
                ivActivityGlobalHead.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取活动主图成功
     * @param model
     */
    @Override
    public void onGetBannerSuc(BaseModel<List<BannerBean>> model) {
        List<BannerBean> datas = model.getData();
        if(datas!=null && datas.size()>0){
            if(datas.get(0).getPosition()==3){//主图
                if(!TextUtils.isEmpty(datas.get(0).getImageUrl())){
                    GlideUtil.show(mContext,datas.get(0).getImageUrl(),ivActivityHead);
                    ivActivityHead.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Logger.e("","onClick");
                        }
                    });
                }
            }
        }
    }

    /**
     * 绑定0元拍数据
     */
    private void bindZero(ActivityBean.AuctionHomeActivityVoBean activityModelBean){
        Logger.e("绑定0元拍");
        GlideUtil.showRadius(mContext, activityModelBean.getBannerUrl(),2, ivZero);
        tvActivityZeroNum.setText(String.valueOf(activityModelBean.getAuctionCommodityCounts()));
        tvActivityZeroMan.setText(String.valueOf(activityModelBean.getAuctionUserCounts()));
    }

    /**
     * 绑定全球买手数据
     */
    private void bindGlobalBuyer(ActivityBean.GlobalBuyerHomeActivityVoBean globalBuyerBeans){
        Logger.e("绑定全球买手");
        GlideUtil.show(mContext, globalBuyerBeans.getBannerUrl(), ivGlobalOne);
        rvActivityGlobalBuyer.setLayoutManager(new GridLayoutManager(mContext, 2));
        ActivityGlobalBuyerAdapter activityGlobalBuyerAdapter =
                new ActivityGlobalBuyerAdapter(mContext, R.layout.activity_global_buyer_adapter, globalBuyerBeans.getGlobalBuyerCommodityList());
        rvActivityGlobalBuyer.setAdapter(activityGlobalBuyerAdapter);
        activityGlobalBuyerAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //点击进入商品详情页面
                Intent intent = new Intent(mContext, GlobalBuyerGoodsDetailActivity.class);
                ActivityBean.GlobalBuyerHomeActivityVoBean.GlobalBuyerCommodityListBean activityModelsBean =
                        globalBuyerBeans.getGlobalBuyerCommodityList().get(position);
//                intent.putExtra(GlobalBuyerGoodsDetailActivity.GOODS_ID, activityModelsBean.getGoodsId());
//                intent.putExtra(GlobalBuyerGoodsDetailActivity.ACTIVITY_ID, activityModelsBean.getActivityId());
//                intent.putExtra(GlobalBuyerGoodsDetailActivity.COMMODITY_ID, activityModelsBean.getCommodityId());
                intent.putExtra(GlobalBuyerGoodsDetailActivity.ACTIVITY_GOODS_ID, activityModelsBean.getId());
//                intent.putExtra(GlobalBuyerGoodsDetailActivity.GOODS_NAME, activityModelsBean.getCommodityName());
//                intent.putExtra(GlobalBuyerGoodsDetailActivity.GOODS_PRICE, activityModelsBean.getCommodityPrice());
//                intent.putExtra(GlobalBuyerGoodsDetailActivity.GOODS_LINE_PRICE, activityModelsBean.getSalePrice());
//                intent.putExtra(GlobalBuyerGoodsDetailActivity.GOODS_COUPON, activityModelsBean.getCouponValue());
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    /**
     * 绑定多买多折数据
     */
    private void bindManyBuy(ActivityBean.DiscountHomeActivityVoBean activityModelBean){
        Logger.e("绑定多买多折");
        List<String> imgs = new ArrayList<>();
        if(TextUtils.isEmpty(activityModelBean.getBannerUrl())){
            imgs.addAll(Arrays.asList(activityModelBean.getImageUrls()));
        }else {
            imgs.add(activityModelBean.getBannerUrl());
        }
        if(activityModelBean.getRule().length != 0 && activityModelBean.getRule().length >= 3){
            SPUtils.getInstance().put(Constant.RATE_ONE, activityModelBean.getRule()[0]);
            SPUtils.getInstance().put(Constant.RATE_TWO, activityModelBean.getRule()[1]);
            SPUtils.getInstance().put(Constant.RATE_THREE, activityModelBean.getRule()[2]);
        }
        bannerActivity.setImageLoader(new GlideImageLoader());
        bannerActivity.setImages(imgs);
        bannerActivity.setBannerStyle(BannerConfig.NUM_INDICATOR);
        bannerActivity.setIndicatorGravity(BannerConfig.RIGHT);
        //设置nearBanner动画效果
        bannerActivity.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        bannerActivity.isAutoPlay(true);
        //设置轮播时间
        bannerActivity.setDelayTime(1500);
        //banner设置方法全部调用完毕时最后调用
        bannerActivity.start();
        bannerActivity.setOnBannerListener(position -> {
            ActivityUtils.startActivity(ManyBuyActivity.class);
        });
        if(activityModelBean.getRule().length >= 3){
            Float rateOne = activityModelBean.getRule()[0] * 10;
            Float rateTwo = activityModelBean.getRule()[1] * 10;
            Float rateThree = activityModelBean.getRule()[2] * 10;
            tvRateOne.setText(rateOne.intValue() + "%");
            tvRateTwo.setText(rateTwo.intValue() + "%");
            tvRateThree.setText(rateThree.intValue() + "%");
        }else {
            tvRateOne.setText("50%");
            tvRateTwo.setText("40%");
            tvRateThree.setText("30%");
        }
    }

    /**
     * 设置喜立得数据
     */
    private void bindCut(ActivityBean.BargainHomeActivityVoBean modelBean){
        Logger.e("绑定喜立得");
        //设置喜立得的数据
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        List<ActivityBean.BargainHomeActivityVoBean.BargainCommodityListBean> cutList =
                modelBean.getBargainCommodityList();
        if(cutList.size() <= 4){
            ActivityBean.BargainHomeActivityVoBean.BargainCommodityListBean activityVoBean =
                    new ActivityBean.BargainHomeActivityVoBean.BargainCommodityListBean();
            activityVoBean.setGoodsImageUrl(modelBean.getBannerUrl());
            cutList.add(activityVoBean);
        }
        else {
            cutList = cutList.subList(0, 5);
            cutList.get(4).setGoodsImageUrl(modelBean.getBannerUrl());
        }

        List<ActivityBean.BargainHomeActivityVoBean.BargainCommodityListBean> finalCutList = cutList;
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if(i == finalCutList.size() - 1){
                    return 2;
                }else {
                    return 1;
                }
            }
        });
        ActivityCutAdapter activityCutAdapter = new ActivityCutAdapter(mContext, R.layout.item_activity_cut, cutList);
        rvActivityCut.setLayoutManager(manager);
        rvActivityCut.addItemDecoration(new GridSpacingItemDecoration(3, ConvertUtils.dp2px(8), false));
        rvActivityCut.setAdapter(activityCutAdapter);
        List<ActivityBean.BargainHomeActivityVoBean.BargainCommodityListBean> finalCutList1 = cutList;
        activityCutAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(position != finalCutList1.size() - 1){
                    //点击进入喜立得商品详情
                    ActivityBean.BargainHomeActivityVoBean.BargainCommodityListBean modelsBean = finalCutList1.get(position);
                    Intent intent = new Intent(mContext, CutGoodsDetailActivity.class);
                    intent.putExtra(CutGoodsDetailActivity.ACTIVITY_GOODS_ID, modelsBean.getId());
//                    intent.putExtra(CutGoodsDetailActivity.CUT_ID, modelsBean.getId());
//                    intent.putExtra(CutGoodsDetailActivity.GOODS_NAME, modelsBean.getCommodityName());
//                    intent.putExtra(CutGoodsDetailActivity.GOODS_PRICE,"" + PriceUtil.dividePrice(modelsBean.getCommodityPrice()));
//                    intent.putExtra(CutGoodsDetailActivity.GOODS_LINE_PRICE, "" + PriceUtil.dividePrice(modelsBean.getSalePrice()));
                    ActivityUtils.startActivity(intent);
                }else {
                    //跳转喜立得主页
                    ActivityUtils.startActivity(CutMainActivity.class);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    class ActivityCutAdapter extends CommonAdapter<ActivityBean.BargainHomeActivityVoBean.BargainCommodityListBean>{
        private List<ActivityBean.BargainHomeActivityVoBean.BargainCommodityListBean> data;
        public ActivityCutAdapter(Context context, int layoutId, List<ActivityBean.BargainHomeActivityVoBean.BargainCommodityListBean> datas) {
            super(context, layoutId, datas);
            this.data = datas;
        }

        @Override
        protected void convert(ViewHolder holder, ActivityBean.BargainHomeActivityVoBean.BargainCommodityListBean cutBean, int position) {
            ImageView ivLogo = holder.getView(R.id.iv_activity_cut_logo);
            TextView tvDiscount = holder.getView(R.id.tv_activity_cut_discount);
            if(position == data.size() - 1){
//                ivLogo.setImageResource(R.drawable.ic_activity_cut_five);
                GlideUtil.showRadius(mContext, cutBean.getGoodsImageUrl(),2, ivLogo);
                tvDiscount.setVisibility(View.GONE);
            }else {
                GlideUtil.showRadius(mContext, cutBean.getGoodsImageUrl(),2, ivLogo);
//                tvDiscount.setVisibility(View.VISIBLE);
            }
            int width = ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(15) * 2 - ConvertUtils.dp2px(8) * 2;
            if(width != 0){
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width/3);
                holder.itemView.setLayoutParams(lp);
            }
        }
    }

    @OnClick({R.id.iv_activity_zero_head, R.id.iv_activity_many_buy_head, R.id.ll_activity_many_buy,R.id.rl_activity_zero,
            R.id.iv_activity_cut_head, R.id.iv_activity_global_head, R.id.iv_activity_global_one})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.iv_activity_zero_head://0元拍
            case R.id.rl_activity_zero:
                ActivityUtils.startActivity(ZeroMainActivity.class);
                break;

            case R.id.iv_activity_many_buy_head://多买多折
            case R.id.ll_activity_many_buy:
                ActivityUtils.startActivity(ManyBuyActivity.class);
                break;

            case R.id.iv_activity_cut_head://喜立得
                ActivityUtils.startActivity(CutMainActivity.class);
                break;

            case R.id.iv_activity_global_head://全球买手
            case R.id.iv_activity_global_one:
                ActivityUtils.startActivity(GlobalBuyerActivity.class);
                break;

        }
    }

}
