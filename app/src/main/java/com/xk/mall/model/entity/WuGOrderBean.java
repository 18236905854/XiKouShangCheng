package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * ClassName WuGOrderBean
 * Description 吾G商品下单需要的Bean
 * Author 卿凯
 * Date 2019/7/15/015
 * Version V1.0
 */
public class WuGOrderBean implements Serializable {

    /**
     * activityGoodsId :
     * activityId :
     * buyerId :
     * commodityId :
     * commodityModel :
     * commodityQuantity : 0
     * commoditySpec :
     * consignmentId :
     * goodsCode :
     * goodsId :
     * goodsImageUrl :
     * goodsName :
     * merchantId :
     * orderAmount : 0
     * orderSource : 0
     * originalOrderNo :
     * receiptAddressRef :
     * insteadPay: 是否代付:0-否;1-是;
     * remarsk:订单备注
     */

    private String activityGoodsId;//活动商品ID
    private String activityId;//活动ID
    private String buyerId;//买家ID
    private String commodityId;//商品SKU ID
    private String commodityModel;//商品型号
    private int commodityQuantity;//购买数量
    private int unitPrice;//商品单价
    private String commoditySpec;//商品规格
    private String consignmentId;//寄卖用户ID
    private String goodsCode;//商品货号
    private String goodsId;//商品ID
    private String goodsImageUrl;//商品主图URL
    private String goodsName;//商品名称
    private String merchantId;//商家ID
    private int orderAmount;//订单金额
    private int orderSource;//订单来源;1: APP 2:公众号 3: 小程序 4：H5
    private String originalOrderNo;//寄卖用户原始订单号(全球买手购买商品生成的订单号)
    private String receiptAddressRef;//收货人信息ID
    private int insteadPay;//是否代付 0-否;1-是;
    private String remarks;//备注
    private String payPhone;//代付人手机号

    public String getPayPhone() {
        return payPhone;
    }

    public void setPayPhone(String payPhone) {
        this.payPhone = payPhone;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getInsteadPay() {
        return insteadPay;
    }

    public void setInsteadPay(int insteadPay) {
        this.insteadPay = insteadPay;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
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

    public String getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId;
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

    public String getOriginalOrderNo() {
        return originalOrderNo;
    }

    public void setOriginalOrderNo(String originalOrderNo) {
        this.originalOrderNo = originalOrderNo;
    }

    public String getReceiptAddressRef() {
        return receiptAddressRef;
    }

    public void setReceiptAddressRef(String receiptAddressRef) {
        this.receiptAddressRef = receiptAddressRef;
    }
}
