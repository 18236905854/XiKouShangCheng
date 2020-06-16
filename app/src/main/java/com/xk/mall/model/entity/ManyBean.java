package com.xk.mall.model.entity;

/**
 * ClassName ManyBean
 * Description 多买多折页面数据Bean
 * Author 卿凯
 * Date 2019/6/24/024
 * Version V1.0
 */
public class ManyBean {

    /**
     * goodsId : 5d1f06aef88dde000147c852
     * commodityId : 5d1f06aef88dde000147c858
     * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190705/63113ccbea1f4c7cb3057158206a5508.jpg
     * commodityName : qwwe
     * salePrice : 20000
     * marketPrice : 30000
     * commodityPrice : null
     * rateOne : 8
     */
    private String activityId;//活动id
    private String goodsId;//商品Spu ID
    private String commodityId;//商品SKU ID
    private String goodsImageUrl;//商品主图
    private String commodityName;//商品名称
    private int salePrice;//销售价
    private int marketPrice;//市场价
    private int commodityPrice;//活动价
    private int rateThree;//最小折扣

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

    public int getRateThree() {
        return rateThree;
    }

    public void setRateThree(int rateThree) {
        this.rateThree = rateThree;
    }
}
