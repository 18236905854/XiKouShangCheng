package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.WuGOrderMoneyBean;
import com.xk.mall.model.impl.WuGOrderListViewImpl;

/**
 * @ClassName: WuGOrderListPresenter
 * @Description: 吾G订单额度请求Presenter
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class WuGOrderListPresenter extends BasePresenter<WuGOrderListViewImpl> {
    public WuGOrderListPresenter(WuGOrderListViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取吾G订单额度
     */
    public void getTotalMoney(String userId){
        addDisposable(apiServer.getWuGOrderMoney(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetMoneySuccess((BaseModel<WuGOrderMoneyBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
