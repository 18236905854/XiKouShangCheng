package com.xk.mall.model.request;

import java.io.Serializable;

/**
 * 0 元拍 下单请求体
 */
public class ZeroOrderRequestBody implements Serializable {

    /**
     * activityGoodsId :
     * activityId :
     * buyerId :
     * commodityAuctionPrice : 0
     * commodityId :
     * commodityModel :
     * commodityQuantity : 0
     * commoditySpec :
     * goodsCode :
     * goodsId :
     * goodsImageUrl :
     * goodsName :
     * merchantId :
     * orderAmount : 0
     * orderSource : 0
     * receiptAddressRef :
     */
    private String activityGoodsId;//活动商品ID
    private String activityId;
    private String buyerId;
    private int commodityAuctionPrice;//竞拍价;拍中者最后拍出的价格
    private String commodityId;
    private String commodityModel;
    private int commodityQuantity;
    private String commoditySpec;
    private String goodsCode;
    private String goodsId;
    private String goodsImageUrl;
    private String goodsName;
    private String merchantId;
    private int orderAmount;//订单金额
    private int orderSource;
    private String receiptAddressRef;//收货人id
    private String remarks;//备注

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

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

    public int getCommodityAuctionPrice() {
        return commodityAuctionPrice;
    }

    public void setCommodityAuctionPrice(int commodityAuctionPrice) {
        this.commodityAuctionPrice = commodityAuctionPrice;
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
