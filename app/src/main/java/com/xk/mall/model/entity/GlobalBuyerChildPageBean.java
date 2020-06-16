package com.xk.mall.model.entity;


import java.util.List;

/**
 * ClassName GlobalBuyerChildPageBean
 * Description 全球买手页面子页面分页数据的Bean
 * Author 卿凯
 * Date 2019/7/7/007
 * Version V1.0
 */
public class GlobalBuyerChildPageBean {
    public List<GlobalBuyerChildGoodsBean> result;
    public int totalCount = 0;//返回的当前页的总数
    public int pageCount = 0;//当前的页数

    public static class GlobalBuyerChildGoodsBean {

        /**
         * id : 5d3fb24472678d000114b0b3
         * activityId : 5d3ee73bf356630001a4080a
         * merchantId : 5d36bbca53009a0001f74517
         * merchantName : lzd
         * goodsId : 5d3bfb13b396bb0001456bd6
         * categoryRef1 : 5d25dd5dda12d50001715817
         * categoryRef2 : 5d25dba2da12d50001715800
         * categoryRef3 : 5d25dc67da12d50001715805
         * categoryName : 休闲短裤
         * goodsCode : 12312312
         * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190727/7956faace920499883fe149dc12f54a4.jpg
         * commodityId : 5d3bfb13b396bb0001456bd9
         * commodityName : 阿斯蒂芬
         * commoditySpec : X
         * commodityModel : L
         * status : 1
         * stock : 10
         * virtualStock : 10
         * salePrice : 2
         * costPrice : 1
         * marketPrice : 10000
         * commodityPrice : 1
         * salesVolume : null
         * startAmount : null
         * discount : 8
         * priority : null
         * deliveryTime : null
         * purchaseLimit : 1
         * online : 1
         * startTime : 2019-07-29 00:00:00
         * endTime : 2019-08-31 00:00:00
         * isDeleted : 2
         * createTime : 2019-07-30 10:58:12
         * updateTime : 2019-07-30 10:58:12
         * couponValue : 0
         */

        private String id;
        private String activityId;
        private String merchantId;
        private String merchantName;
        private String goodsId;
        private String categoryRef1;
        private String categoryRef2;
        private String categoryRef3;
        private String categoryName;
        private String goodsCode;
        private String goodsImageUrl;
        private String commodityId;
        private String commodityName;
        private String commoditySpec;
        private String commodityModel;
        private int status;
        private int stock;
        private int virtualStock;
        private int salePrice;
        private int costPrice;
        private int marketPrice;
        private int commodityPrice;
        private Object salesVolume;
        private Object startAmount;
        private int discount;
        private Object priority;
        private Object deliveryTime;
        private int purchaseLimit;
        private int online;
        private String startTime;
        private String endTime;
        private int isDeleted;
        private String createTime;
        private String updateTime;
        private int couponValue;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getCategoryRef1() {
            return categoryRef1;
        }

        public void setCategoryRef1(String categoryRef1) {
            this.categoryRef1 = categoryRef1;
        }

        public String getCategoryRef2() {
            return categoryRef2;
        }

        public void setCategoryRef2(String categoryRef2) {
            this.categoryRef2 = categoryRef2;
        }

        public String getCategoryRef3() {
            return categoryRef3;
        }

        public void setCategoryRef3(String categoryRef3) {
            this.categoryRef3 = categoryRef3;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getGoodsCode() {
            return goodsCode;
        }

        public void setGoodsCode(String goodsCode) {
            this.goodsCode = goodsCode;
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

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getVirtualStock() {
            return virtualStock;
        }

        public void setVirtualStock(int virtualStock) {
            this.virtualStock = virtualStock;
        }

        public int getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(int salePrice) {
            this.salePrice = salePrice;
        }

        public int getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(int costPrice) {
            this.costPrice = costPrice;
        }

        public int getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(int marketPrice) {
            this.marketPrice = marketPrice;
        }

        public int getCommodityPrice() {
            return commodityPrice;
        }

        public void setCommodityPrice(int commodityPrice) {
            this.commodityPrice = commodityPrice;
        }

        public Object getSalesVolume() {
            return salesVolume;
        }

        public void setSalesVolume(Object salesVolume) {
            this.salesVolume = salesVolume;
        }

        public Object getStartAmount() {
            return startAmount;
        }

        public void setStartAmount(Object startAmount) {
            this.startAmount = startAmount;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public Object getPriority() {
            return priority;
        }

        public void setPriority(Object priority) {
            this.priority = priority;
        }

        public Object getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(Object deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public int getPurchaseLimit() {
            return purchaseLimit;
        }

        public void setPurchaseLimit(int purchaseLimit) {
            this.purchaseLimit = purchaseLimit;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(int isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getCouponValue() {
            return couponValue;
        }

        public void setCouponValue(int couponValue) {
            this.couponValue = couponValue;
        }
    }
}
