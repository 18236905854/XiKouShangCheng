package com.xk.mall.model.entity;

/**
 * ClassName ShareSuccessBean
 * Description 分享成功回调的Bean
 * Author 卿凯
 * Date 2019/7/30/030
 * Version V1.0
 */
public class ShareSuccessBean {

    /**
     * activityGoodsCondition : {"activityId":"","activityType":0,"commodityId":"","goodsId":""}
     * clickUrlUserId :
     * popularizeId :
     * popularizeUserId :
     * state : 0
     */

    private ActivityGoodsConditionBean activityGoodsCondition;
    private String clickUrlUserId;
    private String popularizeId;
    private String popularizeUserId;
    private int state;

    public ActivityGoodsConditionBean getActivityGoodsCondition() {
        return activityGoodsCondition;
    }

    public void setActivityGoodsCondition(ActivityGoodsConditionBean activityGoodsCondition) {
        this.activityGoodsCondition = activityGoodsCondition;
    }

    public String getClickUrlUserId() {
        return clickUrlUserId;
    }

    public void setClickUrlUserId(String clickUrlUserId) {
        this.clickUrlUserId = clickUrlUserId;
    }

    public String getPopularizeId() {
        return popularizeId;
    }

    public void setPopularizeId(String popularizeId) {
        this.popularizeId = popularizeId;
    }

    public String getPopularizeUserId() {
        return popularizeUserId;
    }

    public void setPopularizeUserId(String popularizeUserId) {
        this.popularizeUserId = popularizeUserId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
