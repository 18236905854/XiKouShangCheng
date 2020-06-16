package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName ActiveSectionBean
 * Description 活动版块商品Bean
 * Author 卿凯
 * Date 2019/7/29/029
 * Version V1.0
 */
public class ActiveSectionGoodsPageBean {
    /**
     * pageCount : 0
     * result : [{"activityGoodsId":"","activityId":"","bargainNumber":0,"bargainedNum":0,"commodityId":"","commodityName":"","commodityPrice":0,"couponValue":0,"discount":0,"goodsId":"","goodsImageUrl":"","salePrice":0}]
     * totalCount : 0
     */

    private int pageCount;
    private int totalCount;
    private List<ActiveSectionGoodsBean> result;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ActiveSectionGoodsBean> getResult() {
        return result;
    }

    public void setResult(List<ActiveSectionGoodsBean> result) {
        this.result = result;
    }

}
