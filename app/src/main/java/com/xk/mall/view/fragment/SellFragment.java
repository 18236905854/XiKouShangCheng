package com.xk.mall.view.fragment;

import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.UserItem;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.activity.LoginActivity;
import com.xk.mall.view.activity.PayBackSellActivity;
import com.xk.mall.view.activity.SellConsignmentActivity;
import com.xk.mall.view.activity.SellOrderListActivity;
import com.xk.mall.view.activity.WantSellOrderListActivity;
import com.xk.mall.view.adapter.MeAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName BuyerFragment
 * Description 我是买家
 * Author 卿凯
 * Date 2019/6/10/010
 * Version V1.0
 */
public class SellFragment extends BaseFragment {

    @BindView(R.id.rv_buyer)
    RecyclerView recyclerView;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadLazyData() {
        List<UserItem> buyerItems = new ArrayList<>();
        buyerItems.add(new UserItem(R.drawable.me_sell_want, "我要寄卖"));
        buyerItems.add(new UserItem(R.drawable.me_sell_sell, "我的寄卖"));
        buyerItems.add(new UserItem(R.drawable.me_sell_order, "寄卖订单"));
        if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1) {
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
            buyerItems.add(new UserItem(R.drawable.me_buyer_pay_back, "退货/售后"));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        }
        MeAdapter meAdapter = new MeAdapter(mContext, R.layout.sell_fragment_item, buyerItems);
        recyclerView.setAdapter(meAdapter);
        meAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                //验证是否登录，未登录跳转登录页面
//                Boolean isLogin = SPUtils.getInstance().getBoolean(Constant.IS_LOGIN);
//                if (!isLogin) {
//                    ActivityUtils.startActivity(LoginActivity.class);
//                    return;
//                }
                checkLogin(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    /**
     * 新增方法处理item点击事件注解登录的问题
     */
    @Keep
    @LoginFilter
    private void checkLogin(int position){
        if(position == 0){
            //我要寄卖订单
            ActivityUtils.startActivity(WantSellOrderListActivity.class);
        }else if(position == 1){
            //我的寄卖订单
            ActivityUtils.startActivity(SellOrderListActivity.class);
        }else if(position == 2){
            //寄卖订单
            ActivityUtils.startActivity(SellConsignmentActivity.class);
        }else if(position == 3){
            ActivityUtils.startActivity(PayBackSellActivity.class);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buyer;
    }


}
