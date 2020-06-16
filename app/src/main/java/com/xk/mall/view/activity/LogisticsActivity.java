package com.xk.mall.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.LogisticsResultBean;
import com.xk.mall.model.impl.LogisticsViewImpl;
import com.xk.mall.presenter.LogisticsPresenter;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.widget.StepView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: LogisticsActivity
 * @Description: 物流页面
 * @Author: 卿凯
 * @Date: 2019/9/10/010 19:42
 * @Version: 1.0
 */
public class LogisticsActivity extends BaseActivity<LogisticsPresenter> implements LogisticsViewImpl {
    @BindView(R.id.tv_logistics_receiver_name)
    TextView tvLogisticsReceiverName;//收件人姓名
    @BindView(R.id.tv_logistics_receiver_phone)
    TextView tvLogisticsReceiverPhone;//收件人电话
    @BindView(R.id.tv_logistics_address)
    TextView tvLogisticsAddress;//收件人地址
    @BindView(R.id.ll_order_detail_address)
    LinearLayout llOrderDetailAddress;
    @BindView(R.id.tv_logistics)
    TextView tvLogistics;//快递名称和单号
    @BindView(R.id.stepView)
    StepView stepView;
    @BindView(R.id.state_view)
    MultiStateView stateView;
    /**intent传递过来的订单号的key*/
    public static String ORDER_NO = "order_no";
    /**intent传递过来的订单类型的key*/
    public static String ORDER_TYPE = "order_type";
    private String orderNo;//订单号
    private int orderType;//订单类型
    private String logisticsNo;//物流单号

    @Override
    protected LogisticsPresenter createPresenter() {
        return new LogisticsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_logistics;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("物流信息");
    }

    @Override
    protected void initData() {
        orderNo = getIntent().getStringExtra(ORDER_NO);
        orderType = getIntent().getIntExtra(ORDER_TYPE, 0);
        mPresenter.getLogistics(orderNo, orderType);
        TextView emptyText = stateView.findViewById(R.id.tv_empty_text);
        emptyText.setText("暂无物流信息");
        ImageView ivEmpty = stateView.findViewById(R.id.iv_empty_order);
        ivEmpty.setImageResource(R.drawable.ic_no_logistics);
    }

    @OnClick(R.id.tv_logistics_copy)
    public void copyNumber(){
        PriceUtil.copyOperationLogistics(mContext,logisticsNo);
        ToastUtils.showShortToast(mContext, "复制物流单号成功");
    }

    @Override
    public void onGetLogisticsSuccess(BaseModel<LogisticsResultBean> beanBaseModel) {
        if(beanBaseModel != null && beanBaseModel.getData() != null){
            tvLogisticsReceiverName.setText(beanBaseModel.getData().getConsigneeName());
            tvLogisticsReceiverPhone.setText(beanBaseModel.getData().getConsigneeMobile());
            tvLogisticsAddress.setText(beanBaseModel.getData().getProvinceName() + beanBaseModel.getData().getCityName() +
                    beanBaseModel.getData().getAreaName() + beanBaseModel.getData().getAddress());
            tvLogistics.setText(beanBaseModel.getData().getLogisticsCompany() + "    " + beanBaseModel.getData().getLogisticsNo());
            logisticsNo = beanBaseModel.getData().getLogisticsNo();
            if(beanBaseModel.getData().getData() == null || beanBaseModel.getData().getData().size() == 0){
                //没有物流信息
                stateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                if(beanBaseModel.getData().getMessage() != null){
                    ToastUtils.showShortToast(mContext, beanBaseModel.getData().getMessage());
                }
            }else {
                stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                stepView.setDatas(beanBaseModel.getData().getData());
                stepView.setBindViewListener((itemMsg, itemDate, data, pos) -> {
                    LogisticsResultBean.DataBean dataBean = (LogisticsResultBean.DataBean) data;
                    if(pos == 0){
                        itemMsg.setTextColor(Color.parseColor("#4A4A4A"));
                    }else {
                        itemMsg.setTextColor(Color.parseColor("#9B9B9B"));
                    }
                    itemMsg.setText(dataBean.getContext());
                    itemDate.setText(dataBean.getFtime());
                });
            }
        }else {
            stateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

}
