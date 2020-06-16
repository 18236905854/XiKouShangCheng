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
import com.blankj.utilcode.util.ToastUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.eventbean.EmptyOrderListBean;
import com.xk.mall.model.eventbean.OtherPaySuccessBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.SellConsignmentViewImpl;
import com.xk.mall.model.impl.WuGOrderViewImpl;
import com.xk.mall.presenter.SellConsignmentPresenter;
import com.xk.mall.presenter.WuGOrderPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
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
 * Description 寄卖订单搜索结果页面
 * Author
 * Date
 * Version V1.0
 */
public class SellOrderSearchResultActivity extends BaseActivity<SellConsignmentPresenter> implements SellConsignmentViewImpl {
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
    private int state;//订单类型  0全部、1待付款、2待发货、3待收货
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
    protected SellConsignmentPresenter createPresenter() {
        return new SellConsignmentPresenter(this);
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
        MyApplication.getInstance().addActivity(SellOrderSearchResultActivity.this);
        //进入退出效果 注意这里 创建的效果对象是 Fade()
        getWindow().setEnterTransition(new Fade().setDuration(200));
        getWindow().setExitTransition(new Fade().setDuration(200));
        orderBeanList = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(mContext, R.layout.sell_consignment_order_list_item, orderBeanList);
        recycleView.setAdapter(orderListAdapter);
        orderListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                OrderBean orderBean = orderBeanList.get(position);
                Intent intent = new Intent(mContext, SellOrderDetailActivity.class);
                intent.putExtra(SellOrderDetailActivity.ORDER_NO, orderBean.getOrderNo());
                intent.putExtra(SellOrderDetailActivity.ORDER_TYPE, orderBean.getState());
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        searchOrder();
        smartRefreshLayout.setEnableRefresh(false);
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
        mPresenter.getSellConsignmentSearchOrderList(account, searchName, state,timeFlag, leftPrice, rightPrice, page,Constant.limit);
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
    public void onGetSellConsignmentOrderListSuccess(BaseModel<OrderPageBean> model) {
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
                EventBus.getDefault().post(new EmptyOrderListBean());
            }
        }else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
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
            holder.setText(R.id.tv_order_list_money, getResources().getString(R.string.money) + PriceUtil.dividePrice(orderBean.getPayAmount()));
            holder.setText(R.id.tv_order_list_num, "x" + orderBean.getCommodityQuantity());
            holder.setText(R.id.tv_order_list_sku, orderBean.getCommodityModel() + " " + orderBean.getCommoditySpec());
            ImageView ivLogo = holder.getView(R.id.iv_order_list_logo);
            GlideUtil.showRadius(mContext, orderBean.getGoodsImageUrl(), 2, ivLogo);
            TextView tvType = holder.getView(R.id.tv_order_list_type);
            //订单类型  0全部、1待付款、2待发货、3待收货、4已完成、5已取消
            if(orderBean.getState() == 1){//待付款
                tvType.setText("待付款");
            }else if(orderBean.getState() == 2){//待发货
                tvType.setText("待发货");
            }else if(orderBean.getState() == 3){
                tvType.setText("待收货");
            }else if(orderBean.getState() == 4){
                tvType.setText("已取消");
            }else if(orderBean.getState() == 5){
                tvType.setText("已完成");
            }else if(orderBean.getState() == 6){
                tvType.setText("已关闭");
            }else if(orderBean.getState() == 7){
                tvType.setText("待确认");
            }else if(orderBean.getState() == 11){
                tvType.setText("已寄卖");
            }else if(orderBean.getState() == 15){
                tvType.setText("交易完结");
            }
        }
    }
}
