package com.xk.mall.model.entity;

public class FlowBean {
    private int id;
    private int status;
    private String title;
    private String time;

    public FlowBean(int id, int status, String title, String time) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
