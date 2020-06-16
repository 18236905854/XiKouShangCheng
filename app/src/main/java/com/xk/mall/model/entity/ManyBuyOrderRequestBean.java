package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName ManyBuyOrderRequestBean
 * Description 多买多折订单请求Bean
 * Author 卿凯
 * Date 2019/7/16/016
 * Version V1.0
 */
public class ManyBuyOrderRequestBean {

    /**
     * activityId :
     * buyMoreFoldsVoList : [{"activityGoodsId":"","buyNumber":0,"commodityId":"","commodityModel":"","commoditySpec":"","discount":0,"goodsCode":"","goodsId":"","goodsImageUrl":"","goodsName":"","merchantId":"","orderAmount":0,"orderNo":0,"salePrice":0}]
     * buyerId :
     * orderSource : 0
     * receiptAddressRef :
     * totalAmount : 0
     */

    private String activityId;//活动ID
    private String buyerId;//买家ID
    private int orderSource;//订单来源
    private int totalAmount;//订单金额
    private List<BuyMoreFoldsVoListBean> buyMoreFoldsVoList;

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

    public int getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(int orderSource) {
        this.orderSource = orderSource;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<BuyMoreFoldsVoListBean> getBuyMoreFoldsVoList() {
        return buyMoreFoldsVoList;
    }

    public void setBuyMoreFoldsVoList(List<BuyMoreFoldsVoListBean> buyMoreFoldsVoList) {
        this.buyMoreFoldsVoList = buyMoreFoldsVoList;
    }

    public static class BuyMoreFoldsVoListBean {
        /**
         * activityGoodsId :
         * buyNumber : 0
         * commodityId :
         * commodityModel :
         * commoditySpec :
         * discount : 0
         * goodsCode :
         * goodsId :
         * goodsImageUrl :
         * goodsName :
         * merchantId :
         * orderAmount : 0
         * orderNo : 0
         * salePrice : 0
         */

        private String activityGoodsId;//活动商品ID
        private int buyNumber;//购买数量
        private String commodityId;//商品SKU ID
        private String commodityModel;//商品型号
        private String commoditySpec;//商品规格
        private int discount;//折扣
        private String buyerCartId;//购物车ID
        private String goodsCode;//商品货号
        private String goodsId;//商品ID
        private String receiptAddressRef;//收货人地址ID
        private String goodsImageUrl;//商品主图
        private String goodsName;//商品名称
        private String merchantId;//商家ID
        private int orderAmount;//订单金额
        private int orderNo;//购物车商品序号(1: 第一件 2: 第二件 3: 第三件 ...)
        private int salePrice;//原价
        private String remarks;//备注

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getBuyerCartId() {
            return buyerCartId;
        }

        public void setBuyerCartId(String buyerCartId) {
            this.buyerCartId = buyerCartId;
        }

        public String getReceiptAddressRef() {
            return receiptAddressRef;
        }

        public void setReceiptAddressRef(String receiptAddressRef) {
            this.receiptAddressRef = receiptAddressRef;
        }

        public String getActivityGoodsId() {
            return activityGoodsId;
        }

        public void setActivityGoodsId(String activityGoodsId) {
            this.activityGoodsId = activityGoodsId;
        }

        public int getBuyNumber() {
            return buyNumber;
        }

        public void setBuyNumber(int buyNumber) {
            this.buyNumber = buyNumber;
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

        public String getCommoditySpec() {
            return commoditySpec;
        }

        public void setCommoditySpec(String commoditySpec) {
            this.commoditySpec = commoditySpec;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
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

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }

        public int getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(int salePrice) {
            this.salePrice = salePrice;
        }
    }
}
