package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName DesignerBean
 * Description 设计师主页的Bean
 * Author 卿凯
 * Date 2019/6/20/020
 * Version V1.0
 */
public class DesignerBean {
    /**
     * result : [{"id":"5d185744b4e57100012a59b1","clientUserId":"5d18574438f37b0001ce9cdd","name":"张三","headUrl":null,"startTime":"2019-07-02 15:55:45","createTimeDifference":"17小时前"}]
     * totalCount : 1
     * pageCount : 1
     */

    private int totalCount;
    private int pageCount;
    private List<ResultBean> result;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 5d185744b4e57100012a59b1
         * clientUserId : 5d18574438f37b0001ce9cdd
         * name : 张三
         * headUrl : null
         * startTime : 2019-07-02 15:55:45
         * createTimeDifference : 17小时前
         */

        private String id;
        private String clientUserId;
        private String name;
        private String headUrl;
        private String createTime;
        private String createTimeDifference;
        private String pageName;

        public String getPageName() {
            return pageName;
        }

        public void setPageName(String pageName) {
            this.pageName = pageName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClientUserId() {
            return clientUserId;
        }

        public void setClientUserId(String clientUserId) {
            this.clientUserId = clientUserId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTimeDifference() {
            return createTimeDifference;
        }

        public void setCreateTimeDifference(String createTimeDifference) {
            this.createTimeDifference = createTimeDifference;
        }
    }
}
