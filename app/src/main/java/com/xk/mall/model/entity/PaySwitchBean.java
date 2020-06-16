package com.xk.mall.model.entity;

/**
 * ClassName PaySwitchBean
 * Description 支付开关对象
 * Author 卿凯
 * Date 2019/7/30/030
 * Version V1.0
 */
public class PaySwitchBean {

    /**
     * buyGift : 0
     * globerBuyer : 0
     * auction : 0
     * moreDisCount : 0
     * bargin : 0
     * assemble : 0
     */

    private int buyGift;//吾G
    private int globerBuyer;//全球买手
    private int auction;// 0元拍
    private int moreDisCount;//多买多折
    private int bargin;//喜立得
    private int assemble;//拼团
    private int oto;//OTO
    private int nwPerson;//新人专区
    private int refund;//退款开关（1：开启，0：关闭）;
    private boolean isPingxx;//支付是否使用Ping++(1：使用，0：未使用)

    public boolean isPingxx() {
        return isPingxx;
    }

    public void setPingxx(boolean pingxx) {
        isPingxx = pingxx;
    }

    public int getRefund() {
        return refund;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    public int getNwPerson() {
        return nwPerson;
    }

    public void setNwPerson(int nwPerson) {
        this.nwPerson = nwPerson;
    }

    public int getBuyGift() {
        return buyGift;
    }

    public void setBuyGift(int buyGift) {
        this.buyGift = buyGift;
    }

    public int getGloberBuyer() {
        return globerBuyer;
    }

    public void setGloberBuyer(int globerBuyer) {
        this.globerBuyer = globerBuyer;
    }

    public int getAuction() {
        return auction;
    }

    public void setAuction(int auction) {
        this.auction = auction;
    }

    public int getMoreDisCount() {
        return moreDisCount;
    }

    public void setMoreDisCount(int moreDisCount) {
        this.moreDisCount = moreDisCount;
    }

    public int getBargin() {
        return bargin;
    }

    public void setBargin(int bargin) {
        this.bargin = bargin;
    }

    public int getAssemble() {
        return assemble;
    }

    public void setAssemble(int assemble) {
        this.assemble = assemble;
    }

    public int getOto() {
        return oto;
    }

    public void setOto(int oto) {
        this.oto = oto;
    }
}
