package com.xk.mall.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.gen.AddressServerBeanDao;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.model.entity.GlobalBuyerOrderDetailBean;
import com.xk.mall.model.entity.KeyValueBean;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.UserItem;
import com.xk.mall.model.eventbean.FinishDetailEventBean;
import com.xk.mall.model.eventbean.OtherPaySuccessBean;
import com.xk.mall.model.eventbean.PaySuccessBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.WuGOrderDetailViewImpl;
import com.xk.mall.presenter.WuGOrderDetailPresenter;
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
import com.xk.mall.view.widget.WuGOrderTipDialog;
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
import cn.iwgang.countdownview.CountdownView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName WuGOrderDetailActivity
 * Description 吾G订单详情页面
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class WuGOrderDetailActivity extends BaseActivity<WuGOrderDetailPresenter> implements WuGOrderDetailViewImpl {
    private static final int TIME_DESC = 1200;
    @BindView(R.id.iv_order_state)
    ImageView ivOrderState;//订单状态图片
    @BindView(R.id.tv_order_detail_state)
    TextView tvOrderState;//订单状态文字
    @BindView(R.id.tv_order_detail_close)
    TextView tvOrderDetailClose;//自动关闭订单文字
    @BindView(R.id.count_order_detail)
    CountdownView countdownView;//未付款自动关闭订单倒计时
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
    @BindView(R.id.tv_order_detail_sku)
    TextView tvOrderListSku;//订单的Sku
    @BindView(R.id.tv_order_detail_price)
    TextView tvOrderDetailPrice;//订单产品砍后价格
    @BindView(R.id.tv_order_detail_postage)
    TextView tvOrderDetailPostage;//订单产品邮费
    @BindView(R.id.tv_order_detail_real_price)
    TextView tvOrderDetailRealPrice;//订单产品实付价格
    @BindView(R.id.tv_order_detail_copy)
    TextView tvOrderDetailCopy;//复制按钮
    @BindView(R.id.rv_order_detail)
    RecyclerView rvOrderDetail;//订单详情
    @BindView(R.id.tv_order_detail_right)
    TextView tvOrderDetailRight;//最右边按钮
    @BindView(R.id.tv_order_detail_center)
    TextView tvOrderDetailCenter;//中间按钮
    @BindView(R.id.tv_order_detail_left)
    TextView tvOrderDetailLeft;//最左边按钮
    @BindView(R.id.tv_order_receipt_time)
    TextView tvReceipt;//自动确认收货时间
    @BindView(R.id.tv_order_detail_remarks)
    TextView tvRemarks;
    @BindView(R.id.tv_pay_back)
    TextView tvPayBack;//申请退款
    @BindView(R.id.rl_order_remarks)
    RelativeLayout rlOrderRemarks;
    private int type = 0;//订单状态；不传数据-全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
    public static String ORDER_TYPE = "order_type";
//    /**intent传递过来的商家名称的key*/
////    public static final String MERCHANT_NAME = "merchant_name";
    /**
     * intent传递过来的订单号的key
     */
    public static final String ORDER_NO = "order_no";
    //    /**intent传递过来的商品名称的key*/
//    public static final String GOODS_NAME = "goods_name";
//    /**intent传递过来的订单对象的key*/
//    public static final String GOODS_BEAN = "goods_bean";
//    /**intent传递过来的SKU的key*/
//    public static final String GOODS_SKU = "goods_sku";
//    /**intent传递过来的商品数量的key*/
//    public static final String GOODS_NUM = "goods_num";
//    /**intent传递过来的商品单价的key*/
//    public static final String GOODS_UNIT_PRICE = "goods_unit_price";
//    /**intent传递过来的商品总价的key*/
//    public static final String GOODS_TOTAL_PRICE = "goods_total_price";
//    private OrderBean orderBean;//传递过来订单对象
    private GlobalBuyerOrderDetailBean orderDetailBean;//订单详情对象
    private long chaTime;
    private String orderNo;//订单号

    @Override
    protected WuGOrderDetailPresenter createPresenter() {
        return new WuGOrderDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
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
                ToastUtils.showShort("商家电话为空");
            }
        });
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(this);
        Intent intent = getIntent();
        type = intent.getIntExtra(ORDER_TYPE, 0);
        orderNo = intent.getStringExtra(ORDER_NO);
        mPresenter.getOrderDetail(orderNo, OrderType.WUG_TYPE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @OnClick({R.id.tv_order_detail_copy, R.id.tv_pay_back})
    public void onClick(View view) {
        if(view.getId() == R.id.tv_pay_back){
            //跳转页面
            Intent intent = new Intent(mContext, PayBackPostActivity.class);
            intent.putExtra(PayBackPostActivity.GOODS_ID, orderDetailBean.getGoodsVo().getGoodsId());
            intent.putExtra(PayBackPostActivity.GOODS_NAME, orderDetailBean.getGoodsVo().getGoodsName());
            intent.putExtra(PayBackPostActivity.GOODS_ORDER_NO, orderNo);
            intent.putExtra(PayBackPostActivity.GOODS_ACTIVITY_TYPE, 4);
            intent.putExtra(PayBackPostActivity.GOODS_MONEY, orderDetailBean.getPayAmount());
            intent.putExtra(PayBackPostActivity.GOODS_LOGO, orderDetailBean.getGoodsVo().getGoodsImageUrl());
            intent.putExtra(PayBackPostActivity.GOODS_SKU, orderDetailBean.getGoodsVo().getCommodityModel()
                    + " " + orderDetailBean.getGoodsVo().getCommoditySpec());
            ActivityUtils.startActivity(intent);
        }else if(view.getId() == R.id.tv_order_detail_copy){
            PriceUtil.copyOperation(mContext,orderNo);
        }
    }

    /**
     * 根据订单状态不同显示不同数据
     *
     * @param type 订单状态
     */
    private void bindState(int type) {
        if (type == 1) {//待付款
            ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);

            //计算时间倒计时
            String orderTime = orderDetailBean.getOrderTime();
            long currentTime = System.currentTimeMillis();
            long validTime = orderDetailBean.getPayInvalidTime() * 60 * 1000;
            long endPayTime = DateToolUtils.strToDateLong(orderTime).getTime() + validTime;

            if (currentTime > endPayTime) {
                tvOrderState.setText("下单成功，支付已过期");
                tvOrderDetailRight.setText("无法支付");
                tvOrderDetailClose.setVisibility(View.GONE);
                tvOrderDetailRight.setEnabled(false);
                tvOrderDetailCenter.setEnabled(false);
                tvOrderDetailRight.setBackgroundResource(R.drawable.bg_btn_order_noblack);
            } else {//开始倒计时
                chaTime = endPayTime - currentTime;
//                mHandler.sendEmptyMessage(TIME_DESC);
                EventBus.getDefault().post(TIME_DESC);
                tvOrderState.setText("下单成功，等待买家付款");
                tvOrderDetailClose.setVisibility(View.VISIBLE);
                tvOrderDetailRight.setText("立即付款");
                if(orderDetailBean.getPaid() == 1){
                    tvOrderDetailCenter.setVisibility(View.VISIBLE);
                    tvOrderDetailCenter.setText("请人代付");
                }else {
                    tvOrderDetailCenter.setVisibility(View.GONE);
                }
                tvOrderDetailLeft.setVisibility(View.GONE);
                tvOrderDetailLeft.setText("取消订单");
            }
            tvReceipt.setVisibility(View.GONE);
            tvPayBack.setVisibility(View.GONE);
            tvOrderDetailRight.setOnClickListener(v -> {
                //立即付款
                Intent intent = new Intent(mContext, PayOrderActivity.class);
                intent.putExtra(PayOrderActivity.GOODS_NAME, orderDetailBean.getGoodsVo().getGoodsName());
                intent.putExtra(PayOrderActivity.TOTAL_PRICE, orderDetailBean.getPayAmount() );
                intent.putExtra(PayOrderActivity.ORDER_NUMBER, orderDetailBean.getOrderNo());
                intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, true);
                intent.putExtra(PayOrderActivity.COUPON_VALUE, orderDetailBean.getCouponAmount());
                ActivityUtils.startActivity(intent);
            });
            tvOrderDetailCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, OtherPayActivity.class);
                intent.putExtra(OtherPayActivity.GOODS_NAME, orderDetailBean.getGoodsVo().getGoodsName());
                intent.putExtra(OtherPayActivity.TOTAL_PRICE, orderDetailBean.getPayAmount());
                intent.putExtra(OtherPayActivity.ORDER_NUMBER, orderDetailBean.getOrderNo());
                intent.putExtra(OtherPayActivity.PAYMENT_ID, orderDetailBean.getPartnerId());
                intent.putExtra(OtherPayActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                ActivityUtils.startActivity(intent);
            });

            tvOrderDetailLeft.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定取消订单吗？","确定", (dialog, confirm) -> {
                    if(confirm){
                        mPresenter.cancelOrder(orderNo, OrderType.WUG_TYPE);
                    }
                }).show();
            });
        } else if (type == 2) {//待发货
            ivOrderState.setImageResource(R.drawable.ic_order_detail_state_pay);
            tvOrderState.setText("已付款，等待卖家发货");
            tvOrderDetailCenter.setVisibility(View.GONE);
            tvOrderDetailLeft.setVisibility(View.GONE);
            tvOrderDetailRight.setText("提醒发货");
            tvReceipt.setVisibility(View.GONE);
            if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1
                    && orderDetailBean.getSevenDaysNoReasonReturn() == 1) {
                tvPayBack.setVisibility(View.VISIBLE);
            }else {
                tvPayBack.setVisibility(View.GONE);
            }
            tvOrderDetailRight.setOnClickListener(v -> mPresenter.remindShip(orderNo, OrderType.WUG_TYPE));
        } else if (type == 3) {//待收货
            ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);
            tvOrderState.setText("已发货，等待收货");
            tvOrderDetailRight.setText("确认收货");
            tvOrderDetailCenter.setVisibility(View.VISIBLE);
            tvOrderDetailCenter.setText("查看物流");
            if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1
                    && orderDetailBean.getSevenDaysNoReasonReturn() == 1) {
                tvPayBack.setVisibility(View.VISIBLE);
            }else {
                tvPayBack.setVisibility(View.GONE);
            }
            tvOrderDetailCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra(LogisticsActivity.ORDER_NO, orderNo);
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                ActivityUtils.startActivity(intent);
            });
            tvOrderDetailLeft.setVisibility(View.GONE);
            tvReceipt.setVisibility(View.VISIBLE);
            tvReceipt.setText("将于" + orderDetailBean.getAutoDeliveryTime() +"自动收货");
            tvOrderDetailRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", getResources().getString(R.string.order_dialog_sure_content),
                        "确定", (dialog, confirm) -> {
                    if(confirm){
                        mPresenter.completeOrder(orderNo, OrderType.WUG_TYPE);
                    }
                }).show();
            });
        } else if (type == 4) {//已取消
            ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
            tvOrderState.setText("交易取消");
            tvReceipt.setVisibility(View.GONE);
            tvOrderDetailLeft.setVisibility(View.GONE);
            tvOrderDetailCenter.setVisibility(View.GONE);
            tvOrderDetailRight.setText("删除订单");
            tvPayBack.setVisibility(View.GONE);
            tvOrderDetailRight.setOnClickListener(v ->
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            mPresenter.deleteOrder(orderNo, OrderType.WUG_TYPE);
                        }
                    }).show());
        } else if (type == 5) {//已完成
            ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
            tvOrderState.setText("交易完成");
            tvOrderDetailRight.setText("查看物流");
            tvOrderDetailRight.setTextColor(Color.parseColor("#999999"));
            tvOrderDetailRight.setBackgroundResource(R.drawable.bg_btn_order_gray);
            tvReceipt.setVisibility(View.GONE);
            if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1
                    && orderDetailBean.getSevenDaysNoReasonReturn() == 1) {
                tvPayBack.setVisibility(View.VISIBLE);
            }else {
                tvPayBack.setVisibility(View.GONE);
            }
            tvOrderDetailLeft.setVisibility(View.GONE);
            tvOrderDetailCenter.setVisibility(View.GONE);
            tvOrderDetailRight.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra(LogisticsActivity.ORDER_NO, orderNo);
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                ActivityUtils.startActivity(intent);
            });
        } else if (type == 6) {//已关闭
            ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
            tvOrderState.setText("交易关闭");
            tvReceipt.setVisibility(View.GONE);
            tvOrderDetailCenter.setVisibility(View.GONE);
            tvOrderDetailLeft.setVisibility(View.GONE);
            tvOrderDetailRight.setVisibility(View.VISIBLE);
            tvPayBack.setVisibility(View.GONE);
            tvOrderDetailRight.setText("删除订单");
            tvOrderDetailRight.setOnClickListener(v ->
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                if(confirm){
                    mPresenter.deleteOrder(orderNo, OrderType.WUG_TYPE);
                }
            }).show());
        } else if (type == 7) {
            ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
            tvOrderState.setText("订单待确认");
            tvOrderDetailRight.setText("寄卖");
            tvReceipt.setVisibility(View.GONE);
            tvOrderDetailCenter.setVisibility(View.VISIBLE);
            tvOrderDetailCenter.setText("发货");
            tvOrderDetailLeft.setVisibility(View.GONE);
            tvPayBack.setVisibility(View.GONE);
        }else if(type == 15){//交易完结
            tvPayBack.setVisibility(View.GONE);
            ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
            tvOrderState.setText("交易完结");
            tvOrderDetailRight.setText("删除订单");
            tvReceipt.setVisibility(View.GONE);
            tvOrderDetailLeft.setVisibility(View.GONE);
            tvOrderDetailCenter.setVisibility(View.VISIBLE);
            tvOrderDetailCenter.setText("查看物流");
            tvOrderDetailCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra(LogisticsActivity.ORDER_NO, orderNo);
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                ActivityUtils.startActivity(intent);
            });
            tvOrderDetailRight.setOnClickListener(v ->
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            mPresenter.deleteOrder(orderNo, OrderType.WUG_TYPE);
                        }
                    }).show());
        }
    }

    /**
     * 根据不同订单类型绑定不同数据
     */
    private void bindRvData(GlobalBuyerOrderDetailBean detailBean) {
        List<KeyValueBean> orderInfo = new ArrayList<>();
        //订单状态；不传数据-全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
        if (type == 1) {//待付款
            orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
            orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
        } else if (type == 2) {//待发货
            orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
            if(null != detailBean.getExternalPlatformNo() && !TextUtils.isEmpty(detailBean.getExternalPlatformNo())){
                orderInfo.add(new KeyValueBean("交易流水号:", detailBean.getExternalPlatformNo()));
            }
            orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
            orderInfo.add(new KeyValueBean("付款时间:", detailBean.getPayTime()));
        } else if (type == 3) {//待收货
            orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
            if(null != detailBean.getExternalPlatformNo() && !TextUtils.isEmpty(detailBean.getExternalPlatformNo())){
                orderInfo.add(new KeyValueBean("交易流水号:", detailBean.getExternalPlatformNo()));
            }
            orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
            orderInfo.add(new KeyValueBean("付款时间:", detailBean.getPayTime()));
            orderInfo.add(new KeyValueBean("发货时间:", detailBean.getShipTime()));
        } else if (type == 4) {//已取消
            orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
            orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
        } else if (type == 5) {//已完成
            orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
            if(null != detailBean.getExternalPlatformNo() && !TextUtils.isEmpty(detailBean.getExternalPlatformNo())){
                orderInfo.add(new KeyValueBean("交易流水号:", detailBean.getExternalPlatformNo()));
            }
            orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
            orderInfo.add(new KeyValueBean("付款时间:", detailBean.getPayTime()));
            orderInfo.add(new KeyValueBean("发货时间:", detailBean.getShipTime()));
            orderInfo.add(new KeyValueBean("成交时间:", detailBean.getConfirmReceiptTime()));
        } else if (type == 6) {//已关闭
            orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
            orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
        } else if (type == 7) {
            orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
            orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
        }else if(type == 15){
            orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
            if(null != detailBean.getExternalPlatformNo() && !TextUtils.isEmpty(detailBean.getExternalPlatformNo())){
                orderInfo.add(new KeyValueBean("交易流水号:", detailBean.getExternalPlatformNo()));
            }
            orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
            orderInfo.add(new KeyValueBean("付款时间:", detailBean.getPayTime()));
            orderInfo.add(new KeyValueBean("发货时间:", detailBean.getShipTime()));
            orderInfo.add(new KeyValueBean("成交时间:", detailBean.getConfirmReceiptTime()));
        }
        rvOrderDetail.setLayoutManager(new LinearLayoutManager(mContext));
        OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(mContext, R.layout.order_info_item, orderInfo);
        rvOrderDetail.setAdapter(orderInfoAdapter);
    }

    @Override
    public void onGetOrderDetailSuccess(BaseModel<GlobalBuyerOrderDetailBean> model) {
        if (model.getData() != null) {
            orderDetailBean = model.getData();
            type = model.getData().getState();
            bindAddress(model.getData().getAddress());

            GlobalBuyerOrderDetailBean.GoodsVoBean goodsVo = orderDetailBean.getGoodsVo();
            tvOrderListName.setText(goodsVo.getGoodsName());
            GlideUtil.showRadius(mContext, goodsVo.getGoodsImageUrl(), 2, ivOrderListLogo);
            tvOrderListSku.setText(goodsVo.getCommodityModel() + " " + goodsVo.getCommoditySpec());
            tvOrderListNum.setText("X" + goodsVo.getCommodityQuantity());
            tvOrderListMoney.setText("¥" + PriceUtil.dividePrice(orderDetailBean.getCommoditySalePrice()));//单价
            tvOrderDetailRealPrice.setText(getResources().getString(R.string.money) + PriceUtil.dividePrice(orderDetailBean.getPayAmount()));
            tvOrderDetailPostage.setText(getResources().getString(R.string.money) + PriceUtil.dividePrice(orderDetailBean.getPostage()));
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
            String merchantName = orderDetailBean.getMerchantName();
            if(TextUtils.isEmpty(merchantName)){
                tvOrderListShopName.setText("喜扣商城");
            }else{
                tvOrderListShopName.setText(merchantName);
            }

            bindRvData(model.getData());
            bindState(type);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOtherPaySuccess(OtherPaySuccessBean otherPaySuccessBean){
        Logger.e("代付收到消息");
        if(orderDetailBean.getOrderNo().equals(otherPaySuccessBean.getOrderNo())){
            setShowDialog(false);
            Logger.e("代付刷新数据");
            mPresenter.getOrderDetail(otherPaySuccessBean.getOrderNo(), OrderType.WUG_TYPE);
        }
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
            mPresenter.getOrderDetail(orderNo, OrderType.WUG_TYPE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishDetail(FinishDetailEventBean eventBean){
        finish();
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    /**
     * 取消订单成功回调
     */
    @Override
    public void onCancelOrderSuccess(BaseModel<String> model) {
        ToastUtils.showShort("取消订单成功");
        //修改页面状态
        ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
        tvOrderState.setText("订单已取消");
        countdownView.setVisibility(View.GONE);
        tvOrderDetailClose.setVisibility(View.GONE);
        tvOrderDetailLeft.setVisibility(View.GONE);
        tvOrderDetailCenter.setVisibility(View.GONE);
        tvReceipt.setVisibility(View.GONE);
        tvOrderDetailRight.setVisibility(View.VISIBLE);
        tvOrderDetailRight.setText("删除订单");
        EventBus.getDefault().post(new RefreshOrderListEvent(false,false, orderNo));
        tvOrderDetailRight.setOnClickListener(v -> {
            //删除订单
            mPresenter.deleteOrder(orderNo, OrderType.WUG_TYPE);
        });
        type = 4;
        if (orderDetailBean != null) {
            bindRvData(orderDetailBean);
        }
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
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        if(model == null){
            ToastUtils.showShort("确认收货出错");
            return;
        }
        if(model.getData() != null){
            ToastUtils.showLong(model.getData());
        }else {
            ToastUtils.showLong("确认收货成功");
        }
        //修改页面状态
        MyApplication.isPaySuccess = true;
        ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
        tvOrderState.setText("订单已完成");
        countdownView.setVisibility(View.GONE);
        tvOrderDetailClose.setVisibility(View.GONE);
        tvReceipt.setVisibility(View.GONE);
        tvOrderDetailLeft.setVisibility(View.GONE);
        tvOrderDetailCenter.setVisibility(View.GONE);
        tvOrderDetailRight.setVisibility(View.VISIBLE);
        tvOrderDetailRight.setText("删除订单");
        EventBus.getDefault().post(new RefreshOrderListEvent(false,true, orderNo));
        tvOrderDetailRight.setOnClickListener(v -> {
            //删除订单
            mPresenter.deleteOrder(orderNo, OrderType.WUG_TYPE);
        });
        type = 5;
        if (orderDetailBean != null) {
            bindRvData(orderDetailBean);
        }
    }

    /**
     * 删除订单成功回调
     */
    @Override
    public void onDeleteSuccess(BaseModel<String> model) {
        ToastUtils.showShort("订单删除成功");
        //关闭页面，刷新列表数据
        EventBus.getDefault().post(new RefreshOrderListEvent(true,false, orderDetailBean.getOrderNo()));
        finish();
    }

    /**
     * 绑定地址信息
     */
    private void bindAddress(AddressBean address) {
        if (address != null) {
            tvOrderDetailName.setText(address.consigneeName);
            tvOrderDetailPhone.setText(address.consigneeMobile);
            tvOrderDetailAddress.setText(XiKouUtils.getAddress(mContext, address));
        }
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

}
