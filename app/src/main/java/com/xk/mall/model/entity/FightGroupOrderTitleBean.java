package com.xk.mall.model.entity;

/**
 * ClassName OrderTitleBean
 * Description 喜立得和吾G购订单头部Bean
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class FightGroupOrderTitleBean {
    public String title = "";//标题
    public int orderType = 0;//订单类型  0 全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功

    public FightGroupOrderTitleBean(String title, int orderType) {
        this.title = title;
        this.orderType = orderType;
    }
}
