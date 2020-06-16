package com.xk.mall.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.DPayResultPageBean;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.OtherPayBean;
import com.xk.mall.model.eventbean.DPayResultEvent;
import com.xk.mall.model.eventbean.OtherPaySuccessBean;
import com.xk.mall.model.eventbean.PaySuccessBean;
import com.xk.mall.model.impl.DPayViewImpl;
import com.xk.mall.presenter.DPayPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DateToolUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.activity.PayOrderActivity;
import com.xk.mall.view.widget.WrapContentLinearLayoutManager;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.countdownview.CountdownView;

/**
 * @ClassName: PayFragment
 * @Description: 代付页面Fragment
 * @Author: 卿凯
 * @Date: 2019/8/30/030 14:14
 * @Version: 1.0
 */
public class PayFragment extends BaseFragment<DPayPresenter> implements DPayViewImpl {

    @BindView(R.id.refresh_pay)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rv_pay)
    RecyclerView rvPay;
    @BindView(R.id.state_view_order)
    MultiStateView multiStateView;

    private int state = 1;//订单类型  0 未支付  5 已支付
    private int page = 1;
    private List<DPayResultPageBean.ResultBean.OrderPageUnifiedProcessingModelListBean> data;
    private OtherPayAdapter adapter;

    /**
     * 构造方法
     */
    public static PayFragment getInstance(int titleType) {
        PayFragment fragment = new PayFragment();
        fragment.state = titleType;
        return fragment;
    }

    @Override
    protected DPayPresenter createPresenter() {
        return new DPayPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pay;
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
        data = new ArrayList<>();
        String account = SPUtils.getInstance().getString(Constant.USER_MOBILE);
        mPresenter.getData(account, state, page, Constant.limit);
        rvPay.setLayoutManager(new WrapContentLinearLayoutManager(mContext));
        adapter = new OtherPayAdapter(mContext, R.layout.item_other_pay, data);
        rvPay.setAdapter(adapter);
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getData(account, state, page, Constant.limit);
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getData(account, state, page, Constant.limit);
        });
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOtherPaySuccess(PaySuccessBean otherPaySuccessBean){
        if(data != null){
            int position = -1;
            for(int i = 0; i < data.size(); i++){
                DPayResultPageBean.ResultBean.OrderPageUnifiedProcessingModelListBean orderBean =
                        data.get(i);
                if(orderBean.getOrderNo().equals(otherPaySuccessBean.getOrderNo())){
                    position = i;
                }
            }
            data.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetDataSuccess(BaseModel<DPayResultPageBean> model) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if(model.getData() != null && model.getData().getResult() != null && model.getData().getResult().size() != 0
                && model.getData().getResult().get(0) != null && model.getData().getResult().get(0).getOrderPageUnifiedProcessingModelList() != null &&
                model.getData().getResult().get(0).getOrderPageUnifiedProcessingModelList().size() != 0){
            if(page == 1){
                data.clear();
            }
            data.addAll(model.getData().getResult().get(0).getOrderPageUnifiedProcessingModelList());
            adapter.notifyDataSetChanged();
            if(model.getData().getResult().get(0).getOrderPageUnifiedProcessingModelList().size() < Constant.limit){
                refreshLayout.setEnableLoadMore(false);
            }else {
                refreshLayout.setEnableLoadMore(true);
            }
            if(page == 1 && model.getData().getResult().get(0).getOrderPageUnifiedProcessingModelList().size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }else {
            if(model.getData() != null && model.getData().getResult() != null && model.getData().getResult().size() != 0
                && model.getData().getResult().get(0) != null){
                EventBus.getDefault().post(new DPayResultEvent(model.getData().getResult().get(0).getHasPaid(),
                        model.getData().getResult().get(0).getNotPaid()));
            }
            if(page == 1){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }else {
                refreshLayout.setEnableLoadMore(false);
            }
        }
    }

    class OtherPayAdapter extends CommonAdapter<DPayResultPageBean.ResultBean.OrderPageUnifiedProcessingModelListBean>{

        public OtherPayAdapter(Context context, int layoutId, List<DPayResultPageBean.ResultBean.OrderPageUnifiedProcessingModelListBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, DPayResultPageBean.ResultBean.OrderPageUnifiedProcessingModelListBean otherPayBean, int position) {
            holder.setText(R.id.tv_shop_name, otherPayBean.getMerchantName());
            holder.setText(R.id.tv_pay_goods_name, otherPayBean.getGoodsName());
            holder.setText(R.id.tv_pay_sku, otherPayBean.getCommodityModel() + " " + otherPayBean.getCommoditySpec());
            holder.setText(R.id.tv_pay_mount, "订单总额:¥" + PriceUtil.dividePrice(otherPayBean.getPayAmount()));
            holder.setText(R.id.tv_pay_money, "¥" + PriceUtil.dividePrice(otherPayBean.getCommoditySalePrice()));
            holder.setText(R.id.tv_pay_num, "x" + otherPayBean.getCommodityQuantity());
            holder.setText(R.id.tv_pay_person, "由 " + otherPayBean.getBuyerNickName() + " 发起");
            ImageView ivLogo = holder.getView(R.id.iv_pay_logo);
            CountdownView countdownView = holder.getView(R.id.cv_countdown);
            TextView tvTime = holder.getView(R.id.tv_pay_time);
            GlideUtil.showRadius(mContext, otherPayBean.getGoodsImageUrl(), 2, ivLogo);
            if(otherPayBean.getPostage() == 0){
                holder.setText(R.id.tv_pay_postage, "免运费");
            }else {
                holder.setText(R.id.tv_pay_postage, "邮费:¥" + otherPayBean.getPostage());
            }
            TextView tvGo = holder.getView(R.id.tv_go_pay);
            if(state == 1){
                String orderTime = otherPayBean.getOrderTime();
                long currentTime = System.currentTimeMillis();
                long validTime = otherPayBean.getPayInvalidTime() * 60 * 1000;
                long endPayTime = DateToolUtils.strToDateLong(orderTime).getTime()+ validTime;
                long lastTime = endPayTime - currentTime;
                if(lastTime <= 0){
                    countdownView.setVisibility(View.GONE);
                    tvTime.setText("支付过期");
                    tvGo.setBackgroundResource(R.drawable.bg_zero_hot_disable);
                }else {
                    tvGo.setBackgroundResource(R.drawable.bg_zero_hot_enable);
                    countdownView.setVisibility(View.VISIBLE);
                    countdownView.start(lastTime);
                    tvTime.setText("支付剩余时间:");
                    holder.setText(R.id.tv_go_pay, "帮Ta付");
                    holder.setOnClickListener(R.id.tv_go_pay, v -> {
                        //立即付款
                        Intent intent = new Intent(mContext, PayOrderActivity.class);
                        intent.putExtra(PayOrderActivity.GOODS_NAME, otherPayBean.getGoodsName());
                        intent.putExtra(PayOrderActivity.TOTAL_PRICE, otherPayBean.getPayAmount() + otherPayBean.getPostage());
                        intent.putExtra(PayOrderActivity.ORDER_NUMBER, otherPayBean.getOrderNo());
                        intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, true);
                        intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.OTHER_PAY_TYPE);
                        if(otherPayBean.getCouponAmount() == 0){//全球买手活动
                            intent.putExtra(PayOrderActivity.ACTIVITY_TYPE, ActivityType.ACTIVITY_GLOBAL_BUYER);
                            intent.putExtra(PayOrderActivity.COUPON_VALUE, otherPayBean.getDeductionCouponAmount());
                        }else {
                            intent.putExtra(PayOrderActivity.ACTIVITY_TYPE, ActivityType.ACTIVITY_WUG);
                            intent.putExtra(PayOrderActivity.COUPON_VALUE, otherPayBean.getCouponAmount());
                        }
                        ActivityUtils.startActivity(intent);
                    });
                }
            }else {
                tvTime.setText(otherPayBean.getPayTime());
                countdownView.setVisibility(View.GONE);
                tvGo.setBackgroundResource(R.drawable.bg_zero_hot_disable);
                holder.setText(R.id.tv_go_pay, "已代付");
            }
        }
    }
}
