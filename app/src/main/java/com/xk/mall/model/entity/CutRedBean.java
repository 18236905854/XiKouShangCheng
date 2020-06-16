package com.xk.mall.model.entity;

/**
 * @ClassName: CutRedBean
 * @Description: 喜立得红包对象
 * @Author: 卿凯
 * @Version: ${VERSION}
 */
public class CutRedBean {

    /**
     * id : 5db794f0ff70670001428699
     * bargainScheduleId : 5db790d2ff70670001428696
     * assistUserId : 5d1cb1b386950c0001137a1f
     * assistUserName : 你最珍贵
     * assistUserHeadImageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/user/20190821/da329657877a40f39740f6fca8a7944d.png
     * bargainPrice : 18948
     * redPackAmount : 3304
     * createTime : 2019-10-29 09:25:04
     */

    private String id;//规则ID
    private String bargainScheduleId;//砍价进度ID
    private String assistUserId;//帮砍用户ID
    private String assistUserName;//帮砍用户昵称
    private String assistUserHeadImageUrl;//帮砍用户头像
    private int bargainPrice;//帮砍价格
    private int redPackAmount;//发送红包金额
    private String createTime;//参与时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBargainScheduleId() {
        return bargainScheduleId;
    }

    public void setBargainScheduleId(String bargainScheduleId) {
        this.bargainScheduleId = bargainScheduleId;
    }

    public String getAssistUserId() {
        return assistUserId;
    }

    public void setAssistUserId(String assistUserId) {
        this.assistUserId = assistUserId;
    }

    public String getAssistUserName() {
        return assistUserName;
    }

    public void setAssistUserName(String assistUserName) {
        this.assistUserName = assistUserName;
    }

    public String getAssistUserHeadImageUrl() {
        return assistUserHeadImageUrl;
    }

    public void setAssistUserHeadImageUrl(String assistUserHeadImageUrl) {
        this.assistUserHeadImageUrl = assistUserHeadImageUrl;
    }

    public int getBargainPrice() {
        return bargainPrice;
    }

    public void setBargainPrice(int bargainPrice) {
        this.bargainPrice = bargainPrice;
    }

    public int getRedPackAmount() {
        return redPackAmount;
    }

    public void setRedPackAmount(int redPackAmount) {
        this.redPackAmount = redPackAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
