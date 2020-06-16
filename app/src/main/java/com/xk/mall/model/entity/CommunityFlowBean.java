package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName CommunityFlowBean
 * Description 社交流量Bean
 * Author 卿凯
 * Date 2019/8/2/002
 * Version V1.0
 */
public class CommunityFlowBean {

    /**
     * userId : null
     * inviteUsers : 0
     * shareGoods : 0
     * activities : 0
     * followDisigners : 0
     * likes : 0
     * comments : 0
     * tasks : 0
     * collection : 0
     * growUps : [{"name":"加入喜扣大家庭","date":"2019-07-04 09:39:58"}]
     */

    private String userId;
    private int inviteUsers;
    private int shareGoods;
    private int activities;
    private int followDisigners;
    private int likes;
    private int comments;
    private int tasks;
    private int collection;
    private List<GrowUpsBean> growUps;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getInviteUsers() {
        return inviteUsers;
    }

    public void setInviteUsers(int inviteUsers) {
        this.inviteUsers = inviteUsers;
    }

    public int getShareGoods() {
        return shareGoods;
    }

    public void setShareGoods(int shareGoods) {
        this.shareGoods = shareGoods;
    }

    public int getActivities() {
        return activities;
    }

    public void setActivities(int activities) {
        this.activities = activities;
    }

    public int getFollowDisigners() {
        return followDisigners;
    }

    public void setFollowDisigners(int followDisigners) {
        this.followDisigners = followDisigners;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getTasks() {
        return tasks;
    }

    public void setTasks(int tasks) {
        this.tasks = tasks;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public List<GrowUpsBean> getGrowUps() {
        return growUps;
    }

    public void setGrowUps(List<GrowUpsBean> growUps) {
        this.growUps = growUps;
    }

    public static class GrowUpsBean {
        /**
         * name : 加入喜扣大家庭
         * date : 2019-07-04 09:39:58
         */

        private String name;
        private String date;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
