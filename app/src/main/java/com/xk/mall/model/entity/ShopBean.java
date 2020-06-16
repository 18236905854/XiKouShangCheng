package com.xk.mall.model.entity;

import java.util.List;

/**
 * 店铺详情
 */
public class ShopBean {
    /**
     * id : 5d1590b2594c300001b0dd6a
     * shopName : 你最珍贵
     * startTime : 2019-06-19 15:40:44
     * endTime : 2019-10-19 15:40:44
     * province : 120000
     * city : 120100
     * area : 120102
     * address : 湖南省长沙市岳麓区麓谷街道麓枫路98麓谷锦园
     * mobile : 17773172177
     * description : 你最珍贵的简介~~~
     * discountRate : 0
     * style : 0
     * industry1 : null   //行业
     * distance : null
     * fanCnt : 0
     * popCnt : 0
     * imageList : [{"id":"5d187cdeb4e57100012a59e1","shopId":"5d1590b2594c300001b0dd6a","type":2,"imageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190630/21a9481c71654c2b9483d35c62378770.png","createTime":"2019-06-30 17:11:58","isShow":null},{"id":"5d187cdeb4e57100012a59e2","shopId":"5d1590b2594c300001b0dd6a","type":3,"imageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190622/c3b6fe0ade18452a90ff308879132617.png","createTime":"2019-06-30 17:11:58","isShow":0},{"id":"5d187cdeb4e57100012a59e3","shopId":"5d1590b2594c300001b0dd6a","type":3,"imageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190622/c3b6fe0ade18452a90ff308879132617.png","createTime":"2019-06-30 17:11:58","isShow":0},{"id":"5d187cdeb4e57100012a59e4","shopId":"5d1590b2594c300001b0dd6a","type":3,"imageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190630/7da0446d080b40a59d9ff0c65b8eef96.png","createTime":"2019-06-30 17:11:58","isShow":null}]
     * serviceList : [{"id":"5d187cdeb4e57100012a59e5","shopId":"5d1590b2594c300001b0dd6a","serviceId":"1","serviceName":null,"createTime":"2019-06-30 17:11:58"},{"id":"5d187cdeb4e57100012a59e6","shopId":"5d1590b2594c300001b0dd6a","serviceId":"2","serviceName":null,"createTime":"2019-06-30 17:11:58"}]
     * isFollow : 0
     */

    private String id;
    private String shopName;
    private String startTime;
    private String endTime;
    private int province;
    private int city;
    private int area;
    private String address;
    private String mobile;
    private String description;
    private int discountRate;
    private int style;
    private String distance;
    private int fanCnt;
    private int popCnt;
    private int isFollow;
    private String merchantId;//商家ID
    private List<ImageListBean> imageList;
    private List<ServiceListBean> serviceList;
    private int industry1;//行业

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getIndustry1() {
        return industry1;
    }

    public void setIndustry1(int industry1) {
        this.industry1 = industry1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }




    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getFanCnt() {
        return fanCnt;
    }

    public void setFanCnt(int fanCnt) {
        this.fanCnt = fanCnt;
    }

    public int getPopCnt() {
        return popCnt;
    }

    public void setPopCnt(int popCnt) {
        this.popCnt = popCnt;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public List<ImageListBean> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageListBean> imageList) {
        this.imageList = imageList;
    }

    public List<ServiceListBean> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceListBean> serviceList) {
        this.serviceList = serviceList;
    }

    public static class ImageListBean {
        /**
         * id : 5d187cdeb4e57100012a59e1
         * shopId : 5d1590b2594c300001b0dd6a
         * type : 2
         * imageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190630/21a9481c71654c2b9483d35c62378770.png
         * createTime : 2019-06-30 17:11:58
         * isShow : null
         */

        private String id;
        private String shopId;
        private int type;
        private String imageUrl;
        private String createTime;
        private int isShow;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }
    }

    public static class ServiceListBean {
        /**
         * id : 5d187cdeb4e57100012a59e5
         * shopId : 5d1590b2594c300001b0dd6a
         * serviceId : 1
         * serviceName : null
         * createTime : 2019-06-30 17:11:58
         */

        private String id;
        private String shopId;
        private int serviceId;
        private String serviceName;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public int getServiceId() {
            return serviceId;
        }

        public void setServiceId(int serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
