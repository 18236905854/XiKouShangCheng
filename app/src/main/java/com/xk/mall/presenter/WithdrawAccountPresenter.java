package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.WithdrawAccountBean;
import com.xk.mall.model.impl.WithdrawAccountViewImpl;

import java.util.List;

/**
 * Description 查询我的账户 Presenter
 */
public class WithdrawAccountPresenter extends BasePresenter<WithdrawAccountViewImpl> {
    public WithdrawAccountPresenter(WithdrawAccountViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取我的账户数据
     */
    public void getMyAccountList(String userId){
        addDisposable(apiServer.getAccountList(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetAccountListSuc((BaseModel<List<WithdrawAccountBean>>) o);
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
     * 删除账户
     */
    public void deleteWithdrawAccount(String id){
        addDisposable(apiServer.deleteWtihdrawAccount(id), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onDeleteAccountSuc((BaseModel<String>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
