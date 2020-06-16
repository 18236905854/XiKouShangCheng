package com.xk.mall.model.entity;

/**
 * ClassName CutListBean
 * Description 砍价列表Bean
 * Author 卿凯
 * Date 2019/6/28/028
 * Version V1.0
 */
public class CutListBean {
    public String logo = "";//头像
    public String name = "";//名字
    public String time = "";//砍价时间
    public double money = 0;//砍掉的价格

    public CutListBean(String logo, String name, String time, double money) {
        this.logo = logo;
        this.name = name;
        this.time = time;
        this.money = money;
    }
}
