package com.xk.mall.model.entity;

/**
 * @ClassName: PayPingResultBean
 * @Description: Ping++支付返回对象
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class PayPingResultBean {

    /**
     * orderNo : 202005081033330003789
     * responseBody : {"id":"ch_100200508389284085760006","object":"charge","created":1588905216,"livemode":false,"paid":false,"refunded":false,"reversed":false,"app":"app_1Gqj58ynP0mHeX1q","channel":"wx","order_no":"1258585802258649088","client_ip":"10.0.1.36","amount":50000,"amount_settle":50000,"currency":"cny","subject":"喜扣商城-购买","body":"喜扣商城-购买","extra":{},"time_paid":null,"time_expire":1588912416,"time_settle":null,"transaction_no":null,"refunds":{"object":"list","url":"/v1/charges/ch_100200508389284085760006/refunds","has_more":false,"data":[]},"amount_refunded":0,"failure_code":null,"failure_msg":null,"metadata":{},"credential":{"object":"credential","wx":{"appId":"wxwz5oq9houf9ckydo","partnerId":"4776036683","prepayId":"1101000000200508rj9irp4y9gadhk0e","nonceStr":"471074adf01d117d5ffbd180c782f426","timeStamp":1588905216,"packageValue":"Sign=WXPay","sign":"D5B32302D7AD293375CDC4F7585FAD8E"}},"description":null}
     */

    private String orderNo;
    private String responseBody;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
