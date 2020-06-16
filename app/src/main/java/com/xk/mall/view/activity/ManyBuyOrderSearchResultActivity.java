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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ManyBuyOrderAdapterBean;
import com.xk.mall.model.entity.ManyBuyOrderBean;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.eventbean.RefreshOrderListEvent;
import com.xk.mall.model.impl.ManyBuyOrderViewImpl;
import com.xk.mall.model.impl.WuGOrderViewImpl;
import com.xk.mall.presenter.ManyBuyOrderPresenter;
import com.xk.mall.presenter.WuGOrderPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
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
import butterknife.OnClick;

/**
 * ClassName ManyBuyOrderSearchResultActivity
 * Description 多买多折订单搜索结果页
 * Author
 * Date
 * Version V1.0
 */
public class ManyBuyOrderSearchResultActivity extends BaseActivity<ManyBuyOrderPresenter> implements ManyBuyOrderViewImpl {
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
    List<ManyBuyOrderAdapterBean> manyBuyOrderAdapterBeans;
    private MultiItemTypeAdapter multiItemTypeAdapter;
    private ManyBuyOrderAdapterBean clickBean;//点击的地址
    private String account;


    @Override
    protected ManyBuyOrderPresenter createPresenter() {
        return new ManyBuyOrderPresenter(this);
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
            leftPrice = localLeftPrice;
        }
        int localRightPrice = getIntent().getIntExtra(RIGHT_PRICE, 0);
        if(localRightPrice != 0){
            rightPrice = localRightPrice;
        }
        timeFlag = getIntent().getIntExtra(TIME_FLAG, 0);
        account = SPUtils.getInstance().getString(Constant.USER_MOBILE);
        MyApplication.getInstance().addActivity(ManyBuyOrderSearchResultActivity.this);
        //进入退出效果 注意这里 创建的效果对象是 Fade()
        getWindow().setEnterTransition(new Fade().setDuration(200));
        getWindow().setExitTransition(new Fade().setDuration(200));
        manyBuyOrderAdapterBeans = new ArrayList<>();
        multiItemTypeAdapter = new MultiItemTypeAdapter(mContext, manyBuyOrderAdapterBeans);
        multiItemTypeAdapter.addItemViewDelegate(new ManyOrderListDelegate());
        multiItemTypeAdapter.addItemViewDelegate(new SingleOrderDelegate());
        recycleView.setAdapter(multiItemTypeAdapter);
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

    /**
     * 搜索订单
     */
    private void searchOrder(){
        mPresenter.getOrderList(searchName, account,state,timeFlag, leftPrice, rightPrice, page,Constant.limit);
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
    public void onGetManyBuyOrderListSuccess(BaseModel<ManyBuyOrderBean> model) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
        if(model.getData() != null && model.getData().getResult() != null){
            if(page == 1){
                manyBuyOrderAdapterBeans.clear();
            }
            if(page == 1 && model.getData().getResult().size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }else {
                bindRv(model.getData().getResult());
                if(model.getData().getResult().size() < Constant.limit){
                    smartRefreshLayout.setEnableLoadMore(false);
                }else {
                    smartRefreshLayout.setEnableLoadMore(true);
                }
            }

        }else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    private void bindRv(List<ManyBuyOrderBean.ResultBean> resultBeans){
        //排序有问题
        Map<String, Boolean> filter = new LinkedHashMap<>();
        for(ManyBuyOrderBean.ResultBean resultBean : resultBeans){
            filter.put(resultBean.getTradeNo(), true);
        }

        //处理所有的主订单ID相同的数据
        List<ManyBuyOrderBean.ResultBean> tempChildBean = new ArrayList<>();
        for(String key : filter.keySet()){
            List<ManyBuyOrderBean.ResultBean> childBean = new ArrayList<>();
            for(ManyBuyOrderBean.ResultBean resultBean : resultBeans){
                if(resultBean.getState() == 1 && key.equals(resultBean.getTradeNo())){
                    //当主订单ID相同的数据放在一个list里面
                    childBean.add(resultBean);
                }
            }
            if(childBean.size() != 0){
                ManyBuyOrderAdapterBean manyBuyOrderAdapterBean = new ManyBuyOrderAdapterBean();
                manyBuyOrderAdapterBean.setManyBuyOrderBeanList(childBean);
                if(childBean.size() == 0 || childBean.size() == 1){
                    manyBuyOrderAdapterBean.setState(2);
                }else {
                    manyBuyOrderAdapterBean.setState(1);
                }
                manyBuyOrderAdapterBean.setTradeNo(key);
                manyBuyOrderAdapterBeans.add(manyBuyOrderAdapterBean);
                tempChildBean.addAll(childBean);
            }
        }
        //处理单个的数据
        resultBeans.removeAll(tempChildBean);
        for (ManyBuyOrderBean.ResultBean resultBean : resultBeans){
            List<ManyBuyOrderBean.ResultBean> childBean = new ArrayList<>();
            childBean.add(resultBean);
            ManyBuyOrderAdapterBean manyBuyOrderAdapterBean = new ManyBuyOrderAdapterBean();
            manyBuyOrderAdapterBean.setManyBuyOrderBeanList(childBean);
            if(childBean.size() == 0 || childBean.size() == 1){
                manyBuyOrderAdapterBean.setState(2);
            }else {
                manyBuyOrderAdapterBean.setState(1);
            }
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
        ToastUtils.showShort("延长收货成功");
    }

    @Override
    public void onCompleteOrderSuccess(BaseModel<String> model) {
        ToastUtils.showShort(model.getMsg());
        //确认收货
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
            for(ManyBuyOrderBean.ResultBean resultBean : orderBean.getManyBuyOrderBeanList()){
                paymount += resultBean.getPayAmount();
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
            holder.setText(R.id.tv_many_buy_order_list_money, "订单总额:" + PriceUtil.dividePrice(paymount));
            holder.setText(R.id.tv_many_buy_order_list_num, "商品总件:" + orderBean.getManyBuyOrderBeanList().size() + "件");
            int state = orderBean.getManyBuyOrderBeanList().get(0).getState();
            int finalPaymount = paymount;
            if(state == 1){
                holder.setText(R.id.tv_order_list_right, "立即付款");
                holder.setText(R.id.tv_order_list_center, "取消订单");
                holder.getView(R.id.tv_order_list_right).setVisibility(View.VISIBLE);
                holder.getView(R.id.tv_order_list_center).setVisibility(View.VISIBLE);
//                holder.setOnClickListener(R.id.tv_order_list_right, v -> {
//                    Intent intent = new Intent(mContext, PayOrderActivity.class);
//                    intent.putExtra(PayOrderActivity.GOODS_NAME, orderBean.getManyBuyOrderBeanList().get(0).getGoodsName());
//                    intent.putExtra(PayOrderActivity.TOTAL_PRICE, finalPaymount + orderBean.getManyBuyOrderBeanList().get(0).getPostage());
//                    intent.putExtra(PayOrderActivity.ORDER_NUMBER, orderBean.getTradeNo());
//                    intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.MANY_TYPE);
//                    intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
//                    ActivityUtils.startActivity(intent);
//                });
//                holder.setOnClickListener(R.id.tv_order_list_center, v -> {
//                    clickBean = orderBean;
//                    if(orderBean.getManyBuyOrderBeanList() != null && orderBean.getManyBuyOrderBeanList().size() != 0){
//                        mPresenter.cancelOrder(orderBean.getManyBuyOrderBeanList().get(0).getOrderNo(), OrderType.MANY_TYPE);
//                    }
//                });
            }else {
                holder.setText(R.id.tv_order_list_right, "删除订单");
                holder.getView(R.id.tv_order_list_right).setVisibility(View.VISIBLE);
                holder.getView(R.id.tv_order_list_center).setVisibility(View.GONE);
//                holder.setOnClickListener(R.id.tv_order_list_right, v -> {
//                    clickBean = orderBean;
//                    mPresenter.deleteOrder(orderBean.getManyBuyOrderBeanList().get(0).getOrderNo(), OrderType.MANY_TYPE);
//                });
            }
        }
    }

    /**
     * 除了未付款订单之外的所有的订单adapter
     */
    class SingleOrderDelegate implements ItemViewDelegate<ManyBuyOrderAdapterBean> {

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
            holder.setText(R.id.tv_many_buy_order_sku, resultBean.getCommoditySpec() + " " + resultBean.getCommodityModel());
            holder.setText(R.id.tv_many_buy_order_price, getResources().getString(R.string.money) + PriceUtil.dividePrice(resultBean.getCommoditySalePrice()));
            holder.setText(R.id.tv_many_buy_order_num, "x" + resultBean.getCommodityQuantity());
            holder.setText(R.id.tv_many_goods_xiadan_real_price, "原价:¥" + PriceUtil.dividePrice(resultBean.getCommoditySalePrice()));
            if(resultBean.getPostage() == 0){
                holder.setText(R.id.tv_many_buy_order_list_postage, "运费:¥" + PriceUtil.dividePrice(resultBean.getPostage()));
            }else {
                holder.setText(R.id.tv_many_buy_order_list_postage, "免运费");
            }
            holder.setText(R.id.tv_many_buy_order_list_money, "订单总额:" + PriceUtil.dividePrice(resultBean.getPayAmount()));
            holder.setText(R.id.tv_many_buy_order_list_num, "商品总件:" + orderBean.getManyBuyOrderBeanList().size() + "件");
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
//                btnRight.setOnClickListener(v -> {
//                    //立即付款
//                    Intent intent = new Intent(mContext, PayOrderActivity.class);
//                    intent.putExtra(PayOrderActivity.GOODS_NAME, resultBean.getGoodsName());
//                    intent.putExtra(PayOrderActivity.TOTAL_PRICE, resultBean.getPayAmount() + resultBean.getPostage());
//                    intent.putExtra(PayOrderActivity.ORDER_NUMBER, resultBean.getOrderNo());
//                    intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.MANY_TYPE);
//                    ActivityUtils.startActivity(intent);
//                });
//                btnCenter.setOnClickListener(v -> {
//                    clickBean = orderBean;
//                    mPresenter.cancelOrder(resultBean.getOrderNo(), OrderType.MANY_TYPE);
//                });
            }else if(resultBean.getState() == 2){//待发货
                tvType.setText("待发货");
                btnCenter.setVisibility(View.GONE);
                btnRight.setText("提醒发货");
                btnRight.setOnClickListener(v -> mPresenter.remindShip(orderBean.getTradeNo(), OrderType.MANY_TYPE));
            }else if(resultBean.getState() == 3){
                tvType.setText("待收货");
                btnCenter.setVisibility(View.VISIBLE);
                btnCenter.setText("查看物流");
//                btnLeft.setOnClickListener(v -> {
//                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定延长收货时间？","确定", (dialog, confirm) -> {
//                        if(confirm){
//                            mPresenter.extendTheTime(orderBean.getTradeNo(), OrderType.MANY_TYPE);
//                        }
//                    }).show();
//                });

//                btnRight.setOnClickListener(v -> {
//                    clickBean = orderBean;
//                    mPresenter.completeOrder(orderBean.getTradeNo(), OrderType.MANY_TYPE);
//                });
            }else if(resultBean.getState() == 4){
                tvType.setText("交易取消");
                btnCenter.setVisibility(View.GONE);
                btnRight.setText("删除订单");
//                btnRight.setOnClickListener(v -> {
//                    clickBean = orderBean;
//                    mPresenter.deleteOrder(resultBean.getOrderNo(), OrderType.MANY_TYPE);
//                });
            }else if(resultBean.getState() == 5){//拼团失败
                tvType.setText("交易完成");
                btnCenter.setVisibility(View.GONE);
                btnRight.setText("删除订单");
//                btnRight.setOnClickListener(v -> {
//                    clickBean = orderBean;
//                    mPresenter.deleteOrder(resultBean.getOrderNo(), OrderType.MANY_TYPE);
//                });
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
            holder.setText(R.id.tv_many_buy_order_sku, orderBean.getCommoditySpec() + " " + orderBean.getCommodityModel());
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
