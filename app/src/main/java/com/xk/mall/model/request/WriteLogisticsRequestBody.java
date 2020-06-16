package com.xk.mall.model.request;

/**
 * @ClassName: WriteLogisticsRequestBody
 * @Description: 填写物流信息请求体
 * @Author: 卿凯
 * @Date: 2019/12/10/010 14:32
 * @Version: 1.0
 */
public class WriteLogisticsRequestBody {

    /**
     * expressCompany :
     * logisticsNo :
     * orderNo :
     */

    private String expressCompany;
    private String logisticsNo;
    private String refundOrderNo;

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getRefundOrderNo() {
        return refundOrderNo;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }
}
