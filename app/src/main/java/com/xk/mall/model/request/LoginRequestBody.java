package com.xk.mall.model.request;

/**
 * ClassName LoginRequestBody
 * Description 手机号码验证登录的请求体
 * Author 卿凯
 * Date 2019/6/17/017
 * Version V1.0
 */
public class LoginRequestBody extends ValidCodeRequestBody{

    public String validCode = "";//验证码

    public LoginRequestBody(String mobile, String validCode) {
        super(mobile);
        this.validCode = validCode;
    }
}
