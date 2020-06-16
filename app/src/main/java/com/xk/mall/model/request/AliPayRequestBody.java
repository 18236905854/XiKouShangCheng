package com.xk.mall.model.request;

/**
 * ClassName AliPayRequestBody
 * Description 支付宝支付请求体
 * Author 卿凯
 * Date 2019/7/24/024
 * Version V1.0
 */
public class AliPayRequestBody {

    /**
     * openId :
     * orderNo : GB20190724110000000015
     * orderType : 5
     * paymentAmount : 0
     * timestamp : 0
     * userId : 5d1d58eeaae31800016caedd
     */

    private String openId = "";
    private String orderNo = "";//订单号
    private int orderType = -1;//订单类型
    private int paymentAmount = 0;//订单金额
    private int timestamp;//时间戳
    private String userId = "";//用户ID

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
