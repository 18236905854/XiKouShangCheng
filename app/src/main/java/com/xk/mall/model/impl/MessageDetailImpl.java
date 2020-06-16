package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.MessageBean;

/**
 * ClassName MessageDetailImpl
 * Description 消息详情页面的view实现类
 * Author 卿凯
 * Date 2019/7/4/004
 * Version V1.0
 */
public interface MessageDetailImpl extends BaseViewListener {
    //获取消息详情成功
    void onMessageDetailSuccess(BaseModel<MessageBean.MessageChildBean> childBeanBaseModel);
}
