package com.xk.mall.model.entity;

/**
 * 推广分享 bean
 */
public class PromotionShareBean {
    /**
     * jumpUrl : https://www.baidu.com
     * imageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190708/035c1e49b31f4ea69ce26cda3ee9ff28.png
     */

    private String jumpUrl;//二维码图链接
    private String imageUrl;//底图

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
