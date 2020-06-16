package com.xk.mall.view.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.orhanobut.logger.Logger;
import com.xk.mall.model.entity.NearTitleBean;

import java.util.List;

/**
 *  热排行 viewPager 适配
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
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
//
//    @Override
//    public long getItemId(int position) {
//        return fragments.get(position).hashCode();
//    }
//
//    @Override
//    public int getItemPosition(@NonNull Object object) {
//        if (fragments.contains(object)) {
//            // 如果当前 item 未被 remove，则返回 item 的真实 position
//            return fragments.indexOf(object);
//        } else {
//            // 否则返回状态值 POSITION_NONE
//            return POSITION_NONE;
//        }
//    }
}
