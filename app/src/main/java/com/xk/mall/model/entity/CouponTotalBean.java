package com.xk.mall.model.entity;

/**
 * @ClassName: CouponTotalBean
 * @Description: 优惠券总额对象
 * @Author: 卿凯
 * @Date: 2019/9/29/029 9:16
 * @Version: 1.0
 */
public class CouponTotalBean {

    /**
     * couponSumNum : 7826562
     * couponUsableSumNum : 6710550
     */

    private int couponSumNum;//优惠券总额
    private int couponUsableSumNum;//可用优惠券总额
    private int buyGiftAmount;//通用寄卖额度
    private int directionalAmount;//定向寄卖额度
    private int feeRates = 0;//优惠券费率

    public int getFeeRates() {
        return feeRates;
    }

    public void setFeeRates(int feeRates) {
        this.feeRates = feeRates;
    }

    public int getDirectionalAmount() {
        return directionalAmount;
    }

    public void setDirectionalAmount(int directionalAmount) {
        this.directionalAmount = directionalAmount;
    }

    public int getBuyGiftAmount() {
        return buyGiftAmount;
    }

    public void setBuyGiftAmount(int buyGiftAmount) {
        this.buyGiftAmount = buyGiftAmount;
    }

    public int getCouponSumNum() {
        return couponSumNum;
    }

    public void setCouponSumNum(int couponSumNum) {
        this.couponSumNum = couponSumNum;
    }

    public int getCouponUsableSumNum() {
        return couponUsableSumNum;
    }

    public void setCouponUsableSumNum(int couponUsableSumNum) {
        this.couponUsableSumNum = couponUsableSumNum;
    }
}
