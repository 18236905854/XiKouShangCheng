package com.xk.mall.model.entity;

/**
 * 实名认证 请求body
 */
public class RealNameRequestBody {
    private String userId;
    private String realName;
    private String idCard;
    private String imgFirst;//国徽
    private String imgSecond;//人物面
    private String  mobile;
    private int idType;//证件类型  1：身份证

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getImgFirst() {
        return imgFirst;
    }

    public void setImgFirst(String imgFirst) {
        this.imgFirst = imgFirst;
    }

    public String getImgSecond() {
        return imgSecond;
    }

    public void setImgSecond(String imgSecond) {
        this.imgSecond = imgSecond;
    }
}
