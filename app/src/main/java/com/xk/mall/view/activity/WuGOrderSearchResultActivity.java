package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.transition.Fade;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.eventbean.OtherPaySuccessBean;
import com.xk.mall.model.eventbean.PaySuccessBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.WuGOrderViewImpl;
import com.xk.mall.presenter.WuGOrderPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.widget.OrderTipDialog;
import com.xk.mall.view.widget.WuGOrderTipDialog;
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
public class WuGOrderSearchResultActivity extends BaseActivity<WuGOrderPresenter> implements WuGOrderViewImpl {
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
    private String leftPrice;
    private String rightPrice;
    private String searchName;
    private int timeFlag;//时间类型
    private int page = 1;
    /**全球买手和吾G订单*/
    private OrderListAdapter orderListAdapter;
    private List<OrderBean> orderBeanList;
    private int clickPos = -1;//点击的位置
    private String account;


    @Override
    protected WuGOrderPresenter createPresenter() {
        return new WuGOrderPresenter(this);
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
            leftPrice = String.valueOf(localLeftPrice);
        }
        int localRightPrice = getIntent().getIntExtra(RIGHT_PRICE, 0);
        if(localRightPrice != 0){
            rightPrice = String.valueOf(localRightPrice);
        }
        timeFlag = getIntent().getIntExtra(TIME_FLAG, 0);
        account = SPUtils.getInstance().getString(Constant.USER_MOBILE);
        MyApplication.getInstance().addActivity(WuGOrderSearchResultActivity.this);
        //进入退出效果 注意这里 创建的效果对象是 Fade()
        getWindow().setEnterTransition(new Fade().setDuration(200));
        getWindow().setExitTransition(new Fade().setDuration(200));
        orderBeanList = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(mContext, R.layout.order_list_item, orderBeanList);
        recycleView.setAdapter(orderListAdapter);
        searchOrder();
        smartRefreshLayout.setEnableRefresh(false);

        orderListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //点击跳转订单详情
                OrderBean bean = orderBeanList.get(position);
                Intent intent = new Intent(mContext, WuGOrderDetailActivity.class);
                intent.putExtra(WuGOrderDetailActivity.ORDER_TYPE, bean.getState());
                intent.putExtra(WuGOrderDetailActivity.ORDER_NO, bean.getOrderNo());
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        editSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(editSearch.getText().toString().trim())) {
                searchName = editSearch.getText().toString().trim();
                leftPrice = "";
                rightPrice = "";
                searchOrder();
            }
            return false;
        });

        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            searchOrder();
        });

    }

    //订单类型;1-0元竞拍订单;2-多买多折订单;3-喜立得订单;4-吾G订单;5-全球买手订单;6-定制拼团订单;7:OTO订单
//    public static int ZERO_TYPE = 1;
//    public static int MANY_TYPE = 2;
//    public static int CUT_TYPE = 3;
//    public static int WUG_TYPE = 4;
//    public static int GLOBAL_TYPE = 5;
//    public static int GROUP_TYPE = 6;
//    public static int OTO_TYPE = 7;

    /**
     * 搜索订单
     */
    private void searchOrder(){
        mPresenter.getOrderList(searchName, account, state,timeFlag, leftPrice, rightPrice, page,Constant.limit);
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

    @Override
    public void onGetGlobalBuyerOrderListSuccess(BaseModel<OrderPageBean> model) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
        if(model.getData() != null && model.getData().result != null){
            if(page == 1){
                orderBeanList.clear();
            }
            orderBeanList.addAll(model.getData().result);
            orderListAdapter.notifyDataSetChanged();
            if(model.getData().result.size() < Constant.limit){
                smartRefreshLayout.setEnableLoadMore(false);
            }else {
                smartRefreshLayout.setEnableLoadMore(true);
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
            EventBus.getDefault().post(new RefreshOrderListEvent(false,false, orderBeanList.get(clickPos).getOrderNo()));
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
            EventBus.getDefault().post(new RefreshOrderListEvent(false,true, orderBeanList.get(clickPos).getOrderNo()));
            orderListAdapter.notifyItemChanged(clickPos);
            clickPos = -1;
        }
    }

    @Override
    public void onDeleteSuccess(BaseModel<String> model) {
        if(clickPos != -1){
            EventBus.getDefault().post(new RefreshOrderListEvent(true,false, orderBeanList.get(clickPos).getOrderNo()));
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
            //取消订单
            selectOrderBean.setState(4);
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
            if(orderBean.getState() == 1){//待付款
                tvType.setText("待付款");
                btnLeft.setVisibility(View.GONE);
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
                    intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                    intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, true);
                    intent.putExtra(PayOrderActivity.COUPON_VALUE, orderBean.getCouponAmount());
                    ActivityUtils.startActivity(intent);
                });
                btnCenter.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, OtherPayActivity.class);
                    intent.putExtra(OtherPayActivity.GOODS_NAME, orderBean.getGoodsName());
                    intent.putExtra(OtherPayActivity.TOTAL_PRICE, orderBean.getPayAmount());
                    intent.putExtra(OtherPayActivity.ORDER_NUMBER, orderBean.getOrderNo());
                    intent.putExtra(OtherPayActivity.PAYMENT_ID, orderBean.getPartnerId());
                    intent.putExtra(OtherPayActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                    ActivityUtils.startActivity(intent);
                });

                btnLeft.setOnClickListener(v -> {
                    clickPos = getOrderBeanPosition(orderBean);
                    mPresenter.cancelOrder(orderBean.getOrderNo(), OrderType.WUG_TYPE);
                });
            }else if(orderBean.getState() == 2){//待发货
                tvType.setText("待发货");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("提醒发货");
                btnRight.setOnClickListener(v -> mPresenter.remindShip(orderBean.getOrderNo(), OrderType.WUG_TYPE));
            }else if(orderBean.getState() == 3){
                tvType.setText("待收货");
                btnCenter.setVisibility(View.VISIBLE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnCenter.setText("查看物流");
                btnCenter.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                    ActivityUtils.startActivity(intent);
                });
                btnRight.setText("确认收货");
                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", getResources().getString(R.string.order_dialog_sure_content),
                            "确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            mPresenter.completeOrder(orderBean.getOrderNo(), OrderType.WUG_TYPE);
                        }
                    }).show();
                });
            }else if(orderBean.getState() == 4){
                tvType.setText("已取消");
                btnCenter.setVisibility(View.GONE);
                btnCenter.setText("查看物流");
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("删除订单");
                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            mPresenter.deleteOrder(orderBean.getOrderNo(), OrderType.WUG_TYPE);
                        }
                    }).show();
                });
            }else if(orderBean.getState() == 5){
                tvType.setText("已完成");
                btnCenter.setVisibility(View.GONE);
                btnRight.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.WUG_TYPE);
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
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            mPresenter.deleteOrder(orderBean.getOrderNo(), OrderType.WUG_TYPE);
                        }
                    }).show();
                });
            }else if(orderBean.getState() == 15){//交易完结
                tvType.setText("交易完结");
                btnCenter.setVisibility(View.VISIBLE);
                btnCenter.setText("查看物流");
                btnCenter.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    intent.putExtra(LogisticsActivity.ORDER_NO, orderBean.getOrderNo());
                    intent.putExtra(LogisticsActivity.ORDER_TYPE, OrderType.WUG_TYPE);
                    ActivityUtils.startActivity(intent);
                });
                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("删除订单");
                btnRight.setOnClickListener(v -> {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定删除订单吗？","确定", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = getOrderBeanPosition(orderBean);
                            mPresenter.deleteOrder(orderBean.getOrderNo(), OrderType.WUG_TYPE);
                        }
                    }).show();
                });
            }

        }
    }
}
