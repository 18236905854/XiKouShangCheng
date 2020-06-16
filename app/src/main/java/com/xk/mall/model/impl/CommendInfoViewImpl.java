package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.CommendInfoBean;

/**
 * ClassName CommendInfoViewImpl
 * Description 推荐人信息view接口
 * Author 卿凯
 * Date 2019/7/26/026
 * Version V1.0
 */
public interface CommendInfoViewImpl extends BaseViewListener {
    //获取推荐人信息成功的回调
    void onGetCommendInfoSuccess(BaseModel<CommendInfoBean> model);
}
