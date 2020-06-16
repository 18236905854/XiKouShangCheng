package com.xk.mall.model.entity;

/**
 * ClassName ActivityRoundBean
 * Description 所有活动轮次的Bean
 * Author 卿凯
 * Date 2019/7/5/005
 * Version V1.0
 */
public class ActivityRoundBean {
    public String id = "";//场次ID
    public String activityType = "";//活动ID
    public String roundTitle = "";//场次标题
    public String startTime = "";//开始时间
    public String endTime = "";//结束时间
    public int state = 0;//状态（1.未开始 2.失效,3:结束,4:进行中）
}
