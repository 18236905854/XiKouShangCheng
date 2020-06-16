package com.xk.mall.model.entity;

/**
 * @ClassName: PayOrderRequestBean
 * @Description: 订单统一支付请求body
 * @Author: 卿凯
 * @Date: 2019/8/21/021 9:37
 * @Version: 1.0
 */
public class PayOrderRequestBean {


    /**
     * clientType :
     * openId :
     * orderNo :
     * orderType : 0
     * osType : 0
     * payPassword :
     * payType : 0
     * payWay : 0
     * paymentAmount : 0
     * timestamp : 0
     * userId :
     */

    private String clientType;
    private String openId;
    private String orderNo;
    private int orderType;
    private int osType;
    private String payPassword;
    private int payType;
    private int payWay;
    private int paymentAmount;
    private int timestamp;
    private String userId;

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

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

    public int getOsType() {
        return osType;
    }

    public void setOsType(int osType) {
        this.osType = osType;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
