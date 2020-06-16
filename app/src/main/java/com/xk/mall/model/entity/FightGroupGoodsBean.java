package com.xk.mall.model.entity;

import java.util.List;

/**
 * 定制拼团商品详情 bean
 */
public class FightGroupGoodsBean {
    /**
     * activityType : 6
     * baseRuleModel : {"flag":true,"postage":0,"deliveryDuration":24,"targetNumber":2,"currentFightGroupNum":null}
     * activityCommodityAndSkuModel : {"id":"5d31a189240ed50001eba710","activityId":"5d301dbeed5fc40001c607c3","merchantId":"5d2ed43b7b6c2e00016d2d0d","merchantName":"18075713587","goodsId":"5d317b8daf8f96000121804a","goodCode":null,"commodityName":"法国GUGii手提包2019新款老佛爷同款","categoryName":"","commodityPrice":39000,"salePrice":69900,"stock":100,"virtualStock":100,"imageList":[{"url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/7b1be91891274ad195b7d9e89a921e39.png","type":1,"rankNum":1},{"url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/7fdb3cf860ea4e5d9ba45fc671fa0c1e.png","type":2,"rankNum":1},{"url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/c95eb53e6be6461bb22c9317c8e95afd.png","type":1,"rankNum":2},{"url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/e22c9308a32346b8a2c592080bdadcf3.png","type":2,"rankNum":2},{"url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/08609aa0c8be4360b085b92c9504da0e.png","type":1,"rankNum":3}],"skuList":null,"recordList":null,"startTime":"2019-07-18 00:00:00","endTime":"2019-08-31 00:00:00","currentFightGroupNum":null}
     */

    private int activityType;
    private BaseRuleModelBean baseRuleModel;
    private ActivityCommodityAndSkuModelBean activityCommodityAndSkuModel;

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public BaseRuleModelBean getBaseRuleModel() {
        return baseRuleModel;
    }

    public void setBaseRuleModel(BaseRuleModelBean baseRuleModel) {
        this.baseRuleModel = baseRuleModel;
    }

    public ActivityCommodityAndSkuModelBean getActivityCommodityAndSkuModel() {
        return activityCommodityAndSkuModel;
    }

    public void setActivityCommodityAndSkuModel(ActivityCommodityAndSkuModelBean activityCommodityAndSkuModel) {
        this.activityCommodityAndSkuModel = activityCommodityAndSkuModel;
    }

    public static class BaseRuleModelBean {
        /**
         * flag : true
         * postage : 0
         * deliveryDuration : 24
         * targetNumber : 2
         * currentFightGroupNum : null
         */

        private boolean flag;//是否包邮
        private int postage;//邮费
        private int deliveryDuration;//下单后发货时长
        private int targetNumber;
        private int currentFightGroupNum;

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public int getPostage() {
            return postage;
        }

        public void setPostage(int postage) {
            this.postage = postage;
        }

        public int getDeliveryDuration() {
            return deliveryDuration;
        }

        public void setDeliveryDuration(int deliveryDuration) {
            this.deliveryDuration = deliveryDuration;
        }

        public int getTargetNumber() {
            return targetNumber;
        }

        public void setTargetNumber(int targetNumber) {
            this.targetNumber = targetNumber;
        }

        public int getCurrentFightGroupNum() {
            return currentFightGroupNum;
        }

        public void setCurrentFightGroupNum(int currentFightGroupNum) {
            this.currentFightGroupNum = currentFightGroupNum;
        }
    }

    public static class ActivityCommodityAndSkuModelBean {
        /**
         * id : 5d31a189240ed50001eba710
         * activityId : 5d301dbeed5fc40001c607c3
         * merchantId : 5d2ed43b7b6c2e00016d2d0d
         * merchantName : 18075713587
         * goodsId : 5d317b8daf8f96000121804a
         * goodCode : null
         * commodityName : 法国GUGii手提包2019新款老佛爷同款
         * categoryName :
         * commodityPrice : 39000
         * salePrice : 69900
         * stock : 100
         * virtualStock : 100
         * imageList : [{"url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/7b1be91891274ad195b7d9e89a921e39.png","type":1,"rankNum":1},{"url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/7fdb3cf860ea4e5d9ba45fc671fa0c1e.png","type":2,"rankNum":1},{"url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/c95eb53e6be6461bb22c9317c8e95afd.png","type":1,"rankNum":2},{"url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/e22c9308a32346b8a2c592080bdadcf3.png","type":2,"rankNum":2},{"url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/08609aa0c8be4360b085b92c9504da0e.png","type":1,"rankNum":3}]
         * skuList : null
         * recordList : null
         * startTime : 2019-07-18 00:00:00
         * endTime : 2019-08-31 00:00:00
         * currentFightGroupNum : null
         */

        private String id;
        private String activityId;
        private String merchantId;
        private String merchantName;
        private String goodsId;
        private String goodCode;
        private String commodityName;
        private String categoryName;
        private int commodityPrice;//拼团活动价
        private int salePrice;
        private int stock;//活动商品库存
        private int virtualStock;//虚拟库存
        private List<GoodsSkuListBean2> skuList;
        private List<GlobalBuyerGoodsDetailBean.ActivityCommodityVoBean.RecordListBean> recordList;
        private String startTime;
        private String endTime;
        private int currentFightGroupNum;
        private List<ImageListBean> imageList;

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

        public String getGoodCode() {
            return goodCode;
        }

        public void setGoodCode(String goodCode) {
            this.goodCode = goodCode;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getCommodityPrice() {
            return commodityPrice;
        }

        public void setCommodityPrice(int commodityPrice) {
            this.commodityPrice = commodityPrice;
        }

        public int getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(int salePrice) {
            this.salePrice = salePrice;
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

        public List<GoodsSkuListBean2> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<GoodsSkuListBean2> skuList) {
            this.skuList = skuList;
        }

        public List<GlobalBuyerGoodsDetailBean.ActivityCommodityVoBean.RecordListBean> getRecordList() {
            return recordList;
        }

        public void setRecordList(List<GlobalBuyerGoodsDetailBean.ActivityCommodityVoBean.RecordListBean> recordList) {
            this.recordList = recordList;
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

        public int getCurrentFightGroupNum() {
            return currentFightGroupNum;
        }

        public void setCurrentFightGroupNum(int currentFightGroupNum) {
            this.currentFightGroupNum = currentFightGroupNum;
        }

        public List<ImageListBean> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageListBean> imageList) {
            this.imageList = imageList;
        }

        public static class ImageListBean {
            /**
             * url : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/7b1be91891274ad195b7d9e89a921e39.png
             * type : 1
             * rankNum : 1
             */

            private String url;
            private int type;
            private int rankNum;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getRankNum() {
                return rankNum;
            }

            public void setRankNum(int rankNum) {
                this.rankNum = rankNum;
            }
        }
    }
}
