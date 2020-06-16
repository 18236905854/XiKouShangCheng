package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName ActivityBean
 * Description 活动首页数据Bean
 * Author 卿凯
 * Date 2019/7/18/018
 * Version V1.0
 */
public class ActivityBean {


    /**
     * auctionHomeActivityVo : {"activityId":"","activityName":"","activityType":0,"auctionCommodityCounts":0,"auctionUserCounts":0,"bannerUrl":""}
     * bargainHomeActivityVo : {"activityDesc":"","activityId":"","activityName":"","activityType":0,"bannerUrl":"","bargainCommodityList":[{"commodityName":"","commodityPrice":0,"goodsImageUrl":"","id":"","salePrice":0}]}
     * discountHomeActivityVo : {"activityDesc":"","activityId":"","activityName":"","activityType":0,"bannerUrl":"","imageUrls":[],"rule":[]}
     * globalBuyerHomeActivityVo : {"activityDesc":"","activityId":"","activityName":"","activityType":0,"bannerUrl":"","globalBuyerCommodityList":[{"commodityName":"","commodityPrice":0,"couponValue":0,"goodsImageUrl":"","id":"","salePrice":0}]}
     */

    private AuctionHomeActivityVoBean auctionHomeActivityVo;
    private BargainHomeActivityVoBean bargainHomeActivityVo;
    private DiscountHomeActivityVoBean discountHomeActivityVo;
    private GlobalBuyerHomeActivityVoBean globalBuyerHomeActivityVo;

    public AuctionHomeActivityVoBean getAuctionHomeActivityVo() {
        return auctionHomeActivityVo;
    }

    public void setAuctionHomeActivityVo(AuctionHomeActivityVoBean auctionHomeActivityVo) {
        this.auctionHomeActivityVo = auctionHomeActivityVo;
    }

    public BargainHomeActivityVoBean getBargainHomeActivityVo() {
        return bargainHomeActivityVo;
    }

    public void setBargainHomeActivityVo(BargainHomeActivityVoBean bargainHomeActivityVo) {
        this.bargainHomeActivityVo = bargainHomeActivityVo;
    }

    public DiscountHomeActivityVoBean getDiscountHomeActivityVo() {
        return discountHomeActivityVo;
    }

    public void setDiscountHomeActivityVo(DiscountHomeActivityVoBean discountHomeActivityVo) {
        this.discountHomeActivityVo = discountHomeActivityVo;
    }

    public GlobalBuyerHomeActivityVoBean getGlobalBuyerHomeActivityVo() {
        return globalBuyerHomeActivityVo;
    }

    public void setGlobalBuyerHomeActivityVo(GlobalBuyerHomeActivityVoBean globalBuyerHomeActivityVo) {
        this.globalBuyerHomeActivityVo = globalBuyerHomeActivityVo;
    }

    public static class AuctionHomeActivityVoBean {
        /**
         * activityId :
         * activityName :
         * activityType : 0
         * auctionCommodityCounts : 0
         * auctionUserCounts : 0
         * bannerUrl :
         */

        private String activityId;
        private String activityName;
        private int activityType;
        private int auctionCommodityCounts;
        private int auctionUserCounts;
        private String bannerUrl;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public int getAuctionCommodityCounts() {
            return auctionCommodityCounts;
        }

        public void setAuctionCommodityCounts(int auctionCommodityCounts) {
            this.auctionCommodityCounts = auctionCommodityCounts;
        }

        public int getAuctionUserCounts() {
            return auctionUserCounts;
        }

        public void setAuctionUserCounts(int auctionUserCounts) {
            this.auctionUserCounts = auctionUserCounts;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }
    }

    public static class BargainHomeActivityVoBean {
        /**
         * activityDesc :
         * activityId :
         * activityName :
         * activityType : 0
         * bannerUrl :
         * bargainCommodityList : [{"commodityName":"","commodityPrice":0,"goodsImageUrl":"","id":"","salePrice":0}]
         */

        private String activityDesc;
        private String activityId;
        private String activityName;
        private int activityType;
        private String bannerUrl;
        private List<BargainCommodityListBean> bargainCommodityList;

        public String getActivityDesc() {
            return activityDesc;
        }

        public void setActivityDesc(String activityDesc) {
            this.activityDesc = activityDesc;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public List<BargainCommodityListBean> getBargainCommodityList() {
            return bargainCommodityList;
        }

        public void setBargainCommodityList(List<BargainCommodityListBean> bargainCommodityList) {
            this.bargainCommodityList = bargainCommodityList;
        }

        public static class BargainCommodityListBean {
            /**
             * commodityName :
             * commodityPrice : 0
             * goodsImageUrl :
             * id :
             * salePrice : 0
             */

            private String commodityName;
            private int commodityPrice;
            private String goodsImageUrl;
            private String id;
            private int salePrice;

            public String getCommodityName() {
                return commodityName;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public int getCommodityPrice() {
                return commodityPrice;
            }

            public void setCommodityPrice(int commodityPrice) {
                this.commodityPrice = commodityPrice;
            }

            public String getGoodsImageUrl() {
                return goodsImageUrl;
            }

            public void setGoodsImageUrl(String goodsImageUrl) {
                this.goodsImageUrl = goodsImageUrl;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(int salePrice) {
                this.salePrice = salePrice;
            }
        }
    }

    public static class DiscountHomeActivityVoBean {
        /**
         * activityDesc :
         * activityId :
         * activityName :
         * activityType : 0
         * bannerUrl :
         * imageUrls : []
         * rule : []
         */

        private String activityDesc;
        private String activityId;
        private String activityName;
        private int activityType;
        private String bannerUrl;
        private String[] imageUrls;
        private Float[] rule;

        public String getActivityDesc() {
            return activityDesc;
        }

        public void setActivityDesc(String activityDesc) {
            this.activityDesc = activityDesc;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public String[] getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(String[] imageUrls) {
            this.imageUrls = imageUrls;
        }

        public Float[] getRule() {
            return rule;
        }

        public void setRule(Float[] rule) {
            this.rule = rule;
        }
    }

    public static class GlobalBuyerHomeActivityVoBean {
        /**
         * activityDesc :
         * activityId :
         * activityName :
         * activityType : 0
         * bannerUrl :
         * globalBuyerCommodityList : [{"commodityName":"","commodityPrice":0,"couponValue":0,"goodsImageUrl":"","id":"","salePrice":0}]
         */

        private String activityDesc;
        private String activityId;
        private String activityName;
        private int activityType;
        private String bannerUrl;
        private List<GlobalBuyerCommodityListBean> globalBuyerCommodityList;

        public String getActivityDesc() {
            return activityDesc;
        }

        public void setActivityDesc(String activityDesc) {
            this.activityDesc = activityDesc;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public List<GlobalBuyerCommodityListBean> getGlobalBuyerCommodityList() {
            return globalBuyerCommodityList;
        }

        public void setGlobalBuyerCommodityList(List<GlobalBuyerCommodityListBean> globalBuyerCommodityList) {
            this.globalBuyerCommodityList = globalBuyerCommodityList;
        }

        public static class GlobalBuyerCommodityListBean {
            /**
             * commodityName :
             * commodityPrice : 0
             * couponValue : 0
             * goodsImageUrl :
             * id :
             * salePrice : 0
             */

            private String commodityName;
            private int commodityPrice;
            private int couponValue;
            private String goodsImageUrl;
            private String id;
            private int salePrice;

            public String getCommodityName() {
                return commodityName;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public int getCommodityPrice() {
                return commodityPrice;
            }

            public void setCommodityPrice(int commodityPrice) {
                this.commodityPrice = commodityPrice;
            }

            public int getCouponValue() {
                return couponValue;
            }

            public void setCouponValue(int couponValue) {
                this.couponValue = couponValue;
            }

            public String getGoodsImageUrl() {
                return goodsImageUrl;
            }

            public void setGoodsImageUrl(String goodsImageUrl) {
                this.goodsImageUrl = goodsImageUrl;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(int salePrice) {
                this.salePrice = salePrice;
            }
        }
    }
}
