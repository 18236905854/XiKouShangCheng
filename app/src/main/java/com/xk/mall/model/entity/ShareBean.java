package com.xk.mall.model.entity;

/**
 * ClassName ShareBean
 * Description 获取分享结果Bean
 * Author 卿凯
 * Date 2019/7/23/023
 * Version V1.0
 */
public class ShareBean {

    /**
     * title : 店铺主页
     * content : 店铺主页
     * imageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190717/bd869b1a48394d269c2295f4e72335a3.jpg
     * type : url
     * params : {"url":"http://www.lianquyule.com/index.html?shopId=5d2ef1a27b6c2e00016d2d55&shareUserId=5d1d58eeaae31800016caedd&id=7"}
     */

    private String title;
    private String content;
    private String imageUrl;
    private String type;
    private String appletId;//小程序ID
    private String appletUrl;//小程序路径
    private int isCreatedPoster;//是否生成海报
    private int isSmallRoutine;//是否支持小程序分享
    private ParamsBean params;

    public String getAppletId() {
        return appletId;
    }

    public void setAppletId(String appletId) {
        this.appletId = appletId;
    }

    public String getAppletUrl() {
        return appletUrl;
    }

    public void setAppletUrl(String appletUrl) {
        this.appletUrl = appletUrl;
    }

    public int getIsCreatedPoster() {
        return isCreatedPoster;
    }

    public void setIsCreatedPoster(int isCreatedPoster) {
        this.isCreatedPoster = isCreatedPoster;
    }

    public int getIsSmallRoutine() {
        return isSmallRoutine;
    }

    public void setIsSmallRoutine(int isSmallRoutine) {
        this.isSmallRoutine = isSmallRoutine;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * url : http://www.lianquyule.com/index.html?shopId=5d2ef1a27b6c2e00016d2d55&shareUserId=5d1d58eeaae31800016caedd&id=7
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
