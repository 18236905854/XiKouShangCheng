package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName SellOrderPageBean
 * Description 我的寄卖订单列表分页Bean
 * Author 卿凯
 * Date 2019/7/19/019
 * Version V1.0
 */
public class SellOrderPageBean {
    public List<SellOrderBean> result;
    public int totalCount = 0;//总数
    public int pageCount = 0;//页数
}
