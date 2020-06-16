package com.xk.mall.model.entity;

/**
 * 0 元拍订单详情 bean
 */
public class CutOrderDetailBean {
    /**
     * orderNo : AU20190718163400000458
     * externalPlatformNo : null
     * payAmount : 0
     * postage : 0
     * orderTime : 2019-07-18 16:34:03
     * payTime : null
     * shipTime : null
     * confirmReceiptTime : null
     * state : 4
     * commoditySalePrice : 45000
     * goodsVo : {"goodsId":"5d2fd6e96a7cdf0001dafe71","goodsCode":"ANTA00002","goodsName":"安踏漫威联名鞋美国队长复联marvel ","goodsImageUrl":"goods/20190718/e32d241154184a3cbcfcaed2db8a3cdc.jpg","commodityId":"5d2fd6e96a7cdf0001dafe7b","commoditySpec":"40","commodityModel":"美国队长","commodityQuantity":1,"postage":null}
     * address : {"id":"5d302cde88721c0001d8dc73","userId":"5d1abda2f7748700013e5ac4","consigneeName":"本默默看","consigneeMobile":"13625457813","provinceId":430000,"provinceName":"湖南省","cityId":430100,"cityName":"长沙市","areaId":430104,"areaName":"岳麓区","address":"333号"}
     * commodityAuctionPrice : null
     * payInvalidTime  30
     */

    private String orderNo;
    private String externalPlatformNo;
    private int payAmount;
    private int postage;
    private String orderTime;
    private String payTime;
    private String shipTime;
    private String confirmReceiptTime;
    private int state;
    private String bargainScheduleId;//砍价进度ID,有值表示砍价的订单
    private int commoditySalePrice;
    private GoodsVoBean goodsVo;
    private AddressBean address;
    private String commodityAuctionPrice;
    private int cutPrice;//砍后价
    private int payInvalidTime;//下单后支付时间（分钟）
    private String merchantName;//商家名称
    private String remarks;//订单备注
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

    public String getBargainScheduleId() {
        return bargainScheduleId;
    }

    public void setBargainScheduleId(String bargainScheduleId) {
        this.bargainScheduleId = bargainScheduleId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public int getPayInvalidTime() {
        return payInvalidTime;
    }

    public void setPayInvalidTime(int payInvalidTime) {
        this.payInvalidTime = payInvalidTime;
    }

    public int getCutPrice() {
        return cutPrice;
    }

    public void setCutPrice(int cutPrice) {
        this.cutPrice = cutPrice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getExternalPlatformNo() {
        return externalPlatformNo;
    }

    public void setExternalPlatformNo(String externalPlatformNo) {
        this.externalPlatformNo = externalPlatformNo;
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

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public String getConfirmReceiptTime() {
        return confirmReceiptTime;
    }

    public void setConfirmReceiptTime(String confirmReceiptTime) {
        this.confirmReceiptTime = confirmReceiptTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCommoditySalePrice() {
        return commoditySalePrice;
    }

    public void setCommoditySalePrice(int commoditySalePrice) {
        this.commoditySalePrice = commoditySalePrice;
    }

    public GoodsVoBean getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVoBean goodsVo) {
        this.goodsVo = goodsVo;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getCommodityAuctionPrice() {
        return commodityAuctionPrice;
    }

    public void setCommodityAuctionPrice(String commodityAuctionPrice) {
        this.commodityAuctionPrice = commodityAuctionPrice;
    }

    public static class GoodsVoBean {
        /**
         * goodsId : 5d2fd6e96a7cdf0001dafe71
         * goodsCode : ANTA00002
         * goodsName : 安踏漫威联名鞋美国队长复联marvel
         * goodsImageUrl : goods/20190718/e32d241154184a3cbcfcaed2db8a3cdc.jpg
         * commodityId : 5d2fd6e96a7cdf0001dafe7b
         * commoditySpec : 40
         * commodityModel : 美国队长
         * commodityQuantity : 1
         * postage : null
         */

        private String goodsId;
        private String goodsCode;
        private String goodsName;
        private String goodsImageUrl;
        private String commodityId;
        private String commoditySpec;
        private String commodityModel;
        private int commodityQuantity;
        private Object postage;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsCode() {
            return goodsCode;
        }

        public void setGoodsCode(String goodsCode) {
            this.goodsCode = goodsCode;
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

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
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

        public Object getPostage() {
            return postage;
        }

        public void setPostage(Object postage) {
            this.postage = postage;
        }
    }

}
