package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.utils.XiKouUtils;

import butterknife.BindView;

/**
 * @ClassName: WithDrawResultActivity
 * @Description: 转账结果页面
 * @Author: 卿凯
 * @Date: 2019/9/25/025 9:45
 * @Version: 1.0
 */
public class TransferResultActivity extends BaseActivity {

    @BindView(R.id.img_pay_status)
    ImageView ivStatus;//提现状态图标
    @BindView(R.id.tv_pay_status)
    TextView tvStatus;//提现状态文字(提现失败/提现成功)
    @BindView(R.id.btn_back_home)
    Button btnGoAccount;//返回我的账户
    @BindView(R.id.btn_to_do)
    Button btnTodo;//查看提现明细，成功时显示
    //传递过来的提现成功ID
    public static String WITH_DRAW_ID = "with_draw_id";
    public static String WITH_DRAW_KEY = "with_draw_key";
    private String cashId;//用来查看提现明细的ID
    private String transferKey;//转账的key

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
    public void onBackPressed() {
        super.onBackPressed();
        MyApplication.getInstance().closeActivity();
        finish();
    }

    @Override
    protected void initData() {
        cashId = getIntent().getStringExtra(WITH_DRAW_ID);
        transferKey = getIntent().getStringExtra(WITH_DRAW_KEY);
        if(!XiKouUtils.isNullOrEmpty(cashId)){
            tvStatus.setText("转账成功");
            ivStatus.setImageResource(R.mipmap.ic_pay_success);
            btnTodo.setVisibility(View.VISIBLE);
        }else {
            ivStatus.setImageResource(R.mipmap.ic_pay_fail);
            tvStatus.setText("转账失败");
            btnTodo.setVisibility(View.GONE);
        }
        btnGoAccount.setText("返回我的账户");
        btnTodo.setText("查看转账明细");

        btnGoAccount.setOnClickListener(v -> {
            MyApplication.getInstance().closeActivity();
            finish();
        });

        btnTodo.setOnClickListener(v -> {
            MyApplication.getInstance().closeActivity();
            if(cashId != null && !TextUtils.isEmpty(cashId)){
                //点击跳转详情
                Intent intent = new Intent(mContext, TransferDetailingActivity.class);
                intent.putExtra(TransferDetailingActivity.RED_BAG_ID, cashId);
                intent.putExtra(TransferDetailingActivity.RED_BAG_KEY, transferKey);
                ActivityUtils.startActivity(intent);
                finish();
            }
        });
    }

}
