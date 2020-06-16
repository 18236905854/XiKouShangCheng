package com.xk.mall.model.request;

/**
 * @ClassName: InsertOrUpdateAddressRequestBody
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/11/21/021 15:23
 * @Version: 1.0
 */
public class InsertOrUpdateAddressRequestBody {

    /**
     * orderId :
     * receiptAddressRef :
     */

    private String orderNo;//订单号
    private String receiptAddressRef;//地址ID

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReceiptAddressRef() {
        return receiptAddressRef;
    }

    public void setReceiptAddressRef(String receiptAddressRef) {
        this.receiptAddressRef = receiptAddressRef;
    }
}
