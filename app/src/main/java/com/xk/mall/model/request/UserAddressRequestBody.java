package com.xk.mall.model.request;

/**
 * ClassName UserAddressRequestBody
 * Description 用户地址的请求体
 * Author 卿凯
 * Date 2019/7/6/006
 * Version V1.0
 */
public class UserAddressRequestBody {

    /**
     * address :
     * areaId : 0
     * cityId : 0
     * consigneeMobile :
     * consigneeName :
     * defaultId : 0
     * id :
     * provinceId : 0
     * userId :
     */

    private String address;
    private Integer areaId;
    private int cityId;
    private String consigneeMobile;
    private String consigneeName;
    private int defaultId;
    private String id;
    private int provinceId;
    private String userId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public int getDefaultId() {
        return defaultId;
    }

    public void setDefaultId(int defaultId) {
        this.defaultId = defaultId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
