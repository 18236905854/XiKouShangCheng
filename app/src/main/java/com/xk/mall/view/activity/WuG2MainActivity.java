package com.xk.mall.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.WuGGoodsBean;
import com.xk.mall.model.entity.WuGPageBean;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.WuG2MainImpl;
import com.xk.mall.presenter.WuG2MainPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.ShareType;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.adapter.WugNewGoodsAdapter;
import com.xk.mall.view.widget.DialogShare;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.XKBanner;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: WuG2MainActivity
 * @Description: 吾G首页
 * @Author: 卿凯
 * @Date: 2019/9/20/020 9:58
 * @Version: 1.0
 */
public class WuG2MainActivity extends BaseActivity<WuG2MainPresenter> implements WuG2MainImpl {

    @BindView(R.id.ll_wug_default)
    LinearLayout llWuGDefault;//默认
    @BindView(R.id.tv_wug_default)
    TextView tvWugDefault;//默认文字
    @BindView(R.id.iv_wug_default)
    ImageView ivWugDefault;//默认图标
    @BindView(R.id.ll_wug_price)
    LinearLayout llWuGPrcie;//价格排序
    @BindView(R.id.tv_wug_price)
    TextView tvWugPrice;//价格文字
    @BindView(R.id.iv_wug_price)
    ImageView ivWugPrice;//价格图标
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshWuG)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.state_view_order)
    MultiStateView stateView;
    @BindView(R.id.banner_home)
    XKBanner bannerHome;
    int page = 1;
    int sort = 1;//排序字段，1：时间，2：价格
    int sortDirect = 2;//排序方向，1：升序，2：降序
    private boolean isHasMore = true;
    private String activityId = "";//活动ID
    private List<WuGGoodsBean> newData = new ArrayList<>();//最新寄卖数据
    private WugNewGoodsAdapter hotRankAdapter;

    @Override
    protected WuG2MainPresenter createPresenter() {
        return new WuG2MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wug_main;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar_title.setTextColor(Color.parseColor("#444444"));
        toolbar_title.setText("吾G购");
        setShowDialog(false);
        setRightDrawable(R.drawable.ic_activity_share);
        setOnRightIconClickListener(v -> {
            getShareContent();
        });
    }

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        newData = new ArrayList<>();
        tvWugDefault.setSelected(true);
        tvWugPrice.setSelected(false);
        ivWugDefault.setImageResource(R.drawable.near_order_up);
        mPresenter.getActiveSectionData(ActivityType.ACTIVITY_WUG);
        mPresenter.getWuGData(page, Constant.limit, sort, sortDirect);
        hotRankAdapter = new WugNewGoodsAdapter(mContext, newData, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                if(position != -1 && position  < newData.size()){//防止数组越界
                    Intent intent = new Intent(mContext, WuGGoodsDetailActivity.class);
                    intent.putExtra(WuGGoodsDetailActivity.ACTIVITY_GOODS_ID, newData.get(position).getId());
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(hotRankAdapter);
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if(isHasMore){
                page += 1;
                mPresenter.getWuGData(page, Constant.limit, sort, sortDirect);
            }
        });
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            sort = 1;
            sortDirect = 2;
            isHasMore = true;
            mPresenter.getActiveSectionData(ActivityType.ACTIVITY_WUG);
            mPresenter.getWuGData(page, Constant.limit, sort, sortDirect);
        });
    }

    @OnClick({R.id.ll_wug_default, R.id.ll_wug_price})
    public void onClickView(View view){
        if(view.getId() == R.id.ll_wug_default){
            if(sort == 1){
                return;
            }
            sort = 1;
            page = 1;
            tvWugDefault.setSelected(true);
            tvWugDefault.setTextAppearance(mContext, R.style.textStyleBold);
            tvWugPrice.setSelected(false);
            tvWugPrice.setTextAppearance(mContext, R.style.textStyleDefault);
            sortDirect = 2;
//            if(sortDirect == 1){
//                ivWugDefault.setImageResource(R.drawable.near_order_up);
//            }else {
//                ivWugDefault.setImageResource(R.drawable.near_order_down);
//            }
            ivWugPrice.setImageResource(R.drawable.near_order_default);
        }else if(view.getId() == R.id.ll_wug_price){
            sort = 2;
            page = 1;
            tvWugDefault.setSelected(false);
            tvWugDefault.setTextAppearance(mContext, R.style.textStyleDefault);
            tvWugPrice.setSelected(true);
            tvWugPrice.setTextAppearance(mContext, R.style.textStyleBold);
            sortDirect = sortDirect == 1? 2 : 1;
            if(sortDirect == 1){
                ivWugPrice.setImageResource(R.drawable.near_order_up);
            }else {
                ivWugPrice.setImageResource(R.drawable.near_order_down);
            }
            ivWugDefault.setImageResource(R.drawable.near_order_default);
        }
        mPresenter.getWuGData(page, Constant.limit, sort, sortDirect);
    }

    /**
     * 获取分享内容
     */
    private void getShareContent() {
        if (!TextUtils.isEmpty(activityId)) {
            mPresenter.getShareContent(MyApplication.userId, activityId, ShareType.ACTIVITY_WUG);
        }
    }

    @Override
    public void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model) {
        if(model != null && model.getData() != null && model.getData().getBannerList() != null){
            bindBanner(model.getData().getBannerList());
        }
    }

    /**
     * 绑定banner
     */
    private void bindBanner(List<BannerBean> bannerList) {
        bannerHome.setImageLoader(new GlideImageLoader());
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
    public void onGetWuGDataSuccess(BaseModel<WuGPageBean> model) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if(model != null && model.getData() != null){
            if(model.getData().result != null && model.getData().result.size() != 0){
                activityId = model.getData().result.get(0).getActivityId();
                if(page == 1){
                    newData.clear();
                }
                newData.addAll(model.getData().result);
                hotRankAdapter.notifyDataSetChanged();
                if(model.getData().result.size() < Constant.limit){
                    refreshLayout.setEnableLoadMore(false);
                }else {
                    refreshLayout.setEnableLoadMore(true);
                }
            }else {
                refreshLayout.setEnableLoadMore(false);
            }
        }else {
            stateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        ShareBean shareBean = model.getData();
        if (shareBean != null) {
            DialogShare dialogShare = new DialogShare(mContext, shareBean);
            MyApplication.shareType = ShareType.ACTIVITY_WUG;
            dialogShare.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareCallback(ShareSuccessEvent event) {
        if (event.shareType == ShareType.ACTIVITY_WUG) {
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.ACTIVITY_WUG);
        }
    }

    @Override
    public void onShareCallback(BaseModel model) {

    }

}
