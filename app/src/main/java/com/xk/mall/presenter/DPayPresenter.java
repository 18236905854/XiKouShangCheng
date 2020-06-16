package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.DPayResultPageBean;
import com.xk.mall.model.impl.DPayViewImpl;

/**
 * @ClassName: DPayPresenter
 * @Description: 我的代付页面请求Presenter
 * @Author: 卿凯
 * @Date: 2019/8/30/030 19:02
 * @Version: 1.0
 */
public class DPayPresenter extends BasePresenter<DPayViewImpl> {

    public DPayPresenter(DPayViewImpl baseView) {
        super(baseView);
    }

    public void getData(String buyerAccount, int state, int page, int limit){
        addDisposable(apiServer.getInsteadPayment(buyerAccount, state, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetDataSuccess((BaseModel<DPayResultPageBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
