package com.xk.mall.model.request;

/**
 * ClassName PersonalInfoRequestBody
 * Description 用户信息页面的请求体
 * Author 卿凯
 * Date 2019/6/19/019
 * Version V1.0
 */
public class PersonalInfoRequestBody {
    public String headUrl = "";//用户头像
    public String id = "";//用户ID
    public String nickName = "";//昵称
    public int sex = 0;//用户性别 0：保密  1:男  2：女
    public String mobile = "";

    public PersonalInfoRequestBody(String headUrl, String id, String nickName, int sex, String mobile) {
        this.headUrl = headUrl;
        this.id = id;
        this.nickName = nickName;
        this.sex = sex;
        this.mobile = mobile;
    }
}
