package com.xk.mall.model.request;

/**
 * 多买多折添加购物车请求体
 */
public class ManyAddCartBody {
    /**
     * activityId :
     * activityPrice : 0
     * buyNumber : 0
     * buyerUserId :
     * commodityId :
     * commodityModel :
     * commodityName :
     * commoditySpec :
     * commodityUnit :
     * discount : 0
     * goodsImageUrl :
     * merchantId :
     * merchantName :
     * salePrice : 0
     */

    private String activityGoodsId;
    private String activityId;
    private double activityPrice;
    private int buyerNumber;
    private String buyerUserId;
    private String commodityId;
    private String commodityModel;
    private String commodityName;
    private String commoditySpec;
    private String commodityUnit;
    private int discount;
    private String goodsImageUrl;
    private String merchantId;
    private String merchantName;
    private double salePrice;
    private String goodsId;//商品id

    public String getActivityGoodsId() {
        return activityGoodsId;
    }

    public void setActivityGoodsId(String activityGoodsId) {
        this.activityGoodsId = activityGoodsId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public double getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(double activityPrice) {
        this.activityPrice = activityPrice;
    }

    public int getBuyerNumber() {
        return buyerNumber;
    }

    public void setBuyerNumber(int buyerNumber) {
        this.buyerNumber = buyerNumber;
    }

    public String getBuyerUserId() {
        return buyerUserId;
    }

    public void setBuyerUserId(String buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityModel() {
        return commodityModel;
    }

    public void setCommodityModel(String commodityModel) {
        this.commodityModel = commodityModel;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommoditySpec() {
        return commoditySpec;
    }

    public void setCommoditySpec(String commoditySpec) {
        this.commoditySpec = commoditySpec;
    }

    public String getCommodityUnit() {
        return commodityUnit;
    }

    public void setCommodityUnit(String commodityUnit) {
        this.commodityUnit = commodityUnit;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
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

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }
}
