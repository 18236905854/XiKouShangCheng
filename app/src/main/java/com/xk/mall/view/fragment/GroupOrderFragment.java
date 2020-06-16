package com.xk.mall.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kennyc.view.MultiStateView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.FightGroupOrderBean;
import com.xk.mall.model.eventbean.EmptyOrderListBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.FightGroupOrderImpl;
import com.xk.mall.model.request.CancelOrderRequestBody;
import com.xk.mall.model.request.DeleteOrderRequestBody;
import com.xk.mall.presenter.GroupOrderPresenter;
import com.xk.mall.utils.CalculateUtils;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.activity.CutOrderDetailActivity;
import com.xk.mall.view.activity.GroupOrderDetailActivity;
import com.xk.mall.view.activity.LogisticsActivity;
import com.xk.mall.view.activity.PayOrderActivity;
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
import butterknife.Unbinder;

/**
 * ClassName GroupOrderFragment
 * Description 定制拼团订单列表fragment
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class GroupOrderFragment extends BaseFragment<GroupOrderPresenter> implements FightGroupOrderImpl {
    private static final String TAG = "GroupOrderFragment";

    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @BindView(R.id.refresh_order)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.state_view_order)
    MultiStateView stateViewOrder;
    Unbinder unbinder;
    private int orderType = 0;//订单类型  0全部、1待付款、8.进行中 ，2待发货、3待收货 4.已取消 5，已完成 6 已关闭 7 待确认 8待成团 9成团成功 10拼团失败
    private List<FightGroupOrderBean.ResultBean> listData = new ArrayList<>();
    private  OrderListAdapter orderListAdapter;
    private int page = 1;//页数
    private int limit=10;
    private String account;
    private int operationIndex;//操作的position
    private int createTimeFlag;
    private int orderAmountL;
    private int orderAmountR;
    private String searchName;

    /**
     * 构造方法
     */
    public static GroupOrderFragment getInstance(int titleType) {
        GroupOrderFragment fragment = new GroupOrderFragment();
        fragment.orderType = titleType;
        return fragment;
    }

    @Override
    protected GroupOrderPresenter createPresenter() {
        return new GroupOrderPresenter(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    public void prepareFetchData(boolean forceUpdate) {
        super.prepareFetchData(true);//强制刷新fragment
    }

    /**
     * eventBus接收回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAddress(RefreshOrderListEvent event) {
        Logger.e("接收到刷新订单号"+ event.getOrderNo());
        Logger.e("接收到刷新订单号isDelete"+event.getIsDelete());
        if(!TextUtils.isEmpty(event.getOrderNo())){
            for (FightGroupOrderBean.ResultBean listDatum : listData) {
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

    @Override
    protected void initData() {

        account=SPUtils.getInstance().getString(Constant.USER_MOBILE);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                listData.clear();
                page=1;
                mPresenter.getAllOrderList(searchName, account,orderType,createTimeFlag, orderAmountL, orderAmountR, page,Constant.limit);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getAllOrderList(searchName, account,orderType,createTimeFlag, orderAmountL, orderAmountR, page,Constant.limit);
            }
        });

    }

    @Override
    protected void loadLazyData() {
        stateViewOrder.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mRefreshLayout.autoRefresh();
        rvOrder.setLayoutManager(new LinearLayoutManager(mContext));

        orderListAdapter = new OrderListAdapter(mContext, R.layout.group_order_list_item, listData);
        rvOrder.setAdapter(orderListAdapter);

        orderListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //点击跳转订单详情
                //当type == 8表示进行中，跳转分享页面
                if (listData.get(position).getState() == 8) {
                    //分享好友
                    ToastUtils.showShort("分享好友");
                } else if (listData.get(position).getState() == 10) {
                    ToastUtils.showShort("拼团失败");
                } else {
                    Intent intent = new Intent(mContext, GroupOrderDetailActivity.class);
                    intent.putExtra(GroupOrderDetailActivity.ORDER_NO, listData.get(position).getOrderNo());
                    ActivityUtils.startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    //获取订单成功
    @Override
    public void onGetOrderListDataSuc(BaseModel<FightGroupOrderBean> model) {
        mRefreshLayout.finishRefresh(1200);
        mRefreshLayout.finishLoadMore(1200);

        if(model.getData() != null && model.getData().getResult() != null){
            listData.addAll(model.getData().getResult());
            orderListAdapter.notifyDataSetChanged();
            if(model.getData().getResult().size() < limit){
                mRefreshLayout.setEnableLoadMore(false);
            }else {
                mRefreshLayout.setEnableLoadMore(true);
            }
            if(page == 1 && model.getData().getResult().size() == 0){
                stateViewOrder.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }else {
            stateViewOrder.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }

    }

    //取消订单
    @Override
    public void onGetCancelOrderSuc(BaseModel<String> model) {
        Log.e(TAG, "onGetCancelOrderSuc:");
        listData.get(operationIndex).setState(4);//已取消
        orderListAdapter.notifyDataSetChanged();
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
        listData.get(operationIndex).setState(5);//已取消
        orderListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetDeleteOrderSuc(BaseModel<String> model) {
        Log.e(TAG, "onGetDeleteOrderSuc: " );
        listData.remove(operationIndex);
        orderListAdapter.notifyItemRemoved(operationIndex);
        orderListAdapter.notifyDataSetChanged();
        ToastUtils.showShort("删除成功");
        if(listData.size()==0){
            stateViewOrder.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            EventBus.getDefault().post(new EmptyOrderListBean());
        }
    }


    class OrderListAdapter extends CommonAdapter<FightGroupOrderBean.ResultBean> {

        public OrderListAdapter(Context context, int layoutId, List<FightGroupOrderBean.ResultBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, FightGroupOrderBean.ResultBean orderBean, int position) {
            holder.setText(R.id.tv_order_list_name, orderBean.getGoodsName());
            holder.setText(R.id.tv_order_unit_money, getResources().getString(R.string.money) + PriceUtil.dividePrice(orderBean.getCommoditySalePrice()));
            holder.setText(R.id.tv_order_list_num, "x" + orderBean.getCommodityQuantity());
            holder.setText(R.id.tv_order_list_sku,orderBean.getCommodityModel()+" "+orderBean.getCommoditySpec());
            TextView tvMerchantName = holder.getView(R.id.tv_order_list_shopName);
            TextView tvOrderTotal=holder.getView(R.id.tv_order_total);
            TextView tvPostage=holder.getView(R.id.tv_postage);

            tvOrderTotal.setText("订单总额:¥"+ PriceUtil.dividePrice(orderBean.getPayAmount()));
            if(orderBean.getPostage() == 0){
                tvPostage.setText("免运费");
            }else {
                tvPostage.setText("运费:¥"+ PriceUtil.dividePrice(orderBean.getPostage()));
            }

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
            TextView btnLeft = holder.getView(R.id.tv_order_list_left);//最左边按钮
            //订单类型  0全部、1待付款、8.进行中 ，2待发货、3待收货 4.已取消 5，已完成 6 已关闭 7 待确认 8待成团 9成团成功 10拼团失败
            if (orderBean.getState() == 1) {//待付款
                tvType.setText("待付款");
                btnCenter.setVisibility(View.VISIBLE);
                btnLeft.setVisibility(View.GONE);
                btnCenter.setText("取消订单");
                btnRight.setText("立即付款");
                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //立即付款
                        Intent intent = new Intent(mContext, PayOrderActivity.class);
                        intent.putExtra(PayOrderActivity.GOODS_NAME, orderBean.getGoodsName());
                        intent.putExtra(PayOrderActivity.TOTAL_PRICE, orderBean.getPayAmount());
                        intent.putExtra(PayOrderActivity.ORDER_NUMBER, orderBean.getOrderNo());
                        intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
                        intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
                        ActivityUtils.startActivity(intent);
                    }
                });
                //取消订单
                btnCenter.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定取消订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            operationIndex=position;
                            cancelOrder(orderBean);
                        }
                    }).show();
                });

            } else if (orderBean.getState() == 2) {//待发货
                tvType.setText("待发货");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("提醒发货");
                btnRight.setOnClickListener(v -> mPresenter.remindShip(orderBean.getOrderNo(), OrderType.GROUP_TYPE));
            } else if (orderBean.getState() == 3) {//待收货
                tvType.setText("待收货");
                btnCenter.setVisibility(View.VISIBLE);
                btnLeft.setVisibility(View.GONE);
                btnCenter.setText("查看物流");
                btnRight.setText("确认收货");
                btnCenter.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.GROUP_TYPE);
                    ActivityUtils.startActivity(intent);
                });
                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", getResources().getString(R.string.order_dialog_sure_content),
                            "确定", (dialog, confirm) -> {
                        if(confirm){
                            operationIndex = position;
                            mPresenter.completeOrder(orderBean.getOrderNo(), OrderType.GROUP_TYPE);
                        }
                    }).show();

                });
            } else if(orderBean.getState()==4){//已取消
                tvType.setText("已取消");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("删除订单");

                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                            if(confirm){
                                operationIndex=position;
                                deleteOrder(orderBean);
                            }
                        }).show();
                    }
                });

            }else if (orderBean.getState() == 5) {//已完成
                tvType.setText("交易完成");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("删除订单");
                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                            if(confirm){
                                operationIndex=position;
                                deleteOrder(orderBean);
                            }
                        }).show();
                    }
                });
            } else if (orderBean.getState() == 10) {//拼团失败
                tvType.setText("拼团失败");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("删除订单");
                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                            if(confirm){
                                operationIndex=position;
                                deleteOrder(orderBean);
                            }
                        }).show();
                    }
                });
            } else if (orderBean.getState() == 8) {
                tvType.setText("进行中");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("分享好友");
                btnRight.setOnClickListener(v -> {
                    //点击分享好友
                    ToastUtils.showShort("分享好友");
                });
            }else if(orderBean.getState()==6){//已关闭
                tvType.setText("已关闭");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("删除订单");
                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                            if(confirm){
                                operationIndex=position;
                                deleteOrder(orderBean);
                            }
                        }).show();
                    }
                });
            }else if(orderBean.getState()==7){//待确认
                tvType.setText("待确认");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("确认订单");
            }else if(orderBean.getState()==9){//拼团成功
                tvType.setText("拼团成功");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 取消订单
     */
    private void cancelOrder(FightGroupOrderBean.ResultBean entity){
        CancelOrderRequestBody requestBody=new CancelOrderRequestBody();
        requestBody.setOrderNo(entity.getOrderNo());
        requestBody.setOrderType(OrderType.GROUP_TYPE);
        mPresenter.cancelGroupOrder(requestBody);
    }

    /**
     * 删除订单
     */
    private void deleteOrder(FightGroupOrderBean.ResultBean entity){
        DeleteOrderRequestBody requestBody=new DeleteOrderRequestBody();
        requestBody.setOrderNo(entity.getOrderNo());
        requestBody.setOrderType(OrderType.GROUP_TYPE);
        mPresenter.deleterGroupOrder(requestBody);
    }
}
