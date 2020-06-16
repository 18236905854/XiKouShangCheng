package com.xk.mall.model.eventbean;

/**
 * 切换城市 ----经纬度
 */
public class LocationPointBean {
    private double longitude;
    private double latitude;

    public LocationPointBean(double longitude, double latitude) {
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
