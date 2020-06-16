package com.xk.mall.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ZeroOrderBean;
import com.xk.mall.model.eventbean.OrderStateChangeEvent;
import com.xk.mall.model.eventbean.OtherPaySuccessBean;
import com.xk.mall.model.eventbean.PaySuccessBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.GlobalBuyerOrderViewImpl;
import com.xk.mall.presenter.GlobalBuyerOrderPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareModel;
import com.xk.mall.view.activity.GlobalBuyerOrderDetailActivity;
import com.xk.mall.view.activity.LogisticsActivity;
import com.xk.mall.view.activity.OtherPayActivity;
import com.xk.mall.view.activity.PayOrderActivity;
import com.xk.mall.view.activity.WuGMainActivity;
import com.xk.mall.view.widget.ChooseSellStyleDialog;
import com.xk.mall.view.widget.DialogSellShare;
import com.xk.mall.view.widget.OrderTipDialog;
import com.xk.mall.view.widget.SellSuccessDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName GlobalBuyerOrderFragment
 * Description 全球买手订单fragment
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class GlobalBuyerOrderFragment extends BaseFragment<GlobalBuyerOrderPresenter> implements GlobalBuyerOrderViewImpl {

    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @BindView(R.id.refresh_order)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.state_view_order)
    MultiStateView multiStateView;
    private int orderType = 0;//订单类型  全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
    private int page = 1;//页数
    private int limit = 10;//每页的条数
    private OrderListAdapter orderListAdapter;
    private List<OrderBean> orderBeanList;
    private int clickPos = -1;//点击的位置
    private String account;
    private int createTimeFlag;
    private String orderAmountL;
    private String orderAmountR;
    private String searchName;
    private int sellType = 0;//寄卖类型： 1 ：发货 2：寄卖
    private int shareModel;//分享模式

    /**
     * 构造方法
     */
    public static GlobalBuyerOrderFragment getInstance(int titleType) {
        GlobalBuyerOrderFragment fragment = new GlobalBuyerOrderFragment();
        fragment.orderType = titleType;
        return fragment;
    }

    @Override
    protected GlobalBuyerOrderPresenter createPresenter() {
        return new GlobalBuyerOrderPresenter(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void prepareFetchData(boolean forceUpdate) {
        super.prepareFetchData(true);//强制刷新fragment
    }

    @Override
    protected void loadLazyData() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        account = SPUtils.getInstance().getString(Constant.USER_MOBILE);
        rvOrder.setLayoutManager(new LinearLayoutManager(mContext));
        refreshLayout.autoRefresh();
        orderBeanList = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(mContext, R.layout.order_list_item, orderBeanList);
        rvOrder.setAdapter(orderListAdapter);
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getOrderList(searchName, account,orderType,createTimeFlag, orderAmountL, orderAmountR, page,Constant.limit);
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getOrderList(searchName, account,orderType,createTimeFlag, orderAmountL, orderAmountR, page,Constant.limit);
        });

        orderListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //点击跳转订单详情
                OrderBean bean = orderBeanList.get(position);
                Intent intent = new Intent(mContext, GlobalBuyerOrderDetailActivity.class);
                intent.putExtra(GlobalBuyerOrderDetailActivity.ORDER_TYPE, bean.getState());
                intent.putExtra(GlobalBuyerOrderDetailActivity.ORDER_NO, bean.getOrderNo());
                intent.putExtra(GlobalBuyerOrderDetailActivity.MUST_CHOOSE_WUG, bean.getAlwaysSelectMg());
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void onGetGlobalBuyerOrderListSuccess(BaseModel<OrderPageBean> model) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if(model.getData() != null && model.getData().result != null){
            if(page == 1){
                orderBeanList.clear();
            }
            orderBeanList.addAll(model.getData().result);
            orderListAdapter.notifyDataSetChanged();
            if(model.getData().result.size() < limit){
                refreshLayout.setEnableLoadMore(false);
            }else {
                refreshLayout.setEnableLoadMore(true);
            }
            if(page == 1 && model.getData().result.size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onCancelOrderSuccess(BaseModel<String> model) {
        ToastUtils.showShortToast(mContext, model.getMsg());
        if(clickPos != -1){
            OrderBean orderBean = orderBeanList.get(clickPos);
            orderBean.setState(4);
            ToastUtils.showShortToast(mContext, "取消订单成功");
            orderListAdapter.notifyItemChanged(clickPos);
        }
    }

    @Override
    public void onCompleteOrderSuccess(BaseModel<String> model) {
        if(clickPos != -1){
            OrderBean orderBean = orderBeanList.get(clickPos);
            orderBean.setState(5);
            ToastUtils.showShortToast(mContext, "确认收货成功");
            orderListAdapter.notifyItemChanged(clickPos);
            clickPos = -1;
        }
    }

    @Override
    public void onRemindShipSuccess(BaseModel model) {
        ToastUtils.showShortToast(mContext, "提醒发货成功");
    }

    @Override
    public void onExtendTheTimeSuccess(BaseModel model) {
        com.blankj.utilcode.util.ToastUtils.showShort("延长收货成功");
    }

    @Override
    public void onModifyOrderTypeSuccess(BaseModel<ShareBean> model) {
        //修改当前订单状态
        if(clickPos != -1){
            OrderBean orderBean = orderBeanList.get(clickPos);
            if(sellType == 1){
                orderBean.setState(2);
                ToastUtils.showShortToast(mContext, "申请发货成功");
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
            }
            shareModel = 0;
            orderListAdapter.notifyItemChanged(clickPos);
            clickPos = -1;
            sellType = 0;
        }
    }

    @Override
    public void onDeleteSuccess(BaseModel<String> model) {
        if(clickPos != -1){
            Logger.e("当前点击位置:" + clickPos);
            orderBeanList.remove(clickPos);
            orderListAdapter.notifyItemRemoved(clickPos);
            ToastUtils.showShortToast(mContext, "删除成功");
            clickPos = -1;
            if(orderBeanList == null || orderBeanList.size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaySuccess(PaySuccessBean otherPaySuccessBean){
        if(orderBeanList != null){
            for(OrderBean orderBean : orderBeanList){
                if(orderBean.getOrderNo().equals(otherPaySuccessBean.getOrderNo())){
                    orderBean.setState(2);
                }
            }
            orderListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    /**
     * 订单详情页面传递的数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cancelOrDeleteSuccess(RefreshOrderListEvent event){
        OrderBean selectOrderBean = null;
        for (OrderBean orderBean : orderBeanList){
            if(orderBean.getOrderNo().equals(event.getOrderNo())){
                selectOrderBean = orderBean;
            }
        }
        if(event.getIsDelete() && selectOrderBean != null){
            //删除订单
            orderBeanList.remove(selectOrderBean);
            orderListAdapter.notifyDataSetChanged();
        }else if(event.isFinish() && selectOrderBean != null){
            //取消订单
            selectOrderBean.setState(5);
            orderListAdapter.notifyDataSetChanged();
        }else if(!event.getIsDelete() && selectOrderBean != null){
            selectOrderBean.setState(4);
            orderListAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOrderStateChanged(OrderStateChangeEvent stateChangeEvent){
        OrderBean selectOrderBean = null;
        if(orderBeanList == null || orderBeanList.size() == 0){
            return;
        }
        for (int  i = 0; i < orderBeanList.size(); i++){
            OrderBean orderBean = orderBeanList.get(i);
            if(orderBean.getOrderNo().equals(stateChangeEvent.getOrderNo())){
                selectOrderBean = orderBean;
            }
        }
        if(selectOrderBean != null){
            selectOrderBean.setState(stateChangeEvent.getOrderState());
            orderListAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOtherPaySuccess(OtherPaySuccessBean otherPaySuccessBean){
        if(orderBeanList != null){
            for(OrderBean orderBean : orderBeanList){
                if(orderBean.getOrderNo().equals(otherPaySuccessBean.getOrderNo())){
                    orderBean.setPaid(0);
                }
            }
            orderListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        ToastUtils.showShortToast(mContext, model.getMsg());
//        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    private int getOrderBeanPosition(OrderBean orderBean){
        int result = -1;
        for(int i = 0; i < orderBeanList.size(); i++){
            OrderBean orderBean1 = orderBeanList.get(i);
            if(orderBean1.getOrderNo().equals(orderBean.getOrderNo())){
                result = i;
            }
        }
        return result;
    }

    class OrderListAdapter extends CommonAdapter<OrderBean>{

        public OrderListAdapter(Context context, int layoutId, List<OrderBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, OrderBean orderBean, int position) {
            holder.setText(R.id.tv_order_list_shopName, orderBean.getMerchantName());
            holder.setText(R.id.tv_order_list_name, orderBean.getGoodsName());
            holder.setText(R.id.tv_order_list_money, getResources().getString(R.string.money) + PriceUtil.dividePrice(orderBean.getCommoditySalePrice()));
            holder.setText(R.id.tv_order_list_num, "x" + orderBean.getCommodityQuantity());
            holder.setText(R.id.tv_order_list_sku, orderBean.getCommodityModel() + " " + orderBean.getCommoditySpec());
            holder.setText(R.id.tv_order_list_pay_amount, "订单总额:¥" + PriceUtil.dividePrice(orderBean.getPayAmount()));
            if(orderBean.getPostage() == 0){
                holder.setText(R.id.tv_order_list_postage, "免运费");
            }else {
                holder.setText(R.id.tv_order_list_postage, "运费:¥" + PriceUtil.dividePrice(orderBean.getPostage()));
            }
            ImageView ivLogo = holder.getView(R.id.iv_order_list_logo);
            GlideUtil.showRadius(mContext, orderBean.getGoodsImageUrl(), 2, ivLogo);
            TextView tvType = holder.getView(R.id.tv_order_list_type);
            TextView btnRight = holder.getView(R.id.tv_order_list_right);//最右边按钮
            TextView btnCenter = holder.getView(R.id.tv_order_list_center);//中间按钮
            TextView btnLeft = holder.getView(R.id.tv_order_list_left);//最左边按钮
            //订单类型  全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
            btnRight.setBackgroundResource(R.drawable.bg_btn_order_black);
            btnCenter.setBackgroundResource(R.drawable.bg_btn_order_gray);
            btnLeft.setBackgroundResource(R.drawable.bg_btn_order_gray);
            btnRight.setTextColor(Color.WHITE);
            btnCenter.setTextColor(Color.parseColor("#999999"));
            if(orderBean.getState() == 1){//待付款
                tvType.setText("待付款");
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                if(orderBean.getPaid() == 1){
                    btnCenter.setVisibility(View.VISIBLE);
                    btnCenter.setText("请人代付");
                }else {
                    btnCenter.setVisibility(View.GONE);
                }
                btnLeft.setText("取消订单");
                btnRight.setText("立即付款");
                btnRight.setOnClickListener(v -> {
                    //立即付款
                    Intent intent = new Intent(mContext, PayOrderActivity.class);
                    intent.putExtra(PayOrderActivity.GOODS_NAME, orderBean.getGoodsName());
                    intent.putExtra(PayOrderActivity.TOTAL_PRICE, orderBean.getPayAmount());
                    intent.putExtra(PayOrderActivity.ORDER_NUMBER, orderBean.getOrderNo());
                    intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
                    intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, true);
                    intent.putExtra(PayOrderActivity.COUPON_VALUE, orderBean.getDeductionCouponAmount());
                    ActivityUtils.startActivity(intent);
                });
                btnCenter.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, OtherPayActivity.class);
                    intent.putExtra(OtherPayActivity.GOODS_NAME, orderBean.getGoodsName());
                    intent.putExtra(OtherPayActivity.TOTAL_PRICE, orderBean.getPayAmount());
                    intent.putExtra(OtherPayActivity.ORDER_NUMBER, orderBean.getOrderNo());
                    intent.putExtra(OtherPayActivity.PAYMENT_ID, orderBean.getPartnerId());
                    intent.putExtra(OtherPayActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
                    ActivityUtils.startActivity(intent);
                });

                btnLeft.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定取消订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            mPresenter.cancelOrder(orderBean.getOrderNo(), OrderType.GLOBAL_TYPE);
                        }
                    }).show();
                });
            }else if(orderBean.getState() == 2){//待发货
                tvType.setText("待发货");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("提醒发货");
                btnRight.setOnClickListener(v -> mPresenter.remindShip(orderBean.getOrderNo(), OrderType.GLOBAL_TYPE));
            }else if(orderBean.getState() == 3){
                tvType.setText("待收货");
                btnCenter.setVisibility(View.VISIBLE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnCenter.setText("查看物流");
                btnRight.setText("确认收货");
                btnCenter.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
                    ActivityUtils.startActivity(intent);
                });
                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", getResources().getString(R.string.order_dialog_sure_content),"确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            mPresenter.completeOrder(orderBean.getOrderNo(), OrderType.GLOBAL_TYPE);
                        }
                    }).show();
                });
            }else if(orderBean.getState() == 4){
                tvType.setText("已取消");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("删除订单");
                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            mPresenter.deleteOrder(orderBean.getOrderNo(), OrderType.GLOBAL_TYPE);
                        }
                    }).show();
                });
            }else if(orderBean.getState() == 5){
                tvType.setText("已完成");
                btnCenter.setVisibility(View.GONE);
                btnRight.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
                    ActivityUtils.startActivity(intent);
                });
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("查看物流");
                btnRight.setTextColor(Color.parseColor("#999999"));
                btnRight.setBackgroundResource(R.drawable.bg_btn_order_gray);
            }else if(orderBean.getState() == 6){
                tvType.setText("已关闭");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("删除订单");
                btnRight.setOnClickListener(v -> {
                    //点击发货自用
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            mPresenter.deleteOrder(orderBean.getOrderNo(), OrderType.GLOBAL_TYPE);
                        }
                    }).show();
                });
            }else if(orderBean.getState() == 7){
                tvType.setText("待确认");
                btnCenter.setVisibility(View.VISIBLE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("寄卖");
                btnCenter.setTextColor(Color.parseColor("#4A4A4A"));
                btnRight.setBackgroundResource(R.drawable.bg_btn_want_sell);
                btnRight.setVisibility(View.VISIBLE);
                btnCenter.setText("发货");
                btnCenter.setBackgroundResource(R.drawable.bg_btn_want_self);
                btnCenter.setOnClickListener(v -> {
                    //点击发货自用
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确认申请该商品自用收货","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            sellType = 1;
                            mPresenter.modifyOrderType(MyApplication.userId, orderBean.getOrderNo(), 0, 1);
                        }
                    }).show();
                });
                btnRight.setOnClickListener(v -> {
                    //点击寄卖
                    new ChooseSellStyleDialog(mContext, R.style.mydialog,orderBean.getWhetherToAllow(), orderBean.getIsDirect(), (isWug, isShare) -> {
                        if(isWug && isShare){
                            clickPos = getOrderBeanPosition(orderBean);
                            shareModel = ShareModel.MODEL_BOTH;
                            sellType = 2;
                            mPresenter.modifyOrderType(MyApplication.userId, orderBean.getOrderNo(), ShareModel.MODEL_BOTH, 2);
                        }else if(isWug){
                            //只选择寄卖
                            clickPos = getOrderBeanPosition(orderBean);
                            shareModel = ShareModel.MODEL_SELL;
                            sellType = 2;
                            mPresenter.modifyOrderType(MyApplication.userId, orderBean.getOrderNo(), ShareModel.MODEL_SELL, 2);
                        }else if(isShare){
                            //只选择分享
                            clickPos = getOrderBeanPosition(orderBean);
                            shareModel = ShareModel.MODEL_SHARE;
                            sellType = 2;
                            mPresenter.modifyOrderType(MyApplication.userId, orderBean.getOrderNo(), ShareModel.MODEL_SHARE, 2);
                        }
                    }).show();
                });
            }else if(orderBean.getState() == 11){
                tvType.setText("已寄卖");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.GONE);
            }else if(orderBean.getState() == 15){//交易完结
                tvType.setText("交易完结");
                btnCenter.setVisibility(View.VISIBLE);
                btnCenter.setText("查看物流");
                btnCenter.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
                    ActivityUtils.startActivity(intent);
                });
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("删除订单");
                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            mPresenter.deleteOrder(orderBean.getOrderNo(), OrderType.GLOBAL_TYPE);
                        }
                    }).show();
                });
            }

        }
    }



}
