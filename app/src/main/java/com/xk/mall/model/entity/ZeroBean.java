package com.xk.mall.model.entity;

/**
 * ClassName ZeroBean
 * Description 0元竞拍数据Bean
 * Author 卿凯
 * Date 2019/6/22/022
 * Version V1.0
 */
public class ZeroBean {
    public String logo = "";//图片
    public String name = "";//产品名称
    public double price = 0;//实际价格
    public long endTime = 0;//倒计时
    public int type = 0;//竞拍类型  0:默认 1:抢拍中 2:拍中付款  3:已结束

    public ZeroBean(String logo, String name, double price, long endTime, int type) {
        this.logo = logo;
        this.name = name;
        this.price = price;
        this.endTime = endTime;
        this.type = type;
    }
}
