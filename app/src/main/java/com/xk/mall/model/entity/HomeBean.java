package com.xk.mall.model.entity;

import java.util.List;

/**
 * 最新 首页model bean
 */
public class HomeBean {
    /**
     * homeBuyGiftActivityModel : {"activityType":1,"bannerUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/ef8fdfcee50945b69613ef2ac40b677a.jpg","activityId":"5d26aa371d4213000189ec44","activityName":"买一赠二123","activityDesc":null,"homeBuyGiftCommodityModels":[{"id":"5d26b72a41901f0001af5e34","activityId":"5d26aa371d4213000189ec44","userName":"张全会","commodityId":"5d269cc43ae2f500012b6fea","commodityName":"休闲短裤","salePrice":18000,"commodityPrice":12600,"goodsCode":"XXDK18231231","goodsId":"5d269cc43ae2f500012b6fe1","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/383e9f36502c4a768c95be4e47621721.jpg","couponValue":36000}]}
     * homeAuctionActivityModel : {"activityType":3,"bannerUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190712/65224020b3b643f685d32714b88f47b0.jpg","activityId":"5d28795b9216e50001a95b83","activityName":"0元抢购1","activityDesc":null,"endTime":"2019-07-30T16:00:00.000+0000"}
     * homeBargainActivityModel : {"activityType":5,"bannerUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/88f8ebebf6b4499d8e5f03bc11c6df41.jpg","activityId":"5d26fcac7e12330001743c54","activityName":"砍价1","activityDesc":null,"homeBargainCommodityModels":[{"id":"5d2839106dc8d900013df627","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/383e9f36502c4a768c95be4e47621721.jpg","commodityName":"休闲短裤-男","salePrice":18000,"marketPrice":32000,"commodityPrice":0,"reservePriceRate":null},{"id":"5d28390d6dc8d900013df626","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/21befdbe5f1d4f63a9fd141cbc9eebd1.jpg","commodityName":"超级闪亮蹦迪短裤","salePrice":15000,"marketPrice":20000,"commodityPrice":0,"reservePriceRate":null},{"id":"5d2730a01557650001e74320","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg","commodityName":"明国旗袍","salePrice":30000,"marketPrice":45000,"commodityPrice":150000,"reservePriceRate":null},{"id":"5d27309e1557650001e7431f","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/e9a76aa72b4f4d3abed2e7623b123a14.jpg","commodityName":"小米9-战斗天使","salePrice":309900,"marketPrice":329900,"commodityPrice":2800000,"reservePriceRate":null}]}
     * homeDiscountActivityModel : {"activityType":4,"bannerUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/2b4d686733714e87a75c5280ed4b6d48.png","activityId":"5d26b49bbe899e00015a3b8b","activityName":"sa","activityDesc":null,"homeDiscountCommodityModels":[{"id":"5d25536e06ebb70001e6f0da","activityId":"5d25440bf4c4b10001633db8","goodsId":"5d16011f3cfaa0000176bced","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190628/440c0108ba164a638e7d513ceed5a252.jpg","commodityId":"5d1ffce9f88dde000147c891","commodityName":"HSTYLE2019夏新款少女蕾丝连衣裙","salePrice":20000,"marketPrice":30000,"salesVolume":null,"discount":5.7,"rateOne":null},{"id":"5d25536e06ebb70001e6f0db","activityId":"5d25440bf4c4b10001633db8","goodsId":"5d16011f3cfaa0000176bced","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190628/440c0108ba164a638e7d513ceed5a252.jpg","commodityId":"5d1ffce9f88dde000147c892","commodityName":"HSTYLE2019夏新款少女蕾丝连衣裙","salePrice":18000,"marketPrice":26000,"salesVolume":null,"discount":7.4,"rateOne":null},{"id":"5d25536e06ebb70001e6f0dc","activityId":"5d25440bf4c4b10001633db8","goodsId":"5d16011f3cfaa0000176bced","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190628/440c0108ba164a638e7d513ceed5a252.jpg","commodityId":"5d1ffce9f88dde000147c893","commodityName":"HSTYLE2019夏新款少女蕾丝连衣裙","salePrice":20000,"marketPrice":30000,"salesVolume":null,"discount":5.1,"rateOne":null},{"id":"5d25c789cc814e0001b3e4f5","activityId":"5d25a739cc814e0001b3e4e2","goodsId":"5d16011f3cfaa0000176bced","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190628/440c0108ba164a638e7d513ceed5a252.jpg","commodityId":"5d1ffce9f88dde000147c892","commodityName":"HSTYLE2019夏新款少女蕾丝连衣裙","salePrice":18000,"marketPrice":26000,"salesVolume":null,"discount":7,"rateOne":null},{"id":"5d27350833986f000132ab15","activityId":"5d272e91093d610001dc4a6b","goodsId":"5d27321e37fdc000014a33f1","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/21befdbe5f1d4f63a9fd141cbc9eebd1.jpg","commodityId":"5d27321e37fdc000014a33fc","commodityName":"超级闪亮蹦迪短裤","salePrice":15000,"marketPrice":20000,"salesVolume":null,"discount":8,"rateOne":null}]}
     */
    private List<Integer> bannerUrl;//轮播图
    private HomeBuyGiftActivityModelBean homeBuyGiftActivityModel;//吾G
    private HomeAuctionActivityModelBean homeAuctionActivityModel;//0元拍
    private HomeBargainActivityModelBean homeBargainActivityModel;//喜立得
    private HomeDiscountActivityModelBean homeDiscountActivityModel;//多买多折
    private HomePageGlobalBuyerActivityModelBean homePageGlobalBuyerActivityModel;

    public List<Integer> getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(List<Integer> bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public HomeBuyGiftActivityModelBean getHomeBuyGiftActivityModel() {
        return homeBuyGiftActivityModel;
    }

    public void setHomeBuyGiftActivityModel(HomeBuyGiftActivityModelBean homeBuyGiftActivityModel) {
        this.homeBuyGiftActivityModel = homeBuyGiftActivityModel;
    }

    public HomeAuctionActivityModelBean getHomeAuctionActivityModel() {
        return homeAuctionActivityModel;
    }

    public void setHomeAuctionActivityModel(HomeAuctionActivityModelBean homeAuctionActivityModel) {
        this.homeAuctionActivityModel = homeAuctionActivityModel;
    }

    public HomeBargainActivityModelBean getHomeBargainActivityModel() {
        return homeBargainActivityModel;
    }

    public void setHomeBargainActivityModel(HomeBargainActivityModelBean homeBargainActivityModel) {
        this.homeBargainActivityModel = homeBargainActivityModel;
    }

    public HomeDiscountActivityModelBean getHomeDiscountActivityModel() {
        return homeDiscountActivityModel;
    }

    public void setHomeDiscountActivityModel(HomeDiscountActivityModelBean homeDiscountActivityModel) {
        this.homeDiscountActivityModel = homeDiscountActivityModel;
    }

    public HomePageGlobalBuyerActivityModelBean getHomePageGlobalBuyerActivityModel() {
        return homePageGlobalBuyerActivityModel;
    }

    public void setHomePageGlobalBuyerActivityModel(HomePageGlobalBuyerActivityModelBean homePageGlobalBuyerActivityModel) {
        this.homePageGlobalBuyerActivityModel = homePageGlobalBuyerActivityModel;
    }

    public static class HomeBuyGiftActivityModelBean {
        /**
         * activityType : 1
         * bannerUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/ef8fdfcee50945b69613ef2ac40b677a.jpg
         * activityId : 5d26aa371d4213000189ec44
         * activityName : 买一赠二123
         * activityDesc : null
         * homeBuyGiftCommodityModels : [{"id":"5d26b72a41901f0001af5e34","activityId":"5d26aa371d4213000189ec44","userName":"张全会","commodityId":"5d269cc43ae2f500012b6fea","commodityName":"休闲短裤","salePrice":18000,"commodityPrice":12600,"goodsCode":"XXDK18231231","goodsId":"5d269cc43ae2f500012b6fe1","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/383e9f36502c4a768c95be4e47621721.jpg","couponValue":36000}]
         */

        private int activityType;
        private String bannerUrl;
        private String activityId;
        private String activityName;
        private String activityDesc;
        private List<HomeBuyGiftCommodityModelsBean> homeBuyGiftCommodityModels;

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

        public String getActivityDesc() {
            return activityDesc;
        }

        public void setActivityDesc(String activityDesc) {
            this.activityDesc = activityDesc;
        }

        public List<HomeBuyGiftCommodityModelsBean> getHomeBuyGiftCommodityModels() {
            return homeBuyGiftCommodityModels;
        }

        public void setHomeBuyGiftCommodityModels(List<HomeBuyGiftCommodityModelsBean> homeBuyGiftCommodityModels) {
            this.homeBuyGiftCommodityModels = homeBuyGiftCommodityModels;
        }

        public static class HomeBuyGiftCommodityModelsBean {
            /**
             * id : 5d26b72a41901f0001af5e34
             * activityId : 5d26aa371d4213000189ec44
             * userName : 张全会
             * commodityId : 5d269cc43ae2f500012b6fea
             * commodityName : 休闲短裤
             * salePrice : 18000
             * commodityPrice : 12600
             * goodsCode : XXDK18231231
             * goodsId : 5d269cc43ae2f500012b6fe1
             * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/383e9f36502c4a768c95be4e47621721.jpg
             * couponValue : 36000
             */

            private String id;
            private String activityId;
            private String userName;
            private String commodityId;
            private String commodityName;
            private int salePrice;
            private int commodityPrice;
            private String goodsCode;
            private String goodsId;
            private String goodsImageUrl;
            private int couponValue;
            private String userId;//寄卖用户ID
            private String originalId;//原始订单ID

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
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

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
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

            public int getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(int salePrice) {
                this.salePrice = salePrice;
            }

            public int getCommodityPrice() {
                return commodityPrice;
            }

            public void setCommodityPrice(int commodityPrice) {
                this.commodityPrice = commodityPrice;
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

            public int getCouponValue() {
                return couponValue;
            }

            public void setCouponValue(int couponValue) {
                this.couponValue = couponValue;
            }
        }
    }

    public static class HomeAuctionActivityModelBean {
        /**
         * activityType : 3
         * bannerUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190712/65224020b3b643f685d32714b88f47b0.jpg
         * activityId : 5d28795b9216e50001a95b83
         * activityName : 0元抢购1
         * activityDesc : null
         * endTime : 2019-07-30T16:00:00.000+0000
         */

        private int activityType;
        private String bannerUrl;
        private String activityId;
        private String activityName;
        private String activityDesc;
        private long endTime;
        private List<HomeAuctionCommodityModels> homeAuctionCommodityModels;

        public List<HomeAuctionCommodityModels> getHomeAuctionCommodityModels() {
            return homeAuctionCommodityModels;
        }

        public void setHomeAuctionCommodityModels(List<HomeAuctionCommodityModels> homeAuctionCommodityModels) {
            this.homeAuctionCommodityModels = homeAuctionCommodityModels;
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

        public String getActivityDesc() {
            return activityDesc;
        }

        public void setActivityDesc(String activityDesc) {
            this.activityDesc = activityDesc;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public static class HomeAuctionCommodityModels{
            private String activityId;
            private int bottomPrice;
            private String commodityId;
            private String commodityName;
            private String goodsImageUrl;
            private int commodityPrice;
            private int startPrice;//起拍价
            private int marketPrice;
            private int salePrice;
            private String id;

            public int getStartPrice() {
                return startPrice;
            }

            public void setStartPrice(int startPrice) {
                this.startPrice = startPrice;
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

            public int getBottomPrice() {
                return bottomPrice;
            }

            public void setBottomPrice(int bottomPrice) {
                this.bottomPrice = bottomPrice;
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

            public String getGoodsImageUrl() {
                return goodsImageUrl;
            }

            public void setGoodsImageUrl(String goodsImageUrl) {
                this.goodsImageUrl = goodsImageUrl;
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

            public int getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(int marketPrice) {
                this.marketPrice = marketPrice;
            }
        }
    }

    public static class HomeBargainActivityModelBean {
        /**
         * activityType : 5
         * bannerUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/88f8ebebf6b4499d8e5f03bc11c6df41.jpg
         * activityId : 5d26fcac7e12330001743c54
         * activityName : 砍价1
         * activityDesc : null
         * homeBargainCommodityModels : [{"id":"5d2839106dc8d900013df627","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/383e9f36502c4a768c95be4e47621721.jpg","commodityName":"休闲短裤-男","salePrice":18000,"marketPrice":32000,"commodityPrice":0,"reservePriceRate":null},{"id":"5d28390d6dc8d900013df626","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/21befdbe5f1d4f63a9fd141cbc9eebd1.jpg","commodityName":"超级闪亮蹦迪短裤","salePrice":15000,"marketPrice":20000,"commodityPrice":0,"reservePriceRate":null},{"id":"5d2730a01557650001e74320","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg","commodityName":"明国旗袍","salePrice":30000,"marketPrice":45000,"commodityPrice":150000,"reservePriceRate":null},{"id":"5d27309e1557650001e7431f","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/e9a76aa72b4f4d3abed2e7623b123a14.jpg","commodityName":"小米9-战斗天使","salePrice":309900,"marketPrice":329900,"commodityPrice":2800000,"reservePriceRate":null}]
         */

        private int activityType;
        private String bannerUrl;
        private String activityId;
        private String activityName;
        private String activityDesc;
        private List<HomeBargainCommodityModelsBean> homeBargainCommodityModels;

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

        public String getActivityDesc() {
            return activityDesc;
        }

        public void setActivityDesc(String activityDesc) {
            this.activityDesc = activityDesc;
        }

        public List<HomeBargainCommodityModelsBean> getHomeBargainCommodityModels() {
            return homeBargainCommodityModels;
        }

        public void setHomeBargainCommodityModels(List<HomeBargainCommodityModelsBean> homeBargainCommodityModels) {
            this.homeBargainCommodityModels = homeBargainCommodityModels;
        }

        public static class HomeBargainCommodityModelsBean {
            /**
             * id : 5d2839106dc8d900013df627
             * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/383e9f36502c4a768c95be4e47621721.jpg
             * commodityName : 休闲短裤-男
             * salePrice : 18000
             * marketPrice : 32000
             * commodityPrice : 0
             * reservePriceRate : null
             */

            private String id;
            private String goodsImageUrl;
            private String commodityName;
            private int salePrice;
            private int marketPrice;
            private int commodityPrice;
            private Object reservePriceRate;
            private String commodityId;
            private String goodsId;
            private String activityId;

            public String getActivityId() {
                return activityId;
            }
            public void setActivityId(String activityId) {
                this.activityId = activityId;
            }

            public String getCommodityId() {
                return commodityId;
            }

            public void setCommodityId(String commodityId) {
                this.commodityId = commodityId;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGoodsImageUrl() {
                return goodsImageUrl;
            }

            public void setGoodsImageUrl(String goodsImageUrl) {
                this.goodsImageUrl = goodsImageUrl;
            }

            public String getCommodityName() {
                return commodityName;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public int getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(int salePrice) {
                this.salePrice = salePrice;
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

            public Object getReservePriceRate() {
                return reservePriceRate;
            }

            public void setReservePriceRate(Object reservePriceRate) {
                this.reservePriceRate = reservePriceRate;
            }
        }
    }

    public static class HomeDiscountActivityModelBean {
        /**
         * activityType : 4
         * bannerUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/2b4d686733714e87a75c5280ed4b6d48.png
         * activityId : 5d26b49bbe899e00015a3b8b
         * activityName : sa
         * activityDesc : null
         * homeDiscountCommodityModels : [{"id":"5d25536e06ebb70001e6f0da","activityId":"5d25440bf4c4b10001633db8","goodsId":"5d16011f3cfaa0000176bced","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190628/440c0108ba164a638e7d513ceed5a252.jpg","commodityId":"5d1ffce9f88dde000147c891","commodityName":"HSTYLE2019夏新款少女蕾丝连衣裙","salePrice":20000,"marketPrice":30000,"salesVolume":null,"discount":5.7,"rateOne":null},{"id":"5d25536e06ebb70001e6f0db","activityId":"5d25440bf4c4b10001633db8","goodsId":"5d16011f3cfaa0000176bced","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190628/440c0108ba164a638e7d513ceed5a252.jpg","commodityId":"5d1ffce9f88dde000147c892","commodityName":"HSTYLE2019夏新款少女蕾丝连衣裙","salePrice":18000,"marketPrice":26000,"salesVolume":null,"discount":7.4,"rateOne":null},{"id":"5d25536e06ebb70001e6f0dc","activityId":"5d25440bf4c4b10001633db8","goodsId":"5d16011f3cfaa0000176bced","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190628/440c0108ba164a638e7d513ceed5a252.jpg","commodityId":"5d1ffce9f88dde000147c893","commodityName":"HSTYLE2019夏新款少女蕾丝连衣裙","salePrice":20000,"marketPrice":30000,"salesVolume":null,"discount":5.1,"rateOne":null},{"id":"5d25c789cc814e0001b3e4f5","activityId":"5d25a739cc814e0001b3e4e2","goodsId":"5d16011f3cfaa0000176bced","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190628/440c0108ba164a638e7d513ceed5a252.jpg","commodityId":"5d1ffce9f88dde000147c892","commodityName":"HSTYLE2019夏新款少女蕾丝连衣裙","salePrice":18000,"marketPrice":26000,"salesVolume":null,"discount":7,"rateOne":null},{"id":"5d27350833986f000132ab15","activityId":"5d272e91093d610001dc4a6b","goodsId":"5d27321e37fdc000014a33f1","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/21befdbe5f1d4f63a9fd141cbc9eebd1.jpg","commodityId":"5d27321e37fdc000014a33fc","commodityName":"超级闪亮蹦迪短裤","salePrice":15000,"marketPrice":20000,"salesVolume":null,"discount":8,"rateOne":null}]
         */

        private int activityType;
        private String bannerUrl;
        private String activityId;
        private String activityName;
        private String activityDesc;
        private List<HomeDiscountCommodityModelsBean> homeDiscountCommodityModels;

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

        public String getActivityDesc() {
            return activityDesc;
        }

        public void setActivityDesc(String activityDesc) {
            this.activityDesc = activityDesc;
        }

        public List<HomeDiscountCommodityModelsBean> getHomeDiscountCommodityModels() {
            return homeDiscountCommodityModels;
        }

        public void setHomeDiscountCommodityModels(List<HomeDiscountCommodityModelsBean> homeDiscountCommodityModels) {
            this.homeDiscountCommodityModels = homeDiscountCommodityModels;
        }

        public static class HomeDiscountCommodityModelsBean {
            /**
             * id : 5d25536e06ebb70001e6f0da
             * activityId : 5d25440bf4c4b10001633db8
             * goodsId : 5d16011f3cfaa0000176bced
             * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190628/440c0108ba164a638e7d513ceed5a252.jpg
             * commodityId : 5d1ffce9f88dde000147c891
             * commodityName : HSTYLE2019夏新款少女蕾丝连衣裙
             * salePrice : 20000
             * marketPrice : 30000
             * salesVolume : null
             * discount : 5.7
             * rateOne : null
             */

            private String id;
            private String activityId;
            private String goodsId;
            private String goodsImageUrl;
            private String commodityId;
            private String commodityName;
            private int salePrice;
            private int marketPrice;
            private int salesVolume;
            private double discount;
            private float rateThree;
            private float rateOne;//第一件折扣
            private float rateTwo;//第二件折扣
            private int commodityPriceOne;
            private int commodityPriceTwo;
            private int commodityPriceThree;
            private int shareAmount;//分享赚钱数量

            public int getShareMoney() {
                return shareAmount;
            }

            public void setShareMoney(int shareMoney) {
                this.shareAmount = shareMoney;
            }

            public float getRateOne() {
                return rateOne;
            }

            public void setRateOne(float rateOne) {
                this.rateOne = rateOne;
            }

            public float getRateTwo() {
                return rateTwo;
            }

            public void setRateTwo(float rateTwo) {
                this.rateTwo = rateTwo;
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

            public int getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(int salePrice) {
                this.salePrice = salePrice;
            }

            public int getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(int marketPrice) {
                this.marketPrice = marketPrice;
            }

            public int getSalesVolume() {
                return salesVolume;
            }

            public void setSalesVolume(int salesVolume) {
                this.salesVolume = salesVolume;
            }

            public double getDiscount() {
                return discount;
            }

            public void setDiscount(double discount) {
                this.discount = discount;
            }

            public float getRateThree() {
                return rateThree;
            }

            public void setRateThree(float rateThree) {
                this.rateThree = rateThree;
            }
        }
    }


    public static  class HomePageGlobalBuyerActivityModelBean{
        /**
         * activityType : 2
         * bannerUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190801/a484361834a94b1592c8ee23925c00b0.png
         * activityId : 5d42aa5d98cd4400017fad25
         * activityName : 全球买手
         * activityDesc : 我是活动描述
         * homeGlobalBuyerCommodityModels : [{"id":"5d42c0be98cd4400017fad45","activityId":"5d42aa5d98cd4400017fad25","commodityName":"袁小寒的商品","salePrice":20,"commodityPrice":16,"deductionCouponAmount":20,"goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190801/fdd2ff06a43e4661808d0ab7597465cb.jpg"},{"id":"5d5637aba8dc9f0001d9a20c","activityId":"5d42aa5d98cd4400017fad25","commodityName":"YYTest22","salePrice":12100,"commodityPrice":11858,"deductionCouponAmount":12100,"goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190816/439028ab72fe449aaa8b32b1d8e9552a.jpg"},{"id":"5d42b10b98cd4400017fad36","activityId":"5d42aa5d98cd4400017fad25","commodityName":"四叶草吊坠","salePrice":15000,"commodityPrice":13500,"deductionCouponAmount":15000,"goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190801/b42cf299efe542a1ad290916009c168e.png"},{"id":"5d42c6c098cd4400017fad49","activityId":"5d42aa5d98cd4400017fad25","commodityName":"安踏漫威联名鞋美国队长复联marvel","salePrice":81000,"commodityPrice":64800,"deductionCouponAmount":81000,"goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190801/ccae342395b34cd9880e574c5d0dd7a2.jpg"},{"id":"5d42c6c398cd4400017fad4a","activityId":"5d42aa5d98cd4400017fad25","commodityName":"安踏可口可乐联名鞋","salePrice":50000,"commodityPrice":40000,"deductionCouponAmount":50000,"goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190801/29f07957eee9431cb1f507fce2a8e053.jpg"}]
         */

        private int activityType;
        private String bannerUrl;
        private String activityId;
        private String activityName;
        private String activityDesc;
        private List<HomeGlobalBuyerCommodityModelsBean> homeGlobalBuyerCommodityModels;

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

        public String getActivityDesc() {
            return activityDesc;
        }

        public void setActivityDesc(String activityDesc) {
            this.activityDesc = activityDesc;
        }

        public List<HomeGlobalBuyerCommodityModelsBean> getHomeGlobalBuyerCommodityModels() {
            return homeGlobalBuyerCommodityModels;
        }

        public void setHomeGlobalBuyerCommodityModels(List<HomeGlobalBuyerCommodityModelsBean> homeGlobalBuyerCommodityModels) {
            this.homeGlobalBuyerCommodityModels = homeGlobalBuyerCommodityModels;
        }

        public static class HomeGlobalBuyerCommodityModelsBean {
            /**
             * id : 5d42c0be98cd4400017fad45
             * activityId : 5d42aa5d98cd4400017fad25
             * commodityName : 袁小寒的商品
             * salePrice : 20
             * commodityPrice : 16
             * deductionCouponAmount : 20
             * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190801/fdd2ff06a43e4661808d0ab7597465cb.jpg
             */

            private String id;
            private String activityId;
            private String commodityName;
            private int salePrice;
            private int commodityPrice;
            private int deductionCouponAmount;
            private String goodsImageUrl;

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

            public String getCommodityName() {
                return commodityName;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public int getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(int salePrice) {
                this.salePrice = salePrice;
            }

            public int getCommodityPrice() {
                return commodityPrice;
            }

            public void setCommodityPrice(int commodityPrice) {
                this.commodityPrice = commodityPrice;
            }

            public int getDeductionCouponAmount() {
                return deductionCouponAmount;
            }

            public void setDeductionCouponAmount(int deductionCouponAmount) {
                this.deductionCouponAmount = deductionCouponAmount;
            }

            public String getGoodsImageUrl() {
                return goodsImageUrl;
            }

            public void setGoodsImageUrl(String goodsImageUrl) {
                this.goodsImageUrl = goodsImageUrl;
            }
        }
    }
}
