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
import com.umeng.commonsdk.debug.D;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.CancelOrderListener;
import com.xk.mall.interfaces.DeleteOrderListener;
import com.xk.mall.interfaces.ModifyOrderStateListener;
import com.xk.mall.interfaces.RemindOrderListener;
import com.xk.mall.interfaces.SureOrderListener;
import com.xk.mall.interfaces.VerifyUserAddressListener;
import com.xk.mall.model.entity.CutOrderBean;
import com.xk.mall.model.entity.ManyBuyOrderAdapterBean;
import com.xk.mall.model.entity.ManyBuyOrderBean;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.OrderStateBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ZeroOrderBean;
import com.xk.mall.model.eventbean.PayBackEvent;
import com.xk.mall.model.eventbean.PaySuccessBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.ZeroOrderImpl;
import com.xk.mall.model.request.CancelOrderRequestBody;
import com.xk.mall.model.request.DeleteOrderRequestBody;
import com.xk.mall.presenter.ZeroOrderPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareModel;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.activity.CutOrderDetailActivity;
import com.xk.mall.view.activity.GlobalBuyerOrderDetailActivity;
import com.xk.mall.view.activity.LogisticsActivity;
import com.xk.mall.view.activity.ManyBuyOrderDetailActivity;
import com.xk.mall.view.activity.NewPersonOrderDetailActivity;
import com.xk.mall.view.activity.PayOrderActivity;
import com.xk.mall.view.activity.WuGMainActivity;
import com.xk.mall.view.activity.WuGOrderDetailActivity;
import com.xk.mall.view.activity.ZeroOrderDetailActivity;
import com.xk.mall.view.adapter.CutDelegate;
import com.xk.mall.view.adapter.GlobalBuyerDelegate;
import com.xk.mall.view.adapter.ManyBuyDelegate;
import com.xk.mall.view.adapter.ManyBuySingleDelegate;
import com.xk.mall.view.adapter.NewPersonDelegate;
import com.xk.mall.view.adapter.WuGDelegate;
import com.xk.mall.view.adapter.ZeroDelegate;
import com.xk.mall.view.widget.DialogSellShare;
import com.xk.mall.view.widget.OrderTipDialog;
import com.xk.mall.view.widget.SellSuccessDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * ClassName ZeroOrderFragment
 * Description 0元拍订单fragment
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class ZeroOrderFragment extends BaseFragment<ZeroOrderPresenter> implements ZeroOrderImpl {

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


    private List<OrderStateBean.ResultBean> listData = new ArrayList<>();
    private MultiItemTypeAdapter orderListAdapter;
    private int operationIndex;//操作的position
    /**
     * 构造方法
     */
    public static ZeroOrderFragment getInstance(int titleType) {
        ZeroOrderFragment fragment = new ZeroOrderFragment();
        fragment.state = titleType;
        return fragment;
    }

    @Override
    protected ZeroOrderPresenter createPresenter() {
        return new ZeroOrderPresenter(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payBackSuccess(PayBackEvent payBackEvent){
        int result = -1;
        for(int i = 0; i < listData.size(); i++){
            OrderStateBean.ResultBean orderBean1 = listData.get(i);
            if(orderBean1.getOrderNo().equals(payBackEvent.getOrderNo())){
                result = i;
            }
        }
        if(result != -1){
            listData.remove(result);
            orderListAdapter.notifyItemRemoved(result);
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

    private CancelOrderListener cancelOrderListener = new CancelOrderListener() {
        @Override
        public void cancelOrder(OrderStateBean.ResultBean item, int orderType) {
            operationIndex = getOrderBeanPosition(item);
            CancelOrderRequestBody cancelOrderRequestBody = new CancelOrderRequestBody();
            cancelOrderRequestBody.setOrderNo(item.getOrderNo());
            cancelOrderRequestBody.setOrderType(orderType);
            mPresenter.cancelOrder(cancelOrderRequestBody);
        }
    };

    private DeleteOrderListener deleteOrderListener = new DeleteOrderListener() {
        @Override
        public void deleteOrder(OrderStateBean.ResultBean item, int orderType) {
            operationIndex = getOrderBeanPosition(item);
            DeleteOrderRequestBody deleteOrderRequestBody = new DeleteOrderRequestBody();
            deleteOrderRequestBody.setOrderNo(item.getOrderNo());
            deleteOrderRequestBody.setOrderType(orderType);
            mPresenter.deleteOrder(deleteOrderRequestBody);
        }
    };

    private RemindOrderListener remindOrderListener = new RemindOrderListener() {
        @Override
        public void remindOrder(OrderStateBean.ResultBean item, int orderType) {
            operationIndex = getOrderBeanPosition(item);
            mPresenter.remindShip(item.getOrderNo(), orderType);
        }
    };

    private SureOrderListener sureOrderListener = new SureOrderListener() {
        @Override
        public void sureOrder(OrderStateBean.ResultBean item, int orderType) {
            operationIndex = getOrderBeanPosition(item);
            mPresenter.completeOrder(item.getOrderNo(), orderType);
        }
    };

    //检查用户地址是否超出范围
    private VerifyUserAddressListener addressListener = new VerifyUserAddressListener() {
        @Override
        public void verifyAddress(String address) {
            mPresenter.verifyUserAddressInfo(address);
        }
    };

    //修改订单状态，寄卖和发货
    private ModifyOrderStateListener modifyOrderStateListener = new ModifyOrderStateListener() {
        @Override
        public void modifyOrderState(OrderStateBean.ResultBean item, int orderType, int type, int shareModel) {
            operationIndex = getOrderBeanPosition(item);
            ZeroOrderFragment.this.shareModel = shareModel;
            sellType = type;
            mPresenter.modifyOrderType(MyApplication.userId, item.getOrderNo(), shareModel, type);
        }
    };

    @Override
    protected void loadLazyData() {
        stateViewOrder.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        mRefreshLayout.autoRefresh();
        rvOrder.setLayoutManager(new LinearLayoutManager(mContext));
        orderListAdapter = new MultiItemTypeAdapter(mContext, listData);
        orderListAdapter.addItemViewDelegate(new WuGDelegate(mContext).setCancelOrderListener(cancelOrderListener)
        .setDeleteOrderListener(deleteOrderListener).setRemindOrderListener(remindOrderListener).setSureOrderListener(sureOrderListener));
        orderListAdapter.addItemViewDelegate(new GlobalBuyerDelegate(mContext).setCancelOrderListener(cancelOrderListener).setModifyOrderStateListener(modifyOrderStateListener)
        .setDeleteOrderListener(deleteOrderListener).setRemindOrderListener(remindOrderListener).setSureOrderListener(sureOrderListener));
        orderListAdapter.addItemViewDelegate(new CutDelegate(mContext).setCancelOrderListener(cancelOrderListener)
        .setDeleteOrderListener(deleteOrderListener).setRemindOrderListener(remindOrderListener).setSureOrderListener(sureOrderListener));
        orderListAdapter.addItemViewDelegate(new NewPersonDelegate(mContext).setCancelOrderListener(cancelOrderListener)
        .setDeleteOrderListener(deleteOrderListener).setRemindOrderListener(remindOrderListener).setSureOrderListener(sureOrderListener));
        orderListAdapter.addItemViewDelegate(new ManyBuyDelegate(mContext).setCancelOrderListener(cancelOrderListener)
        .setDeleteOrderListener(deleteOrderListener).setRemindOrderListener(remindOrderListener).setSureOrderListener(sureOrderListener));
        orderListAdapter.addItemViewDelegate(new ManyBuySingleDelegate(mContext).setCancelOrderListener(cancelOrderListener)
                .setDeleteOrderListener(deleteOrderListener).setRemindOrderListener(remindOrderListener).setSureOrderListener(sureOrderListener));
        orderListAdapter.addItemViewDelegate(new ZeroDelegate(mContext).setCancelOrderListener(cancelOrderListener)
                .setAddressListener(addressListener).setDeleteOrderListener(deleteOrderListener)
                .setRemindOrderListener(remindOrderListener).setSureOrderListener(sureOrderListener));
        rvOrder.setAdapter(orderListAdapter);

        orderListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                goDetail(listData.get(position));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    private void goDetail(OrderStateBean.ResultBean item){
        if(item.getActivityModule() == ActivityType.ACTIVITY_WUG){
            Intent intent = new Intent(mContext, WuGOrderDetailActivity.class);
            intent.putExtra(WuGOrderDetailActivity.ORDER_TYPE, item.getState());
            intent.putExtra(WuGOrderDetailActivity.ORDER_NO, item.getOrderNo());
            ActivityUtils.startActivity(intent);
        }else if(item.getActivityModule() == ActivityType.ACTIVITY_GLOBAL_BUYER){
            Intent intent = new Intent(mContext, GlobalBuyerOrderDetailActivity.class);
            intent.putExtra(GlobalBuyerOrderDetailActivity.ORDER_TYPE, item.getState());
            intent.putExtra(GlobalBuyerOrderDetailActivity.ORDER_NO, item.getOrderNo());
            intent.putExtra(GlobalBuyerOrderDetailActivity.MUST_CHOOSE_WUG, item.getAlwaysSelectMg());
            ActivityUtils.startActivity(intent);
        }else if(item.getActivityModule() == ActivityType.ACTIVITY_MANY_BUY){
            //跳转订单详情
            Intent intent = new Intent(mContext, ManyBuyOrderDetailActivity.class);
            intent.putExtra(ManyBuyOrderDetailActivity.TRADE_NO, item.getTradeNo());
            int paymount = 0;//订单总额
            if(item.getChildOrderItems() != null && item.getChildOrderItems().size() > 0){
                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_NO, item.getChildOrderItems().get(0).getOrderNo());
                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_STATE, item.getChildOrderItems().get(0).getState());
                Map<String, Integer> map = new HashMap<>();
                for(OrderStateBean.ResultBean resultBean : item.getChildOrderItems()){
                    paymount += resultBean.getPayAmount();
                    map.put(resultBean.getMerchantName(), resultBean.getPostage());
                }
                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_PAY_AMOUNT, paymount);
            }else {
                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_NO, item.getOrderNo());
                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_STATE, item.getState());
                Map<String, Integer> map = new HashMap<>();
                paymount = item.getPayAmount();
                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_PAY_AMOUNT, paymount);
            }
            ActivityUtils.startActivity(intent);
        }else if(item.getActivityModule() == ActivityType.ACTIVITY_CUT){
            Intent intent = new Intent(mContext, CutOrderDetailActivity.class);
            intent.putExtra(CutOrderDetailActivity.ORDER_NO, item.getOrderNo());
            ActivityUtils.startActivity(intent);
        }else if(item.getActivityModule() == ActivityType.ACTIVITY_ZERO){
            Intent intent = new Intent(mContext, ZeroOrderDetailActivity.class);
            intent.putExtra(ZeroOrderDetailActivity.ORDER_NO, item.getOrderNo());
            ActivityUtils.startActivity(intent);
        }else if(item.getActivityModule() == ActivityType.ACTIVITY_NEW_PERSON){
            Intent intent = new Intent(mContext, NewPersonOrderDetailActivity.class);
            intent.putExtra(NewPersonOrderDetailActivity.ORDER_TYPE, item.getState());
            intent.putExtra(NewPersonOrderDetailActivity.ORDER_NO, item.getOrderNo());
            ActivityUtils.startActivity(intent);
        }
    }

    //获取订单成功
    @Override
    public void onGetOrderListDataSuc(BaseModel<OrderStateBean> model) {
        mRefreshLayout.finishRefresh(1500);
        mRefreshLayout.finishLoadMore(1500);

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
            if(state != 0){//不是全部
                listData.remove(operationIndex);
                orderListAdapter.notifyItemRemoved(operationIndex);
            }else {
                for(OrderStateBean.ResultBean orderBean : listData){
                    if(orderBean.getOrderNo().equals(otherPaySuccessBean.getOrderNo())){
                        orderBean.setState(2);
                    }
                }
                orderListAdapter.notifyDataSetChanged();
            }
        }
    }

    //取消订单成功
    @Override
    public void onGetCancelOrderSuc(BaseModel<String> model) {
        if(state != 0){
            listData.remove(operationIndex);
            orderListAdapter.notifyItemRemoved(operationIndex);
        }else {
            listData.get(operationIndex).setState(4);//已取消
            orderListAdapter.notifyDataSetChanged();
        }
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
        if(state != 0){
            listData.remove(operationIndex);
            orderListAdapter.notifyItemRemoved(operationIndex);
        }else {
            listData.get(operationIndex).setState(5);//已取消
            orderListAdapter.notifyDataSetChanged();
        }
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
    public void onVerifyUserAddressInfo(BaseModel<Boolean> model) {
        if(model.getData() != null){
            if(operationIndex != -1){
                OrderStateBean.ResultBean orderBean = listData.get(operationIndex);
                toPay(orderBean);
            }
        }else {
            //弹出对话框
            //该订单没有地址
            if(operationIndex != -1){
                OrderStateBean.ResultBean orderBean = listData.get(operationIndex);
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "收货地址超出配送范围，请更换收货地址再付款",
                        "知道了", (dialog, confirm) -> {
                    if(confirm){
                        Intent intent = new Intent(mContext, ZeroOrderDetailActivity.class);
                        intent.putExtra(ZeroOrderDetailActivity.ORDER_NO, orderBean.getOrderNo());
                        intent.putExtra(ZeroOrderDetailActivity.POSITION,operationIndex);
                        ActivityUtils.startActivity(intent);
                    }
                }).show();
            }
        }
    }

    private int sellType = 0;//寄卖类型： 1 ：发货 2：寄卖
    private int shareModel;//分享模式

    @Override
    public void onModifyOrderTypeSuccess(BaseModel<ShareBean> model) {
        //修改当前订单状态
        if(operationIndex != -1){
            OrderStateBean.ResultBean orderBean = listData.get(operationIndex);
            if(sellType == 1){
                orderBean.setState(2);
                if(state != 0){//当不是全部时，移除当前item数据
                    listData.remove(operationIndex);
                    orderListAdapter.notifyItemRemoved(operationIndex);
                }
                ToastUtils.showShortToast(mContext, "申请发货成功");
                if(listData.size() == 0){
                    stateViewOrder.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                }
            }else if(sellType == 2){
                orderBean.setState(11);
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
                if(state != 0){//当不是全部时，移除当前item数据
                    listData.remove(operationIndex);
                    orderListAdapter.notifyItemRemoved(operationIndex);
                }
                if(listData.size() == 0){
                    stateViewOrder.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                }
            }
            shareModel = 0;
            operationIndex = -1;
            sellType = 0;
        }
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

    /**
     * 去支付
     */
    private void toPay(OrderStateBean.ResultBean entity){
        Intent intent = new Intent(mContext, PayOrderActivity.class);
        intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
        intent.putExtra(PayOrderActivity.GOODS_NAME, entity.getGoodsName());
        intent.putExtra(PayOrderActivity.TOTAL_PRICE, entity.getPayAmount());
        intent.putExtra(PayOrderActivity.ORDER_NUMBER, entity.getOrderNo());
        intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.ZERO_TYPE);
        startActivity(intent);
    }

}
