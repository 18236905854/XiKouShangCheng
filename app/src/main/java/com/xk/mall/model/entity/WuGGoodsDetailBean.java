package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName WuGGoodsDetailBean
 * Description 吾G商品详情Bean
 * Author 卿凯
 * Date 2019/7/12/012
 * Version V1.0
 */
public class WuGGoodsDetailBean {

    /**
     * activityType : 1
     * baseRuleModel : {"commodityPostageModel":{"flag":true,"postage":0},"deliveryDuration":null,"consignmentModel":{"isConsignment":null,"consignmentDuration":null},"couponValue":36000,"buyUserLimitModel":{"flag":true,"limitNumber":2}}
     * goodsManageModel : {"id":"5d269cc43ae2f500012b6fe1","merchantId":"888","url":null,"goodsCode":"XXDK18231231","goodsName":"休闲短裤-男","brandId":"5d25e0c2da12d5000171583a","brandName":null,"marketPrice":null,"costPrice":null,"salePrice":null,"stock":null,"cumulativeSales":null,"goodsType":null,"createTime":"2019-07-11T02:19:48.000+0000","lastUpdateTime":"2019-07-11T02:20:05.000+0000","tagId":"5d25e12ada12d5000171583d","tagIds":null,"categoryId":"5d25dd5dda12d50001715817","introduction":"我是商品描述","keyword":null,"postage":0,"logistics":1,"online":1,"isUsed":1,"deleted":0,"checked":2,"checkReason":null,"categoryId1":"5d25dba2da12d50001715800","categoryId2":"5d25dc67da12d50001715805","goodsCount":null,"categoryName":null,"categoryNameLink":"男装-裤子-休闲短裤","goodsImagesList":[{"id":"5d269cc43ae2f500012b6fe2","goodsId":"5d269cc43ae2f500012b6fe1","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/383e9f36502c4a768c95be4e47621721.jpg","type":1,"rankNum":1,"createTime":"2019-07-11T02:19:48.000+0000"},{"id":"5d269cc43ae2f500012b6fe6","goodsId":"5d269cc43ae2f500012b6fe1","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/0cd33cffd6134c17ad5de4996edbeb8c.jpg","type":2,"rankNum":1,"createTime":"2019-07-11T02:19:48.000+0000"},{"id":"5d269cc43ae2f500012b6fe3","goodsId":"5d269cc43ae2f500012b6fe1","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/15fe7644f29f4ab2a23b43a52f4c0758.jpg","type":1,"rankNum":2,"createTime":"2019-07-11T02:19:48.000+0000"},{"id":"5d269cc43ae2f500012b6fe7","goodsId":"5d269cc43ae2f500012b6fe1","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/d453b14602464ed2b33d804f416ab1af.jpg","type":2,"rankNum":2,"createTime":"2019-07-11T02:19:48.000+0000"},{"id":"5d269cc43ae2f500012b6fe4","goodsId":"5d269cc43ae2f500012b6fe1","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/2af98af35a3d4b0889e6b6c4a8f92d32.jpg","type":1,"rankNum":3,"createTime":"2019-07-11T02:19:48.000+0000"},{"id":"5d269cc43ae2f500012b6fe8","goodsId":"5d269cc43ae2f500012b6fe1","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/e5b11aac9dcf4b3c82e8a2501ae5c006.jpg","type":2,"rankNum":3,"createTime":"2019-07-11T02:19:48.000+0000"},{"id":"5d269cc43ae2f500012b6fe5","goodsId":"5d269cc43ae2f500012b6fe1","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/47388c77efb243a6a8593f38ac08ea84.jpg","type":1,"rankNum":4,"createTime":"2019-07-11T02:19:48.000+0000"},{"id":"5d269cc43ae2f500012b6fe9","goodsId":"5d269cc43ae2f500012b6fe1","url":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/871a8a6ca6f04f238b753a4e0adac00c.jpg","type":2,"rankNum":4,"createTime":"2019-07-11T02:19:48.000+0000"}],"goodsSkuList":[{"id":"5d269cc43ae2f500012b6fea","goodsId":"5d269cc43ae2f500012b6fe1","goodsUnit":"1","goodsModel":"X","goodsType":"1","stock":1000,"virtualStock":null,"freezeStock":null,"marketPrice":32000,"costPrice":8000,"salePrice":18000,"deleted":0,"status":1,"startNum":1},{"id":"5d269cc43ae2f500012b6feb","goodsId":"5d269cc43ae2f500012b6fe1","goodsUnit":"1","goodsModel":"M","goodsType":"1","stock":1000,"virtualStock":null,"freezeStock":null,"marketPrice":32000,"costPrice":8000,"salePrice":18000,"deleted":0,"status":1,"startNum":1},{"id":"5d269cc43ae2f500012b6fec","goodsId":"5d269cc43ae2f500012b6fe1","goodsUnit":"1","goodsModel":"L","goodsType":"1","stock":1000,"virtualStock":null,"freezeStock":null,"marketPrice":32000,"costPrice":8000,"salePrice":18000,"deleted":0,"status":1,"startNum":1}],"goodsCheckLogList":null,"businessName":null,"businessMobile":null}
     */

    private int activityType;
    private BaseRuleModelBean baseRuleModel;
    private GoodsServerDetailBean goodsManageModel;

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public BaseRuleModelBean getBaseRuleModel() {
        return baseRuleModel;
    }

    public void setBaseRuleModel(BaseRuleModelBean baseRuleModel) {
        this.baseRuleModel = baseRuleModel;
    }

    public GoodsServerDetailBean getGoodsManageModel() {
        return goodsManageModel;
    }

    public void setGoodsManageModel(GoodsServerDetailBean goodsManageModel) {
        this.goodsManageModel = goodsManageModel;
    }

    public static class BaseRuleModelBean {
        /**
         * commodityPostageModel : {"flag":true,"postage":0}
         * deliveryDuration : null
         * consignmentModel : {"isConsignment":null,"consignmentDuration":null}
         * couponValue : 36000
         * buyUserLimitModel : {"flag":true,"limitNumber":2}
         */

        private CommodityPostageModelBean commodityPostageModel;
        private int deliveryDuration;//发货时长
        private ConsignmentModelBean consignmentModel;
        private int couponValue;
        private BuyUserLimitModelBean buyUserLimitModel;

        public CommodityPostageModelBean getCommodityPostageModel() {
            return commodityPostageModel;
        }

        public void setCommodityPostageModel(CommodityPostageModelBean commodityPostageModel) {
            this.commodityPostageModel = commodityPostageModel;
        }

        public int getDeliveryDuration() {
            return deliveryDuration;
        }

        public void setDeliveryDuration(int deliveryDuration) {
            this.deliveryDuration = deliveryDuration;
        }

        public ConsignmentModelBean getConsignmentModel() {
            return consignmentModel;
        }

        public void setConsignmentModel(ConsignmentModelBean consignmentModel) {
            this.consignmentModel = consignmentModel;
        }

        public int getCouponValue() {
            return couponValue;
        }

        public void setCouponValue(int couponValue) {
            this.couponValue = couponValue;
        }

        public BuyUserLimitModelBean getBuyUserLimitModel() {
            return buyUserLimitModel;
        }

        public void setBuyUserLimitModel(BuyUserLimitModelBean buyUserLimitModel) {
            this.buyUserLimitModel = buyUserLimitModel;
        }

        public static class CommodityPostageModelBean {
            /**
             * flag : true
             * postage : 0
             */

            private boolean flag;
            private int postage;

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public int getPostage() {
                return postage;
            }

            public void setPostage(int postage) {
                this.postage = postage;
            }
        }

        public static class ConsignmentModelBean {
            /**
             * isConsignment : null
             * consignmentDuration : null
             */

            private int isConsignment;//最大寄卖天数
            private int consignmentDuration;//是否支持寄卖(1：支持，2：不支持)

            public int getIsConsignment() {
                return isConsignment;
            }

            public void setIsConsignment(int isConsignment) {
                this.isConsignment = isConsignment;
            }

            public int getConsignmentDuration() {
                return consignmentDuration;
            }

            public void setConsignmentDuration(int consignmentDuration) {
                this.consignmentDuration = consignmentDuration;
            }
        }

        public static class BuyUserLimitModelBean {
            /**
             * flag : true
             * limitNumber : 2
             */

            private boolean flag;
            private int limitNumber;

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public int getLimitNumber() {
                return limitNumber;
            }

            public void setLimitNumber(int limitNumber) {
                this.limitNumber = limitNumber;
            }
        }
    }

}
