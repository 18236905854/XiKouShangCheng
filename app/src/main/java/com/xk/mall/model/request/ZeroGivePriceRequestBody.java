package com.xk.mall.model.request;

/**
 * ClassName ZeroGivePriceRequestBody
 * Description 竞拍出价接口的请求体
 * Author 卿凯
 * Date 2019/7/5/005
 * Version V1.0
 */
public class ZeroGivePriceRequestBody {

    /**
     * activityId : 5d1dffe32d13760001b5c34f
     * area : 长沙
     * auctionPrice : 0
     * commodityId : 5d1e02cd2d13760001b5c354
     * currentPrice : 0
     * id : 5d1e02cd2d13760001b5c354
     * userId : 5d1d58eeaae31800016caedd
     * userName : xk9652341
     */

    private String activityId;//活动ID
    private String area;//区域
    private int auctionPrice;//竞拍价,0元拍可不传
    private String commodityId;//商品ID
    private int currentPrice;//当前价格，0元拍可不传
    private String id;//活动商品ID
    private String userId;//竞拍用户ID
    private String userName;//用户名称

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getAuctionPrice() {
        return auctionPrice;
    }

    public void setAuctionPrice(int auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
