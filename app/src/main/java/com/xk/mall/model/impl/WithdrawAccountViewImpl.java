package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.WithdrawAccountBean;

import java.util.List;


/**
 *
 * Description 查询账户list view接口
 */
public interface WithdrawAccountViewImpl extends BaseViewListener {
    //添加银行卡成功
    void onGetAccountListSuc(BaseModel<List<WithdrawAccountBean>> model);

    //校验支付密码成功
    void onGetVerityPayPwdSuc(BaseModel<String> model);

    //删除提现账户
    void onDeleteAccountSuc(BaseModel<String> model);

}
