package com.xk.mall.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.interfaces.CancelOrderListener;
import com.xk.mall.interfaces.DeleteOrderListener;
import com.xk.mall.interfaces.RemindOrderListener;
import com.xk.mall.interfaces.SureOrderListener;
import com.xk.mall.model.entity.ManyBuyOrderBean;
import com.xk.mall.model.entity.OrderStateBean;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.activity.LogisticsActivity;
import com.xk.mall.view.activity.PayOrderActivity;
import com.xk.mall.view.widget.OrderTipDialog;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * @ClassName: GlobalBuyerDelegate
 * @Description: 多买多折已付款订单代理
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class ManyBuySingleDelegate implements ItemViewDelegate<OrderStateBean.ResultBean> {
    private Context mContext;
    private CancelOrderListener cancelOrderListener;
    private DeleteOrderListener deleteOrderListener;
    private RemindOrderListener remindOrderListener;
    private SureOrderListener sureOrderListener;

    public ManyBuySingleDelegate setCancelOrderListener(CancelOrderListener cancelOrderListener) {
        this.cancelOrderListener = cancelOrderListener;
        return this;
    }

    public ManyBuySingleDelegate setDeleteOrderListener(DeleteOrderListener deleteOrderListener) {
        this.deleteOrderListener = deleteOrderListener;
        return this;
    }

    public ManyBuySingleDelegate setRemindOrderListener(RemindOrderListener remindOrderListener) {
        this.remindOrderListener = remindOrderListener;
        return this;
    }

    public ManyBuySingleDelegate setSureOrderListener(SureOrderListener sureOrderListener) {
        this.sureOrderListener = sureOrderListener;
        return this;
    }

    public ManyBuySingleDelegate(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.many_buy_oder_single_child_item;
    }

    @Override
    public boolean isForViewType(OrderStateBean.ResultBean item, int position) {
        return item.getActivityModule() == ActivityType.ACTIVITY_MANY_BUY && item.getState() != 1;
    }

    @Override
    public void convert(ViewHolder holder, OrderStateBean.ResultBean orderBean, int position) {
        holder.setText(R.id.tv_many_buy_order_shop_name, orderBean.getMerchantName());
        holder.setText(R.id.tv_many_buy_order_name, orderBean.getGoodsName());
        holder.setText(R.id.tv_many_buy_order_sku, orderBean.getCommodityModel() + " " + orderBean.getCommoditySpec());
        holder.setText(R.id.tv_many_buy_order_price, mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(orderBean.getCommoditySalePrice()));
        holder.setText(R.id.tv_many_buy_order_num, "x" + orderBean.getCommodityQuantity());
        if(orderBean.getPostage() == 0){
            holder.setText(R.id.tv_many_buy_order_list_postage, "免运费");
        }else {
            holder.setText(R.id.tv_many_buy_order_list_postage, "运费:¥" + PriceUtil.dividePrice(orderBean.getPostage()));
        }
        holder.setText(R.id.tv_many_buy_order_list_money, "订单总额：¥" + PriceUtil.dividePrice(orderBean.getPayAmount()));
        int num = orderBean.getCommodityQuantity();
        holder.setText(R.id.tv_many_buy_order_list_num, "商品总计：" + num + "件");
        ImageView ivLogo = holder.getView(R.id.iv_many_buy_order_logo);
        GlideUtil.showRadius(mContext, orderBean.getGoodsImageUrl(), 2, ivLogo);
        TextView tvType = holder.getView(R.id.tv_many_buy_order_type);
        TextView btnRight = holder.getView(R.id.tv_order_list_right);//最右边按钮
        TextView btnCenter = holder.getView(R.id.tv_order_list_center);//中间按钮
        //订单类型  全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
        if(orderBean.getState() == 1){//待付款
            tvType.setText("待付款");
            btnCenter.setVisibility(View.VISIBLE);
            btnCenter.setText("取消订单");
            btnRight.setText("立即付款");
            btnRight.setOnClickListener(v -> {
                //立即付款
                Intent intent = new Intent(mContext, PayOrderActivity.class);
                intent.putExtra(PayOrderActivity.TOTAL_PRICE, orderBean.getPayAmount());
                intent.putExtra(PayOrderActivity.ORDER_NUMBER, orderBean.getTradeNo());
                intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.MANY_TYPE);
                intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
                ActivityUtils.startActivity(intent);
            });
            btnCenter.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定取消订单吗？","确定", (dialog, confirm) -> {
                    if(confirm && cancelOrderListener != null){
                        cancelOrderListener.cancelOrder(orderBean, OrderType.MANY_TYPE);
                    }
                }).show();
            });
        }else if(orderBean.getState() == 2){//待发货
            tvType.setText("待发货");
            btnCenter.setVisibility(View.GONE);
            btnRight.setText("提醒发货");
            btnRight.setOnClickListener(v -> {
                if(remindOrderListener != null){
                    remindOrderListener.remindOrder(orderBean, OrderType.MANY_TYPE);
                }
            });
        }else if(orderBean.getState() == 3){
            tvType.setText("待收货");
            btnCenter.setVisibility(View.VISIBLE);
            btnCenter.setText("查看物流");
            btnCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.MANY_TYPE);
                ActivityUtils.startActivity(intent);
            });
            btnRight.setText("确认收货");
            btnRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", mContext.getResources().getString(R.string.order_dialog_sure_content),"确定", (dialog, confirm) -> {
                    if(confirm && sureOrderListener != null){
                        sureOrderListener.sureOrder(orderBean, OrderType.MANY_TYPE);
                    }
                }).show();
            });
        }else if(orderBean.getState() == 4){
            tvType.setText("交易取消");
            btnCenter.setVisibility(View.GONE);
            btnRight.setText("删除订单");
            btnRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                    if(confirm && deleteOrderListener != null){
                        deleteOrderListener.deleteOrder(orderBean, OrderType.MANY_TYPE);
                    }
                }).show();
            });
        }else if(orderBean.getState() == 5){//拼团失败
            tvType.setText("交易完成");
            btnCenter.setVisibility(View.VISIBLE);
            btnCenter.setText("查看物流");
            btnCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.MANY_TYPE);
                ActivityUtils.startActivity(intent);
            });
            btnRight.setText("删除订单");
            btnRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                    if(confirm && deleteOrderListener != null){
                        deleteOrderListener.deleteOrder(orderBean, OrderType.MANY_TYPE);
                    }
                }).show();
            });
        }
    }
}
