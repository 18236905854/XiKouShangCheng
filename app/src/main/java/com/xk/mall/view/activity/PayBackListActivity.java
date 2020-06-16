package com.xk.mall.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.utils.OrderType;
import com.xk.mall.view.adapter.ViewPagerAdapter;
import com.xk.mall.view.fragment.PayBackFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName: PayBackListActivity
 * @Description: 退款售后订单
 * @Author: 卿凯
 * @Date: 2019/12/8/008 14:59
 * @Version: 1.0
 */
public class PayBackListActivity extends BaseActivity {
    @BindView(R.id.tab_pay_back)
    SlidingTabLayout tabPayBack;
    @BindView(R.id.vp_pay_back)
    ViewPager vpPayBack;
    String[] titles = new String[]{"全球买手", "吾G购"};

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_back;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("退款售后订单");
    }

    @Override
    protected void initData() {
        List<Fragment> fragmentList=new ArrayList<>();
        List<String> listTitle = Arrays.asList(titles);
//        fragmentList.add(PayBackFragment.getInstance(OrderType.MANY_TYPE));
//        fragmentList.add(PayBackFragment.getInstance(OrderType.CUT_TYPE));
        fragmentList.add(PayBackFragment.getInstance(OrderType.GLOBAL_TYPE));
        fragmentList.add(PayBackFragment.getInstance(OrderType.WUG_TYPE));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,listTitle);
        vpPayBack.setOffscreenPageLimit(listTitle.size());
        vpPayBack.setAdapter(viewPagerAdapter);
        tabPayBack.setViewPager(vpPayBack);
        tabPayBack.setCurrentTab(0);
    }

}
