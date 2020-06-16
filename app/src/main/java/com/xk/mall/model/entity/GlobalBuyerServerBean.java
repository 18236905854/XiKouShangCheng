package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName GlobalBuyerServerBean
 * Description 全球买手页面返回数据Bean
 * Author 卿凯
 * Date 2019/7/7/007
 * Version V1.0
 */
public class GlobalBuyerServerBean {

    private List<BannerBean> bannerList;
    private List<SectionCommodityListBean> sectionCommodityList;

    public List<BannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<SectionCommodityListBean> getSectionCommodityList() {
        return sectionCommodityList;
    }

    public void setSectionCommodityList(List<SectionCommodityListBean> sectionCommodityList) {
        this.sectionCommodityList = sectionCommodityList;
    }

    public static class SectionCommodityListBean {
        /**
         * categoryId :
         * categoryName :
         * commodityList : {"pageCount":0,"result":[{"activityGoodsId":"","activityId":"","bargainNumber":0,"bargainedNum":0,"commodityId":"","commodityName":"","commodityPrice":0,"couponValue":0,"discount":0,"goodsId":"","goodsImageUrl":"","salePrice":0}],"totalCount":0}
         */

        private String categoryId;
        private String categoryName;
        private CommodityListBean commodityList;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public CommodityListBean getCommodityList() {
            return commodityList;
        }

        public void setCommodityList(CommodityListBean commodityList) {
            this.commodityList = commodityList;
        }

        public static class CommodityListBean {
            /**
             * pageCount : 0
             * result : [{"activityGoodsId":"","activityId":"","bargainNumber":0,"bargainedNum":0,"commodityId":"","commodityName":"","commodityPrice":0,"couponValue":0,"discount":0,"goodsId":"","goodsImageUrl":"","salePrice":0}]
             * totalCount : 0
             */

            private int pageCount;
            private int totalCount;
            private List<ResultBean> result;

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public List<ResultBean> getResult() {
                return result;
            }

            public void setResult(List<ResultBean> result) {
                this.result = result;
            }

            public static class ResultBean {
                /**
                 * activityGoodsId :
                 * activityId :
                 * bargainNumber : 0
                 * bargainedNum : 0
                 * commodityId :
                 * commodityName :
                 * commodityPrice : 0
                 * couponValue : 0
                 * discount : 0
                 * goodsId :
                 * goodsImageUrl :
                 * salePrice : 0
                 */

                private String activityGoodsId;
                private String activityId;
                private int bargainNumber;
                private int bargainedNum;
                private String commodityId;
                private String commodityName;
                private int commodityPrice;
                private int couponValue;
                private int discount;
                private String goodsId;
                private String goodsImageUrl;
                private int salePrice;
                private int stock;

                public int getStock() {
                    return stock;
                }

                public void setStock(int stock) {
                    this.stock = stock;
                }

                public String getActivityGoodsId() {
                    return activityGoodsId;
                }

                public void setActivityGoodsId(String activityGoodsId) {
                    this.activityGoodsId = activityGoodsId;
                }

                public String getActivityId() {
                    return activityId;
                }

                public void setActivityId(String activityId) {
                    this.activityId = activityId;
                }

                public int getBargainNumber() {
                    return bargainNumber;
                }

                public void setBargainNumber(int bargainNumber) {
                    this.bargainNumber = bargainNumber;
                }

                public int getBargainedNum() {
                    return bargainedNum;
                }

                public void setBargainedNum(int bargainedNum) {
                    this.bargainedNum = bargainedNum;
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

                public int getDiscount() {
                    return discount;
                }

                public void setDiscount(int discount) {
                    this.discount = discount;
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

                public int getSalePrice() {
                    return salePrice;
                }

                public void setSalePrice(int salePrice) {
                    this.salePrice = salePrice;
                }
            }
        }
    }
}
