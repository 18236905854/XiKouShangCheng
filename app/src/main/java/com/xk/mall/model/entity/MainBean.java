package com.xk.mall.model.entity;

/**
 * File descripition:
 */

public class MainBean {

    private int id;
    private String act_logo;
    private String play_time;
    private String name;
    private String province;
    private String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAct_logo() {
        return act_logo;
    }

    public void setAct_logo(String act_logo) {
        this.act_logo = act_logo;
    }

    public String getPlay_time() {
        return play_time;
    }

    public void setPlay_time(String play_time) {
        this.play_time = play_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "MainBean{" +
                "id=" + id +
                ", act_logo='" + act_logo + '\'' +
                ", play_time='" + play_time + '\'' +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
