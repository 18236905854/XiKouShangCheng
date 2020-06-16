package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.GroupOrderDetailBean;
import com.xk.mall.model.entity.KeyValueBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.GroupOrderDetailImpl;
import com.xk.mall.model.request.CancelOrderRequestBody;
import com.xk.mall.model.request.DeleteOrderRequestBody;
import com.xk.mall.presenter.GroupOrderDetailPresenter;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.MeiQiaUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.widget.OrderTipDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName OrderDetailActivity
 * Description 拼团订单详情页面
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class GroupOrderDetailActivity extends BaseActivity<GroupOrderDetailPresenter> implements GroupOrderDetailImpl {
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
    @BindView(R.id.tv_order_detail_price)
    TextView tvOrderDetailPrice;//订单产品砍后价格
    @BindView(R.id.tv_order_detail_postage)
    TextView tvOrderDetailPostage;//订单产品邮费
    @BindView(R.id.tv_order_detail_real_price)
    TextView tvOrderDetailRealPrice;//订单产品实付价格
    @BindView(R.id.tv_sku)
    TextView tvSku;
    @BindView(R.id.tv_order_detail_copy)
    TextView tvOrderDetailCopy;//复制按钮
    @BindView(R.id.rv_order_detail)
    RecyclerView rvOrderDetail;//订单详情
    @BindView(R.id.tv_order_detail_right)
    TextView tvRight;//最右边按钮
    @BindView(R.id.tv_order_detail_center)
    TextView tvCenter;//中间按钮
    @BindView(R.id.tv_order_detail_left)
    TextView tvLeft;//最左边按钮
    private String orderNo;
    public static final String ORDER_NO = "order_no";
    GroupOrderDetailBean orderDetailBean;

    @Override
    protected GroupOrderDetailPresenter createPresenter() {
        return new GroupOrderDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_order_detail;
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
        orderNo = intent.getStringExtra(ORDER_NO);
        mPresenter.getGroupOrderDetail(orderNo);
//        type = intent.getIntExtra(ORDER_TYPE, 0);
        rvOrderDetail.setLayoutManager(new LinearLayoutManager(mContext));


    }

    /**
     * 根据不同订单类型绑定不同数据
     *
     * @param type 1待付款、2待发货、3待收货、5已完成、5已取消
     */
    private void bindRvData(int type) {
        List<KeyValueBean> orderInfo = new ArrayList<>();
        if (type == 1) {//待付款
            orderInfo.add(new KeyValueBean("订单编号:", orderNo));
            orderInfo.add(new KeyValueBean("创建时间:", orderDetailBean.getOrderTime()));
        } else if (type == 2) {//待发货
            orderInfo.add(new KeyValueBean("订单编号:", orderNo));
            orderInfo.add(new KeyValueBean("交易流水号:",orderDetailBean.getExternalPlatformNo()));
            orderInfo.add(new KeyValueBean("创建时间:", orderDetailBean.getOrderTime()));
            orderInfo.add(new KeyValueBean("付款时间:", (String) orderDetailBean.getPayTime()));
        } else if (type == 3) {//待收货
            orderInfo.add(new KeyValueBean("订单编号:", orderNo));
            orderInfo.add(new KeyValueBean("交易流水号:", orderDetailBean.getExternalPlatformNo()));
            orderInfo.add(new KeyValueBean("创建时间:", orderDetailBean.getOrderTime()));
            orderInfo.add(new KeyValueBean("付款时间:", (String) orderDetailBean.getPayTime()));
            orderInfo.add(new KeyValueBean("发货时间:", (String) orderDetailBean.getShipTime()));
        } else if (type == 5) {//已完成
            orderInfo.add(new KeyValueBean("订单编号:", orderNo));
            orderInfo.add(new KeyValueBean("交易流水号:",orderDetailBean.getExternalPlatformNo()));
            orderInfo.add(new KeyValueBean("创建时间:", orderDetailBean.getOrderTime()));
            orderInfo.add(new KeyValueBean("付款时间:", (String) orderDetailBean.getPayTime()));
            orderInfo.add(new KeyValueBean("发货时间:", (String) orderDetailBean.getShipTime()));
            orderInfo.add(new KeyValueBean("成交时间:", "2019-06-22 13:11:30"));
        } else if (type == 4) {//已取消
            orderInfo.add(new KeyValueBean("订单编号:", orderNo));
            orderInfo.add(new KeyValueBean("交易流水号:", orderDetailBean.getExternalPlatformNo()));
            orderInfo.add(new KeyValueBean("取消时间:", "2019-06-22 13:11:30"));
        }
        OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(mContext, R.layout.order_info_item, orderInfo);
        rvOrderDetail.setAdapter(orderInfoAdapter);
    }

    //获取订单详情  //订单类型  1待付款、8.进行中 ，2待发货、3待收货 4.已取消 5，已完成 6 已关闭 7 待确认 8待成团 9成团成功 10拼团失败
    @Override
    public void onGetOrderDetailSuc(BaseModel<GroupOrderDetailBean> model) {
        orderDetailBean = model.getData();
        GroupOrderDetailBean.AddressBean address = orderDetailBean.getAddress();
        tvOrderDetailName.setText(address.getConsigneeName());
        tvOrderDetailPhone.setText(address.getConsigneeMobile());
        tvOrderDetailAddress.setText(address.getProvinceName() + address.getCityName() + address.getAreaName() + address.getAddress());
        tvOrderDetailPostage.setText(""+PriceUtil.dividePrice(orderDetailBean.getPostage()));
        tvOrderDetailRealPrice.setText(""+PriceUtil.dividePrice(orderDetailBean.getPayAmount()));
        if (orderDetailBean != null) {
            int type = orderDetailBean.getState();
            if (type == 1) {//待付款
                ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);
                tvOrderState.setText("拼团成功，等待买家付款");
                tvOrderDetailClose.setText("29分自动关闭");
                tvOrderDetailClose.setVisibility(View.VISIBLE);
                tvRight.setText("立即付款");
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText("取消订单");

                tvCenter.setOnClickListener(v -> cancelOrder(orderNo));

                tvRight.setOnClickListener(v -> payOrder(orderDetailBean));
                bindRvData(type);
            } else if (type == 2) {//待发货
                ivOrderState.setImageResource(R.drawable.ic_order_detail_state_pay);
                tvOrderState.setText("已付款，等待卖家发货");
                tvRight.setText("提醒发货");
                bindRvData(type);
                tvRight.setOnClickListener(v -> mPresenter.remindShip(orderNo, OrderType.GROUP_TYPE));
            } else if (type == 3) {//待收货
                ivOrderState.setImageResource(R.drawable.ic_order_detail_cut_success);
                tvOrderState.setText("已发货，等待收货");
                tvRight.setText("确认收货");
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText("查看物流");
                tvLeft.setVisibility(View.GONE);
                bindRvData(type);

                tvCenter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ToastUtils.showShortToast(mContext,"查看物流");
                    }
                });

                tvRight.setOnClickListener(v -> mPresenter.completeOrder(orderNo, OrderType.GROUP_TYPE));

            } else if (type == 5) {//已完成
                ivOrderState.setImageResource(R.drawable.ic_order_detail_success);
                tvOrderState.setText("交易完成");
                tvRight.setText("删除订单");
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText("查看物流");
                bindRvData(type);

                tvRight.setOnClickListener(v -> deleteOrder(orderNo));

                tvCenter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ToastUtils.showShortToast(mContext,"查看物流");
                    }
                });
            } else if (type == 4) {//已取消
                ivOrderState.setImageResource(R.drawable.ic_order_state_cancel);
                tvOrderState.setText("订单已取消");
                tvRight.setText("删除订单");
                bindRvData(type);

                tvRight.setOnClickListener(v -> deleteOrder(orderNo));
            }

            String merchantName=orderDetailBean.getMerchantName();
            if (TextUtils.isEmpty(merchantName)) {
                tvOrderListShopName.setText("喜扣商城");
            } else {
                tvOrderListShopName.setText(merchantName);
            }
            GroupOrderDetailBean.GoodsVoBean goodsVo = orderDetailBean.getGoodsVo();
            GlideUtil.show(mContext, goodsVo.getGoodsImageUrl(), ivOrderListLogo);
            tvOrderListName.setText(goodsVo.getGoodsName());
            tvOrderListMoney.setText("¥" + PriceUtil.dividePrice(orderDetailBean.getPayAmount()));
            tvOrderListNum.setText("X" + goodsVo.getCommodityQuantity());
            tvSku.setText(goodsVo.getCommodityModel() + " " + goodsVo.getCommoditySpec());
        }
    }

    //取消订单
    @Override
    public void onGetCancelOrderSuc(BaseModel<String> model) {
        if(tvLeft.getVisibility()==View.VISIBLE){
            tvLeft.setVisibility(View.GONE);
        }
        if(tvCenter.getVisibility()==View.VISIBLE){
            tvCenter.setVisibility(View.GONE);
        }
        //刷新页面
        mPresenter.getGroupOrderDetail(orderNo);
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
        if(tvLeft.getVisibility()==View.VISIBLE){
            tvLeft.setVisibility(View.GONE);
        }
        if(tvCenter.getVisibility()==View.VISIBLE){
            tvCenter.setVisibility(View.GONE);
        }
        //刷新页面
        mPresenter.getGroupOrderDetail(orderNo);
        //通知列表刷新
        EventBus.getDefault().post(new RefreshOrderListEvent(false,true, orderNo));
    }

    @Override
    public void onGetDeleteOrderSuc(BaseModel<String> model) {
            finish();
            //通知列表刷新
        EventBus.getDefault().post(new RefreshOrderListEvent(true,false,orderNo));
    }

    @OnClick(R.id.tv_order_detail_copy)
    public void onClick() {
        PriceUtil.copyOperation(mContext,orderNo);
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
     * 立即付款
     */
    private void payOrder(GroupOrderDetailBean orderDetailBean){
        Intent intent=new Intent(this,PayOrderActivity.class);
        intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
        intent.putExtra(PayOrderActivity.GOODS_NAME, orderDetailBean.getGoodsVo().getGoodsName());
        intent.putExtra(PayOrderActivity.TOTAL_PRICE, orderDetailBean.getPayAmount() + orderDetailBean.getPostage());
        intent.putExtra(PayOrderActivity.ORDER_NUMBER, orderDetailBean.getOrderNo());
        intent.putExtra(PayOrderActivity.ORDER_TYPE,OrderType.GROUP_TYPE);
        startActivity(intent);
    }

    /**
     * 取消订单
     */
    private void cancelOrder(String orderNum){
        CancelOrderRequestBody requestBody=new CancelOrderRequestBody();
        requestBody.setOrderNo(orderNum);
        requestBody.setOrderType(OrderType.GROUP_TYPE);
        mPresenter.cancelGroupOrder(requestBody);
    }

    /**
     * 删除订单
     */
    private void deleteOrder(String orderNum){
        DeleteOrderRequestBody requestBody=new DeleteOrderRequestBody();
        requestBody.setOrderNo(orderNum);
        requestBody.setOrderType(OrderType.GROUP_TYPE);
        mPresenter.deleterGroupOrder(requestBody);
    }

}
