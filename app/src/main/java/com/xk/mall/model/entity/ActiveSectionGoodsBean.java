package com.xk.mall.model.entity;

/**
 * ClassName ActiveSectionBean
 * Description 活动版块商品Bean
 * Author 卿凯
 * Date 2019/7/29/029
 * Version V1.0
 */
public class ActiveSectionGoodsBean {


    /**
     * activityGoodsId :
     * activityId :
     * bargainCount=(null)
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
     * "currentFightGroupNum": 当前成团人数
     *         "targetNumber": 拼团目标人数
     */

    private String activityGoodsId;
    private String activityId;
    private String commodityId;
    private String commodityName;
    private int commodityPrice;
    private int couponValue;
    private double discount;
    private String goodsId;
    private String goodsImageUrl;
    private int salePrice;
    private int stock;//库存，用来判断列表商品是否售罄
    private int marketPrice;//市场价，多买多折显示划掉价格
    //多买多折
    private int commodityPriceOne;
    private int commodityPriceTwo;
    private int commodityPriceThree;
    private double rateOne;
    private double rateTwo;
    private double rateThree;
    private int shareAmount;//分享赚
    //自己加的
    private int currentFightGroupNum;//该拼团的状态 1：进行中 2:结束
    private int targetNumber;//商品认证状态  0:未认证  1: 已认证
    //喜立得
    private int bargainState;// 1继续砍价 2 砍价成功
    private int bargainStatus;// 1 未发起砍价 2 已砍过价  3.正在砍价
    private String bargainCreateTime;//发起砍价时间
    private int bargainEffectiveTime;//此次砍价有效时长
    private int currentPrice;//当前已砍至价格
    private int bargainCount;//砍价次数
    private int bargainNumber;//已砍价人数
    private int bargainedNum;//参与砍价最大人数

    public int getShareMoney() {
        return shareAmount;
    }

    public void setShareMoney(int shareMoney) {
        this.shareAmount = shareMoney;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getRateOne() {
        return rateOne;
    }

    public void setRateOne(double rateOne) {
        this.rateOne = rateOne;
    }

    public double getRateTwo() {
        return rateTwo;
    }

    public void setRateTwo(double rateTwo) {
        this.rateTwo = rateTwo;
    }

    public double getRateThree() {
        return rateThree;
    }

    public void setRateThree(double rateThree) {
        this.rateThree = rateThree;
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

    public int getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getBargainState() {
        return bargainState;
    }

    public void setBargainState(int bargainState) {
        this.bargainState = bargainState;
    }

    public int getBargainStatus() {
        return bargainStatus;
    }

    public void setBargainStatus(int bargainStatus) {
        this.bargainStatus = bargainStatus;
    }

    public String getBargainCreateTime() {
        return bargainCreateTime;
    }

    public void setBargainCreateTime(String bargainCreateTime) {
        this.bargainCreateTime = bargainCreateTime;
    }

    public int getBargainEffectiveTime() {
        return bargainEffectiveTime;
    }

    public void setBargainEffectiveTime(int bargainEffectiveTime) {
        this.bargainEffectiveTime = bargainEffectiveTime;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getBargainCount() {
        return bargainCount;
    }

    public void setBargainCount(int bargainCount) {
        this.bargainCount = bargainCount;
    }

    public int getCurrentFightGroupNum() {
        return currentFightGroupNum;
    }

    public void setCurrentFightGroupNum(int currentFightGroupNum) {
        this.currentFightGroupNum = currentFightGroupNum;
    }

    public int getTargetNumber() {
        return targetNumber;
    }

    public void setTargetNumber(int targetNumber) {
        this.targetNumber = targetNumber;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
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
