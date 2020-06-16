package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName CustomBean
 * Description 定制馆数据Bean
 * Author 卿凯
 * Date 2019/6/20/020
 * Version V1.0
 */
public class CustomBean {
    public String headUrl = "";//头像
    public String name = "";//设计师名字
    public String time = "";//动态发布时间
    public String worksName = "";//作品名
    public String content = "";//内容
    public int msgCount = 0;//评论数
    public int loveCount = 0;//点赞数
    public int loveType = 0;//是否点赞
    public int type = 0;//数据类型  多图还是单图
    public String id = "";//设计师ID
    public int isAttention = 0;//是否关注
    public List<String> imgs;//返回的所有的图片地址

    public CustomBean(String headUrl, String name, String time, String worksName, String content, int msgCount,
                      int loveCount, int loveType, int type, String id, int isAttention, List<String> imgs) {
        this.headUrl = headUrl;
        this.name = name;
        this.time = time;
        this.worksName = worksName;
        this.content = content;
        this.msgCount = msgCount;
        this.loveCount = loveCount;
        this.loveType = loveType;
        this.type = type;
        this.id = id;
        this.isAttention = isAttention;
        this.imgs = imgs;
    }
}
