package com.xk.mall.model.entity;

/**
 * 实名认证 信息bean
 */
public class RealNameInfoBean {


    /**
     * id : 5d37d1312b58bf0001b2073a
     * userId : 5d1abda2f7748700013e5ac4
     * realName :
     * idCard : 430181198911031458
     * idType : 1
     * mobile : 18900791426
     * imgFirst : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/user/20190724/ae542a68c8c24c789d8681e26c88431f.jpg
     * imgSecond : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/user/20190724/4dccb49c289e4a79851c94cd78f95601.jpg
     * createTime : 2019-07-24 11:32:01
     */

    private String id;
    private String userId;
    private String realName;
    private String idCard;
    private int idType;
    private String mobile;
    private String imgFirst;
    private String imgSecond;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
