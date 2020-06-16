package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.CutPageBean;
import com.xk.mall.model.entity.CutShangJiaBean;
import com.xk.mall.model.entity.RealNameInfoBean;
import com.xk.mall.model.entity.ShareBean;


/**
 *
 * Description 添加银行卡view接口
 */
public interface AddBankCardViewImpl extends BaseViewListener {
    //添加银行卡成功
    void onAddBankCardSuc(BaseModel model);
    //查询实名信息成功
    void onGetRealNameInfoSuc(BaseModel<RealNameInfoBean> model);
    //获取验证码成功
    void onGetCodeSuc(BaseModel model);
}
