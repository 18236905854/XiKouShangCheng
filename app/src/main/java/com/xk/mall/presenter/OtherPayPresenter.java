package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OtherPayResultBean;
import com.xk.mall.model.impl.OtherPayViewImpl;
import com.xk.mall.model.request.OtherPayRequestBody;
import com.xk.mall.utils.XiKouUtils;

/**
 * @ClassName: OtherPayPresenter
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/8/30/030 16:38
 * @Version: 1.0
 */
public class OtherPayPresenter extends BasePresenter<OtherPayViewImpl> {

    public OtherPayPresenter(OtherPayViewImpl baseView) {
        super(baseView);
    }

    //创建代付订单
    public void goOtherPay(String orderNo, int orderType, String userId, String phone){
        OtherPayRequestBody requestBody = new OtherPayRequestBody();
        requestBody.setOrderNo(orderNo);
        requestBody.setOrderType(orderType);
        requestBody.setUserId(userId);
        if(!XiKouUtils.isNullOrEmpty(phone)){
            requestBody.setPayPhone(phone);
        }
        addDisposable(apiServer.goOtherPay(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onOtherPaySuccess((BaseModel<OtherPayResultBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
