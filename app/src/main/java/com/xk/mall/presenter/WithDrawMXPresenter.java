package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.RedBagMxBean;
import com.xk.mall.model.entity.WithDrawCountBean;
import com.xk.mall.model.entity.WithDrawMxBean;
import com.xk.mall.model.impl.RedBagMXViewImpl;
import com.xk.mall.model.impl.WithDrawMXViewImpl;

/**
 * Description 提现明细 Presenter
 */
public class WithDrawMXPresenter extends BasePresenter<WithDrawMXViewImpl> {
    public WithDrawMXPresenter(WithDrawMXViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取提现明细数据
     * @param userId
     *
     */
    public void getWithDrawMXList(String userId,String searchTime,int page,int limit){
        addDisposable(apiServer.getWithDrawDetailList(userId,searchTime,5, page,limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetListDataSuc((BaseModel<WithDrawMxBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }


    /**
     * 获取提现月统计数据
     */
    public void getMonthCount(String userId, String searchTime){
        addDisposable(apiServer.getWithDrawMonthCount(userId, searchTime), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetMonthCountSuccess((BaseModel<WithDrawCountBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
