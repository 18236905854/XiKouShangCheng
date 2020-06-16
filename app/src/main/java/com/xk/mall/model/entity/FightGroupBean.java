package com.xk.mall.model.entity;

/**
 * ClassName FightGroupBean
 * Description 定制拼团的Bean
 * Author 卿凯
 * Date 2019/6/20/020
 * Version V1.0
 */
public class FightGroupBean {

    /**
     * activityId : null
     * goodsId : 5d1f06aef88dde000147c852
     * commodityId : 5d1f06aef88dde000147c858
     * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190705/63113ccbea1f4c7cb3057158206a5508.jpg
     * commodityName : qwwe
     * salePrice : 20000
     * marketPrice : 30000
     * commodityPrice : 10000
     * rateOne : null
     * merchantId
     */
    private String id;//活动商品id
    private String activityId;//活动ID
    private String goodsId;//商品ID sku
    private String commodityId;// spuID
    private String goodsImageUrl;//图片
    private String commodityName;//商品名称
    private int salePrice;//销售价
    private int marketPrice;//市场价
    private int commodityPrice;//拼团价
    private int state;//该拼团的状态 1：进行中 2:结束
    private int authen;//商品认证状态  0:未认证  1: 已认证
    private int sumNum;//该拼团总件数
    private int lastNum;//该拼团还剩件数
    private long endTime;//该拼团剩余时间
    private String merchantId;//商家id

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getAuthen() {
        return authen;
    }

    public void setAuthen(int authen) {
        this.authen = authen;
    }

    public int getSumNum() {
        return sumNum;
    }

    public void setSumNum(int sumNum) {
        this.sumNum = sumNum;
    }

    public int getLastNum() {
        return lastNum;
    }

    public void setLastNum(int lastNum) {
        this.lastNum = lastNum;
    }
}
