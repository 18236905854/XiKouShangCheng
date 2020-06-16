package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.WithDrawDetailBean;
import com.xk.mall.model.impl.WithDrawDetailViewImpl;

/**
 * @ClassName: WithDrawDetailPresenter
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/9/24/024 20:59
 * @Version: 1.0
 */
public class WithDrawDetailPresenter extends BasePresenter<WithDrawDetailViewImpl> {
    public WithDrawDetailPresenter(WithDrawDetailViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取提现明细详情
     */
    public void getDetail(String cashId){
        addDisposable(apiServer.getWithDrawDetail(cashId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetDetailSuccess((BaseModel<WithDrawDetailBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
