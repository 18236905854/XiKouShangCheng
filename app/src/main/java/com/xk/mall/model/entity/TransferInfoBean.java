package com.xk.mall.model.entity;

/**
 * @ClassName: TransferInfoBean
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/10/20/020 15:45
 * @Version: 1.0
 */
public class TransferInfoBean {

    /**
     * id : 5d491e8d2b92be0001dba9bd
     * mobile : 13066979652
     * nickName : xk9652658
     * headUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/user/20190812/b11098d30ac14d91aaa05fc3862be591.png
     * balance : 0
     */

    private String id;//用户ID
    private String mobile;//用户手机号
    private String nickName;//用户昵称
    private String headUrl;//用户头像
    private int balance;//用户可转账余额

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
