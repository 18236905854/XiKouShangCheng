package com.xk.mall.view.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * ClassName MeViewPagerAdapter
 * Description 我是买家或卖家的adapter
 * Author 卿凯
 * Date 2019/6/10/010
 * Version V1.0
 */
public class MeViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public MeViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
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
        return titles.get(position);
    }
}
