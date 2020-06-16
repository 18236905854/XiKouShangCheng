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
import com.xk.mall.interfaces.VerifyUserAddressListener;
import com.xk.mall.model.entity.OrderStateBean;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.activity.LogisticsActivity;
import com.xk.mall.view.activity.ZeroOrderDetailActivity;
import com.xk.mall.view.widget.OrderTipDialog;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * @ClassName: GlobalBuyerDelegate
 * @Description: 全球买手订单代理
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class ZeroDelegate implements ItemViewDelegate<OrderStateBean.ResultBean> {
    private Context mContext;
    private CancelOrderListener cancelOrderListener;
    private DeleteOrderListener deleteOrderListener;
    private RemindOrderListener remindOrderListener;
    private SureOrderListener sureOrderListener;
    private VerifyUserAddressListener addressListener;

    public ZeroDelegate setAddressListener(VerifyUserAddressListener addressListener) {
        this.addressListener = addressListener;
        return this;
    }

    public ZeroDelegate setCancelOrderListener(CancelOrderListener cancelOrderListener) {
        this.cancelOrderListener = cancelOrderListener;
        return this;
    }

    public ZeroDelegate setDeleteOrderListener(DeleteOrderListener deleteOrderListener) {
        this.deleteOrderListener = deleteOrderListener;
        return this;
    }

    public ZeroDelegate setRemindOrderListener(RemindOrderListener remindOrderListener) {
        this.remindOrderListener = remindOrderListener;
        return this;
    }

    public ZeroDelegate setSureOrderListener(SureOrderListener sureOrderListener) {
        this.sureOrderListener = sureOrderListener;
        return this;
    }

    public ZeroDelegate(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.zero_order_list_item;
    }

    @Override
    public boolean isForViewType(OrderStateBean.ResultBean item, int position) {
        return item.getActivityModule() == ActivityType.ACTIVITY_ZERO;
    }

    @Override
    public void convert(ViewHolder holder, OrderStateBean.ResultBean orderBean, int position) {
        holder.setText(R.id.tv_order_list_name, orderBean.getGoodsName());
        TextView tvMarketPrice=holder.getView(R.id.tv_market_price);
        TextView tvOrderTotal=holder.getView(R.id.tv_order_total);
        TextView tvPostage=holder.getView(R.id.tv_postage);
        StringBuilder sbContent = new StringBuilder();
        sbContent.append("销售价:¥").append(PriceUtil.dividePrice(orderBean.getCommoditySalePrice()));
        if(orderBean.getCommodityAuctionPrice()!=0){
            sbContent.append("  拍卖价:¥").append(PriceUtil.dividePrice(orderBean.getCommodityAuctionPrice()));
        }else{
            sbContent.append("  拍卖价:¥0");
        }
        sbContent.append("  我出价:").append(orderBean.getTimesNum()).append("次");
        tvMarketPrice.setText(sbContent.toString());
        tvOrderTotal.setText("订单总额:¥"+PriceUtil.dividePrice(orderBean.getPayAmount()));
        if(orderBean.getPostage() == 0){
            tvPostage.setText("免运费");
        }else {
            tvPostage.setText("运费:¥"+PriceUtil.dividePrice(orderBean.getPostage()));
        }

        holder.setText(R.id.tv_order_list_sku,orderBean.getCommodityModel()+" "+orderBean.getCommoditySpec());
        TextView tvMerchantName = holder.getView(R.id.tv_order_list_shopName);
        if (TextUtils.isEmpty(orderBean.getMerchantName())) {
            tvMerchantName.setText("喜扣商城");
        } else {
            tvMerchantName.setText(orderBean.getMerchantName());
        }
        ImageView ivLogo = holder.getView(R.id.iv_order_list_logo);
        GlideUtil.show(mContext, orderBean.getGoodsImageUrl(), ivLogo);

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
                        cancelOrderListener.cancelOrder(orderBean, OrderType.ZERO_TYPE);
//                        operationIndex=getOrderBeanPosition(orderBean);
//                        cancelOrder(orderBean);
                    }
                }).show();
            });

            //立即付款
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(XiKouUtils.isNullOrEmpty(orderBean.getReceiptAddressRef())){
                        //该订单没有地址
                        new OrderTipDialog(mContext, R.style.mydialog, "提示", "请先选择地址再付款","知道了", (dialog, confirm) -> {
                            Intent intent = new Intent(mContext, ZeroOrderDetailActivity.class);
                            intent.putExtra(ZeroOrderDetailActivity.ORDER_NO, orderBean.getOrderNo());
                            intent.putExtra(ZeroOrderDetailActivity.POSITION,position);
                            ActivityUtils.startActivity(intent);
                        }).show();
                    }else {
                        if(addressListener != null){
                            addressListener.verifyAddress(orderBean.getReceiptAddressRef());
                        }
                    }
                }
            });

        } else if (orderBean.getState() == 2) {//待发货
            tvType.setText("待发货");
            btnCenter.setVisibility(View.GONE);
            btnRight.setText("提醒发货");

            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(remindOrderListener != null){
                        remindOrderListener.remindOrder(orderBean, OrderType.ZERO_TYPE);
                    }
//                    operationIndex=getOrderBeanPosition(orderBean);
//                    mPresenter.remindShip(orderBean.getOrderNo(), OrderType.ZERO_TYPE);
                }
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
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.ZERO_TYPE);
                    ActivityUtils.startActivity(intent);
                }
            });

            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", mContext.getResources().getString(R.string.order_dialog_sure_content),"确定", (dialog, confirm) -> {
                        if(confirm && sureOrderListener != null){
                            sureOrderListener.sureOrder(orderBean, OrderType.ZERO_TYPE);
//                            operationIndex=getOrderBeanPosition(orderBean);
//                            mPresenter.completeOrder(orderBean.getOrderNo(), OrderType.ZERO_TYPE);
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
                            deleteOrderListener.deleteOrder(orderBean, OrderType.ZERO_TYPE);
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
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.ZERO_TYPE);
                ActivityUtils.startActivity(intent);
            });
            btnRight.setText("删除订单");

            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm && deleteOrderListener != null){
                            deleteOrderListener.deleteOrder(orderBean, OrderType.ZERO_TYPE);
//                            operationIndex=getOrderBeanPosition(orderBean);
//                            deleteOrder(orderBean);
                        }
                    }).show();
                }
            });
        }
    }
}
