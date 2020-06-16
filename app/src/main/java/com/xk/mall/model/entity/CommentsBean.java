package com.xk.mall.model.entity;

import java.util.List;

public class CommentsBean {
    /**
     * result : [{"id":"2","userId":"67","designerId":"5d185744b4e57100012a59b1","workId":"5d187fa74405f600012d3767","userName":null,"headUrl":null,"workName":null,"name":null,"commentContent":"作品评论内容2","replayContent":"","commentTime":null,"replayTime":null,"imageList":[{"id":"5d187fa74405f600012d3768","workId":"5d187fa74405f600012d3767","type":"","imageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190628/de712f4fa4c647af9672de0221b0fc33.png","createTime":"2019-06-30 17:23:52","updateTime":"2019-06-30 17:23:52"}],"commentCount":2},{"id":"1","userId":"67","designerId":"5d185744b4e57100012a59b1","workId":"5d187fa74405f600012d3767","userName":null,"headUrl":null,"workName":null,"name":null,"commentContent":"作品评论内容","replayContent":"sdsd","commentTime":null,"replayTime":null,"imageList":[{"id":"5d187fa74405f600012d3768","workId":"5d187fa74405f600012d3767","type":"","imageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190628/de712f4fa4c647af9672de0221b0fc33.png","createTime":"2019-06-30 17:23:52","updateTime":"2019-06-30 17:23:52"}],"commentCount":2}]
     * totalCount : 2
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
         * id : 2
         * userId : 67
         * designerId : 5d185744b4e57100012a59b1
         * workId : 5d187fa74405f600012d3767
         * userName : null
         * headUrl : null
         * workName : null
         * name : null
         * commentContent : 作品评论内容2
         * replayContent :
         * commentTime : null
         * replayTime : null
         * imageList : [{"id":"5d187fa74405f600012d3768","workId":"5d187fa74405f600012d3767","type":"","imageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190628/de712f4fa4c647af9672de0221b0fc33.png","createTime":"2019-06-30 17:23:52","updateTime":"2019-06-30 17:23:52"}]
         * commentCount : 2
         */

        private String id;
        private String userId;
        private String designerId;
        private String workId;
        private String userName;
        private String headUrl;
        private String name;//设计师名称
        private String commentContent;
        private String replayContent;
        private String commentTime;
        private String replayTime;
        private int commentCount;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDesignerId() {
            return designerId;
        }

        public void setDesignerId(String designerId) {
            this.designerId = designerId;
        }

        public String getWorkId() {
            return workId;
        }

        public void setWorkId(String workId) {
            this.workId = workId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public String getReplayContent() {
            return replayContent;
        }

        public void setReplayContent(String replayContent) {
            this.replayContent = replayContent;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }

        public String getReplayTime() {
            return replayTime;
        }

        public void setReplayTime(String replayTime) {
            this.replayTime = replayTime;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

    }
}
