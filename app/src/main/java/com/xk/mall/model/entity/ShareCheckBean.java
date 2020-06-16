package com.xk.mall.model.entity;

import java.util.List;

/**
 * @ClassName: ShareCheckBean
 * @Description: 分享审核页面Bean
 * @Author: 卿凯
 * @Date: 2019/12/8/008 14:27
 * @Version: 1.0
 */
public class ShareCheckBean {

    /**
     * advertUserImgModels : [{"auditImgId":"","auditImgUrl":""}]
     * auditId :
     * auditIntegral : 0
     * auditName :
     * auditReson :
     * auditStatus :
     * auditTime :
     * createTime :
     * userId : 0
     * userName :
     * userPhone :
     */

    private String auditId;
    private int auditIntegral;
    private String auditName;
    private String auditReson;
    private String auditStatus;
    private String auditTime;
    private String createTime;
    private int userId;
    private String userName;
    private String userPhone;
    private List<AdvertUserImgModelsBean> advertUserImgModels;

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public int getAuditIntegral() {
        return auditIntegral;
    }

    public void setAuditIntegral(int auditIntegral) {
        this.auditIntegral = auditIntegral;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public String getAuditReson() {
        return auditReson;
    }

    public void setAuditReson(String auditReson) {
        this.auditReson = auditReson;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public List<AdvertUserImgModelsBean> getAdvertUserImgModels() {
        return advertUserImgModels;
    }

    public void setAdvertUserImgModels(List<AdvertUserImgModelsBean> advertUserImgModels) {
        this.advertUserImgModels = advertUserImgModels;
    }

    public static class AdvertUserImgModelsBean {
        /**
         * auditImgId :
         * auditImgUrl :
         */

        private String auditImgId;
        private String auditImgUrl;

        public String getAuditImgId() {
            return auditImgId;
        }

        public void setAuditImgId(String auditImgId) {
            this.auditImgId = auditImgId;
        }

        public String getAuditImgUrl() {
            return auditImgUrl;
        }

        public void setAuditImgUrl(String auditImgUrl) {
            this.auditImgUrl = auditImgUrl;
        }
    }
}
