package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * 提现账号 bean
 */
public class WithdrawAccountBean implements Serializable {

    /**
     * id : 5d4165d873f4d700018bc9c4
     * userId : 5d1abda2f7748700013e5ac4
     * channel : 3
     * account : 6236682920002663662
     * bankName : XX银行
     * branchName : 长沙商学院支行
     * bankLocation : 桐梓坡路717号
     * mobile : null
     * validaCode : null
     */
    private String id;
    private String userId;
    private int channel;//账号类型：1：微信， 2：支付宝，3：银行卡，4：信用卡，5：存折
    private String account;
    private String bankName;
    private String image;//银行图标
    private String branchName;
    private String bankLocation;
    private String mobile;

    public String getImage() {
        return image;
    }

    public void setImager(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getBankLocation() {
        return bankLocation;
    }

    public void setBankLocation(String bankLocation) {
        this.bankLocation = bankLocation;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
