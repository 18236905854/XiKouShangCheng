package com.xk.mall.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.interfaces.CancelOrderListener;
import com.xk.mall.interfaces.DeleteOrderListener;
import com.xk.mall.interfaces.RemindOrderListener;
import com.xk.mall.interfaces.SureOrderListener;
import com.xk.mall.model.entity.OrderStateBean;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.activity.LogisticsActivity;
import com.xk.mall.view.activity.OtherPayActivity;
import com.xk.mall.view.activity.PayOrderActivity;
import com.xk.mall.view.widget.OrderTipDialog;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * @ClassName: GlobalBuyerDelegate
 * @Description: 全球买手订单代理
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class WuGDelegate implements ItemViewDelegate<OrderStateBean.ResultBean> {
    private Context mContext;
    private CancelOrderListener cancelOrderListener;
    private DeleteOrderListener deleteOrderListener;
    private RemindOrderListener remindOrderListener;
    private SureOrderListener sureOrderListener;

    public WuGDelegate setCancelOrderListener(CancelOrderListener cancelOrderListener) {
        this.cancelOrderListener = cancelOrderListener;
        return this;
    }

    public WuGDelegate setDeleteOrderListener(DeleteOrderListener deleteOrderListener) {
        this.deleteOrderListener = deleteOrderListener;
        return this;
    }

    public WuGDelegate setRemindOrderListener(RemindOrderListener remindOrderListener) {
        this.remindOrderListener = remindOrderListener;
        return this;
    }

    public WuGDelegate setSureOrderListener(SureOrderListener sureOrderListener) {
        this.sureOrderListener = sureOrderListener;
        return this;
    }

    public WuGDelegate(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.order_list_item;
    }

    @Override
    public boolean isForViewType(OrderStateBean.ResultBean item, int position) {
        return item.getActivityModule() == ActivityType.ACTIVITY_WUG;
    }

    @Override
    public void convert(ViewHolder holder, OrderStateBean.ResultBean orderBean, int position) {
        holder.setText(R.id.tv_order_list_shopName, orderBean.getMerchantName());
        holder.setText(R.id.tv_order_list_name, orderBean.getGoodsName());
        holder.setText(R.id.tv_order_list_money, mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(orderBean.getCommoditySalePrice()));
        holder.setText(R.id.tv_order_list_num, "x" + orderBean.getCommodityQuantity());
        holder.setText(R.id.tv_order_list_sku, orderBean.getCommodityModel() + " " + orderBean.getCommoditySpec());
        holder.setText(R.id.tv_order_list_pay_amount, "订单总额：¥" + PriceUtil.dividePrice(orderBean.getPayAmount()));
        if(orderBean.getPostage() == 0){
            holder.setText(R.id.tv_order_list_postage, "免运费");
        }else {
            holder.setText(R.id.tv_order_list_postage, "运费：¥" + PriceUtil.dividePrice(orderBean.getPostage()));
        }
        ImageView ivLogo = holder.getView(R.id.iv_order_list_logo);
        GlideUtil.showRadius(mContext, orderBean.getGoodsImageUrl(), 2, ivLogo);
        TextView tvType = holder.getView(R.id.tv_order_list_type);
        TextView btnRight = holder.getView(R.id.tv_order_list_right);//最右边按钮
        TextView btnCenter = holder.getView(R.id.tv_order_list_center);//中间按钮
        TextView btnLeft = holder.getView(R.id.tv_order_list_left);//最左边按钮
        btnRight.setBackgroundResource(R.drawable.bg_btn_order_black);
        btnCenter.setBackgroundResource(R.drawable.bg_btn_order_gray);
        btnLeft.setBackgroundResource(R.drawable.bg_btn_order_gray);
        btnCenter.setTextColor(Color.parseColor("#999999"));
        btnRight.setTextColor(Color.WHITE);
        //订单类型  全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
        if(orderBean.getState() == 1){//待付款
            tvType.setText("待付款");
            btnLeft.setVisibility(View.GONE);
            btnRight.setVisibility(View.VISIBLE);
            if(orderBean.getPaid() == 1){
                btnCenter.setVisibility(View.VISIBLE);
                btnCenter.setText("请人代付");
            }else {
                btnCenter.setVisibility(View.GONE);
            }
            btnLeft.setText("取消订单");
            btnRight.setText("立即付款");
            btnRight.setOnClickListener(v -> {
                //立即付款
                Intent intent = new Intent(mContext, PayOrderActivity.class);
                intent.putExtra(PayOrderActivity.GOODS_NAME, orderBean.getGoodsName());
                intent.putExtra(PayOrderActivity.TOTAL_PRICE, orderBean.getPayAmount());
                intent.putExtra(PayOrderActivity.ORDER_NUMBER, orderBean.getOrderNo());
                intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, true);
                intent.putExtra(PayOrderActivity.COUPON_VALUE, orderBean.getCouponAmount());
                ActivityUtils.startActivity(intent);
            });
            btnCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, OtherPayActivity.class);
                intent.putExtra(OtherPayActivity.GOODS_NAME, orderBean.getGoodsName());
                intent.putExtra(OtherPayActivity.TOTAL_PRICE, orderBean.getPayAmount());
                intent.putExtra(OtherPayActivity.ORDER_NUMBER, orderBean.getOrderNo());
                intent.putExtra(OtherPayActivity.PAYMENT_ID, orderBean.getPartnerId());
                intent.putExtra(OtherPayActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                ActivityUtils.startActivity(intent);
            });

            btnLeft.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定取消订单吗？","确定", (dialog, confirm) -> {
                    if(confirm && cancelOrderListener != null){
                        cancelOrderListener.cancelOrder(orderBean, OrderType.WUG_TYPE);
                    }
                }).show();
            });
        }else if(orderBean.getState() == 2){//待发货
            tvType.setText("待发货");
            btnCenter.setVisibility(View.GONE);
            btnLeft.setVisibility(View.GONE);
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setText("提醒发货");
            btnRight.setOnClickListener(v -> {
                if(remindOrderListener != null){
                    remindOrderListener.remindOrder(orderBean, OrderType.WUG_TYPE);
                }
            });
        }else if(orderBean.getState() == 3){
            tvType.setText("待收货");
            btnCenter.setVisibility(View.VISIBLE);
            btnLeft.setVisibility(View.GONE);
            btnRight.setVisibility(View.VISIBLE);
            btnCenter.setText("查看物流");
            btnRight.setText("确认收货");
            btnCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                ActivityUtils.startActivity(intent);
            });
            btnRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", mContext.getResources().getString(R.string.order_dialog_sure_content),
                        "确定", (dialog, confirm) -> {
                    if(confirm && sureOrderListener != null){
                        sureOrderListener.sureOrder(orderBean, OrderType.WUG_TYPE);
//                        clickPos = getOrderBeanPosition(orderBean);
//                        mPresenter.completeOrder(orderBean.getOrderNo(), OrderType.WUG_TYPE);
                    }
                }).show();
            });
        }else if(orderBean.getState() == 4){
            tvType.setText("已取消");
            btnCenter.setVisibility(View.GONE);
            btnLeft.setVisibility(View.GONE);
            btnRight.setText("删除订单");
            btnRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                    if(confirm && deleteOrderListener != null){
                        deleteOrderListener.deleteOrder(orderBean, OrderType.WUG_TYPE);
//                        clickPos = getOrderBeanPosition(orderBean);
//                        mPresenter.deleteOrder(orderBean.getOrderNo(), OrderType.WUG_TYPE);
                    }
                }).show();
            });
        }else if(orderBean.getState() == 5){
            tvType.setText("已完成");
            btnCenter.setVisibility(View.GONE);
            btnRight.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                ActivityUtils.startActivity(intent);
            });
            btnLeft.setVisibility(View.GONE);
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setText("查看物流");
            btnRight.setTextColor(Color.parseColor("#999999"));
            btnRight.setBackgroundResource(R.drawable.bg_btn_order_gray);
        }else if(orderBean.getState() == 6){
            tvType.setText("已关闭");
            btnCenter.setVisibility(View.GONE);
            btnLeft.setVisibility(View.GONE);
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setText("删除订单");
            btnRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                    if(confirm && deleteOrderListener != null){
                        deleteOrderListener.deleteOrder(orderBean, OrderType.WUG_TYPE);
//                        clickPos = getOrderBeanPosition(orderBean);
//                        mPresenter.deleteOrder(orderBean.getOrderNo(), OrderType.WUG_TYPE);
                    }
                }).show();
            });
        }else if(orderBean.getState() == 15){//交易完结
            tvType.setText("交易完结");
            btnCenter.setVisibility(View.VISIBLE);
            btnCenter.setText("查看物流");
            btnCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                ActivityUtils.startActivity(intent);
            });
            btnLeft.setVisibility(View.GONE);
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setText("删除订单");
            btnRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                    if(confirm && deleteOrderListener != null){
                        deleteOrderListener.deleteOrder(orderBean, OrderType.WUG_TYPE);
//                        clickPos = getOrderBeanPosition(orderBean);
//                        mPresenter.deleteOrder(orderBean.getOrderNo(), OrderType.WUG_TYPE);
                    }
                }).show();
            });
        }
    }

}
