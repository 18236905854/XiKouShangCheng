package com.xk.mall.model.entity;

import java.util.List;

/**
 * 商品详情 sku
 */
public class GoodsSkuListBean {
    /**
     * id : 5d1f06aef88dde000147c855
     * goodsId : 5d1f06aef88dde000147c852
     * goodsUnit : 1
     * goodsModel : S
     * goodsType : 红色
     * stock : 100
     * virtualStock : null
     * freezeStock : null
     * marketPrice : 30000
     * costPrice : 10000
     * salePrice : 20000
     * deleted : 0
     * status : 1
     * startNum : 5
     */

    private String id;
    private String goodsId;
    private String goodsUnit;
    private String goodsModel;//型号
    private String goodsType;//规格
    private int stock;//实物库存
    private String virtualStock;//虚拟库存
    private String freezeStock;//冻结库存
    private int marketPrice;
    private int costPrice;
    private int salePrice;
    private int deleted;
    private int status;
    private int startNum;



    private List<SkuAttribute> specMap;//自己写上去sku的属性

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getVirtualStock() {
        return virtualStock;
    }

    public void setVirtualStock(String virtualStock) {
        this.virtualStock = virtualStock;
    }

    public String getFreezeStock() {
        return freezeStock;
    }

    public void setFreezeStock(String freezeStock) {
        this.freezeStock = freezeStock;
    }

    public int getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(int costPrice) {
        this.costPrice = costPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public List<SkuAttribute> getSpecMap() {
        return specMap;
    }

    public void setSpecMap(List<SkuAttribute> specMap) {
        this.specMap = specMap;
    }
}
