package com.xk.mall.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加 选择填写类型 提现账号
 */
public class SelectAccountTypeActivity extends BaseActivity {
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.rl_wechat)
    RelativeLayout rlWechat;
    @BindView(R.id.rl_zfb)
    RelativeLayout rlZfb;
    @BindView(R.id.rl_bank_person)
    RelativeLayout rlBankPerson;
    @BindView(R.id.rl_back_company)
    RelativeLayout rlBackCompany;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_account;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        myToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.rl_wechat, R.id.rl_zfb, R.id.rl_bank_person, R.id.rl_back_company})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_wechat:
                break;
            case R.id.rl_zfb:
                break;
            case R.id.rl_bank_person://银行卡 个人账号
                ActivityUtils.startActivity(AddBankCardActivity.class);
                break;
            case R.id.rl_back_company://银行卡 对公账号

                break;
        }
    }
}
