package com.xk.mall.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 定制馆
 */
public class CustomGuanBean implements Serializable {

    /**
     * result : [{"id":"5d3ab4efc882c200019f1d43","designerId":"5d2d684886bd3d0001d6d1e2","name":null,"pageName":"黑头了","headUrl":null,"workName":"中心查VC","description":"123123","fabulousCnt":3,"commentCnt":1,"updateTime":"2019-07-26 16:08:15","showTime":"3天前","imageList":[{"id":"5d3ab4efc882c200019f1d44","workId":"5d3ab4efc882c200019f1d43","type":"1","imageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190726/fec21fb8ac03413786e757a1b8f42b70.png","createTime":"2019-07-26 16:08:15","updateTime":"2019-07-26 16:08:15"},{"id":"5d3ab4efc882c200019f1d45","workId":"5d3ab4efc882c200019f1d43","type":"2","imageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190726/fa6abee2bddd48378666973602e56260.png","createTime":"2019-07-26 16:08:15","updateTime":"2019-07-26 16:08:15"}],"isFollow":1,"isPraise":1}]
     * totalCount : 11
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

    public static class ResultBean implements Serializable {
        /**
         * id : 5d3ab4efc882c200019f1d43
         * designerId : 5d2d684886bd3d0001d6d1e2
         * name : null
         * pageName : 黑头了
         * headUrl : null
         * workName : 中心查VC
         * description : 123123
         * fabulousCnt : 3
         * commentCnt : 1
         * updateTime : 2019-07-26 16:08:15
         * showTime : 3天前
         * imageList : [{"id":"5d3ab4efc882c200019f1d44","workId":"5d3ab4efc882c200019f1d43","type":"1","imageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190726/fec21fb8ac03413786e757a1b8f42b70.png","createTime":"2019-07-26 16:08:15","updateTime":"2019-07-26 16:08:15"},{"id":"5d3ab4efc882c200019f1d45","workId":"5d3ab4efc882c200019f1d43","type":"2","imageUrl":"https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190726/fa6abee2bddd48378666973602e56260.png","createTime":"2019-07-26 16:08:15","updateTime":"2019-07-26 16:08:15"}]
         * isFollow : 1
         * isPraise : 1
         */

        private String id;
        private String designerId;
        private String name;
        private String pageName;
        private String headUrl;
        private String workName;
        private String description;
        private int fabulousCnt;//点赞数
        private int commentCnt;//评论数
        private String updateTime;
        private String showTime;
        private int isFollow;
        private int isPraise;
        private List<ImageListBean> imageList;
        private int viewType;//= 0 4张图  =1 张图

        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDesignerId() {
            return designerId;
        }

        public void setDesignerId(String designerId) {
            this.designerId = designerId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPageName() {
            return pageName;
        }

        public void setPageName(String pageName) {
            this.pageName = pageName;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getWorkName() {
            return workName;
        }

        public void setWorkName(String workName) {
            this.workName = workName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getFabulousCnt() {
            return fabulousCnt;
        }

        public void setFabulousCnt(int fabulousCnt) {
            this.fabulousCnt = fabulousCnt;
        }

        public int getCommentCnt() {
            return commentCnt;
        }

        public void setCommentCnt(int commentCnt) {
            this.commentCnt = commentCnt;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }

        public int getIsPraise() {
            return isPraise;
        }

        public void setIsPraise(int isPraise) {
            this.isPraise = isPraise;
        }

        public List<ImageListBean> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageListBean> imageList) {
            this.imageList = imageList;
        }

        public static class ImageListBean implements Serializable{
            /**
             * id : 5d3ab4efc882c200019f1d44
             * workId : 5d3ab4efc882c200019f1d43
             * type : 1
             * imageUrl : https://xikou-shop-test-file.oss-cn-shenzhen.aliyuncs.com/goods/20190726/fec21fb8ac03413786e757a1b8f42b70.png
             * createTime : 2019-07-26 16:08:15
             * updateTime : 2019-07-26 16:08:15
             */

            private String id;
            private String workId;
            private String type;
            private String imageUrl;
            private String createTime;
            private String updateTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getWorkId() {
                return workId;
            }

            public void setWorkId(String workId) {
                this.workId = workId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
