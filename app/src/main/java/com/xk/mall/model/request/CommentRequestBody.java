package com.xk.mall.model.request;

/**
 * 添加评论 请求体
 */
public class CommentRequestBody {
    private String commentContent;//评内容
    private String designerId;//设计师id
    private String workId;//作品id
    private String userId;//用户id
    private int commentCount;//评论数量

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
