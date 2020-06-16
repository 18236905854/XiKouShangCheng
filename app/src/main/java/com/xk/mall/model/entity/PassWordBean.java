package com.xk.mall.model.entity;

/**
 * 是否设置支付密码
 */
public class PassWordBean {
    private int id;
    private int accountType;
    private String payPassword;//支付密码
    private String confirmPayPassword;//确认支付密码

    private int status;//状态：1：正常， 2：冻结
}
