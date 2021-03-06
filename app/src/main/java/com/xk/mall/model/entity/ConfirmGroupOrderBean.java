package com.xk.mall.model.entity;

/**
 * 确认定制拼团订单
 */
public class ConfirmGroupOrderBean {

    /**
     * orderNo : FG20190716163900000834
     * payAmount : 30005
     * postage : 5
     * orderTime : 2019-07-16 16:39:27
     * address : {"id":"5d2d6be14a125c0001448758","userId":"5d1abda2f7748700013e5ac4","consigneeName":"小新","consigneeMobile":"13625457823","provinceId":430000,"provinceName":"湖南省","cityId":430100,"cityName":"长沙市","areaId":430104,"areaName":"岳麓区","address":"武汉路236号"}
     * goods : {"goodsId":"5d27176937fdc000014a33d4","goodsCode":"5d200edb2a32d60001fe42f1","goodsName":"明国旗袍","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg","commodityId":"5d27176937fdc000014a33de","commoditySpec":"均码","commodityModel":"白色","commodityQuantity":1,"postage":null}
     */

    private String orderNo;
    private int payAmount;
    private int postage;
    private String orderTime;
    private AddressBean address;
    private GoodsBean goods;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public int getPostage() {
        return postage;
    }

    public void setPostage(int postage) {
        this.postage = postage;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public static class AddressBean {
        /**
         * id : 5d2d6be14a125c0001448758
         * userId : 5d1abda2f7748700013e5ac4
         * consigneeName : 小新
         * consigneeMobile : 13625457823
         * provinceId : 430000
         * provinceName : 湖南省
         * cityId : 430100
         * cityName : 长沙市
         * areaId : 430104
         * areaName : 岳麓区
         * address : 武汉路236号
         */

        private String id;
        private String userId;
        private String consigneeName;
        private String consigneeMobile;
        private int provinceId;
        private String provinceName;
        private int cityId;
        private String cityName;
        private int areaId;
        private String areaName;
        private String address;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getConsigneeName() {
            return consigneeName;
        }

        public void setConsigneeName(String consigneeName) {
            this.consigneeName = consigneeName;
        }

        public String getConsigneeMobile() {
            return consigneeMobile;
        }

        public void setConsigneeMobile(String consigneeMobile) {
            this.consigneeMobile = consigneeMobile;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class GoodsBean {
        /**
         * goodsId : 5d27176937fdc000014a33d4
         * goodsCode : 5d200edb2a32d60001fe42f1
         * goodsName : 明国旗袍
         * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/10ef2c2c11124145a67c55081b9e2af0.jpg
         * commodityId : 5d27176937fdc000014a33de
         * commoditySpec : 均码
         * commodityModel : 白色
         * commodityQuantity : 1
         * postage : null
         */

        private String goodsId;
        private String goodsCode;
        private String goodsName;
        private String goodsImageUrl;
        private String commodityId;
        private String commoditySpec;
        private String commodityModel;
        private int commodityQuantity;
        private Object postage;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsCode() {
            return goodsCode;
        }

        public void setGoodsCode(String goodsCode) {
            this.goodsCode = goodsCode;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsImageUrl() {
            return goodsImageUrl;
        }

        public void setGoodsImageUrl(String goodsImageUrl) {
            this.goodsImageUrl = goodsImageUrl;
        }

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommoditySpec() {
            return commoditySpec;
        }

        public void setCommoditySpec(String commoditySpec) {
            this.commoditySpec = commoditySpec;
        }

        public String getCommodityModel() {
            return commodityModel;
        }

        public void setCommodityModel(String commodityModel) {
            this.commodityModel = commodityModel;
        }

        public int getCommodityQuantity() {
            return commodityQuantity;
        }

        public void setCommodityQuantity(int commodityQuantity) {
            this.commodityQuantity = commodityQuantity;
        }

        public Object getPostage() {
            return postage;
        }

        public void setPostage(Object postage) {
            this.postage = postage;
        }
    }
}
