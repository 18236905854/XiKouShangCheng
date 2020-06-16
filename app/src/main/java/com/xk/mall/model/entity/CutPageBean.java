package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName CutPageBean
 * Description 喜立得分页Bean
 * Author 卿凯
 * Date 2019/7/11/011
 * Version V1.0
 */
public class CutPageBean {
    public List<CutServerBean> result;
    public int totalCount = 0;//总数
    public int pageCount = 0;//当前页面的数量
}
