package com.xk.mall.model.entity;

public class NewPersonBean {
    private int logo;
    private String name;
    private int newPrice;
    private int linePrice;

    public NewPersonBean(int logo, String name, int newPrice, int linePrice) {
        this.logo = logo;
        this.name = name;
        this.newPrice = newPrice;
        this.linePrice = linePrice;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(int newPrice) {
        this.newPrice = newPrice;
    }

    public int getLinePrice() {
        return linePrice;
    }

    public void setLinePrice(int linePrice) {
        this.linePrice = linePrice;
    }
}
