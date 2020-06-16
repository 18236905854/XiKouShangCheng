package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * ClassName AddressBean
 * Description 我的地址Bean
 * Author 卿凯
 * Date 2019/6/10/010
 * Version V1.0
 */
public class AddressBean implements Serializable {

    public String id = "";//地址ID
    public String consigneeName = "";//收货人姓名
    public String consigneeMobile = "";//收货人号码
    public String provinceName = "";//省份名字
    public int provinceId = 0;//收货地址省ID
    public String cityName = "";//城市名字
    public int cityId = 0;//收货地址城市ID
    public String areaName = "";//区域名字
    public int areaId = 0;//收货地址区域ID
    public String address = "";//收货详细地址
    public int defaultId = 0;//默认标识（1：默认地址 0：正常地址）
    public boolean outRange = true;//是否超出配送范围  true是没有超出，false表示超出

    public AddressBean(){
    }

    public AddressBean(String consigneeName, String consigneeMobile, int provinceId, int cityId, int areaId, String address, int defaultId) {
        this.consigneeName = consigneeName;
        this.consigneeMobile = consigneeMobile;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.areaId = areaId;
        this.address = address;
        this.defaultId = defaultId;
    }
}
