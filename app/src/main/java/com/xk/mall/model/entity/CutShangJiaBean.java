package com.xk.mall.model.entity;

import java.util.List;

/**
 * 喜立得 上架商品
 */
public class CutShangJiaBean {
    /**
     * result : [{"id":"5d2730a01557650001e74320","activityId":"5d26fcac7e12330001743c54","merchantId":"5d184ec326cc7500014a9bf2","goodsId":"5d27176937fdc000014a33d4","commodityId":"5d27176937fdc000014a33de","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg","commodityName":"明国旗袍","salePrice":30000,"marketPrice":45000,"commodityPrice":150000,"stock":6,"reservePriceRate":10,"bargainNumber":10,"salesNumber":0},{"id":"5d27309e1557650001e7431f","activityId":"5d26fcac7e12330001743c54","merchantId":"5d184ec326cc7500014a9bf2","goodsId":"5d26a6023ae2f500012b700d","commodityId":"5d26d0bc3ae2f500012b7025","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/e9a76aa72b4f4d3abed2e7623b123a14.jpg","commodityName":"小米9-战斗天使","salePrice":309900,"marketPrice":329900,"commodityPrice":2800000,"stock":2,"reservePriceRate":10,"bargainNumber":10,"salesNumber":0}]
     * totalCount : 2
     * pageCount : 1
     */
    private int totalCount;
    private int pageCount;
    private List<ResultBean> result;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 5d2730a01557650001e74320
         * activityId : 5d26fcac7e12330001743c54
         * merchantId : 5d184ec326cc7500014a9bf2
         * goodsId : 5d27176937fdc000014a33d4
         * commodityId : 5d27176937fdc000014a33de
         * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg
         * commodityName : 明国旗袍
         * salePrice : 30000
         * marketPrice : 45000
         * commodityPrice : 150000
         * stock : 6
         * reservePriceRate : 10
         * bargainNumber : 10
         * salesNumber : 0
         */

        private String id;
        private String activityId;
        private String merchantId;
        private String goodsId;
        private String commodityId;
        private String goodsImageUrl;
        private String commodityName;
        private int salePrice;
        private int marketPrice;
        private int commodityPrice;
        private int stock;
        private int reservePriceRate;//折扣
        private int bargainNumber;
        private int salesNumber;
        private int bargainStatus;//1：未发起砍价，2：已砍过价，3：正在砍价
        private int bargainEffectiveTimed;//砍价申请有效时长; 小时

        public int getBargainEffectiveTimed() {
            return bargainEffectiveTimed;
        }

        public void setBargainEffectiveTimed(int bargainEffectiveTimed) {
            this.bargainEffectiveTimed = bargainEffectiveTimed;
        }

        public int getBargainStatus() {
            return bargainStatus;
        }

        public void setBargainStatus(int bargainStatus) {
            this.bargainStatus = bargainStatus;
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

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
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

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getReservePriceRate() {
            return reservePriceRate;
        }

        public void setReservePriceRate(int reservePriceRate) {
            this.reservePriceRate = reservePriceRate;
        }

        public int getBargainNumber() {
            return bargainNumber;
        }

        public void setBargainNumber(int bargainNumber) {
            this.bargainNumber = bargainNumber;
        }

        public int getSalesNumber() {
            return salesNumber;
        }

        public void setSalesNumber(int salesNumber) {
            this.salesNumber = salesNumber;
        }
    }
}
