package com.xk.mall.model.request;

/**
 * ClassName ShareRequestBody
 * Description 获取分享内容的请求体
 * Author 卿凯
 * Date 2019/7/23/023
 * Version V1.0
 */
public class ShareRequestBody {

    /**
     * activityGoodsCondition : {"activityId":"","activityType":0,"commodityId":"","goodsId":""}
     * activityId :
     * popularizePosition : 0
     * shareUserId :
     * shopId :
     */

    private ActivityGoodsConditionBean activityGoodsCondition;
    private String activityId;
    private String designerId;
    private int popularizePosition;
    private String shareUserId;
    private String shopId;

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public ActivityGoodsConditionBean getActivityGoodsCondition() {
        return activityGoodsCondition;
    }

    public void setActivityGoodsCondition(ActivityGoodsConditionBean activityGoodsCondition) {
        this.activityGoodsCondition = activityGoodsCondition;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getPopularizePosition() {
        return popularizePosition;
    }

    public void setPopularizePosition(int popularizePosition) {
        this.popularizePosition = popularizePosition;
    }

    public String getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(String shareUserId) {
        this.shareUserId = shareUserId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public static class ActivityGoodsConditionBean {
        /**
         * activityId :
         * activityType : 0
         * commodityId :
         * goodsId :
         */

        private String activityId;
        private int activityType;
        private String commodityId;
        private String goodsId;
        private String activityGoodsId;//活动商品ID

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

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }
    }
}
