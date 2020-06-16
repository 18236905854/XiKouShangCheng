package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.blankj.utilcode.util.ActivityUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.FightGroupOrderTitleBean;
import com.xk.mall.utils.OrderType;
import com.xk.mall.view.adapter.FightGroupOrderViewPagerAdapter;
import com.xk.mall.view.fragment.GroupOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName GroupOrderListActivity
 * Description 定制拼团订单列表页面
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class GroupOrderListActivity extends BaseActivity  {
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
        setTitle(getResources().getString(R.string.fight_group_order_title));
        setOnRightIconClickListener(v -> {
            Intent intent = new Intent(mContext, OrderFilterActivity.class);
            intent.putExtra(OrderFilterActivity.ORDER_TYPE, OrderType.GROUP_TYPE);
            ActivityUtils.startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        List<FightGroupOrderTitleBean> titles = new ArrayList<>();
        //订单类型 不传数据-0 全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功
        titles.add(new FightGroupOrderTitleBean("全部", 0));
        titles.add(new FightGroupOrderTitleBean("待付款", 1));
        titles.add(new FightGroupOrderTitleBean("进行中", 8));
        titles.add(new FightGroupOrderTitleBean("待发货", 2));
        titles.add(new FightGroupOrderTitleBean("待收货", 3));

        List<Fragment> fragments = new ArrayList<>();
        for(FightGroupOrderTitleBean titleBean : titles){
            fragments.add(GroupOrderFragment.getInstance(titleBean.orderType));
        }
        FightGroupOrderViewPagerAdapter viewPagerAdapter = new FightGroupOrderViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vpCutOrder.setOffscreenPageLimit(titles.size());
        vpCutOrder.setAdapter(viewPagerAdapter);
        tabCutOrder.setViewPager(vpCutOrder);
        tabCutOrder.setCurrentTab(0);
    }


    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        Log.e(TAG, "onErrorCode: "+model.getMsg() );
    }
}
