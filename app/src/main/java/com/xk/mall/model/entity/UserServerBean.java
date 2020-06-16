package com.xk.mall.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * ClassName UserServerBean
 * Description 用户Bean
 * Author 卿凯
 * Date 2019/6/15/015
 * Version V1.0
 */
public class UserServerBean {
    public String id = "";//用户ID
    public int balance = 0;//红包余额
    public String barthday = "";//生日
    public int certification = 0;//实名认证标识:0-未实名认证；1-已实名认知
    public int couponNum = 0;//优惠券张数
    public String createTime = "";//注册时间
    public int deleted  = 0;//是否删除:0-否（未删除）；1-是（删除）
    public String email = "";//邮件
    public int followCnt = 0;//关注数
    public String headUrl = "";//头像
    public String mobile = "";//手机号
    public String nickName = "";//昵称
    public int address = 0;//是否填写地址信息 0:未填写，1：已填写
    public int isBindWX = 0;//是否绑定微信: 0:未绑定，1：已绑定
    public int sex = 0;//0：保密  1:男  2:女
    public int status = 0;//状态 1-正常  2-冻结
    public String userName = "";//用户名
    public int userType = 1;//用户类型：1-普通用户；2-VIP用户
    public int isPayPassword;//1 已设置  0未设置
    public String extCode = "";//用户邀请码

}
