package com.xk.mall.model.request;

/**
 * ClassName ManyGoodsDetailActivity
 * Description 多买多折 商品详情
 * Author
 * Date
 * Version
 */
public class ModifyBuyerNumInBuyerCartRequestBody {
//    {
//        "buyerCartId": "",
//            "buyerNumber": 0
//    }
    private String buyerCartId;//购物车ID
    private int buyerNumber;//购买数量

    public String getBuyerCartId() {
        return buyerCartId;
    }

    public void setBuyerCartId(String buyerCartId) {
        this.buyerCartId = buyerCartId;
    }

    public int getBuyerNumber() {
        return buyerNumber;
    }

    public void setBuyerNumber(int buyerNumber) {
        this.buyerNumber = buyerNumber;
    }
}
