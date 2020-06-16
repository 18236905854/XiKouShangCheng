package com.xk.mall.model.entity;

/**
 *  skipType goods   商品
 */
public class BannerGoodsBean {
    /**
     * activityId : 5d301d56ed5fc40001c607bf
     * goodsId : 5d2f13306a7cdf0001dafe63
     * commodityId : 5d2f13306a7cdf0001dafe66
     * activityType : 4
     */
    private String activityId;
    private String goodsId;
    private String commodityId;
    private int activityType;

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

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }
}
