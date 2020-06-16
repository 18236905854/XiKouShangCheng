package com.xk.mall.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName ForgetActivity
 * Description 忘记密码的页面
 * Author Kay
 * Date 2019/6/6/006
 * Version V1.0
 */
public class ForgetActivity extends BaseActivity {

    @BindView(R.id.tv_forget_phone)
    TextView tvForgetPhone;//设置重置密码的账号的textView
    @BindView(R.id.rl_forget_phone)
    RelativeLayout rlForgetPhone;//重置密码的账号的电话号码输入框
    @BindView(R.id.et_forget_phone)
    EditText etForgetPhone;//电话号码输入框
    @BindView(R.id.iv_forget_delete)
    ImageView ivForgetDelete;//电话号码输入框清除按钮
    @BindView(R.id.et_forget_psd)
    EditText etForgetPsd;//验证码输入框，验证完验证码之后变成密码输入框
    @BindView(R.id.btn_forget_code)
    Button btnForgetCode;//获取验证码按钮
    @BindView(R.id.rlForgetPsdTwo)
    RelativeLayout rlForgetPsdTwo;//确认密码输入框父布局
    @BindView(R.id.et_forget_psd_two)
    EditText etForgetPsdTwo;//确认密码输入框
    @BindView(R.id.btn_forget_next)
    Button btnForgetNext;//下一步按钮
    private int count = 59;
    private boolean isGetCode = false;//检查是否获取了验证码

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        showLeft(true);
        setStatusBar(getResources().getColor(R.color.colorWhite));
        setTitle("设置密码");
    }

    /**
     * 所有View的点击事件
     */
    @OnClick({R.id.iv_forget_delete, R.id.btn_forget_code, R.id.btn_forget_next})
    public void clickView(View view){
        switch (view.getId()){
            case R.id.iv_forget_delete://清空电话号码按钮
                etForgetPhone.setText("");
                ivForgetDelete.setVisibility(View.GONE);
                break;

            case R.id.btn_forget_code://获取验证码按钮
                getCode();
                break;

            case R.id.btn_forget_next://下一步按钮
                goNext();
                break;
        }
    }

    /**
     * 点击下一步
     */
    private void goNext(){
        if(!isGetCode){//当没有获取验证码时
            isGetCode = true;
            String phone = etForgetPhone.getText().toString();
            if(TextUtils.isEmpty(phone)){
                ToastUtils.showShort("请输入手机号码");
                return;
            }
            String code = etForgetPsd.getText().toString();
            if(TextUtils.isEmpty(code)){
                ToastUtils.showShort("验证码错误");
                return;
            }
            //调用接口检查验证码
            btnForgetCode.setVisibility(View.GONE);
            rlForgetPhone.setVisibility(View.GONE);
            tvForgetPhone.setVisibility(View.VISIBLE);
            tvForgetPhone.setText("重置密码的账号:" + phone);
            rlForgetPsdTwo.setVisibility(View.VISIBLE);
            etForgetPsd.setText("");
            etForgetPsd.setHint(getResources().getString(R.string.forget_psd_hint));
            etForgetPsd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

        }else {
            String psd = etForgetPsd.getText().toString();
            String psdTwo = etForgetPsdTwo.getText().toString();
            if(!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(psdTwo)){
                //检查两个字符串是否相等
                if(!psd.equals(psdTwo)){
                    ToastUtils.showShort("2次输入密码不一致");
                }else {
                    ToastUtils.showShort("登录成功,关闭页面");
                }
            }else {
                ToastUtils.showShort("2次输入密码不一致或为空");
            }
        }
    }

    /**
     * 获取验证码的方法  测试
     */
    private void getCode(){
        String phone = etForgetPhone.getText().toString();
        if(TextUtils.isEmpty(phone)){
            //提示用户填写手机号码
            ToastUtils.showShort("请填写手机号码");
        }else {
            Logger.e("开始倒计时");
            etForgetPhone.setEnabled(false);
            //调用获取验证码接口，显示倒计时
            Observable.interval(0, 1, TimeUnit.SECONDS) //0延迟  每隔1秒触发
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                    .take(count + 1) //设置循环次数
                    .map(aLong -> count - aLong) //从60-1
                    .doOnSubscribe(disposable -> btnForgetCode.setClickable(false))
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Long aLong) {
                            btnForgetCode.setText(aLong + "s 后重发");
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            btnForgetCode.setText(getResources().getString(R.string.login_get_code));
                            btnForgetCode.setClickable(true);
                            etForgetPhone.setEnabled(true);
                            count = 59;
                        }
                    });
        }
    }


    @Override
    protected void initData() {
        etForgetPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = s.toString();
                if(TextUtils.isEmpty(phone)){
                    ivForgetDelete.setVisibility(View.GONE);
                }else {
                    ivForgetDelete.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
