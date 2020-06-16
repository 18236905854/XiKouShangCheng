package com.xk.mall.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.impl.PayBackSellViewImpl;
import com.xk.mall.presenter.PayBackSellPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName: PayBackSellActivity
 * @Description: 我是卖家退款售后页面
 * @Author: 卿凯
 * @Date: 2019/12/10/010 9:42
 * @Version: 1.0
 */
public class PayBackSellActivity extends BaseActivity<PayBackSellPresenter> implements PayBackSellViewImpl {
    @BindView(R.id.rv_want_sell_order_list)
    RecyclerView rvWantSellOrderList;
    @BindView(R.id.refresh_order)
    SmartRefreshLayout refreshOrder;
    @BindView(R.id.state_view_order)
    MultiStateView stateViewOrder;
    private int page = 1;
    private List<OrderBean> orderBeanList;
    private OrderListAdapter orderListAdapter;

    @Override
    protected PayBackSellPresenter createPresenter() {
        return new PayBackSellPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_back_sell;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("退款售后订单");
    }

    @Override
    protected void initData() {
        String phone = SPUtils.getInstance().getString(Constant.USER_MOBILE);
        mPresenter.getSellPayBackList(phone, page, Constant.limit);
        orderBeanList = new ArrayList<>();
        rvWantSellOrderList.setLayoutManager(new LinearLayoutManager(mContext));
        orderListAdapter = new OrderListAdapter(mContext, R.layout.pay_back_order_list_item, orderBeanList);
        rvWantSellOrderList.setAdapter(orderListAdapter);
        refreshOrder.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getSellPayBackList(phone, page, Constant.limit);
        });

        refreshOrder.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getSellPayBackList(phone, page, Constant.limit);
        });
    }

    @Override
    public void onGetPayBackSellListSuccess(BaseModel<OrderPageBean> model) {
        refreshOrder.finishRefresh();
        refreshOrder.finishLoadMore();
        if(model.getData() != null && model.getData().result != null){
            if(page == 1){
                orderBeanList.clear();
            }
            orderBeanList.addAll(model.getData().result);
            orderListAdapter.notifyDataSetChanged();
            if(model.getData().result.size() < Constant.limit){
                refreshOrder.setEnableLoadMore(false);
            }else {
                refreshOrder.setEnableLoadMore(true);
            }
            if(page == 1 && model.getData().result.size() == 0){
                stateViewOrder.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }else {
            stateViewOrder.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    class OrderListAdapter extends CommonAdapter<OrderBean> {

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
            holder.setText(R.id.tv_order_list_pay_amount, "订单总额:¥" + PriceUtil.dividePrice(orderBean.getPayAmount()));
            holder.setText(R.id.tv_pay_back_money, "退款金额:¥" + PriceUtil.dividePrice(orderBean.getPayAmount()));
            ImageView ivLogo = holder.getView(R.id.iv_order_list_logo);
            GlideUtil.showRadius(mContext, orderBean.getGoodsImageUrl(), 2, ivLogo);
            TextView tvType = holder.getView(R.id.tv_order_list_type);
            TextView btnRight = holder.getView(R.id.tv_order_list_right);//最右边按钮
            //退货状态，0=审核中，1=退款中，2=退款退货中，3=已退款，4=退款失败，5=退款已关闭
            if(orderBean.getRefundStatus() == 0) {//审核中
                tvType.setText("审核中");
                tvType.setTextColor(Color.parseColor("#F94119"));
                btnRight.setVisibility(View.GONE);
            }else if(orderBean.getRefundStatus() == 1){
                tvType.setText("退款中");
                tvType.setTextColor(Color.parseColor("#F94119"));
                btnRight.setVisibility(View.GONE);
            }else if(orderBean.getRefundStatus() == 2) {
                tvType.setText("退款退货中");
                tvType.setTextColor(Color.parseColor("#F94119"));
                btnRight.setVisibility(View.GONE);
            }else if(orderBean.getRefundStatus() == 3){
                tvType.setText("退款成功");
                tvType.setTextColor(Color.parseColor("#999999"));
                btnRight.setVisibility(View.GONE);
            }else if(orderBean.getState() == 4){//退款成功
                tvType.setText("退款失败");
                tvType.setTextColor(Color.parseColor("#999999"));
                btnRight.setVisibility(View.GONE);
            }else if(orderBean.getRefundStatus() == 5){
                tvType.setText("退款关闭");
                tvType.setTextColor(Color.parseColor("#999999"));
                btnRight.setVisibility(View.GONE);
            }
        }
    }
}
