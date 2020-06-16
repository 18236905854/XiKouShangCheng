package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.utils.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName MyProtocolActivity
 * Description 我的协议页面
 * Author 卿凯
 * Date 2019/7/27/027
 * Version V1.0
 */
public class MyProtocolActivity extends BaseActivity {
    @BindView(R.id.iv_protocol_sell)
    ImageView ivProtocolSell;
    @BindView(R.id.tv_protocol_sell)
    TextView tvProtocolSell;
    @BindView(R.id.rl_protocol_sell)
    RelativeLayout rlProtocolSell;
    @BindView(R.id.iv_setting_protocol)
    ImageView ivSettingProtocol;
    @BindView(R.id.tv_setting_protocol)
    TextView tvSettingProtocol;
    @BindView(R.id.rl_protocol_user)
    RelativeLayout rlProtocolUser;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_protocol;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("我的协议");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.rl_protocol_sell, R.id.rl_protocol_user})
    public void clickView(View view){
        switch (view.getId()){
            case R.id.rl_protocol_sell://寄卖协议
                goProtocol(0);
                break;

            case R.id.rl_protocol_user:
                goProtocol(1);
                break;
        }
    }

    private void goProtocol(int position){
        Intent intent = new Intent(mContext, ProtocolWebViewActivity.class);
        if(position == 0){
            intent.putExtra(Constant.INTENT_URL, Constant.sellProtocolUrl);
        }else {
            intent.putExtra(Constant.INTENT_URL, Constant.protocolUrl);
        }
        intent.putExtra(ProtocolWebViewActivity.IS_SHOW, false);
        ActivityUtils.startActivity(intent);
    }

}
