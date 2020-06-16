package com.xk.mall.model.entity;

/**
 * ClassName CustomizedFilterBean
 * Description 定制馆筛选Bean
 * Author 卿凯
 * Date 2019/7/2/002
 * Version V1.0
 */
public class CustomizedFilterBean {
    public String key = "";
    public boolean isSelect = false;

    public CustomizedFilterBean(String key, boolean isSelect) {
        this.key = key;
        this.isSelect = isSelect;
    }
}
