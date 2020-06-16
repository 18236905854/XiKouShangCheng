package com.xk.mall.model.entity;

import java.util.List;

public class GoodsModel {

    /**
     * name : 颜色
     * value : ["黑色","白色"]
     */

    private String name;
    private List<String> value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
