package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.Fade;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.FightGroupOrderBean;
import com.xk.mall.model.entity.ZeroOrderBean;
import com.xk.mall.model.eventbean.EmptyOrderListBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.FightGroupOrderImpl;
import com.xk.mall.model.impl.ZeroOrderImpl;
import com.xk.mall.model.request.CancelOrderRequestBody;
import com.xk.mall.model.request.DeleteOrderRequestBody;
import com.xk.mall.presenter.GroupOrderPresenter;
import com.xk.mall.presenter.ZeroOrderPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
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
 * ClassName OrderSearchResultActivity
 * Description 订单搜索结果页
 * Author
 * Date
 * Version V1.0
 */
public class GroupOrderSearchResultActivity extends BaseActivity<GroupOrderPresenter> implements FightGroupOrderImpl {
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
    private List<FightGroupOrderBean.ResultBean> listData = new ArrayList<>();
    private  OrderListAdapter orderListAdapter;
    private int operationIndex;//操作的position


    @Override
    protected GroupOrderPresenter createPresenter() {
        return new GroupOrderPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_search_result;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
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
        MyApplication.getInstance().addActivity(GroupOrderSearchResultActivity.this);
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
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
    public void onGetOrderListDataSuc(BaseModel<FightGroupOrderBean> model) {
        smartRefreshLayout.finishRefresh(1200);
        smartRefreshLayout.finishLoadMore(1200);

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

    //取消订单
    @Override
    public void onGetCancelOrderSuc(BaseModel<String> model) {
        listData.get(operationIndex).setState(4);//已取消
        orderListAdapter.notifyDataSetChanged();
        EventBus.getDefault().post(new RefreshOrderListEvent(false,false, listData.get(operationIndex).getOrderNo()));
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
        EventBus.getDefault().post(new RefreshOrderListEvent(false,true, listData.get(operationIndex).getOrderNo()));
    }

    @Override
    public void onGetDeleteOrderSuc(BaseModel<String> model) {
        EventBus.getDefault().post(new RefreshOrderListEvent(true,false, listData.get(operationIndex).getOrderNo()));
        listData.remove(operationIndex);
        orderListAdapter.notifyItemRemoved(operationIndex);
        orderListAdapter.notifyDataSetChanged();
        com.blankj.utilcode.util.ToastUtils.showShort("删除成功");
        if(listData.size()==0){
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
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
                tvPostage.setText("运费:¥" + PriceUtil.dividePrice(orderBean.getPostage()));
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
                //取消订单
                btnCenter.setOnClickListener(v -> {
                    operationIndex=position;
                    cancelOrder(orderBean);
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
                        operationIndex=position;
                        deleteOrder(orderBean);
                    }
                });

            }else if (orderBean.getState() == 5) {//已完成
                tvType.setText("交易完成");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("删除订单");
            } else if (orderBean.getState() == 10) {//拼团失败
                tvType.setText("拼团失败");
                btnCenter.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("删除订单");
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
