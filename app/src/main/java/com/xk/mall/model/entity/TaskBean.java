package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName TaskBean
 * Description 任务中心Bean
 * Author 卿凯
 * Date 2019/7/20/020
 * Version V1.0
 */
public class TaskBean {

    /**
     * list : [{"award":0,"completeStatus":0,"curTimes":0,"id":"","maxTimes":0,"taskExplain":"","type":0,"url":""}]
     * maxLimitValue : 0
     * taskValue : 0
     * todayTotalValue : 0
     * userId :
     */

    private int maxLimitValue;//每天最大限制贡献值
    private int taskValue;//贡献值
    private int todayTotalValue;//今天获得总贡献值
    private String userId;//用户ID
    private List<ListBean> list;//任务列表

    public int getMaxLimitValue() {
        return maxLimitValue;
    }

    public void setMaxLimitValue(int maxLimitValue) {
        this.maxLimitValue = maxLimitValue;
    }

    public int getTaskValue() {
        return taskValue;
    }

    public void setTaskValue(int taskValue) {
        this.taskValue = taskValue;
    }

    public int getTodayTotalValue() {
        return todayTotalValue;
    }

    public void setTodayTotalValue(int todayTotalValue) {
        this.todayTotalValue = todayTotalValue;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * award : 0
         * completeStatus : 0
         * curTimes : 0
         * id :
         * maxTimes : 0
         * taskExplain :
         * type : 0
         * url :
         */

        private int award;//奖励值
        private int completeStatus;//状态：1 已完成，0 未完成
        private int curTimes;//当前完成次数
        private String id;//任务id
        private int maxTimes;//每天完成最大次数,0表示无限循环，-1表示唯一性任务，不可重复
        private String taskExplain;//说明
        private int type;//类型：1: 分享、2 获客、3 消费、4 促活、5 用户数据
        private String url;//点击跳转的url，可以schema

        public int getAward() {
            return award;
        }

        public void setAward(int award) {
            this.award = award;
        }

        public int getCompleteStatus() {
            return completeStatus;
        }

        public void setCompleteStatus(int completeStatus) {
            this.completeStatus = completeStatus;
        }

        public int getCurTimes() {
            return curTimes;
        }

        public void setCurTimes(int curTimes) {
            this.curTimes = curTimes;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getMaxTimes() {
            return maxTimes;
        }

        public void setMaxTimes(int maxTimes) {
            this.maxTimes = maxTimes;
        }

        public String getTaskExplain() {
            return taskExplain;
        }

        public void setTaskExplain(String taskExplain) {
            this.taskExplain = taskExplain;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
