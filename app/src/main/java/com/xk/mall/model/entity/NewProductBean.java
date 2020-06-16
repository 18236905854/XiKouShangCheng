package com.xk.mall.model.entity;

public class NewProductBean {
    private int logo;
    private String name;
    private int newPrice;
    private int linePrice;
    private int type;//活动类型(1:买一赠二(吾G)，2: 全球买手, 3：0元竞拍 4:多买多折，5:砍价得红包，6:定制拼团, 7:新人专区)

    public NewProductBean(int logo, String name, int newPrice, int linePrice, int type) {
        this.logo = logo;
        this.name = name;
        this.newPrice = newPrice;
        this.linePrice = linePrice;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
