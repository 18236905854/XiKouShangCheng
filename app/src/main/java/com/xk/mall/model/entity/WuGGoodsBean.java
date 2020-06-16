package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * 吾G 限购 商品
 */
public class WuGGoodsBean implements Serializable {

    /**
     * id : 5d842ef440bdf50001c57d97
     * commodityId : 5d835363a9c9c100014f1cc4
     * commodityName : Razer雷蛇 曼巴眼镜蛇竞技版RGB吃鸡电竞有线游戏鼠标绝地求生
     * activityId : 5d42a68292c04200017f1c94
     * stock : 1
     * state : 1
     * createTime : 2019-09-20 09:44:20
     * updateTime : 2019-09-20 09:44:20
     * salePrice : 9900
     * costPrice : 5500
     * marketPrice : 19900
     * commodityPrice : 8910
     * goodsImageUrl : https://oss-shop-test.luluxk.com/goods/20190919/70e2e5d4cd874a8dae1ec299ff943d17.png
     * consumptionTaskValue : null
     * consumptionNum : 1
     * couponValue : null
     * salesVolume : 0
     */

    private String id;
    private String commodityId;
    private String commodityName;
    private String activityId;
    private int stock;
    private int state;
    private String createTime;
    private String updateTime;
    private int salePrice;
    private int costPrice;
    private int marketPrice;
    private int commodityPrice;
    private String goodsImageUrl;
    private int consumptionTaskValue;
    private int consumptionNum;
    private int couponValue;
    private int salesVolume;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public int getConsumptionTaskValue() {
        return consumptionTaskValue;
    }

    public void setConsumptionTaskValue(int consumptionTaskValue) {
        this.consumptionTaskValue = consumptionTaskValue;
    }

    public int getConsumptionNum() {
        return consumptionNum;
    }

    public void setConsumptionNum(int consumptionNum) {
        this.consumptionNum = consumptionNum;
    }

    public int getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(int couponValue) {
        this.couponValue = couponValue;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }
}
