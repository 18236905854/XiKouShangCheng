package com.xk.mall.model.entity;

/**
 * 确认喜立得订单
 */
public class ConfirmCutOrderBean {

    /**
     * orderNo : CD20190722185700000408
     * payAmount : 35524
     * commoditySalePrice : 39900
     * postage : 35524
     * orderTime : 2019-07-22 18:57:01
     * address : {"id":"5d3184df7e6fc300015f7036","userId":"5d1abda2f7748700013e5ac4","consigneeName":"哇哈哈","consigneeMobile":"13788899999","provinceId":430000,"provinceName":"湖南省","cityId":430100,"cityName":"长沙市","areaId":430104,"areaName":"岳麓区","address":"42号街道"}
     * cutPrice : 35524
     * goods : {"goodsId":"5d316b4aaf8f960001217fd5","goodsCode":"1","goodsName":"稻草人女包2019新款时尚百搭可爱","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png","commodityId":"5d316b4aaf8f960001217fd9","commoditySpec":"白色","commodityModel":"猫咪","commodityQuantity":1,"postage":null}
     */

    private String orderNo;
    private int payAmount;
    private int commoditySalePrice;
    private int postage;
    private String orderTime;
    private AddressBean address;
    private int cutPrice;
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

    public int getCommoditySalePrice() {
        return commoditySalePrice;
    }

    public void setCommoditySalePrice(int commoditySalePrice) {
        this.commoditySalePrice = commoditySalePrice;
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

    public int getCutPrice() {
        return cutPrice;
    }

    public void setCutPrice(int cutPrice) {
        this.cutPrice = cutPrice;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public static class AddressBean {
        /**
         * id : 5d3184df7e6fc300015f7036
         * userId : 5d1abda2f7748700013e5ac4
         * consigneeName : 哇哈哈
         * consigneeMobile : 13788899999
         * provinceId : 430000
         * provinceName : 湖南省
         * cityId : 430100
         * cityName : 长沙市
         * areaId : 430104
         * areaName : 岳麓区
         * address : 42号街道
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
         * goodsId : 5d316b4aaf8f960001217fd5
         * goodsCode : 1
         * goodsName : 稻草人女包2019新款时尚百搭可爱
         * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png
         * commodityId : 5d316b4aaf8f960001217fd9
         * commoditySpec : 白色
         * commodityModel : 猫咪
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
