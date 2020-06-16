package com.xk.mall.model.request;

/**
 * ClassName CutRequestBody
 * Description 发起砍价请求体
 * Author 卿凯
 * Date 2019/7/12/012
 * Version V1.0
 */
public class CutRequestBody {

    /**
     * activityId :
     * commodityId :
     * id :
     * merchantId :
     * userId :
     */

    private String activityId;//活动ID
    private String commodityId;//商品sku ID
    private String id;//活动商品ID
    private String merchantId;//商家ID
    private String userId;//发起人ID
    private String address;//地址id

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

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
