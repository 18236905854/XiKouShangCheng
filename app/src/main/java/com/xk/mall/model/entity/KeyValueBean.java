package com.xk.mall.model.entity;

/**
 * ClassName KeyValueBean
 * Description key-value形式的Bean 用于订单详情页面
 * Author 卿凯
 * Date 2019/6/26/026
 * Version V1.0
 */
public class KeyValueBean {
    public String key = "";
    public String value = "";

    public KeyValueBean(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
