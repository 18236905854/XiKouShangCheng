package com.xk.mall.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.CutOrderDetailBean;
import com.xk.mall.model.entity.KeyValueBean;
import com.xk.mall.model.eventbean.FinishDetailEventBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.CutOrderDetailImpl;
import com.xk.mall.model.request.CancelOrderRequestBody;
import com.xk.mall.model.request.DeleteOrderRequestBody;
import com.xk.mall.presenter.CutOrderDetailPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.DateToolUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.MeiQiaUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareModel;
import com.xk.mall.utils.TimeTools;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.ChooseSellStyleDialog;
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
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * 喜立得 订单详情页面
 */
public class CutOrderDetailActivity extends BaseActivity<CutOrderDetailPresenter> implements CutOrderDetailImpl {
    private static final int TIME_DESC = 1200;
    @BindView(R.id.iv_order_state)
    ImageView ivOrderState;//订单状态图片
    @BindView(R.id.tv_order_detail_state)
    TextView tvOrderState;//订单状态文字
    @BindView(R.id.tv_order_detail_close)
    TextView tvOrderDetailClose;//砍价成功之后自动关闭订单文字
    @BindView(R.id.tv_order_detail_name)
    TextView tvOrderDetailName;//订单收货人姓名
    @BindView(R.id.tv_order_detail_phone)
    TextView tvOrderDetailPhone;//订单收货人电话
    @BindView(R.id.tv_order_detail_address)
    TextView tvOrderDetailAddress;//订单收货人地址
    @BindView(R.id.tv_order_list_shopName)
    TextView tvOrderListShopName;//订单店铺名字
    @BindView(R.id.iv_order_list_logo)
    ImageView ivOrderListLogo;//订单图片
    @BindView(R.id.tv_order_list_name)
    TextView tvOrderListName;//订单产品图片
    @BindView(R.id.tv_order_list_money)
    TextView tvOrderListMoney;//订单产品单价
    @BindView(R.id.tv_order_list_num)
    TextView tvOrderListNum;//订单产品数据
    @BindView(R.id.tv_order_detail_postage)
    TextView tvOrderDetailPostage;//订单产品邮费
    @BindView(R.id.tv_order_detail_real_price)
    TextView tvOrderDetailRealPrice;//订单产品实付价格
    @BindView(R.id.tv_order_detail_copy)
    TextView tvOrderDetailCopy;//复制按钮
    @BindView(R.id.rv_order_detail)
    RecyclerView rvOrderDetail;//订单详情
    @BindView(R.id.tv_order_detail_right)
    TextView tvRight;//最右边按钮
    @BindView(R.id.tv_order_detail_center)
    TextView tvCenter;//中间按钮
    @BindView(R.id.tv_order_receipt_time)
    TextView tvReceipt;//自动收货时间
    @BindView(R.id.tv_sku)
    TextView tvSku;
    @BindView(R.id.tv_cut_after_price)
    TextView tvCutAfterPrice;
    @BindView(R.id.tv_order_detail_remarks)
    TextView tvRemarks;//订单备注文字
    @BindView(R.id.rl_order_remarks)
    RelativeLayout rlOrderRemarks;//订单备注按钮
    @BindView(R.id.tv_pay_back)
    TextView tvPayBack;//申请退款
    private int type = 0;//订单类型  0全部、1待付款、2待发货、3待收货、4已取消、5 已完成
    public static final String ORDER_NO = "order_no";
    public static final String POSITION = "position";

    private String orderNo;
    private int listPosition;//列表下标
    private CutOrderDetailBean orderDetailBean;
    private long chaTime;
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case TIME_DESC:
//                    chaTime = chaTime - 1000;
//                    if (chaTime > 0) {
//                        tvRight.setClickable(true);
//                        mHandler.sendEmptyMessageDelayed(TIME_DESC, 1000);
//                        tvOrderDetailClose.setText(TimeTools.getCountTimeByLongZh(chaTime)+"后自动关闭");
//                    } else {
//                        tvOrderState.setText("砍价成功，支付已过期");
//                        tvRight.setEnabled(false);
//                        tvRight.setBackgroundResource(R.drawable.bg_btn_order_noblack);
//                        mPresenter.getOrderDetail(orderNo);
//                    }
//                    break;
//            }
//        }
//    };

    @Override
    protected CutOrderDetailPresenter createPresenter() {
        return new CutOrderDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cut_order_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("订单详情");
        setRightDrawable(R.drawable.wug_kefu);
        setOnRightIconClickListener(v -> {
            if(orderDetailBean != null && orderDetailBean.getMerchantPhone() != null){
                MerchantPhoneDialog dialog = new MerchantPhoneDialog(mContext, R.style.mydialog,
                        orderDetailBean.getMerchantPhone());
                dialog.show();
            }else {
                ToastUtils.showShortToast(mContext, "商家电话为空");
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        orderNo = intent.getStringExtra(ORDER_NO);
        listPosition=intent.getIntExtra(POSITION,0);
        mPresenter.getOrderDetail(orderNo);
        rvOrderDetail.setLayoutManager(new LinearLayoutManager(mContext));

    }

    /**
     * 根据不同订单类型绑定不同数据
     *
     * @param type 1待付款、2待发货、3待收货、4已完成、5已取消
     */
    private void bindRvData(int type) {
        List<KeyValueBean> orderInfo = new ArrayList<>();
        if (type == 1) {//待付款
            orderInfo.add(new KeyValueBean("订单编号:", orderNo));
            orderInfo.add(new KeyValueBean("创建时间:", orderDetailBean.getOrderTime()));
        } else if (type == 2) {//待发货
            orderInfo.add(new KeyValueBean("订单编号:", orderNo));
            if(null != orderDetailBean.getExternalPlatformNo() && !TextUtils.isEmpty(orderDetailBean.getExternalPlatformNo())){
                orderInfo.add(new KeyValueBean("交易流水号:", orderDetailBean.getExternalPlatformNo()));
            }
            orderInfo.add(new KeyValueBean("创建时间:", orderDetailBean.getOrderTime()));
            orderInfo.add(new KeyValueBean("付款时间:", orderDetailBean.getPayTime()));
        } else if (type == 3) {//待收货
            orderInfo.add(new KeyValueBean("订单编号:", orderNo));
            if(null != orderDetailBean.getExternalPlatformNo() && !TextUtils.isEmpty(orderDetailBean.getExternalPlatformNo())){
                orderInfo.add(new KeyValueBean("交易流水号:", orderDetailBean.getExternalPlatformNo()));
            }
            orderInfo.add(new KeyValueBean("创建时间:", orderDetailBean.getOrderTime()));
            orderInfo.add(new KeyValueBean("付款时间:", orderDetailBean.getPayTime()));
            orderInfo.add(new KeyValueBean("发货时间:", orderDetailBean.getShipTime()));
        } else if (type == 5) {//已完成
            orderInfo.add(new KeyValueBean("订单编号:", orderNo));
            if(null != orderDetailBean.getExternalPlatformNo() && !TextUtils.isEmpty(orderDetailBean.getExternalPlatformNo())){
                orderInfo.add(new KeyValueBean("交易流水号:", orderDetailBean.getExternalPlatformNo()));
            }
            orderInfo.add(new KeyValueBean("创建时间:", orderDetailBean.getOrderTime()));
            orderInfo.add(new KeyValueBean("付款时间:", orderDetailBean.getPayTime()));
            orderInfo.add(new KeyValueBean("发货时间:", orderDetailBean.getShipTime()));
            orderInfo.add(new KeyValueBean("成交时间:", orderDetailBean.getConfirmReceiptTime()));
        } else if (type ==4) {//已取消
            orderInfo.add(new KeyValueBean("订单编号:", orderNo));
            if(null != orderDetailBean.getExternalPlatformNo() && !TextUtils.isEmpty(orderDetailBean.getExternalPlatformNo())){
                orderInfo.add(new KeyValueBean("交易流水号:", orderDetailBean.getExternalPlatformNo()));
            }
            orderInfo.add(new KeyValueBean("创建时间:", orderDetailBean.getOrderTime()));
        }
        OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(mContext, R.layout.order_info_item, orderInfo);
        rvOrderDetail.setAdapter(orderInfoAdapter);
    }

    //获取详情成功
    @Override
    public void onGetOrderDetailSuc(BaseModel<CutOrderDetailBean> model) {
        orderDetailBean = model.getData();
        if(orderDetailBean!=null){
            AddressBean address = orderDetailBean.getAddress();
            tvOrderDetailName.setText(address.consigneeName);
            tvOrderDetailPhone.setText(address.consigneeMobile);
            tvOrderDetailAddress.setText(XiKouUtils.getAddress(mContext, address));
            if(orderDetailBean.getPostage() == 0){
                tvOrderDetailPostage.setText("全国包邮");
            }else {
                tvOrderDetailPostage.setText("¥"+ PriceUtil.dividePrice(orderDetailBean.getPostage()));
            }
            tvOrderDetailRealPrice.setText("¥"+PriceUtil.dividePrice(orderDetailBean.getPayAmount()));
            type=orderDetailBean.getState();

            String merchantName = orderDetailBean.getMerchantName();
            if(TextUtils.isEmpty(merchantName)){
                tvOrderListShopName.setText("喜扣商城");
            }else{
                tvOrderListShopName.setText(merchantName);
            }

            CutOrderDetailBean.GoodsVoBean goodsVo = orderDetailBean.getGoodsVo();
            GlideUtil.show(mContext, goodsVo.getGoodsImageUrl(), ivOrderListLogo);
            tvOrderListName.setText(goodsVo.getGoodsName());
            tvOrderListMoney.setText("¥" + PriceUtil.dividePrice(orderDetailBean.getCommoditySalePrice()));//单价
            tvOrderListNum.setText("X" + goodsVo.getCommodityQuantity());
            tvSku.setText(goodsVo.getCommodityModel() + " " + goodsVo.getCommoditySpec());
            tvCutAfterPrice.setText("¥" + PriceUtil.dividePrice(orderDetailBean.getCutPrice()));
            String remarks = orderDetailBean.getRemarks() == null? "" : orderDetailBean.getRemarks();
            if(!XiKouUtils.isNullOrEmpty(remarks)){
                tvRemarks.setText(remarks);
                rlOrderRemarks.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, RemarksActivity.class);
                    intent.putExtra(RemarksActivity.REMARKS_CONTENT, remarks);
                    intent.putExtra(RemarksActivity.REMARKS_ISEDIT, false);
                    ActivityUtils.startActivity(intent);
                });
            }else {
                tvRemarks.setText("无");
            }
            if (type == 1) {//待付款
                ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);
                //计算时间倒计时
                String orderTime=orderDetailBean.getOrderTime();
                long currentTime=System.currentTimeMillis();
                long validTime=orderDetailBean.getPayInvalidTime() * 60 * 1000;
                long endPayTime= DateToolUtils.strToDateLong(orderTime).getTime()+validTime;
                Log.e(TAG, "onGetOrderDetailSuc:endTime== "+endPayTime );
                if(currentTime > endPayTime){
                    tvOrderState.setText("支付已过期");
                    tvRight.setText("无法支付");
                    tvRight.setEnabled(false);
                    tvRight.setBackgroundResource(R.drawable.bg_btn_order_noblack);
                }else{//开始倒计时
                    chaTime = endPayTime - currentTime;
//                    mHandler.sendEmptyMessage(TIME_DESC);
                    EventBus.getDefault().post(TIME_DESC);
                    tvOrderState.setText("等待买家付款");
                    tvOrderDetailClose.setVisibility(View.VISIBLE);
                }
                tvRight.setText("立即付款");
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText("取消订单");
                tvReceipt.setVisibility(View.GONE);
                tvPayBack.setVisibility(View.GONE);
                tvCenter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定取消订单吗？","确定", (dialog, confirm) -> {
                            if(confirm){
                                cancelOrder(orderNo);
                            }
                        }).show();
                    }
                });

                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payOrder(orderDetailBean);
                    }
                });
                
                bindRvData(type);
            } else if (type == 2) {//待发货
                ivOrderState.setImageResource(R.drawable.ic_order_detail_state_pay);
                tvOrderState.setText("已付款，等待卖家发货");
                tvRight.setText("提醒发货");
                tvCenter.setVisibility(View.GONE);
                tvReceipt.setVisibility(View.GONE);
                if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1
                        && orderDetailBean.getSevenDaysNoReasonReturn() == 1) {
                    tvPayBack.setVisibility(View.GONE);
                }else {
                    tvPayBack.setVisibility(View.GONE);
                }
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.remindShip(orderNo, OrderType.CUT_TYPE);
                    }
                });
                bindRvData(type);
            } else if (type == 3) {//待收货
                ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);
                tvOrderState.setText("已发货，等待收货");
                tvRight.setText("确认收货");
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText("查看物流");
                tvReceipt.setVisibility(View.VISIBLE);
                if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1
                        && orderDetailBean.getSevenDaysNoReasonReturn() == 1) {
                    tvPayBack.setVisibility(View.GONE);
                }else {
                    tvPayBack.setVisibility(View.GONE);
                }
                tvReceipt.setText("将于" + orderDetailBean.getAutoDeliveryTime() +"自动收货");
                tvRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", getResources().getString(R.string.order_dialog_sure_content),
                            "确定", (dialog, confirm) -> {
                        if(confirm){
                            mPresenter.completeOrder(orderNo, OrderType.CUT_TYPE);
                        }
                    }).show();
                });
                bindRvData(type);
            } else if (type == 5) {//已完成
                ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
                tvOrderState.setText("交易完成");
                tvRight.setText("删除订单");
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText("查看物流");
                tvReceipt.setVisibility(View.GONE);
                if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1
                        && orderDetailBean.getSevenDaysNoReasonReturn() == 1) {
                    tvPayBack.setVisibility(View.GONE);
                }else {
                    tvPayBack.setVisibility(View.GONE);
                }
                tvCenter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, LogisticsActivity.class);
                        intent.putExtra(LogisticsActivity.ORDER_NO, orderNo);
                        intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.CUT_TYPE);
                        ActivityUtils.startActivity(intent);
                    }
                });
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                            if(confirm){
                                deleteOrder(orderNo);
                            }
                        }).show();
                    }
                });

                bindRvData(type);
            } else if (type == 4) {//已取消
                ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
                tvOrderState.setText("订单已取消");
                tvRight.setText("删除订单");
                tvReceipt.setVisibility(View.GONE);
                tvCenter.setVisibility(View.GONE);
                tvPayBack.setVisibility(View.GONE);
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                            if(confirm){
                                deleteOrder(orderNo);
                            }
                        }).show();
                    }
                });
                bindRvData(type);
            }else if(type == 6){//已关闭
                tvReceipt.setVisibility(View.GONE);
                ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
                tvOrderState.setText("交易关闭");
                tvCenter.setVisibility(View.GONE);
                tvPayBack.setVisibility(View.GONE);
                tvRight.setText("删除订单");
                tvRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            deleteOrder(orderNo);
                        }
                    }).show();
                });
            }else if(type == 15){//交易完结
                tvReceipt.setVisibility(View.GONE);
                ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
                tvOrderState.setText("交易完结");
                tvRight.setText("删除订单");
                tvPayBack.setVisibility(View.GONE);
                tvRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            deleteOrder(orderNo);
                        }
                    }).show();
                });
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText("查看物流");
                tvCenter.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    intent.putExtra(LogisticsActivity.ORDER_NO, orderNo);
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
                    ActivityUtils.startActivity(intent);
                });
            }
        }
    }

    //取消成功
    @Override
    public void onGetCancelOrderSuc(BaseModel<String> model) {
        if(tvCenter.getVisibility()==View.VISIBLE){
            tvCenter.setVisibility(View.GONE);
        }
        tvReceipt.setVisibility(View.GONE);
//        mHandler.removeMessages(TIME_DESC);
        tvOrderDetailClose.setVisibility(View.GONE);
        //刷新页面
        mPresenter.getOrderDetail(orderNo);

        //通知列表刷新
        EventBus.getDefault().post(new RefreshOrderListEvent(false,false, orderNo));
    }

    @Override
    public void onRemindShipSuccess(BaseModel model) {
        ToastUtils.showShortToast(mContext, "提醒发货成功");
    }

    @Override
    public void onExtendTheTimeSuccess(BaseModel model) {
        ToastUtils.showShortToast(mContext, "延长收货成功");
    }

    @Override
    public void onCompleteOrderSuccess(BaseModel<String> model) {
        if(tvCenter.getVisibility()==View.VISIBLE){
            tvCenter.setVisibility(View.GONE);
        }
        tvReceipt.setVisibility(View.GONE);
//        mHandler.removeMessages(TIME_DESC);
        tvOrderDetailClose.setVisibility(View.GONE);
        //刷新页面
        mPresenter.getOrderDetail(orderNo);

        //通知列表刷新
        EventBus.getDefault().post(new RefreshOrderListEvent(false,true, orderNo));
    }

    //删除成功
    @Override
    public void onGetDeleteOrderSuc(BaseModel<String> model) {
        finish();
        //通知列表刷新
        EventBus.getDefault().post(new RefreshOrderListEvent(true,false, orderNo));
    }

    /**
     * 取消订单
     */
    private void cancelOrder(String orderNum){
        CancelOrderRequestBody requestBody=new CancelOrderRequestBody();
        requestBody.setOrderNo(orderNum);
        requestBody.setOrderType(OrderType.CUT_TYPE);
        mPresenter.cancelOrder(requestBody);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishDetail(FinishDetailEventBean eventBean){
        finish();
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshTime(Integer time){
        chaTime = chaTime - 1000;
        Logger.e("刷新数据:" + chaTime);
        if (chaTime > 0) {
            tvRight.setClickable(true);
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
            tvOrderState.setText("砍价成功，支付已过期");
            tvRight.setEnabled(false);
            tvRight.setBackgroundResource(R.drawable.bg_btn_order_noblack);
            mPresenter.getOrderDetail(orderNo);
        }
    }

    /**
     * 删除订单
     */
    private void deleteOrder(String orderNum){
        DeleteOrderRequestBody requestBody=new DeleteOrderRequestBody();
        requestBody.setOrderNo(orderNum);
        requestBody.setOrderType(OrderType.CUT_TYPE);
        mPresenter.deleterOrder(requestBody);
    }

    /**
     * 立即付款
     */
    private void payOrder(CutOrderDetailBean orderDetailBean){
        Intent intent=new Intent(this,PayOrderActivity.class);
        intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
        if(!XiKouUtils.isNullOrEmpty(orderDetailBean.getBargainScheduleId())){
            intent.putExtra(PayOrderActivity.CUT_ID, orderDetailBean.getBargainScheduleId());
        }
        intent.putExtra(PayOrderActivity.GOODS_NAME, orderDetailBean.getGoodsVo().getGoodsName());
        intent.putExtra(PayOrderActivity.TOTAL_PRICE, orderDetailBean.getPayAmount() + orderDetailBean.getPostage());
        intent.putExtra(PayOrderActivity.ORDER_NUMBER, orderDetailBean.getOrderNo());
        intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.CUT_TYPE);
        startActivity(intent);
    }


    class OrderInfoAdapter extends CommonAdapter<KeyValueBean> {

        public OrderInfoAdapter(Context context, int layoutId, List<KeyValueBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, KeyValueBean keyValueBean, int position) {
            holder.setText(R.id.tv_order_info_item_key, keyValueBean.key);
            holder.setText(R.id.tv_order_info_item_value, keyValueBean.value);
        }
    }

    @OnClick({R.id.tv_order_detail_copy, R.id.tv_pay_back})
    public void onClick(View view) {
        if(view.getId() == R.id.tv_order_detail_copy){
            PriceUtil.copyOperation(mContext,orderNo);
        }else if(view.getId() == R.id.tv_pay_back){
            //跳转页面
            new SellOrderTipDialog(mContext, R.style.mydialog, "是否退款", "请确认是否退款",
                    "同意","取消", (dialog, confirm) -> {
                if(confirm){
                    Intent intent = new Intent(mContext, PayBackPostActivity.class);
                    intent.putExtra(PayBackPostActivity.GOODS_ID, orderDetailBean.getGoodsVo().getGoodsId());
                    intent.putExtra(PayBackPostActivity.GOODS_NAME, orderDetailBean.getGoodsVo().getGoodsName());
                    intent.putExtra(PayBackPostActivity.GOODS_ORDER_NO, orderNo);
                    intent.putExtra(PayBackPostActivity.GOODS_ACTIVITY_TYPE, OrderType.CUT_TYPE);
                    intent.putExtra(PayBackPostActivity.GOODS_MONEY, orderDetailBean.getPayAmount());
                    intent.putExtra(PayBackPostActivity.GOODS_LOGO, orderDetailBean.getGoodsVo().getGoodsImageUrl());
                    intent.putExtra(PayBackPostActivity.GOODS_SKU, orderDetailBean.getGoodsVo().getCommodityModel()
                            + " " + orderDetailBean.getGoodsVo().getCommoditySpec());
                    ActivityUtils.startActivity(intent);
                }
            }).show();
        }
    }
}
