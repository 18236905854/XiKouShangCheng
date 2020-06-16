package com.xk.mall.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.kennyc.view.MultiStateView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.OrderBean;
import com.xk.mall.model.entity.RankResultBean;
import com.xk.mall.model.entity.SellOrderBean;
import com.xk.mall.model.entity.SellOrderPageBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.TaskBean;
import com.xk.mall.model.entity.WantSellBean;
import com.xk.mall.model.impl.SellOrderListViewImpl;
import com.xk.mall.presenter.SellOrderListPresenter;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.MeiQiaUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareModel;
import com.xk.mall.utils.TimeTools;
import com.xk.mall.view.widget.DialogSellShare;
import com.xk.mall.view.widget.OrderTipDialog;
import com.xk.mall.view.widget.SellOrderTipDialog;
import com.xk.mall.view.widget.SellSuccessDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.countdownview.CountdownView;

/**
 * ClassName WantSellOrderListActivity
 * Description 我的寄卖订单列表
 * Author 卿凯
 * Date 2019/6/27/027
 * Version V1.0
 */
public class SellOrderListActivity extends BaseActivity<SellOrderListPresenter> implements SellOrderListViewImpl {

    @BindView(R.id.rv_want_sell_order_list)
    RecyclerView rvWantSellOrderList;
    @BindView(R.id.refresh_order)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.state_view_order)
    MultiStateView multiStateView;
    private int tasksNum = 0;//贡献值
    private int page = 1;
    private int limit = 10;
    private int clickPos = -1;//点击位置
    private List<SellOrderBean> wantSellBeans;
    private SellOrderBean clickBean;
    private SellOrderListAdapter adapter;
    private int processingMethod;//处理方式;1-自提;2-寄卖;3-分享
    private int type;//类型

    @Override
    protected SellOrderListPresenter createPresenter() {
        return new SellOrderListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sell_order_list;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("寄卖中商品");
        setRightDrawable(R.drawable.wug_kefu);
        setOnRightIconClickListener(v -> {
            MeiQiaUtil.initMeiqiaSDK(mContext);
        });
    }

    @Override
    protected void initData() {
        //已付款  待发货
        //endType 0:未过，显示剩余时间  1:已过，显示申请寄卖过期
        mPresenter.getSellOrderList(MyApplication.userId, page, limit);
        mPresenter.getTaskList(MyApplication.userId);
        wantSellBeans = new ArrayList<>();
        adapter = new SellOrderListAdapter(mContext, R.layout.sell_order_list_item, wantSellBeans);
        rvWantSellOrderList.setLayoutManager(new LinearLayoutManager(mContext));
        rvWantSellOrderList.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getSellOrderList(MyApplication.userId, page, limit);
        });

        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getSellOrderList(MyApplication.userId, page, limit);
        });

    }

    /**
     * 获取贡献值成功回调
     */
    @Override
    public void onGetTaskListSuccess(BaseModel<TaskBean> model) {
        if (model.getData() != null) {
            tasksNum = model.getData().getTaskValue();
        }
    }

    /**
     * 自提成功
     */
    @Override
    public void onModifyOrderTypeSuccess(BaseModel<ShareBean> model) {
        //删除数据
        if(processingMethod == 1){
            if(clickPos != -1){
                wantSellBeans.remove(clickPos);
                adapter.notifyDataSetChanged();
            }
            ToastUtils.showShortToast(mContext, "申请发货成功");
        }else if(processingMethod == 2){
            com.lljjcoder.style.citylist.Toast.ToastUtils.showLongToast(mContext, "您的商品已进入寄卖序列中，请耐心等待...");
            if(clickPos != -1){
                SellOrderBean wantSellBean = wantSellBeans.get(clickPos);
                if(wantSellBean != null ){
                    wantSellBean.setShareModel(2);
                    adapter.notifyItemChanged(clickPos);
                }
            }
        }else if(processingMethod == 3 && model.getData() != null){
            new DialogSellShare(mContext, model.getData()).show();
            if(clickPos != -1){
                SellOrderBean wantSellBean = wantSellBeans.get(clickPos);
                if(wantSellBean != null && type == 1){
                    wantSellBean.setShareModel(ShareModel.MODEL_SHARE);
                    adapter.notifyItemChanged(clickPos);
                }
            }
        }
        clickPos = -1;
        processingMethod = 0;
        type = 0;
    }

    @Override
    public void onGetSellOrderListSuccess(BaseModel<SellOrderPageBean> model) {
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

    /**
     * 我的寄卖订单adapter
     */
    class SellOrderListAdapter extends CommonAdapter<SellOrderBean>{

        public SellOrderListAdapter(Context context, int layoutId, List<SellOrderBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, SellOrderBean wantSellBean, int position) {
//            holder.setText(R.id.tv_sell_order_shop_name, wantSellBean.get);
            holder.setText(R.id.tv_sell_order_merchant_name, wantSellBean.getMerchantName());
            holder.setText(R.id.tv_sell_order_name, wantSellBean.getCommodityName());
            holder.setText(R.id.tv_sell_real_price, "原价:¥" + PriceUtil.dividePrice(wantSellBean.getSalePrice()) + " " +
                    "折扣价:¥" + PriceUtil.dividePrice(wantSellBean.getCommodityPrice()) + " " + "优惠券:" + PriceUtil.divideCoupon(wantSellBean.getCouponValue()));
            TextView tvRight = holder.getView(R.id.tv_sell_list_right);//排名优先
            TextView tvCenter = holder.getView(R.id.tv_sell_list_center);
            TextView tvLeft = holder.getView(R.id.tv_sell_list_left);
            ImageView ivLogo = holder.getView(R.id.iv_sell_order_logo);
            GlideUtil.showRadius(mContext, wantSellBean.getGoodsImageUrl(), 2, ivLogo);
            TextView tvOrder = holder.getView(R.id.tv_sell_end_time);
            //发货
            tvRight.setOnClickListener(v -> {
                //点击发货自用
                new OrderTipDialog(mContext, R.style.mydialog, "提示", "确认申请该商品自用收货","确定", (dialog, confirm) -> {
                    if(confirm){
                        clickPos = position;
                        processingMethod = 1;
                        mPresenter.modifyOrderType(MyApplication.userId, wantSellBean.getOriginalId(), 0, 1);
                    }
                }).show();
            });

            if(wantSellBean.getShareModel() == 0 || wantSellBean.getShareModel() == 2 || wantSellBean.getShareModel() == 3){//之前的数据处理
                tvLeft.setVisibility(View.GONE);
                tvCenter.setVisibility(View.VISIBLE);
                tvRight.setVisibility(View.VISIBLE);
                tvOrder.setText("已寄卖到吾G");
                tvLeft.setText("");
                tvCenter.setText("给好友购买");
                tvRight.setText("发货");
                tvCenter.setOnClickListener(v -> {
                    //点击分享
                    new SellOrderTipDialog(mContext, R.style.mydialog, "分享给好友", "分享给好友购买后，您的商品将在吾G购中下架",
                            "确定分享","取消分享", (dialog, confirm) -> {
                        if(confirm){
                            clickPos = position;
                            processingMethod = 3;
                            type = 1;
                            mPresenter.modifyOrderType(MyApplication.userId, wantSellBean.getOriginalId(),ShareModel.MODEL_SHARE, 3);
                        }
                    }).show();
                });
            }else {
                tvLeft.setVisibility(View.VISIBLE);
                tvRight.setVisibility(View.VISIBLE);
                tvCenter.setVisibility(View.GONE);
                tvOrder.setText("已分享给好友");
                tvRight.setText("发货");
//                tvCenter.setText("寄卖到吾G");
                tvLeft.setText("给好友购买");
                tvLeft.setOnClickListener(v -> {
                    //点击分享
                    clickPos = position;
                    processingMethod = 3;
                    mPresenter.modifyOrderType(MyApplication.userId, wantSellBean.getOriginalId(), ShareModel.MODEL_SHARE, 3);
                });
//                tvCenter.setOnClickListener(v -> {
//                    //寄卖到吾G
//                    new SellOrderTipDialog(mContext, R.style.mydialog, "寄卖到吾G", "寄卖到吾G后，您分享给好友的链接将会失效。",
//                            "确定寄卖", "取消寄卖", (dialog, confirm) -> {
//                                if(confirm){
//                                    clickPos = position;
//                                    processingMethod = 2;
//                                    mPresenter.modifyOrderType(MyApplication.userId, wantSellBean.getOriginalId(), ShareModel.MODEL_SELL, 2);
//                                }
//                            }).show();
//                });
            }

            if(wantSellBean.getConsignmentType() == 1){
                //隐藏寄卖到吾G和给好友购买按钮
                tvCenter.setVisibility(View.GONE);
            }else if(wantSellBean.getConsignmentType() == 2){
                tvCenter.setVisibility(View.GONE);
                tvLeft.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 显示贡献值不够对话框
     */
    private void showJudgeTasksNum(){
        new OrderTipDialog(mContext, R.style.mydialog, "提示", "您的贡献值不足50，请前往任务\n 中心获取贡献值",
                "确定", (dialog, confirm) -> {
            if(confirm){
                ActivityUtils.startActivity(MakeTaskActivity.class);
                finish();
            }
        }).show();
    }
}
