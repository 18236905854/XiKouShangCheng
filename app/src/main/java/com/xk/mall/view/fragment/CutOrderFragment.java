package com.xk.mall.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.CutOrderBean;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.eventbean.PaySuccessBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.CutOrderImpl;
import com.xk.mall.model.request.CancelOrderRequestBody;
import com.xk.mall.model.request.DeleteOrderRequestBody;
import com.xk.mall.presenter.CutOrderPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.activity.CutOrderDetailActivity;
import com.xk.mall.view.activity.LogisticsActivity;
import com.xk.mall.view.activity.PayOrderActivity;
import com.xk.mall.view.activity.ZeroOrderDetailActivity;
import com.xk.mall.view.widget.OrderTipDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName CutOrderFragment
 * Description 喜立得订单fragment
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class CutOrderFragment extends BaseFragment<CutOrderPresenter> implements CutOrderImpl {
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @BindView(R.id.refresh_order)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.state_view_order)
    MultiStateView stateViewOrder;

    private String searchName;
    private String buyerAccount;
    private int state = 0;//订单类型  0全部、1待付款、2待发货、3待收货
    private int createTimeFlag;
    private int orderAmountL;
    private int orderAmountR;
    private int page = 1;
    private int limit = 10;


    private List<CutOrderBean.ResultBean> listData = new ArrayList<>();
    private OrderListAdapter orderListAdapter;
    private int operationIndex;//操作的position
    /**
     * 构造方法
     */
    public static CutOrderFragment getInstance(int titleType) {
        CutOrderFragment fragment = new CutOrderFragment();
        fragment.state = titleType;
        return fragment;
    }

    @Override
    protected CutOrderPresenter createPresenter() {
        return new CutOrderPresenter(this);
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
            for (CutOrderBean.ResultBean listDatum : listData) {
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
        buyerAccount= SPUtils.getInstance().getString(Constant.USER_MOBILE);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                listData.clear();
                page=1;
                mPresenter.getAllOrderList(searchName,buyerAccount,state,createTimeFlag,orderAmountL,orderAmountR,page,limit);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getAllOrderList(searchName,buyerAccount,state,createTimeFlag,orderAmountL,orderAmountR,page,limit);
            }
        });
    }


    @Override
    protected void loadLazyData() {
        stateViewOrder.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mRefreshLayout.autoRefresh();
        rvOrder.setLayoutManager(new LinearLayoutManager(mContext));
        orderListAdapter = new OrderListAdapter(mContext, R.layout.cut_order_list_item, listData);
        rvOrder.setAdapter(orderListAdapter);

        orderListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(mContext, CutOrderDetailActivity.class);
                intent.putExtra(CutOrderDetailActivity.ORDER_NO, listData.get(position).getOrderNo());
                intent.putExtra(CutOrderDetailActivity.POSITION,position);
                ActivityUtils.startActivity(intent);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    //获取订单成功
    @Override
    public void onGetOrderListDataSuc(BaseModel<CutOrderBean> model) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaySuccess(PaySuccessBean otherPaySuccessBean){
        if(listData != null){
            for(CutOrderBean.ResultBean orderBean : listData){
                if(orderBean.getOrderNo().equals(otherPaySuccessBean.getOrderNo())){
                    orderBean.setState(2);
                }
            }
            orderListAdapter.notifyDataSetChanged();
        }
    }

    //取消订单成功
    @Override
    public void onGetCancelOrderSuc(BaseModel<String> model) {
        listData.get(operationIndex).setState(4);//已取消
        orderListAdapter.notifyDataSetChanged();
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
        listData.get(operationIndex).setState(5);//已取消
        orderListAdapter.notifyDataSetChanged();
    }

    //删除订单成功
    @Override
    public void onGetDeleteOrderSuc(BaseModel<String> model) {
        listData.remove(operationIndex);
        orderListAdapter.notifyItemRemoved(operationIndex);
        orderListAdapter.notifyDataSetChanged();
        ToastUtils.showShortToast(mContext,"删除成功");
        if(listData.size()==0){
            stateViewOrder.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        ToastUtils.showShortToast(mContext,model.getMsg());
    }

    private int getOrderBeanPosition(CutOrderBean.ResultBean orderBean){
        int result = -1;
        for(int i = 0; i < listData.size(); i++){
            CutOrderBean.ResultBean orderBean1 = listData.get(i);
            if(orderBean1.getOrderNo().equals(orderBean.getOrderNo())){
                result = i;
            }
        }
        return result;
    }

    class OrderListAdapter extends CommonAdapter<CutOrderBean.ResultBean> {

        public OrderListAdapter(Context context, int layoutId, List<CutOrderBean.ResultBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, CutOrderBean.ResultBean orderBean, int position) {
            holder.setText(R.id.tv_order_list_name, orderBean.getGoodsName());
            TextView tvOrderTotal=holder.getView(R.id.tv_order_total);
            TextView tvPostage=holder.getView(R.id.tv_postage);
            TextView tvUnitMoney=holder.getView(R.id.tv_order_unit_money);
            TextView tvQuantity=holder.getView(R.id.tv_order_list_num);
            TextView tvSku=holder.getView(R.id.tv_order_list_sku);

            TextView tvMerchantName = holder.getView(R.id.tv_order_list_shopName);
            if (TextUtils.isEmpty(orderBean.getMerchantName())) {
                tvMerchantName.setText("喜扣商城");
            } else {
                tvMerchantName.setText(orderBean.getMerchantName());
            }
            ImageView ivLogo = holder.getView(R.id.iv_order_list_logo);
            GlideUtil.show(mContext, orderBean.getGoodsImageUrl(), ivLogo);
            tvUnitMoney.setText("¥"+PriceUtil.dividePrice(orderBean.getCommoditySalePrice()));
            tvQuantity.setText("X"+orderBean.getCommodityQuantity());
            tvSku.setText(orderBean.getCommodityModel()+" "+orderBean.getCommoditySpec());
            tvOrderTotal.setText("订单总额:¥"+PriceUtil.dividePrice(orderBean.getPayAmount()));
            if(orderBean.getPostage() == 0){
                tvPostage.setText("免运费");
            }else {
                tvPostage.setText("运费:¥"+ PriceUtil.dividePrice(orderBean.getPostage()));
            }



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
                        operationIndex=getOrderBeanPosition(orderBean);
                        toPay(orderBean);
//                        ToastUtils.showShortToast(mContext,"payOrder");
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
                        mPresenter.remindShip(orderBean.getOrderNo(), OrderType.CUT_TYPE);
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
                        intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.CUT_TYPE);
                        ActivityUtils.startActivity(intent);
                    }
                });

                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new OrderTipDialog(mContext, R.style.mydialog, "提示", getResources().getString(R.string.order_dialog_sure_content),"确定", (dialog, confirm) -> {
                            if(confirm){
                                operationIndex=getOrderBeanPosition(orderBean);
                                mPresenter.completeOrder(orderBean.getOrderNo(), OrderType.CUT_TYPE);
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
                            if(confirm){
                                operationIndex=getOrderBeanPosition(orderBean);
                                deleteOrder(orderBean);
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
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.CUT_TYPE);
                    ActivityUtils.startActivity(intent);
                });
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
    private void cancelOrder(CutOrderBean.ResultBean entity){
        CancelOrderRequestBody requestBody=new CancelOrderRequestBody();
        requestBody.setOrderNo(entity.getOrderNo());
        requestBody.setOrderType(OrderType.CUT_TYPE);
        mPresenter.cancelOrder(requestBody);
    }

    /**
     * 删除订单
     */
    private void deleteOrder(CutOrderBean.ResultBean entity){
        DeleteOrderRequestBody requestBody=new DeleteOrderRequestBody();
        requestBody.setOrderNo(entity.getOrderNo());
        requestBody.setOrderType(OrderType.CUT_TYPE);
        mPresenter.deleteOrder(requestBody);
    }

    /**
     * 去支付
     */
    private void toPay(CutOrderBean.ResultBean entity){
        Intent intent = new Intent(mContext, PayOrderActivity.class);
        intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
        intent.putExtra(PayOrderActivity.GOODS_NAME, entity.getGoodsName());
        intent.putExtra(PayOrderActivity.TOTAL_PRICE, entity.getPayAmount());
        intent.putExtra(PayOrderActivity.ORDER_NUMBER, entity.getOrderNo());
        if(!XiKouUtils.isNullOrEmpty(entity.getBargainScheduleId())){
            intent.putExtra(PayOrderActivity.CUT_ID, entity.getBargainScheduleId());
        }
        intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.CUT_TYPE);
        startActivity(intent);
    }

}
