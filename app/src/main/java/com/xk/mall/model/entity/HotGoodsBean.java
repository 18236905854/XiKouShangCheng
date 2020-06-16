package com.xk.mall.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 热排行商品
 */
public class HotGoodsBean implements Serializable {
    private Integer image;
    private int goodsId;
    private String name;
    private String price;
    private String linePrice;
    private int forWard;//转发数量
    private String lowDisCount;//最低几折
    private String coupon;//券
    private String perpleNum;//助力
    private String shengPrice;//省钱


    public HotGoodsBean(Integer image, int goodsId, String name, String price, String linePrice, int forWard, String lowDisCount, String coupon, String perpleNum, String shengPrice) {
        this.image = image;
        this.goodsId = goodsId;
        this.name = name;
        this.price = price;
        this.linePrice = linePrice;
        this.forWard = forWard;
        this.lowDisCount = lowDisCount;
        this.coupon = coupon;
        this.perpleNum = perpleNum;
        this.shengPrice = shengPrice;
    }

//    public HotGoodsBean(Integer image, int goodsId, String name, String price, String linePrice, int forWard) {
//        this.image = image;
//        this.goodsId = goodsId;
//        this.name = name;
//        this.price = price;
//        this.linePrice = linePrice;
//        this.forWard = forWard;
//    }

    public String getLowDisCount() {
        return lowDisCount;
    }

    public void setLowDisCount(String lowDisCount) {
        this.lowDisCount = lowDisCount;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getPerpleNum() {
        return perpleNum;
    }

    public void setPerpleNum(String perpleNum) {
        this.perpleNum = perpleNum;
    }

    public String getShengPrice() {
        return shengPrice;
    }

    public void setShengPrice(String shengPrice) {
        this.shengPrice = shengPrice;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLinePrice() {
        return linePrice;
    }

    public void setLinePrice(String linePrice) {
        this.linePrice = linePrice;
    }

    public int getForWard() {
        return forWard;
    }

    public void setForWard(int forWard) {
        this.forWard = forWard;
    }

}
