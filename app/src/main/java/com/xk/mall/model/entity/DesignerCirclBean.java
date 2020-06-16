package com.xk.mall.model.entity;

import java.util.List;

/**
 * 设计师圈
 */
public class DesignerCirclBean {
    /**
     * result : [{"id":"5d185744b4e57100012a59b1","name":"张三","headUrl":null,"commentCount":0,"fabulousCount":0},{"id":"5d185744b4e57100012a59b1","name":"张三","headUrl":null,"commentCount":0,"fabulousCount":0},{"id":"5d185744b4e57100012a59b1","name":"张三","headUrl":null,"commentCount":0,"fabulousCount":0},{"id":"5d185744b4e57100012a59b1","name":"张三","headUrl":null,"commentCount":0,"fabulousCount":0},{"id":"5d185744b4e57100012a59b1","name":"张三","headUrl":null,"commentCount":0,"fabulousCount":0},{"id":"5d185744b4e57100012a59b1","name":"张三","headUrl":null,"commentCount":0,"fabulousCount":0},{"id":"5d185744b4e57100012a59b1","name":"张三","headUrl":null,"commentCount":0,"fabulousCount":0},{"id":"5d1a16659ea0e30001168278","name":"测试","headUrl":"deflt.jpg","commentCount":0,"fabulousCount":0},{"id":"5d1a16879ea0e3000116827b","name":"测试","headUrl":"deflt.jpg","commentCount":0,"fabulousCount":0},{"id":"5d1a19829ea0e30001168284","name":"测试","headUrl":"deflt.jpg","commentCount":0,"fabulousCount":0}]
     * totalCount : 14
     * pageCount : 2
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
         * name : 张三
         * headUrl : null
         * commentCount : 0
         * fabulousCount : 0
         * pageName
         */

        private String id;
        private String name;
        private String pageName;
        private String headUrl;
        private int commentCount;
        private int fabulousCount;
        private int type = 0;//类型  0:大图  1:左图  2:右图

        public String getPageName() {
            return pageName;
        }

        public void setPageName(String pageName) {
            this.pageName = pageName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getFabulousCount() {
            return fabulousCount;
        }

        public void setFabulousCount(int fabulousCount) {
            this.fabulousCount = fabulousCount;
        }
    }
}
