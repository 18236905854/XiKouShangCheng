package com.xk.mall.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.KeyValueBean;
import com.xk.mall.model.entity.ManyBuyOrderDetailBean;
import com.xk.mall.model.eventbean.FinishDetailEventBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.ManyBuyOrderDetailViewImpl;
import com.xk.mall.presenter.ManyBuyOrderDetailPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.DateToolUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.MeiQiaUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.TimeTools;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.MerchantPhoneDialog;
import com.xk.mall.view.widget.OrderTipDialog;
import com.xk.mall.view.widget.SellOrderTipDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName ManyBuyOrderDetailActivity
 * Description 多买多折订单详情页面
 * Author 卿凯
 * Date 2019/7/20/020
 * Version V1.0
 */
public class ManyBuyOrderDetailActivity extends BaseActivity<ManyBuyOrderDetailPresenter> implements ManyBuyOrderDetailViewImpl {
    @BindView(R.id.iv_order_state)
    ImageView ivOrderState;//订单状态图片
    @BindView(R.id.tv_order_detail_state)
    TextView tvOrderDetailState;//订单状态文字
    @BindView(R.id.tv_order_detail_close)
    TextView tvOrderDetailClose;//订单关闭时间
    @BindView(R.id.rv_many_buy_detail)
    RecyclerView rvManyBuyDetail;
    @BindView(R.id.tv_order_detail_right)
    TextView tvOrderDetailRight;//右边按钮
    @BindView(R.id.tv_order_detail_center)
    TextView tvOrderDetailCenter;//中间按钮
    @BindView(R.id.tv_order_receipt_time)
    TextView tvReceipt;//自动确认收货
    /**intent传递过来的订单号的key*/
    public static final String ORDER_NO = "order_no";
    /**intent传递过来的订单状态的key*/
    public static final String ORDER_STATE = "order_state";
    /**intent传递过来的订单总金额的key*/
    public static final String ORDER_PAY_AMOUNT = "order_pay_amount";
    /**intent传递过来的主订单号的key*/
    public static final String TRADE_NO = "trade_no";
    private List<ManyBuyOrderDetailBean> detailBeanList;
    private ManyBuyOrderDetailAdapter adapter;
    private int type = -1;//订单状态
    private int payAmount = 0;//订单总金额
    private String tradeNo = "";//主订单号
    private String orderNo = "";//订单号
    private String autoDeliveryTime = "";//自动收货时间

    private long chaTime;
    private static final int TIME_DESC =1000 ;

    @Override
    protected ManyBuyOrderDetailPresenter createPresenter() {
        return new ManyBuyOrderDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_many_buy_order_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("订单详情");
        setRightDrawable(R.drawable.wug_kefu);
        setOnRightIconClickListener(v -> {
            if(detailBeanList != null && detailBeanList.size() != 0 && detailBeanList.get(0).getMerchantPhone() != null){
                MerchantPhoneDialog dialog = new MerchantPhoneDialog(mContext, R.style.mydialog,
                        detailBeanList.get(0).getMerchantPhone());
                dialog.show();
            }else {
                ToastUtils.showShort("商家电话为空");
            }
        });
    }

    @Override
    protected void initData() {
        orderNo = getIntent().getStringExtra(ORDER_NO);
        type = getIntent().getIntExtra(ORDER_STATE, -1);
        payAmount = getIntent().getIntExtra(ORDER_PAY_AMOUNT, 0);
        tradeNo = getIntent().getStringExtra(TRADE_NO);
        mPresenter.getOrderDetail(orderNo);

        detailBeanList = new ArrayList<>();
        rvManyBuyDetail.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ManyBuyOrderDetailAdapter(mContext, R.layout.item_many_buy_order_detail, detailBeanList);
        rvManyBuyDetail.setAdapter(adapter);
    }

    /**
     * 根据订单状态不同显示不同数据
     * @param type 订单状态
     */
    private void bindState(int type) {
        if(type == 1){//待付款
            if(detailBeanList.size() >= 2){
                showRightIcon(false);
            }
            String orderTime = detailBeanList.get(0).getOrderTime();
            long currentTime = System.currentTimeMillis();
            long validTime = detailBeanList.get(0).getPayInvalidTime() * 60 * 1000;
            long endPayTime= DateToolUtils.strToDateLong(orderTime).getTime()+validTime;
            Log.e(TAG, "onGetOrderDetailSuc:endTime== "+endPayTime );
            if(currentTime > endPayTime){
                tvOrderDetailState.setText("下单成功，支付已过期");
                tvOrderDetailRight.setText("无法支付");
                tvOrderDetailClose.setVisibility(View.GONE);
                tvOrderDetailRight.setEnabled(false);
                tvOrderDetailRight.setBackgroundResource(R.drawable.bg_btn_order_noblack);
            }else{//开始倒计时
                chaTime = endPayTime - currentTime;
//                mHandler.sendEmptyMessage(TIME_DESC);
                EventBus.getDefault().post(TIME_DESC);
                tvOrderDetailState.setText("下单成功，等待买家付款");
                tvOrderDetailClose.setVisibility(View.VISIBLE);
                tvOrderDetailRight.setText("立即付款");
            }
            ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);
            tvOrderDetailCenter.setVisibility(View.VISIBLE);
            tvOrderDetailCenter.setText("取消订单");
            tvReceipt.setVisibility(View.GONE);
            tvOrderDetailRight.setOnClickListener(v -> {
                //立即付款
                Intent intent = new Intent(mContext, PayOrderActivity.class);
                intent.putExtra(PayOrderActivity.TOTAL_PRICE, payAmount);
                intent.putExtra(PayOrderActivity.ORDER_NUMBER, tradeNo);
                intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.MANY_TYPE);
                intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
                ActivityUtils.startActivity(intent);
            });

            tvOrderDetailCenter.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定取消订单吗？","确定", (dialog, confirm) -> {
                    if(confirm){
                        mPresenter.cancelOrder(orderNo, OrderType.MANY_TYPE);
                    }
                }).show();
            });
        }else if(type == 2){//待发货
            ivOrderState.setImageResource(R.drawable.ic_order_detail_state_pay);
            tvOrderDetailState.setText("已付款，等待卖家发货");
            tvOrderDetailRight.setText("提醒发货");
            tvOrderDetailCenter.setVisibility(View.GONE);
            tvReceipt.setVisibility(View.GONE);
            tvOrderDetailRight.setOnClickListener(v -> mPresenter.remindShip(orderNo, OrderType.MANY_TYPE));
        }else if(type == 3){//待收货
            ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);
            tvOrderDetailState.setText("已发货，等待收货");
            tvOrderDetailRight.setText("确认收货");
            tvOrderDetailCenter.setVisibility(View.VISIBLE);
            tvOrderDetailCenter.setText("查看物流");
            tvReceipt.setVisibility(View.VISIBLE);
            tvReceipt.setText("将于" + autoDeliveryTime +"自动收货");
            tvOrderDetailCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra(LogisticsActivity.ORDER_NO, orderNo);
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.MANY_TYPE);
                ActivityUtils.startActivity(intent);
            });
            tvOrderDetailRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", getResources().getString(R.string.order_dialog_sure_content),
                        "确定", (dialog, confirm) -> {
                    if(confirm){
                        mPresenter.completeOrder(orderNo, OrderType.MANY_TYPE);
                    }
                }).show();
            });
        }else if(type == 4){//已取消
            ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
            tvOrderDetailState.setText("交易取消");
            tvOrderDetailRight.setText("删除订单");
            tvReceipt.setVisibility(View.GONE);
            tvOrderDetailCenter.setVisibility(View.GONE);
            tvOrderDetailRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                    if(confirm){
                        mPresenter.deleteOrder(orderNo, OrderType.MANY_TYPE);
                    }
                }).show();
            });
        }else if(type == 5){//已完成
            ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
            tvOrderDetailState.setText("交易完成");
            tvOrderDetailRight.setText("删除订单");
            tvReceipt.setVisibility(View.GONE);
            tvOrderDetailRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                    if(confirm){
                        mPresenter.deleteOrder(orderNo, OrderType.MANY_TYPE);
                    }
                }).show();
            });
            tvOrderDetailCenter.setVisibility(View.VISIBLE);
            tvOrderDetailCenter.setText("查看物流");
            tvOrderDetailCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra(LogisticsActivity.ORDER_NO, orderNo);
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.MANY_TYPE);
                ActivityUtils.startActivity(intent);
            });
        }else if(type == 6){//已关闭
            ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
            tvOrderDetailState.setText("交易关闭");
            tvOrderDetailRight.setText("删除订单");
            tvOrderDetailCenter.setVisibility(View.GONE);
            tvReceipt.setVisibility(View.GONE);
            tvOrderDetailRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                    if(confirm){
                        mPresenter.deleteOrder(orderNo, OrderType.MANY_TYPE);
                    }
                }).show();
            });
        } else if(type == 7){
            ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
            tvOrderDetailState.setText("订单待确认");
            tvOrderDetailRight.setText("寄卖");
            tvOrderDetailCenter.setVisibility(View.VISIBLE);
            tvOrderDetailCenter.setText("发货");
            tvReceipt.setVisibility(View.GONE);
        }
    }


    @Override
    public void onGetOderDetailSuccess(BaseModel<List<ManyBuyOrderDetailBean>> model) {
        if(model.getData() != null && model.getData().size() != 0){
            detailBeanList.clear();
            detailBeanList.addAll(model.getData());
            adapter.notifyDataSetChanged();
            type = model.getData().get(0).getState();
            tradeNo = model.getData().get(0).getTradeNo();
            autoDeliveryTime = model.getData().get(0).getAutoDeliveryTime();
            bindState(type);
        }
    }

    @Override
    public void onCancelOrderSuccess(BaseModel<String> model) {
        ToastUtils.showShort("取消订单成功");
        //修改页面状态
//        isCancel = true;
        ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
        tvOrderDetailState.setText("订单已取消");
        tvOrderDetailClose.setVisibility(View.GONE);
        tvReceipt.setVisibility(View.GONE);
        tvOrderDetailCenter.setVisibility(View.GONE);
        tvOrderDetailRight.setVisibility(View.VISIBLE);
        tvOrderDetailRight.setText("删除订单");
        EventBus.getDefault().post(new RefreshOrderListEvent(false,false, tradeNo));
        tvOrderDetailRight.setOnClickListener(v -> {
            //删除订单
            mPresenter.deleteOrder(tradeNo, OrderType.MANY_TYPE);
        });
        type = 4;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishDetail(FinishDetailEventBean eventBean){
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshTime(Integer time){
        chaTime = chaTime - 1000;
        Logger.e("刷新数据:" + chaTime);
        if (chaTime > 0) {
            tvOrderDetailRight.setClickable(true);
            tvOrderDetailClose.setText(TimeTools.getCountTimeByLongZh(chaTime)+"后自动关闭");
            //调用获取验证码接口，显示倒计时
            Observable.interval(1, 1, TimeUnit.SECONDS) //0延迟  每隔1秒触发
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                    .take(1) //设置循环次数
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Long aLong) {
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            EventBus.getDefault().post(TIME_DESC);
                        }
                    });
        } else {
            //重新获取数据
            mPresenter.getOrderDetail(orderNo);
        }
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    public void onRemindShipSuccess(BaseModel model) {
        ToastUtils.showShort("提醒发货成功");
    }

    @Override
    public void onExtendTheTimeSuccess(BaseModel model) {
        ToastUtils.showShort("延长收货成功");
    }

    @Override
    public void onCompleteOrderSuccess(BaseModel<String> model) {
        ToastUtils.showShort("确认收货成功");
        //修改页面状态
//        isCancel = true;
        ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
        tvOrderDetailState.setText("订单已完成");
        tvOrderDetailClose.setVisibility(View.GONE);
        tvReceipt.setVisibility(View.GONE);
        tvOrderDetailCenter.setVisibility(View.GONE);
        tvOrderDetailRight.setVisibility(View.VISIBLE);
        tvOrderDetailRight.setText("删除订单");
        EventBus.getDefault().post(new RefreshOrderListEvent(false,true, tradeNo));
        tvOrderDetailRight.setOnClickListener(v -> {
            //删除订单
            mPresenter.deleteOrder(tradeNo, OrderType.MANY_TYPE);
        });
        type = 4;
    }

    @Override
    public void onDeleteSuccess(BaseModel<String> model) {
        ToastUtils.showShort("订单删除成功");
        //关闭页面，刷新列表数据
        EventBus.getDefault().post(new RefreshOrderListEvent(true, false, tradeNo));
        finish();
    }

    /**
     * 多买多折订单详情adapter
     */
    class ManyBuyOrderDetailAdapter extends CommonAdapter<ManyBuyOrderDetailBean>{

        public ManyBuyOrderDetailAdapter(Context context, int layoutId, List<ManyBuyOrderDetailBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ManyBuyOrderDetailBean manyBuyOrderDetailBean, int position) {
            View view = holder.getView(R.id.iv_many_buy_item_head);
            if(position == 0){
                view.setVisibility(View.GONE);
            }else {
                view.setVisibility(View.VISIBLE);
            }
            if(manyBuyOrderDetailBean.getAddress() != null){
                holder.setText(R.id.tv_order_detail_name, manyBuyOrderDetailBean.getAddress().consigneeName);
                holder.setText(R.id.tv_order_detail_phone, manyBuyOrderDetailBean.getAddress().consigneeMobile);
                holder.setText(R.id.tv_order_detail_address, XiKouUtils.getAddress(mContext, manyBuyOrderDetailBean.getAddress()));
            }
            ImageView ivLogo = holder.getView(R.id.iv_order_list_logo);
            GlideUtil.showRadius(mContext, manyBuyOrderDetailBean.getGoodsVo().getGoodsImageUrl(), 2, ivLogo);
            holder.setText(R.id.tv_order_list_shopName, manyBuyOrderDetailBean.getMerchantName());
            holder.setText(R.id.tv_order_list_name, manyBuyOrderDetailBean.getGoodsVo().getGoodsName());
            holder.setText(R.id.tv_order_detail_sku, manyBuyOrderDetailBean.getGoodsVo().getCommodityModel()
                    + " " + manyBuyOrderDetailBean.getGoodsVo().getCommoditySpec());
            holder.setText(R.id.tv_order_list_money, "" + PriceUtil.dividePrice(manyBuyOrderDetailBean.getCommoditySalePrice()));
            holder.setText(R.id.tv_order_list_num, "x" + manyBuyOrderDetailBean.getGoodsVo().getCommodityQuantity());
            holder.setText(R.id.tv_order_detail_postage, getResources().getString(R.string.money) +
                    PriceUtil.dividePrice(manyBuyOrderDetailBean.getPostage()));
            holder.setText(R.id.tv_order_detail_real_price, getResources().getString(R.string.money) +
                    PriceUtil.dividePrice(manyBuyOrderDetailBean.getPayAmount()));
            RecyclerView rvChild = holder.getView(R.id.rv_order_detail);
            bindRvData(manyBuyOrderDetailBean.getState(), manyBuyOrderDetailBean, rvChild);
            String remarks = manyBuyOrderDetailBean.getRemarks() == null? "" : manyBuyOrderDetailBean.getRemarks();
            if(!XiKouUtils.isNullOrEmpty(remarks)){
                holder.setText(R.id.tv_order_detail_remarks, remarks);
                holder.setOnClickListener(R.id.rl_order_remarks, v -> {
                    Intent intent = new Intent(mContext, RemarksActivity.class);
                    intent.putExtra(RemarksActivity.REMARKS_CONTENT, remarks);
                    intent.putExtra(RemarksActivity.REMARKS_ISEDIT, false);
                    ActivityUtils.startActivity(intent);
                });
            }else {
                holder.setText(R.id.tv_order_detail_remarks, "无");
            }
            holder.setOnClickListener(R.id.tv_order_detail_copy, v -> PriceUtil.copyOperation(mContext, manyBuyOrderDetailBean.getOrderNo()));
            if(type == 1){//待付款
                holder.setVisible(R.id.tv_pay_back, false);
            }else if(type == 2){//待发货
                if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1
                        && manyBuyOrderDetailBean.getSevenDaysNoReasonReturn() == 1) {
                    holder.setVisible(R.id.tv_pay_back, false);
                }else {
                    holder.setVisible(R.id.tv_pay_back, false);
                }
            }else if(type == 3){
                if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1
                        && manyBuyOrderDetailBean.getSevenDaysNoReasonReturn() == 1) {
                    holder.setVisible(R.id.tv_pay_back, false);
                }else {
                    holder.setVisible(R.id.tv_pay_back, false);
                }
            }else if(type == 4){
                holder.setVisible(R.id.tv_pay_back, false);
            }else if(type == 5){
                if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1
                        && manyBuyOrderDetailBean.getSevenDaysNoReasonReturn() == 1) {
                    holder.setVisible(R.id.tv_pay_back, false);
                }else {
                    holder.setVisible(R.id.tv_pay_back, false);
                }
            }else if(type == 6){
                holder.setVisible(R.id.tv_pay_back, false);
            }else if(type == 15){
                holder.setVisible(R.id.tv_pay_back, false);
            }

            holder.setOnClickListener(R.id.tv_pay_back, v -> {
                //跳转页面
                new SellOrderTipDialog(mContext, R.style.mydialog, "是否退款", "请确认是否退款",
                        "同意","取消", (dialog, confirm) -> {
                    if(confirm){
                        Intent intent = new Intent(mContext, PayBackPostActivity.class);
                        intent.putExtra(PayBackPostActivity.GOODS_ID, manyBuyOrderDetailBean.getGoodsVo().getGoodsId());
                        intent.putExtra(PayBackPostActivity.GOODS_NAME, manyBuyOrderDetailBean.getGoodsVo().getGoodsName());
                        intent.putExtra(PayBackPostActivity.GOODS_ORDER_NO, orderNo);
                        intent.putExtra(PayBackPostActivity.GOODS_ACTIVITY_TYPE, OrderType.MANY_TYPE);
                        intent.putExtra(PayBackPostActivity.GOODS_MONEY, manyBuyOrderDetailBean.getPayAmount());
                        intent.putExtra(PayBackPostActivity.GOODS_LOGO, manyBuyOrderDetailBean.getGoodsVo().getGoodsImageUrl());
                        intent.putExtra(PayBackPostActivity.GOODS_SKU, manyBuyOrderDetailBean.getGoodsVo().getCommodityModel()
                                + " " + manyBuyOrderDetailBean.getGoodsVo().getCommoditySpec());
                        ActivityUtils.startActivity(intent);
                    }
                }).show();
            });
        }

        /**
         * 根据不同订单类型绑定不同数据
         */
        private void bindRvData(int type, ManyBuyOrderDetailBean detailBean, RecyclerView rvOrderDetail) {
            List<KeyValueBean> orderInfo = new ArrayList<>();
            //订单状态；不传数据-全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
            orderInfo.add(new KeyValueBean("总订单编号:", tradeNo));
            if(type == 1){//待付款
                orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
                orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
            }else if(type == 2){//待发货
                orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
                if(null != detailBean.getExternalPlatformNo() && !TextUtils.isEmpty(detailBean.getExternalPlatformNo())){
                    orderInfo.add(new KeyValueBean("交易流水号:", detailBean.getExternalPlatformNo()));
                }
                orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
                orderInfo.add(new KeyValueBean("付款时间:", detailBean.getPayTime()));
            }else if(type == 3){//待收货
                orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
                if(null != detailBean.getExternalPlatformNo() && !TextUtils.isEmpty(detailBean.getExternalPlatformNo())){
                    orderInfo.add(new KeyValueBean("交易流水号:", detailBean.getExternalPlatformNo()));
                }
                orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
                orderInfo.add(new KeyValueBean("付款时间:", detailBean.getPayTime()));
                orderInfo.add(new KeyValueBean("发货时间:", detailBean.getShipTime()));
            }else if(type == 4){//已取消
                orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
                orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
            }else if(type == 5){//已完成
                orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
                if(null != detailBean.getExternalPlatformNo() && !TextUtils.isEmpty(detailBean.getExternalPlatformNo())){
                    orderInfo.add(new KeyValueBean("交易流水号:", detailBean.getExternalPlatformNo()));
                }
                orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
                orderInfo.add(new KeyValueBean("付款时间:", detailBean.getPayTime()));
                orderInfo.add(new KeyValueBean("发货时间:", detailBean.getShipTime()));
                orderInfo.add(new KeyValueBean("成交时间:", detailBean.getConfirmReceiptTime()));
            }else if(type == 6){//已关闭
                orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
                orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
            }else if(type == 7){
                orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
                orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
            }
            rvOrderDetail.setLayoutManager(new LinearLayoutManager(mContext));
            OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(mContext, R.layout.order_info_item, orderInfo);
            rvOrderDetail.setAdapter(orderInfoAdapter);
        }
    }

    /**
     * 订单信息adapter
     */
    class OrderInfoAdapter extends CommonAdapter<KeyValueBean>{

        public OrderInfoAdapter(Context context, int layoutId, List<KeyValueBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, KeyValueBean keyValueBean, int position) {
            holder.setText(R.id.tv_order_info_item_key, keyValueBean.key);
            holder.setText(R.id.tv_order_info_item_value, keyValueBean.value);
        }
    }
}
