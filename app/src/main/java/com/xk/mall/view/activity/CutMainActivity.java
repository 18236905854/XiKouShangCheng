package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.entity.CutPageBean;
import com.xk.mall.model.entity.CutServerBean;
import com.xk.mall.model.entity.CutShangJiaBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.CutViewImpl;
import com.xk.mall.presenter.CutPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.ShareType;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.adapter.CutNewAdapter;
import com.xk.mall.view.adapter.CutRecommendAdapter;
import com.xk.mall.view.adapter.CutTuiJianAdapter;
import com.xk.mall.view.widget.DialogShare;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.internal.queue.MpscLinkedQueue;

/**
 * ClassName CutMainActivity
 * Description 喜立得页面
 * Author 卿凯
 * Date 2019/6/24/024
 * Version V1.0
 */
public class CutMainActivity extends BaseActivity<CutPresenter> implements CutViewImpl {
    @BindView(R.id.banner_cut)
    Banner banner;
    @BindView(R.id.tv_cut_new)
    TextView tvNew;//最新上架
    @BindView(R.id.tv_cut_recommend)
    TextView tvRecommend;//为您推荐
    @BindView(R.id.iv_cut_invite)
    ImageView ivCutInvite;
    @BindView(R.id.rv_cut_new)
    RecyclerView rvCutNew;//最新上架view
    @BindView(R.id.rv_cut_recommend)
    RecyclerView rvCutRecommend;//为您推荐view
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private List<ActiveSectionGoodsBean> cutNewListData = new ArrayList<>();
    private CutNewAdapter cutNewAdapter;
    private List<ActiveSectionGoodsBean> cutRecommendList = new ArrayList<>();
    private CutTuiJianAdapter cutRecommendAdapter;

    private int page = 1;
    private int limit = 10;
    private boolean hasMore = true;//是否有更多
    private String activityId = "";//活动ID，获取分享资料用
    private   String categoryId ;//为你推荐

    @Override
    protected CutPresenter createPresenter() {
        return new CutPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cut_main;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("喜立得");
        setRightDrawable(R.drawable.ic_activity_share);
        setOnRightIconClickListener(v -> {
            getShareContent();
        });
    }

    @Override
    protected void initData() {
        mPresenter.getActiveSectionData(ActivityType.ACTIVITY_CUT);
        initEvent();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvCutNew.setLayoutManager(linearLayoutManager);
        cutNewAdapter = new CutNewAdapter(mContext, R.layout.cut_new_item, cutNewListData);
        rvCutNew.setAdapter(cutNewAdapter);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> mPresenter.getActiveSectionData(ActivityType.ACTIVITY_CUT));

        cutNewAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //点击进入喜立得商品详情
                Intent intent = new Intent(mContext, CutGoodsDetailActivity.class);
                ActiveSectionGoodsBean resultBean = cutNewListData.get(position);
                intent.putExtra(CutGoodsDetailActivity.ACTIVITY_GOODS_ID, resultBean.getActivityGoodsId());
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        rvCutRecommend.setLayoutManager(new LinearLayoutManager(mContext));

        cutRecommendAdapter = new CutTuiJianAdapter(mContext,cutRecommendList);
        rvCutRecommend.setAdapter(cutRecommendAdapter);

        cutRecommendAdapter.setOnItemClickListener(position -> {
            //点击进入喜立得商品详情
            Intent intent = new Intent(mContext, CutGoodsDetailActivity.class);
            intent.putExtra(CutGoodsDetailActivity.ACTIVITY_GOODS_ID, cutRecommendList.get(position).getActivityGoodsId());
            ActivityUtils.startActivity(intent);
        });

    }

    private void initEvent() {
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if(hasMore){
                page++;
                mPresenter.getActiveSectionTwoGoods(categoryId, ActivityType.ACTIVITY_CUT,MyApplication.userId,page,limit);
            }else {
                mRefreshLayout.finishLoadMore();
            }
        });
    }

    /**
     * 获取分享内容
     */
    private void getShareContent(){
        if(!TextUtils.isEmpty(activityId)){
            mPresenter.getShareContent(MyApplication.userId, activityId, ShareType.ACTIVITY_CUT);
        }
    }

    //获取板块数据成功
    @Override
    public void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        if(model.getData() != null){
            bindBanner(model.getData().getBannerList());
            bindTitle(model.getData().getSectionList());
        }
    }

    /**
     * 绑定分类版块
     */
    private void bindTitle(List<ActiveSectionBean.SectionListBean> sectionList) {
        if(sectionList != null && sectionList.size() != 0){
            if(sectionList.size() == 1){
                tvNew.setText(sectionList.get(0).getCategoryName());
                mPresenter.getActiveSectionGoods(sectionList.get(0).getId(), ActivityType.ACTIVITY_CUT, MyApplication.userId,page, limit);
                tvRecommend.setVisibility(View.GONE);
                rvCutRecommend.setVisibility(View.GONE);
            }else if(sectionList.size() >= 2){
                tvNew.setText(sectionList.get(0).getCategoryName());
                tvRecommend.setText(sectionList.get(1).getCategoryName());

                categoryId=sectionList.get(1).getId();//为您推荐 板块id
                mPresenter.getActiveSectionGoods(sectionList.get(0).getId(), ActivityType.ACTIVITY_CUT,MyApplication.userId, page, limit);
                mPresenter.getActiveSectionTwoGoods(sectionList.get(1).getId(), ActivityType.ACTIVITY_CUT, MyApplication.userId,page, limit);
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
            integerList.add(R.drawable.ic_cut_head);
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
            ivCutInvite.setVisibility(View.GONE);
        }else {
            ivCutInvite.setVisibility(View.VISIBLE);
            GlideUtil.show(mContext, center.getImageUrl(), ivCutInvite);
            BannerBean finalCenter = center;
            ivCutInvite.setOnClickListener(v -> {
                String skipType = finalCenter.getSkipType();
                String targetParams = finalCenter.getTargetParams();
                String webUrl = finalCenter.getTargetUrl1();
                XiKouUtils.parseBannner(mContext,skipType,targetParams,webUrl);
            });
        }
    }

    @Override
    public void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model) {
        if(model != null && model.getData() != null && model.getData().getResult() != null){
            if(page == 1){
                cutNewListData.clear();
            }
            cutNewListData.addAll(model.getData().getResult());
            cutNewAdapter.notifyDataSetChanged();
            if(cutNewListData.size() != 0){
                activityId = cutNewListData.get(0).getActivityId();
            }
        }
    }

    @Override
    public void onGetActiveSectionTwoGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model) {
        mRefreshLayout.finishLoadMore();
        if(model != null && model.getData() != null && model.getData().getResult() != null){
            if(page == 1){
                cutRecommendList.clear();
            }
            cutRecommendList.addAll(model.getData().getResult());
            cutRecommendAdapter.notifyDataSetChanged();
            if (model.getData().getResult().size() < limit) {
                hasMore = false;
                mRefreshLayout.setEnableLoadMore(false);
            }else {
                mRefreshLayout.setEnableLoadMore(true);
            }
        }
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        ShareBean shareBean = model.getData();
        if(shareBean != null){
            DialogShare dialogShare = new DialogShare(mContext, shareBean);
            MyApplication.shareType = ShareType.ACTIVITY_CUT;
            dialogShare.show();
        }
    }

    @Override
    public void onShareCallback(BaseModel model) {

    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareCallback(ShareSuccessEvent event){
        if(event.shareType == ShareType.ACTIVITY_CUT){
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.ACTIVITY_CUT);
        }
    }
}
