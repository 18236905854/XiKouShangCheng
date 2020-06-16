package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * EventBus
 * 定位实体bean
 */
public class LocationBean implements Serializable {
    private double longitude;//经度
    private double latitude;//纬度

    public LocationBean(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
