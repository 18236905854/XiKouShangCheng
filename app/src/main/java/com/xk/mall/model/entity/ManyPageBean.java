package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName ManyPageBean
 * Description 多买多折页面分页的Bean
 * Author 卿凯
 * Date 2019/7/7/007
 * Version V1.0
 */
public class ManyPageBean {
    public List<ManyBean> result;
    public int totalCount = 0;//当前页返回的总数
    public int pageCount = 0;//当前页数
}
