package com.xk.mall.model.request;

/**
 * ClassName InvitationRequestBody
 * Description 使用邀请码注册接口的请求体
 * Author 卿凯
 * Date 2019/6/17/017
 * Version V1.0
 */
public class InvitationRequestBody extends LoginRequestBody {
    public String invitationCode = "";
    public String thirdId = "";
    public int type = 0;

    public InvitationRequestBody(String mobile, String validCode, String invitationCode, String thirdId, int type) {
        super(mobile, validCode);
        this.invitationCode = invitationCode;
        this.thirdId = thirdId;
        this.type = type;
    }
}
