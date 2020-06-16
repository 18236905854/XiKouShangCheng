package com.xk.mall.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.eventbean.EmptyOrderListBean;
import com.xk.mall.model.impl.SellConsignmentViewImpl;
import com.xk.mall.presenter.SellConsignmentPresenter;
import com.xk.mall.utils.CalculateUtils;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.activity.SellOrderDetailActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName SellConsignmentOrderFragment
 * Description 我是卖家  寄卖订单fragment
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class SellConsignmentOrderFragment extends BaseFragment<SellConsignmentPresenter> implements SellConsignmentViewImpl {

    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @BindView(R.id.refresh_order)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.state_view_order)
    MultiStateView multiStateView;
    private int orderType = 0;//订单类型  全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
    private int page = 1;//页数
    private int limit = 10;//每页的条数
    private OrderListAdapter orderListAdapter;
    private List<OrderBean> orderBeanList;
    private int createTimeFlag;
    private String orderAmountL;
    private String orderAmountR;
    private String searchName;
    /**
     * 构造方法
     */
    public static SellConsignmentOrderFragment getInstance(int titleType) {
        SellConsignmentOrderFragment fragment = new SellConsignmentOrderFragment();
        fragment.orderType = titleType;
        return fragment;
    }

    @Override
    protected SellConsignmentPresenter createPresenter() {
        return new SellConsignmentPresenter(this);
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadLazyData() {
        rvOrder.setLayoutManager(new LinearLayoutManager(mContext));
        mPresenter.getSellConsignmentOrderList(SPUtils.getInstance().getString(Constant.USER_MOBILE),
                searchName, orderType, orderAmountL, orderAmountR, page, limit);
        orderBeanList = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(mContext, R.layout.sell_consignment_order_list_item, orderBeanList);
        rvOrder.setAdapter(orderListAdapter);
        orderListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                OrderBean orderBean = orderBeanList.get(position);
                Intent intent = new Intent(mContext, SellOrderDetailActivity.class);
                intent.putExtra(SellOrderDetailActivity.ORDER_NO, orderBean.getOrderNo());
                intent.putExtra(SellOrderDetailActivity.ORDER_TYPE, orderType);
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getSellConsignmentOrderList(SPUtils.getInstance().getString(Constant.USER_MOBILE),
                    searchName, orderType, orderAmountL, orderAmountR, page, limit);
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getSellConsignmentOrderList(SPUtils.getInstance().getString(Constant.USER_MOBILE),
                    searchName, orderType, orderAmountL, orderAmountR, page, limit);
        });
    }


    @Override
    public void onGetSellConsignmentOrderListSuccess(BaseModel<OrderPageBean> model) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if(model.getData() != null && model.getData().result != null){
            if(page == 1){
                orderBeanList.clear();
            }
            orderBeanList.addAll(model.getData().result);
            orderListAdapter.notifyDataSetChanged();
            if(model.getData().result.size() < limit){
                refreshLayout.setEnableLoadMore(false);
            }else {
                refreshLayout.setEnableLoadMore(true);
            }
            if(page == 1 && model.getData().result.size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                EventBus.getDefault().post(new EmptyOrderListBean());
            }
        }else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }


    class OrderListAdapter extends CommonAdapter<OrderBean>{

        public OrderListAdapter(Context context, int layoutId, List<OrderBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, OrderBean orderBean, int position) {
            holder.setText(R.id.tv_order_list_shopName, orderBean.getMerchantName());
            holder.setText(R.id.tv_order_list_name, orderBean.getGoodsName());
            holder.setText(R.id.tv_order_list_money, getResources().getString(R.string.money) + PriceUtil.dividePrice(orderBean.getCommoditySalePrice()));
            holder.setText(R.id.tv_order_list_num, "x" + orderBean.getCommodityQuantity());
            holder.setText(R.id.tv_order_list_sku, orderBean.getCommodityModel() + " " + orderBean.getCommoditySpec());
            ImageView ivLogo = holder.getView(R.id.iv_order_list_logo);
            GlideUtil.showRadius(mContext, orderBean.getGoodsImageUrl(), 2, ivLogo);
            TextView tvType = holder.getView(R.id.tv_order_list_type);
            //订单类型  0全部、1待付款、2待发货、3待收货、4已完成、5已取消
            if(orderBean.getState() == 1){//待付款
                tvType.setText("待付款");
            }else if(orderBean.getState() == 2){//待发货
                tvType.setText("待发货");
            }else if(orderBean.getState() == 3){
                tvType.setText("待收货");
            }else if(orderBean.getState() == 4){
                tvType.setText("已取消");
            }else if(orderBean.getState() == 5){
                tvType.setText("已完成");
            }else if(orderBean.getState() == 6){
                tvType.setText("已关闭");
            }else if(orderBean.getState() == 7){
                tvType.setText("待确认");
            }else if(orderBean.getState() == 11){
                tvType.setText("已寄卖");
            }else if(orderBean.getState() == 15){
                tvType.setText("交易完结");
            }else {
                tvType.setText("状态错误");
            }

        }
    }


}
