package com.xk.mall.model.entity;

/**
 * ClassName ManyBuyRateBean
 * Description 多买多折活动商品费率Bean
 * Author 卿凯
 * Date 2019/7/16/016
 * Version V1.0
 */
public class ManyBuyRateBean {

    /**
     * dividedRatio : 0
     * maxLimit : 0
     * minLimit : 0
     * outDiscountValuationWay : 0
     * rateOne : 0
     * rateOne : 0
     * rateTwo : 0
     */

    private int dividedRatio;//推荐用户参与购买分润比例(百分比)
    private int maxLimit;//最大购买件数
    private int minLimit;//最小购买件数
    private int outDiscountValuationWay;//超出折扣件数计价方式(1：按最后一件折扣计价 2：按商品销售价计价 ）
    private float rateOne;//费率1
    private float rateThree;//费率2
    private float rateTwo;//费率3

    public int getDividedRatio() {
        return dividedRatio;
    }

    public void setDividedRatio(int dividedRatio) {
        this.dividedRatio = dividedRatio;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
    }

    public int getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(int minLimit) {
        this.minLimit = minLimit;
    }

    public int getOutDiscountValuationWay() {
        return outDiscountValuationWay;
    }

    public void setOutDiscountValuationWay(int outDiscountValuationWay) {
        this.outDiscountValuationWay = outDiscountValuationWay;
    }

    public float getRateOne() {
        return rateOne;
    }

    public void setRateOne(float rateOne) {
        this.rateOne = rateOne;
    }

    public float getRateThree() {
        return rateThree;
    }

    public void setRateThree(float rateThree) {
        this.rateThree = rateThree;
    }

    public float getRateTwo() {
        return rateTwo;
    }

    public void setRateTwo(float rateTwo) {
        this.rateTwo = rateTwo;
    }
}
