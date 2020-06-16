package com.xk.mall.model.entity;

/**
 * ClassName GlobalBuyerOrderDetailBean
 * Description 全球买手订单详情Bean
 * Author 卿凯
 * Date 2019/7/15/015
 * Version V1.0
 */
public class GlobalBuyerOrderDetailBean {

    /**
     * address : {"address":"","areaId":0,"areaName":"","cityId":0,"cityName":"","consigneeMobile":"","consigneeName":"","id":"","provinceId":0,"provinceName":"","userId":""}
     * confirmReceiptTime :
     * externalPlatformNo :
     * goodsVo : {"commodityId":"","commodityModel":"","commodityQuantity":0,"commoditySpec":"","goodsCode":"","goodsId":"","goodsImageUrl":"","goodsName":"","postage":0}
     * orderNo :
     * orderTime :
     * payAmount : 0
     * payTime :
     * postage : 0
     * shipTime :
     * state : 0
     */

    private AddressBean address;
    private String confirmReceiptTime;//确定收货时间
    private String externalPlatformNo;//外部平台号
    private GoodsVoBean goodsVo;//商品vo
    private String orderNo;//订单号
    private int payInvalidTime;//支付失效时长
    private String orderTime;//下单时间
    private int payAmount;//实付金额
    private String payTime;//支付时间
    private int postage;//邮费
    private String shipTime;//发货时间
    private int state;//订单状态(1:待支付 2: 待发货 3: 待收货 4: 已取消 5: 已完成 6: 已关闭 )
    private String merchantName;//商家名称
    private int commoditySalePrice;//销售价
    private int deductionCouponAmount;//优惠券抵扣金额(全球买手)
    private int couponAmount;//赠券金额(吾G)
    private  int discountPrice;//折扣价
    private int paid;//是否可以找人代付 0：否 1 ：是
    private String partnerId;//上级合伙人ID
    private String remarks;//订单备注
    private int alwaysSelectMg;//0：不必须选中，1：必须选中
    private String autoDeliveryTime;//自动收货时间
    private String merchantPhone;//商家电话
    private int sevenDaysNoReasonReturn;//是否支持7天无理由退货  0 : 不支持， 1:支持

    public int getSevenDaysNoReasonReturn() {
        return sevenDaysNoReasonReturn;
    }

    public void setSevenDaysNoReasonReturn(int sevenDaysNoReasonReturn) {
        this.sevenDaysNoReasonReturn = sevenDaysNoReasonReturn;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getAutoDeliveryTime() {
        return autoDeliveryTime;
    }

    public void setAutoDeliveryTime(String autoDeliveryTime) {
        this.autoDeliveryTime = autoDeliveryTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getAlwaysSelectMg() {
        return alwaysSelectMg;
    }

    public void setAlwaysSelectMg(int alwaysSelectMg) {
        this.alwaysSelectMg = alwaysSelectMg;
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

    public int getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(int couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getDeductionCouponAmount() {
        return deductionCouponAmount;
    }

    public void setDeductionCouponAmount(int deductionCouponAmount) {
        this.deductionCouponAmount = deductionCouponAmount;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getCommoditySalePrice() {
        return commoditySalePrice;
    }

    public void setCommoditySalePrice(int commoditySalePrice) {
        this.commoditySalePrice = commoditySalePrice;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getConfirmReceiptTime() {
        return confirmReceiptTime;
    }

    public void setConfirmReceiptTime(String confirmReceiptTime) {
        this.confirmReceiptTime = confirmReceiptTime;
    }

    public int getPayInvalidTime() {
        return payInvalidTime;
    }

    public void setPayInvalidTime(int payInvalidTime) {
        this.payInvalidTime = payInvalidTime;
    }

    public String getExternalPlatformNo() {
        return externalPlatformNo;
    }

    public void setExternalPlatformNo(String externalPlatformNo) {
        this.externalPlatformNo = externalPlatformNo;
    }

    public GoodsVoBean getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVoBean goodsVo) {
        this.goodsVo = goodsVo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public int getPostage() {
        return postage;
    }

    public void setPostage(int postage) {
        this.postage = postage;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static class GoodsVoBean {
        /**
         * commodityId :
         * commodityModel :
         * commodityQuantity : 0
         * commoditySpec :
         * goodsCode :
         * goodsId :
         * goodsImageUrl :
         * goodsName :
         * postage : 0
         */

        private String commodityId;//商品SKU ID
        private String commodityModel;//商品型号
        private int commodityQuantity;//购买数量
        private String commoditySpec;//商品规格
        private String goodsCode;//商品货号
        private String goodsId;//商品ID
        private String goodsImageUrl;//主图Url
        private String goodsName;//商品名称
        private int postage;//邮费
        private int consignmentType;//寄卖方式(1：定向寄卖 2：寄卖到吾G NULL或者3：所有寄卖方式)
        private int whetherToAllow;//全球买手寄卖能否选择寄卖(1：可以寄卖 2：不可寄卖)
        private int isDirect;//全球买手寄卖能否选择定向(1：可定向 2：不可定向)

        public int getIsDirect() {
            return isDirect;
        }

        public void setIsDirect(int isDirect) {
            this.isDirect = isDirect;
        }

        public int getWhetherToAllow() {
            return whetherToAllow;
        }


        public int getConsignmentType() {
            return consignmentType;
        }

        public void setConsignmentType(int consignmentType) {
            this.consignmentType = consignmentType;
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

        public int getPostage() {
            return postage;
        }

        public void setPostage(int postage) {
            this.postage = postage;
        }
    }
}
