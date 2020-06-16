package com.xk.mall.model.entity;

import java.util.List;

/**
 * 喜立得商品详情 bean
 */
public class CutGoodsDetailBean {
    /**
     * activityType : 5
     * baseRuleModel : {"id":"5d301c56ed5fc40001c607b7","ruleName":"喜立得-Test-Min4","activityType":5,"isLimitNumber":1,"limitNumber":0,"isConsignment":2,"consignmentDuration":null,"postage":0,"deliveryDuration":24,"maxRangPrice":0,"minRangPrice":0,"reservePriceRate":0,"actualPriceRate":0,"bargainNumber":8,"bargainFrequency":0,"userBuyLimit":0,"isAllowRepeatBargain":null,"isAllowSelfBargain":1,"bargainOrderEffectiveDuration":3,"redPacketRate":0,"bargainValueWay":1,"redPacketSendRule":1,"redPacketReceiveRule":1,"createTime":"2019-07-18 15:14:31","updateTime":"2019-07-18 15:14:31"}
     * goodsManageModel : {"id":"5d316b4aaf8f960001217fd5","bussId":"5d2ed43b7b6c2e00016d2d0d","url":null,"goodsCode":"1","goodsName":"稻草人女包2019新款时尚百搭可爱","brandId":"5d313c14bbe399000123681c","brandName":null,"marketPrice":null,"costPrice":null,"salePrice":null,"stock":null,"cumulativeSales":null,"goodsType":null,"createTime":"2019-07-19 15:03:38","lastUpdateTime":"2019-07-19 15:04:05","tagId":null,"tagIds":null,"categoryId":null,"introduction":"我是商品描述","keyword":null,"postage":0,"logistics":1,"online":1,"isUsed":1,"deleted":0,"checked":2,"checkReason":null,"categoryId1":"5d313c84bbe399000123681d","categoryId2":"5d3168c8af8f960001217fd4","goodsCount":null,"categoryName":null,"categoryNameLink":"轻奢-品牌","goodsImagesList":[{"id":"5d316b4aaf8f960001217fd6","goodsId":"5d316b4aaf8f960001217fd5","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png","type":1,"rankNum":1,"createTime":"2019-07-19 15:03:38"},{"id":"5d316b4aaf8f960001217fd7","goodsId":"5d316b4aaf8f960001217fd5","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/2a89b7c29045414fbc1ccd737d939646.png","type":2,"rankNum":1,"createTime":"2019-07-19 15:03:38"},{"id":"5d316b4aaf8f960001217fd8","goodsId":"5d316b4aaf8f960001217fd5","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/4eac789221bd458cafbdc1a765061984.png","type":2,"rankNum":2,"createTime":"2019-07-19 15:03:38"}],"goodsSkuList":[{"id":"5d316b4aaf8f960001217fd9","goodsId":"5d316b4aaf8f960001217fd5","goodsUnit":"1","goodsModel":"猫咪","goodsType":"白色","stock":100,"virtualStock":null,"freezeStock":null,"marketPrice":59900,"costPrice":29900,"salePrice":39900,"deleted":0,"status":1,"startNum":1}],"goodsCheckLogList":null,"businessName":"Min4","businessMobile":"18075713587"}
     */

    private int activityType;
    private BaseRuleModelBean baseRuleModel;
    private GoodsServerDetailBean goodsManageModel;

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

    public GoodsServerDetailBean getGoodsManageModel() {
        return goodsManageModel;
    }

    public void setGoodsManageModel(GoodsServerDetailBean goodsManageModel) {
        this.goodsManageModel = goodsManageModel;
    }

    public static class BaseRuleModelBean {
        /**
         * id : 5d301c56ed5fc40001c607b7
         * ruleName : 喜立得-Test-Min4
         * activityType : 5
         * isLimitNumber : 1
         * limitNumber : 0
         * isConsignment : 2
         * consignmentDuration : null
         * postage : 0
         * deliveryDuration : 24
         * maxRangPrice : 0
         * minRangPrice : 0
         * reservePriceRate : 0
         * actualPriceRate : 0
         * bargainNumber : 8
         * bargainFrequency : 0
         * userBuyLimit : 0
         * isAllowRepeatBargain : null
         * isAllowSelfBargain : 1
         * bargainOrderEffectiveDuration : 3
         * redPacketRate : 0
         * bargainValueWay : 1
         * redPacketSendRule : 1
         * redPacketReceiveRule : 1
         * createTime : 2019-07-18 15:14:31
         * updateTime : 2019-07-18 15:14:31
         */

        private String id;
        private String ruleName;
        private int activityType;
        private int isLimitNumber;
        private int limitNumber;// 限购数量
        private int isConsignment;
        private Object consignmentDuration;
        private int postage;
        private int deliveryDuration;//下单后最晚发货时长(小时)
        private int maxRangPrice;
        private int minRangPrice;
        private int reservePriceRate;
        private int actualPriceRate;
        private int bargainNumber;
        private int bargainFrequency;
        private int userBuyLimit;
        private Object isAllowRepeatBargain;
        private int isAllowSelfBargain;//砍价方式(1：只允许分享用户帮忙砍价 2：分享前允许用户给自己砍价 3：分享前允许平台帮忙砍价)
        private int bargainOrderEffectiveDuration;
        private int redPacketRate;
        private int bargainValueWay;
        private int redPacketSendRule;
        private int redPacketReceiveRule;
        private String createTime;
        private String updateTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRuleName() {
            return ruleName;
        }

        public void setRuleName(String ruleName) {
            this.ruleName = ruleName;
        }

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public int getIsLimitNumber() {
            return isLimitNumber;
        }

        public void setIsLimitNumber(int isLimitNumber) {
            this.isLimitNumber = isLimitNumber;
        }

        public int getLimitNumber() {
            return limitNumber;
        }

        public void setLimitNumber(int limitNumber) {
            this.limitNumber = limitNumber;
        }

        public int getIsConsignment() {
            return isConsignment;
        }

        public void setIsConsignment(int isConsignment) {
            this.isConsignment = isConsignment;
        }

        public Object getConsignmentDuration() {
            return consignmentDuration;
        }

        public void setConsignmentDuration(Object consignmentDuration) {
            this.consignmentDuration = consignmentDuration;
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

        public int getMaxRangPrice() {
            return maxRangPrice;
        }

        public void setMaxRangPrice(int maxRangPrice) {
            this.maxRangPrice = maxRangPrice;
        }

        public int getMinRangPrice() {
            return minRangPrice;
        }

        public void setMinRangPrice(int minRangPrice) {
            this.minRangPrice = minRangPrice;
        }

        public int getReservePriceRate() {
            return reservePriceRate;
        }

        public void setReservePriceRate(int reservePriceRate) {
            this.reservePriceRate = reservePriceRate;
        }

        public int getActualPriceRate() {
            return actualPriceRate;
        }

        public void setActualPriceRate(int actualPriceRate) {
            this.actualPriceRate = actualPriceRate;
        }

        public int getBargainNumber() {
            return bargainNumber;
        }

        public void setBargainNumber(int bargainNumber) {
            this.bargainNumber = bargainNumber;
        }

        public int getBargainFrequency() {
            return bargainFrequency;
        }

        public void setBargainFrequency(int bargainFrequency) {
            this.bargainFrequency = bargainFrequency;
        }

        public int getUserBuyLimit() {
            return userBuyLimit;
        }

        public void setUserBuyLimit(int userBuyLimit) {
            this.userBuyLimit = userBuyLimit;
        }

        public Object getIsAllowRepeatBargain() {
            return isAllowRepeatBargain;
        }

        public void setIsAllowRepeatBargain(Object isAllowRepeatBargain) {
            this.isAllowRepeatBargain = isAllowRepeatBargain;
        }

        public int getIsAllowSelfBargain() {
            return isAllowSelfBargain;
        }

        public void setIsAllowSelfBargain(int isAllowSelfBargain) {
            this.isAllowSelfBargain = isAllowSelfBargain;
        }

        public int getBargainOrderEffectiveDuration() {
            return bargainOrderEffectiveDuration;
        }

        public void setBargainOrderEffectiveDuration(int bargainOrderEffectiveDuration) {
            this.bargainOrderEffectiveDuration = bargainOrderEffectiveDuration;
        }

        public int getRedPacketRate() {
            return redPacketRate;
        }

        public void setRedPacketRate(int redPacketRate) {
            this.redPacketRate = redPacketRate;
        }

        public int getBargainValueWay() {
            return bargainValueWay;
        }

        public void setBargainValueWay(int bargainValueWay) {
            this.bargainValueWay = bargainValueWay;
        }

        public int getRedPacketSendRule() {
            return redPacketSendRule;
        }

        public void setRedPacketSendRule(int redPacketSendRule) {
            this.redPacketSendRule = redPacketSendRule;
        }

        public int getRedPacketReceiveRule() {
            return redPacketReceiveRule;
        }

        public void setRedPacketReceiveRule(int redPacketReceiveRule) {
            this.redPacketReceiveRule = redPacketReceiveRule;
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
    }

    public static class GoodsManageModelBean {
        /**
         * id : 5d316b4aaf8f960001217fd5
         * bussId : 5d2ed43b7b6c2e00016d2d0d
         * url : null
         * goodsCode : 1
         * goodsName : 稻草人女包2019新款时尚百搭可爱
         * brandId : 5d313c14bbe399000123681c
         * brandName : null
         * marketPrice : null
         * costPrice : null
         * salePrice : null
         * stock : null
         * cumulativeSales : null
         * goodsType : null
         * createTime : 2019-07-19 15:03:38
         * lastUpdateTime : 2019-07-19 15:04:05
         * tagId : null
         * tagIds : null
         * categoryId : null
         * introduction : 我是商品描述
         * keyword : null
         * postage : 0
         * logistics : 1
         * online : 1
         * isUsed : 1
         * deleted : 0
         * checked : 2
         * checkReason : null
         * categoryId1 : 5d313c84bbe399000123681d
         * categoryId2 : 5d3168c8af8f960001217fd4
         * goodsCount : null
         * categoryName : null
         * categoryNameLink : 轻奢-品牌
         * goodsImagesList : [{"id":"5d316b4aaf8f960001217fd6","goodsId":"5d316b4aaf8f960001217fd5","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png","type":1,"rankNum":1,"createTime":"2019-07-19 15:03:38"},{"id":"5d316b4aaf8f960001217fd7","goodsId":"5d316b4aaf8f960001217fd5","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/2a89b7c29045414fbc1ccd737d939646.png","type":2,"rankNum":1,"createTime":"2019-07-19 15:03:38"},{"id":"5d316b4aaf8f960001217fd8","goodsId":"5d316b4aaf8f960001217fd5","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/4eac789221bd458cafbdc1a765061984.png","type":2,"rankNum":2,"createTime":"2019-07-19 15:03:38"}]
         * goodsSkuList : [{"id":"5d316b4aaf8f960001217fd9","goodsId":"5d316b4aaf8f960001217fd5","goodsUnit":"1","goodsModel":"猫咪","goodsType":"白色","stock":100,"virtualStock":null,"freezeStock":null,"marketPrice":59900,"costPrice":29900,"salePrice":39900,"deleted":0,"status":1,"startNum":1}]
         * goodsCheckLogList : null
         * businessName : Min4
         * businessMobile : 18075713587
         */

        private String id;
        private String bussId;
        private Object url;
        private String goodsCode;
        private String goodsName;
        private String brandId;
        private Object brandName;
        private Object marketPrice;
        private Object costPrice;
        private Object salePrice;
        private Object stock;
        private Object cumulativeSales;
        private Object goodsType;
        private String createTime;
        private String lastUpdateTime;
        private Object tagId;
        private Object tagIds;
        private Object categoryId;
        private String introduction;
        private Object keyword;
        private int postage;
        private int logistics;
        private int online;
        private int isUsed;
        private int deleted;
        private int checked;
        private Object checkReason;
        private String categoryId1;
        private String categoryId2;
        private Object goodsCount;
        private Object categoryName;
        private String categoryNameLink;
        private Object goodsCheckLogList;
        private String businessName;
        private String businessMobile;
        private List<GoodsImagesListBean> goodsImagesList;
        private List<GoodsSkuListBean> goodsSkuList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBussId() {
            return bussId;
        }

        public void setBussId(String bussId) {
            this.bussId = bussId;
        }

        public Object getUrl() {
            return url;
        }

        public void setUrl(Object url) {
            this.url = url;
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

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public Object getBrandName() {
            return brandName;
        }

        public void setBrandName(Object brandName) {
            this.brandName = brandName;
        }

        public Object getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(Object marketPrice) {
            this.marketPrice = marketPrice;
        }

        public Object getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(Object costPrice) {
            this.costPrice = costPrice;
        }

        public Object getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(Object salePrice) {
            this.salePrice = salePrice;
        }

        public Object getStock() {
            return stock;
        }

        public void setStock(Object stock) {
            this.stock = stock;
        }

        public Object getCumulativeSales() {
            return cumulativeSales;
        }

        public void setCumulativeSales(Object cumulativeSales) {
            this.cumulativeSales = cumulativeSales;
        }

        public Object getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(Object goodsType) {
            this.goodsType = goodsType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public Object getTagId() {
            return tagId;
        }

        public void setTagId(Object tagId) {
            this.tagId = tagId;
        }

        public Object getTagIds() {
            return tagIds;
        }

        public void setTagIds(Object tagIds) {
            this.tagIds = tagIds;
        }

        public Object getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Object categoryId) {
            this.categoryId = categoryId;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public Object getKeyword() {
            return keyword;
        }

        public void setKeyword(Object keyword) {
            this.keyword = keyword;
        }

        public int getPostage() {
            return postage;
        }

        public void setPostage(int postage) {
            this.postage = postage;
        }

        public int getLogistics() {
            return logistics;
        }

        public void setLogistics(int logistics) {
            this.logistics = logistics;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public int getIsUsed() {
            return isUsed;
        }

        public void setIsUsed(int isUsed) {
            this.isUsed = isUsed;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public int getChecked() {
            return checked;
        }

        public void setChecked(int checked) {
            this.checked = checked;
        }

        public Object getCheckReason() {
            return checkReason;
        }

        public void setCheckReason(Object checkReason) {
            this.checkReason = checkReason;
        }

        public String getCategoryId1() {
            return categoryId1;
        }

        public void setCategoryId1(String categoryId1) {
            this.categoryId1 = categoryId1;
        }

        public String getCategoryId2() {
            return categoryId2;
        }

        public void setCategoryId2(String categoryId2) {
            this.categoryId2 = categoryId2;
        }

        public Object getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(Object goodsCount) {
            this.goodsCount = goodsCount;
        }

        public Object getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(Object categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryNameLink() {
            return categoryNameLink;
        }

        public void setCategoryNameLink(String categoryNameLink) {
            this.categoryNameLink = categoryNameLink;
        }

        public Object getGoodsCheckLogList() {
            return goodsCheckLogList;
        }

        public void setGoodsCheckLogList(Object goodsCheckLogList) {
            this.goodsCheckLogList = goodsCheckLogList;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getBusinessMobile() {
            return businessMobile;
        }

        public void setBusinessMobile(String businessMobile) {
            this.businessMobile = businessMobile;
        }

        public List<GoodsImagesListBean> getGoodsImagesList() {
            return goodsImagesList;
        }

        public void setGoodsImagesList(List<GoodsImagesListBean> goodsImagesList) {
            this.goodsImagesList = goodsImagesList;
        }

        public List<GoodsSkuListBean> getGoodsSkuList() {
            return goodsSkuList;
        }

        public void setGoodsSkuList(List<GoodsSkuListBean> goodsSkuList) {
            this.goodsSkuList = goodsSkuList;
        }

        public static class GoodsImagesListBean {
            /**
             * id : 5d316b4aaf8f960001217fd6
             * goodsId : 5d316b4aaf8f960001217fd5
             * url : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png
             * type : 1
             * rankNum : 1
             * createTime : 2019-07-19 15:03:38
             */

            private String id;
            private String goodsId;
            private String url;
            private int type;
            private int rankNum;
            private String createTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class GoodsSkuListBean {
            /**
             * id : 5d316b4aaf8f960001217fd9
             * goodsId : 5d316b4aaf8f960001217fd5
             * goodsUnit : 1
             * goodsModel : 猫咪
             * goodsType : 白色
             * stock : 100
             * virtualStock : null
             * freezeStock : null
             * marketPrice : 59900
             * costPrice : 29900
             * salePrice : 39900
             * deleted : 0
             * status : 1
             * startNum : 1
             */

            private String id;
            private String goodsId;
            private String goodsUnit;
            private String goodsModel;
            private String goodsType;
            private int stock;
            private Object virtualStock;
            private Object freezeStock;
            private int marketPrice;
            private int costPrice;
            private int salePrice;
            private int deleted;
            private int status;
            private int startNum;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsUnit() {
                return goodsUnit;
            }

            public void setGoodsUnit(String goodsUnit) {
                this.goodsUnit = goodsUnit;
            }

            public String getGoodsModel() {
                return goodsModel;
            }

            public void setGoodsModel(String goodsModel) {
                this.goodsModel = goodsModel;
            }

            public String getGoodsType() {
                return goodsType;
            }

            public void setGoodsType(String goodsType) {
                this.goodsType = goodsType;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public Object getVirtualStock() {
                return virtualStock;
            }

            public void setVirtualStock(Object virtualStock) {
                this.virtualStock = virtualStock;
            }

            public Object getFreezeStock() {
                return freezeStock;
            }

            public void setFreezeStock(Object freezeStock) {
                this.freezeStock = freezeStock;
            }

            public int getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(int marketPrice) {
                this.marketPrice = marketPrice;
            }

            public int getCostPrice() {
                return costPrice;
            }

            public void setCostPrice(int costPrice) {
                this.costPrice = costPrice;
            }

            public int getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(int salePrice) {
                this.salePrice = salePrice;
            }

            public int getDeleted() {
                return deleted;
            }

            public void setDeleted(int deleted) {
                this.deleted = deleted;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getStartNum() {
                return startNum;
            }

            public void setStartNum(int startNum) {
                this.startNum = startNum;
            }
        }
    }
}
