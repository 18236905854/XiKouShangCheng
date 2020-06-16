package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.SettlementMxBean;
import com.xk.mall.model.entity.WithDrawMxBean;
import com.xk.mall.model.impl.SettlementMXViewImpl;
import com.xk.mall.model.impl.WithDrawMXViewImpl;

/**
 * Description 待结算明细 Presenter
 */
public class SettlementMXPresenter extends BasePresenter<SettlementMXViewImpl> {
    public SettlementMXPresenter(SettlementMXViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取待结算明细数据
     */
    public void getSettlementMXList(String userId, int page, int limit) {
        addDisposable(apiServer.getSettlementDetailList(userId, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetListDataSuc((BaseModel<SettlementMxBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }


}
