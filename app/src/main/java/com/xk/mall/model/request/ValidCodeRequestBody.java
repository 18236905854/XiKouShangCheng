package com.xk.mall.model.request;

/**
 * ClassName ValidCodeRequestBody
 * Description 获取验证码接口的Body
 * Author 卿凯
 * Date 2019/6/17/017
 * Version V1.0
 */
public class ValidCodeRequestBody {

    public String mobile = "";

    public ValidCodeRequestBody(String mobile) {
        this.mobile = mobile;
    }
}
