package com.xk.mall.model.entity;

/**
 * ClassName WuGOrderResultBean
 * Description 吾G下单页面返回数据Bean
 * Author 卿凯
 * Date 2019/7/15/015
 * Version V1.0
 */
public class WuGOrderResultBean {

    /**
     * address : {"address":"","areaId":0,"areaName":"","cityId":0,"cityName":"","consigneeMobile":"","consigneeName":"","id":"","provinceId":0,"provinceName":"","userId":""}
     * goods : {"commodityId":"","commodityModel":"","commodityQuantity":0,"commoditySpec":"","goodsCode":"","goodsId":"","goodsImageUrl":"","goodsName":"","postage":0}
     * orderNo :
     * orderTime :
     * payAmount : 0
     * postage : 0
     */

    private AddressBean address;
    private GoodsBean goods;
    private String orderNo;
    private String orderTime;
    private int payAmount;
    private int postage;
    private String insteadPaymentAccount;//代付人账号;代付订单时显示此字段
    private String insteadPaymentId;//代付人ID;代付订单时显示此字段

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
