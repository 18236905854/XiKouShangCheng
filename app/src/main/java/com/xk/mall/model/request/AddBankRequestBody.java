package com.xk.mall.model.request;

/**
 * 添加银行账户
 */
public class AddBankRequestBody {

    /**
     * account :
     * bankLocation :
     * bankName :
     * branchName :
     * channel : 0
     * mobile :
     * userId :
     * validaCode :
     */

    private String account;//账号
    private String bankLocation;//开户行所在地
    private String bankName;//银行名称
    private String branchName;//支行名称
    private int channel;//账号类型：1：微信， 2：支付宝，3：银行卡，4：信用卡，5：存折
    private String mobile;
    private String userId;
    private String bankCode;//银行编码
    private String validaCode;//验证码

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankLocation() {
        return bankLocation;
    }

    public void setBankLocation(String bankLocation) {
        this.bankLocation = bankLocation;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getValidaCode() {
        return validaCode;
    }

    public void setValidaCode(String validaCode) {
        this.validaCode = validaCode;
    }
}
