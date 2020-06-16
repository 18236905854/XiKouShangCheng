package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;

import butterknife.BindView;

/**
 * @ClassName: WithDrawResultActivity
 * @Description: 提现结果页面
 * @Author: 卿凯
 * @Date: 2019/9/25/025 9:45
 * @Version: 1.0
 */
public class WithDrawResultActivity extends BaseActivity {

    @BindView(R.id.img_pay_status)
    ImageView ivStatus;//提现状态图标
    @BindView(R.id.tv_pay_status)
    TextView tvStatus;//提现状态文字(提现失败/提现成功)
    @BindView(R.id.tv_pay_coupon)
    TextView tvContent;//申请成功显示，实际提现和手续费
    @BindView(R.id.btn_back_home)
    Button btnGoAccount;//返回我的账户
    @BindView(R.id.btn_to_do)
    Button btnTodo;//查看提现明细，成功时显示
    //传递过来的提现成功ID
    public static String WITH_DRAW_ID = "with_draw_id";
    //传递过来的实际提现金额key
    public static String REAL_MONEY = "real_money";
    //传递过来的手续费金额key
    public static String RATE_MONEY = "rate_money";
    private String cashId;//用来查看提现明细的ID
    private double realMoney;//实际到账金额
    private double rateMoney;//手续费

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_order_result;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        cashId = getIntent().getStringExtra(WITH_DRAW_ID);
        realMoney = getIntent().getDoubleExtra(REAL_MONEY, 0);
        rateMoney = getIntent().getDoubleExtra(RATE_MONEY, 0);
        if(realMoney != 0 && rateMoney != 0){
            tvContent.setVisibility(View.VISIBLE);
            btnTodo.setVisibility(View.VISIBLE);
            tvStatus.setText("申请提现成功");
            ivStatus.setImageResource(R.mipmap.ic_pay_success);
            tvContent.setText("实际提现：" + realMoney + "  手续费：" + rateMoney);
        }else {
            ivStatus.setImageResource(R.mipmap.ic_pay_fail);
            tvStatus.setText("申请提现失败");
            tvContent.setVisibility(View.GONE);
            btnTodo.setVisibility(View.GONE);
        }
        btnGoAccount.setText("返回我的账户");
        btnTodo.setText("查看提现明细");

        btnGoAccount.setOnClickListener(v -> finish());

        btnTodo.setOnClickListener(v -> {
            if(cashId != null && !TextUtils.isEmpty(cashId)){
                //点击跳转详情
                Intent intent = new Intent(mContext, WithDrawDetailingActivity.class);
                intent.putExtra(WithDrawDetailingActivity.WITHDRAW_DETAIL, cashId);
                ActivityUtils.startActivity(intent);
                finish();
            }
        });
    }

}
