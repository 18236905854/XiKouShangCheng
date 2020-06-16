package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.WithDrawRequestBean;
import com.xk.mall.model.entity.WithdrawAccountBean;
import com.xk.mall.model.impl.MyWithdrawViewImpl;

import java.util.List;

/**
 * @ClassName: MyWithdrawPresenter
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/9/24/024 9:07
 * @Version: 1.0
 */
public class MyWithdrawPresenter extends BasePresenter<MyWithdrawViewImpl> {
    public MyWithdrawPresenter(MyWithdrawViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取用户银行卡列表
     */
    public void getAccountList(String userId){
        addDisposable(apiServer.getAccountList(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetAccountSuccess((BaseModel<List<WithdrawAccountBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 校验支付密码
     */
    public void verifyPayPwd(String userId,String passWord){
        addDisposable(apiServer.verifyPayPwd(userId,passWord), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetVerityPayPwdSuc((BaseModel<String>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 用户申请提现
     */
    public void goWithdraw(WithDrawRequestBean requestBean){
        addDisposable(apiServer.goWithDraw(requestBean), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onWithdrawSuccess((BaseModel<String>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
