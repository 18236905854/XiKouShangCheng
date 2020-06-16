package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName GlobalBuyerGoodsDetailBean
 * Description 全球买手商品详情Bean
 * Author 卿凯
 * Date 2019/7/13/013
 * Version V1.0
 */
public class GlobalBuyerGoodsDetailBean {
    /**
     * activityType : null
     * baseRuleVo : {"flag":true,"postage":0,"deliveryDuration":10}
     * activityCommodityVo : {"id":"5d37b19e88bde9000145762a","activityId":"5d367d9c86a63e000160c244","merchantId":"5d2ed43b7b6c2e00016d2d0d","merchantName":"18075713587","goodsId":"5d37b15d6527a00001406a69","goodCode":null,"commodityName":"Test-商品支付3","categoryName":"食品-零食-薯片","commodityPrice":5,"salePrice":10,"stock":55,"virtualStock":55,"imageList":[],"skuList":[{"id":"5d37b15d6527a00001406a6c","goodsModel":"3","goodsSpec":"3"}]}
     */

    private int activityType;
    private BaseRuleVoBean baseRuleModel;
    private ActivityCommodityVoBean activityCommodityAndSkuModel;
    private String officialPictures;//官方说明图片

    public String getOfficialPictures() {
        return officialPictures;
    }

    public void setOfficialPictures(String officialPictures) {
        this.officialPictures = officialPictures;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public BaseRuleVoBean getBaseRuleVo() {
        return baseRuleModel;
    }

    public void setBaseRuleVo(BaseRuleVoBean baseRuleVo) {
        this.baseRuleModel = baseRuleVo;
    }

    public ActivityCommodityVoBean getActivityCommodityVo() {
        return activityCommodityAndSkuModel;
    }

    public void setActivityCommodityVo(ActivityCommodityVoBean activityCommodityVo) {
        this.activityCommodityAndSkuModel = activityCommodityVo;
    }

    public static class BaseRuleVoBean {
        /**
         * flag : true
         * postage : 0
         * deliveryDuration : 10
         */

        private boolean flag;//是否包邮
        private int postage;
        private int deliveryDuration;
        private int buyLimit;//限购数量
        private int deductionCouponAmount;//商品可以用优惠券金额
        private boolean buyLimited;//是否限购
        private int generateType;//赠送优惠券类型字段，1：支付成功后赠送   2：确认收货后赠送

        private int reservePriceRate;//商品底价占商品原价的百分比(0-100)
        private int isConsignment;//寄卖
        private int consignmentDuration;//寄卖时长
        private int couponValue;//赠送优惠券
        private int targetNumber;//目标拼团人数
        private int currentFightGroupNum;//目前拼团人数
        private int fightGroupType;//拼团类型(1: 按成团人数 2: 按成团件数)
        //喜立得
        private int bargainNumber;//需要砍价人数
        private String bargainTime;//砍价总时长
        //0元拍
        private int expendNumber;//每次消耗数量
        private int postponeRange;//每次顺延的秒数
        private int appLoopSeconds;//轮询时间 单位(毫秒）
        //多买多折
        private int maxLimit;//最小购买数
        private int minLimit;//最小购买数

        public String getBargainTime() {
            return bargainTime;
        }

        public void setBargainTime(String bargainTime) {
            this.bargainTime = bargainTime;
        }

        public int getGenerateType() {
            return generateType;
        }

        public void setGenerateType(int generateType) {
            this.generateType = generateType;
        }

        public int getMaxLimit() {
            return maxLimit;
        }

        public void setMaxLimit(int maxLimit) {
            this.maxLimit = maxLimit;
        }

        public int getMinLimit() {
            return minLimit;
        }

        public void setMinLimit(int minLimit) {
            this.minLimit = minLimit;
        }

        public int getFightGroupType() {
            return fightGroupType;
        }

        public void setFightGroupType(int fightGroupType) {
            this.fightGroupType = fightGroupType;
        }

        public int getExpendNumber() {
            return expendNumber;
        }

        public void setExpendNumber(int expendNumber) {
            this.expendNumber = expendNumber;
        }

        public int getPostponeRange() {
            return postponeRange;
        }

        public int getAppLoopSeconds() {
            return appLoopSeconds;
        }

        public void setAppLoopSeconds(int appLoopSeconds) {
            this.appLoopSeconds = appLoopSeconds;
        }

        public void setPostponeRange(int postponeRange) {
            this.postponeRange = postponeRange;
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

        public int getIsConsignment() {
            return isConsignment;
        }

        public void setIsConsignment(int isConsignment) {
            this.isConsignment = isConsignment;
        }

        public int getConsignmentDuration() {
            return consignmentDuration;
        }

        public void setConsignmentDuration(int consignmentDuration) {
            this.consignmentDuration = consignmentDuration;
        }

        public int getCouponValue() {
            return couponValue;
        }

        public void setCouponValue(int couponValue) {
            this.couponValue = couponValue;
        }

        public int getBargainNumber() {
            return bargainNumber;
        }

        public void setBargainNumber(int bargainNumber) {
            this.bargainNumber = bargainNumber;
        }

        public int getReservePriceRate() {
            return reservePriceRate;
        }

        public void setReservePriceRate(int reservePriceRate) {
            this.reservePriceRate = reservePriceRate;
        }

        public int getBuyLimit() {
            return buyLimit;
        }

        public void setBuyLimit(int buyLimit) {
            this.buyLimit = buyLimit;
        }

        public int getDeductionCouponAmount() {
            return deductionCouponAmount;
        }

        public void setDeductionCouponAmount(int deductionCouponAmount) {
            this.deductionCouponAmount = deductionCouponAmount;
        }

        public boolean isBuyLimited() {
            return buyLimited;
        }

        public void setBuyLimited(boolean buyLimited) {
            this.buyLimited = buyLimited;
        }

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
    }

    public static class ActivityCommodityVoBean {
        /**
         * id : 5d37b19e88bde9000145762a
         * activityId : 5d367d9c86a63e000160c244
         * merchantId : 5d2ed43b7b6c2e00016d2d0d
         * merchantName : 18075713587
         * goodsId : 5d37b15d6527a00001406a69
         * goodCode : null
         * commodityName : Test-商品支付3
         * categoryName : 食品-零食-薯片
         * commodityPrice : 5
         * salePrice : 10
         * stock : 55
         * virtualStock : 55
         * imageList : []
         * skuList : [{"id":"5d37b15d6527a00001406a6c","goodsModel":"3","goodsSpec":"3"}]
         */

        private String id;
        private String activityId;
        private String merchantId;
        private String merchantName;//商家
        private String goodsId;
        private String goodCode;
        private String commodityName;
        private String categoryName;//品类
        private String consignmentId;//寄卖用户ID
        private int shareAmount;//多买多折分享赚
        private int commodityPrice;
        private int salePrice;
        private int stock;
        private int totalConsignmentStock;//吾G库存判断
        private int virtualStock;
        private int consignmentType;//寄卖方式(1：定向寄卖 2：寄卖到吾G NULL或者3：所有寄卖方式)
        private String goodsSpec;//商品规格
        private String goodsModel;//商品型号
        private String merchantPhone;//商家电话号码，用“,”隔开
        private int brandAuthorized;//商品是否有品牌授权 0:未授权  1:已授权
        private int sevenDaysNoReasonReturn = 1;//是否支持无理由退货  0:不支持  1:支持
        private List<ImageListBean> imageList;
        private List<GoodsSkuListBean2> skuList;
        private List<GoodsModel> models;

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

        public int getBrandAuthorized() {
            return brandAuthorized;
        }

        public void setBrandAuthorized(int brandAuthorized) {
            this.brandAuthorized = brandAuthorized;
        }

        public int getConsignmentType() {
            return consignmentType;
        }

        public void setConsignmentType(int consignmentType) {
            this.consignmentType = consignmentType;
        }

        public int getShareAmount() {
            return shareAmount;
        }

        public void setShareAmount(int shareAmount) {
            this.shareAmount = shareAmount;
        }

        public String getGoodsSpec() {
            return goodsSpec;
        }

        public void setGoodsSpec(String goodsSpec) {
            this.goodsSpec = goodsSpec;
        }

        public String getGoodsModel() {
            return goodsModel;
        }

        public void setGoodsModel(String goodsModel) {
            this.goodsModel = goodsModel;
        }

        public List<GoodsModel> getModels() {
            return models;
        }

        public void setModels(List<GoodsModel> models) {
            this.models = models;
        }

        private int discount;
        private int marketPrice;
        //多买多折
        private int commodityPriceOne;
        private int commodityPriceTwo;
        private int commodityPriceThree;

        private int bargainEffectiveTime;//喜立得活动有效时间
        private int currentPrice;//喜立得当前价格
        private int bargainCount;//喜立得当前砍价人数
        private int bargainSuccessCount;//喜立得当前成功人数
        private int bargainStatus;//喜立得状态 1继续砍价  2完成砍价（支付）
        private int bargainState;// 0 未发起砍价  1 继续砍价  2砍价成功
        private String originalId;//原始订单ID
        private String bargainCreateTime;//砍价发起时间

        //0元拍
        private List<RecordListBean> recordList;//出价记录
        private int startPrice;//起拍价
        private int finalPrice;//成交价
        private int bottomPrice;//bottomPrice
        private String startTime;//开始时间
        private String endTime;//结束时间
        private int biddingNumber;//每次加价多少钱
        //喜立得
        private int salesVolume;//销量  当前购买量
        private int cutPrice;//可以砍掉的金额

        public int getTotalConsignmentStock() {
            return totalConsignmentStock;
        }

        public void setTotalConsignmentStock(int totalConsignmentStock) {
            this.totalConsignmentStock = totalConsignmentStock;
        }

        public int getCommodityPriceOne() {
            return commodityPriceOne;
        }

        public void setCommodityPriceOne(int commodityPriceOne) {
            this.commodityPriceOne = commodityPriceOne;
        }

        public int getCommodityPriceTwo() {
            return commodityPriceTwo;
        }

        public void setCommodityPriceTwo(int commodityPriceTwo) {
            this.commodityPriceTwo = commodityPriceTwo;
        }

        public int getCommodityPriceThree() {
            return commodityPriceThree;
        }

        public void setCommodityPriceThree(int commodityPriceThree) {
            this.commodityPriceThree = commodityPriceThree;
        }

        public int getCutPrice() {
            return cutPrice;
        }

        public void setCutPrice(int cutPrice) {
            this.cutPrice = cutPrice;
        }

        public int getSalesVolume() {
            return salesVolume;
        }

        public void setSalesVolume(int salesVolume) {
            this.salesVolume = salesVolume;
        }

        public int getBargainState() {
            return bargainState;
        }

        public void setBargainState(int bargainState) {
            this.bargainState = bargainState;
        }

        public String getBargainCreateTime() {
            return bargainCreateTime;
        }

        public void setBargainCreateTime(String bargainCreateTime) {
            this.bargainCreateTime = bargainCreateTime;
        }

        public String getConsignmentId() {
            return consignmentId;
        }

        public void setConsignmentId(String consignmentId) {
            this.consignmentId = consignmentId;
        }

        public int getStartPrice() {
            return startPrice;
        }

        public void setStartPrice(int startPrice) {
            this.startPrice = startPrice;
        }

        public int getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(int finalPrice) {
            this.finalPrice = finalPrice;
        }

        public int getBottomPrice() {
            return bottomPrice;
        }

        public void setBottomPrice(int bottomPrice) {
            this.bottomPrice = bottomPrice;
        }

        public int getBiddingNumber() {
            return biddingNumber;
        }

        public void setBiddingNumber(int biddingNumber) {
            this.biddingNumber = biddingNumber;
        }

        public int getBargainStatus() {
            return bargainStatus;
        }

        public void setBargainStatus(int bargainStatus) {
            this.bargainStatus = bargainStatus;
        }

        public String getOriginalId() {
            return originalId;
        }

        public void setOriginalId(String originalId) {
            this.originalId = originalId;
        }

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

        public List<ImageListBean> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageListBean> imageList) {
            this.imageList = imageList;
        }

        public List<GoodsSkuListBean2> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<GoodsSkuListBean2> skuList) {
            this.skuList = skuList;
        }

        public List<RecordListBean> getRecordList() {
            return recordList;
        }

        public void setRecordList(List<RecordListBean> recordList) {
            this.recordList = recordList;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public int getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(int marketPrice) {
            this.marketPrice = marketPrice;
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

        public int getBargainEffectiveTime() {
            return bargainEffectiveTime;
        }

        public void setBargainEffectiveTime(int bargainEffectiveTime) {
            this.bargainEffectiveTime = bargainEffectiveTime;
        }

        public int getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(int currentPrice) {
            this.currentPrice = currentPrice;
        }

        public int getBargainCount() {
            return bargainCount;
        }

        public void setBargainCount(int bargainCount) {
            this.bargainCount = bargainCount;
        }

        public int getBargainSuccessCount() {
            return bargainSuccessCount;
        }

        public void setBargainSuccessCount(int bargainSuccessCount) {
            this.bargainSuccessCount = bargainSuccessCount;
        }

        public static class ImageListBean {

            /**
             * rankNum : 图片排序
             * type : 1：主图 ,2：详情图
             * url :
             */

            private int rankNum;
            private int type;
            private String url;

            public int getRankNum() {
                return rankNum;
            }

            public void setRankNum(int rankNum) {
                this.rankNum = rankNum;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class RecordListBean {

            /**
             * activityId :
             * area :
             * auctionPrice : 0
             * commodityId :
             * createTime :
             * currentPrice : 0
             * id :
             * userId :
             * userName :
             */

            private String activityId;
            private String area;
            private int auctionPrice;
            private String commodityId;
            private String createTime;
            private int currentPrice;
            private String id;
            private String userId;
            private String userName;

            public String getActivityId() {
                return activityId;
            }

            public void setActivityId(String activityId) {
                this.activityId = activityId;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public int getAuctionPrice() {
                return auctionPrice;
            }

            public void setAuctionPrice(int auctionPrice) {
                this.auctionPrice = auctionPrice;
            }

            public String getCommodityId() {
                return commodityId;
            }

            public void setCommodityId(String commodityId) {
                this.commodityId = commodityId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getCurrentPrice() {
                return currentPrice;
            }

            public void setCurrentPrice(int currentPrice) {
                this.currentPrice = currentPrice;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
