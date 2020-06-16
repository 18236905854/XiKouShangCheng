package com.xk.mall.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.WithDrawDetailBean;
import com.xk.mall.model.impl.WithDrawDetailViewImpl;
import com.xk.mall.presenter.WithDrawDetailPresenter;
import com.xk.mall.utils.PriceUtil;

import butterknife.BindView;

/**
 * 提现明细详情
 */
public class WithDrawDetailingActivity extends BaseActivity<WithDrawDetailPresenter> implements WithDrawDetailViewImpl {
    private static final String TAG = "WithDrawDetailingActivity";
    public static final String WITHDRAW_DETAIL = "withdraw_detail";
    @BindView(R.id.tv_red_dec)
    TextView tvRedDec;
    @BindView(R.id.tv_count_money)
    TextView tvCountMoney;
    @BindView(R.id.tv_withdraw_status)
    TextView tvWithdrawStatus;
    @BindView(R.id.tv_withdraw_time)
    TextView tvWithdrawTime;
    @BindView(R.id.tv_withdraw_account)
    TextView tvWithdrawAccount;
    @BindView(R.id.tv_withdraw_type)
    TextView tvWithdrawType;
    @BindView(R.id.tv_handling_fee)
    TextView tvHandlingFee;
    @BindView(R.id.tv_dzhang_time)
    TextView tvDzhangTime;
    @BindView(R.id.tv_dzhang_money)
    TextView tvDZhangMoney;//实际到账金额
    @BindView(R.id.rl_update_time)
    RelativeLayout rlUpdateTime;//更新时间(到账时间)
    @BindView(R.id.tv_update_time)
    TextView tvUpdateTime;
    @BindView(R.id.tv_real_money)
    TextView tvRealMoney;
    @BindView(R.id.rl_lose)
    RelativeLayout rlLose;//失败原因
    @BindView(R.id.tv_lose)
    TextView tvLose;//失败原因显示文字

    private String id;

    @Override
    protected WithDrawDetailPresenter createPresenter() {
        return new WithDrawDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw_detailing;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("提现明细详情");
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra(WITHDRAW_DETAIL);
        mPresenter.getDetail(id);
    }

    @Override
    public void onGetDetailSuccess(BaseModel<WithDrawDetailBean> model) {
        if(model != null && model.getData() != null){
            WithDrawDetailBean detailBean = model.getData();
            tvCountMoney.setText(PriceUtil.dividePrice(detailBean.getCashAmount()));
            if(detailBean.getState() == 1){
                tvWithdrawStatus.setText("到账中");
            }else if(detailBean.getState() == 2){
                tvWithdrawStatus.setText("到账中");
            }else if(detailBean.getState() == 3){
                tvWithdrawStatus.setText("已到账");
                rlUpdateTime.setVisibility(View.VISIBLE);
                tvRealMoney.setText("实际到账金额");
                tvUpdateTime.setText("实际到账时间");
                tvDzhangTime.setText(detailBean.getUpdateTime());
            }else if(detailBean.getState() == 4){
                tvWithdrawStatus.setText("提现失败");
                rlUpdateTime.setVisibility(View.VISIBLE);
                tvDzhangTime.setText(detailBean.getUpdateTime());
                if(detailBean.getCashContent() != null && !TextUtils.isEmpty(detailBean.getCashContent())){
                    rlLose.setVisibility(View.VISIBLE);
                    tvLose.setText(detailBean.getCashContent());
                }
            }
            tvWithdrawTime.setText(detailBean.getCashTime());
            tvWithdrawType.setText(detailBean.getCashType());
            tvDZhangMoney.setText(PriceUtil.dividePrice(detailBean.getAmount()) + "元");
            tvHandlingFee.setText(PriceUtil.dividePrice(detailBean.getCashCommission()) + "元(" + detailBean.getRate() + "%)");
            tvWithdrawAccount.setText(detailBean.getCashBankCard());
        }
    }
}
