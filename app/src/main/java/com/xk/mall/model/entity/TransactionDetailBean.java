package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * 交易明细
 */
public class TransactionDetailBean implements Serializable {
    private int id;
    private String title;
    private String content;
    private String date;
    private String money;
    private int type;//0 商城订单  1 线下订单

    public TransactionDetailBean(int id, String title, String content, String date, String money,int type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.money = money;
        this.type=type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
