package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName ZeroAuctionBean
 * Description 0元拍商品竞拍情况Bean
 * Author 卿凯
 * Date 2019/7/11/011
 * Version V1.0
 */
public class ZeroAuctionBean {
    /**
     * activityId :
     * auctionCommodityId :
     * currentPrice : 0
     * finishPrice : 0
     * recordList : [{"activityId":"","area":"","auctionPrice":0,"commodityId":"","createTime":"","currentPrice":0,"id":"","userId":"","userName":""}]
     * remainingTime : 0
     * status : 0
     * winerName :
     * winnerId :
     */

    private String activityId;//活动ID
    private String auctionCommodityId;//活动商品ID
    private int currentPrice;//当前价格
    private int finishPrice;//中标价格
    private int remainingTime;//剩余秒数
    private int status;//拍卖状态:1:未开始，2:已开始，3:已结束，4:已取消
    private String winerName;//中拍用户名称
    private String winnerId;//中拍用户ID
    private int recordCount;//竞价次数
    private int recordCountForUser;//用户参与次数
    private String orderNo;//中标订单号
    private int postponeStart;//剩余多少秒开始倒计时

    public int getPostponeStart() {
        return postponeStart;
    }

    public void setPostponeStart(int postponeStart) {
        this.postponeStart = postponeStart;
    }

    private List<RecordListBean> recordList;//出价记录

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getAuctionCommodityId() {
        return auctionCommodityId;
    }

    public void setAuctionCommodityId(String auctionCommodityId) {
        this.auctionCommodityId = auctionCommodityId;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getFinishPrice() {
        return finishPrice;
    }

    public void setFinishPrice(int finishPrice) {
        this.finishPrice = finishPrice;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWinerName() {
        return winerName;
    }

    public void setWinerName(String winerName) {
        this.winerName = winerName;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getRecordCountForUser() {
        return recordCountForUser;
    }

    public void setRecordCountForUser(int recordCountForUser) {
        this.recordCountForUser = recordCountForUser;
    }

    public List<RecordListBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordListBean> recordList) {
        this.recordList = recordList;
    }

    public static class RecordListBean {
        /**
         * activityId :
         * area :
         * auctionPrice : 0
         * commodityId :
         * createTime :
         * currentPrice : 0
         * id :
         * userId :
         * userName :
         */

        private String activityId;//活动ID
        private String area;//区域
        private int auctionPrice;//竞拍价
        private String commodityId;//商品ID
        private String createTime;//创建时间
        private double currentPrice;//当前价格
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public double getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
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
}
