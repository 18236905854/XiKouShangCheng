package com.xk.mall.model.request;

/**
 * ClassName WXLoginRequestBody
 * Description 微信登录请求体
 * Author 卿凯
 * Date 2019/6/19/019
 * Version V1.0
 */
public class WXLoginRequestBody {
    public String appid = "";//微信登录的key
    public String code = "";//微信授权返回的code

    public WXLoginRequestBody(String appid, String code) {
        this.appid = appid;
        this.code = code;
    }
}
