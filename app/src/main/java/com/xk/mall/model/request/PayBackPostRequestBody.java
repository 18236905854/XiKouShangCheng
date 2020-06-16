package com.xk.mall.model.request;

import java.util.List;

/**
 * @ClassName: PayBackPostRequestBody
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/12/9/009 9:58
 * @Version: 1.0
 */
public class PayBackPostRequestBody {


    /**
     * activityType : 0
     * goodsId :
     * orderNo :
     * purchaserPhone :
     * salesReturnImageList : []
     * salesReturnMsg :
     */

    private int activityType;
    private String goodsId;
    private String orderNo;
    private String purchaserPhone;
    private String salesReturnMsg;
    private List<String> salesReturnImageList;

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPurchaserPhone() {
        return purchaserPhone;
    }

    public void setPurchaserPhone(String purchaserPhone) {
        this.purchaserPhone = purchaserPhone;
    }

    public String getSalesReturnMsg() {
        return salesReturnMsg;
    }

    public void setSalesReturnMsg(String salesReturnMsg) {
        this.salesReturnMsg = salesReturnMsg;
    }

    public List<?> getSalesReturnImageList() {
        return salesReturnImageList;
    }

    public void setSalesReturnImageList(List<String> salesReturnImageList) {
        this.salesReturnImageList = salesReturnImageList;
    }
}
