package com.xk.mall.model.request;

/**
 * ClassName UnbindRequestBody
 * Description 用户解绑第三方账号的请求体
 * Author 卿凯
 * Date 2019/7/2/002
 * Version V1.0
 */
public class UnbindRequestBody {
    public int loginType = 0;//第三方类型：1：微信
    public String userId = "";//用户ID

    public UnbindRequestBody(int loginType, String userId) {
        this.loginType = loginType;
        this.userId = userId;
    }
}
