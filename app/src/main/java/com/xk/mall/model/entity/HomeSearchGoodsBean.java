package com.xk.mall.model.entity;

/**
 * ClassName HomeSearchGoodsBean
 * Description 首页搜索商品Bean
 * Author 卿凯
 * Date 2019/8/2/002
 * Version V1.0
 */
public class HomeSearchGoodsBean {

    /**
     * activityType : 0
     * commodityName :
     * commodityPrice : 0
     * createTime :
     * id :
     * imageUrl :
     * salePrice : 0
     * salesVolume : 0
     */

    private int activityType;
    private String commodityName;
    private int commodityPrice;
    private String createTime;
    private String id;
    private String imageUrl;
    private int salePrice;
    private int salesVolume;

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public int getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(int commodityPrice) {
        this.commodityPrice = commodityPrice;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }
}
