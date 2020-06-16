package com.xk.mall.model.entity;

/**
 * ClassName ZeroGoodsBean
 * Description 0元竞拍商品的Bean
 * Author 卿凯
 * Date 2019/7/5/005
 * Version V1.0
 */
public class ZeroGoodsBean {

    /**
     * id : 5d1e02cd2d13760001b5c354
     * activityId : 5d1dffe32d13760001b5c34f
     * merchantId : 5d184ec326cc7500014a9bf2
     * roundId : 5d1dfdc22d13760001b5c34e
     * commodityId : 5d1df078bde9760001cb43f8
     * auctionRuleId : 5d1da57a5b63db00017a39e8
     * stock : 0
     * salePrice : 0
     * costPrice : 0
     * marketPrice : 0
     * commodityPrice : 0
     * startPrice : 0
     * bottomPrice : null
     * finalPrice : 0
     * winnerPrice : null
     * winnerId : null
     * winnerName : null
     * winnerTime : null
     * biddingNumber : null
     * online : 1
     * priority : 0
     * startTime : null
     * endTime : null
     * isDeleted : 0
     * createTime : 2019-07-04T13:44:45.000+0000
     * updateTime : 2019-07-04T14:13:36.000+0000
     * merchantName :
     * goodsId :
     * categoryRef1 :
     * categoryRef2 :
     * categoryRef3 :
     * categoryName :
     * goodsCode :
     * goodsImageUrl :
     * commodityModel :
     * commodityName :
     * commoditySpec :
     * status : 2
     * lastDeliveryTime : 0
     */

    private String id;//活动商品ID
    private String activityId;//活动ID
    private String merchantId;//商家ID
    private String roundId;//场次ID
    private String commodityId;//商品ID(SKU)
    private String auctionRuleId;//0元竞拍规则ID
    private int stock;//活动商品库存
    private int salePrice;//销售价
    private int costPrice;//成本价
    private int marketPrice;//市场价
    private int commodityPrice;//活动价
    private int startPrice;//起拍价
    private int bottomPrice;//成交底价
    private int finalPrice;//成交价
    private int winnerPrice;//获胜价格
    private String winnerId;//获胜者ID
    private String winnerName;//获胜者姓名
    private String winnerTime;//获胜时间
    private int biddingNumber;//每次竞价金额变更量
    private int online;//是否上架
    private int priority;//优先级
    private String startTime;//开始时间
    private String endTime;//结束时间
    private int isDeleted;//是否删除
    private String createTime;//创建时间
    private String updateTime;//修改时间
    private String merchantName;//上架名称
    private String goodsId;//商品SPU ID
    private String categoryRef1;//商品后台一级分类
    private String categoryRef2;//商品后台二级分类
    private String categoryRef3;//商品后台三级分类
    private String categoryName;//商品后台类名名称
    private String goodsCode;//商品货号
    private String goodsImageUrl;//商品主图
    private String commodityModel;//商品型号
    private String commodityName;//商品名称
    private String commoditySpec;//商品规格
    private int status;
    private int lastDeliveryTime;//承诺最后发货时间
    private long closeTime;//竞拍结束时间（服务器没返回该字段）

    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
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

    public String getRoundId() {
        return roundId;
    }

    public void setRoundId(String roundId) {
        this.roundId = roundId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getAuctionRuleId() {
        return auctionRuleId;
    }

    public void setAuctionRuleId(String auctionRuleId) {
        this.auctionRuleId = auctionRuleId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(int costPrice) {
        this.costPrice = costPrice;
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

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(int bottomPrice) {
        this.bottomPrice = bottomPrice;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getWinnerPrice() {
        return winnerPrice;
    }

    public void setWinnerPrice(int winnerPrice) {
        this.winnerPrice = winnerPrice;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getWinnerTime() {
        return winnerTime;
    }

    public void setWinnerTime(String winnerTime) {
        this.winnerTime = winnerTime;
    }

    public int getBiddingNumber() {
        return biddingNumber;
    }

    public void setBiddingNumber(int biddingNumber) {
        this.biddingNumber = biddingNumber;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getCategoryRef1() {
        return categoryRef1;
    }

    public void setCategoryRef1(String categoryRef1) {
        this.categoryRef1 = categoryRef1;
    }

    public String getCategoryRef2() {
        return categoryRef2;
    }

    public void setCategoryRef2(String categoryRef2) {
        this.categoryRef2 = categoryRef2;
    }

    public String getCategoryRef3() {
        return categoryRef3;
    }

    public void setCategoryRef3(String categoryRef3) {
        this.categoryRef3 = categoryRef3;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public String getCommodityModel() {
        return commodityModel;
    }

    public void setCommodityModel(String commodityModel) {
        this.commodityModel = commodityModel;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLastDeliveryTime() {
        return lastDeliveryTime;
    }

    public void setLastDeliveryTime(int lastDeliveryTime) {
        this.lastDeliveryTime = lastDeliveryTime;
    }
}
