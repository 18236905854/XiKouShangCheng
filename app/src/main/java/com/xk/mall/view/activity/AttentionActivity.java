package com.xk.mall.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.view.adapter.MeViewPagerAdapter;
import com.xk.mall.view.fragment.AttentionFragment;
import com.xk.mall.view.fragment.AttentionShopFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName AttentionActivity
 * Description 我的关注页面
 * Author 卿凯
 * Date 2019/6/11/011
 * Version V1.0
 */
public class AttentionActivity extends BaseActivity{

    @BindView(R.id.tab_attention)
    SlidingTabLayout tabAttention;
    @BindView(R.id.vp_attention)
    ViewPager vpAttention;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_attention;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("我的关注");
    }

    @Override
    protected void initData() {
        List<String> titles = new ArrayList<>();
        titles.add("设计师");
        titles.add("O2O店铺");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(AttentionFragment.getInstance());
        fragments.add(AttentionShopFragment.getInstance());
        MeViewPagerAdapter viewPagerAdapter = new MeViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vpAttention.setAdapter(viewPagerAdapter);
        vpAttention.setOffscreenPageLimit(titles.size());
        tabAttention.setViewPager(vpAttention);
        tabAttention.setCurrentTab(0);
    }
}
