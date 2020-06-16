package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName HomeSearchGoodsPageBean
 * Description 首页搜索商品分页Bean
 * Author 卿凯
 * Date 2019/8/2/002
 * Version V1.0
 */
public class HomeSearchGoodsPageBean {
    private int pageCount;
    private int totalCount;
    private List<HomeSearchGoodsBean> result;

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

    public List<HomeSearchGoodsBean> getResult() {
        return result;
    }

    public void setResult(List<HomeSearchGoodsBean> result) {
        this.result = result;
    }
}
