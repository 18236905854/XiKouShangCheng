package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * 首页功能清单对应实体
 */
public class HomeFuncationBean implements Serializable {
    private int resourceId;//图标的ID
    private String title;//文字

    public HomeFuncationBean(int resourceId, String title) {
        this.resourceId = resourceId;
        this.title = title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
