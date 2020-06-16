package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OrderTitleBean;
import com.xk.mall.model.entity.ZeroOrderTitleBean;
import com.xk.mall.model.eventbean.EmptyOrderListBean;
import com.xk.mall.utils.OrderType;
import com.xk.mall.view.adapter.OrderViewPagerAdapter;
import com.xk.mall.view.adapter.ZeroOrderViewPagerAdapter;
import com.xk.mall.view.fragment.CutOrderFragment;
import com.xk.mall.view.fragment.ZeroOrderFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName ZeroOrderListActivity
 * Description 0元拍订单列表页面
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class ZeroOrderListActivity extends BaseActivity {
    @BindView(R.id.tab_cut_order)
    SlidingTabLayout tabCutOrder;
    @BindView(R.id.vp_cut_order)
    ViewPager vpCutOrder;
    int clickPos = -1;

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
        setTitle(getResources().getString(R.string.my_order));
        setOnRightIconClickListener(v -> {
            Intent intent = new Intent(mContext, OrderFilterActivity.class);
//            intent.putExtra(OrderFilterActivity.ORDER_TYPE, OrderType.ZERO_TYPE);
            ActivityUtils.startActivity(intent);
        });
    }

    @Override
    protected void initData() { // 0全部、1待付款、2待发货、3待收货
        clickPos = getIntent().getIntExtra("position", -1);
        List<ZeroOrderTitleBean> titles = new ArrayList<>();
        //全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
        titles.add(new ZeroOrderTitleBean("全部", 0));
        titles.add(new ZeroOrderTitleBean("待付款", 1));
        titles.add(new ZeroOrderTitleBean("待确认", 7));
        titles.add(new ZeroOrderTitleBean("待发货", 2));
        titles.add(new ZeroOrderTitleBean("待收货", 3));
        List<Fragment> fragments = new ArrayList<>();
        for(ZeroOrderTitleBean titleBean : titles){
            fragments.add(ZeroOrderFragment.getInstance(titleBean.orderType));
        }
        ZeroOrderViewPagerAdapter viewPagerAdapter = new ZeroOrderViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vpCutOrder.setOffscreenPageLimit(titles.size());
        vpCutOrder.setAdapter(viewPagerAdapter);
        tabCutOrder.setViewPager(vpCutOrder);
        tabCutOrder.setCurrentTab(clickPos == -1 ? 0 : clickPos);
    }

}
