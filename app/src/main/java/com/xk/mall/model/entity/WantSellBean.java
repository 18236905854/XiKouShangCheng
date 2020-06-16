package com.xk.mall.model.entity;

/**
 * ClassName WantSellBean
 * Description 我要寄卖订单Bean
 * Author 卿凯
 * Date 2019/6/27/027
 * Version V1.0
 */
public class WantSellBean {
    public String shopName = "";//店铺名称
    public String name = "";//商品名称
    public String logo = "";//商品图片
    public double realPrice = 0;//原价
    public double discountPrice = 0;//折扣价
    public int coupon = 0;//优惠券
    public int type = 0;//订单类型  1:已付款  2:待发货
    public String endTime = "";//剩余时间
    public int endType = 0;//是否已经过了寄卖期  0:未过，显示剩余时间  1:已过，显示申请寄卖过期

    public WantSellBean(String shopName, String name, String logo, double realPrice, double discountPrice, int coupon, int type, String endTime, int endType) {
        this.shopName = shopName;
        this.name = name;
        this.logo = logo;
        this.realPrice = realPrice;
        this.discountPrice = discountPrice;
        this.coupon = coupon;
        this.type = type;
        this.endTime = endTime;
        this.endType = endType;
    }
}
