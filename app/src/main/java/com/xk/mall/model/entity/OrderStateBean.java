package com.xk.mall.model.entity;

import java.util.List;

/**
 * @ClassName: OrderStateBean
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class OrderStateBean {

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
         * activityModule : 0
         * alwaysSelectMg : 0
         * commodityModel :
         * commodityQuantity : 0
         * commoditySalePrice : 0
         * commoditySpec :
         * consignmentType : 0
         * goodsImageUrl :
         * goodsName :
         * id :
         * isDirect : 0
         * orderNo :
         * paid : 0
         * payAmount : 0
         * postage : 0
         * sevenDaysNoReasonReturn : 0
         * state : 0
         * tradeNo :
         * whetherToAllow : 0
         */

        private int activityModule;//1:买一赠二(吾G)，2: 全球买手, 3：0元竞拍 4:多买多折，5:砍价得红包，6:定制拼团，8：新人专区
        private int alwaysSelectMg;//是否必须选中分享到吾G 0：不必须选中，1：必须选中
        private String commodityModel;//商品型号
        private int commodityQuantity;//购买数量
        private int commoditySalePrice;//销售价;0元抢拍活动对应的是市场价
        private String commoditySpec;//商品规格
        private int consignmentType;//全球买手的寄卖方式(1：定向寄卖 2：寄卖到吾G NULL或者3：所有寄卖方式)
        private String goodsImageUrl;//商品图片
        private String goodsName;//商品名称
        private String id;//订单ID
        private int isDirect;//全球买手寄卖能否选择定向(1：可定向 2：不可定向)
        private int deductionCouponAmount;//优惠券抵扣金额(全球买手)
        private int couponAmount;//赠券金额(吾G使用字段)
        private String partnerId;//上级合伙人ID
        private String orderNo;//订单号
        private String merchantName;//商家名称
        private int paid;//是否代付;0-否;1-是
        private int payAmount;//应付金额
        private int commodityAuctionPrice;//竞拍价，0元拍订单属性
        private int postage;//邮费
        private int sevenDaysNoReasonReturn;//7天无理由退货，0：否，1是
        private int state;//订单状态:1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖;12-已付款
        private String tradeNo;//主订单号
        private int timesNum;//出价次数
        private String receiptAddressRef;//0元抢是否有收货地址
        private int whetherToAllow;//全球买手寄卖能否选择寄卖(1：可以寄卖 2：不可寄卖)
        private List<OrderStateBean.ResultBean> childOrderItems;//多买多折子订单数据

        public int getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(int couponAmount) {
            this.couponAmount = couponAmount;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public int getDeductionCouponAmount() {
            return deductionCouponAmount;
        }

        public void setDeductionCouponAmount(int deductionCouponAmount) {
            this.deductionCouponAmount = deductionCouponAmount;
        }

        public String getReceiptAddressRef() {
            return receiptAddressRef;
        }

        public void setReceiptAddressRef(String receiptAddressRef) {
            this.receiptAddressRef = receiptAddressRef;
        }

        public int getTimesNum() {
            return timesNum;
        }

        public void setTimesNum(int timesNum) {
            this.timesNum = timesNum;
        }

        public List<OrderStateBean.ResultBean> getChildOrderItems() {
            return childOrderItems;
        }

        public void setChildOrderItems(List<OrderStateBean.ResultBean> childOrderItems) {
            this.childOrderItems = childOrderItems;
        }

        public int getCommodityAuctionPrice() {
            return commodityAuctionPrice;
        }

        public void setCommodityAuctionPrice(int commodityAuctionPrice) {
            this.commodityAuctionPrice = commodityAuctionPrice;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public int getActivityModule() {
            return activityModule;
        }

        public void setActivityModule(int activityModule) {
            this.activityModule = activityModule;
        }

        public int getAlwaysSelectMg() {
            return alwaysSelectMg;
        }

        public void setAlwaysSelectMg(int alwaysSelectMg) {
            this.alwaysSelectMg = alwaysSelectMg;
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

        public int getConsignmentType() {
            return consignmentType;
        }

        public void setConsignmentType(int consignmentType) {
            this.consignmentType = consignmentType;
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

        public int getIsDirect() {
            return isDirect;
        }

        public void setIsDirect(int isDirect) {
            this.isDirect = isDirect;
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

        public int getSevenDaysNoReasonReturn() {
            return sevenDaysNoReasonReturn;
        }

        public void setSevenDaysNoReasonReturn(int sevenDaysNoReasonReturn) {
            this.sevenDaysNoReasonReturn = sevenDaysNoReasonReturn;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public int getWhetherToAllow() {
            return whetherToAllow;
        }

        public void setWhetherToAllow(int whetherToAllow) {
            this.whetherToAllow = whetherToAllow;
        }
    }

}
