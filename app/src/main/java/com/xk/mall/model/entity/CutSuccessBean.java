package com.xk.mall.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Description 砍价成功Bean
 *
 */
public class CutSuccessBean implements Serializable {

    /**
     * activityId :
     * bargainCount : 0
     * commodityId :
     * createTime :
     * currentPrice : 0
     * finalPrice : 0
     * id :
     * merchantId :
     * state : 0
     * updateTime :
     * userBargainRecordVoList : [{"assistUserHeadImageUrl":"","assistUserId":"","assistUserName":"","bargainPrice":0,"bargainScheduleId":"","createTime":"","id":""}]
     * userId :
     */

    private String activityId;
    private double bargainCount;//被砍价成功次数
    private String commodityId;
    private String createTime;//发起时间
    private int currentPrice;//当前砍价
    private int finalPrice;
    private String id;
    private String merchantId;
    private int state;
    private String updateTime;
    private String userId;
    private List<UserBargainRecordVoListBean> userBargainRecordVoList;
    private String address;
    private String commodityName;//商品名称
    private int bargainNumber;//多少人助力
    private int bargainEffectiveTimed;//砍价有效期--效时
    private String goodsImageUrl;//图片地址

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

    public int getBargainNumber() {
        return bargainNumber;
    }

    public void setBargainNumber(int bargainNumber) {
        this.bargainNumber = bargainNumber;
    }

    public int getBargainEffectiveTimed() {
        return bargainEffectiveTimed;
    }

    public void setBargainEffectiveTimed(int bargainEffectiveTimed) {
        this.bargainEffectiveTimed = bargainEffectiveTimed;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public double getBargainCount() {
        return bargainCount;
    }

    public void setBargainCount(int bargainCount) {
        this.bargainCount = bargainCount;
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

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<UserBargainRecordVoListBean> getUserBargainRecordVoList() {
        return userBargainRecordVoList;
    }

    public void setUserBargainRecordVoList(List<UserBargainRecordVoListBean> userBargainRecordVoList) {
        this.userBargainRecordVoList = userBargainRecordVoList;
    }

    public static class UserBargainRecordVoListBean {
        /**
         * assistUserHeadImageUrl :
         * assistUserId :
         * assistUserName :
         * bargainPrice : 0
         * bargainScheduleId :
         * createTime :
         * id :
         */

        private String assistUserHeadImageUrl;
        private String assistUserId;
        private String assistUserName;
        private int bargainPrice;
        private String bargainScheduleId;
        private String createTime;
        private String id;

        public String getAssistUserHeadImageUrl() {
            return assistUserHeadImageUrl;
        }

        public void setAssistUserHeadImageUrl(String assistUserHeadImageUrl) {
            this.assistUserHeadImageUrl = assistUserHeadImageUrl;
        }

        public String getAssistUserId() {
            return assistUserId;
        }

        public void setAssistUserId(String assistUserId) {
            this.assistUserId = assistUserId;
        }

        public String getAssistUserName() {
            return assistUserName;
        }

        public void setAssistUserName(String assistUserName) {
            this.assistUserName = assistUserName;
        }

        public int getBargainPrice() {
            return bargainPrice;
        }

        public void setBargainPrice(int bargainPrice) {
            this.bargainPrice = bargainPrice;
        }

        public String getBargainScheduleId() {
            return bargainScheduleId;
        }

        public void setBargainScheduleId(String bargainScheduleId) {
            this.bargainScheduleId = bargainScheduleId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
