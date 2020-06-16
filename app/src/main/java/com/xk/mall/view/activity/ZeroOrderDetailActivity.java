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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.KeyValueBean;
import com.xk.mall.model.entity.ZeroOrderBean;
import com.xk.mall.model.entity.ZeroOrderDetailBean;
import com.xk.mall.model.eventbean.FinishDetailEventBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.ZeroOrderDetailImpl;
import com.xk.mall.model.request.CancelOrderRequestBody;
import com.xk.mall.model.request.DeleteOrderRequestBody;
import com.xk.mall.presenter.ZeroOrderDetailPresenter;
import com.xk.mall.utils.DateToolUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.MeiQiaUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.TimeTools;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.MerchantPhoneDialog;
import com.xk.mall.view.widget.OrderTipDialog;
import com.xk.mall.view.widget.UIHandler;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
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
 *
 * 0元拍订单详情页面
 */
public class ZeroOrderDetailActivity extends BaseActivity<ZeroOrderDetailPresenter> implements ZeroOrderDetailImpl {
    private static final int TIME_DESC = 1200;
    @BindView(R.id.iv_order_state)
    ImageView ivOrderState;//订单状态图片
    @BindView(R.id.tv_order_detail_state)
    TextView tvOrderState;//订单状态文字
    @BindView(R.id.tv_order_detail_close)
    TextView tvOrderDetailClose;//砍价成功之后自动关闭订单文字
    @BindView(R.id.ll_address_out_range)
    LinearLayout llOutRange;//超出范围
    @BindView(R.id.ll_order_detail_have_address)
    LinearLayout llHaveAddress;//地址信息
    @BindView(R.id.rl_no_address)
    RelativeLayout rlNoAddress;//没有地址
    @BindView(R.id.iv_change_address)
    ImageView ivChangeAddress;//箭头
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
    TextView tvRight;//最右边按钮
    @BindView(R.id.tv_order_detail_center)
    TextView tvCenter;//中间按钮
    @BindView(R.id.tv_order_receipt_time)
    TextView tvReceipt;//最左边按钮
    @BindView(R.id.tv_sku)
    TextView tvSku;
    @BindView(R.id.tv_order_detail_remarks)
    TextView tvRemarks;//订单备注
    @BindView(R.id.rl_order_remarks)
    RelativeLayout rlOrderRemarks;
    private int type = 0;//订单类型  0全部、1待付款、2待发货、3待收货、4已取消、5 已完成
    public static final String ORDER_NO = "order_no";
    public static final String POSITION = "position";
    private boolean isOutRange;//订单地址是否超出范围
    private boolean hasAddress;//是否有地址
    private AddressBean defaultAddress;//默认地址

    private String orderNo;
    private int listPosition;//列表下标
    private ZeroOrderDetailBean orderDetailBean;
    private long chaTime;

//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case TIME_DESC:
//                    chaTime = chaTime - 1000;
//                    if (chaTime > 0) {
//                        tvRight.setClickable(true);
//
//                        mHandler.sendEmptyMessageDelayed(TIME_DESC, 1000);
////                        tvOrderDetailClose.setText("29分自动关闭");
//                        tvOrderDetailClose.setText(TimeTools.getCountTimeByLongZh(chaTime)+"后自动关闭");
//                    } else {
//                        tvOrderState.setText("竞拍成功，支付已过期");
//                        tvRight.setEnabled(false);
//                        tvRight.setBackgroundResource(R.drawable.bg_btn_order_noblack);
//                    }
//                    break;
//            }
//        }
//    };

    @Override
    protected ZeroOrderDetailPresenter createPresenter() {
        return new ZeroOrderDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zero_order_detail;
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
        Log.e(TAG, "initData:======== "+orderNo );
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
            tvOrderState.setText("竞拍成功，支付已过期");
            tvRight.setEnabled(false);
            tvRight.setBackgroundResource(R.drawable.bg_btn_order_noblack);
        }
    }

    //获取详情成功
    @Override
    public void onGetOrderDetailSuc(BaseModel<ZeroOrderDetailBean> model) {
        orderDetailBean = model.getData();
        if(orderDetailBean!=null){
            AddressBean address = orderDetailBean.getAddress();
            if(address != null){
                defaultAddress = address;
                hasAddress = true;
                llHaveAddress.setVisibility(View.VISIBLE);
                rlNoAddress.setVisibility(View.GONE);
                tvOrderDetailName.setText(address.consigneeName);
                tvOrderDetailPhone.setText(address.consigneeMobile);
                tvOrderDetailAddress.setText(XiKouUtils.getAddress(mContext, address));
                if(!address.outRange){
                    llOutRange.setVisibility(View.VISIBLE);
                    isOutRange = true;
                }else {
                    isOutRange = false;
                    llOutRange.setVisibility(View.GONE);
                }
            }else {
                defaultAddress = null;
                hasAddress = false;
                llHaveAddress.setVisibility(View.GONE);
                rlNoAddress.setVisibility(View.VISIBLE);
                tvOrderDetailName.setText("");
                tvOrderDetailPhone.setText("");
                tvOrderDetailAddress.setText("");
            }
            tvOrderDetailPostage.setText("¥"+ PriceUtil.dividePrice(orderDetailBean.getPostage()));
            tvOrderDetailRealPrice.setText("¥"+PriceUtil.dividePrice(orderDetailBean.getPayAmount()));
            tvOrderDetailPrice.setText("¥"+ PriceUtil.dividePrice(orderDetailBean.getCommodityAuctionPrice()));
            type=orderDetailBean.getState();
            if(type != 1){
                ivChangeAddress.setVisibility(View.GONE);
            }

            String merchantName = orderDetailBean.getMerchantName();
            if(TextUtils.isEmpty(merchantName)){
                tvOrderListShopName.setText("喜扣商城");
            }else{
                tvOrderListShopName.setText(merchantName);
            }

            ZeroOrderDetailBean.GoodsVoBean goodsVo = orderDetailBean.getGoodsVo();
            GlideUtil.show(mContext, goodsVo.getGoodsImageUrl(), ivOrderListLogo);
            tvOrderListName.setText(goodsVo.getGoodsName());
            tvOrderListMoney.setText("¥" + PriceUtil.dividePrice(orderDetailBean.getCommoditySalePrice()));
            tvOrderListNum.setText("X" + goodsVo.getCommodityQuantity());
            tvSku.setText(goodsVo.getCommodityModel() + " " + goodsVo.getCommoditySpec());
            String remarks = orderDetailBean.getRemarks() == null? "" : orderDetailBean.getRemarks();
            tvRemarks.setText(remarks);
            if(!XiKouUtils.isNullOrEmpty(remarks)){
                rlOrderRemarks.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, RemarksActivity.class);
                    intent.putExtra(RemarksActivity.REMARKS_CONTENT, remarks);
                    intent.putExtra(RemarksActivity.REMARKS_ISEDIT, false);
                    ActivityUtils.startActivity(intent);
                });
            }
            if (type == 1) {//待付款
                ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);
                //计算时间倒计时
                String orderTime=orderDetailBean.getOrderTime();
                long currentTime=System.currentTimeMillis();
                long validTime=orderDetailBean.getPayInvalidTime() * 60 * 1000;
                long endPayTime= DateToolUtils.strToDateLong(orderTime).getTime()+validTime;

                if(currentTime > endPayTime){
                    tvOrderState.setText("已拍中，支付已过期");
                    tvRight.setText("无法支付");
                    tvRight.setEnabled(false);
                    tvRight.setBackgroundResource(R.drawable.bg_btn_order_noblack);
                }else{//开始倒计时
                    chaTime = endPayTime - currentTime;
                    EventBus.getDefault().post(TIME_DESC);
//                    mHandler.sendEmptyMessage(TIME_DESC);
                    tvOrderState.setText("已拍中，等待买家付款");
                    tvOrderDetailClose.setVisibility(View.VISIBLE);
                }

                tvRight.setText("立即付款");
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText("取消订单");
                tvReceipt.setVisibility(View.GONE);

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
                        if(!hasAddress){
                            ToastUtils.showShortToast(mContext, "请先选择地址再付款");
                            return;
                        }
                        if(isOutRange){
                            ToastUtils.showShortToast(mContext, getResources().getString(R.string.address_out_range));
                            return;
                        }
                        mPresenter.verifyUserAddressInfo(orderDetailBean.getReceiptAddressRef());
                    }
                });
                
                bindRvData(type);
            } else if (type == 2) {//待发货
                ivOrderState.setImageResource(R.drawable.ic_order_detail_state_pay);
                tvOrderState.setText("已付款，等待卖家发货");
                tvRight.setText("提醒发货");
                tvCenter.setVisibility(View.GONE);
                tvReceipt.setVisibility(View.GONE);
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.remindShip(orderNo, OrderType.ZERO_TYPE);
                    }
                });
                bindRvData(type);
            } else if (type == 3) {//待收货
                ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);
                tvOrderState.setText("已发货，等待收货");
                tvRight.setText("确认收货");
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText("查看物流");
                tvCenter.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    intent.putExtra(LogisticsActivity.ORDER_NO, orderNo);
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.ZERO_TYPE);
                    ActivityUtils.startActivity(intent);
                });
                tvReceipt.setVisibility(View.VISIBLE);
                tvReceipt.setText("将于" + orderDetailBean.getAutoDeliveryTime() +"自动收货");
                tvRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", getResources().getString(R.string.order_dialog_sure_content),"确定", (dialog, confirm) -> {
                        if(confirm){
                            mPresenter.completeOrder(orderNo, OrderType.ZERO_TYPE);
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

                tvCenter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, LogisticsActivity.class);
                        intent.putExtra(LogisticsActivity.ORDER_NO, orderNo);
                        intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.ZERO_TYPE);
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
            }
        }
    }

    //取消成功
    @Override
    public void onGetCancelOrderSuc(BaseModel<String> model) {
        ToastUtils.showShortToast(mContext, model.getMsg());
        tvReceipt.setVisibility(View.GONE);
        if(tvCenter.getVisibility()==View.VISIBLE){
            tvCenter.setVisibility(View.GONE);
        }

//        mHandler.removeMessages(TIME_DESC);
        tvOrderDetailClose.setVisibility(View.GONE);

        //刷新页面
        mPresenter.getOrderDetail(orderNo);
        //通知列表刷新
        EventBus.getDefault().post(new RefreshOrderListEvent(false,false, orderNo));
    }

    //在该页面跳转支付成功之后，当点击查看详情时，关闭当前页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishDetail(FinishDetailEventBean eventBean){
        finish();
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
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
        ToastUtils.showShortToast(mContext, model.getMsg());
        tvReceipt.setVisibility(View.GONE);
        if(tvCenter.getVisibility()==View.VISIBLE){
            tvCenter.setVisibility(View.GONE);
        }

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

    @Override
    public void onInsertOrUpdateAddressSuccess(BaseModel model) {
        if(model != null && defaultAddress != null){
            hasAddress = true;
            llHaveAddress.setVisibility(View.VISIBLE);
            rlNoAddress.setVisibility(View.GONE);
            tvOrderDetailName.setText(defaultAddress.consigneeName);
            tvOrderDetailPhone.setText(defaultAddress.consigneeMobile);
            tvOrderDetailAddress.setText(XiKouUtils.getAddress(mContext, defaultAddress));
            if(!defaultAddress.outRange){
                llOutRange.setVisibility(View.VISIBLE);
                isOutRange = true;
            }else {
                isOutRange = false;
                llOutRange.setVisibility(View.GONE);
            }
            ToastUtils.showShortToast(mContext, "更新地址成功");
        }else {
            ToastUtils.showShortToast(mContext, "更新地址失败");
        }
    }

    @Override
    public void onVerifyUserAddressInfo(BaseModel<Boolean> model) {
        if(model.getData() != null && model.getData()){
            payOrder(orderDetailBean);
        }else {
            //该订单没有地址
            if(orderDetailBean != null && orderDetailBean.getAddress() != null){
                ToastUtils.showShortToast(mContext, "收货地址有误，请更换地址");
            }else {
                ToastUtils.showShortToast(mContext, "请先选择地址再付款");
            }
        }
    }

    /**
     * 取消订单
     */
    private void cancelOrder(String orderNum){
        CancelOrderRequestBody requestBody=new CancelOrderRequestBody();
        requestBody.setOrderNo(orderNum);
        requestBody.setOrderType(OrderType.ZERO_TYPE);
        mPresenter.cancelOrder(requestBody);
    }

    /**
     * 删除订单
     */
    private void deleteOrder(String orderNum){
        DeleteOrderRequestBody requestBody=new DeleteOrderRequestBody();
        requestBody.setOrderNo(orderNum);
        requestBody.setOrderType(OrderType.ZERO_TYPE);
        mPresenter.deleterOrder(requestBody);
    }

    /**
     * 立即付款
     */
    private void payOrder(ZeroOrderDetailBean orderDetailBean){
        Intent intent=new Intent(this,PayOrderActivity.class);
        intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
        intent.putExtra("goods_name",orderDetailBean.getGoodsVo().getGoodsName());
        intent.putExtra("total_price",orderDetailBean.getPayAmount());
        intent.putExtra("order_number",orderDetailBean.getOrderNo());
        intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.ZERO_TYPE);
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

    /**
     * eventBus接收回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAddress(AddressBean addressBean) {
        Logger.e("接收到地址"+addressBean.address);
        if(addressBean != null){
            //更新订单地址信息
            defaultAddress = addressBean;
            mPresenter.insertOrUpdateAddress(orderNo, addressBean.id);
        }else {
            defaultAddress = null;
            hasAddress = false;
            llHaveAddress.setVisibility(View.GONE);
            rlNoAddress.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tv_order_detail_copy, R.id.ll_order_detail_address})
    public void onClick(View view) {
        if(view.getId() == R.id.ll_order_detail_address && type == 1){
            //跳转选择地址页面
            Intent toAddress = new Intent(mContext, AddressActivity.class);
            toAddress.putExtra("is_xiadan",true);
            startActivity(toAddress);
        }else if(view.getId() == R.id.tv_order_detail_copy){
            PriceUtil.copyOperation(mContext,orderNo);
        }
    }
}
