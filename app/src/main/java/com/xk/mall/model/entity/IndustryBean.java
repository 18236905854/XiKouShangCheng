package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName IndustryBean
 * Description 行业分类Bean
 * Author 卿凯
 * Date 2019/7/19/019
 * Version V1.0
 */
public class IndustryBean {


    /**
     * industry1 : 1
     * industryName : 美食餐饮
     */

    private int industry1;
    private String industryName;
    private List<String> bannerImageList;

    public List<String> getBannerImageList() {
        return bannerImageList;
    }

    public void setBannerImageList(List<String> bannerImageList) {
        this.bannerImageList = bannerImageList;
    }

    public int getIndustry1() {
        return industry1;
    }

    public void setIndustry1(int industry1) {
        this.industry1 = industry1;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
}
