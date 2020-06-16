package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * ClassName OrderBean
 * Description 所有商品的Bean
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class OrderBean implements Serializable {

    /**
     * id : 5d298a7162bf930001a749ad
     * orderNo : GB20190713153800000227
     * merchantName : lzd
     * payAmount : 18001
     * state : 1
     * goodsName : 休闲短裤-男
     * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/383e9f36502c4a768c95be4e47621721.jpg
     * commoditySpec : 1
     * commodityModel : L
     * commodityQuantity : 1
     */

    private String id;//订单ID
    private String orderNo;//订单号
    private String merchantName;//商家名称
    private int payAmount;//应付金额
    private int state;//订单状态
    private String goodsName;//商品名称
    private String goodsImageUrl;//商品图片
    private String commoditySpec;//商品规格
    private String commodityModel;//商品型号
    private int commodityQuantity;//购买数量
    private int commoditySalePrice;//销售价
    private int postage;//邮费
    private String waitConfirmTime;//剩余确认时间
    private int deductionCouponAmount;//优惠券可抵扣金额(全球买手使用字段)
    private int couponAmount;//赠券金额(吾G使用字段)
    private int paid;//是否可以找人代付 0：否 1 ：是
    private String partnerId;//上级合伙人ID
    private String payTime;//订单支付时间
    private int discountPrice;//折扣价
    private int timeToBeConfirmed;//剩余确认时间 单位天
    private int alwaysSelectMg;//0：不必须选中，1：必须选中
    private int consignmentType;//寄卖方式(1：定向寄卖 2：寄卖到吾G NULL或者3：所有寄卖方式)
    private int whetherToAllow;//全球买手寄卖能否选择寄卖(1：可以寄卖 2：不可寄卖)
    private int isDirect;//全球买手寄卖能否选择定向(1：可定向 2：不可定向)
    private int refundAmount;//退款金额
    private String refundOrderNo;//退款订单号
    private int refundStatus;//退货状态，0=审核中，1=退款中，2=退款退货中，3=已退款，4=退款失败，5=退款已关闭

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundOrderNo() {
        return refundOrderNo;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }

    public int getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(int refundAmount) {
        this.refundAmount = refundAmount;
    }

    public int getIsDirect() {
        return isDirect;
    }

    public void setIsDirect(int isDirect) {
        this.isDirect = isDirect;
    }

    public int getWhetherToAllow() {
        return whetherToAllow;
    }

    public void setWhetherToAllow(int whetherToAllow) {
        this.whetherToAllow = whetherToAllow;
    }

    public int getConsignmentType() {
        return consignmentType;
    }

    public void setConsignmentType(int consignmentType) {
        this.consignmentType = consignmentType;
    }

    public int getAlwaysSelectMg() {
        return alwaysSelectMg;
    }

    public void setAlwaysSelectMg(int alwaysSelectMg) {
        this.alwaysSelectMg = alwaysSelectMg;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getTimeToBeConfirmed() {
        return timeToBeConfirmed;
    }

    public void setTimeToBeConfirmed(int timeToBeConfirmed) {
        this.timeToBeConfirmed = timeToBeConfirmed;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getDeductionCouponAmount() {
        return deductionCouponAmount;
    }

    public void setDeductionCouponAmount(int deductionCouponAmount) {
        this.deductionCouponAmount = deductionCouponAmount;
    }

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getWaitConfirmTime() {
        return waitConfirmTime;
    }

    public void setWaitConfirmTime(String waitConfirmTime) {
        this.waitConfirmTime = waitConfirmTime;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public int getCommodityQuantity() {
        return commodityQuantity;
    }

    public void setCommodityQuantity(int commodityQuantity) {
        this.commodityQuantity = commodityQuantity;
    }
}
