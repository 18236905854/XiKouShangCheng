package com.xk.mall.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.interfaces.CancelOrderListener;
import com.xk.mall.interfaces.DeleteOrderListener;
import com.xk.mall.interfaces.RemindOrderListener;
import com.xk.mall.interfaces.SureOrderListener;
import com.xk.mall.model.entity.ManyBuyOrderAdapterBean;
import com.xk.mall.model.entity.ManyBuyOrderBean;
import com.xk.mall.model.entity.OrderStateBean;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.activity.ManyBuyOrderDetailActivity;
import com.xk.mall.view.activity.PayOrderActivity;
import com.xk.mall.view.widget.OrderTipDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: GlobalBuyerDelegate
 * @Description: 多买多折未付款订单代理
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class ManyBuyDelegate implements ItemViewDelegate<OrderStateBean.ResultBean> {
    private Context mContext;
    private CancelOrderListener cancelOrderListener;
    private DeleteOrderListener deleteOrderListener;
    private RemindOrderListener remindOrderListener;
    private SureOrderListener sureOrderListener;

    public ManyBuyDelegate setCancelOrderListener(CancelOrderListener cancelOrderListener) {
        this.cancelOrderListener = cancelOrderListener;
        return this;
    }

    public ManyBuyDelegate setDeleteOrderListener(DeleteOrderListener deleteOrderListener) {
        this.deleteOrderListener = deleteOrderListener;
        return this;
    }

    public ManyBuyDelegate setRemindOrderListener(RemindOrderListener remindOrderListener) {
        this.remindOrderListener = remindOrderListener;
        return this;
    }

    public ManyBuyDelegate setSureOrderListener(SureOrderListener sureOrderListener) {
        this.sureOrderListener = sureOrderListener;
        return this;
    }


    public ManyBuyDelegate(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.many_buy_order_list_item;
    }

    @Override
    public boolean isForViewType(OrderStateBean.ResultBean item, int position) {
        return item.getActivityModule() == ActivityType.ACTIVITY_MANY_BUY && item.getState() == 1;
    }

    @Override
    public void convert(ViewHolder holder, OrderStateBean.ResultBean orderBean, int position) {
        RecyclerView rvChild = holder.getView(R.id.rv_many_buy_order_list);
        rvChild.setLayoutManager(new LinearLayoutManager(mContext));
        OrderListAdapter orderListAdapter = new OrderListAdapter(mContext, R.layout.many_buy_oder_list_child_item, orderBean.getChildOrderItems());
        rvChild.setAdapter(orderListAdapter);
        orderListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //跳转订单详情
                Intent intent = new Intent(mContext, ManyBuyOrderDetailActivity.class);
                intent.putExtra(ManyBuyOrderDetailActivity.TRADE_NO, orderBean.getTradeNo());
                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_NO, orderBean.getChildOrderItems().get(0).getOrderNo());
                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_STATE, orderBean.getChildOrderItems().get(0).getState());
                int paymount = 0;//订单总额
                Map<String, Integer> map = new HashMap<>();
                for (OrderStateBean.ResultBean resultBean : orderBean.getChildOrderItems()) {
                    paymount += resultBean.getPayAmount();
                    map.put(resultBean.getMerchantName(), resultBean.getPostage());
                }
                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_PAY_AMOUNT, paymount);
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        int postage = 0;//邮费
        int paymount = 0;//订单总额
        Map<String, Integer> map = new HashMap<>();
        int num = 0;
        for (OrderStateBean.ResultBean resultBean : orderBean.getChildOrderItems()) {
            paymount += resultBean.getPayAmount();
            num += resultBean.getCommodityQuantity();
            map.put(resultBean.getMerchantName(), resultBean.getPostage());
        }
        for (String key : map.keySet()) {
            postage += map.get(key);
        }
        if (postage == 0) {
            holder.setText(R.id.tv_many_buy_order_list_postage, "免运费");
        } else {
            holder.setText(R.id.tv_many_buy_order_list_postage, "运费:¥" + PriceUtil.dividePrice(postage));
        }
        holder.setText(R.id.tv_many_buy_order_list_money, "订单总额：¥" + PriceUtil.dividePrice(paymount));
        holder.setText(R.id.tv_many_buy_order_list_num, "商品总计：" + num + "件");
        int state = orderBean.getChildOrderItems().get(0).getState();
        int finalPaymount = paymount;
        if (state == 1) {
            holder.setText(R.id.tv_order_list_right, "立即付款");
            holder.setText(R.id.tv_order_list_center, "取消订单");
            holder.getView(R.id.tv_order_list_right).setVisibility(View.VISIBLE);
            holder.getView(R.id.tv_order_list_center).setVisibility(View.VISIBLE);
            holder.setOnClickListener(R.id.tv_order_list_right, v -> {
                Intent intent = new Intent(mContext, PayOrderActivity.class);
                intent.putExtra(PayOrderActivity.TOTAL_PRICE, finalPaymount + orderBean.getChildOrderItems().get(0).getPostage());
                intent.putExtra(PayOrderActivity.ORDER_NUMBER, orderBean.getTradeNo());
                intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.MANY_TYPE);
                intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
                ActivityUtils.startActivity(intent);
            });
            holder.setOnClickListener(R.id.tv_order_list_center, v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定取消订单吗？", "确定", (dialog, confirm) -> {
                    if (confirm && cancelOrderListener != null) {
                        if (orderBean.getChildOrderItems() != null && orderBean.getChildOrderItems().size() != 0) {
                            cancelOrderListener.cancelOrder(orderBean.getChildOrderItems().get(0), OrderType.MANY_TYPE);
                        }
                    }
                }).show();

            });
        } else {
            holder.setText(R.id.tv_order_list_right, "删除订单");
            holder.getView(R.id.tv_order_list_right).setVisibility(View.VISIBLE);
            holder.getView(R.id.tv_order_list_center).setVisibility(View.GONE);
            holder.setOnClickListener(R.id.tv_order_list_right, v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？", "确定", (dialog, confirm) -> {
                    if (confirm && deleteOrderListener != null) {
                        deleteOrderListener.deleteOrder(orderBean.getChildOrderItems().get(0), OrderType.MANY_TYPE);
                    }
                }).show();

            });
        }
    }

    /**
     *  未付款订单的Adapter
     */
    class OrderListAdapter extends CommonAdapter<OrderStateBean.ResultBean> {

        private List<OrderStateBean.ResultBean> manyBuyOrderBeans;

        public OrderListAdapter(Context context, int layoutId, List<OrderStateBean.ResultBean> datas) {
            super(context, layoutId, datas);
            manyBuyOrderBeans = datas;
        }

        @Override
        protected void convert(ViewHolder holder, OrderStateBean.ResultBean orderBean, int position) {
            holder.setText(R.id.tv_many_buy_order_shop_name, orderBean.getMerchantName());
            holder.setText(R.id.tv_many_buy_order_name, orderBean.getGoodsName());
            holder.setText(R.id.tv_many_buy_order_sku, orderBean.getCommodityModel() + " " + orderBean.getCommoditySpec());
            holder.setText(R.id.tv_many_buy_order_price, mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(orderBean.getCommoditySalePrice()));
            holder.setText(R.id.tv_many_buy_order_num, "x" + orderBean.getCommodityQuantity());
            holder.setText(R.id.tv_many_goods_xiadan_real_price, "原价¥" + PriceUtil.dividePrice(orderBean.getCommoditySalePrice()));
            ImageView ivLogo = holder.getView(R.id.iv_many_buy_order_logo);
            GlideUtil.showRadius(mContext, orderBean.getGoodsImageUrl(), 2, ivLogo);
            RelativeLayout rlShopName = holder.getView(R.id.rl_many_buy_order_list_title);
            TextView tvType = holder.getView(R.id.tv_many_buy_order_type);
            tvType.setText("待付款");
            if(orderBean.getState() == 1){//待付款
                tvType.setText("待付款");
            }else if(orderBean.getState() == 2){//待发货
                tvType.setText("待发货");
            }else if(orderBean.getState() == 3){
                tvType.setText("待收货");
            }else if(orderBean.getState() == 4){
                tvType.setText("交易取消");
            }else if(orderBean.getState() == 5){//拼团失败
                tvType.setText("交易完成");
            }
            if(position != 0){
                OrderStateBean.ResultBean beforeBean = manyBuyOrderBeans.get(position - 1);
                OrderStateBean.ResultBean afterBean = manyBuyOrderBeans.get(position);
                if(afterBean.getMerchantName().equals(beforeBean.getMerchantName())){
                    rlShopName.setVisibility(View.GONE);
                }else {
                    rlShopName.setVisibility(View.VISIBLE);
                }
            }else {
                rlShopName.setVisibility(View.VISIBLE);
            }
        }
    }
}
