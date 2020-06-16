package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OrderTitleBean;
import com.xk.mall.utils.OrderType;
import com.xk.mall.view.adapter.OrderViewPagerAdapter;
import com.xk.mall.view.fragment.CutOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName CutOrderListActivity
 * Description 喜立得订单列表页面
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class CutOrderListActivity extends BaseActivity {
    @BindView(R.id.tab_cut_order)
    SlidingTabLayout tabCutOrder;
    @BindView(R.id.vp_cut_order)
    ViewPager vpCutOrder;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cut_order;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.cut_order_title));
        setOnRightIconClickListener(v -> {
            Intent intent = new Intent(mContext, OrderFilterActivity.class);
            intent.putExtra(OrderFilterActivity.ORDER_TYPE, OrderType.CUT_TYPE);
            ActivityUtils.startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        List<OrderTitleBean> titles = new ArrayList<>();
        titles.add(new OrderTitleBean("全部", 0));
        titles.add(new OrderTitleBean("待付款", 1));
        titles.add(new OrderTitleBean("待发货", 2));
        titles.add(new OrderTitleBean("待收货", 3));
        List<Fragment> fragments = new ArrayList<>();
        for(OrderTitleBean titleBean : titles){
            fragments.add(CutOrderFragment.getInstance(titleBean.orderType));
        }
        OrderViewPagerAdapter viewPagerAdapter = new OrderViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vpCutOrder.setAdapter(viewPagerAdapter);
        vpCutOrder.setOffscreenPageLimit(titles.size());
        tabCutOrder.setViewPager(vpCutOrder);
        tabCutOrder.setCurrentTab(0);
    }
}
