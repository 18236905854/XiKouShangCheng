package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * ClassName LoginBean
 * Description 登录Bean
 * Author 卿凯
 * Date 2019/6/14/014
 * Version V1.0
 */
public class LoginBean implements Serializable {
    public String token = "";//服务端返回的token
    public String refreshToken = "";//服务端返回的refreshToken
    public String userId = "";//服务端返回的用户ID
}
