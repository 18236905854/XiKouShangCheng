package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName OrderPageBean
 * Description 订单分页Bean
 * Author 卿凯
 * Date 2019/7/13/013
 * Version V1.0
 */
public class OrderPageBean {
    public List<OrderBean> result;
    public int pageCount = 0;//当前页数量
    public int totalCount = 0;//总数量
}
