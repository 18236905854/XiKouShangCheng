package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.model.impl.LoginViewImpl;
import com.xk.mall.model.impl.SetPwdViewImpl;
import com.xk.mall.model.request.InvitationRequestBody;
import com.xk.mall.model.request.LoginRequestBody;
import com.xk.mall.model.request.SetPwdRequestBody;
import com.xk.mall.model.request.ValidCodeRequestBody;
import com.xk.mall.model.request.WXLoginRequestBody;

/**
 * 设置支付密码 presenter
 */
public class SetPwdPresenter extends BasePresenter<SetPwdViewImpl> {
    public SetPwdPresenter(SetPwdViewImpl baseView) {
        super(baseView);
    }

    //获取验证码接口
    public void getValidCode(String phoneNum){
        addDisposable(apiServer.getSetPwdCode(phoneNum), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetValidCodeSuccess((BaseModel) o);
            }
            @Override
            public void onError(String msg) {
            }
        });
    }

    //检验验证码接口
    public void getValidCodeNext(String code,String phoneNum){
        addDisposable(apiServer.verificationCodePayPwd(code,phoneNum), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onCodeNextSucess((BaseModel<Boolean>) o);
            }
            @Override
            public void onError(String msg) {
            }
        });
    }

    //设置支付密码
    public void setPayPwd(SetPwdRequestBody requestBody){
        addDisposable(apiServer.savePayPwd(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onSavePayPwdSuc((BaseModel) o);
            }
            @Override
            public void onError(String msg) {
            }
        });
    }

}
