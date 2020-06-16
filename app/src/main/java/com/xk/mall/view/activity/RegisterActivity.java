package com.xk.mall.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName RegisterActivity
 * Description 注册页面
 * Author 卿凯
 * Date 2019/6/4/004
 * Version V1.0
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;//手机号输入框
    @BindView(R.id.iv_register_delete)
    ImageView ivRegisterDelete;//手机号输入框的清空按钮
    @BindView(R.id.et_register_psd)
    EditText etRegisterPsd;//验证码输入框
    @BindView(R.id.btn_register_code)
    Button btnGetCode;//获取验证码按钮
    @BindView(R.id.et_register_invitation)
    EditText etInvitation;//邀请码输入框
    @BindView(R.id.btn_register)
    Button btnRegister;//注册并登录按钮
    @BindView(R.id.rl_register_bottom)
    RelativeLayout llRegisterBottom;//注册按钮下面所有view的父布局,当点击注册并登录按钮之后需要隐藏
    @BindView(R.id.tv_register_protocol)
    TextView tvProtocol;//同意协议的textView，目前用来测试获取第三方登录的资料
    private int count = 59;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        showLeft(true);
        setStatusBar(getResources().getColor(R.color.colorWhite));
        setTitle("快速注册");
    }

    @Override
    protected void initData() {
        etRegisterPhone.addTextChangedListener(new TextWatcher() {
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
                    ivRegisterDelete.setVisibility(View.GONE);
                }else {
                    ivRegisterDelete.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.iv_register_delete, R.id.btn_register_code, R.id.btn_register, R.id.tv_register_qq,
            R.id.tv_register_wechat, R.id.tv_register_sina, R.id.tv_register_protocol})
    public void ClickView(View view){
        switch (view.getId()){
            case R.id.iv_register_delete://清空按钮
                etRegisterPhone.setText("");
                ivRegisterDelete.setVisibility(View.GONE);
                break;

            case R.id.btn_register://注册并登录按钮
                login();
                break;

            case R.id.btn_register_code://获取验证码按钮
                getCode();
                break;

            case R.id.tv_register_qq://点击QQ登录
                UMShareAPI.get(mContext).getPlatformInfo(this, SHARE_MEDIA.QQ.toSnsPlatform().mPlatform, authListener);
                break;

            case R.id.tv_register_wechat://点击微信登录
                UMShareAPI.get(mContext).getPlatformInfo(this, SHARE_MEDIA.WEIXIN.toSnsPlatform().mPlatform, authListener);
                break;

            case R.id.tv_register_sina://点击微博登录
                UMShareAPI.get(mContext).getPlatformInfo(this, SHARE_MEDIA.SINA.toSnsPlatform().mPlatform, authListener);
                break;

            case R.id.tv_register_protocol://点击跳转用户协议

                break;
        }
    }

    /**
     * 确认注册及登录按钮事件
     */
    private void login(){
        //手机号
        String phone = etRegisterPhone.getText().toString();
        //验证码
        String password = etRegisterPsd.getText().toString();
        //邀请码
        String invitationCode = etInvitation.getText().toString();
        //检查校验三个输入的值
        if(TextUtils.isEmpty(phone)){
            ToastUtils.showShort(getResources().getString(R.string.register_phone_hint));
            return;
        }

        if(TextUtils.isEmpty(password)){
            ToastUtils.showShort(getResources().getString(R.string.register_code_hint));
            return;
        }

        if(TextUtils.isEmpty(invitationCode)){
            ToastUtils.showShort(getResources().getString(R.string.register_invitation_code_hint));
            return;
        }
    }

    /**
     * 获取验证码的方法
     */
    private void getCode(){
        String phone = etRegisterPhone.getText().toString();
        if(TextUtils.isEmpty(phone)){
            //提示用户填写手机号码
            ToastUtils.showShort(getResources().getString(R.string.register_phone_hint));
        }else {
            //调用获取验证码接口，显示倒计时
            etRegisterPhone.setEnabled(false);
            Observable.interval(0, 1, TimeUnit.SECONDS) //0延迟  每隔1秒触发
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                    .take(count + 1) //设置循环次数
                    .map(aLong -> count - aLong) //从60-1
                    .doOnSubscribe(disposable -> btnGetCode.setClickable(false))
                    .subscribe(new Observer<Long>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Long aLong) {
                    btnGetCode.setText(aLong + "s 重新获取");
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {
                    btnGetCode.setText(getResources().getString(R.string.login_get_code));
                    count = 59;
                    btnGetCode.setClickable(true);
                    etRegisterPhone.setEnabled(true);
                }
            });
        }
    }

    /**
     * 友盟第三方登录需要重写的方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 友盟第三方登录需要重写的方法
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    /**
     * 友盟第三方登录需要重写的方法
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    /**
     * 友盟第三方登录的回调
     */
    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //显示加载框
//            WaitDialog.show(RegisterActivity.this, "加载中...");
            showLoading();
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(mContext, "成功了", Toast.LENGTH_LONG).show();
            StringBuilder sb = new StringBuilder();
            for (String key : data.keySet()) {
                sb.append(key).append(" : ").append(data.get(key)).append("\n");
                Logger.e(key + ":" + data.get(key));
            }
            tvProtocol.setText(sb.toString());
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(mContext, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };

}
