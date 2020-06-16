package com.xk.mall.view.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xk.mall.model.entity.ActivityRoundBean;

import java.util.List;

/**
 * ClassName ZeroViewPagerAdapter
 * Description 0元竞拍页面的adapter
 * Author 卿凯
 * Date 2019/7/5/005
 * Version V1.0
 */
public class ZeroViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<ActivityRoundBean> titles;

    public ZeroViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<ActivityRoundBean> titles) {
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
        return titles.get(position).roundTitle;
    }

}
