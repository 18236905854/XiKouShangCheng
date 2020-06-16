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
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.KeyValueBean;
import com.xk.mall.model.entity.SellOrderDetailBean;
import com.xk.mall.model.eventbean.FinishDetailEventBean;
import com.xk.mall.model.eventbean.OrderStateChangeEvent;
import com.xk.mall.model.eventbean.OtherPaySuccessBean;
import com.xk.mall.model.impl.SellOrderDetailViewImpl;
import com.xk.mall.presenter.SellOrderDetailPresenter;
import com.xk.mall.utils.DateToolUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.MeiQiaUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.TimeTools;
import com.xk.mall.utils.XiKouUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: SellOrderDetailActivity
 * @Description: 寄卖订单详情页面
 * @Author: 卿凯
 * @Date: 2019/10/15/015 18:09
 * @Version: 1.0
 */
public class SellOrderDetailActivity extends BaseActivity<SellOrderDetailPresenter> implements SellOrderDetailViewImpl {
    private static final int TIME_DESC =1000 ;
    @BindView(R.id.iv_order_state)
    ImageView ivOrderState;//订单状态图片
    @BindView(R.id.tv_order_detail_state)
    TextView tvOrderState;//订单状态文字
    @BindView(R.id.tv_order_detail_close)
    TextView tvOrderDetailClose;//砍价成功之后自动关闭订单文字
    //    @BindView(R.id.count_order_detail)
//    CountdownView countdownView;//未付款自动关闭订单倒计时
    @BindView(R.id.iv_buyer_logo)
    ImageView ivBuyerLogo;//买家用户头像
    @BindView(R.id.tv_sell_tip)
    TextView tvSellTip;//订单提示
    @BindView(R.id.tv_order_detail_name)
    TextView tvOrderDetailName;//订单收货人姓名
    @BindView(R.id.tv_order_detail_phone)
    TextView tvOrderDetailPhone;//订单收货人电话
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
    @BindView(R.id.tv_order_detail_remarks)
    TextView tvRemarks;//订单备注
    private int type = 0;//订单类型  全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功
    public static String ORDER_TYPE = "order_type";
    /**intent传递过来的订单号的key*/
    public static final String ORDER_NO = "order_no";
    private SellOrderDetailBean orderDetailBean;//订单详情对象
    private String orderNo;//订单号

    private long chaTime;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_DESC:
                    chaTime = chaTime - 1000;
                    if (chaTime > 0) {
                        mHandler.sendEmptyMessageDelayed(TIME_DESC, 1000);
//                        tvOrderDetailClose.setText("29分自动关闭");
                        tvOrderDetailClose.setText(TimeTools.getCountTimeByLongZh(chaTime)+"后自动关闭");
                    } else {
                        //用户停留该页面倒计时结束之后重新刷新页面
                        mPresenter.getOrderDetail(orderNo);
                    }
                    break;
            }
        }
    };

    @Override
    protected SellOrderDetailPresenter createPresenter() {
        return new SellOrderDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sell_order_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("订单详情");
        setRightDrawable(R.drawable.wug_kefu);
        setOnRightIconClickListener(v -> {
            MeiQiaUtil.initMeiqiaSDK(mContext);
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        type = intent.getIntExtra(ORDER_TYPE, 0);
        orderNo = intent.getStringExtra(ORDER_NO);
        mPresenter.getOrderDetail(orderNo);

    }

    @OnClick(R.id.tv_order_detail_copy)
    public void onClick() {
        PriceUtil.copyOperation(mContext,orderNo);
    }

    /**
     * 根据订单状态不同显示不同数据
     * @param type 订单状态
     */
    private void bindState(int type) {
        if(type == 1){//待付款
            //计算时间倒计时
            String orderTime=orderDetailBean.getOrderTime();
            long currentTime=System.currentTimeMillis();
            long validTime=orderDetailBean.getPayInvalidTime() * 60 * 1000;
            long endPayTime= DateToolUtils.strToDateLong(orderTime).getTime()+validTime;
            Log.e(TAG, "onGetOrderDetailSuc:endTime== "+endPayTime );
            if(currentTime > endPayTime){
                tvOrderState.setText("下单成功，支付已过期");
                tvOrderDetailClose.setVisibility(View.GONE);
            }else{//开始倒计时
                chaTime = endPayTime - currentTime;
                mHandler.sendEmptyMessage(TIME_DESC);
                tvOrderState.setText("下单成功，等待买家付款");
                tvOrderDetailClose.setVisibility(View.VISIBLE);
            }
            tvSellTip.setVisibility(View.GONE);
            ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);
        }else if(type == 2){//待发货
            ivOrderState.setImageResource(R.drawable.ic_order_detail_state_pay);
            tvOrderState.setText("已付款，等待卖家发货");
            tvSellTip.setVisibility(View.VISIBLE);
            tvSellTip.setText("商品将由" + orderDetailBean.getMerchantName() + "发货给买家");
        }else if(type == 3){//待收货
            ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);
            tvOrderState.setText("已发货，等待收货");
            tvSellTip.setVisibility(View.GONE);
            tvSellTip.setText("买家收货后货款将打入您的钱包");
        }else if(type == 4){//已取消
            ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
            tvOrderState.setText("交易取消");
            tvSellTip.setVisibility(View.GONE);
        }else if(type == 5){//已完成
            ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
            tvOrderState.setText("交易完成");
            tvSellTip.setVisibility(View.GONE);
            tvSellTip.setText("货款已打入您的钱包");
        }else if(type == 6){//已关闭
            ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
            tvOrderState.setText("交易关闭");
            tvSellTip.setVisibility(View.GONE);
        } else if(type == 7){//待确认
            ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
            tvOrderState.setText("订单待确认");

        }else if(type == 11){//已寄卖
            ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
            tvOrderState.setText("已寄卖");
            llOrderDetail.setVisibility(View.VISIBLE);
        }else if(type == 15){//交易完结
            ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
            tvOrderState.setText("交易完结");
            tvSellTip.setVisibility(View.GONE);
            tvSellTip.setText("货款已打入您的钱包");
        }
        //订单详情获取到订单状态后通知订单列表更新状态
        OrderStateChangeEvent event = new OrderStateChangeEvent(orderNo, type);
        EventBus.getDefault().post(event);
    }

    /**
     * 根据不同订单类型绑定不同数据
     */
    private void bindRvData(SellOrderDetailBean detailBean) {
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
    public void onGetOrderDetailSuccess(BaseModel<SellOrderDetailBean> model) {
        if(model.getData() != null){
            orderDetailBean = model.getData();
            type=orderDetailBean.getState();
            SellOrderDetailBean.GoodsVoBean goodsVo = orderDetailBean.getGoodsVo();
            tvOrderListName.setText(goodsVo.getGoodsName());
            GlideUtil.showRadius(mContext, goodsVo.getGoodsImageUrl(), 2, ivOrderListLogo);
            GlideUtil.showUserCircle(mContext, orderDetailBean.getBuyerHeadImage(), ivBuyerLogo);
            tvOrderListSku.setText(goodsVo.getCommodityModel() + " " + goodsVo.getCommoditySpec());
            tvOrderListNum.setText("X" + goodsVo.getCommodityQuantity());
            tvOrderListMoney.setText("¥" + PriceUtil.dividePrice(orderDetailBean.getCommoditySalePrice()));//单价
            tvOrderDetailRealPrice.setText(getResources().getString(R.string.money) + PriceUtil.dividePrice(orderDetailBean.getPayAmount()));
            tvOrderDetailPostage.setText(getResources().getString(R.string.money) + PriceUtil.dividePrice(orderDetailBean.getPostage()));
            tvOrderDetailName.setText(orderDetailBean.getBuyerNickName());
            String phone = orderDetailBean.getBuyerAccount();
            if(phone.length() <= 4){
                tvOrderDetailPhone.setText(orderDetailBean.getBuyerAccount());
            }else {
                //设为*号
                int center = phone.length() / 2;//11位数除以2 = 5
                int start = center - 2;//截取末位不包含，所以需要多向前移一位
                int end = center + 2;
                String stringBuilder = phone.substring(0, start) +
                        "****" + phone.substring(end);
                tvOrderDetailPhone.setText(stringBuilder);
            }
            String remarks = orderDetailBean.getRemarks() == null? "" : orderDetailBean.getRemarks();
            if(!XiKouUtils.isNullOrEmpty(remarks)){
                tvRemarks.setText(remarks);
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
