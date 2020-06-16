package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.GlobalBuyerOrderTitleBean;
import com.xk.mall.utils.OrderType;
import com.xk.mall.view.adapter.GlobalBuyerOrderViewPagerAdapter;
import com.xk.mall.view.fragment.GlobalBuyerOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName ZeroOrderListActivity
 * Description 全球买手订单列表页面
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class GlobalBuyerOrderListActivity extends BaseActivity {
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
        setTitle(getResources().getString(R.string.global_buyer_order_title));
        setOnRightIconClickListener(v -> {
            Intent intent = new Intent(mContext, OrderFilterActivity.class);
            intent.putExtra(OrderFilterActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
            ActivityUtils.startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(this);
        //订单状态；不传数据-0全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
        List<GlobalBuyerOrderTitleBean> titles = new ArrayList<>();
        titles.add(new GlobalBuyerOrderTitleBean("全部", 0));
        titles.add(new GlobalBuyerOrderTitleBean("待付款", 1));
        titles.add(new GlobalBuyerOrderTitleBean("待确认", 7));
        titles.add(new GlobalBuyerOrderTitleBean("待发货", 2));
        titles.add(new GlobalBuyerOrderTitleBean("待收货", 3));
        List<Fragment> fragments = new ArrayList<>();
        for(GlobalBuyerOrderTitleBean titleBean : titles){
            fragments.add(GlobalBuyerOrderFragment.getInstance(titleBean.orderType));
        }
        GlobalBuyerOrderViewPagerAdapter viewPagerAdapter = new GlobalBuyerOrderViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vpCutOrder.setOffscreenPageLimit(titles.size());
        vpCutOrder.setAdapter(viewPagerAdapter);
        tabCutOrder.setViewPager(vpCutOrder);
        tabCutOrder.setCurrentTab(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }
}
