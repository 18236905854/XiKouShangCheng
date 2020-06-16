package com.xk.mall.model.entity;

/**
 * @ClassName: SellOrderDetailBean
 * @Description: 寄卖订单详情对象
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class SellOrderDetailBean {


    /**
     * orderNo : CO201909251447389469571
     * externalPlatformNo : null
     * merchantName : 你最珍贵yy
     * buyerAccount : 18075719999
     * buyerNickName : xk9999200
     * buyerHeadImage : https://oss-shop-test.luluxk.com//default.png
     * payAmount : 229900
     * postage : 0
     * orderTime : 2019-09-25 14:47:39
     * payTime : 2019-09-25 14:49:41
     * payInvalidTime : 30
     * shipTime : null
     * confirmReceiptTime : null
     * lastUpdateTime : 2019-09-25 14:49:41
     * state : 2
     * commoditySalePrice : 229900
     * goodsVo : {"goodsId":"5d78884026c3400001e4717e","goodsCode":null,"goodsName":"志高新款破壁料理机家用加热多功能全自动德国小型辅食养生豆浆","goodsImageUrl":"https://oss-shop-test.luluxk.com/goods/20190911/2c2d1149f61d457f8cf01bee27373c61.jpg","commodityId":"5d78884026c3400001e47182","commoditySpec":"","commodityModel":"玫瑰金","commodityQuantity":1,"postage":null}
     */

    private String orderNo;//订单号
    private String externalPlatformNo;//支付流水号(外部平台号)
    private String merchantName;//商家名称
    private String buyerAccount;//买家账号
    private String buyerNickName;//买家昵称
    private String buyerHeadImage;//买家头像
    private int payAmount;//实付金额
    private int postage;//邮费
    private String orderTime;//下单时间
    private String payTime;//支付时间
    private int payInvalidTime;//支付时效时长
    private String shipTime;//发货时间
    private String confirmReceiptTime;//确定收货时间
    private String lastUpdateTime;//最后修改时间
    private int state;//订单状态
    private int commoditySalePrice;//销售价
    private String remarks;//订单备注
    private GoodsVoBean goodsVo;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getExternalPlatformNo() {
        return externalPlatformNo;
    }

    public void setExternalPlatformNo(String externalPlatformNo) {
        this.externalPlatformNo = externalPlatformNo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public String getBuyerNickName() {
        return buyerNickName;
    }

    public void setBuyerNickName(String buyerNickName) {
        this.buyerNickName = buyerNickName;
    }

    public String getBuyerHeadImage() {
        return buyerHeadImage;
    }

    public void setBuyerHeadImage(String buyerHeadImage) {
        this.buyerHeadImage = buyerHeadImage;
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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public int getPayInvalidTime() {
        return payInvalidTime;
    }

    public void setPayInvalidTime(int payInvalidTime) {
        this.payInvalidTime = payInvalidTime;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public String getConfirmReceiptTime() {
        return confirmReceiptTime;
    }

    public void setConfirmReceiptTime(String confirmReceiptTime) {
        this.confirmReceiptTime = confirmReceiptTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCommoditySalePrice() {
        return commoditySalePrice;
    }

    public void setCommoditySalePrice(int commoditySalePrice) {
        this.commoditySalePrice = commoditySalePrice;
    }

    public GoodsVoBean getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVoBean goodsVo) {
        this.goodsVo = goodsVo;
    }

    public static class GoodsVoBean {
        /**
         * goodsId : 5d78884026c3400001e4717e
         * goodsCode : null
         * goodsName : 志高新款破壁料理机家用加热多功能全自动德国小型辅食养生豆浆
         * goodsImageUrl : https://oss-shop-test.luluxk.com/goods/20190911/2c2d1149f61d457f8cf01bee27373c61.jpg
         * commodityId : 5d78884026c3400001e47182
         * commoditySpec :
         * commodityModel : 玫瑰金
         * commodityQuantity : 1
         * postage : null
         */

        private String goodsId;//商品ID
        private String goodsCode;//商品货号
        private String goodsName;//商品名称
        private String goodsImageUrl;//商品图片
        private String commodityId;//商品SKU ID
        private String commoditySpec;//商品规格
        private String commodityModel;//商品型号
        private int commodityQuantity;//数量
        private int postage;//邮费

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

        public int getPostage() {
            return postage;
        }

        public void setPostage(int postage) {
            this.postage = postage;
        }
    }
}
