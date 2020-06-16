package com.xk.mall.model.entity;

/**
 * ClassName CutBean
 * Description 喜立得页面Bean
 * Author 卿凯
 * Date 2019/6/24/024
 * Version V1.0
 */
public class CutBean {
    public String id = "";//商品ID
    public String logo = "";//商品的logo
    public String name = "";//产品名称
    public int cutSuccessNum = 0;//砍价成功人数
    public int cutNum = 0;//正在助力人数
    public double nowPrice = 0;//现在的价格
    public double realPrice = 0;//原始价格
    public long endTime = 0;//剩余时间
    public int type = 0;//砍价类型  0:我要砍价 1:砍价成功 2:继续砍价

    public CutBean() {
    }

    public CutBean(String logo, String name, int cutSuccessNum, int cutNum, double nowPrice, double realPrice, long endTime, int type) {
        this.logo = logo;
        this.name = name;
        this.cutSuccessNum = cutSuccessNum;
        this.cutNum = cutNum;
        this.nowPrice = nowPrice;
        this.realPrice = realPrice;
        this.endTime = endTime;
        this.type = type;
    }
}
