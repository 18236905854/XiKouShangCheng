package com.xk.mall.model.entity;

/**
 * ClassName ManyBuyOrderDetailBean
 * Description 多买多折订单详情页面Bean
 * Author 卿凯
 * Date 2019/7/20/020
 * Version V1.0
 */
public class ManyBuyOrderDetailBean {

    /**
     * orderNo : DI20190720135600000856
     * externalPlatformNo : null
     * payAmount : 141100
     * postage : 0
     * orderTime : 2019-07-20 13:56:31
     * payTime : null
     * shipTime : null
     * confirmReceiptTime : null
     * state : 1
     * commoditySalePrice : 15900
     * goodsVo : {"goodsId":"5d31717caf8f960001218014","goodsCode":null,"goodsName":"酷尔纳斯主持人晚装宴会小礼 服女2019","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/485c12c61b824331a17e1ff5e73134da.png","commodityId":"5d31717caf8f96000121801a","commoditySpec":"均码","commodityModel":"黑色","commodityQuantity":1,"postage":null}
     * address : {"id":"5d201139790385000187533b","userId":"5d1d58eeaae31800016caedd","consigneeName":"卿凯","consigneeMobile":"13066979652","provinceId":430000,"provinceName":"湖南省","cityId":430100,"cityName":"长沙市","areaId":430104,"areaName":"岳麓区","address":"梅溪青秀二期"}
     */

    private String tradeNo;//主订单号
    private String orderNo;//订单号
    private String externalPlatformNo;//外部平台号
    private String merchantName;//商家名称
    private int payAmount;//实付金额
    private int postage;//邮费
    private String orderTime;//下单时间
    private String payTime;//支付时间
    private String shipTime;//发货时间
    private String confirmReceiptTime;//确定收货时间
    private int payInvalidTime;//实付失效时长
    private int state;//订单状态
    private int commoditySalePrice;//销售价
    private GoodsVoBean goodsVo;
    private AddressBean address;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
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

    public static class GoodsVoBean {
        /**
         * goodsId : 5d31717caf8f960001218014
         * goodsCode : null
         * goodsName : 酷尔纳斯主持人晚装宴会小礼 服女2019
         * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/485c12c61b824331a17e1ff5e73134da.png
         * commodityId : 5d31717caf8f96000121801a
         * commoditySpec : 均码
         * commodityModel : 黑色
         * commodityQuantity : 1
         * postage : null
         */

        private String goodsId;
        private Object goodsCode;
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

        public Object getGoodsCode() {
            return goodsCode;
        }

        public void setGoodsCode(Object goodsCode) {
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
