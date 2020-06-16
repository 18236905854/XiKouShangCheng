package com.xk.mall.model.request;

import java.util.List;

/**
 * @ClassName: SaveShareRequestBody
 * @Description: 提交截图请求体
 * @Author: 卿凯
 * @Date: 2019/12/9/009 15:52
 * @Version: 1.0
 */
public class SaveShareRequestBody {

    /**
     * imgModels : [{"auditImgUrl":""}]
     * userId : 0
     * userName :
     * userPhone :
     */

    private String userId;
    private String userName;
    private String userPhone;
    private List<ImgModelsBean> imgModels;

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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public List<ImgModelsBean> getImgModels() {
        return imgModels;
    }

    public void setImgModels(List<ImgModelsBean> imgModels) {
        this.imgModels = imgModels;
    }

    public static class ImgModelsBean {
        /**
         * auditImgUrl :
         */

        private String auditImgUrl;

        public String getAuditImgUrl() {
            return auditImgUrl;
        }

        public void setAuditImgUrl(String auditImgUrl) {
            this.auditImgUrl = auditImgUrl;
        }
    }
}
