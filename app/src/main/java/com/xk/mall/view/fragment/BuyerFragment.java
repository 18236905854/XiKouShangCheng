package com.xk.mall.view.fragment;

import android.content.Intent;
import android.support.annotation.Keep;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.UserItem;
import com.xk.mall.view.activity.CutOrderListActivity;
import com.xk.mall.view.activity.GlobalBuyerOrderListActivity;
import com.xk.mall.view.activity.ManyBuyOrderListActivity;
import com.xk.mall.view.activity.NewPersonOrderListActivity;
import com.xk.mall.view.activity.PayBackListActivity;
import com.xk.mall.view.activity.WuGOrderListActivity;
import com.xk.mall.view.activity.ZeroOrderListActivity;
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
public class BuyerFragment extends BaseFragment {

    @BindView(R.id.rv_buyer)
    RecyclerView recyclerView;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadLazyData() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        List<UserItem> buyerItems = new ArrayList<>();
//        buyerItems.add(new UserItem(R.drawable.me_buyer_zero, "0元抢"));
//        buyerItems.add(new UserItem(R.drawable.me_buyer_many, "多买多折"));
//        buyerItems.add(new UserItem(R.drawable.me_buyer_cut, "喜立得"));
//        buyerItems.add(new UserItem(R.drawable.me_buyer_global_buy, "全球买手"));
//        buyerItems.add(new UserItem(R.drawable.me_buyer_wug, "吾G购"));
////        buyerItems.add(new UserItem(R.drawable.me_buyer_fight_group, "定制拼团"));
//        buyerItems.add(new UserItem(R.drawable.me_buyer_new_person, "新人专区"));
        buyerItems.add(new UserItem(R.drawable.me_buyer_payback, "待付款"));
        buyerItems.add(new UserItem(R.drawable.me_buyer_confirmed, "待确认"));
        buyerItems.add(new UserItem(R.drawable.me_buyer_delivered, "待发货"));
        buyerItems.add(new UserItem(R.drawable.me_buyer_received, "待收货"));
        if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1) {
            buyerItems.add(new UserItem(R.drawable.me_buyer_pay_back, "退款/售后"));
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        }
        MeAdapter meAdapter = new MeAdapter(mContext, R.layout.user_buyer_item, buyerItems);
        recyclerView.setAdapter(meAdapter);
        meAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
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
        if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1 &&
                            position == recyclerView.getChildCount() - 1) {
            ActivityUtils.startActivity(PayBackListActivity.class);
        }else {
            Intent intent = new Intent(mContext, ZeroOrderListActivity.class);
            intent.putExtra("position", position + 1);
            ActivityUtils.startActivity(intent);
        }
//        if(position == 0){
//            //点击进入0元拍订单列表页面
//        }else if(position == 1){
//            //点击进入多买多折订单列表页面
//            ActivityUtils.startActivity(ManyBuyOrderListActivity.class);
//        }else if(position == 2){
//            //点击进入喜立得订单列表页面
//            ActivityUtils.startActivity(CutOrderListActivity.class);
//        }else if(position == 3){
//            //点击进入全球买手页面
//            ActivityUtils.startActivity(GlobalBuyerOrderListActivity.class);
//        }else if(position == 4){
//            //点击进入吾G购订单列表页面
//            ActivityUtils.startActivity(WuGOrderListActivity.class);
////        }else if(position == 5){
////            //点击进入定制拼团订单列表页面
////            ActivityUtils.startActivity(GroupOrderListActivity.class);
//        }else if(position == 5){
//            //点击进入新人专区订单列表页面
//            ActivityUtils.startActivity(NewPersonOrderListActivity.class);
//        }else if(position == 6){//退款售后订单
//            ActivityUtils.startActivity(PayBackListActivity.class);
//        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buyer;
    }

}
