package com.xk.mall.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.PhoneUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CommendInfoBean;
import com.xk.mall.model.impl.CommendInfoViewImpl;
import com.xk.mall.presenter.CommendInfoPresenter;
import com.xk.mall.utils.XiKouUtils;

import butterknife.BindView;

/**
 * ClassName CommendInfoActivity
 * Description 推荐人信息页面
 * Author 卿凯
 * Date 2019/7/25/025
 * Version V1.0
 */
public class CommendInfoActivity extends BaseActivity<CommendInfoPresenter> implements CommendInfoViewImpl {
    @BindView(R.id.tv_commend_phone)
    TextView tvCommendPhone;
    @BindView(R.id.iv_commend_phone)
    ImageView ivCommendPhone;
    @BindView(R.id.tv_commend_city_phone)
    TextView tvCommendCityPhone;

    @Override
    protected CommendInfoPresenter createPresenter() {
        return new CommendInfoPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commend_info;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("推荐人信息");
    }

    @Override
    protected void initData() {
        mPresenter.getCommendInfo(MyApplication.userId);
        tvCommendPhone.setOnClickListener(v -> {
            String phone = tvCommendPhone.getText().toString().trim();
            if(!XiKouUtils.isNullOrEmpty(phone) && TextUtils.isDigitsOnly(phone)){
                //拨打电话
                PhoneUtils.dial(phone);
            }
        });
        ivCommendPhone.setOnClickListener(v -> {
            String phone = tvCommendPhone.getText().toString().trim();
            if(!XiKouUtils.isNullOrEmpty(phone) && TextUtils.isDigitsOnly(phone)){
                //拨打电话
                PhoneUtils.dial(phone);
            }
        });
    }

    @Override
    public void onGetCommendInfoSuccess(BaseModel<CommendInfoBean> model) {
        if(model != null && model.getData() != null){
            if(TextUtils.isEmpty(model.getData().getMobile())){
                tvCommendCityPhone.setText("未知");
            }else {
                String phone = model.getData().getMobile();
                if(phone.length() <= 4){
                    tvCommendCityPhone.setText(model.getData().getMobile());
                }else {
                    //设为*号
                    int center = phone.length() / 2;//11位数除以2 = 5
                    int start = center - 2;//截取末位不包含，所以需要多向前移一位
                    int end = center + 2;
                    String stringBuilder = phone.substring(0, start) +
                            "****" + phone.substring(end);
                    tvCommendCityPhone.setText(stringBuilder);
                }
            }

            if(TextUtils.isEmpty(model.getData().getInvitationMobile())){
                tvCommendPhone.setText("未知");
            }else {
                tvCommendPhone.setText(model.getData().getInvitationMobile());
            }
        }
    }
}
