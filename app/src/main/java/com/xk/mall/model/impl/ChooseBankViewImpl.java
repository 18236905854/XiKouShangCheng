package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.BankBean;
import com.xk.mall.model.entity.ResultPageBean;


/**
 * @ClassName: ChooseBankViewImpl
 * @Description: 选择银行卡页面view接口
 * @Author: 卿凯
 * @Date: 2019/10/8/008 14:43
 * @Version: 1.0
 */
public interface ChooseBankViewImpl extends BaseViewListener {
    //获取银行卡列表成功
    void onGetBankListSuccess(BaseModel<ResultPageBean<BankBean>> model);
}
