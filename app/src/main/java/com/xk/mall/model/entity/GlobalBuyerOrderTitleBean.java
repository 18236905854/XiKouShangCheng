package com.xk.mall.model.entity;

/**
 * ClassName GlobalBuyerOrderTitleBean
 * Description 全球买手订单头部Bean
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class GlobalBuyerOrderTitleBean {
    public String title = "";//标题
    public int orderType = 0;//订单状态；不传数据-0全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功

    public GlobalBuyerOrderTitleBean(String title, int orderType) {
        this.title = title;
        this.orderType = orderType;
    }
}
