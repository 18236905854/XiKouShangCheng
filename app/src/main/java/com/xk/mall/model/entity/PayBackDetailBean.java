package com.xk.mall.model.entity;

/**
 * @ClassName: PayBackDetailBean
 * @Description: 退款订单详情
 * @Author: 卿凯
 * @Date: 2019/12/9/009 21:48
 * @Version: 1.0
 */
public class PayBackDetailBean {

    /**
     * orderNo : GB201912092328408885911
     * merchantPhone : 18175713587
     * salesReturnSite : null
     * state : 11
     * addressInfoModel : {"id":"5dee68285fd1c50001ce597b","userId":null,"consigneeName":"啦啦","consigneeMobile":"18245671117","provinceId":330000,"provinceName":"浙江省","cityId":330200,"cityName":"宁波市","areaId":330203,"areaName":"海曙区","address":"哈哈","outRange":false}
     * logisticsCompany : null
     * logisticsNo : null
     * goodsName : 支付测试
     * goodsImageUrl : https://oss-shop-test.luluxk.com/goods/20191114/81962259990e43cb8f2eccf2fe89dc66.jpg
     * commoditySpec : 1
     * commodityModel : 1
     * commodityUnit : 个
     * commodityQuantity : 1
     * deductionCouponAmount : 2
     * payAmount : 1
     * postage : 0
     * commoditySalePrice : 2
     * discountPrice : 1
     * refundMsg : 555
     * refundAmount : 1
     * refundOrderTime : 2019-12-10 10:23:28
     * rejectionTime : null
     * refundOrderNo : 5def01a0f4ff4c0001c59a63
     * refundStatus : 0
     * successTime : null
     */

    private String orderNo;
    private String merchantPhone;
    private String merchantName;//商家名称
    private String salesReturnSite;
    private int state;
    private AddressBean addressInfoModel;
    private String logisticsCompany;
    private String logisticsNo;
    private String goodsName;
    private String goodsImageUrl;
    private String commoditySpec;
    private String commodityModel;
    private String commodityUnit;
    private int commodityQuantity;
    private int deductionCouponAmount;
    private int payAmount;
    private int postage;
    private int commoditySalePrice;
    private int discountPrice;
    private String refundMsg;
    private int refundAmount;
    private String refundOrderTime;
    private String rejectionTime;
    private String refundOrderNo;
    private int refundStatus;//退货状态，0=审核中，1=退款中，2=退款退货中，3=已退款，4=退款失败，5=退款已关闭
    private String successTime;
    private String checkExplain;//驳回原因
    private String remarks;//订单备注

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCheckExplain() {
        return checkExplain;
    }

    public void setCheckExplain(String checkExplain) {
        this.checkExplain = checkExplain;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getSalesReturnSite() {
        return salesReturnSite;
    }

    public void setSalesReturnSite(String salesReturnSite) {
        this.salesReturnSite = salesReturnSite;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public AddressBean getAddressInfoModel() {
        return addressInfoModel;
    }

    public void setAddressInfoModel(AddressBean addressInfoModel) {
        this.addressInfoModel = addressInfoModel;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public String getCommoditySpec() {
        return commoditySpec;
    }

    public void setCommoditySpec(String commoditySpec) {
        this.commoditySpec = commoditySpec;
    }

    public String getCommodityModel() {
        return commodityModel;
    }

    public void setCommodityModel(String commodityModel) {
        this.commodityModel = commodityModel;
    }

    public String getCommodityUnit() {
        return commodityUnit;
    }

    public void setCommodityUnit(String commodityUnit) {
        this.commodityUnit = commodityUnit;
    }

    public int getCommodityQuantity() {
        return commodityQuantity;
    }

    public void setCommodityQuantity(int commodityQuantity) {
        this.commodityQuantity = commodityQuantity;
    }

    public int getDeductionCouponAmount() {
        return deductionCouponAmount;
    }

    public void setDeductionCouponAmount(int deductionCouponAmount) {
        this.deductionCouponAmount = deductionCouponAmount;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public int getPostage() {
        return postage;
    }

    public void setPostage(int postage) {
        this.postage = postage;
    }

    public int getCommoditySalePrice() {
        return commoditySalePrice;
    }

    public void setCommoditySalePrice(int commoditySalePrice) {
        this.commoditySalePrice = commoditySalePrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getRefundMsg() {
        return refundMsg;
    }

    public void setRefundMsg(String refundMsg) {
        this.refundMsg = refundMsg;
    }

    public int getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(int refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundOrderTime() {
        return refundOrderTime;
    }

    public void setRefundOrderTime(String refundOrderTime) {
        this.refundOrderTime = refundOrderTime;
    }

    public String getRejectionTime() {
        return rejectionTime;
    }

    public void setRejectionTime(String rejectionTime) {
        this.rejectionTime = rejectionTime;
    }

    public String getRefundOrderNo() {
        return refundOrderNo;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }

}
