package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.WithdrawAccountBean;

import java.util.List;

/**
 * @ClassName: MyWithdrawViewImpl
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/9/24/024 9:08
 * @Version: 1.0
 */
public interface MyWithdrawViewImpl extends BaseViewListener {
    //获取银行卡账号成功
    void onGetAccountSuccess(BaseModel<List<WithdrawAccountBean>> model);

    //用户申请提现成功
    void onWithdrawSuccess(BaseModel<String> model);

    //校验支付密码成功
    void onGetVerityPayPwdSuc(BaseModel<String> model);
}
