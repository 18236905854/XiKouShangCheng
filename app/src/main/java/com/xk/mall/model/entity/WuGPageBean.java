package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName WuGPageBean
 * Description 吾G商品列表返回的Bean
 * Author 卿凯
 * Date 2019/7/12/012
 * Version V1.0
 */
public class WuGPageBean {
    public List<WuGGoodsBean> result;
    public int totalCount = 0;//总数
    public int pageCount = 0;//当前页面条数
}
