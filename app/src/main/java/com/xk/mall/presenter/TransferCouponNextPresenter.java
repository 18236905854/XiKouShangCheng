package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.TransferResultBean;
import com.xk.mall.model.impl.TransferCouponNextViewImpl;
import com.xk.mall.model.impl.TransferNextViewImpl;
import com.xk.mall.model.request.TransferCouponRequestBody;
import com.xk.mall.model.request.TransferRequestBody;

/**
 * @ClassName: TransferCouponNextPresenter
 * @Description: 优惠券转赠
 * @Author: 卿凯
 * @Date: 2019/10/20/020 18:32
 * @Version: 1.0
 */
public class TransferCouponNextPresenter extends BasePresenter<TransferCouponNextViewImpl> {
    public TransferCouponNextPresenter(TransferCouponNextViewImpl baseView) {
        super(baseView);
    }

    /**
     * 转账
     */
    public void transferCoupon(TransferCouponRequestBody requestBody){
        addDisposable(apiServer.userTransferCoupon(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onTransferSuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
