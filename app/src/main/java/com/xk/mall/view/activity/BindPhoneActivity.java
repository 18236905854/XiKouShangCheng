package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.model.eventbean.BindWXEvent;
import com.xk.mall.model.impl.LoginViewImpl;
import com.xk.mall.presenter.LoginPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.KeyBoardUtils;
import com.xk.mall.utils.XiKouUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName BindPhoneActivity
 * Description 绑定手机页面
 * Author 卿凯
 * Date 2019/6/19/019
 * Version V1.0
 */
public class BindPhoneActivity extends BaseActivity<LoginPresenter> implements LoginViewImpl {

    @BindView(R.id.et_bind_phone_phone)
    EditText etLoginPhone;//电话号码输入框

    @BindView(R.id.et_bind_phone_psd)
    EditText etBindPhonePsd;//验证码输入框
    @BindView(R.id.et_bind_phone_invitation)
    EditText etBindPhoneInvitation;//邀请码输入框
    @BindView(R.id.btn_bind_phone_code)
    Button btnGetCode;//获取验证码按钮
    @BindView(R.id.btn_bind)
    Button btnBind;//绑定按钮
    @BindView(R.id.rl_bind_phone_invitation)
    RelativeLayout rlInvitation;//邀请码输入框
    private int count = 59;//计时器初始值
    private String unionid = "";//微信登录传递过来的unionid
    private String inviteCode;//邀请码
    private String phone;//个人信息页面传递的手机号


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.bind_phone_title));
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        unionid = intent.getStringExtra("unionid");
        inviteCode=intent.getStringExtra("invite_code");
        phone = intent.getStringExtra("user_phone");//用户手机号，当不为空时表示从个人信息页面过来
        if(!XiKouUtils.isNullOrEmpty(phone)){
            etLoginPhone.setText(phone);
            etLoginPhone.setEnabled(false);
        }
        etBindPhoneInvitation.setText(inviteCode);


        etLoginPhone.addTextChangedListener(new TextWatcher() {
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
                    btnBind.setEnabled(false);
                }else {
                    btnBind.setEnabled(true);
                }
            }
        });

        etBindPhonePsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String  code = s.toString();
                if(TextUtils.isEmpty(code)){
                    btnBind.setEnabled(false);
                }else {
                    btnBind.setEnabled(true);
                }
            }
        });

        etBindPhoneInvitation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = s.toString();
                if(TextUtils.isEmpty(phone) && etBindPhoneInvitation.getVisibility() == View.VISIBLE){
                    btnBind.setEnabled(false);
                }else {
                    btnBind.setEnabled(true);
                }
            }
        });
    }

    @OnClick({ R.id.btn_bind_phone_code, R.id.btn_bind})
    public void onClickView(View view){
        switch (view.getId()){


            case R.id.btn_bind_phone_code://获取验证码
                getCode();
                break;

            case R.id.btn_bind://绑定按钮
                //调用注册接口
                checkCodeAndLogin();
                break;
        }
    }

    /**
     * 检查验证码，验证成功之后显示设置密码页面
     */
    private void checkCodeAndLogin(){
        KeyBoardUtils.hideInputForce(this);
        String phone = etLoginPhone.getText().toString();
        if(TextUtils.isEmpty(phone)){
            ToastUtils.showShort("请输入手机号码");
            return;
        }else if(phone.length() != 11){
            ToastUtils.showShort("手机号码格式有误");
            return;
        }
        String code = etBindPhonePsd.getText().toString();
        if(TextUtils.isEmpty(code) || code.length() != 4){
//            ToastUtils.showShort("验证码未空，请输入验证码");
            ToastUtils.showShort("验证码输入有误，请重新输入");
            return;
        }

        //判断需要填写邀请码
        String  invitationCode = etBindPhoneInvitation.getText().toString();
        if(TextUtils.isEmpty(invitationCode) && rlInvitation.getVisibility() == View.VISIBLE){
            ToastUtils.showShort("请输入邀请码");
            return;
        }
        mPresenter.register(etLoginPhone.getText().toString().trim(), etBindPhonePsd.getText().toString(), invitationCode, unionid, 1);

    }

    /**
     * 获取验证码的方法  测试
     */
    private void getCode(){
        String phone = etLoginPhone.getText().toString();
        if(TextUtils.isEmpty(phone)){
            //提示用户填写手机号码
            ToastUtils.showShort("请填写手机号码");
        } else if(phone.length() != 11){
            ToastUtils.showShort("手机号码格式有误");
        } else {
            Logger.e("开始倒计时");
            etLoginPhone.setEnabled(false);
            mPresenter.getValidCode(phone);
            //调用获取验证码接口，显示倒计时
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
                            btnGetCode.setText(aLong + "s 后重发");
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            btnGetCode.setText(getResources().getString(R.string.login_get_code));
                            btnGetCode.setClickable(true);
                            etLoginPhone.setEnabled(true);
                            count = 59;
                        }
                    });
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
        Logger.e("onErrorCode返回的msg:" + model.getMsg());
        if(model.getCode() == BaseObserver.CONNECT_CODE_NOT_MATCH){
            showToast(model.getMsg());
        }else if(model.getCode() == BaseObserver.CONNECT_NOT_BIND){//手机号已注册，但未绑定
            rlInvitation.setVisibility(View.GONE);
        }else if(model.getCode() == BaseObserver.CONNECT_HAS_BIND_OTHER){//用户已绑定其他帐号
            showToast("手机号已经注册并绑定，请更换手机号，或者直接登录");
            rlInvitation.setVisibility(View.GONE);
        }else {
            rlInvitation.setVisibility(View.VISIBLE);
            super.onErrorCode(model);
        }
    }

    /**
     * 验证手机号是否已经注册接口
     */
    @Override
    public void isRegistSuccess(BaseModel baseModel) {

    }

    /**
     * 登录成功回调
     */
    @Override
    public void onLoginSuccess(BaseModel<LoginBean> o) {

    }

    /**
     * 获取验证码接口回调
     */
    @Override
    public void onGetValidCodeSuccess(BaseModel<LoginBean> loginBean) {

    }

    /**
     * 注册成功接口回调
     */
    @Override
    public void onRegisterSuccess(BaseModel<LoginBean> baseModel) {
        if(baseModel.getCode() == -1){
            ToastUtils.showShort("邀请码不存在，请确认");
        }else {
            MyApplication.token = baseModel.getData().token;
            MyApplication.refreshToken = baseModel.getData().refreshToken;
            MyApplication.userId = baseModel.getData().userId;
            SPUtils.getInstance().put(Constant.SP_TOKEN, MyApplication.token);
            SPUtils.getInstance().put(Constant.SP_REFRESH_TOKEN, MyApplication.refreshToken);
            SPUtils.getInstance().put(Constant.SP_USER_ID, MyApplication.userId);
            SPUtils.getInstance().put(Constant.IS_LOGIN,true);
            //登录成功之后获取用户ID再去调用获取用户信息的接口，保存在数据库中并关闭页面
            EventBus.getDefault().post(baseModel.getData());
            if(!XiKouUtils.isNullOrEmpty(phone)){
                //发送消息刷新页面
                EventBus.getDefault().post(new BindWXEvent(true));
            }
            finish();
        }
    }

    @Override
    public void onWXLoginSuccess(BaseModel<LoginBean> baseModel) {

    }
}
