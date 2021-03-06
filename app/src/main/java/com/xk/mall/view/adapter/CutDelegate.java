package com.xk.mall.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import com.xk.mall.view.widget.OrderTipDialog;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * @ClassName: GlobalBuyerDelegate
 * @Description: 全球买手订单代理
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class CutDelegate implements ItemViewDelegate<OrderStateBean.ResultBean> {
    private Context mContext;
    private CancelOrderListener cancelOrderListener;
    private DeleteOrderListener deleteOrderListener;
    private RemindOrderListener remindOrderListener;
    private SureOrderListener sureOrderListener;

    public CutDelegate setCancelOrderListener(CancelOrderListener cancelOrderListener) {
        this.cancelOrderListener = cancelOrderListener;
        return this;
    }

    public CutDelegate setDeleteOrderListener(DeleteOrderListener deleteOrderListener) {
        this.deleteOrderListener = deleteOrderListener;
        return this;
    }

    public CutDelegate setRemindOrderListener(RemindOrderListener remindOrderListener) {
        this.remindOrderListener = remindOrderListener;
        return this;
    }

    public CutDelegate setSureOrderListener(SureOrderListener sureOrderListener) {
        this.sureOrderListener = sureOrderListener;
        return this;
    }

    public CutDelegate(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.cut_order_list_item;
    }

    @Override
    public boolean isForViewType(OrderStateBean.ResultBean item, int position) {
        return item.getActivityModule() == ActivityType.ACTIVITY_CUT;
    }

    @Override
    public void convert(ViewHolder holder, OrderStateBean.ResultBean orderBean, int position) {
        holder.setText(R.id.tv_order_list_name, orderBean.getGoodsName());
        TextView tvOrderTotal=holder.getView(R.id.tv_order_total);
        TextView tvPostage=holder.getView(R.id.tv_postage);
        TextView tvUnitMoney=holder.getView(R.id.tv_order_unit_money);
        TextView tvQuantity=holder.getView(R.id.tv_order_list_num);
        TextView tvSku=holder.getView(R.id.tv_order_list_sku);

        TextView tvMerchantName = holder.getView(R.id.tv_order_list_shopName);
        if (TextUtils.isEmpty(orderBean.getMerchantName())) {
            tvMerchantName.setText("喜扣商城");
        } else {
            tvMerchantName.setText(orderBean.getMerchantName());
        }
        ImageView ivLogo = holder.getView(R.id.iv_order_list_logo);
        GlideUtil.show(mContext, orderBean.getGoodsImageUrl(), ivLogo);
        tvUnitMoney.setText("¥"+ PriceUtil.dividePrice(orderBean.getCommoditySalePrice()));
        tvQuantity.setText("X"+orderBean.getCommodityQuantity());
        tvSku.setText(orderBean.getCommodityModel()+" "+orderBean.getCommoditySpec());
        tvOrderTotal.setText("订单总额:¥"+PriceUtil.dividePrice(orderBean.getPayAmount()));
        if(orderBean.getPostage() == 0){
            tvPostage.setText("免运费");
        }else {
            tvPostage.setText("运费:¥"+ PriceUtil.dividePrice(orderBean.getPostage()));
        }
        TextView tvType = holder.getView(R.id.tv_order_list_type);
        TextView btnRight = holder.getView(R.id.tv_order_list_right);//最右边按钮
        TextView btnCenter = holder.getView(R.id.tv_order_list_center);//中间按钮
        //订单类型  0全部、1待付款、，2待发货、3待收货 4.已取消 5，已完成
        if (orderBean.getState() == 1) {//待付款
            tvType.setText("待付款");
            btnCenter.setVisibility(View.VISIBLE);
            btnCenter.setText("取消订单");
            btnRight.setText("立即付款");
            //取消订单
            btnCenter.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定取消订单吗？","确定", (dialog, confirm) -> {
                    if(confirm && cancelOrderListener != null){
                        cancelOrderListener.cancelOrder(orderBean, OrderType.CUT_TYPE);
//                        operationIndex=getOrderBeanPosition(orderBean);
//                        cancelOrder(orderBean);
                    }
                }).show();
            });

            //立即付款
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    operationIndex=getOrderBeanPosition(orderBean);
//                    toPay(orderBean);
//                        ToastUtils.showShortToast(mContext,"payOrder");
                }
            });

        } else if (orderBean.getState() == 2) {//待发货
            tvType.setText("待发货");
            btnCenter.setVisibility(View.GONE);
            btnRight.setText("提醒发货");

            btnRight.setOnClickListener(v -> {
                if(remindOrderListener != null){
                    remindOrderListener.remindOrder(orderBean, OrderType.CUT_TYPE);
                }
//                    operationIndex=getOrderBeanPosition(orderBean);
//                    mPresenter.remindShip(orderBean.getOrderNo(), OrderType.CUT_TYPE);
            });

        } else if (orderBean.getState() == 3) {//待收货
            tvType.setText("待收货");
            btnCenter.setVisibility(View.VISIBLE);
            btnCenter.setText("查看物流");
            btnRight.setText("确认收货");

            btnCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.CUT_TYPE);
                    ActivityUtils.startActivity(intent);
                }
            });

            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", mContext.getResources().getString(R.string.order_dialog_sure_content),"确定", (dialog, confirm) -> {
                        if(confirm && sureOrderListener != null){
                            sureOrderListener.sureOrder(orderBean, OrderType.CUT_TYPE);
//                            operationIndex=getOrderBeanPosition(orderBean);
//                            mPresenter.completeOrder(orderBean.getOrderNo(), OrderType.CUT_TYPE);
                        }
                    }).show();
                }
            });

        } else if(orderBean.getState()==4){//已取消
            tvType.setText("已取消");
            btnCenter.setVisibility(View.GONE);
            btnRight.setText("删除订单");

            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm && deleteOrderListener != null){
                            deleteOrderListener.deleteOrder(orderBean, OrderType.CUT_TYPE);
//                            operationIndex=getOrderBeanPosition(orderBean);
//                            deleteOrder(orderBean);
                        }
                    }).show();

                }
            });

        }else if(orderBean.getState() == 5) {//已完成
            tvType.setText("交易完成");
            btnCenter.setVisibility(View.VISIBLE);
            btnCenter.setText("查看物流");
            btnCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.CUT_TYPE);
                ActivityUtils.startActivity(intent);
            });
            btnRight.setText("删除订单");

            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm && deleteOrderListener != null){
                            deleteOrderListener.deleteOrder(orderBean, OrderType.CUT_TYPE);
//                            operationIndex=getOrderBeanPosition(orderBean);
//                            deleteOrder(orderBean);
                        }
                    }).show();
                }
            });
        }
    }
}
