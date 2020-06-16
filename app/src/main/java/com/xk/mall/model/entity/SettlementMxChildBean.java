package com.xk.mall.model.entity;

/**
 * ClassName SettlementMxChildBean
 * Description 待结算明细Bean
 * Author 卿凯
 * Date 2019/8/2/002
 * Version V1.0
 */
public class SettlementMxChildBean {

    /**
     * businessType : 0  支持类型/收入类型：1 ：红包分润，2：使用红包，3：红包提现，4：提现手续费，5:拼团退款，6:购物，7:代付支出，8:代付收入
     * changeValue : 0  金额
     * channel : 0  提现渠道
     * createTime :  时间
     * moduleId :   服务ID
     * moduleName :  服务名称
     * operateType : 0  操作类型1，增加 2，扣减
     * refKey :  订单号
     * status : 0   提现状态：1：到账中， 2：已到账，3：提现失败
     */

    private String id;//红包明细ID
    private int businessType;
    private String businessName;//业务类型
    private int changeValue;
    private int channel;
    private String createTime;
    private String moduleId;
    private String moduleName;
    private int operateType;
    private String refKey;
    private String paymentAccount;//付款账号
    private int status;
    private String transferUserName;//转账账户
    private String remark;//转账备注

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public String getTransferName() {
        return transferUserName;
    }

    public void setTransferName(String transferName) {
        this.transferUserName = transferName;
    }

    public String getTransferRemarks() {
        return remark;
    }

    public void setTransferRemarks(String transferRemarks) {
        this.remark = transferRemarks;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public int getBusinessType() {
//        return businessType;
//    }
//
//    public void setBusinessType(int businessType) {
//        this.businessType = businessType;
//    }

    public int getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(int changeValue) {
        this.changeValue = changeValue;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public String getRefKey() {
        return refKey;
    }

    public void setRefKey(String refKey) {
        this.refKey = refKey;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
