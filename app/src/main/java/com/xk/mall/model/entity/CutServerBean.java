package com.xk.mall.model.entity;

/**
 * ClassName CutServerBean
 * Description 喜立得页面推荐商品 根据服务端返回数据Bean
 * Author 卿凯
 * Date 2019/7/11/011
 * Version V1.0
 */
public class CutServerBean {
    /**
     * bargainNumber : 0
     * commodityName :
     * commodityPrice : 0
     * goodsImageUrl :
     * id :
     * marketPrice : 0
     * reservePriceRate : 0
     * salePrice : 0
     * salesNumber : 0
     * stock : 0
     */

    private int bargainNumber;//最多砍价人数
    private int bargainStatus;//1：未发起砍价，2：已砍过价，3：正在砍价
    private String commodityName;//商品名称
    private int commodityPrice;//活动价
    private String goodsImageUrl;//商品主图
    private String id;//商品ID
    private String goodsId;//商品ID
    private String activityId;//活动ID
    private String commodityId;//商品spuID
    private int marketPrice;//市场价
    private int reservePriceRate;//最小折扣
    private int salePrice;//销售价
    private int salesNumber;//砍价成功人数
    private int stock;//活动商品库存
    private int bargainEffectiveTimed;//砍价申请有效时长; 小时
    private String bargainCreateTime;//发起砍价时间

    public String getBargainCreateTime() {
        return bargainCreateTime;
    }
    public void setBargainCreateTime(String bargainCreateTime) {
        this.bargainCreateTime = bargainCreateTime;
    }
    public int getBargainEffectiveTimed() {
        return bargainEffectiveTimed;
    }

    public void setBargainEffectiveTimed(int bargainEffectiveTimed) {
        this.bargainEffectiveTimed = bargainEffectiveTimed;
    }

    public int getBargainStatus() {
        return bargainStatus;
    }

    public void setBargainStatus(int bargainStatus) {
        this.bargainStatus = bargainStatus;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public int getBargainNumber() {
        return bargainNumber;
    }

    public void setBargainNumber(int bargainNumber) {
        this.bargainNumber = bargainNumber;
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

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getReservePriceRate() {
        return reservePriceRate;
    }

    public void setReservePriceRate(int reservePriceRate) {
        this.reservePriceRate = reservePriceRate;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getSalesNumber() {
        return salesNumber;
    }

    public void setSalesNumber(int salesNumber) {
        this.salesNumber = salesNumber;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
