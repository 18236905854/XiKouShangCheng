package com.xk.mall.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.view.adapter.ViewPagerAdapter;
import com.xk.mall.view.fragment.MaterialShareCheckFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName: ShareCheckActivity
 * @Description: 分享审核页面
 * @Author: 卿凯
 * @Date: 2019/12/7/007 16:31
 * @Version: 1.0
 */
public class ShareCheckActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private String[] titles = {"待审核", "已通过", "未通过"};
    private String[] states = {"NONE", "PASS", "FAILED"};

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_check;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("上传记录");
    }

    @Override
    protected void initData() {
        List<Fragment> fragmentList=new ArrayList<>();
        List<String> listTitle = Arrays.asList(titles);
        for (String title : states) {
            fragmentList.add(MaterialShareCheckFragment.getInstance(title));
        }

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,listTitle);
        viewPager.setOffscreenPageLimit(listTitle.size());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setViewPager(viewPager);
        tabLayout.setCurrentTab(0);
    }
}
