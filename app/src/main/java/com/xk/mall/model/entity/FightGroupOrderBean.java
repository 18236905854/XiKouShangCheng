package com.xk.mall.model.entity;

import java.util.List;

/**
 * 定制拼团订单bean
 */
public class FightGroupOrderBean {

    /**
     * result : [{"id":"5d2d91f6cd920c00013a0b53","orderNo":"FG20190716165900000668","merchantName":null,"payAmount":30005,"state":1,"goodsName":"明国旗袍","goodsImageUrl":"goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg","commoditySpec":"均码","commodityModel":"白色","commodityQuantity":1,"commoditySalePrice":null},{"id":"5d2d9190cd920c00013a0b50","orderNo":"FG20190716165700000377","merchantName":null,"payAmount":30005,"state":1,"goodsName":"明国旗袍","goodsImageUrl":"goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg","commoditySpec":"均码","commodityModel":"白色","commodityQuantity":1,"commoditySalePrice":null},{"id":"5d2d9180cd920c00013a0b4e","orderNo":"FG20190716165700000292","merchantName":null,"payAmount":30005,"state":1,"goodsName":"明国旗袍","goodsImageUrl":"goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg","commoditySpec":"均码","commodityModel":"白色","commodityQuantity":1,"commoditySalePrice":null},{"id":"5d2d9176cd920c00013a0b4c","orderNo":"FG20190716165700000801","merchantName":null,"payAmount":30005,"state":1,"goodsName":"明国旗袍","goodsImageUrl":"goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg","commoditySpec":"均码","commodityModel":"白色","commodityQuantity":1,"commoditySalePrice":null},{"id":"5d2d8d3fcd920c00013a0b4a","orderNo":"FG20190716163900000834","merchantName":null,"payAmount":30005,"state":1,"goodsName":"明国旗袍","goodsImageUrl":"goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg","commoditySpec":"均码","commodityModel":"白色","commodityQuantity":1,"commoditySalePrice":null},{"id":"5d2d8d37cd920c00013a0b48","orderNo":"FG20190716163900000966","merchantName":null,"payAmount":30005,"state":1,"goodsName":"明国旗袍","goodsImageUrl":"goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg","commoditySpec":"均码","commodityModel":"白色","commodityQuantity":1,"commoditySalePrice":null},{"id":"5d2d8cebcd920c00013a0b46","orderNo":"FG20190716163800000787","merchantName":null,"payAmount":30005,"state":1,"goodsName":"明国旗袍","goodsImageUrl":"goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg","commoditySpec":"均码","commodityModel":"白色","commodityQuantity":1,"commoditySalePrice":null},{"id":"5d2d8bc9cd920c00013a0b44","orderNo":"FG20190716163300000193","merchantName":null,"payAmount":30005,"state":1,"goodsName":"明国旗袍","goodsImageUrl":"goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg","commoditySpec":"均码","commodityModel":"白色","commodityQuantity":1,"commoditySalePrice":null}]
     * totalCount : 8
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
         * id : 5d2d91f6cd920c00013a0b53
         * orderNo : FG20190716165900000668
         * merchantName : null
         * payAmount : 30005
         * state : 1
         * goodsName : 明国旗袍
         * goodsImageUrl : goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg
         * commoditySpec : 均码
         * commodityModel : 白色
         * commodityQuantity : 1
         * commoditySalePrice : null
         */

        private String id;
        private String orderNo;
        private String merchantName;
        private int payAmount;
        private int state;
        private String goodsName;
        private String goodsImageUrl;
        private String commoditySpec;
        private String commodityModel;
        private int commodityQuantity;
        private int commoditySalePrice;
        private int postage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public int getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(int payAmount) {
            this.payAmount = payAmount;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsImageUrl() {
            return goodsImageUrl;
        }

        public void setGoodsImageUrl(String goodsImageUrl) {
            this.goodsImageUrl = goodsImageUrl;
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

        public int getCommodityQuantity() {
            return commodityQuantity;
        }

        public void setCommodityQuantity(int commodityQuantity) {
            this.commodityQuantity = commodityQuantity;
        }

        public int getCommoditySalePrice() {
            return commoditySalePrice;
        }

        public void setCommoditySalePrice(int commoditySalePrice) {
            this.commoditySalePrice = commoditySalePrice;
        }

        public int getPostage() {
            return postage;
        }

        public void setPostage(int postage) {
            this.postage = postage;
        }
    }
}
