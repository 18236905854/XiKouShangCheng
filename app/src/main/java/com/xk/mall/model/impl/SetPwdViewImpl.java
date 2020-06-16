package com.xk.mall.model.impl;

import com.umeng.socialize.media.Base;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.LoginBean;

/**
 * 设置支付密码view
 */
public interface SetPwdViewImpl extends BaseViewListener {
    //获取验证码接口
    void onGetValidCodeSuccess(BaseModel loginBean);
    //校验验证码
    void onCodeNextSucess(BaseModel<Boolean> o);

    //设置支付密码
    void onSavePayPwdSuc(BaseModel o);
}
