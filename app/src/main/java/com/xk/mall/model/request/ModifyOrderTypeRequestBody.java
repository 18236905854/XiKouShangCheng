package com.xk.mall.model.request;

/**
 * ClassName ModifyOrderTypeRequestBody
 * Description 修改全球买手订单处理方式请求体
 * Author 卿凯
 * Date 2019/7/15/015
 * Version V1.0
 */
public class ModifyOrderTypeRequestBody {

    /**
     * buyerId :
     * orderNo :
     * processingMethod : 0
     */

    private String buyerId;//买家用户ID
    private String orderNo;//订单号
    private int shareModel;//分享模式: 1-分享给好友;2-寄卖;3-两者都有
    private int processingMethod;//处理方式;1-自提;2-寄卖

    public int getShareModel() {
        return shareModel;
    }

    public void setShareModel(int shareModel) {
        this.shareModel = shareModel;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getProcessingMethod() {
        return processingMethod;
    }

    public void setProcessingMethod(int processingMethod) {
        this.processingMethod = processingMethod;
    }
}
