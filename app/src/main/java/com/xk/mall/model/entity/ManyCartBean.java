package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * ClassName ManyCartsBean
 * Description 购物车数据Bean
 * Author 卿凯
 * Date 2019/6/26/026
 * Version V1.0
 */
public class ManyCartBean implements Serializable {
    public String shopName = "";//店铺名称
    public String logo = "";//商品图标
    public String name = "";//商品名称
    public String sku = "";//商品规格
    public double nowPrice = 0;//当前价格
    public double realPrice = 0;//原始价格
    public int number = 1;//当前件数

    public int position = 0;//选中的顺序
    public int discount = 0;//折扣  购物车中商品的折扣
    public int secondDiscount = 0;//折扣  第二件商品的折扣
    public int thirdDiscount = 0;//折扣  第三件商品的折扣
//    public boolean isSelect = false;//是否被选中

    public ManyCartBean(String shopName, String logo, String name, String sku, double nowPrice, double realPrice, int number) {
        this.shopName = shopName;
        this.logo = logo;
        this.name = name;
        this.sku = sku;
        this.nowPrice = nowPrice;
        this.realPrice = realPrice;
        this.number = number;
    }
}
