package com.xk.mall.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.impl.EnterLogisticsViewImpl;
import com.xk.mall.presenter.EnterLogisticsPresenter;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.ClearEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * @ClassName: EnterLogisticsInfoActivity
 * @Description: 用户填写物流信息页面
 * @Author: 卿凯
 * @Date: 2019/12/10/010 14:17
 * @Version: 1.0
 */
public class EnterLogisticsInfoActivity extends BaseActivity<EnterLogisticsPresenter> implements EnterLogisticsViewImpl {
    @BindView(R.id.tv_merchant_note)
    TextView tvMerchantNote;//商家备注
    @BindView(R.id.et_logistic_name)
    ClearEditText etLogisticName;//物流公司名称
    @BindView(R.id.et_logistic_no)
    ClearEditText etLogisticNo;//物流单号
    @BindView(R.id.btn_submit)
    Button btnSubmit;//提交按钮
    private String orderNo;//订单号
    private String orderNote;//商家备注
    /**intent传递过来订单号的key*/
    public final static String ORDER_NO = "order_no";
    /**intent传递过来的商家备注key*/
    public final static String ORDER_NOTE = "order_note";

    @Override
    protected EnterLogisticsPresenter createPresenter() {
        return new EnterLogisticsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_enter_logistics_info;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("填写物流");
    }

    @Override
    protected void initData() {
        orderNo = getIntent().getStringExtra(ORDER_NO);
        orderNote = getIntent().getStringExtra(ORDER_NOTE);
        tvMerchantNote.setText(orderNote);
        btnSubmit.setEnabled(false);
        etLogisticName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String number = etLogisticNo.getText().toString().trim();
                if(!XiKouUtils.isNullOrEmpty(number) && !XiKouUtils.isNullOrEmpty(s.toString().trim())){
                    btnSubmit.setEnabled(true);
                }
            }
        });

        etLogisticNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String number = etLogisticNo.getText().toString().trim();
                if(!XiKouUtils.isNullOrEmpty(number) && !XiKouUtils.isNullOrEmpty(s.toString().trim())){
                    btnSubmit.setEnabled(true);
                }
            }
        });

        btnSubmit.setOnClickListener(v -> {
            //点击提交
            String company = etLogisticName.getText().toString().trim();
            String number = etLogisticNo.getText().toString().trim();
            mPresenter.postLogisticInfo(company, number, orderNo);
        });
    }

    @Override
    public void onPostSuccess(BaseModel baseModel) {
        if(baseModel != null){
            ToastUtils.showShort("提交成功，请等待审核");
            //刷新数据
            EventBus.getDefault().post(PayBackDetailActivity.REFRESH_DATA);
            finish();
        }
    }
}
