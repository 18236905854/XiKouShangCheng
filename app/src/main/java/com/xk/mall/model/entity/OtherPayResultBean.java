package com.xk.mall.model.entity;

/**
 * @ClassName: OtherPayResultBean
 * @Description: 代付订单返回对象
 * @Author: 卿凯
 * @Date: 2019/8/30/030 16:42
 * @Version: 1.0
 */
public class OtherPayResultBean {

    /**
     * goodsName :
     * orderNo :
     * partnerId :
     * payAmount : 0
     * userAccount :
     */

    private String goodsName;
    private String orderNo;
    private String partnerId;
    private int payAmount;
    private String userAccount;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
