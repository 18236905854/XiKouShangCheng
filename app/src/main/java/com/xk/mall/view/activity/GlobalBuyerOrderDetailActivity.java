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
import android.widget.LinearLayout;
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
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.eventbean.FinishDetailEventBean;
import com.xk.mall.model.eventbean.OrderStateChangeEvent;
import com.xk.mall.model.eventbean.OtherPaySuccessBean;
import com.xk.mall.model.eventbean.PaySuccessBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.GlobalBuyerOrderDetailViewImpl;
import com.xk.mall.presenter.GlobalBuyerOrderDetailPresenter;
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
import com.xk.mall.view.widget.DialogSellShare;
import com.xk.mall.view.widget.MerchantPhoneDialog;
import com.xk.mall.view.widget.OrderTipDialog;
import com.xk.mall.view.widget.SellOrderTipDialog;
import com.xk.mall.view.widget.SellSuccessDialog;
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
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName GlobalBuyerOrderDetailActivity
 * Description 全球买手订单详情页面
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class GlobalBuyerOrderDetailActivity extends BaseActivity<GlobalBuyerOrderDetailPresenter> implements GlobalBuyerOrderDetailViewImpl {
    private static final int TIME_DESC =1000 ;
    @BindView(R.id.iv_order_state)
    ImageView ivOrderState;//订单状态图片
    @BindView(R.id.tv_order_detail_state)
    TextView tvOrderState;//订单状态文字
    @BindView(R.id.tv_order_detail_close)
    TextView tvOrderDetailClose;//砍价成功之后自动关闭订单文字
//    @BindView(R.id.count_order_detail)
//    CountdownView countdownView;//未付款自动关闭订单倒计时
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
    @BindView(R.id.ll_order_detail)
    LinearLayout llOrderDetail;//订单类型view
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
    TextView tvRemarks;//订单备注文字
    @BindView(R.id.rl_order_remarks)
    RelativeLayout rlOrderRemarks;//订单备注按钮
    @BindView(R.id.tv_pay_back)
    TextView tvPayBack;//申请退款
    private int type = 0;//订单类型  全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功
    public static String ORDER_TYPE = "order_type";
//    /**intent传递过来的商家名称的key*/
//    public static final String MERCHANT_NAME = "merchant_name";
    /**intent传递过来的订单号的key*/
    public static final String ORDER_NO = "order_no";
    /**intent传递过来的寄卖必须选中寄卖到吾G*/
    public static final String MUST_CHOOSE_WUG = "must_choose_wug";
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
    private boolean isCancel = false;//是否表示取消了订单
//    private OrderBean orderBean;
    private GlobalBuyerOrderDetailBean orderDetailBean;//订单详情对象
    private String orderNo;//订单号
    private int sellType = 0;//寄卖类型： 1 ：发货 2：寄卖
    private int shareModel;//分享模式
    private int mustChooseWug;//是否必须选中吾G

    private long chaTime;

    @Override
    protected GlobalBuyerOrderDetailPresenter createPresenter() {
        return new GlobalBuyerOrderDetailPresenter(this);
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
        mustChooseWug = intent.getIntExtra(MUST_CHOOSE_WUG, 0);
        mPresenter.getOrderDetail(orderNo);

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
            new SellOrderTipDialog(mContext, R.style.mydialog, "是否退款", "申请退款后优惠券将无法再次使用",
                    "同意","取消", (dialog, confirm) -> {
                if(confirm){
                    Intent intent = new Intent(mContext, PayBackPostActivity.class);
                    intent.putExtra(PayBackPostActivity.GOODS_ID, orderDetailBean.getGoodsVo().getGoodsId());
                    intent.putExtra(PayBackPostActivity.GOODS_NAME, orderDetailBean.getGoodsVo().getGoodsName());
                    intent.putExtra(PayBackPostActivity.GOODS_ORDER_NO, orderNo);
                    intent.putExtra(PayBackPostActivity.GOODS_ACTIVITY_TYPE, 5);
                    intent.putExtra(PayBackPostActivity.GOODS_MONEY, orderDetailBean.getPayAmount());
                    intent.putExtra(PayBackPostActivity.GOODS_LOGO, orderDetailBean.getGoodsVo().getGoodsImageUrl());
                    intent.putExtra(PayBackPostActivity.GOODS_SKU, orderDetailBean.getGoodsVo().getCommodityModel()
                            + " " + orderDetailBean.getGoodsVo().getCommoditySpec());
                    ActivityUtils.startActivity(intent);
                }
            }).show();
        }else if(view.getId() == R.id.tv_order_detail_copy){
            PriceUtil.copyOperation(mContext,orderNo);
        }
    }

    /**
     * 根据订单状态不同显示不同数据
     * @param type 订单状态
     */
    private void bindState(int type) {
        tvOrderDetailRight.setBackgroundResource(R.drawable.bg_btn_order_black);
        tvOrderDetailCenter.setBackgroundResource(R.drawable.bg_btn_order_gray);
        tvOrderDetailCenter.setTextColor(Color.parseColor("#999999"));
        tvOrderDetailLeft.setBackgroundResource(R.drawable.bg_btn_order_gray);
        if(type == 1){//待付款
            //计算时间倒计时
            String orderTime=orderDetailBean.getOrderTime();
            long currentTime=System.currentTimeMillis();
            long validTime=orderDetailBean.getPayInvalidTime() * 60 * 1000;
            long endPayTime= DateToolUtils.strToDateLong(orderTime).getTime()+validTime;
            Log.e(TAG, "onGetOrderDetailSuc:endTime== "+endPayTime );
            if(currentTime > endPayTime){
                tvOrderState.setText("下单成功，支付已过期");
                tvOrderDetailRight.setText("无法支付");
                tvOrderDetailClose.setVisibility(View.GONE);
                tvOrderDetailRight.setEnabled(false);
                tvOrderDetailCenter.setEnabled(false);
                tvOrderDetailRight.setBackgroundResource(R.drawable.bg_btn_order_noblack);
            }else{//开始倒计时
                chaTime = endPayTime - currentTime;
                tvOrderDetailRight.setText("立即付款");
                EventBus.getDefault().post(TIME_DESC);
                tvOrderState.setText("下单成功，等待买家付款");
                tvOrderDetailClose.setVisibility(View.VISIBLE);
            }
            ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);
            if(orderDetailBean.getPaid() == 1){
                tvOrderDetailCenter.setVisibility(View.VISIBLE);
                tvOrderDetailCenter.setText("请人代付");
            }else {
                tvOrderDetailCenter.setVisibility(View.GONE);
            }
            tvOrderDetailLeft.setVisibility(View.VISIBLE);
            tvOrderDetailLeft.setText("取消订单");
            tvReceipt.setVisibility(View.GONE);
            tvPayBack.setVisibility(View.GONE);
            tvOrderDetailRight.setOnClickListener(v -> {
                //立即付款
                Intent intent = new Intent(mContext, PayOrderActivity.class);
                intent.putExtra(PayOrderActivity.GOODS_NAME, orderDetailBean.getGoodsVo().getGoodsName());
                intent.putExtra(PayOrderActivity.TOTAL_PRICE, orderDetailBean.getPayAmount() );
                intent.putExtra(PayOrderActivity.ORDER_NUMBER,orderNo);
                intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);//2
                intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, true);
                intent.putExtra(PayOrderActivity.COUPON_VALUE, orderDetailBean.getDeductionCouponAmount());
                ActivityUtils.startActivity(intent);
            });
            tvOrderDetailCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, OtherPayActivity.class);
                intent.putExtra(OtherPayActivity.GOODS_NAME, orderDetailBean.getGoodsVo().getGoodsName());
                intent.putExtra(OtherPayActivity.TOTAL_PRICE, orderDetailBean.getPayAmount() );
                intent.putExtra(OtherPayActivity.ORDER_NUMBER, orderNo);
                intent.putExtra(OtherPayActivity.PAYMENT_ID, orderDetailBean.getPartnerId());
                intent.putExtra(OtherPayActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
                ActivityUtils.startActivity(intent);
            });

            tvOrderDetailLeft.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定取消订单吗？","确定", (dialog, confirm) -> {
                    if(confirm){
                        mPresenter.cancelOrder(orderNo, OrderType.GLOBAL_TYPE);
                    }
                }).show();
            });
        }else if(type == 2){//待发货
            ivOrderState.setImageResource(R.drawable.ic_order_detail_state_pay);
            tvOrderState.setText("已付款，等待卖家发货");
            tvOrderDetailCenter.setVisibility(View.GONE);
            tvOrderDetailLeft.setVisibility(View.GONE);
            if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1
                     && orderDetailBean.getSevenDaysNoReasonReturn() == 1) {
                tvPayBack.setVisibility(View.VISIBLE);
            }else {
                tvPayBack.setVisibility(View.GONE);
            }
            tvOrderDetailRight.setText("提醒发货");
            tvReceipt.setVisibility(View.GONE);
            tvOrderDetailRight.setOnClickListener(v -> mPresenter.remindShip(orderNo, OrderType.GLOBAL_TYPE));
        }else if(type == 3){//待收货
            ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);
            tvOrderState.setText("已发货，等待收货");
            tvOrderDetailRight.setText("确认收货");
            if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1
                    && orderDetailBean.getSevenDaysNoReasonReturn() == 1) {
                tvPayBack.setVisibility(View.VISIBLE);
            }else {
                tvPayBack.setVisibility(View.GONE);
            }
            tvOrderDetailCenter.setVisibility(View.VISIBLE);
            tvOrderDetailCenter.setText("查看物流");
            tvOrderDetailCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra(LogisticsActivity.ORDER_NO, orderNo);
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
                ActivityUtils.startActivity(intent);
            });
            tvOrderDetailLeft.setVisibility(View.GONE);
            tvReceipt.setVisibility(View.VISIBLE);
            tvReceipt.setText("将于" + orderDetailBean.getAutoDeliveryTime() +"自动收货");
            tvOrderDetailRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", getResources().getString(R.string.order_dialog_sure_content),
                        "确定", (dialog, confirm) -> {
                    if(confirm){
                        mPresenter.completeOrder(orderNo, OrderType.GLOBAL_TYPE);
                    }
                }).show();
            });
        }else if(type == 4){//已取消
            tvReceipt.setVisibility(View.GONE);
            ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
            tvOrderState.setText("交易取消");
            tvOrderDetailCenter.setVisibility(View.GONE);
            tvOrderDetailLeft.setVisibility(View.GONE);
            tvOrderDetailRight.setText("删除订单");
            tvPayBack.setVisibility(View.GONE);
            tvOrderDetailRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                    if(confirm){
                        mPresenter.deleteOrder(orderNo, OrderType.GLOBAL_TYPE);
                    }
                }).show();
            });
        }else if(type == 5){//已完成
            tvReceipt.setVisibility(View.GONE);
            ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
            tvOrderState.setText("交易完成");
            tvOrderDetailRight.setText("查看物流");
            tvOrderDetailRight.setTextColor(Color.parseColor("#999999"));
            tvOrderDetailCenter.setVisibility(View.GONE);
            if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1
                    && orderDetailBean.getSevenDaysNoReasonReturn() == 1) {
                tvPayBack.setVisibility(View.VISIBLE);
            }else {
                tvPayBack.setVisibility(View.GONE);
            }
            tvOrderDetailLeft.setVisibility(View.GONE);
            tvOrderDetailRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    intent.putExtra(LogisticsActivity.ORDER_NO, orderNo);
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
                    ActivityUtils.startActivity(intent);
                }
            });
        }else if(type == 6){//已关闭
            tvReceipt.setVisibility(View.GONE);
            ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
            tvOrderState.setText("交易关闭");
            tvOrderDetailCenter.setVisibility(View.GONE);
            tvOrderDetailLeft.setVisibility(View.GONE);
            tvPayBack.setVisibility(View.GONE);
            tvOrderDetailRight.setText("删除订单");
            tvOrderDetailRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                    if(confirm){
                        mPresenter.deleteOrder(orderNo, OrderType.GLOBAL_TYPE);
                    }
                }).show();
            });
        } else if(type == 7){//待确认
            tvReceipt.setVisibility(View.GONE);
            ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
            tvOrderState.setText("订单待确认");
            tvOrderDetailRight.setText("寄卖");
            if(MyApplication.switchBean != null && MyApplication.switchBean.getRefund() == 1
                    && orderDetailBean.getSevenDaysNoReasonReturn() == 1) {
                tvPayBack.setVisibility(View.VISIBLE);
            }else {
                tvPayBack.setVisibility(View.GONE);
            }
            tvOrderDetailRight.setBackgroundResource(R.drawable.bg_btn_want_sell);
            tvOrderDetailCenter.setVisibility(View.VISIBLE);
            tvOrderDetailCenter.setText("发货");
            tvOrderDetailCenter.setTextColor(Color.parseColor("#4A4A4A"));
            tvOrderDetailCenter.setBackgroundResource(R.drawable.bg_btn_want_self);
            tvOrderDetailCenter.setOnClickListener(v -> {
                //点击发货
                //点击发货自用
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确认申请该商品自用收货","确定", (dialog, confirm) -> {
                    if(confirm){
                        sellType = 1;
                        mPresenter.modifyOrderType(MyApplication.userId, orderNo, 1, 0);
                    }
                }).show();
            });
            tvOrderDetailRight.setOnClickListener(v -> {
                //点击寄卖
                new ChooseSellStyleDialog(mContext, R.style.mydialog, orderDetailBean.getGoodsVo().getWhetherToAllow(),
                        orderDetailBean.getGoodsVo().getIsDirect(), (isWug, isShare) -> {
                    if(isWug && isShare){
                        shareModel = ShareModel.MODEL_BOTH;
                        sellType = 2;
                        mPresenter.modifyOrderType(MyApplication.userId, orderNo, 2, ShareModel.MODEL_BOTH);
                    }else if(isWug){
                        //只选择寄卖
                        shareModel = ShareModel.MODEL_SELL;
                        sellType = 2;
                        mPresenter.modifyOrderType(MyApplication.userId, orderNo,  2, ShareModel.MODEL_SELL);
                    }else if(isShare){
                        //只选择分享
                        shareModel = ShareModel.MODEL_SHARE;
                        sellType = 2;
                        mPresenter.modifyOrderType(MyApplication.userId, orderNo, 2, ShareModel.MODEL_SHARE);
                    }
                }).show();
            });
        }else if(type == 11){//已寄卖
            tvReceipt.setVisibility(View.GONE);
            ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
            tvOrderState.setText("已寄卖");
            tvOrderDetailRight.setVisibility(View.GONE);
            tvOrderDetailCenter.setVisibility(View.GONE);
            llOrderDetail.setVisibility(View.VISIBLE);
            tvPayBack.setVisibility(View.GONE);
        }else if(type == 15){//交易完结
            tvReceipt.setVisibility(View.GONE);
            ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
            tvOrderState.setText("交易完结");
            tvOrderDetailRight.setText("删除订单");
            tvPayBack.setVisibility(View.GONE);
            tvOrderDetailCenter.setVisibility(View.GONE);
            tvOrderDetailLeft.setVisibility(View.GONE);
            tvOrderDetailRight.setOnClickListener(v -> {
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                    if(confirm){
                        mPresenter.deleteOrder(orderNo, OrderType.GLOBAL_TYPE);
                    }
                }).show();
            });
            tvOrderDetailCenter.setVisibility(View.VISIBLE);
            tvOrderDetailCenter.setText("查看物流");
            tvOrderDetailCenter.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, LogisticsActivity.class);
                intent.putExtra(LogisticsActivity.ORDER_NO, orderNo);
                intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
                ActivityUtils.startActivity(intent);
            });
        }
        //订单详情获取到订单状态后通知订单列表更新状态
        OrderStateChangeEvent event = new OrderStateChangeEvent(orderNo, type);
        EventBus.getDefault().post(event);
    }

    /**
     * 根据不同订单类型绑定不同数据
     */
    private void bindRvData(GlobalBuyerOrderDetailBean detailBean) {
        List<KeyValueBean> orderInfo = new ArrayList<>();
        //订单状态；不传数据-全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
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
        }else if(type == 7){//待确认
            orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
            orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
            orderInfo.add(new KeyValueBean("付款时间:", detailBean.getPayTime()));
        }else if(type == 11){
            orderInfo.add(new KeyValueBean("订单编号:", detailBean.getOrderNo()));
            orderInfo.add(new KeyValueBean("创建时间:", detailBean.getOrderTime()));
            orderInfo.add(new KeyValueBean("付款时间:", detailBean.getPayTime()));
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
        if(model.getData() != null){
            mustChooseWug = model.getData().getAlwaysSelectMg();
            orderDetailBean = model.getData();
            type=orderDetailBean.getState();
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
            //用户停留该页面倒计时结束之后重新刷新页面
            mPresenter.getOrderDetail(orderNo);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOtherPaySuccess(OtherPaySuccessBean otherPaySuccessBean){
        Logger.e("代付收到消息");
        if(orderDetailBean.getOrderNo().equals(otherPaySuccessBean.getOrderNo())){
            setShowDialog(false);
            Logger.e("代付刷新消息");
            mPresenter.getOrderDetail(otherPaySuccessBean.getOrderNo());
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
        isCancel = true;
        ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
        tvOrderState.setText("订单已取消");
        tvReceipt.setVisibility(View.GONE);
        tvOrderDetailClose.setVisibility(View.GONE);
        tvOrderDetailLeft.setVisibility(View.GONE);
        tvOrderDetailCenter.setVisibility(View.GONE);
        tvOrderDetailRight.setVisibility(View.VISIBLE);
        tvOrderDetailRight.setText("删除订单");
//        mHandler.removeMessages(TIME_DESC);
        EventBus.getDefault().post(new RefreshOrderListEvent(false, false, orderNo));
        tvOrderDetailRight.setOnClickListener(v -> {
            //删除订单
            mPresenter.deleteOrder(orderNo, OrderType.GLOBAL_TYPE);
        });
        type = 4;
        if(orderDetailBean != null){
            bindRvData(orderDetailBean);
        }
    }

    @Override
    public void onCompleteOrderSuccess(BaseModel<String> model) {
        ToastUtils.showShort("确认收货成功");
        //修改页面状态
        isCancel = true;
        tvReceipt.setVisibility(View.GONE);
        ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
        tvOrderState.setText("订单已完成");
        tvOrderDetailClose.setVisibility(View.GONE);
        tvOrderDetailLeft.setVisibility(View.GONE);
        tvOrderDetailCenter.setVisibility(View.GONE);
        tvOrderDetailRight.setVisibility(View.VISIBLE);
        tvOrderDetailRight.setText("删除订单");
//        mHandler.removeMessages(TIME_DESC);
        EventBus.getDefault().post(new RefreshOrderListEvent(false, true, orderNo));
        tvOrderDetailRight.setOnClickListener(v -> {
            //删除订单
            mPresenter.deleteOrder(orderNo, OrderType.GLOBAL_TYPE);
        });
        type = 5;
        if(orderDetailBean != null){
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

    /**
     * 删除订单成功回调
     */
    @Override
    public void onDeleteSuccess(BaseModel<String> model) {
        ToastUtils.showShort("订单删除成功");
        //关闭页面，刷新列表数据
        EventBus.getDefault().post(new RefreshOrderListEvent(true, false, orderNo));
        finish();
    }

    @Override
    public void onModifyOrderTypeSuccess(BaseModel<ShareBean> model) {
        if(sellType == 1){
            com.lljjcoder.style.citylist.Toast.ToastUtils.showShortToast(mContext, "申请发货成功");
        }else if(sellType == 2){
            if(shareModel == ShareModel.MODEL_SELL){
                new SellSuccessDialog(mContext, R.style.mydialog, () -> {
                    //跳转到吾G页面
                    ActivityUtils.startActivity(WuGMainActivity.class);
                }).show();
            }else if(shareModel == ShareModel.MODEL_SHARE && model.getData() != null){
                new DialogSellShare(mContext, model.getData()).show();
            }else if(shareModel == ShareModel.MODEL_BOTH && model.getData() != null){
                DialogSellShare dialogSellShare = new DialogSellShare(mContext, model.getData());
                dialogSellShare.setShowTip(true);
                dialogSellShare.show();
            }
        }
        shareModel = 0;
        sellType = 0;
        //修改订单状态寄卖或者发货
        mPresenter.getOrderDetail(orderNo);
    }

    /**
     * 绑定地址信息
     */
    private void bindAddress(AddressBean address) {
        if(address != null){
            tvOrderDetailName.setText(address.consigneeName);
            tvOrderDetailPhone.setText(address.consigneeMobile);
            tvOrderDetailAddress.setText(XiKouUtils.getAddress(mContext, address));
        }
    }

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
