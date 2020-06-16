package com.xk.mall.model.request;

import java.io.Serializable;

/**
 * 定制拼团 下单请求体
 */
public class GroupOrderRequestBody implements Serializable {

    /**
     * activityGoodsId :
     * activityId :
     * buyerId :
     * commodityId :
     * commodityModel :
     * commodityQuantity : 0
     * commoditySpec :
     * fightGroupNumber : 0
     * goodsCode :
     * goodsId :
     * goodsImageUrl :
     * goodsName :
     * merchantId :
     * orderAmount : 0
     * orderSource : 0
     * receiptAddressRef :
     */
    private int fightGroupNumber;//
    private String activityId;
    private String buyerId;
    private String commodityId;//商品skuid
    private String commodityModel;//商品型号
    private int commodityQuantity;//购买数量
    private String commoditySpec;//商品规格
    private String activityGoodsId;//活动商品id
    private String goodsCode;//商品货号
    private String goodsId;//商品ID
    private String goodsImageUrl;//商品主图Url
    private String goodsName;//商品名称
    private String merchantId;//商家ID
    private int orderAmount;//订单金额
    private int orderSource;//订单来源;1: APP 2:公众号 3: 小程序 4：H5
    private String receiptAddressRef;

    public String getActivityGoodsId() {
        return activityGoodsId;
    }

    public void setActivityGoodsId(String activityGoodsId) {
        this.activityGoodsId = activityGoodsId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
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

    public int getCommodityQuantity() {
        return commodityQuantity;
    }

    public void setCommodityQuantity(int commodityQuantity) {
        this.commodityQuantity = commodityQuantity;
    }

    public String getCommoditySpec() {
        return commoditySpec;
    }

    public void setCommoditySpec(String commoditySpec) {
        this.commoditySpec = commoditySpec;
    }

    public int getFightGroupNumber() {
        return fightGroupNumber;
    }

    public void setFightGroupNumber(int fightGroupNumber) {
        this.fightGroupNumber = fightGroupNumber;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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

    public String getReceiptAddressRef() {
        return receiptAddressRef;
    }

    public void setReceiptAddressRef(String receiptAddressRef) {
        this.receiptAddressRef = receiptAddressRef;
    }
}
