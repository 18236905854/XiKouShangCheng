package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.LoginBean;

/**
 * ClassName LoginViewImpl
 * Description 登录页面的view层接口
 * Author 卿凯
 * Date 2019/6/14/014
 * Version V1.0
 */
public interface LoginViewImpl extends BaseViewListener {
    //登录接口
    void onLoginSuccess(BaseModel<LoginBean> o);
    //获取验证码接口
    void onGetValidCodeSuccess(BaseModel<LoginBean> loginBean);
    //验证手机号是否已注册接口
    void isRegistSuccess(BaseModel baseModel);
    //注册接口
    void onRegisterSuccess(BaseModel<LoginBean> baseModel);
    //微信登录接口
    void onWXLoginSuccess(BaseModel<LoginBean> baseModel);
}
