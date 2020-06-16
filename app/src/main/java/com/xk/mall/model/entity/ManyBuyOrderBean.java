package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName ManyBuyOrderBean
 * Description 多买多折订单的Bean
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class ManyBuyOrderBean {


    /**
     * result : [{"orderNo":"DI20190718111800000660","tradeNo":"DI20190718111800000918","merchantName":"滚球的店","goodsId":"5d2ef5a53320780001819ca8","goodsName":"安踏可口可乐联名鞋","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190717/1a1f482472f945998c3ed352fa389b5d.jpg","commoditySpec":"40","commodityModel":"红","commoditySalePrice":40000,"payAmount":2160000,"postage":0,"commodityQuantity":1},{"orderNo":"DI20190718111800000457","tradeNo":"DI20190718111800000918","merchantName":"滚球的店","goodsId":"5d25e8fada12d50001715846","goodsName":"华为P30","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190710/0064828caa45403bb70688cf6a6b241c.jpg","commoditySpec":"1","commodityModel":"P30","commoditySalePrice":400000,"payAmount":2160000,"postage":0,"commodityQuantity":1},{"orderNo":"DI20190718111300000852","tradeNo":"DI20190718111300000149","merchantName":"滚球的店","goodsId":"5d25e8fada12d50001715846","goodsName":"华为P30","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190710/0064828caa45403bb70688cf6a6b241c.jpg","commoditySpec":"1","commodityModel":"P30","commoditySalePrice":400000,"payAmount":2160000,"postage":0,"commodityQuantity":1},{"orderNo":"DI20190718111300000501","tradeNo":"DI20190718111300000149","merchantName":"滚球的店","goodsId":"5d2ef5a53320780001819ca8","goodsName":"安踏可口可乐联名鞋","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190717/1a1f482472f945998c3ed352fa389b5d.jpg","commoditySpec":"40","commodityModel":"红","commoditySalePrice":40000,"payAmount":2160000,"postage":0,"commodityQuantity":1},{"orderNo":"DI20190718105200000445","tradeNo":"DI20190718105200000480","merchantName":"滚球的店","goodsId":"5d25e8fada12d50001715846","goodsName":"华为P30","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190710/0064828caa45403bb70688cf6a6b241c.jpg","commoditySpec":"1","commodityModel":"P30","commoditySalePrice":400000,"payAmount":2000000,"postage":0,"commodityQuantity":1},{"orderNo":"DI20190717221700000413","tradeNo":"DI20190717221700000800","merchantName":"滚球的店","goodsId":"5d25e8fada12d50001715846","goodsName":"华为P30","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190710/0064828caa45403bb70688cf6a6b241c.jpg","commoditySpec":"1","commodityModel":"P30","commoditySalePrice":400000,"payAmount":412000,"postage":0,"commodityQuantity":1},{"orderNo":"DI20190717221700000397","tradeNo":"DI20190717221700000800","merchantName":"滚球的店","goodsId":"5d2694d63ae2f500012b6fcf","goodsName":"超短裙","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/38e00116d5844e919aa8fd45e2b16957.jpg","commoditySpec":"1","commodityModel":"L","commoditySalePrice":12000,"payAmount":412000,"postage":0,"commodityQuantity":1},{"orderNo":"DI20190717210600000634","tradeNo":"DI20190717210600000678","merchantName":"滚球的店","goodsId":"5d2694d63ae2f500012b6fcf","goodsName":"超短裙","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/38e00116d5844e919aa8fd45e2b16957.jpg","commoditySpec":"1","commodityModel":"X","commoditySalePrice":12000,"payAmount":12000,"postage":0,"commodityQuantity":1},{"orderNo":"DI20190717205000000011","tradeNo":"DI20190717205000000510","merchantName":"滚球的店","goodsId":"5d2694d63ae2f500012b6fcf","goodsName":"超短裙","goodsImageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190711/38e00116d5844e919aa8fd45e2b16957.jpg","commoditySpec":"1","commodityModel":"X","commoditySalePrice":12000,"payAmount":12000,"postage":0,"commodityQuantity":1}]
     * totalCount : 9
     * pageCount : 1
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
         * orderNo : DI20190718111800000660
         * tradeNo : DI20190718111800000918
         * merchantName : 滚球的店
         * goodsId : 5d2ef5a53320780001819ca8
         * goodsName : 安踏可口可乐联名鞋
         * goodsImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190717/1a1f482472f945998c3ed352fa389b5d.jpg
         * commoditySpec : 40
         * commodityModel : 红
         * commoditySalePrice : 40000
         * payAmount : 2160000
         * postage : 0
         * commodityQuantity : 1
         */

        private String orderNo;//订单ID
        private String tradeNo;//主订单号
        private String merchantName;//商家名称
        private String goodsId;//商品ID
        private String goodsName;//商品名称
        private String goodsImageUrl;//商品主图
        private String commoditySpec;//规格
        private String commodityModel;//型号
        private int commoditySalePrice;//销售价 单价
        private int payAmount;//订单实际支付金额
        private int postage;//邮费
        private int commodityQuantity;//购买数量
        private int state;//订单状态

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
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

        public int getCommoditySalePrice() {
            return commoditySalePrice;
        }

        public void setCommoditySalePrice(int commoditySalePrice) {
            this.commoditySalePrice = commoditySalePrice;
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

        public int getCommodityQuantity() {
            return commodityQuantity;
        }

        public void setCommodityQuantity(int commodityQuantity) {
            this.commodityQuantity = commodityQuantity;
        }
    }
}
