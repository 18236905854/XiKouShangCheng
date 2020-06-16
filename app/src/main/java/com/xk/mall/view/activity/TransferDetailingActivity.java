package com.xk.mall.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.SettlementMxChildBean;
import com.xk.mall.model.entity.WithDrawDetailBean;
import com.xk.mall.model.impl.SettlementDetailViewImpl;
import com.xk.mall.model.impl.WithDrawDetailViewImpl;
import com.xk.mall.presenter.SettlementDetailPresenter;
import com.xk.mall.presenter.WithDrawDetailPresenter;
import com.xk.mall.utils.PriceUtil;

import butterknife.BindView;

/**
 * 转账明细详情
 */
public class TransferDetailingActivity extends BaseActivity<SettlementDetailPresenter> implements SettlementDetailViewImpl {
    private static final String TAG = "RedBagDetailingActivity";
    public static final String RED_BAG_ID = "red_bag_id";
    public static final String RED_BAG_KEY = "red_bag_key";
    @BindView(R.id.state_view_order)
    MultiStateView stateView;
    @BindView(R.id.tv_red_dec)
    TextView tvRedDec;
    @BindView(R.id.tv_count_money)
    TextView tvCountMoney;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.tv_transfer_name)
    TextView tvTransferName;//转账账户
    @BindView(R.id.tv_transfer_remarks)
    TextView tvTransferRemarks;//转账备注
    @BindView(R.id.tv_dzhang_time)
    TextView tvDzhangTime;
    @BindView(R.id.rl_arrival_time)
    RelativeLayout rlArrivalTime;//到账时间布局
    @BindView(R.id.rl_business_time)
    RelativeLayout rlBusinessTime;//交易时间布局
    private String id = "";//红包明细ID
    private String refKey = "";//来自不同操作的key，如订单id

    @Override
    protected SettlementDetailPresenter createPresenter() {
        return new SettlementDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfer_detailing;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("转账明细详情");
    }

    @Override
    protected void initData() {
        id =  getIntent().getStringExtra(RED_BAG_ID);
        refKey = getIntent().getStringExtra(RED_BAG_KEY);
        mPresenter.getSettlementDetail(id, refKey);
        Button btnReplay = stateView.findViewById(R.id.btn_replay);
        btnReplay.setOnClickListener(v -> mPresenter.getSettlementDetail(id, refKey));
    }

    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        stateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void onGetDetailSuccess(BaseModel<SettlementMxChildBean> mxChildBeanBaseModel) {
        if(mxChildBeanBaseModel.getData() != null){
            stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            SettlementMxChildBean bean = mxChildBeanBaseModel.getData();
            tvRedDec.setText(bean.getBusinessName());
            if(bean.getOperateType() == 1){//增加
                tvCountMoney.setText("+" + PriceUtil.dividePrice(bean.getChangeValue()));
            }else if(bean.getOperateType() == 2){//扣减
                tvCountMoney.setText("-" + PriceUtil.dividePrice(bean.getChangeValue()));
            }
            tvOrderTime.setText(bean.getCreateTime());
            tvDzhangTime.setText(bean.getCreateTime());
            tvOrderMoney.setText(PriceUtil.dividePrice(bean.getChangeValue()));
            tvOrderNum.setText(bean.getRefKey());
            tvOrderType.setText(bean.getModuleName());
            tvTransferName.setText(bean.getTransferName());
            tvTransferRemarks.setText(bean.getTransferRemarks());
        }
    }
}
