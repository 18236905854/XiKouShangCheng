package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OrderTitleBean;
import com.xk.mall.model.entity.WuGOrderMoneyBean;
import com.xk.mall.model.eventbean.EmptyOrderListBean;
import com.xk.mall.model.impl.WuGOrderListViewImpl;
import com.xk.mall.presenter.WuGOrderListPresenter;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.adapter.OrderViewPagerAdapter;
import com.xk.mall.view.fragment.WuGOrderFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName WuGOrderListActivity
 * Description 吾G购订单列表页面
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class WuGOrderListActivity extends BaseActivity<WuGOrderListPresenter> implements WuGOrderListViewImpl {
    @BindView(R.id.tab_cut_order)
    SlidingTabLayout tabCutOrder;
    @BindView(R.id.vp_cut_order)
    ViewPager vpCutOrder;
    @BindView(R.id.tv_pay_money)
    TextView tvMoney;
    @BindView(R.id.rl_wug_top)
    RelativeLayout rlWuGTop;//吾G订单列表页面头部

    @Override
    protected WuGOrderListPresenter createPresenter() {
        return new WuGOrderListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cut_order;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.wug_order_title));
        setOnRightIconClickListener(v -> {
            Intent intent = new Intent(mContext, OrderFilterActivity.class);
            intent.putExtra(OrderFilterActivity.ORDER_TYPE, OrderType.WUG_TYPE);
            ActivityUtils.startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(this);
        List<OrderTitleBean> titles = new ArrayList<>();
        titles.add(new OrderTitleBean("全部", 0));
        titles.add(new OrderTitleBean("待付款", 1));
        titles.add(new OrderTitleBean("待发货", 2));
        titles.add(new OrderTitleBean("待收货", 3));
        List<Fragment> fragments = new ArrayList<>();
        for(OrderTitleBean titleBean : titles){
            fragments.add(WuGOrderFragment.getInstance(titleBean.orderType));
        }
        OrderViewPagerAdapter viewPagerAdapter = new OrderViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
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

    @Override
    public void onGetMoneySuccess(BaseModel<WuGOrderMoneyBean> moneyBeanBaseModel) {
        if(moneyBeanBaseModel != null && moneyBeanBaseModel.getData() != null){
            rlWuGTop.setVisibility(View.VISIBLE);
            if(moneyBeanBaseModel.getData().getLimitAmount() < 0){
                moneyBeanBaseModel.getData().setLimitAmount(0);
            }
            if(moneyBeanBaseModel.getData().getBalance() < 0){
                moneyBeanBaseModel.getData().setBalance(0);
            }
            tvMoney.setText("吾G购每月限购" + formatLimit(moneyBeanBaseModel.getData().getLimitAmount()) +
                    "元，当前剩余额度" + PriceUtil.dividePrice(moneyBeanBaseModel.getData().getBalance()) + "元");
        }
    }

    private String formatLimit(int limitAmount){
        String result = "";
        if(limitAmount < 1000000){
            result = PriceUtil.dividePrice(limitAmount);
        }else {
            //返回多少万元
            result = PriceUtil.dividePrice(limitAmount / 10000) + "万";
        }
        return result;
    }
}
