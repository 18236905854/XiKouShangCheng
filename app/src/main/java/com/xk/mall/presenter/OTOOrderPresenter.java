package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OtoOrderPageBean;
import com.xk.mall.model.impl.OTOOrderViewImpl;

/**
 * ClassName OTOOrderPresenter
 * Description OTO联盟订单请求Presenter
 * Author 卿凯
 * Date 2019/7/19/019
 * Version V1.0
 */
public class OTOOrderPresenter extends BasePresenter<OTOOrderViewImpl> {
    public OTOOrderPresenter(OTOOrderViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取OTO联盟订单列表
     * @param buyerAccount 用户账号
     * @param page 页数
     * @param limit 每页的条数
     */
    public void getOTOOrderList(String buyerAccount, int page, int limit){
        addDisposable(apiServer.getOrderList(buyerAccount, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetOtoOrderListSuccess((BaseModel<OtoOrderPageBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
