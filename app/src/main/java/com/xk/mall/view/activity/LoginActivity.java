package com.xk.mall.view.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xk.mall.BuildConfig;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.api.ApiRetrofit;
import com.xk.mall.base.BaseContent;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.model.impl.LoginViewImpl;
import com.xk.mall.presenter.LoginPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.EventBusMessage;
import com.xk.mall.utils.InstallUtil;
import com.xk.mall.utils.KeyBoardUtils;
import com.xk.mall.view.widget.CommomDialog;
import com.xk.mall.view.widget.CommonPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName LoginActivity
 * Description 登录页面
 * Author 卿凯
 * Date 2019/6/4/004
 * Version V1.0
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginViewImpl, CommonPopupWindow.ViewInterface {

    @BindView(R.id.iv_login_close)
    ImageView ivLoginClose;//关闭页面按钮
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;//电话号码输入框
    @BindView(R.id.et_login_psd)
    EditText etLoginPsd;//密码或者验证码输入框
    @BindView(R.id.iv_login_delete)
    ImageView ivLoginDelete;//电话号码输入框中的删除按钮，点击清空输入框
    @BindView(R.id.btn_login_code)
    Button btnGetCode;//获取验证码按钮
    @BindView(R.id.btn_login)
    Button btnLogin;//登录按钮
    @BindView(R.id.tv_login_forget_psd)
    TextView tvLoginForgetPsd;//忘记密码按钮
    @BindView(R.id.tv_login_quick)
    TextView tvLoginQuick;//快速注册登录按钮
    @BindView(R.id.tv_login_protocol)
    TextView tvProtocol;//用户协议按钮
    @BindView(R.id.tv_login_wechat)
    TextView tvWechat;//微信登录按钮
    @BindView(R.id.rl_login_invitation)
    RelativeLayout rlLoginInvitation;//邀请码的输入框
    @BindView(R.id.et_login_invitation)
    EditText etLoginInvitation;//邀请码输入框
    @BindView(R.id.rl_login_bottom)
    RelativeLayout rlBottom;
    private int count = 59;//计时器初始值
    private boolean isLoginByPhoneAndPsd = false;//是否是手机和密码登录,默认验证码登录
    private boolean isRegister = true;//表示当前手机号是否注册
    private IWXAPI api;
    private WXBroadcastReceiver wxBroadcastReceiver;
    /**是否显示邀请码输入框的key*/
    public static String IS_SHOW_CODE = "is_show_code";
    /**扫码传递过来的邀请码的key*/
    public static String INVITE_CODE = "invite_code";
    private boolean isShowCode;//是否显示邀请码输入框
    private String inviteCode = "";//邀请码
    private static final int PROTOCOL_CODE = 301;
    private String phone;
    private String code;
    private CommonPopupWindow popupWindow;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setStatusBar(getResources().getColor(R.color.colorWhite));
    }

    @OnClick({R.id.iv_login_close, R.id.iv_login_delete, R.id.btn_login_code,
            R.id.tv_login_forget_psd, R.id.btn_login, R.id.tv_login_quick, R.id.tv_login_protocol,
                R.id.tv_login_wechat})
    public void clickView(View view){
        switch (view.getId()){
            case R.id.iv_login_close://关闭按钮
                finish();
                break;

            case R.id.iv_login_delete://清空手机号按钮
                etLoginPhone.setText("");
                ivLoginDelete.setVisibility(View.GONE);
                break;

            case R.id.btn_login_code://点击获取验证码
                getCode();
                break;

            case R.id.tv_login_forget_psd://手机密码登录或者忘记密码
                if(isLoginByPhoneAndPsd){
                    //忘记密码,跳转到设置密码页面
                    ActivityUtils.startActivity(ForgetActivity.class);
                }else {
                    //账号密码登录
                    changeToLoginByPhoneAndPsd();
                }
                break;

            case R.id.btn_login://点击登录,调用登录接口进行登录
                checkCodeAndLogin();
                break;

            case R.id.tv_login_quick://快速注册登录
                ActivityUtils.startActivity(RegisterActivity.class);
                break;

            case R.id.tv_login_protocol://用户协议
                Intent intent = new Intent(this,WebViewActivity.class);
                intent.putExtra(Constant.INTENT_URL, Constant.protocolUrl);
                ActivityUtils.startActivity(intent);
                break;

            case R.id.tv_login_wechat:
                //点击微信登录
                if(InstallUtil.isWeChatAppInstalled(mContext)){
                    regToWx();
                    sendReq();
                }else {
                    new CommomDialog(mContext, R.style.mydialog, "请先安装微信，再进行授权登录", (dialog, confirm) -> {
                        dialog.dismiss();
                    }).show();
                }
                break;
        }
    }

    /**
     * 注册到微信
     */
    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, BaseContent.UM_WECHAT_KEY, true);

        // 将应用的appId注册到微信
        api.registerApp(BaseContent.UM_WECHAT_KEY);

        //建议动态监听微信启动广播进行注册到微信
        wxBroadcastReceiver = new WXBroadcastReceiver();
        registerReceiver(wxBroadcastReceiver, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(wxBroadcastReceiver != null){
            unregisterReceiver(wxBroadcastReceiver);
        }
    }

    private class WXBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 将该app注册到微信
            api.registerApp(BaseContent.UM_WECHAT_KEY);

        }
    }

    private void sendReq(){
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo,snsapi_friend,snsapi_message,snsapi_contact";
        req.state = "none";
        api.sendReq(req);
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(EventBusMessage message){
        String code = message.getMessage();
        //用code去请求服务器
        mPresenter.wxLogin(BaseContent.UM_WECHAT_KEY, code);
    }

    /**
     * 微信登录的回调
     */
    @Override
    public void onWXLoginSuccess(BaseModel<LoginBean> o) {
        MyApplication.token = o.getData().token;
        MyApplication.refreshToken = o.getData().refreshToken;
        MyApplication.userId = o.getData().userId;
        SPUtils.getInstance().put(Constant.SP_TOKEN, MyApplication.token);
        SPUtils.getInstance().put(Constant.SP_REFRESH_TOKEN, MyApplication.refreshToken);
        SPUtils.getInstance().put(Constant.SP_USER_ID, MyApplication.userId);
        SPUtils.getInstance().put(Constant.IS_LOGIN,true);
        //登录成功之后获取用户ID再去调用获取用户信息的接口，保存在数据库中并关闭页面
        EventBus.getDefault().post(o.getData());
        finish();
    }

    /**
     * 变成手机号码和密码登录
     */
    private void changeToLoginByPhoneAndPsd(){
        isLoginByPhoneAndPsd = true;
        //隐藏获取验证码按钮
        btnGetCode.setVisibility(View.GONE);
        count = 60;
        //修改密码输入框的hint
        etLoginPsd.setHint(getResources().getString(R.string.login_psd_hint));
        //修改密码输入框的inputType
        etLoginPsd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //手机密码登录按钮文字设为忘记密码
        tvLoginForgetPsd.setText(getResources().getString(R.string.login_forget));
    }

    /**
     * 检查验证码，验证成功之后显示设置密码页面
     */
    private void checkCodeAndLogin(){
        KeyBoardUtils.hideInputForce(this);
        phone = etLoginPhone.getText().toString();
        if(TextUtils.isEmpty(phone)){
            ToastUtils.showShortToast(LoginActivity.this,"请输入手机号码");
            return;
        }else if(phone.length() != 11){
            ToastUtils.showShortToast(LoginActivity.this,"手机号码格式有误");
            return;
        }
        code = etLoginPsd.getText().toString();
        if(TextUtils.isEmpty(code) || code.length() != 4){
//            ToastUtils.showShort("验证码未空，请输入验证码");
            ToastUtils.showShortToast(this, "验证码输入有误，请重新输入");
        }else {
            if(isShowCode){
                //判断需要填写邀请码
                inviteCode = etLoginInvitation.getText().toString();
                if(TextUtils.isEmpty(inviteCode)){
                    ToastUtils.showShortToast(LoginActivity.this,"请输入邀请码");
                    return;
                }else {
                    mPresenter.register(phone, code, inviteCode, "", 0);
                }
            }else if(!isRegister){
                //判断需要填写邀请码
                inviteCode = etLoginInvitation.getText().toString();
                if(TextUtils.isEmpty(inviteCode)){
                    ToastUtils.showShortToast(LoginActivity.this,"请输入邀请码");
                    return;
                }else {
                    mPresenter.register(phone, code, inviteCode, "", 0);
                }
            }else {
                mPresenter.login(phone, code);
            }
        }
    }



    /**
     * 获取验证码的方法  测试
     */
    private void getCode(){
        String phone = etLoginPhone.getText().toString();
        if(TextUtils.isEmpty(phone)){
            //提示用户填写手机号码

            ToastUtils.showShortToast(LoginActivity.this,"请填写手机号码");
        } else if(phone.length() != 11){
            ToastUtils.showShortToast(LoginActivity.this,"手机号码格式有误");
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == PROTOCOL_CODE){
            Log.e(TAG, "onActivityResult: ");
            isRegister=false;//未注册的手机号

        }
    }
    @Override
    protected void initData() {
        isShowCode = getIntent().getBooleanExtra(IS_SHOW_CODE, false);
        inviteCode = getIntent().getStringExtra(INVITE_CODE);
        if(isShowCode && !TextUtils.isEmpty(inviteCode)){
            rlLoginInvitation.setVisibility(View.VISIBLE);
            etLoginInvitation.setText(inviteCode);
            btnLogin.setText("注册");
            //延迟1s打开协议页面
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(mContext, ProtocolWebViewActivity.class);
                intent.putExtra(ProtocolWebViewActivity.IS_SHOW, true);
                intent.putExtra(Constant.INTENT_URL, Constant.protocolUrl);
                startActivityForResult(intent,PROTOCOL_CODE);
            }, 800);
        }
        etLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isShowCode){
                    rlLoginInvitation.setVisibility(View.GONE);
                }
                isRegister = true;
                if(rlLoginInvitation.getVisibility() == View.VISIBLE){
                    btnLogin.setText("注册");
                }else {
                    btnLogin.setText("登录");
                }
                String phone = s.toString();
                if(TextUtils.isEmpty(phone)){
                    btnLogin.setEnabled(false);
                    ivLoginDelete.setVisibility(View.GONE);
                }else {
                    btnLogin.setEnabled(true);
                    ivLoginDelete.setVisibility(View.VISIBLE);
                }
            }
        });

        //获取信息并绑定
        if(!"product".equals(BuildConfig.FLAVOR)){
            rlBottom.setOnLongClickListener(v -> {
                chooseUrl();
                return false;
            });
        }

    }

    @Override
    public void getChildView(View view, int layoutResId) {
        if(layoutResId == R.layout.pop_choose_url){
            TextView tvTitle = view.findViewById(R.id.txt_title);
            TextView tvContentOne = view.findViewById(R.id.tv_content_one);
            TextView tvContentTwo = view.findViewById(R.id.tv_content_two);
            TextView tvContentThree = view.findViewById(R.id.tv_content_three);
            TextView tvContentFour = view.findViewById(R.id.tv_content_four);
            TextView tvCancel = view.findViewById(R.id.btn_cancel);
            tvTitle.setText("选择环境");
            tvContentOne.setText("正式环境");
            tvContentTwo.setText("测试环境");
            tvContentThree.setText("dev环境");
            tvContentFour.setText("预发布环境");
            tvContentOne.setOnClickListener(v -> {
                ApiRetrofit.BASE_SERVER_URL = BaseContent.baseProductUrl;
                ApiRetrofit.getInstance().setBaseServerUrl(ApiRetrofit.BASE_SERVER_URL);
                SPUtils.getInstance().put(Constant.SP_BASE_URL, ApiRetrofit.BASE_SERVER_URL);
                popupWindow.dismiss();
            });
            tvContentTwo.setOnClickListener(v -> {
                ApiRetrofit.BASE_SERVER_URL = BaseContent.baseTestUrl;
                ApiRetrofit.getInstance().setBaseServerUrl(ApiRetrofit.BASE_SERVER_URL);
                SPUtils.getInstance().put(Constant.SP_BASE_URL, ApiRetrofit.BASE_SERVER_URL);
                popupWindow.dismiss();
            });
            tvContentThree.setOnClickListener(v -> {
                ApiRetrofit.BASE_SERVER_URL = BaseContent.baseDevUrl;
                ApiRetrofit.getInstance().setBaseServerUrl(ApiRetrofit.BASE_SERVER_URL);
                SPUtils.getInstance().put(Constant.SP_BASE_URL, ApiRetrofit.BASE_SERVER_URL);
                popupWindow.dismiss();
            });
            tvContentFour.setOnClickListener(v -> {
                ApiRetrofit.BASE_SERVER_URL = BaseContent.basePreUrl;
                ApiRetrofit.getInstance().setBaseServerUrl(ApiRetrofit.BASE_SERVER_URL);
                SPUtils.getInstance().put(Constant.SP_BASE_URL, ApiRetrofit.BASE_SERVER_URL);
                popupWindow.dismiss();
            });
            tvCancel.setOnClickListener(v -> popupWindow.dismiss());
        }
    }

    private void chooseUrl(){
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.pop_choose_url, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.pop_choose_url)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 登录成功的回调
     */
    @Override
    public void onLoginSuccess(BaseModel<LoginBean> o) {
        //获取返回的token和用户ID
        Logger.e("onLoginSuccess:" + o.getCode());
        isRegister = true;
        MyApplication.token = o.getData().token;
        MyApplication.refreshToken = o.getData().refreshToken;
        MyApplication.userId = o.getData().userId;
        SPUtils.getInstance().put(Constant.SP_TOKEN, MyApplication.token);
        SPUtils.getInstance().put(Constant.SP_REFRESH_TOKEN, MyApplication.refreshToken);
        SPUtils.getInstance().put(Constant.SP_USER_ID, MyApplication.userId);
        SPUtils.getInstance().put(Constant.IS_LOGIN,true);
        //登录成功之后获取用户ID再去调用获取用户信息的接口，保存在数据库中并关闭页面
        EventBus.getDefault().post(o.getData());
        finish();
    }

    @Override
    public void onErrorCode(BaseModel model) {
        Logger.e("onErrorCode返回的msg:" + model.getMsg());
        if(model.getCode() == BaseObserver.CONNECT_NOT_REGISTER){
            //表示用户手机号未注册，需要显示填写邀请码的布局
            isRegister = false;
            rlLoginInvitation.setVisibility(View.VISIBLE);
            btnLogin.setText("注册");
            ToastUtils.showShortToast(LoginActivity.this, "用户未注册，请输入邀请码注册");

            //跳转协议页面  延迟1s打开协议页面
            if(!isShowCode){
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(mContext, ProtocolWebViewActivity.class);
                    intent.putExtra(ProtocolWebViewActivity.IS_SHOW, true);
                    intent.putExtra(Constant.INTENT_URL, Constant.protocolUrl);
                    startActivityForResult(intent,PROTOCOL_CODE);
                }, 800);
            }

        }else if(model.getCode() == BaseObserver.CONNECT_CODE_NOT_MATCH){//code==1表示验证码有误
//            //验证码有误
//            isRegister = true;
            ToastUtils.showShortToast(LoginActivity.this,"验证码有误，请重新输入");
        }else if(model.getCode() == BaseObserver.CONNECT_INVALID_INVATATION_CODE){
            ToastUtils.showShortToast(LoginActivity.this,model.getMsg());
        }else if(model.getCode()==1){
            ToastUtils.showShortToast(LoginActivity.this,"邀请码不存在");
        }else if(model.getCode() == BaseObserver.CONNECT_WX_NOT_BIND){
            //微信未绑定手机号，获取msg做为unionid去进行注册，跳转绑定手机号页面
            String unionid = model.getMsg();
            Intent intent = new Intent(mContext, BindPhoneActivity.class);
            intent.putExtra("unionid", unionid);
            intent.putExtra("invite_code",inviteCode);
            ActivityUtils.startActivity(intent);
            finish();
        }else {
            super.onErrorCode(model);
        }
    }

    /**
     * 获取验证码成功的回调
     */
    @Override
    public void onGetValidCodeSuccess(BaseModel loginBean) {

    }

    /**
     * 验证手机号是否已经注册成功的回调
     */
    @Override
    public void isRegistSuccess(BaseModel baseModel) {

    }

    /**
     * 注册成功的回调
     */
    @Override
    public void onRegisterSuccess(BaseModel<LoginBean> baseModel) {
        if(baseModel.getCode() == -1){
            ToastUtils.showShortToast(LoginActivity.this,"邀请码不存在，请确认");
        }else {
            MyApplication.token = baseModel.getData().token;
            MyApplication.refreshToken = baseModel.getData().refreshToken;
            MyApplication.userId = baseModel.getData().userId;
            SPUtils.getInstance().put(Constant.SP_TOKEN, MyApplication.token);
            SPUtils.getInstance().put(Constant.SP_REFRESH_TOKEN, MyApplication.refreshToken);
            SPUtils.getInstance().put(Constant.SP_USER_ID, MyApplication.userId);
            //登录成功之后获取用户ID再去调用获取用户信息的接口，保存在数据库中并关闭页面
            EventBus.getDefault().post(baseModel.getData());
            finish();
        }
    }

}
