package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.BankBean;
import com.xk.mall.model.entity.ResultPageBean;
import com.xk.mall.model.impl.ChooseBankViewImpl;


/**
 * @ClassName: ChooseBankPresenter
 * @Description: 选择银行卡请求类
 * @Author: 卿凯
 * @Date: 2019/10/8/008 14:42
 * @Version: 1.0
 */
public class ChooseBankPresenter extends BasePresenter<ChooseBankViewImpl> {

    public ChooseBankPresenter(ChooseBankViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取银行卡列表
     */
    public void getBankList(String name, int page, int limit){
        addDisposable(apiServer.getBankList(name, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetBankListSuccess((BaseModel<ResultPageBean<BankBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
