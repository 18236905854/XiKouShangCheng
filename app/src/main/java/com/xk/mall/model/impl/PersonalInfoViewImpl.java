package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.model.entity.UploadLogoBean;
import com.xk.mall.model.entity.UserServerBean;

/**
 * ClassName PersonalInfoViewImpl
 * Description 用户信息页面的view的实现类
 * Author 卿凯
 * Date 2019/6/19/019
 * Version V1.0
 */
public interface PersonalInfoViewImpl extends BaseViewListener {
    //获取用户信息成功
    void getUserInfoSuccess(BaseModel<UserServerBean> userServerBeanBaseModel);
    //上传用户头像成功
    void onUploadImgSuccess(BaseModel<UploadLogoBean> model);
    //保存信息成功
    void onSaveSuccess(BaseModel model);
    //解除第三方账号成功
    void onUnbindSuccess(BaseModel model);
    //微信登录接口
    void onWXLoginSuccess(BaseModel<LoginBean> baseModel);
}
