package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.SettlementMxChildBean;
import com.xk.mall.model.impl.SettlementDetailViewImpl;

/**
 * ClassName SettlementDetailPresenter
 * Description 待结算明细详情请求Presenter
 * Author 卿凯
 * Date 2019/8/2/002
 * Version V1.0
 */
public class SettlementDetailPresenter extends BasePresenter<SettlementDetailViewImpl> {
    public SettlementDetailPresenter(SettlementDetailViewImpl baseView) {
        super(baseView);
    }

    public void getSettlementDetail(String id, String refKey){
        addDisposable(apiServer.getRedPagDetail(id, refKey), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetDetailSuccess((BaseModel<SettlementMxChildBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
