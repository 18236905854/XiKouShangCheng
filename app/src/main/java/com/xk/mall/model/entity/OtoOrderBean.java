package com.xk.mall.model.entity;

import java.io.Serializable;

public class OtoOrderBean implements Serializable {


    /**
     * address :
     * areaName :
     * cityName :
     * imageUrl :
     * industryName :
     * offerAmount : 0
     * payAmount : 0
     * payTime :
     * provinceName :
     * shopName :
     * state : 0
     * totalAmount : 0
     */

    private String address;
    private String areaName;
    private String cityName;
    private String imageUrl;
    private String industryName;
    private int offerAmount;
    private int payAmount;
    private String payTime;
    private String provinceName;
    private String shopName;
    private int state;
    private int totalAmount;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public int getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(int offerAmount) {
        this.offerAmount = offerAmount;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
