package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * @ClassName UserItem
 * @Description 我的页面中实体类，不涉及到接口的转换
 * @Author 卿凯
 * @Date 2019/6/4/004
 * @Version V1.0
 */
public class UserItem implements Serializable {
    public int resourceId;//图标的ID
    public String title;//文字

    public UserItem(int resourceId, String title) {
        this.resourceId = resourceId;
        this.title = title;
    }
}
