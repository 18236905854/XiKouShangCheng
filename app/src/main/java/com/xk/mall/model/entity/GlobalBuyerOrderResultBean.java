package com.xk.mall.model.entity;

/**
 * ClassName GlobalBuyerOrderResultBean
 * Description 全球买手下单返回Bean
 * Author 卿凯
 * Date 2019/7/13/013
 * Version V1.0
 */
public class GlobalBuyerOrderResultBean {

    /**
     * address : {"address":"","areaId":0,"areaName":"","cityId":0,"cityName":"","consigneeMobile":"","consigneeName":"","id":"","provinceId":0,"provinceName":"","userId":""}
     * deductionCouponAmount : 0
     * goods : {"commodityId":"","commodityModel":"","commodityQuantity":0,"commoditySpec":"","goodsCode":"","goodsId":"","goodsImageUrl":"","goodsName":"","postage":0}
     * orderNo :
     * orderTime :
     * payAmount : 0
     * postage : 0
     */

    private AddressBean address;//地址对象
    private int deductionCouponAmount;//抵扣优惠券总金额
    private GoodsBean goods;//商品对象
    private String orderNo;//订单号
    private String orderTime;//下单时间
    private String insteadPaymentAccount;//代付人账号;代付订单时显示此字段
    private String insteadPaymentId;//代付人ID;代付订单时显示此字段
    private int payAmount;//实付金额
    private int postage;//邮费

    public String getInsteadPaymentAccount() {
        return insteadPaymentAccount;
    }

    public void setInsteadPaymentAccount(String insteadPaymentAccount) {
        this.insteadPaymentAccount = insteadPaymentAccount;
    }

    public String getInsteadPaymentId() {
        return insteadPaymentId;
    }

    public void setInsteadPaymentId(String insteadPaymentId) {
        this.insteadPaymentId = insteadPaymentId;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public int getDeductionCouponAmount() {
        return deductionCouponAmount;
    }

    public void setDeductionCouponAmount(int deductionCouponAmount) {
        this.deductionCouponAmount = deductionCouponAmount;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
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

    public int getPostage() {
        return postage;
    }

    public void setPostage(int postage) {
        this.postage = postage;
    }

    public static class GoodsBean {
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

        private String commodityId;
        private String commodityModel;
        private int commodityQuantity;
        private String commoditySpec;
        private String goodsCode;
        private String goodsId;
        private String goodsImageUrl;
        private String goodsName;
        private int postage;

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
