package com.xk.mall.model.entity;

import java.util.List;
/**
 * 关注店铺 实体bean
 */
public class AttenShopListBean {
    /**
     * pageCount : 0
     * result : [{"followTime":"","id":"","shopImageUrl":"","shopName":""}]
     * totalCount : 0
     */
    private int pageCount;
    private int totalCount;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * followTime :
         * id :
         * shopImageUrl :
         * shopName :
         */

        private String followTime;
        private String id;
        private String shopImageUrl;
        private String shopName;

        public String getFollowTime() {
            return followTime;
        }

        public void setFollowTime(String followTime) {
            this.followTime = followTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopImageUrl() {
            return shopImageUrl;
        }

        public void setShopImageUrl(String shopImageUrl) {
            this.shopImageUrl = shopImageUrl;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }
    }
}
