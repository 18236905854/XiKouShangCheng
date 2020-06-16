package com.xk.mall.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 多买多折购物车 实体bean
 */
public class ManyCartsBean implements Serializable {
    /**
     * merchantId : 888
     * list : [{"id":"5d24434c49b4cc0001597575","buyerUserId":"5d1abda2f7748700013e5ac4","commodityId":"5d1f06aef88dde000147c85b","commodityName":"qwweM红色","commoditySpec":"红色","commodityModel":"M","commodityUnit":"1","salePrice":20000,"activityPrice":20000,"discount":0,"buyerNumber":1,"orderNo":null},{"id":"5d24435649b4cc0001597576","buyerUserId":"5d1abda2f7748700013e5ac4","commodityId":"5d1f06aef88dde000147c861","commodityName":"qwweL红色","commoditySpec":"红色","commodityModel":"L","commodityUnit":"1","salePrice":20000,"activityPrice":20000,"discount":0,"buyerNumber":1,"orderNo":null}]
     * merchantName : 喜扣商家
     */
    private String merchantId;
    private String merchantName;
    private int postage = 0;
    private String remarks;//订单备注
    private List<ListBean> list;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getPostage() {
        return postage;
    }

    public void setPostage(int postage) {
        this.postage = postage;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * id : 5d24434c49b4cc0001597575
         * buyerUserId : 5d1abda2f7748700013e5ac4
         * commodityId : 5d1f06aef88dde000147c85b
         * commodityName : qwweM红色
         * commoditySpec : 红色
         * commodityModel : M
         * commodityUnit : 1
         * salePrice : 20000
         * activityPrice : 20000
         * marketPrice://市场价
         * discount : 0.0
         * buyerNumber : 1
         * orderNo : null
         */

        private String activityGoodsId;
        private String id;
        private String buyerUserId;
        private String commodityId;
        private String activityId;
        private String merchantId;//商家ID
        private String commodityName;
        private String commoditySpec;
        private String commodityModel;
        private String commodityUnit;
        private int salePrice;
        private int marketPrice;
        private int activityPrice;
        private int discount;
        private int buyerNumber;
        private int orderNo;
        private String goodsImageUrl;//商品图片
        private String goodsId;//商品id
        private boolean isCheck;//是否选中
        private int position = 0;//当前选中的位置
        private float discountMin = 0;//最小的折扣
        private String remarks;//订单备注

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getActivityGoodsId() {
            return activityGoodsId;
        }

        public void setActivityGoodsId(String activityGoodsId) {
            this.activityGoodsId = activityGoodsId;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public float getDiscountMin() {
            return discountMin;
        }

        public void setDiscountMin(float discountMin) {
            this.discountMin = discountMin;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getGoodsImageUrl() {
            return goodsImageUrl;
        }

        public void setGoodsImageUrl(String goodsImageUrl) {
            this.goodsImageUrl = goodsImageUrl;
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

        public String getBuyerUserId() {
            return buyerUserId;
        }

        public void setBuyerUserId(String buyerUserId) {
            this.buyerUserId = buyerUserId;
        }

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

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
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

        public String getCommodityUnit() {
            return commodityUnit;
        }

        public void setCommodityUnit(String commodityUnit) {
            this.commodityUnit = commodityUnit;
        }

        public int getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(int marketPrice) {
            this.marketPrice = marketPrice;
        }

        public int getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(int salePrice) {
            this.salePrice = salePrice;
        }

        public int getActivityPrice() {
            return activityPrice;
        }

        public void setActivityPrice(int activityPrice) {
            this.activityPrice = activityPrice;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public int getBuyerNumber() {
            return buyerNumber;
        }

        public void setBuyerNumber(int buyerNumber) {
            this.buyerNumber = buyerNumber;
        }

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }
    }

}
