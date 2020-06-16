package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName GlobalBuyerTitleBean
 * Description 全球买手列表数据Bean
 * Author 卿凯
 * Date 2019/6/22/022
 * Version V1.0
 */
public class GlobalBuyerTitleBean {
    public String title = "";//标题
    public int type = 0;//类型  家居日用，奢侈小众，美食酒水，美妆个护
    public List<GlobalBuyerBean> buyerBeans;//商品列表

    public GlobalBuyerTitleBean(String title, int type, List<GlobalBuyerBean> buyerBeans) {
        this.title = title;
        this.type = type;
        this.buyerBeans = buyerBeans;
    }
}
