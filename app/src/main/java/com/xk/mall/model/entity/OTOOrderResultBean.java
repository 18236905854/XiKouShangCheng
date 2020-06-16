package com.xk.mall.model.entity;

/**
 * ClassName OTOOrderResultBean
 * Description OTO下单返回Bean
 * Author 卿凯
 * Date 2019/7/18/018
 * Version V1.0
 */
public class OTOOrderResultBean {

    /**
     * deductionCouponAmount : 0
     * orderAmount : 0
     * orderNo :
     * payAmount : 0
     */

    private int deductionCouponAmount;//订单优惠券金额
    private int orderAmount;//订单金额
    private String orderNo;//订单号
    private int payAmount;//实付金额

    public int getDeductionCouponAmount() {
        return deductionCouponAmount;
    }

    public void setDeductionCouponAmount(int deductionCouponAmount) {
        this.deductionCouponAmount = deductionCouponAmount;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }
}
