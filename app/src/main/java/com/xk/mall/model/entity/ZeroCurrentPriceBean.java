package com.xk.mall.model.entity;

/**
 * 当前0元拍商品 当前竞拍价 与剩余时间
 */
public class ZeroCurrentPriceBean {

    /**
     * auctionCommodityId : 5d2806fc497acd00016fd79c
     * status : 2
     * currentPrice : 4501.2
     * remainingTime : 25774
     */

    private String auctionCommodityId;//商品id
    private int status;//拍卖状态 1:未开始，2:已开始，3:已结束，4:已取消, 5:已流拍
    private int currentPrice;// 当前竞拍价
    private int remainingTime;//剩余时间 秒
    private String goodsName;//商品名称
    private String goodsUrl;//商品图片

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getAuctionCommodityId() {
        return auctionCommodityId;
    }

    public void setAuctionCommodityId(String auctionCommodityId) {
        this.auctionCommodityId = auctionCommodityId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}
