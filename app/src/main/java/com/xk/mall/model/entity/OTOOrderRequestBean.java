package com.xk.mall.model.entity;

/**
 * ClassName OTOOrderRequestBean
 * Description OTO联盟下单请求Bean
 * Author 卿凯
 * Date 2019/7/18/018
 * Version V1.0
 */
public class OTOOrderRequestBean {

    /**
     * buyerId :
     * deductionCouponAmount : 0
     * discount : 0
     * merchantId :
     * nonOfferAmount : 0
     * orderAmount : 0
     * orderSource : 0
     * payAmount : 0
     */

    private String buyerId;//买家用户ID
    private int deductionCouponAmount;//抵扣优惠券金额
    private int discount;//优惠比例
    private String merchantId;//商家ID
    private int nonOfferAmount;//不参与优惠金额
    private int orderAmount;//订单金额
    private int orderSource;//订单来源
    private int payAmount;//实付金额
    private String shopId;//

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public int getDeductionCouponAmount() {
        return deductionCouponAmount;
    }

    public void setDeductionCouponAmount(int deductionCouponAmount) {
        this.deductionCouponAmount = deductionCouponAmount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getNonOfferAmount() {
        return nonOfferAmount;
    }

    public void setNonOfferAmount(int nonOfferAmount) {
        this.nonOfferAmount = nonOfferAmount;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(int orderSource) {
        this.orderSource = orderSource;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }
}
