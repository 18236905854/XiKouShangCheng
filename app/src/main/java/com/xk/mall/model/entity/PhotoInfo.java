package com.xk.mall.model.entity;

/**
 * 多张图 实体bean
 */
public class PhotoInfo {

    public String url;
    public int w;
    public int h;

    public PhotoInfo(String url, int w, int h) {
        this.url = url;
        this.w = w;
        this.h = h;
    }


}
