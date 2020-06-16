package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.model.impl.LoginViewImpl;
import com.xk.mall.model.request.InvitationRequestBody;
import com.xk.mall.model.request.LoginRequestBody;
import com.xk.mall.model.request.ValidCodeRequestBody;
import com.xk.mall.model.request.WXLoginRequestBody;

/**
 * ClassName LoginPresenter
 * Description 登录页面的Presenter
 * Author 卿凯
 * Date 2019/6/14/014
 * Version V1.0
 */
public class LoginPresenter extends BasePresenter<LoginViewImpl> {
    public LoginPresenter(LoginViewImpl baseView) {
        super(baseView);
    }

    //获取验证码接口
    public void getValidCode(String phoneNum){
        addDisposable(apiServer.getValidCode(new ValidCodeRequestBody(phoneNum)), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetValidCodeSuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    //登录接口
    public void login(String phoneNum, String validCode){
        addDisposable(apiServer.login(new LoginRequestBody(phoneNum, validCode)), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onLoginSuccess((BaseModel<LoginBean>) o);
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    //验证手机号是否已经注册
    public void isRegist(String phone, int type){
        addDisposable(apiServer.isRegist(phone, type), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onLoginSuccess((BaseModel<LoginBean>) o);
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    public void wxLogin(String appId, String code){
        addDisposable(apiServer.wxLogin(new WXLoginRequestBody(appId, code)), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onWXLoginSuccess((BaseModel<LoginBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    //注册接口
    public void register(String phoneNum, String validCode, String invitationCode, String thirdId, int type){
        addDisposable(apiServer.register(new InvitationRequestBody(phoneNum, validCode, invitationCode, thirdId, type)), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onRegisterSuccess((BaseModel<LoginBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
