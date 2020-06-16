package com.xk.mall.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kennyc.view.MultiStateView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ManyBuyOrderAdapterBean;
import com.xk.mall.model.entity.ManyBuyOrderBean;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.entity.ZeroOrderBean;
import com.xk.mall.model.eventbean.EmptyOrderListBean;
import com.xk.mall.model.eventbean.PaySuccessBean;
import com.xk.mall.model.impl.ManyBuyOrderViewImpl;
import com.xk.mall.presenter.ManyBuyOrderPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.activity.LogisticsActivity;
import com.xk.mall.view.activity.ManyBuyOrderDetailActivity;
import com.xk.mall.view.activity.PayOrderActivity;
import com.xk.mall.view.widget.OrderTipDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ClassName ManyBuyOrderFragment
 * Description 多买多折订单fragment
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class ManyBuyOrderFragment extends BaseFragment<ManyBuyOrderPresenter> implements ManyBuyOrderViewImpl {

    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @BindView(R.id.refresh_order)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.state_view_order)
    MultiStateView multiStateView;
    private int orderType = 0;//订单类型  全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
    private int page = 1;//页数
    private int limit = 10;//每页的条数
    private boolean isHasMore = true;
//    private int clickPos = -1;//点击的位置
//    List<ManyBuyOrderBean.ResultBean> manyBuyOrderBeanList;
    List<ManyBuyOrderAdapterBean> manyBuyOrderAdapterBeans;
    private MultiItemTypeAdapter multiItemTypeAdapter;
    private ManyBuyOrderAdapterBean clickBean;//点击的地址
    private String account;
    private int createTimeFlag;
    private int orderAmountL;
    private int orderAmountR;
    private String searchName;

    /**
     * 构造方法
     */
    public static ManyBuyOrderFragment getInstance(int titleType) {
        ManyBuyOrderFragment fragment = new ManyBuyOrderFragment();
        fragment.orderType = titleType;
        return fragment;
    }

    @Override
    protected ManyBuyOrderPresenter createPresenter() {
        return new ManyBuyOrderPresenter(this);
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
        rvOrder.setLayoutManager(new LinearLayoutManager(mContext));
        refreshLayout.autoRefresh();
        account = SPUtils.getInstance().getString(Constant.USER_MOBILE);
        manyBuyOrderAdapterBeans = new ArrayList<>();
        multiItemTypeAdapter = new MultiItemTypeAdapter(mContext, manyBuyOrderAdapterBeans);
        multiItemTypeAdapter.addItemViewDelegate(new ManyOrderListDelegate());
        multiItemTypeAdapter.addItemViewDelegate(new SingleOrderDelegate());
        rvOrder.setAdapter(multiItemTypeAdapter);
//        multiItemTypeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                //跳转订单详情
//                ManyBuyOrderAdapterBean orderAdapterBean = manyBuyOrderAdapterBeans.get(0);
//                Intent intent = new Intent(mContext, ManyBuyOrderDetailActivity.class);
//                intent.putExtra(ManyBuyOrderDetailActivity.TRADE_NO, orderAdapterBean.getTradeNo());
//                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_NO, orderAdapterBean.getManyBuyOrderBeanList().get(0).getOrderNo());
//                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_STATE, orderAdapterBean.getManyBuyOrderBeanList().get(0).getState());
//                int paymount = 0;//订单总额
//                Map<String, Integer> map = new HashMap<>();
//                for(ManyBuyOrderBean.ResultBean resultBean : orderAdapterBean.getManyBuyOrderBeanList()){
//                    paymount += resultBean.getPayAmount();
//                    map.put(resultBean.getMerchantName(), resultBean.getPostage());
//                }
//                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_PAY_AMOUNT, paymount);
//                ActivityUtils.startActivity(intent);
//            }
//
//            @Override
//            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//                return false;
//            }
//        });
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getOrderList(searchName, account,orderType,createTimeFlag, orderAmountL, orderAmountR, page,Constant.limit);
        });

        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getOrderList(searchName, account,orderType,createTimeFlag, orderAmountL, orderAmountR, page,Constant.limit);
        });
    }

    @Override
    public void onGetManyBuyOrderListSuccess(BaseModel<ManyBuyOrderBean> model) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if(model.getData() != null && model.getData().getResult() != null){
            if(page == 1){
                manyBuyOrderAdapterBeans.clear();
            }
            if(page == 1 && model.getData().getResult().size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }else {
                bindRv(model.getData().getResult());
                if(model.getData().getResult().size() < limit){
                    refreshLayout.setEnableLoadMore(false);
                }else {
                    refreshLayout.setEnableLoadMore(true);
                }
            }

        }else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    private void bindRv(List<ManyBuyOrderBean.ResultBean> resultBeans){
        //排序有问题
//        Map<String, Boolean> filter = new LinkedHashMap<>();
        for(int i = 0; i < resultBeans.size(); i++){
            ManyBuyOrderBean.ResultBean resultBean = resultBeans.get(i);
            List<ManyBuyOrderBean.ResultBean> childBean = new ArrayList<>();
            ManyBuyOrderAdapterBean manyBuyOrderAdapterBean = new ManyBuyOrderAdapterBean();
            childBean.add(resultBean);
            for(int j = resultBeans.size() - 1; j >= 0; j--){
                ManyBuyOrderBean.ResultBean resultBean2 = resultBeans.get(j);
                if(resultBean2.getTradeNo().equals(resultBean.getTradeNo()) && i != j
                        && resultBean.getState() == 1 && resultBean2.getState() == 1){
                    childBean.add(resultBean2);
                    resultBeans.remove(resultBean2);
                }
            }
            manyBuyOrderAdapterBean.setManyBuyOrderBeanList(childBean);
            manyBuyOrderAdapterBean.setState(resultBean.getState());
            manyBuyOrderAdapterBean.setTradeNo(resultBean.getTradeNo());
            manyBuyOrderAdapterBeans.add(manyBuyOrderAdapterBean);
        }

        multiItemTypeAdapter.notifyDataSetChanged();
        multiItemTypeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //跳转订单详情
                ManyBuyOrderAdapterBean orderAdapterBean = manyBuyOrderAdapterBeans.get(position);
                Intent intent = new Intent(mContext, ManyBuyOrderDetailActivity.class);
                intent.putExtra(ManyBuyOrderDetailActivity.TRADE_NO, orderAdapterBean.getTradeNo());
                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_NO, orderAdapterBean.getManyBuyOrderBeanList().get(0).getOrderNo());
                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_STATE, orderAdapterBean.getManyBuyOrderBeanList().get(0).getState());
                int paymount = 0;//订单总额
                Map<String, Integer> map = new HashMap<>();
                for(ManyBuyOrderBean.ResultBean resultBean : orderAdapterBean.getManyBuyOrderBeanList()){
                    paymount += resultBean.getPayAmount();
                    map.put(resultBean.getMerchantName(), resultBean.getPostage());
                }
                intent.putExtra(ManyBuyOrderDetailActivity.ORDER_PAY_AMOUNT, paymount);
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void onCancelOrderSuccess(BaseModel<String> model) {
        ToastUtils.showShort(model.getMsg());
        //取消订单
        if(clickBean != null){
            for(ManyBuyOrderBean.ResultBean resultBean : clickBean.getManyBuyOrderBeanList()){
                resultBean.setState(4);
            }
            int position = getPosition(clickBean);
            if(position != -1){
                multiItemTypeAdapter.notifyItemChanged(position);
            }
        }
    }

    @Override
    public void onRemindShipSuccess(BaseModel model) {
        ToastUtils.showShort("提醒发货成功");
    }

    @Override
    public void onExtendTheTimeSuccess(BaseModel model) {
        com.lljjcoder.style.citylist.Toast.ToastUtils.showShortToast(mContext, "延长收货成功");
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaySuccess(PaySuccessBean otherPaySuccessBean){
        //重新刷新数据
        refreshLayout.autoRefresh();
    }

    @Override
    public void onCompleteOrderSuccess(BaseModel<String> model) {
        ToastUtils.showShort(model.getMsg());
        //取消订单
        if(clickBean != null){
            for(ManyBuyOrderBean.ResultBean resultBean : clickBean.getManyBuyOrderBeanList()){
                resultBean.setState(5);
            }
            int position = getPosition(clickBean);
            if(position != -1){
                multiItemTypeAdapter.notifyItemChanged(position);
            }
        }
    }

    @Override
    public void onDeleteSuccess(BaseModel<String> model) {
        //删除订单
        if(clickBean != null){
            ToastUtils.showShort("删除成功");
            int position = getPosition(clickBean);
            manyBuyOrderAdapterBeans.remove(clickBean);
            if(position != -1){
                multiItemTypeAdapter.notifyItemRemoved(position);
            }
            if(manyBuyOrderAdapterBeans == null || manyBuyOrderAdapterBeans.size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }
    }

    private int getPosition(ManyBuyOrderAdapterBean clickBean){
        int pos = -1;
        if(manyBuyOrderAdapterBeans == null){
            return pos;
        }
        for(int i = 0; i < manyBuyOrderAdapterBeans.size(); i++){
            ManyBuyOrderAdapterBean manyBuyOrderAdapterBean = manyBuyOrderAdapterBeans.get(i);
            if(manyBuyOrderAdapterBean.getTradeNo().equals(clickBean.getTradeNo())){
                pos = i;
            }
        }
        return pos;
    }


    /**
     * 未付款订单adapter
     */
    class ManyOrderListDelegate implements ItemViewDelegate<ManyBuyOrderAdapterBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.many_buy_order_list_item;
        }

        @Override
        public boolean isForViewType(ManyBuyOrderAdapterBean item, int position) {
            return item.getState() == 1;
        }

        @Override
        public void convert(ViewHolder holder, ManyBuyOrderAdapterBean orderBean, int position) {
            RecyclerView rvChild = holder.getView(R.id.rv_many_buy_order_list);
            rvChild.setLayoutManager(new LinearLayoutManager(mContext));
            OrderListAdapter orderListAdapter = new OrderListAdapter(mContext, R.layout.many_buy_oder_list_child_item, orderBean.getManyBuyOrderBeanList());
            rvChild.setAdapter(orderListAdapter);
            orderListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    //跳转订单详情
                    Intent intent = new Intent(mContext, ManyBuyOrderDetailActivity.class);
                    intent.putExtra(ManyBuyOrderDetailActivity.TRADE_NO, orderBean.getTradeNo());
                    intent.putExtra(ManyBuyOrderDetailActivity.ORDER_NO, orderBean.getManyBuyOrderBeanList().get(0).getOrderNo());
                    intent.putExtra(ManyBuyOrderDetailActivity.ORDER_STATE, orderBean.getManyBuyOrderBeanList().get(0).getState());
                    int paymount = 0;//订单总额
                    Map<String, Integer> map = new HashMap<>();
                    for(ManyBuyOrderBean.ResultBean resultBean : orderBean.getManyBuyOrderBeanList()){
                        paymount += resultBean.getPayAmount();
                        map.put(resultBean.getMerchantName(), resultBean.getPostage());
                    }
                    intent.putExtra(ManyBuyOrderDetailActivity.ORDER_PAY_AMOUNT, paymount);
                    ActivityUtils.startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            int postage = 0;//邮费
            int paymount = 0;//订单总额
            Map<String, Integer> map = new HashMap<>();
            int num = 0;
            for(ManyBuyOrderBean.ResultBean resultBean : orderBean.getManyBuyOrderBeanList()){
                paymount += resultBean.getPayAmount();
                num += resultBean.getCommodityQuantity();
                map.put(resultBean.getMerchantName(), resultBean.getPostage());
            }
            for (String key : map.keySet()){
                postage += map.get(key);
            }
            if(postage == 0){
                holder.setText(R.id.tv_many_buy_order_list_postage, "免运费");
            }else {
                holder.setText(R.id.tv_many_buy_order_list_postage, "运费:¥" + PriceUtil.dividePrice(postage));
            }
            holder.setText(R.id.tv_many_buy_order_list_money, "订单总额：¥" + PriceUtil.dividePrice(paymount));
            holder.setText(R.id.tv_many_buy_order_list_num, "商品总计：" + num + "件");
            int state = orderBean.getManyBuyOrderBeanList().get(0).getState();
            int finalPaymount = paymount;
            if(state == 1){
                holder.setText(R.id.tv_order_list_right, "立即付款");
                holder.setText(R.id.tv_order_list_center, "取消订单");
                holder.getView(R.id.tv_order_list_right).setVisibility(View.VISIBLE);
                holder.getView(R.id.tv_order_list_center).setVisibility(View.VISIBLE);
                holder.setOnClickListener(R.id.tv_order_list_right, v -> {
                    Intent intent = new Intent(mContext, PayOrderActivity.class);
                    intent.putExtra(PayOrderActivity.TOTAL_PRICE, finalPaymount + orderBean.getManyBuyOrderBeanList().get(0).getPostage());
                    intent.putExtra(PayOrderActivity.ORDER_NUMBER, orderBean.getTradeNo());
                    intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.MANY_TYPE);
                    intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
                    ActivityUtils.startActivity(intent);
                });
                holder.setOnClickListener(R.id.tv_order_list_center, v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定取消订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickBean = orderBean;
                            if(orderBean.getManyBuyOrderBeanList() != null && orderBean.getManyBuyOrderBeanList().size() != 0){
                                mPresenter.cancelOrder(orderBean.getManyBuyOrderBeanList().get(0).getOrderNo(), OrderType.MANY_TYPE);
                            }
                        }
                    }).show();

                });
            }else {
                holder.setText(R.id.tv_order_list_right, "删除订单");
                holder.getView(R.id.tv_order_list_right).setVisibility(View.VISIBLE);
                holder.getView(R.id.tv_order_list_center).setVisibility(View.GONE);
                holder.setOnClickListener(R.id.tv_order_list_right, v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickBean = orderBean;
                            mPresenter.deleteOrder(orderBean.getManyBuyOrderBeanList().get(0).getOrderNo(), OrderType.MANY_TYPE);
                        }
                    }).show();

                });
            }
        }
    }

    /**
     * 除了未付款订单之外的所有的订单adapter
     */
    class SingleOrderDelegate implements ItemViewDelegate<ManyBuyOrderAdapterBean>{

        @Override
        public int getItemViewLayoutId() {
            return R.layout.many_buy_oder_single_child_item;
        }

        @Override
        public boolean isForViewType(ManyBuyOrderAdapterBean item, int position) {
            return item.getState() != 1;
        }

        @Override
        public void convert(ViewHolder holder, ManyBuyOrderAdapterBean orderBean, int position) {
            ManyBuyOrderBean.ResultBean resultBean = orderBean.getManyBuyOrderBeanList().get(0);
            holder.setText(R.id.tv_many_buy_order_shop_name, resultBean.getMerchantName());
            holder.setText(R.id.tv_many_buy_order_name, resultBean.getGoodsName());
            holder.setText(R.id.tv_many_buy_order_sku, resultBean.getCommodityModel() + " " + resultBean.getCommoditySpec());
            holder.setText(R.id.tv_many_buy_order_price, getResources().getString(R.string.money) + PriceUtil.dividePrice(resultBean.getCommoditySalePrice()));
            holder.setText(R.id.tv_many_buy_order_num, "x" + resultBean.getCommodityQuantity());
            if(resultBean.getPostage() == 0){
                holder.setText(R.id.tv_many_buy_order_list_postage, "免运费");
            }else {
                holder.setText(R.id.tv_many_buy_order_list_postage, "运费:¥" + PriceUtil.dividePrice(resultBean.getPostage()));
            }
            holder.setText(R.id.tv_many_buy_order_list_money, "订单总额：¥" + PriceUtil.dividePrice(resultBean.getPayAmount()));
            int num = 0;
            for(ManyBuyOrderBean.ResultBean resultBean1 : orderBean.getManyBuyOrderBeanList()){
                num += resultBean1.getCommodityQuantity();
            }
            holder.setText(R.id.tv_many_buy_order_list_num, "商品总计：" + num + "件");
            ImageView ivLogo = holder.getView(R.id.iv_many_buy_order_logo);
            GlideUtil.showRadius(mContext, resultBean.getGoodsImageUrl(), 2, ivLogo);
            TextView tvType = holder.getView(R.id.tv_many_buy_order_type);
            TextView btnRight = holder.getView(R.id.tv_order_list_right);//最右边按钮
            TextView btnCenter = holder.getView(R.id.tv_order_list_center);//中间按钮
            //订单类型  全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
            if(resultBean.getState() == 1){//待付款
                tvType.setText("待付款");
                btnCenter.setVisibility(View.VISIBLE);
                btnCenter.setText("取消订单");
                btnRight.setText("立即付款");
                btnRight.setOnClickListener(v -> {
                    //立即付款
                    Intent intent = new Intent(mContext, PayOrderActivity.class);
                    intent.putExtra(PayOrderActivity.TOTAL_PRICE, resultBean.getPayAmount());
                    intent.putExtra(PayOrderActivity.ORDER_NUMBER, orderBean.getTradeNo());
                    intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.MANY_TYPE);
                    intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
                    ActivityUtils.startActivity(intent);
                });
                btnCenter.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定取消订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickBean = orderBean;
                            mPresenter.cancelOrder(resultBean.getOrderNo(), OrderType.MANY_TYPE);
                        }
                    }).show();
                });
            }else if(resultBean.getState() == 2){//待发货
                tvType.setText("待发货");
                btnCenter.setVisibility(View.GONE);
                btnRight.setText("提醒发货");
                btnRight.setOnClickListener(v -> mPresenter.remindShip(orderBean.getTradeNo(), OrderType.MANY_TYPE));
            }else if(resultBean.getState() == 3){
                tvType.setText("待收货");
                btnCenter.setVisibility(View.VISIBLE);
                btnCenter.setText("查看物流");
                btnCenter.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    intent.putExtra(LogisticsActivity.ORDER_NO, resultBean.getOrderNo());
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.MANY_TYPE);
                    ActivityUtils.startActivity(intent);
                });
                btnRight.setText("确认收货");
                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", getResources().getString(R.string.order_dialog_sure_content),"确定", (dialog, confirm) -> {
                        if(confirm){
                            clickBean = orderBean;
                            mPresenter.completeOrder(resultBean.getOrderNo(), OrderType.MANY_TYPE);
                        }
                    }).show();
                });
            }else if(resultBean.getState() == 4){
                tvType.setText("交易取消");
                btnCenter.setVisibility(View.GONE);
                btnRight.setText("删除订单");
                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickBean = orderBean;
                            mPresenter.deleteOrder(resultBean.getOrderNo(), OrderType.MANY_TYPE);
                        }
                    }).show();
                });
            }else if(resultBean.getState() == 5){//拼团失败
                tvType.setText("交易完成");
                btnCenter.setVisibility(View.VISIBLE);
                btnCenter.setText("查看物流");
                btnCenter.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    intent.putExtra(LogisticsActivity.ORDER_NO, resultBean.getOrderNo());
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.MANY_TYPE);
                    ActivityUtils.startActivity(intent);
                });
                btnRight.setText("删除订单");
                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickBean = orderBean;
                            mPresenter.deleteOrder(resultBean.getOrderNo(), OrderType.MANY_TYPE);
                        }
                    }).show();
                });
            }
        }
    }


    /**
     *  未付款订单的Adapter
     */
    class OrderListAdapter extends CommonAdapter<ManyBuyOrderBean.ResultBean>{

        private List<ManyBuyOrderBean.ResultBean> manyBuyOrderBeans;

        public OrderListAdapter(Context context, int layoutId, List<ManyBuyOrderBean.ResultBean> datas) {
            super(context, layoutId, datas);
            manyBuyOrderBeans = datas;
        }

        @Override
        protected void convert(ViewHolder holder, ManyBuyOrderBean.ResultBean orderBean, int position) {
            holder.setText(R.id.tv_many_buy_order_shop_name, orderBean.getMerchantName());
            holder.setText(R.id.tv_many_buy_order_name, orderBean.getGoodsName());
            holder.setText(R.id.tv_many_buy_order_sku, orderBean.getCommodityModel() + " " + orderBean.getCommoditySpec());
            holder.setText(R.id.tv_many_buy_order_price, getResources().getString(R.string.money) + PriceUtil.dividePrice(orderBean.getCommoditySalePrice()));
            holder.setText(R.id.tv_many_buy_order_num, "x" + orderBean.getCommodityQuantity());
            holder.setText(R.id.tv_many_goods_xiadan_real_price, "原价¥" + PriceUtil.dividePrice(orderBean.getCommoditySalePrice()));
            ImageView ivLogo = holder.getView(R.id.iv_many_buy_order_logo);
            GlideUtil.showRadius(mContext, orderBean.getGoodsImageUrl(), 2, ivLogo);
            RelativeLayout rlShopName = holder.getView(R.id.rl_many_buy_order_list_title);
            TextView tvType = holder.getView(R.id.tv_many_buy_order_type);
            tvType.setText("待付款");
            if(orderBean.getState() == 1){//待付款
                tvType.setText("待付款");
            }else if(orderBean.getState() == 2){//待发货
                tvType.setText("待发货");
            }else if(orderBean.getState() == 3){
                tvType.setText("待收货");
            }else if(orderBean.getState() == 4){
                tvType.setText("交易取消");
            }else if(orderBean.getState() == 5){//拼团失败
                tvType.setText("交易完成");
            }
            if(position != 0){
                ManyBuyOrderBean.ResultBean beforeBean = manyBuyOrderBeans.get(position - 1);
                ManyBuyOrderBean.ResultBean afterBean = manyBuyOrderBeans.get(position);
                if(afterBean.getMerchantName().equals(beforeBean.getMerchantName())){
                    rlShopName.setVisibility(View.GONE);
                }else {
                    rlShopName.setVisibility(View.VISIBLE);
                }
            }else {
                rlShopName.setVisibility(View.VISIBLE);
            }
        }
    }


}
