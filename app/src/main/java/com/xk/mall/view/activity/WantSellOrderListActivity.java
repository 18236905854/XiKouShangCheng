package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.kennyc.view.MultiStateView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.WantSellBean;
import com.xk.mall.model.impl.WantSellOrderViewImpl;
import com.xk.mall.model.impl.WuGOrderViewImpl;
import com.xk.mall.presenter.WantSellOrderPresenter;
import com.xk.mall.presenter.WuGOrderPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DateToolUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareModel;
import com.xk.mall.utils.TimeTools;
import com.xk.mall.view.widget.ChooseSellStyleDialog;
import com.xk.mall.view.widget.DialogSellShare;
import com.xk.mall.view.widget.OrderTipDialog;
import com.xk.mall.view.widget.SellSuccessDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.countdownview.CountdownView;

/**
 * ClassName WantSellOrderListActivity
 * Description 我要寄卖订单列表
 * Author 卿凯
 * Date 2019/6/27/027
 * Version V1.0
 */
public class WantSellOrderListActivity extends BaseActivity<WantSellOrderPresenter> implements WantSellOrderViewImpl {

    @BindView(R.id.rv_want_sell_order_list)
    RecyclerView rvWantSellOrderList;
    @BindView(R.id.refresh_order)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.state_view_order)
    MultiStateView multiStateView;
    private List<OrderBean> wantSellBeans;
    private WantSellOrderListAdapter adapter;
    private int page = 1;
    private int limit = 10;
    private int isSell = 0;//是否是寄卖 0:没有操作  1:寄卖  2:发货
    private int clickPos = -1;//点击位置
    private int shareModel;
    private int sellType = -1;

    @Override
    protected WantSellOrderPresenter createPresenter() {
        return new WantSellOrderPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_want_sell_order_list;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("可寄卖商品");
        setRightText("寄卖协议");
        showRight(true);
        setOnRightClickListener(v -> {
            //打开寄卖协议
            Intent intent = new Intent(mContext, ProtocolWebViewActivity.class);
            intent.putExtra(Constant.INTENT_URL, Constant.sellProtocolUrl);
            intent.putExtra(ProtocolWebViewActivity.IS_SHOW, false);
            ActivityUtils.startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        //已付款  待发货
        //endType 0:未过，显示剩余时间  1:已过，显示申请寄卖过期
        mPresenter.getOrderList(SPUtils.getInstance().getString(Constant.USER_MOBILE), page, limit);
        wantSellBeans = new ArrayList<>();
        adapter = new WantSellOrderListAdapter(mContext, R.layout.want_sell_order_list_item, wantSellBeans);
        rvWantSellOrderList.setLayoutManager(new LinearLayoutManager(mContext));
        rvWantSellOrderList.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getOrderList(SPUtils.getInstance().getString(Constant.USER_MOBILE), page, limit);
        });

        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getOrderList(SPUtils.getInstance().getString(Constant.USER_MOBILE), page, limit);
        });
    }

    @Override
    public void onGetWantSellOrderListSuccess(BaseModel<OrderPageBean> model) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if(model.getData() != null && model.getData().result != null){
            if(page == 1){
                wantSellBeans.clear();
            }
            wantSellBeans.addAll(model.getData().result);
            adapter.notifyDataSetChanged();
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
    public void onModifyOrderTypeSuccess(BaseModel<ShareBean> model) {
        //修改当前订单状态
        if(clickPos != -1){
            OrderBean orderBean = wantSellBeans.get(clickPos);
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
            wantSellBeans.remove(clickPos);
            adapter.notifyItemRemoved(clickPos);
            clickPos = -1;
            sellType = 0;
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        ToastUtils.showShortToast(mContext, model.getMsg());
    }

    private int getOrderBeanPosition(OrderBean orderBean){
        int result = -1;
        for(int i = 0; i < wantSellBeans.size(); i++){
            OrderBean orderBean1 = wantSellBeans.get(i);
            if(orderBean1.getOrderNo().equals(orderBean.getOrderNo())){
                result = i;
            }
        }
        return result;
    }

    /**
     * 我要寄卖订单adapter
     */
    class WantSellOrderListAdapter extends CommonAdapter<OrderBean>{

        public WantSellOrderListAdapter(Context context, int layoutId, List<OrderBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, OrderBean wantSellBean, int position) {
            ImageView imageView = holder.getView(R.id.iv_want_sell_order_logo);
            GlideUtil.showRadius(mContext, wantSellBean.getGoodsImageUrl(), 2, imageView);
            holder.setText(R.id.tv_want_sell_order_shop_name, wantSellBean.getMerchantName());
            holder.setText(R.id.tv_want_sell_order_name, wantSellBean.getGoodsName());
            holder.setText(R.id.tv_want_sell_real_price, "原价:¥" + PriceUtil.dividePrice(wantSellBean.getCommoditySalePrice()) +
                    "  折扣价:¥" + PriceUtil.dividePrice(wantSellBean.getDiscountPrice()) + "  优惠券:" +
                    PriceUtil.divideCoupon(wantSellBean.getDeductionCouponAmount()));
            TextView tvType = holder.getView(R.id.tv_want_sell_order_type);
            TextView tvEndTime = holder.getView(R.id.tv_want_sell_end_time);
            TextView tvRight = holder.getView(R.id.tv_want_sell_list_right);
            TextView tvCenter = holder.getView(R.id.tv_want_sell_list_center);
            if(wantSellBean.getState() == 7){
                tvType.setText("待确认");
                tvRight.setVisibility(View.VISIBLE);
                tvCenter.setVisibility(View.VISIBLE);
            }else if(wantSellBean.getState() == 2){
                tvType.setText("待发货");
                tvRight.setVisibility(View.GONE);
                tvCenter.setVisibility(View.GONE);
            }else if(wantSellBean.getState() == 11){
                tvType.setText("已寄卖");
                tvRight.setVisibility(View.GONE);
                tvCenter.setVisibility(View.GONE);
            }
            tvRight.setOnClickListener(v -> {
                //点击寄卖
                new ChooseSellStyleDialog(mContext, R.style.mydialog, wantSellBean.getWhetherToAllow(), wantSellBean.getIsDirect(), (isWug, isShare) -> {
                    if(isWug && isShare){
                        clickPos = getOrderBeanPosition(wantSellBean);
                        shareModel = ShareModel.MODEL_BOTH;
                        sellType = 2;
                        mPresenter.modifyOrderType(MyApplication.userId, wantSellBean.getOrderNo(), ShareModel.MODEL_BOTH, 2);
                    }else if(isWug){
                        //只选择寄卖
                        clickPos = getOrderBeanPosition(wantSellBean);
                        shareModel = ShareModel.MODEL_SELL;
                        sellType = 2;
                        mPresenter.modifyOrderType(MyApplication.userId, wantSellBean.getOrderNo(), ShareModel.MODEL_SELL, 2);
                    }else if(isShare){
                        //只选择分享
                        clickPos = getOrderBeanPosition(wantSellBean);
                        shareModel = ShareModel.MODEL_SHARE;
                        sellType = 2;
                        mPresenter.modifyOrderType(MyApplication.userId, wantSellBean.getOrderNo(), ShareModel.MODEL_SHARE, 2);
                    }
                }).show();
            });

            tvCenter.setOnClickListener(v -> {
                //点击发货自用
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确认申请该商品自用收货","确定", (dialog, confirm) -> {
                    if(confirm){
                        clickPos = position;
                        isSell = 2;
                        mPresenter.modifyOrderType(MyApplication.userId, wantSellBean.getOrderNo(), 0,1);
                    }
                }).show();
            });
            if(wantSellBean.getState() == 7 && !TextUtils.isEmpty(wantSellBean.getWaitConfirmTime())){
                long currentTime = System.currentTimeMillis();
                long validTime = wantSellBean.getTimeToBeConfirmed() * 24 * 3600 * 1000;
                long orderTime = TimeUtils.string2Millis(wantSellBean.getPayTime(), "yyyy-MM-dd HH:mm:ss");
                long endPayTime = orderTime + validTime - currentTime;
                if(endPayTime <= 0){
                    tvEndTime.setText("申请寄卖过期");
                }else {
                    tvEndTime.setText("剩余时间:" + TimeTools.getCountTimeHMZH(endPayTime));
                }
            }else {
                tvEndTime.setText("申请寄卖过期");
            }

        }
    }
}
