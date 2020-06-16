package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * 热排行商品
 */
public class BaoPinGoodsBean implements Serializable {
    /**
     * activityId :
     * activityModule : 0
     * commodityId :
     * commodityName :
     * commodityPrice : 0
     * costPrice : 0
     * discount : 0
     * goodsId :
     * goodsImageUrl :
     * id : 主键ID(采用分布式ObjectId生成策略 24位长度)
     * marketPrice : 0
     * merchantId :
     * merchantName :
     * salesVolume : 0
     * shareCount : 0
     */

    private String activityId;
    private int activityModule;
    private String commodityId;
    private String commodityName;
    private int commodityPrice;
    private int salePrice;
    private int discount;
    private String goodsId;
    private String goodsImageUrl;
    private String id;
    private int marketPrice;
    private String merchantId;
    private String merchantName;
    private int salesVolume;
    private int shareCount;//转发量
    private int couponAmount;//优惠券
    private int saveMoney;//省钱
    private int bargainNumber;//多少人助力

    private float rateThree;
    private float rateOne;
    private int commodityPriceOne;
    private int commodityPriceTwo;
    private int commodityPriceThree;

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

    public int getCommodityPriceOne() {
        return commodityPriceOne;
    }

    public void setCommodityPriceOne(int commodityPriceOne) {
        this.commodityPriceOne = commodityPriceOne;
    }

    public int getCommodityPriceTwo() {
        return commodityPriceTwo;
    }

    public void setCommodityPriceTwo(int commodityPriceTwo) {
        this.commodityPriceTwo = commodityPriceTwo;
    }

    public int getCommodityPriceThree() {
        return commodityPriceThree;
    }

    public void setCommodityPriceThree(int commodityPriceThree) {
        this.commodityPriceThree = commodityPriceThree;
    }

    public int getBargainNumber() {
        return bargainNumber;
    }

    public void setBargainNumber(int bargainNumber) {
        this.bargainNumber = bargainNumber;
    }

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getSaveMoney() {
        return saveMoney;
    }

    public void setSaveMoney(int saveMoney) {
        this.saveMoney = saveMoney;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getActivityModule() {
        return activityModule;
    }

    public void setActivityModule(int activityModule) {
        this.activityModule = activityModule;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public int getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(int commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }
}
