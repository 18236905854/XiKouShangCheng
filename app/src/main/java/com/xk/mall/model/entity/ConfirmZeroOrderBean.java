package com.xk.mall.model.entity;

/**
 * 确认0元拍订单
 */
public class ConfirmZeroOrderBean {

    /**
     * orderNo : AU20190802180500000735
     * payAmount : 0
     * commoditySalePrice : 3000000
     * postage : 0
     * orderTime : 2019-08-02 18:05:13
     * insteadPaymentId : null
     * insteadPaymentAccount : null
     * address : null
     * commodityAuctionPrice : 3000001
     * goods : {"goodsId":"5d43a1247d5ba80001ffa018","goodsCode":null,"goodsName":"浪琴","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190802/5bbf831ad3424b31a5fc69e00117d8ba.jpg","commodityId":"5d43a1247d5ba80001ffa01b","commoditySpec":"s","commodityModel":"s","commodityQuantity":1,"postage":null}
     */

    private String orderNo;
    private int payAmount;
    private int commoditySalePrice;
    private int postage;
    private String orderTime;
    private Object insteadPaymentId;
    private Object insteadPaymentAccount;
    private Object address;
    private int commodityAuctionPrice;
    private GoodsBean goods;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
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

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Object getInsteadPaymentId() {
        return insteadPaymentId;
    }

    public void setInsteadPaymentId(Object insteadPaymentId) {
        this.insteadPaymentId = insteadPaymentId;
    }

    public Object getInsteadPaymentAccount() {
        return insteadPaymentAccount;
    }

    public void setInsteadPaymentAccount(Object insteadPaymentAccount) {
        this.insteadPaymentAccount = insteadPaymentAccount;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public int getCommodityAuctionPrice() {
        return commodityAuctionPrice;
    }

    public void setCommodityAuctionPrice(int commodityAuctionPrice) {
        this.commodityAuctionPrice = commodityAuctionPrice;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * goodsId : 5d43a1247d5ba80001ffa018
         * goodsCode : null
         * goodsName : 浪琴
         * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190802/5bbf831ad3424b31a5fc69e00117d8ba.jpg
         * commodityId : 5d43a1247d5ba80001ffa01b
         * commoditySpec : s
         * commodityModel : s
         * commodityQuantity : 1
         * postage : null
         */

        private String goodsId;
        private Object goodsCode;
        private String goodsName;
        private String goodsImageUrl;
        private String commodityId;
        private String commoditySpec;
        private String commodityModel;
        private int commodityQuantity;
        private Object postage;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public Object getGoodsCode() {
            return goodsCode;
        }

        public void setGoodsCode(Object goodsCode) {
            this.goodsCode = goodsCode;
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

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
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

        public Object getPostage() {
            return postage;
        }

        public void setPostage(Object postage) {
            this.postage = postage;
        }
    }
}
