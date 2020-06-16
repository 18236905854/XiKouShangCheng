package com.xk.mall.model.request;

/**
 * ClassName CancelOrderRequestBody
 * Description 取消订单请求体
 * Author 卿凯
 * Date 2019/7/13/013
 * Version V1.0
 */
public class CancelOrderRequestBody {

    /**
     * orderNo :
     * orderType : 0
     * payType : 0
     * payerAccount :
     * paymentWay : 0
     */

    private String orderNo;//订单号
    private int orderType;//订单类型;1-0元竞拍订单;2-多买多折订单;3-喜立得订单;4-吾G订单;5-全球买手订单;6-定制拼团订单
    private String payerAccount;//代付人账号;全球买手订单、寄卖订单支付时传入此参数

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

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

}
