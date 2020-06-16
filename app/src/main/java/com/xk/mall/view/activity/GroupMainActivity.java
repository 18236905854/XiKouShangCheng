package com.xk.mall.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.GroupMainViewImpl;
import com.xk.mall.presenter.GroupMainPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.EventBusMessage;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.ShareType;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.adapter.FightGroupViewPagerAdapter;
import com.xk.mall.view.fragment.GroupMainFragment;
import com.xk.mall.view.widget.DialogShare;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.view.BannerViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ClassName GroupMainActivity
 * Description 定制拼团主页面
 * Author 卿凯
 * Date 2019/6/20/020
 * Version V1.0
 */
public class GroupMainActivity extends BaseActivity<GroupMainPresenter> implements GroupMainViewImpl {

    @BindView(R.id.tab_fight_group)
    SlidingTabLayout tabFightGroup;
    @BindView(R.id.view_page_fight_group)
    ViewPager vpFightGroup;
    @BindView(R.id.banner_group)
    Banner bannerGroup;

    private String activityId = "";//活动Id

    @Override
    protected GroupMainPresenter createPresenter() {
        return new GroupMainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fight_group;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("定制拼团");
        setRightDrawable(R.drawable.ic_activity_share);
        setOnRightIconClickListener(v -> {
            getShareContent();
        });
    }

    @Override
    protected void initData() {
        mPresenter.getActiveSectionData(ActivityType.ACTIVITY_FIGHT_GROUP);
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventGetActivityId(EventBusMessage eventBusMessage) {
        if (!TextUtils.isEmpty(eventBusMessage.getMessage())) {
            activityId = eventBusMessage.getMessage();
        }
    }

    /**
     * 获取分享内容
     */
    private void getShareContent() {
        if (!TextUtils.isEmpty(activityId)) {
            mPresenter.getShareContent(MyApplication.userId, activityId, ShareType.ACTIVITY_FIGHT_GROUP);
        }
    }

    @Override
    public void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model) {
        if (model != null) {
            bindBanner(model.getData().getBannerList());
            bindViewPager(model.getData().getSectionList());
        }
    }

    /**
     * 绑定banner图片
     */
    private void bindBanner(List<BannerBean> bannerList) {
        bannerGroup.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<String> images = new ArrayList<>();
        if (bannerList != null && bannerList.size() > 0) {
            for (int i = 0; i < bannerList.size(); i++) {
                if(bannerList.get(i).getPosition() == 1){
                    images.add(bannerList.get(i).getImageUrl());
                }
            }

            bannerGroup.setImages(images);
            //设置nearBanner动画效果
            bannerGroup.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            bannerGroup.isAutoPlay(true);
            //设置指示器
            bannerGroup.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
            //设置轮播时间
            bannerGroup.setDelayTime(3000);
            //banner设置方法全部调用完毕时最后调用
            bannerGroup.start();

            bannerGroup.setOnBannerListener(position -> {
                String skipType = bannerList.get(position).getSkipType();
                String targetParams = bannerList.get(position).getTargetParams();
                String webUrl = bannerList.get(position).getTargetUrl1();
                XiKouUtils.parseBannner(mContext,skipType,targetParams,webUrl);
            });
        }else {
            List<Integer> integerList = new ArrayList<>();
            integerList.add(R.drawable.ic_cut_head);
            bannerGroup.setImages(integerList);
            //设置nearBanner动画效果
            bannerGroup.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            bannerGroup.isAutoPlay(true);
            //设置指示器
            bannerGroup.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
            //设置轮播时间
            bannerGroup.setDelayTime(3000);
            //banner设置方法全部调用完毕时最后调用
            bannerGroup.start();
        }
    }

    /**
     * 绑定viewPager
     */
    private void bindViewPager(List<ActiveSectionBean.SectionListBean> sectionList) {
        if(sectionList != null && sectionList.size() != 0){
            List<Fragment> fragments = new ArrayList<>();
            for (ActiveSectionBean.SectionListBean titleBean : sectionList) {
                fragments.add(GroupMainFragment.getInstance(titleBean.getId()));
            }
            FightGroupViewPagerAdapter viewPagerAdapter = new FightGroupViewPagerAdapter(getSupportFragmentManager(), fragments, sectionList);
            vpFightGroup.setAdapter(viewPagerAdapter);
            vpFightGroup.setOffscreenPageLimit(sectionList.size());
            tabFightGroup.setViewPager(vpFightGroup);
            tabFightGroup.setCurrentTab(0);
        }
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        ShareBean shareBean = model.getData();
        if (shareBean != null) {
            DialogShare dialogShare = new DialogShare(mContext, shareBean);
            MyApplication.shareType = ShareType.ACTIVITY_FIGHT_GROUP;
            dialogShare.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareCallback(ShareSuccessEvent event) {
        if (event.shareType == ShareType.ACTIVITY_FIGHT_GROUP) {
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.ACTIVITY_FIGHT_GROUP);
        }
    }

    @Override
    public void onShareCallback(BaseModel model) {

    }
}
