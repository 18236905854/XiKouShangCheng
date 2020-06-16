package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.RealNameInfoBean;
import com.xk.mall.model.entity.UploadLogoBean;


/**
 *
 * Description 实名认证view接口
 *
 *
 *
 */
public interface RealNameViewImpl extends BaseViewListener {
    //获取实名认证信息 成功回调
    void onGetRealNameInfoSuc(BaseModel<RealNameInfoBean> model);

    //保存实名认证
    void onGetSaveRealNameSuc(BaseModel<String> model);

    void onUploadImgSuccess(BaseModel<UploadLogoBean> model);
}
