package com.xk.mall.model.entity;

/**
 * ClassName RedPayRequestBean
 * Description 红包支付请求的Bean
 * Author 卿凯
 * Date 2019/8/6/006
 * Version V1.0
 */
public class RedPayRequestBean {

    /**
     * orderNo :
     * orderType : 0
     * payPassword :
     * paymentAmount : 0
     * userId :
     */

    private String orderNo;
    private int orderType;
    private String payPassword;
    private int paymentAmount;
    private String userId;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
