package com.xk.mall.model.entity;

/**
 * 喜扣赚 bean
 */
public class XiKouZuanBean {
    private int resourceId;
    private String taskTitle;
    private String taskValue;


    public XiKouZuanBean(int resourceId, String taskTitle, String taskValue) {
        this.resourceId = resourceId;
        this.taskTitle = taskTitle;
        this.taskValue = taskValue;
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

}
