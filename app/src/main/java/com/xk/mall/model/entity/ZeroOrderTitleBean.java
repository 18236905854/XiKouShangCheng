package com.xk.mall.model.entity;

/**
 * ClassName OrderTitleBean
 * Description 喜立得和吾G购订单头部Bean
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class ZeroOrderTitleBean {
    public String title = "";//标题
    public int orderType = 0;//订单类型  0全部、1待付款、2待发货、3待收货、4已完成、5拼团失败、6进行中

    public ZeroOrderTitleBean(String title, int orderType) {
        this.title = title;
        this.orderType = orderType;
    }
}
