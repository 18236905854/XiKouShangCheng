package com.xk.mall.model.entity;

/**
 * ClassName NearBean
 * Description 附近模块的Bean
 * Author 卿凯
 * Date 2019/6/18/018
 * Version V1.0
 */
public class NearBean {
    public String title = "";//标题
    public String discount = "";//优惠券
    public String distance = "";//距离
    public String imgOne = "";//第一张图
    public String imgTwo = "";//第二张图
    public String imgThree = "";//第三张图
    public int type = 0;//类型 0：大图  1：三张小图的类型

    public NearBean(String title, String discount, String distance, String imgOne, String imgTwo, String imgThree, int type) {
        this.title = title;
        this.discount = discount;
        this.distance = distance;
        this.imgOne = imgOne;
        this.imgTwo = imgTwo;
        this.imgThree = imgThree;
        this.type = type;
    }
}
