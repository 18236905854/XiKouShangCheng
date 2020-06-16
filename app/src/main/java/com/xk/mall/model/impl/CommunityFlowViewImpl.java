package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.CommunityFlowBean;

/**
 * ClassName CommunityFlowViewImpl
 * Description 社交流量页面view接口
 * Author 卿凯
 * Date 2019/8/2/002
 * Version V1.0
 */
public interface CommunityFlowViewImpl extends BaseViewListener {
    //获取社交流量成功回调
    void onGetCommunitySuccess(BaseModel<CommunityFlowBean> model);
}
