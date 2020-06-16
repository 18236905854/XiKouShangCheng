package com.xk.mall.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.SettlementMxChildBean;
import com.xk.mall.model.impl.SettlementDetailViewImpl;
import com.xk.mall.presenter.SettlementDetailPresenter;
import com.xk.mall.utils.CouponUtil;

import butterknife.BindView;

/**
 * 待结算中明细详情
 */
public class SettlementDetailActivity extends BaseActivity<SettlementDetailPresenter> implements SettlementDetailViewImpl {
    private static final String TAG = "SettlementDetailActivity";
    public static final String RED_BAG_ID = "red_bag_id";
    public static final String RED_BAG_KEY = "red_bag_key";
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
    @BindView(R.id.tv_yewu_type)
    TextView tvYeWuType;
    private String id = "";//红包明细ID
    private String refKey = "";//来自不同操作的key，如订单id

    @Override
    protected SettlementDetailPresenter createPresenter() {
        return new SettlementDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settlement_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("待结算中明细详情");
    }

    @Override
    protected void initData() {
        id =  getIntent().getStringExtra(RED_BAG_ID);
        refKey = getIntent().getStringExtra(RED_BAG_KEY);
        mPresenter.getSettlementDetail(id, refKey);

    }


    @Override
    public void onGetDetailSuccess(BaseModel<SettlementMxChildBean> mxChildBeanBaseModel) {
        if(mxChildBeanBaseModel.getData() != null){
            SettlementMxChildBean bean = mxChildBeanBaseModel.getData();
            tvRedDec.setText(bean.getBusinessName());
            tvCountMoney.setText(bean.getChangeValue());
            tvOrderTime.setText(bean.getCreateTime());
            tvOrderMoney.setText(bean.getChangeValue());
            tvOrderType.setText(bean.getChannel());
            tvYeWuType.setText(bean.getModuleName());
        }
    }
}
