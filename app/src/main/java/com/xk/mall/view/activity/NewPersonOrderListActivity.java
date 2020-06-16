package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CutOrderBean;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.eventbean.FinishDetailEventBean;
import com.xk.mall.model.eventbean.OtherPaySuccessBean;
import com.xk.mall.model.eventbean.PaySuccessBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.NewPersonOrderViewImpl;
import com.xk.mall.model.impl.WuGOrderViewImpl;
import com.xk.mall.presenter.NewPersonOrderPresenter;
import com.xk.mall.presenter.WuGOrderPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
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
 * @ClassName: NewPersonOrderListActivity
 * @Description: 新人专区订单列表页面
 * @Author: 卿凯
 * @Date: 2019/9/3/003 17:07
 * @Version: 1.0
 */
public class NewPersonOrderListActivity extends BaseActivity<NewPersonOrderPresenter> implements NewPersonOrderViewImpl {

    @BindView(R.id.rv_want_sell_order_list)
    RecyclerView rvWantSellOrderList;
    @BindView(R.id.refresh_order)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.state_view_order)
    MultiStateView multiStateView;
    private List<OrderBean> wantSellBeans;
    private int page = 1;
    private int clickPos = -1;//点击的位置
    private OrderListAdapter orderListAdapter;
    private List<OrderBean> orderBeanList;
    private int createTimeFlag;
    private String orderAmountL;
    private String orderAmountR;
    private String searchName;

    @Override
    protected NewPersonOrderPresenter createPresenter() {
        return new NewPersonOrderPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_want_sell_order_list;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("新人专区订单");
    }

    @Override
    protected void initData() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        refreshLayout.autoRefresh();
        String account = SPUtils.getInstance().getString(Constant.USER_MOBILE);
        rvWantSellOrderList.setLayoutManager(new LinearLayoutManager(mContext));
        orderBeanList = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(mContext, R.layout.order_list_item, orderBeanList);
        rvWantSellOrderList.setAdapter(orderListAdapter);

        orderListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //点击跳转订单详情
                OrderBean bean = orderBeanList.get(position);
                Intent intent = new Intent(mContext, NewPersonOrderDetailActivity.class);
                intent.putExtra(NewPersonOrderDetailActivity.ORDER_TYPE, bean.getState());
                intent.putExtra(NewPersonOrderDetailActivity.ORDER_NO, bean.getOrderNo());
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            //请求接口获取订单列表
            mPresenter.getOrderList(searchName, account,0,createTimeFlag, orderAmountL, orderAmountR, page,Constant.limit);
        });

        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            //请求接口获取订单列表
            mPresenter.getOrderList(searchName, account,0,createTimeFlag, orderAmountL, orderAmountR, page,Constant.limit);
        });
    }

    @Override
    public void onGetNewOrderListSuccess(BaseModel<OrderPageBean> model) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if(model.getData() != null && model.getData().result != null){
            if(page == 1){
                orderBeanList.clear();
            }
            orderBeanList.addAll(model.getData().result);
            orderListAdapter.notifyDataSetChanged();
            if(model.getData().result.size() < Constant.limit){
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
        ToastUtils.showShort(model.getMsg());
        if(clickPos != -1){
            OrderBean orderBean = orderBeanList.get(clickPos);
            orderBean.setState(4);
            ToastUtils.showShort("取消订单成功");
            orderListAdapter.notifyItemChanged(clickPos);
            clickPos = -1;
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
        ToastUtils.showShort(model.getMsg());
        if(clickPos != -1){
            OrderBean orderBean = orderBeanList.get(clickPos);
            orderBean.setState(5);
            ToastUtils.showShort("确认收货成功");
            orderListAdapter.notifyItemChanged(clickPos);
            clickPos = -1;
        }
    }

    @Override
    public void onDeleteSuccess(BaseModel<String> model) {
        if(clickPos != -1){
            orderBeanList.remove(clickPos);
            orderListAdapter.notifyItemRemoved(clickPos);
            ToastUtils.showShort("删除成功");
            clickPos = -1;
            if(orderBeanList == null || orderBeanList.size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
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
            //取消订单
            selectOrderBean.setState(4);
            orderListAdapter.notifyDataSetChanged();
        }
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

    class OrderListAdapter extends CommonAdapter<OrderBean> {

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
            holder.setText(R.id.tv_order_list_pay_amount, "订单总额：¥" + PriceUtil.dividePrice(orderBean.getPayAmount()));
            if(orderBean.getPostage() == 0){
                holder.setText(R.id.tv_order_list_postage, "免运费");
            }else {
                holder.setText(R.id.tv_order_list_postage, "运费：¥" + PriceUtil.dividePrice(orderBean.getPostage()));
            }
            ImageView ivLogo = holder.getView(R.id.iv_order_list_logo);
            GlideUtil.showRadius(mContext, orderBean.getGoodsImageUrl(), 2, ivLogo);
            TextView tvType = holder.getView(R.id.tv_order_list_type);
            TextView btnRight = holder.getView(R.id.tv_order_list_right);//最右边按钮
            TextView btnCenter = holder.getView(R.id.tv_order_list_center);//中间按钮
            TextView btnLeft = holder.getView(R.id.tv_order_list_left);//最左边按钮
            //订单类型  全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
            if(orderBean.getState() == 1){//待付款
                tvType.setText("待付款");
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                btnCenter.setVisibility(View.GONE);
                btnLeft.setText("取消订单");
                btnRight.setText("立即付款");
                btnRight.setOnClickListener(v -> {
                    //立即付款
                    Intent intent = new Intent(mContext, PayOrderActivity.class);
                    intent.putExtra(PayOrderActivity.GOODS_NAME, orderBean.getGoodsName());
                    intent.putExtra(PayOrderActivity.TOTAL_PRICE, orderBean.getPayAmount());
                    intent.putExtra(PayOrderActivity.ORDER_NUMBER, orderBean.getOrderNo());
                    intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.NEW_PERSON_TYPE);
                    intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
                    intent.putExtra(PayOrderActivity.COUPON_VALUE, orderBean.getCouponAmount());
                    ActivityUtils.startActivity(intent);
                });

                btnLeft.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定取消订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            mPresenter.cancelOrder(orderBean.getOrderNo(), OrderType.NEW_PERSON_TYPE);
                        }
                    }).show();
                });
            }else if(orderBean.getState() == 2){//待发货
                tvType.setText("待发货");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("提醒发货");
                btnRight.setOnClickListener(v -> mPresenter.remindShip(orderBean.getOrderNo(), OrderType.NEW_PERSON_TYPE));
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
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.NEW_PERSON_TYPE);
                    ActivityUtils.startActivity(intent);
                });
                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", getResources().getString(R.string.order_dialog_sure_content),"确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            mPresenter.completeOrder(orderBean.getOrderNo(), OrderType.NEW_PERSON_TYPE);
                        }
                    }).show();
                });
            }else if(orderBean.getState() == 4){
                tvType.setText("已取消");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("删除订单");
                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            mPresenter.deleteOrder(orderBean.getOrderNo(), OrderType.NEW_PERSON_TYPE);
                        }
                    }).show();
                });
            }else if(orderBean.getState() == 5){
                tvType.setText("已完成");
                btnCenter.setVisibility(View.VISIBLE);
                btnCenter.setText("查看订单");
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
                            mPresenter.deleteOrder(orderBean.getOrderNo(), OrderType.NEW_PERSON_TYPE);
                        }
                    }).show();
                });
            }else if(orderBean.getState() == 6){
                tvType.setText("已关闭");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("删除订单");
                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            mPresenter.deleteOrder(orderBean.getOrderNo(), OrderType.NEW_PERSON_TYPE);
                        }
                    }).show();
                });
            }

        }
    }
}
