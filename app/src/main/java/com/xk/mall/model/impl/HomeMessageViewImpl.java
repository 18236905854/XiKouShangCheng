package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.HomeMessageBean;

/**
 * ClassName HomeMessageViewImpl
 * Description 我的消息页面view接口
 * Author 卿凯
 * Date 2019/7/22/022
 * Version V1.0
 */
public interface HomeMessageViewImpl extends BaseViewListener {
    //获取未读消息数
    void onGetUnreadMessSuc(BaseModel<HomeMessageBean> model);
}
