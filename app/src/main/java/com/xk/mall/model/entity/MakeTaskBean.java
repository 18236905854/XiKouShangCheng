package com.xk.mall.model.entity;

public class MakeTaskBean {
    private int resourceId;
    private String taskTitle;
    private String taskValue;
    private int status;// 0 未完成  已完成

    public MakeTaskBean(int resourceId, String taskTitle, String taskValue, int status) {
        this.resourceId = resourceId;
        this.taskTitle = taskTitle;
        this.taskValue = taskValue;
        this.status = status;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskValue() {
        return taskValue;
    }

    public void setTaskValue(String taskValue) {
        this.taskValue = taskValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
