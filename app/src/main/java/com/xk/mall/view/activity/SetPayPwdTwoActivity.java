package com.xk.mall.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.impl.SetPwdViewImpl;
import com.xk.mall.model.request.SetPwdRequestBody;
import com.xk.mall.presenter.SetPwdPresenter;
import com.xk.mall.utils.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName SetPayPwdTwoActivity
 * Description 设置支付密码 two
 * Author
 * Date
 * Version
 */
public class SetPayPwdTwoActivity extends BaseActivity<SetPwdPresenter> implements SetPwdViewImpl {
    private static final String TAG = "SetPayPwdTwoActivity";
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.et_psd_two)
    EditText etPsdTwo;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    private String phone;
    private String code;
    @Override
    protected SetPwdPresenter createPresenter() {
        return new SetPwdPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_paypwd_two;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar_title.setText("支付密码");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(SetPayPwdTwoActivity.this);
            phone=getIntent().getStringExtra("phone_num");
            code = getIntent().getStringExtra("inviteCode");
            if(!TextUtils.isEmpty(phone)){
                tvPhone.setText(phone);
            }
            initEvent();
    }

    private void initEvent() {
        etPsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String pwd=s.toString();
                if(!TextUtils.isEmpty(pwd)&&!TextUtils.isEmpty(etPsdTwo.getText().toString())){
                    btnConfirm.setEnabled(true);
                }else{
                    btnConfirm.setEnabled(false);
                }
            }
        });


        etPsdTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String confirmPwd=s.toString();
                if(!TextUtils.isEmpty(confirmPwd)&&!TextUtils.isEmpty(etPsd.getText().toString())){
                    btnConfirm.setEnabled(true);
                }else{
                    btnConfirm.setEnabled(false);
                }
            }
        });
    }

    @OnClick(R.id.btn_confirm)
    public void onClick() {
        if(etPsd.getText().length()<6 ||etPsdTwo.getText().length()<6){
            ToastUtils.showShortToast(mContext,"请输入6位数支付密码");
            return ;
        }
        if(!etPsd.getText().toString().equals(etPsdTwo.getText().toString())){
            ToastUtils.showShortToast(mContext,"两次密码输入不一致");
            return;
        }

        SetPwdRequestBody requestBody=new SetPwdRequestBody();
        requestBody.setUserId(MyApplication.userId);
        String key = "xikouxikouxikoux";
        String input1 = etPsd.getText().toString();
        String input2 = etPsdTwo.getText().toString();
        byte[] result1 = EncryptUtils.encryptAES(input1.getBytes(), key.getBytes(), "AES/CBC/PKCS5Padding", key.getBytes());
        byte[] result2 = EncryptUtils.encryptAES(input2.getBytes(), key.getBytes(), "AES/CBC/PKCS5Padding", key.getBytes());
        requestBody.setPayPassword(EncodeUtils.base64Encode2String(result1));
        requestBody.setConfirmPayPassword(EncodeUtils.base64Encode2String(result2));
        requestBody.setAccountType(1);
        requestBody.setMobile(phone);
        requestBody.setVerificationCode(code);
        mPresenter.setPayPwd(requestBody);
    }
    //设置支付密码成功
    @Override
    public void onSavePayPwdSuc(BaseModel o) {
        ToastUtils.showShortToast(mContext,o.getMsg());
        SPUtils.getInstance().put(Constant.USER_SET_PWD,1);
        MyApplication.getInstance().closeActivityNoPayOrderActivty();
    }

    @Override
    public void onGetValidCodeSuccess(BaseModel loginBean) {

    }

    @Override
    public void onCodeNextSucess(BaseModel<Boolean> o) {

    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        ToastUtils.showShortToast(mContext,model.getMsg());
    }
}
