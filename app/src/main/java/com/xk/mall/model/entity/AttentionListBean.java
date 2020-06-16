package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName AttentionListBean
 * Description 我的关注页面返回的Bean
 * Author 卿凯
 * Date 2019/6/17/017
 * Version V1.0
 */
public class AttentionListBean {
    public List<AttentionBean> result;
    public int totalCount = 0;//总数
    public int pageCount = 0;//当前页数
}
