package com.xk.mall.model.entity;

/**
 * ClassName GlobalBuyerBean
 * Description 全球买手Bean
 * Author 卿凯
 * Date 2019/6/21/021
 * Version V1.0
 */
public class GlobalBuyerBean {
    public String logo = "";//图片
    public String name = "";//产品名称
    public double realPrice = 0;//实际价格
    public double nowPrice = 0;//当前价格
    public int coupon = 0;//优惠券数

    public GlobalBuyerBean(String logo, String name, double realPrice, double nowPrice, int coupon) {
        this.logo = logo;
        this.name = name;
        this.realPrice = realPrice;
        this.nowPrice = nowPrice;
        this.coupon = coupon;
    }
}
