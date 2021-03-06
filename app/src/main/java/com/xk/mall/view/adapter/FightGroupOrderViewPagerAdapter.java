package com.xk.mall.view.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xk.mall.model.entity.FightGroupOrderTitleBean;
import com.xk.mall.model.entity.OrderTitleBean;

import java.util.List;

/**
 * ClassName FightGroupOrderViewPagerAdapter
 * Description 定制拼团订单页面的viewpager adapter
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class FightGroupOrderViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<FightGroupOrderTitleBean> titles;

    public FightGroupOrderViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<FightGroupOrderTitleBean> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).title;
    }
}
