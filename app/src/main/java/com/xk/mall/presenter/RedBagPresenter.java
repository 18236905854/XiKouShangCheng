package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.RedBagBean;
import com.xk.mall.model.entity.WithdrawAccountBean;
import com.xk.mall.model.impl.RedBagImpl;

import java.util.List;

/**
 * ClassName RedBagPresenter
 * Description 我的红包页面Presenter
 * Author 卿凯
 * Date 2019/6/17/017
 * Version V1.0
 */
public class RedBagPresenter extends BasePresenter<RedBagImpl> {
    public RedBagPresenter(RedBagImpl baseView) {
        super(baseView);
    }

    /**
     * 根据用户ID获取用户的红包信息
     */
    public void getRedBag(String userId){
        addDisposable(apiServer.getRedBagById(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetSuccess((BaseModel<RedBagBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 是否设置支付密码
     *
     */
    public void getSetPayPwd(String userId){
        addDisposable(apiServer.getSetPayPwd(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetSetPwd((BaseModel<Boolean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
