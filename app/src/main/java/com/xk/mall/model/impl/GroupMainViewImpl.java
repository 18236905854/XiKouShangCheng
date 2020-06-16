package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ShareBean;

/**
 * ClassName GroupMainViewImpl
 * Description 定制拼团主页view接口
 * Author 卿凯
 * Date 2019/7/24/024
 * Version V1.0
 */
public interface GroupMainViewImpl extends BaseViewListener {
    //获取版块信息成功的回调
    void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model);

    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);
}
