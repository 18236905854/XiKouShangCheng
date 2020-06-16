package com.xk.mall.model.entity;

import java.util.List;

/**
 * 喜立得 订单
 */
public class CutOrderBean {
    /**
     * result : [{"id":"5d359c2f9475bd0001c3c0be","orderNo":"CD20190722192100000846","merchantName":"Min4","payAmount":35524,"state":1,"goodsName":"稻草人女包2019新款时尚百搭可爱","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png","commoditySpec":"白色","commodityModel":"猫咪","commodityQuantity":1,"commoditySalePrice":39900,"postage":0},{"id":"5d35967d9475bd0001c3c0bb","orderNo":"CD20190722185700000408","merchantName":"Min4","payAmount":35524,"state":1,"goodsName":"稻草人女包2019新款时尚百搭可爱","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png","commoditySpec":"白色","commodityModel":"猫咪","commodityQuantity":1,"commoditySalePrice":39900,"postage":0},{"id":"5d358abd9475bd0001c3c0b4","orderNo":"CD20190722180600000283","merchantName":"Min4","payAmount":35524,"state":1,"goodsName":"稻草人女包2019新款时尚百搭可爱","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png","commoditySpec":"白色","commodityModel":"猫咪","commodityQuantity":1,"commoditySalePrice":39900,"postage":0},{"id":"5d358a659475bd0001c3c0b1","orderNo":"CD20190722180500000316","merchantName":"Min4","payAmount":35524,"state":1,"goodsName":"稻草人女包2019新款时尚百搭可爱","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png","commoditySpec":"白色","commodityModel":"猫咪","commodityQuantity":1,"commoditySalePrice":39900,"postage":0},{"id":"5d3589709475bd0001c3c0ae","orderNo":"CD20190722180100000032","merchantName":"Min4","payAmount":35524,"state":1,"goodsName":"稻草人女包2019新款时尚百搭可爱","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png","commoditySpec":"白色","commodityModel":"猫咪","commodityQuantity":1,"commoditySalePrice":39900,"postage":0},{"id":"5d3589569475bd0001c3c0ab","orderNo":"CD20190722180000000068","merchantName":"Min4","payAmount":35524,"state":1,"goodsName":"稻草人女包2019新款时尚百搭可爱","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png","commoditySpec":"白色","commodityModel":"猫咪","commodityQuantity":1,"commoditySalePrice":39900,"postage":0},{"id":"5d35890f9475bd0001c3c0a8","orderNo":"CD20190722175900000977","merchantName":"Min4","payAmount":35524,"state":1,"goodsName":"稻草人女包2019新款时尚百搭可爱","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png","commoditySpec":"白色","commodityModel":"猫咪","commodityQuantity":1,"commoditySalePrice":39900,"postage":0},{"id":"5d3588ef9475bd0001c3c0a5","orderNo":"CD20190722175900000416","merchantName":"Min4","payAmount":35524,"state":1,"goodsName":"稻草人女包2019新款时尚百搭可爱","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png","commoditySpec":"白色","commodityModel":"猫咪","commodityQuantity":1,"commoditySalePrice":39900,"postage":0},{"id":"5d3588af9475bd0001c3c0a2","orderNo":"CD20190722175800000756","merchantName":"Min4","payAmount":35524,"state":1,"goodsName":"稻草人女包2019新款时尚百搭可爱","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png","commoditySpec":"白色","commodityModel":"猫咪","commodityQuantity":1,"commoditySalePrice":39900,"postage":0},{"id":"5d3588309475bd0001c3c09f","orderNo":"CD20190722175600000649","merchantName":"Min4","payAmount":35524,"state":1,"goodsName":"稻草人女包2019新款时尚百搭可爱","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png","commoditySpec":"白色","commodityModel":"猫咪","commodityQuantity":1,"commoditySalePrice":39900,"postage":0}]
     * totalCount : 16
     * pageCount : 2
     */

    private int totalCount;
    private int pageCount;
    private List<ResultBean> result;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 5d359c2f9475bd0001c3c0be
         * orderNo : CD20190722192100000846
         * merchantName : Min4
         * payAmount : 35524
         * state : 1
         * goodsName : 稻草人女包2019新款时尚百搭可爱
         * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190719/33502bb71f4e4808bb0f367e2535d4d4.png
         * commoditySpec : 白色
         * commodityModel : 猫咪
         * commodityQuantity : 1
         * commoditySalePrice : 39900
         * postage : 0
         */

        private String id;
        private String orderNo;
        private String merchantName;
        private int payAmount;
        private int state;
        private String goodsName;
        private String goodsImageUrl;
        private String bargainScheduleId;//砍价进度ID,当有值时表示砍价的订单
        private String commoditySpec;
        private String commodityModel;
        private int commodityQuantity;
        private int commoditySalePrice;
        private int postage;

        public String getBargainScheduleId() {
            return bargainScheduleId;
        }

        public void setBargainScheduleId(String bargainScheduleId) {
            this.bargainScheduleId = bargainScheduleId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public int getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(int payAmount) {
            this.payAmount = payAmount;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
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
    }
}
