package com.xk.mall.model.request;

/**
 * 设置支付密码请求体
 */
public class SetPwdRequestBody {
    private String userId;
    private String payPassword;
    private String confirmPayPassword;
    private int accountType;//1 红包账户
    private String verificationCode;//验证码
    private String mobile;//手机号

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getConfirmPayPassword() {
        return confirmPayPassword;
    }

    public void setConfirmPayPassword(String confirmPayPassword) {
        this.confirmPayPassword = confirmPayPassword;
    }
}
