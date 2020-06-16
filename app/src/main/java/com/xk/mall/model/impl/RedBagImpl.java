package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.RedBagBean;
import com.xk.mall.model.entity.WithdrawAccountBean;

import java.util.List;

/**
 * ClassName RedBagImpl
 * Description 我的红包页面view实现类
 * Author 卿凯
 * Date 2019/6/17/017
 * Version V1.0
 */
public interface RedBagImpl extends BaseViewListener {
    void onGetSuccess(BaseModel<RedBagBean> o);
    void onGetSetPwd(BaseModel<Boolean> o);
}
