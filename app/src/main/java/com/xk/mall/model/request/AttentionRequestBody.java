package com.xk.mall.model.request;

/**
 * ClassName AttentionRequestBody
 * Description 我的关注  关注设计师/店铺 页面的请求体
 * Author 卿凯
 * Date 2019/6/19/019
 * Version V1.0
 */
public class AttentionRequestBody {
    private String userId ;
    private String  designerId ;
    private String  operationType;//follow 关注  cancel 取消关注
    private String  shopId;//店铺id
    private String workId;//设计师作品id

    public String getDesignerWorkId() {
        return workId;
    }

    public void setDesignerWorkId(String designerWorkId) {
        this.workId = designerWorkId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

}
