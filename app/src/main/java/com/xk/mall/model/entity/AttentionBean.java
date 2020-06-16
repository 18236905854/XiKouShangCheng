package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * ClassName AttentionBean
 * Description 我的关注对象
 * Author 卿凯
 * Date 2019/6/11/011
 * Version V1.0
 */
public class AttentionBean implements Serializable {
    public String headUrl = "";//头像
    public String nickName = "";//名字
    public String createTime = "";//关注时间
    public int sex = 0;//性别
    public String mobile = "";//手机号
    public String followedUserId = "";//关注的人的用户ID
}
