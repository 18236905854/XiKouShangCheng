package com.xk.mall.model.entity;

/**
 * ClassName SellOrderBean
 * Description 我的寄卖订单页面Bean
 * Author 卿凯
 * Date 2019/6/27/027
 * Version V1.0
 */
public class SellOrderBean {

    /**
     * activityId :
     * commodityId :
     * commodityModel :
     * commodityName :
     * commodityPrice : 0
     * commoditySpec :
     * consumptionNum : 0
     * consumptionTaskValue : 0
     * costPrice : 0
     * couponValue : 0
     * createTime :
     * goodsId :
     * goodsImageUrl :
     * id :
     * marketPrice : 0
     * merchantId :
     * originalId :
     * priority : 0
     * ranking : 0
     * salePrice : 0
     * updateTime :
     * userId :
     * userName :
     */

    private String activityId;//活动ID
    private String commodityId;//商品ID
    private String commodityModel;//商品型号
    private String commodityName;//商品名称
    private int commodityPrice;//活动价
    private String commoditySpec;//商品规格
    private int consumptionNum;//消耗次数
    private int consumptionTaskValue;//消耗贡献值
    private int costPrice;//成本价
    private int couponValue;//该商品获赠的优惠券面值
    private String createTime;//创建时间
    private String goodsId;//商品SPU ID
    private String goodsImageUrl;//商品主图
    private String id;//主键ID
    private int marketPrice;//市场价
    private String merchantId;//商家ID
    private String originalId;//原始订单ID
    private int priority;//优先级
    private int ranking;//当前排名
    private int salePrice;//销售价
    private int shareModel;//分享模式: 1-分享给好友;2-寄卖;3-两者都有
    private String updateTime;//修改时间
    private String userId;//寄卖用户ID
    private String userName;//寄卖用户名称
    private String merchantName;//商家名称
    private int alwaysSelectMg;//0：不必须选中，1：必须选中
    private long linkExpiredTime;//链接失效时间
    private int consignmentType;//寄卖方式(1：定向寄卖 2：寄卖到吾G NULL或者3：所有寄卖方式)

    public int getConsignmentType() {
        return consignmentType;
    }

    public void setConsignmentType(int consignmentType) {
        this.consignmentType = consignmentType;
    }

    public long getLinkExpiredTime() {
        return linkExpiredTime;
    }

    public void setLinkExpiredTime(long linkExpiredTime) {
        this.linkExpiredTime = linkExpiredTime;
    }

    public int getAlwaysSelectMg() {
        return alwaysSelectMg;
    }

    public void setAlwaysSelectMg(int alwaysSelectMg) {
        this.alwaysSelectMg = alwaysSelectMg;
    }

    public int getShareModel() {
        return shareModel;
    }

    public void setShareModel(int shareModel) {
        this.shareModel = shareModel;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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

    public String getCommodityModel() {
        return commodityModel;
    }

    public void setCommodityModel(String commodityModel) {
        this.commodityModel = commodityModel;
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

    public String getCommoditySpec() {
        return commoditySpec;
    }

    public void setCommoditySpec(String commoditySpec) {
        this.commoditySpec = commoditySpec;
    }

    public int getConsumptionNum() {
        return consumptionNum;
    }

    public void setConsumptionNum(int consumptionNum) {
        this.consumptionNum = consumptionNum;
    }

    public int getConsumptionTaskValue() {
        return consumptionTaskValue;
    }

    public void setConsumptionTaskValue(int consumptionTaskValue) {
        this.consumptionTaskValue = consumptionTaskValue;
    }

    public int getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(int costPrice) {
        this.costPrice = costPrice;
    }

    public int getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(int couponValue) {
        this.couponValue = couponValue;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

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
}
