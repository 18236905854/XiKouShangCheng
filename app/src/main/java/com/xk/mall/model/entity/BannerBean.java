package com.xk.mall.model.entity;

/**
 * banner 图 实体类
 */
public class BannerBean {
    /**
     * id : 5d31904970a0440001ddac85
     * moudle : 1
     * position : 1
     * title : 测试banner
     * skipType : goods
     * targetUrl1 :
     * targetUrl2 :
     * targetParams : {
     "params": {
     "activityId": "5d301d56ed5fc40001c607bf",
     "goodsId": "5d2f13306a7cdf0001dafe63",
     "commodityId": "5d2f13306a7cdf0001dafe66",
     "activityType": 4
     }
     }
     * imageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/f01ee0ec5d5f4c6d8924459291e9624c.jpg
     * sort : 1
     * status : 2
     * isTop : 1
     */

    private String id;
    private int moudle;
    private int position;
    private String title;
    private String skipType;//跳转类型: goods(商品),activity(活动),url(链接)等
    private String targetUrl1;
    private String targetUrl2;
    private String targetParams;
    private String imageUrl;
    private int sort;
    private int status;
    private int isTop;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMoudle() {
        return moudle;
    }

    public void setMoudle(int moudle) {
        this.moudle = moudle;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getTargetUrl1() {
        return targetUrl1;
    }

    public void setTargetUrl1(String targetUrl1) {
        this.targetUrl1 = targetUrl1;
    }

    public String getTargetUrl2() {
        return targetUrl2;
    }

    public void setTargetUrl2(String targetUrl2) {
        this.targetUrl2 = targetUrl2;
    }

    public String getTargetParams() {
        return targetParams;
    }

    public void setTargetParams(String targetParams) {
        this.targetParams = targetParams;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }
}
