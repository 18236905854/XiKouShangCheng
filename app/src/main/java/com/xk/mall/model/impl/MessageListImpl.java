package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.MessageBean;

/**
 * ClassName MessageListImpl
 * Description 消息列表
 * Author Kay
 * Date 2019/7/4/004
 * Version V1.0
 */
public interface MessageListImpl extends BaseViewListener {
    void onGetListSuccess(BaseModel<MessageBean> messageBeanBaseModel);
}
