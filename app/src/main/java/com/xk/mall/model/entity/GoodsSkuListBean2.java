package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName GoodsSkuListBean2
 * Description 商品SKU对象
 * Author 卿凯
 * Date 2019/7/30/030
 * Version V1.0
 */
public class GoodsSkuListBean2 {

    /**
     * id : 5da5ad46952d800001afd2c8
     * goodsModel : 颜色
     * goodsSpec : 尺寸
     * goodsUnit : 0
     * commodityName : myPhoneShan
     * categoryName : 商家自营-家电
     * commodityPrice : null
     * commodityId : 5da5ad46952d800001afd2c8
     * salePrice : 25500
     * marketPrice : 55500
     * stock : 2
     * virtualStock : 1
     * contition : ["白色","小"]
     * commoditySpec : 小
     * commodityModel : 白色
     */

    private String id;
    private String goodsModel;
    private String goodsSpec;
    private String goodsUnit;
    private String commodityName;
    private String categoryName;
    private int commodityPrice;
    private int commodityPriceOne;//多买多折显示价格
    private String commodityId;
    private int salePrice;
    private int marketPrice;
    private int stock;
    private int virtualStock;
    private String skuImage;//商品图片
    private String commoditySpec;
    private String commodityModel;
    private int couponValue;//优惠券价格  吾G使用
    private int deductionCouponAmount;//赠送优惠券数量，全球买手使用
    private List<String> contition;
    //喜立得使用字段
    private String bargainCreateTime;//发起砍价时间
    private int bargainEffectiveTimed;//砍价申请有效时长
    private int bargainStatus;//砍价状态：1：未发起砍价，2：已砍过价，3：正在砍价
    private int bargainState;//砍价进度状态;0-未发起砍价;1-继续砍价;2-砍价成功;3-生成订单成功;4-支付成功
    private List<SkuAttribute> specMap;//自己写上去sku的属性

    public int getBargainState() {
        return bargainState;
    }

    public void setBargainState(int bargainState) {
        this.bargainState = bargainState;
    }

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

    public int getCommodityPriceOne() {
        return commodityPriceOne;
    }

    public void setCommodityPriceOne(int commodityPriceOne) {
        this.commodityPriceOne = commodityPriceOne;
    }

    public int getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(int couponValue) {
        this.couponValue = couponValue;
    }

    public int getDeductionCouponAmount() {
        return deductionCouponAmount;
    }

    public void setDeductionCouponAmount(int deductionCouponAmount) {
        this.deductionCouponAmount = deductionCouponAmount;
    }

    public String getSkuImage() {
        return skuImage;
    }

    public void setSkuImage(String skuImage) {
        this.skuImage = skuImage;
    }

    public List<SkuAttribute> getSpecMap() {
        return specMap;
    }

    public void setSpecMap(List<SkuAttribute> specMap) {
        this.specMap = specMap;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(int commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getVirtualStock() {
        return virtualStock;
    }

    public void setVirtualStock(int virtualStock) {
        this.virtualStock = virtualStock;
    }

    public String getCommoditySpec() {
        return commoditySpec;
    }

    public void setCommoditySpec(String commoditySpec) {
        this.commoditySpec = commoditySpec;
    }

    public String getCommodityModel() {
        return commodityModel;
    }

    public void setCommodityModel(String commodityModel) {
        this.commodityModel = commodityModel;
    }

    public List<String> getContition() {
        return contition;
    }

    public void setContition(List<String> contition) {
        this.contition = contition;
    }
}
