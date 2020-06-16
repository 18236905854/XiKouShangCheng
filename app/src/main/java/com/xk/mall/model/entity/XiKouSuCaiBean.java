package com.xk.mall.model.entity;

import java.io.Serializable;
import java.util.List;

public class XiKouSuCaiBean implements Serializable {
    private String img;
    private String name;
    private String time;
    private String content;
    private List<String> photoInfoList;

    public XiKouSuCaiBean(String img, String name, String time, String content, List<String> photoInfoList) {
        this.img = img;
        this.name = name;
        this.time = time;
        this.content = content;
        this.photoInfoList = photoInfoList;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPhotoInfoList() {
        return photoInfoList;
    }

    public void setPhotoInfoList(List<String> photoInfoList) {
        this.photoInfoList = photoInfoList;
    }
}
