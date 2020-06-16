package com.xk.mall.view.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xk.mall.model.entity.GlobalBuyerOrderTitleBean;
import com.xk.mall.model.entity.ZeroOrderTitleBean;

import java.util.List;

/**
 * ClassName GlobalBuyerOrderViewPagerAdapter
 * Description 全球买手订单页面的viewpager adapter
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class GlobalBuyerOrderViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<GlobalBuyerOrderTitleBean> titles;

    public GlobalBuyerOrderViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<GlobalBuyerOrderTitleBean> titles) {
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
