package com.xk.mall.model.entity;

/**
 * ClassName GivePriceBean
 * Description 0元竞拍出价Bean
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class GivePriceBean {

    /**
     * activityId : 5d1dffe32d13760001b5c34f
     * commodityId : 5d1e02cd2d13760001b5c354
     * userId : 5d1d58eeaae31800016caedd
     * userName : aaa
     * currentPrice : 0.8
     * auctionPrice : 0.8
     * area : aa
     * createTime : 2019-07-05T04:25:21.155+0000
     */

    private String activityId;//活动ID
    private String commodityId;//商品ID
    private String userId;//用户ID
    private String userName;//用户名称
    private double currentPrice;//当前价格
    private double auctionPrice;//竞拍价格
    private String area;//地区
    private String createTime;//创建时间

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getAuctionPrice() {
        return auctionPrice;
    }

    public void setAuctionPrice(double auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
