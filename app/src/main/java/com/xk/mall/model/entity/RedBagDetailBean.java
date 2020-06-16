package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * 红包明细
 */
public class RedBagDetailBean implements Serializable {
    private int id;
    private String title;
    private String content;
    private String date;
    private String money;

    public RedBagDetailBean(int id, String title, String content, String date, String money) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
