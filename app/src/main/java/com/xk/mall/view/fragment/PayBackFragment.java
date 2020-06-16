package com.xk.mall.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.impl.PayBackViewImpl;
import com.xk.mall.presenter.PayBackPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.activity.PayBackDetailActivity;
import com.xk.mall.view.activity.PayBackPostActivity;
import com.xk.mall.view.widget.SellOrderTipDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName: PayBackFragment
 * @Description: 退款售后订单
 * @Author: 卿凯
 * @Date: 2019/12/8/008 15:03
 * @Version: 1.0
 */
public class PayBackFragment extends BaseFragment<PayBackPresenter> implements PayBackViewImpl {

    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @BindView(R.id.refresh_order)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.state_view_order)
    MultiStateView multiStateView;
    private int page = 1;//页数
    private int limit = 10;//每页的条数
    private OrderListAdapter orderListAdapter;
    private List<OrderBean> orderBeanList;
    private int orderType = 0;//用来请求数据的ID
    private int clickPos;//点击的位置

    public static PayBackFragment getInstance(int orderType) {
        PayBackFragment fragment = new PayBackFragment();
        fragment.orderType = orderType;
        return fragment;
    }

    @Override
    protected PayBackPresenter createPresenter() {
        return new PayBackPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadLazyData() {
        String phone = SPUtils.getInstance().getString(Constant.USER_MOBILE);
        mPresenter.getPayBackList(phone, orderType, page, limit);
        orderBeanList = new ArrayList<>();
        rvOrder.setLayoutManager(new LinearLayoutManager(mContext));
        orderListAdapter = new OrderListAdapter(mContext, R.layout.pay_back_order_list_item, orderBeanList);
        rvOrder.setAdapter(orderListAdapter);
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getPayBackList(phone, orderType, page, limit);
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getPayBackList(phone, orderType, page, limit);
        });

        orderListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                OrderBean orderBean = orderBeanList.get(position);
                Intent intent = new Intent(mContext, PayBackDetailActivity.class);
                intent.putExtra(PayBackDetailActivity.ACTIVITY_TYPE, orderType);
                intent.putExtra(PayBackDetailActivity.REFUND_ORDER_NO, orderBean.getRefundOrderNo());
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void onGetPayBackListSuccess(BaseModel<OrderPageBean> model) {
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
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cancelSalesReturn(String orderNo){//退款订单号RefundOrderNo
        if(XiKouUtils.isNullOrEmpty(orderNo)){
            return;
        }
        int pos = -1;
        for(int i = 0; i < orderBeanList.size() - 1; i++){
            OrderBean orderBean = orderBeanList.get(i);
            if(orderNo.equals(orderBean.getRefundOrderNo())){
                pos = i;
            }
        }
        if(pos != -1){
            orderBeanList.remove(pos);
            orderListAdapter.notifyItemRemoved(pos);
        }
    }

    @Override
    public void cancelSalesReturnSuccess(BaseModel baseModel) {
        if(baseModel != null){
            ToastUtils.showShort("取消退款成功");
            if(clickPos != -1){
                orderBeanList.remove(clickPos);
                orderListAdapter.notifyDataSetChanged();
            }
            clickPos = -1;
        }
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
            holder.setText(R.id.tv_order_list_pay_amount, "订单总额:¥" + PriceUtil.dividePrice(orderBean.getPayAmount()));
            holder.setText(R.id.tv_pay_back_money, "退款金额:¥" + PriceUtil.dividePrice(orderBean.getRefundAmount()));
            ImageView ivLogo = holder.getView(R.id.iv_order_list_logo);
            GlideUtil.showRadius(mContext, orderBean.getGoodsImageUrl(), 2, ivLogo);
            TextView tvType = holder.getView(R.id.tv_order_list_type);
            TextView btnRight = holder.getView(R.id.tv_order_list_right);//最右边按钮
            //退货状态，0=审核中，1=退款中，2=退款退货中，3=已退款，4=退款失败，5=退款已关闭
            if(orderBean.getRefundStatus() == 0) {//退款中
                tvType.setText("审核中");
                tvType.setTextColor(Color.parseColor("#F94119"));
                btnRight.setVisibility(View.VISIBLE);
            }else if(orderBean.getRefundStatus() == 1){
                tvType.setText("退款中");
                tvType.setTextColor(Color.parseColor("#F94119"));
                btnRight.setVisibility(View.GONE);
            }else if(orderBean.getRefundStatus() == 2) {
                tvType.setText("退款退货中");
                tvType.setTextColor(Color.parseColor("#F94119"));
                btnRight.setVisibility(View.VISIBLE);
            }else if(orderBean.getRefundStatus() == 3){
                tvType.setText("退款成功");
                tvType.setTextColor(Color.parseColor("#999999"));
                btnRight.setVisibility(View.GONE);
            }else if(orderBean.getRefundStatus() == 4){//退款成功
                tvType.setText("退款失败");
                tvType.setTextColor(Color.parseColor("#999999"));
                btnRight.setVisibility(View.VISIBLE);
            }else if(orderBean.getRefundStatus() == 5){
                tvType.setText("退款关闭");
                tvType.setTextColor(Color.parseColor("#999999"));
                btnRight.setVisibility(View.GONE);
            }
            btnRight.setOnClickListener(v ->
                    new SellOrderTipDialog(mContext, R.style.mydialog, "是否确认?", "确定取消订单退款申请吗？",
                    "同意","取消", (dialog, confirm) -> {
                if(confirm){
                    clickPos = position;
                    mPresenter.cancelPayBack(orderBean.getRefundOrderNo());
                }
            }).show());
        }
    }
}
