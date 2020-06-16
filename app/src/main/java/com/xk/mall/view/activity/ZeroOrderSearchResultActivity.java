package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.entity.OrderStateBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ZeroOrderBean;
import com.xk.mall.model.eventbean.PaySuccessBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.WuGOrderViewImpl;
import com.xk.mall.model.impl.ZeroOrderImpl;
import com.xk.mall.model.request.CancelOrderRequestBody;
import com.xk.mall.model.request.DeleteOrderRequestBody;
import com.xk.mall.presenter.WuGOrderPresenter;
import com.xk.mall.presenter.ZeroOrderPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.OrderTipDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName OrderSearchResultActivity
 * Description 订单搜索结果页
 * Author
 * Date
 * Version V1.0
 */
public class ZeroOrderSearchResultActivity extends BaseActivity<ZeroOrderPresenter> implements ZeroOrderImpl {
    private static final String TAG = "OrderSearchResultActivity";
    public static final String SEARCH_NAME = "search_name";
    public static final String ORDER_TYPE = "order_type";//订单类型
    public static final String LEFT_PRICE = "left_price";//最低金额
    public static final String RIGHT_PRICE = "right_price";//最低金额
    public static final String TIME_FLAG = "time_flag";//最低金额
    @BindView(R.id.et_search)
    EditText editSearch;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.rv_order_search_result)
    RecyclerView recycleView;
    @BindView(R.id.state_view_order)
    MultiStateView multiStateView;
    @BindView(R.id.refresh_order_filter)
    SmartRefreshLayout smartRefreshLayout;
    private int state = 0;//订单类型  0全部、1待付款、2待发货、3待收货
    private int leftPrice;
    private int rightPrice;
    private String searchName;
    private int timeFlag;//时间类型
    private int page = 1;
    private String account;
    private List<OrderStateBean.ResultBean> listData = new ArrayList<>();
    private OrderListAdapter orderListAdapter;
    private int operationIndex;//操作的position


    @Override
    protected ZeroOrderPresenter createPresenter() {
        return new ZeroOrderPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_search_result;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @Override
    protected void initData() {
        searchName = getIntent().getStringExtra(SEARCH_NAME);
        editSearch.setText(searchName);
        recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        int localLeftPrice = getIntent().getIntExtra(LEFT_PRICE, 0);
        if(localLeftPrice != 0){
            leftPrice = localLeftPrice;
        }
        int localRightPrice = getIntent().getIntExtra(RIGHT_PRICE, 0);
        if(localRightPrice != 0){
            rightPrice = localRightPrice;
        }
        timeFlag = getIntent().getIntExtra(TIME_FLAG, 0);
        account = SPUtils.getInstance().getString(Constant.USER_MOBILE);
        MyApplication.getInstance().addActivity(ZeroOrderSearchResultActivity.this);
        //进入退出效果 注意这里 创建的效果对象是 Fade()
        getWindow().setEnterTransition(new Fade().setDuration(200));
        getWindow().setExitTransition(new Fade().setDuration(200));
        orderListAdapter = new OrderListAdapter(mContext, R.layout.zero_order_list_item, listData);
        recycleView.setAdapter(orderListAdapter);
        searchOrder();
        smartRefreshLayout.setEnableRefresh(false);
        editSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(editSearch.getText().toString().trim())) {
                searchName = editSearch.getText().toString().trim();
                leftPrice = 0;
                rightPrice = 0;
                searchOrder();
            }
            return false;
        });

        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            searchOrder();
        });

        orderListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(mContext, ZeroOrderDetailActivity.class);
                intent.putExtra(ZeroOrderDetailActivity.ORDER_NO, listData.get(position).getOrderNo());
                intent.putExtra(ZeroOrderDetailActivity.POSITION,position);
                ActivityUtils.startActivity(intent);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    /**
     * eventBus接收回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAddress(RefreshOrderListEvent event) {
        Logger.e("接收到刷新订单号"+ event.getOrderNo());
        Logger.e("接收到刷新订单号isDelete"+event.getIsDelete());
        if(!TextUtils.isEmpty(event.getOrderNo())){
            for (OrderStateBean.ResultBean listDatum : listData) {
                if(listDatum.getOrderNo().equals(listDatum.getOrderNo())){
                    if(event.getIsDelete()){//删除订单
                        listData.remove(listDatum);
                    }else{
                        listDatum.setState(4);//取消状态
                    }
                    orderListAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    /**
     * 搜索订单
     */
    private void searchOrder(){
        mPresenter.getAllOrderList(searchName,account,state,timeFlag,leftPrice,rightPrice,page,Constant.limit);
    }

    @OnClick({R.id.tv_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:
                if (Build.VERSION.SDK_INT >= 21) {
                    MyApplication.getInstance().closeActivity();
                    finishAfterTransition();
                } else {
                    MyApplication.getInstance().closeActivity();
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishAfterTransition();
            MyApplication.getInstance().closeActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //获取订单成功
    @Override
    public void onGetOrderListDataSuc(BaseModel<OrderStateBean> model) {
        smartRefreshLayout.finishRefresh(1500);
        smartRefreshLayout.finishLoadMore(1500);

        if(model.getData() != null && model.getData().getResult() != null){
            listData.addAll(model.getData().getResult());
            orderListAdapter.notifyDataSetChanged();
            if(model.getData().getResult().size() < Constant.limit){
                smartRefreshLayout.setEnableLoadMore(false);
            }else {
                smartRefreshLayout.setEnableLoadMore(true);
            }
            if(page == 1 && model.getData().getResult().size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }

    }

    //取消订单成功
    @Override
    public void onGetCancelOrderSuc(BaseModel<String> model) {
        listData.get(operationIndex).setState(4);//已取消
        orderListAdapter.notifyDataSetChanged();
        //通知列表刷新
        EventBus.getDefault().post(new RefreshOrderListEvent(false,false, listData.get(operationIndex).getOrderNo()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaySuccess(PaySuccessBean otherPaySuccessBean){
        if(listData != null){
            for(OrderStateBean.ResultBean orderBean : listData){
                if(orderBean.getOrderNo().equals(otherPaySuccessBean.getOrderNo())){
                    orderBean.setState(2);
                }
            }
            orderListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRemindShipSuccess(BaseModel model) {
        ToastUtils.showShortToast(mContext, "提醒发货成功");
    }

    @Override
    public void onExtendTheTimeSuccess(BaseModel model) {
        ToastUtils.showShortToast(mContext, "提醒发货成功");
    }

    @Override
    public void onCompleteOrderSuccess(BaseModel<String> model) {
        listData.get(operationIndex).setState(5);//已取消
        orderListAdapter.notifyDataSetChanged();
        //通知列表刷新
        EventBus.getDefault().post(new RefreshOrderListEvent(false,true, listData.get(operationIndex).getOrderNo()));
    }

    //删除订单成功
    @Override
    public void onGetDeleteOrderSuc(BaseModel<String> model) {
        OrderStateBean.ResultBean orderBean = listData.get(operationIndex);
        listData.remove(operationIndex);
        orderListAdapter.notifyItemRemoved(operationIndex);
        orderListAdapter.notifyDataSetChanged();
        com.lljjcoder.style.citylist.Toast.ToastUtils.showShortToast(mContext,"删除成功");
        //通知列表刷新
        EventBus.getDefault().post(new RefreshOrderListEvent(true,false, orderBean.getOrderNo()));
        if(listData.size()==0){
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onVerifyUserAddressInfo(BaseModel<Boolean> model) {
        if(model.getData() != null && !model.getData()){
            if(operationIndex != -1){
                OrderStateBean.ResultBean orderBean = listData.get(operationIndex);
                toPay(orderBean);
            }
        }else {
            //该订单没有地址
            if(operationIndex != -1){
                OrderStateBean.ResultBean orderBean = listData.get(operationIndex);
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "请先选择地址再付款","知道了", (dialog, confirm) -> {
                    Intent intent = new Intent(mContext, ZeroOrderDetailActivity.class);
                    intent.putExtra(ZeroOrderDetailActivity.ORDER_NO, orderBean.getOrderNo());
                    intent.putExtra(ZeroOrderDetailActivity.POSITION,operationIndex);
                    ActivityUtils.startActivity(intent);
                }).show();
            }
        }
    }

    @Override
    public void onModifyOrderTypeSuccess(BaseModel<ShareBean> model) {

    }

    private int getOrderBeanPosition(OrderStateBean.ResultBean orderBean){
        int result = -1;
        for(int i = 0; i < listData.size(); i++){
            OrderStateBean.ResultBean orderBean1 = listData.get(i);
            if(orderBean1.getOrderNo().equals(orderBean.getOrderNo())){
                result = i;
            }
        }
        return result;
    }

    class OrderListAdapter extends CommonAdapter<OrderStateBean.ResultBean> {

        public OrderListAdapter(Context context, int layoutId, List<OrderStateBean.ResultBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, OrderStateBean.ResultBean orderBean, int position) {
            holder.setText(R.id.tv_order_list_name, orderBean.getGoodsName());
//            holder.setText(R.id.tv_order_list_money, getResources().getString(R.string.money) + CalculateUtils.subDoubleHundred(orderBean.getPayAmount()));
//            holder.setText(R.id.tv_order_list_num, "x" + orderBean.getCommodityQuantity());
            TextView tvMarketPrice=holder.getView(R.id.tv_market_price);
//            TextView tvPaiPrice=holder.getView(R.id.tv_pai_price);
//            TextView tvChuJiaNumber=holder.getView(R.id.tv_wochu_price_num);
            TextView tvOrderTotal=holder.getView(R.id.tv_order_total);
            TextView tvPostage=holder.getView(R.id.tv_postage);
            StringBuilder sbContent = new StringBuilder();
            sbContent.append("销售价:¥").append(PriceUtil.dividePrice(orderBean.getCommoditySalePrice()));
//            if(orderBean.getCommodityAuctionPrice()!=0){
//                sbContent.append("  拍卖价:¥").append(PriceUtil.dividePrice(orderBean.getCommodityAuctionPrice()));
//            }else{
//                sbContent.append("  拍卖价:¥0");
//            }
//            sbContent.append("  我出价:").append(orderBean.getTimesNum()).append("次");
            tvMarketPrice.setText(sbContent.toString());
            tvOrderTotal.setText("订单总额:¥"+PriceUtil.dividePrice(orderBean.getPayAmount()));
            if(orderBean.getPostage() == 0){
                tvPostage.setText("免运费");
            }else {
                tvPostage.setText("运费:¥" + PriceUtil.dividePrice(orderBean.getPostage()));
            }

            holder.setText(R.id.tv_order_list_sku,orderBean.getCommodityModel()+" "+orderBean.getCommoditySpec());
            TextView tvMerchantName = holder.getView(R.id.tv_order_list_shopName);
//            if (TextUtils.isEmpty(orderBean.getMerchantName())) {
//                tvMerchantName.setText("喜扣商城");
//            } else {
//                tvMerchantName.setText(orderBean.getMerchantName());
//            }
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
                        if(confirm){
                            operationIndex=getOrderBeanPosition(orderBean);
                            cancelOrder(orderBean);
                        }
                    }).show();
                });

                //立即付款
                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if(XiKouUtils.isNullOrEmpty(orderBean.getReceiptAddressRef())){
//                            //该订单没有地址
//                            new OrderTipDialog(mContext, R.style.mydialog, "提示", "请先选择地址再付款","知道了", (dialog, confirm) -> {
//                                Intent intent = new Intent(mContext, ZeroOrderDetailActivity.class);
//                                intent.putExtra(ZeroOrderDetailActivity.ORDER_NO, listData.get(position).getOrderNo());
//                                intent.putExtra(ZeroOrderDetailActivity.POSITION,position);
//                                ActivityUtils.startActivity(intent);
//                            }).show();
//                        }else {
//                            operationIndex=getOrderBeanPosition(orderBean);
//                            mPresenter.verifyUserAddressInfo(orderBean.getReceiptAddressRef());
//                        }
                    }
                });

            } else if (orderBean.getState() == 2) {//待发货
                tvType.setText("待发货");
                btnCenter.setVisibility(View.GONE);
                btnRight.setText("提醒发货");

                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        operationIndex=getOrderBeanPosition(orderBean);
                        mPresenter.remindShip(orderBean.getOrderNo(), OrderType.ZERO_TYPE);
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

                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", getResources().getString(R.string.order_dialog_sure_content),
                            "确定", (dialog, confirm) -> {
                        if(confirm){
                            operationIndex=getOrderBeanPosition(orderBean);
                            mPresenter.completeOrder(orderBean.getOrderNo(), OrderType.ZERO_TYPE);
                        }
                    }).show();
                });

            } else if(orderBean.getState()==4){//已取消
                tvType.setText("已取消");
                btnCenter.setVisibility(View.GONE);
                btnRight.setText("删除订单");

                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                            if(confirm){
                                operationIndex=getOrderBeanPosition(orderBean);
                                deleteOrder(orderBean);
                            }
                        }).show();
                    }
                });

            }else if(orderBean.getState() == 5) {//已完成
                tvType.setText("交易完成");
                btnCenter.setVisibility(View.GONE);
                btnRight.setText("删除订单");

                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                            if(confirm){
                                operationIndex=getOrderBeanPosition(orderBean);
                                deleteOrder(orderBean);
                            }
                        }).show();
                    }
                });
            }
        }
    }

    /**
     * 取消订单
     */
    private void cancelOrder(OrderStateBean.ResultBean entity){
        CancelOrderRequestBody requestBody=new CancelOrderRequestBody();
        requestBody.setOrderNo(entity.getOrderNo());
        requestBody.setOrderType(OrderType.ZERO_TYPE);
        mPresenter.cancelOrder(requestBody);
    }

    /**
     * 删除订单
     */
    private void deleteOrder(OrderStateBean.ResultBean entity){
        DeleteOrderRequestBody requestBody=new DeleteOrderRequestBody();
        requestBody.setOrderNo(entity.getOrderNo());
        requestBody.setOrderType(OrderType.ZERO_TYPE);
        mPresenter.deleteOrder(requestBody);
    }

    /**
     * 去支付
     */
    private void toPay(OrderStateBean.ResultBean entity){
        Intent intent = new Intent(mContext, PayOrderActivity.class);
        intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
        intent.putExtra(PayOrderActivity.GOODS_NAME, entity.getGoodsName());
        intent.putExtra(PayOrderActivity.TOTAL_PRICE, entity.getPayAmount() + entity.getPostage());
        intent.putExtra(PayOrderActivity.ORDER_NUMBER, entity.getOrderNo());
        intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.CUT_TYPE);
        startActivity(intent);
    }
}
