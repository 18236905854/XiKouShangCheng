package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName MessageBean
 * Description 消息列表的Bean
 * Author 卿凯
 * Date 2019/7/4/004
 * Version V1.0
 */
public class MessageBean {

    public int totalCount = 0;//单页的总数
    public int pageCount = 0;//当前页数
    public List<MessageChildBean> result;

    public class MessageChildBean {
        public String id = "";//消息ID
        public String img = "";//图片，公告类有图片，最多一张
        public String title = "";//标题
        public String content = "";//内容
        public String sendTime = "";//发送时间
        public int isRead = 0;//是否已读：1：已读，0：未读
        public String url = "";//url或者schema  当是http开头的直接用网页打开
    }
}
