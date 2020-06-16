package com.xk.mall.view.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.IndustryBean;
import com.xk.mall.model.entity.NearTitleBean;

import java.util.List;

/**
 * ClassName FightGroupViewPagerAdapter
 * Description 定制拼团的adapter
 * Author 卿凯
 * Date 2019/6/10/010
 * Version V1.0
 */
public class FightGroupViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<ActiveSectionBean.SectionListBean> titles;

    public FightGroupViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<ActiveSectionBean.SectionListBean> titles) {
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
        return titles.get(position).getCategoryName();
    }
}
