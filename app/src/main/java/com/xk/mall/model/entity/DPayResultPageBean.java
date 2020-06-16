package com.xk.mall.model.entity;

import java.util.List;

/**
 * @ClassName: DPayResultPageBean
 * @Description: 代付页面请求结果Bean
 * @Author: 卿凯
 * @Date: 2019/8/30/030 19:04
 * @Version: 1.0
 */
public class DPayResultPageBean {


    /**
     * pageCount : 0
     * result : [{"hasPaid":0,"notPaid":0,"orderPageUnifiedProcessingModelList":[{"commodityModel":"","commodityQuantity":0,"commoditySalePrice":0,"commoditySpec":"","couponAmount":0,"deductionCouponAmount":0,"goodsImageUrl":"","goodsName":"","id":"","merchantName":"","orderNo":"","paid":0,"partnerId":"","payAmount":0,"postage":0,"state":0}]}]
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
         * hasPaid : 0
         * notPaid : 0
         * orderPageUnifiedProcessingModelList : [{"commodityModel":"","commodityQuantity":0,"commoditySalePrice":0,"commoditySpec":"","couponAmount":0,"deductionCouponAmount":0,"goodsImageUrl":"","goodsName":"","id":"","merchantName":"","orderNo":"","paid":0,"partnerId":"","payAmount":0,"postage":0,"state":0}]
         */

        private int hasPaid;
        private int notPaid;
        private List<OrderPageUnifiedProcessingModelListBean> insteadPaymentOrderPageModels;

        public int getHasPaid() {
            return hasPaid;
        }

        public void setHasPaid(int hasPaid) {
            this.hasPaid = hasPaid;
        }

        public int getNotPaid() {
            return notPaid;
        }

        public void setNotPaid(int notPaid) {
            this.notPaid = notPaid;
        }

        public List<OrderPageUnifiedProcessingModelListBean> getOrderPageUnifiedProcessingModelList() {
            return insteadPaymentOrderPageModels;
        }

        public void setOrderPageUnifiedProcessingModelList(List<OrderPageUnifiedProcessingModelListBean> orderPageUnifiedProcessingModelList) {
            this.insteadPaymentOrderPageModels = orderPageUnifiedProcessingModelList;
        }

        public static class OrderPageUnifiedProcessingModelListBean {
            /**
             * commodityModel :
             * commodityQuantity : 0
             * commoditySalePrice : 0
             * commoditySpec :
             * couponAmount : 0
             * deductionCouponAmount : 0
             * goodsImageUrl :
             * goodsName :
             * id :
             * merchantName :
             * orderNo :
             * paid : 0
             * partnerId :
             * payAmount : 0
             * postage : 0
             * state : 0
             */

            private String commodityModel;
            private int commodityQuantity;
            private int commoditySalePrice;
            private String commoditySpec;
            private int couponAmount;
            private int deductionCouponAmount;
            private String goodsImageUrl;
            private String goodsName;
            private String id;
            private String merchantName;
            private String orderNo;
            private int paid;
            private String partnerId;
            private int payAmount;
            private int postage;
            private int state;
            private int waitPaymentTime;//支付失效时长
            private String orderTime;//下单时间
            private String payTime;//支付时间
            private String buyerNickName;//发起人名称

            public String getBuyerNickName() {
                return buyerNickName;
            }

            public void setBuyerNickName(String buyerNickName) {
                this.buyerNickName = buyerNickName;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public int getPayInvalidTime() {
                return waitPaymentTime;
            }

            public void setPayInvalidTime(int payInvalidTime) {
                this.waitPaymentTime = payInvalidTime;
            }

            public String getOrderTime() {
                return orderTime;
            }

            public void setOrderTime(String orderTime) {
                this.orderTime = orderTime;
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

            public String getCommoditySpec() {
                return commoditySpec;
            }

            public void setCommoditySpec(String commoditySpec) {
                this.commoditySpec = commoditySpec;
            }

            public int getCouponAmount() {
                return couponAmount;
            }

            public void setCouponAmount(int couponAmount) {
                this.couponAmount = couponAmount;
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

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMerchantName() {
                return merchantName;
            }

            public void setMerchantName(String merchantName) {
                this.merchantName = merchantName;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public int getPaid() {
                return paid;
            }

            public void setPaid(int paid) {
                this.paid = paid;
            }

            public String getPartnerId() {
                return partnerId;
            }

            public void setPartnerId(String partnerId) {
                this.partnerId = partnerId;
            }

            public int getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(int payAmount) {
                this.payAmount = payAmount;
            }

            public int getPostage() {
                return postage;
            }

            public void setPostage(int postage) {
                this.postage = postage;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }
        }
    }
}
