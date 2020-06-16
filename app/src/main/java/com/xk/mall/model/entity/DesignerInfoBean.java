package com.xk.mall.model.entity;

/**
 * 设计师 主页个人信息
 */
public class DesignerInfoBean {
    /**
     * designerId : 5d185744b4e57100012a59b1
     * name : 张三
     * headUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190628/de712f4fa4c647af9672de0221b0fc33.png
     * fanCnt : null
     * fabulousCnt : null
     * workCnt : 7
     * coverUrl : 543
     * description : 234
     * pageName : 222   //主页名称
     * id : 5d0cd27def31822c18e8bc36
     * isFollow   0   （1-已关注；0-未关注）
     */
    private String designerId;
    private String name;
    private String headUrl;
    private int fanCnt;
    private int fabulousCnt;
    private int workCnt;
    private String coverUrl;
    private String description;
    private String pageName;
    private String id;
    private int isFollow;

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getFanCnt() {
        return fanCnt;
    }

    public void setFanCnt(int fanCnt) {
        this.fanCnt = fanCnt;
    }

    public int getFabulousCnt() {
        return fabulousCnt;
    }

    public void setFabulousCnt(int fabulousCnt) {
        this.fabulousCnt = fabulousCnt;
    }

    public int getWorkCnt() {
        return workCnt;
    }

    public void setWorkCnt(int workCnt) {
        this.workCnt = workCnt;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
