package com.xk.mall.model.eventbean;

/**
 * ClassName ShareSuccessEvent
 * Description 分享成功消息Bean
 * Author 卿凯
 * Date 2019/7/30/030
 * Version V1.0
 */
public class ShareSuccessEvent {
    //分享类型 ShareType类中
    public int shareType = 0;

    public ShareSuccessEvent(int shareType) {
        this.shareType = shareType;
    }
}
