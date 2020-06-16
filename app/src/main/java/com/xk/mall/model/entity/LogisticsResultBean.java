package com.xk.mall.model.entity;

import java.util.List;

/**
 * @ClassName: LogisticsResultBean
 * @Description: 物流信息返回Bean
 * @Author: 卿凯
 * @Date: 2019/9/10/010 20:42
 * @Version: 1.0
 */
public class LogisticsResultBean {

    /**
     * address :
     * areaName :
     * cityName :
     * consigneeMobile :
     * consigneeName :
     * data : [{"context":"","ftime":"","time":""}]
     * logisticsCompany :
     * logisticsNo :
     * message :
     * provinceName :
     */

    private String address;//用户详细地址
    private String areaName;//区域名称
    private String cityName;//城市名称
    private String consigneeMobile;//收货人手机号
    private String consigneeName;//收货人名称
    private String logisticsCompany;//快递公司
    private String logisticsNo;//快递单号
    private String message;//当data没有数据时显示该字段
    private String provinceName;//省的名称
    private List<DataBean> data;//物流详细数据

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * context :
         * ftime :
         * time :
         */

        private String context;
        private String ftime;
        private String time;

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
