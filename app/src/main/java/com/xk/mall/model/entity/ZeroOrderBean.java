package com.xk.mall.model.entity;

import java.util.List;

/**
 * 0元拍订单 bean
 */
public class ZeroOrderBean {

    /**
     * result : [{"id":"5d302f01566b90000193214c","orderNo":"AU20190718163400000417","merchantName":"Min4","payAmount":0,"state":1,"goodsName":"安踏漫威联名鞋美国队长复联marvel ","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190718/e32d241154184a3cbcfcaed2db8a3cdc.jpg","commoditySpec":"40","commodityModel":"美国队长","commodityQuantity":1,"commoditySalePrice":45000,"commodityAuctionPrice":null,"timesNum":4},{"id":"5d302efa566b900001932149","orderNo":"AU20190718163400000458","merchantName":"Min4","payAmount":0,"state":1,"goodsName":"安踏漫威联名鞋美国队长复联marvel ","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190718/e32d241154184a3cbcfcaed2db8a3cdc.jpg","commoditySpec":"40","commodityModel":"美国队长","commodityQuantity":1,"commoditySalePrice":45000,"commodityAuctionPrice":null,"timesNum":4},{"id":"5d302ecc566b900001932146","orderNo":"AU20190718163300000525","merchantName":"Min4","payAmount":0,"state":1,"goodsName":"安踏漫威联名鞋美国队长复联marvel ","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190718/e32d241154184a3cbcfcaed2db8a3cdc.jpg","commoditySpec":"40","commodityModel":"美国队长","commodityQuantity":1,"commoditySalePrice":45000,"commodityAuctionPrice":null,"timesNum":4},{"id":"5d302ec6566b900001932143","orderNo":"AU20190718163300000133","merchantName":"Min4","payAmount":0,"state":1,"goodsName":"安踏漫威联名鞋美国队长复联marvel ","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190718/e32d241154184a3cbcfcaed2db8a3cdc.jpg","commoditySpec":"40","commodityModel":"美国队长","commodityQuantity":1,"commoditySalePrice":45000,"commodityAuctionPrice":null,"timesNum":4},{"id":"5d302e49566b900001932140","orderNo":"AU20190718163100000293","merchantName":"Min4","payAmount":0,"state":1,"goodsName":"安踏漫威联名鞋美国队长复联marvel ","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190718/e32d241154184a3cbcfcaed2db8a3cdc.jpg","commoditySpec":"40","commodityModel":"美国队长","commodityQuantity":1,"commoditySalePrice":45000,"commodityAuctionPrice":null,"timesNum":4},{"id":"5d302e2c566b90000193213d","orderNo":"AU20190718163000000039","merchantName":"Min4","payAmount":0,"state":1,"goodsName":"安踏漫威联名鞋美国队长复联marvel ","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190718/e32d241154184a3cbcfcaed2db8a3cdc.jpg","commoditySpec":"40","commodityModel":"美国队长","commodityQuantity":1,"commoditySalePrice":45000,"commodityAuctionPrice":null,"timesNum":4},{"id":"5d302e23566b90000193213a","orderNo":"AU20190718163000000341","merchantName":"Min4","payAmount":0,"state":1,"goodsName":"安踏漫威联名鞋美国队长复联marvel ","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190718/e32d241154184a3cbcfcaed2db8a3cdc.jpg","commoditySpec":"40","commodityModel":"美国队长","commodityQuantity":1,"commoditySalePrice":45000,"commodityAuctionPrice":null,"timesNum":4},{"id":"5d302c79566b900001932137","orderNo":"AU20190718162300000906","merchantName":"Min4","payAmount":0,"state":1,"goodsName":"安踏漫威联名鞋美国队长复联marvel ","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190718/e32d241154184a3cbcfcaed2db8a3cdc.jpg","commoditySpec":"40","commodityModel":"美国队长","commodityQuantity":1,"commoditySalePrice":45000,"commodityAuctionPrice":null,"timesNum":4}]
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
         * id : 5d302f01566b90000193214c
         * orderNo : AU20190718163400000417
         * merchantName : Min4
         * payAmount : 0
         * state : 1
         * goodsName : 安踏漫威联名鞋美国队长复联marvel
         * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190718/e32d241154184a3cbcfcaed2db8a3cdc.jpg
         * commoditySpec : 40
         * commodityModel : 美国队长
         * commodityQuantity : 1
         * commoditySalePrice : 45000
         * commodityAuctionPrice : null
         * timesNum : 4
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
        private int commodityAuctionPrice;
        private int timesNum;
        private int postage;//邮费
        private String receiptAddressRef;//收货人信息ID

        public String getReceiptAddressRef() {
            return receiptAddressRef;
        }

        public void setReceiptAddressRef(String receiptAddressRef) {
            this.receiptAddressRef = receiptAddressRef;
        }

        public int getPostage() {
            return postage;
        }

        public void setPostage(int postage) {
            this.postage = postage;
        }

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

        public int getCommodityAuctionPrice() {
            return commodityAuctionPrice;
        }

        public void setCommodityAuctionPrice(int commodityAuctionPrice) {
            this.commodityAuctionPrice = commodityAuctionPrice;
        }

        public int getTimesNum() {
            return timesNum;
        }

        public void setTimesNum(int timesNum) {
            this.timesNum = timesNum;
        }
    }
}
