package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.KeyValueBean;
import com.xk.mall.model.entity.PayBackDetailBean;
import com.xk.mall.model.impl.PayBackDetailViewImpl;
import com.xk.mall.presenter.PayBackDetailPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.SellOrderTipDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName: PayBackDetailActivity
 * @Description: 退款订单详情页面
 * @Author: 卿凯
 * @Date: 2019/12/9/009 21:38
 * @Version: 1.0
 */
public class PayBackDetailActivity extends BaseActivity<PayBackDetailPresenter> implements PayBackDetailViewImpl {
    @BindView(R.id.iv_order_state)
    ImageView ivOrderState;//订单状态图片
    @BindView(R.id.tv_order_detail_state)
    TextView tvOrderDetailState;//订单状态文字
    @BindView(R.id.tv_order_detail_name)
    TextView tvOrderDetailName;//收货地址名称
    @BindView(R.id.tv_order_detail_phone)
    TextView tvOrderDetailPhone;//收货地址电话
    @BindView(R.id.tv_order_detail_address)
    TextView tvOrderDetailAddress;//收货地址
    @BindView(R.id.tv_order_list_shopName)
    TextView tvOrderListShopName;//店铺名称
    @BindView(R.id.iv_order_list_logo)
    ImageView ivOrderListLogo;//商品图片
    @BindView(R.id.tv_order_list_name)
    TextView tvOrderListName;//商品名称
    @BindView(R.id.tv_order_list_money)
    TextView tvOrderListMoney;//单价
    @BindView(R.id.tv_order_list_num)
    TextView tvOrderListNum;//数量
    @BindView(R.id.tv_sku)
    TextView tvSku;//SKU
    @BindView(R.id.tv_order_detail_postage)
    TextView tvOrderDetailPostage;//邮费
    @BindView(R.id.tv_order_detail_real_price)
    TextView tvOrderDetailRealPrice;//实付款
    @BindView(R.id.tv_order_detail_copy)
    TextView tvOrderDetailCopy;//复制按钮
    @BindView(R.id.rv_order_detail)
    RecyclerView rvOrderDetail;//售后信息列表
    @BindView(R.id.tv_order_detail_right)
    TextView tvOrderDetailRight;//取消退款按钮
    @BindView(R.id.tv_order_detail_center)
    TextView tvOrderDetailCenter;//填写物流按钮
    @BindView(R.id.ll_merchant)
    LinearLayout llMerchant;//商家备注信息
    @BindView(R.id.tv_merchant_note)
    TextView tvMerchant;//商家备注内容
    @BindView(R.id.ll_logistic)
    LinearLayout llLogistic;//物流信息
    @BindView(R.id.tv_logistic_name)
    TextView tvLogisticName;//物流名称
    @BindView(R.id.tv_logistic_no)
    TextView tvLogisticNo;//物流订单号
    @BindView(R.id.tv_order_detail_remarks)
    TextView tvRemarks;//订单备注
    @BindView(R.id.rl_order_remarks)
    RelativeLayout rlOrderRemarks;//订单备注布局
    /**intent传递的订单类型*/
    public static final String ACTIVITY_TYPE = "activity_type";
    /**intent传递的订单号*/
    public static final String REFUND_ORDER_NO = "refund_order_no";
    /**重新刷新数据的字符串*/
    public static final String REFRESH_DATA = "refresh_data";
    private String orderNo;//订单号

    @Override
    protected PayBackDetailPresenter createPresenter() {
        return new PayBackDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_back_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("订单详情");
    }

    @Override
    protected void initData() {
        int orderType = getIntent().getIntExtra(ACTIVITY_TYPE, 0);
        orderNo = getIntent().getStringExtra(REFUND_ORDER_NO);
        String phone = SPUtils.getInstance().getString(Constant.USER_MOBILE);
        mPresenter.getOrderDetail(phone, orderType, orderNo);
    }

    @Override
    public void onGetDetailSuccess(BaseModel<PayBackDetailBean> backBeanBaseModel) {
        if(backBeanBaseModel != null && backBeanBaseModel.getData() != null){
            PayBackDetailBean backDetailBean = backBeanBaseModel.getData();
            bindAddress(backDetailBean.getAddressInfoModel());
            tvOrderListName.setText(backDetailBean.getGoodsName());
            GlideUtil.showRadius(mContext, backDetailBean.getGoodsImageUrl(), 2, ivOrderListLogo);
            tvSku.setText(backDetailBean.getCommodityModel() + " " + backDetailBean.getCommoditySpec());
            tvOrderListNum.setText("X" + backDetailBean.getCommodityQuantity());
            tvOrderListMoney.setText("¥" + PriceUtil.dividePrice(backDetailBean.getCommoditySalePrice()));//单价
            tvOrderDetailRealPrice.setText(getResources().getString(R.string.money) + PriceUtil.dividePrice(backDetailBean.getPayAmount()));
            tvOrderDetailPostage.setText(getResources().getString(R.string.money) + PriceUtil.dividePrice(backDetailBean.getPostage()));
            String merchantName = backDetailBean.getMerchantName();
            if(TextUtils.isEmpty(merchantName)){
                tvOrderListShopName.setText("喜扣商城");
            }else{
                tvOrderListShopName.setText(merchantName);
            }
            if(!XiKouUtils.isNullOrEmpty(backDetailBean.getSalesReturnSite())){
                llMerchant.setVisibility(View.VISIBLE);
                tvMerchant.setText(backDetailBean.getSalesReturnSite());
                tvOrderDetailCenter.setVisibility(View.VISIBLE);
            }
            if(!XiKouUtils.isNullOrEmpty(backDetailBean.getRemarks())){
                tvRemarks.setText(backDetailBean.getRemarks());
                rlOrderRemarks.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, RemarksActivity.class);
                    intent.putExtra(RemarksActivity.REMARKS_CONTENT, backDetailBean.getRemarks());
                    intent.putExtra(RemarksActivity.REMARKS_ISEDIT, false);
                    ActivityUtils.startActivity(intent);
                });
            }else {
                tvRemarks.setText("无");
            }

            if(!XiKouUtils.isNullOrEmpty(backDetailBean.getLogisticsCompany()) && !XiKouUtils.isNullOrEmpty(backDetailBean.getLogisticsNo())){
                llLogistic.setVisibility(View.VISIBLE);
                tvLogisticName.setText(backDetailBean.getLogisticsCompany());
                tvLogisticNo.setText(backDetailBean.getLogisticsNo());
            }
            tvOrderDetailCopy.setOnClickListener(v -> {
                //复制退款编号
                PriceUtil.copyOperation(mContext, backDetailBean.getRefundOrderNo());
            });
            bindState(backDetailBean);
            bindRvData(backDetailBean);
        }
    }

    @Override
    public void cancelSalesReturnSuccess(BaseModel baseModel) {
        //取消售后成功关闭页面，通知列表删除数据
        if(baseModel != null){
            ToastUtils.showShort("取消退款成功");
            EventBus.getDefault().post(orderNo);
        }
        finish();
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(String content){
        if(REFRESH_DATA.equals(content)){
            int orderType = getIntent().getIntExtra(ACTIVITY_TYPE, 0);
            orderNo = getIntent().getStringExtra(REFUND_ORDER_NO);
            String phone = SPUtils.getInstance().getString(Constant.USER_MOBILE);
            mPresenter.getOrderDetail(phone, orderType, orderNo);
        }
    }

    /**
     * 根据不同订单类型绑定不同数据
     */
    private void bindRvData(PayBackDetailBean detailBean) {
        if(detailBean == null){
            return;
        }
        List<KeyValueBean> orderInfo = new ArrayList<>();
        int type = detailBean.getRefundStatus();
        //退货状态，0=审核中，1=退款中，2=退款退货中，3=已退款，4=退款失败，5=退款已关闭
        if(type == 0 || type == 1 || type == 2){//审核中
            orderInfo.add(new KeyValueBean("退款原因:", detailBean.getRefundMsg()));
            orderInfo.add(new KeyValueBean("退款金额:", "¥" + PriceUtil.dividePrice(detailBean.getPayAmount())));
            orderInfo.add(new KeyValueBean("申请时间:", detailBean.getRefundOrderTime()));
            orderInfo.add(new KeyValueBean("退款编号:", detailBean.getRefundOrderNo()));
        }else if(type == 3){//已退款
            orderInfo.add(new KeyValueBean("退款原因:", detailBean.getRefundMsg()));
            orderInfo.add(new KeyValueBean("退款金额:", "¥" +PriceUtil.dividePrice(detailBean.getPayAmount())));
            orderInfo.add(new KeyValueBean("申请时间:", detailBean.getRefundOrderTime()));
            orderInfo.add(new KeyValueBean("退款编号:", detailBean.getRefundOrderNo()));
            orderInfo.add(new KeyValueBean("通过时间:", detailBean.getSuccessTime()));
        }else if(type == 4){//退款失败
            orderInfo.add(new KeyValueBean("退款原因:", detailBean.getRefundMsg()));
            orderInfo.add(new KeyValueBean("退款金额:", "¥" +PriceUtil.dividePrice(detailBean.getPayAmount())));
            orderInfo.add(new KeyValueBean("申请时间:", detailBean.getRefundOrderTime()));
            orderInfo.add(new KeyValueBean("退款编号:", detailBean.getRefundOrderNo()));
            orderInfo.add(new KeyValueBean("失败原因:", detailBean.getCheckExplain()));
            orderInfo.add(new KeyValueBean("拒绝时间:", detailBean.getRejectionTime()));
        }
        rvOrderDetail.setLayoutManager(new LinearLayoutManager(mContext));
        OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(mContext, R.layout.pay_back_order_info_item, orderInfo);
        rvOrderDetail.setAdapter(orderInfoAdapter);
    }

    /**
     * 退货状态，0=审核中，1=退款中，2=退款退货中，3=已退款，4=退款失败，5=退款已关闭
     */
    private void bindState(PayBackDetailBean backDetailBean) {
        if(backDetailBean == null){
            return;
        }
        int state = backDetailBean.getRefundStatus();
        if(state == 0) {//审核中
            tvOrderDetailState.setText("退款申请成功，请耐心等待商家处理");
            tvOrderDetailRight.setVisibility(View.VISIBLE);
            tvOrderDetailCenter.setVisibility(View.GONE);
        }else if(state == 1) {//退款中
            tvOrderDetailState.setText("商家已同意退款，等待商家退款中");
            tvOrderDetailCenter.setVisibility(View.GONE);
            tvOrderDetailRight.setVisibility(View.GONE);
        }else if(state == 2){//退款退货中
            tvOrderDetailState.setText("商家已同意退款，等待用户退货中");
            if(!XiKouUtils.isNullOrEmpty(backDetailBean.getLogisticsCompany()) && !XiKouUtils.isNullOrEmpty(backDetailBean.getLogisticsNo())){
                tvOrderDetailCenter.setVisibility(View.GONE);
            }else {
                tvOrderDetailCenter.setVisibility(View.VISIBLE);
            }
            tvOrderDetailRight.setVisibility(View.VISIBLE);
            tvOrderDetailCenter.setOnClickListener(v -> {
                //进入填写物流页面
                Intent intent = new Intent(mContext, EnterLogisticsInfoActivity.class);
                intent.putExtra(EnterLogisticsInfoActivity.ORDER_NO, backDetailBean.getRefundOrderNo());
                intent.putExtra(EnterLogisticsInfoActivity.ORDER_NOTE, backDetailBean.getSalesReturnSite());
                ActivityUtils.startActivity(intent);
            });
        }else if(state == 3){//已退款
            tvOrderDetailState.setText("退款成功");
            tvOrderDetailRight.setVisibility(View.GONE);
            tvOrderDetailCenter.setVisibility(View.GONE);
        }else if(state == 4){//退款失败
            tvOrderDetailState.setText("退款失败");
            tvOrderDetailRight.setVisibility(View.VISIBLE);
            tvOrderDetailCenter.setVisibility(View.GONE);
        }
        tvOrderDetailRight.setOnClickListener(v -> {
            //取消退款
            new SellOrderTipDialog(mContext, R.style.mydialog, "是否确认?", "确定取消订单退款申请吗？",
                    "同意", "取消", (dialog, confirm) -> {
                if (confirm) {
                    mPresenter.cancelPayBack(backDetailBean.getRefundOrderNo());
                }
            }).show();
        });
    }

    /**
     * 绑定地址信息
     */
    private void bindAddress(AddressBean address) {
        if(address != null){
            tvOrderDetailName.setText(address.consigneeName);
            tvOrderDetailPhone.setText(address.consigneeMobile);
            tvOrderDetailAddress.setText(XiKouUtils.getAddress(mContext, address));
        }
    }

    class OrderInfoAdapter extends CommonAdapter<KeyValueBean> {

        public OrderInfoAdapter(Context context, int layoutId, List<KeyValueBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, KeyValueBean keyValueBean, int position) {
            holder.setText(R.id.tv_order_info_item_key, keyValueBean.key);
            holder.setText(R.id.tv_order_info_item_value, keyValueBean.value);
        }
    }
}
