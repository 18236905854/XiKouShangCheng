package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CutPageBean;
import com.xk.mall.model.entity.CutShangJiaBean;
import com.xk.mall.model.entity.RealNameInfoBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.impl.AddBankCardViewImpl;
import com.xk.mall.model.impl.CutViewImpl;
import com.xk.mall.model.request.AddBankRequestBody;
import com.xk.mall.model.request.ShareRequestBody;

/**
 * Description 添加银行卡 Presenter
 */
public class AddBankCardPresenter extends BasePresenter<AddBankCardViewImpl> {
    public AddBankCardPresenter(AddBankCardViewImpl baseView) {
        super(baseView);
    }

    /**
     * 查询实名认证信息
     * @param userId
     *
     */
    public void getRealNameInfo(String userId){
        addDisposable(apiServer.getRealNameInfoByUserId(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetRealNameInfoSuc((BaseModel<RealNameInfoBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    //获取验证码接口
    public void getValidCode(String phoneNum){
        addDisposable(apiServer.addBankGetCode(phoneNum), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetCodeSuc((BaseModel) o);
            }
            @Override
            public void onError(String msg) {
            }
        });
    }


    /**
     * 保存银行卡账户
     */
    public void saveBankCard(AddBankRequestBody requestBody){
        addDisposable(apiServer.saveBankAccout(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onAddBankCardSuc((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
