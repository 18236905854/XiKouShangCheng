package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.TransferResultBean;
import com.xk.mall.model.impl.TransferNextViewImpl;
import com.xk.mall.model.request.TransferRequestBody;

/**
 * @ClassName: TransferNextPresenter
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/10/20/020 18:32
 * @Version: 1.0
 */
public class TransferNextPresenter extends BasePresenter<TransferNextViewImpl> {
    public TransferNextPresenter(TransferNextViewImpl baseView) {
        super(baseView);
    }

    /**
     * 转账
     */
    public void transfer(TransferRequestBody requestBody){
        addDisposable(apiServer.userTransfer(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onTransferSuccess((BaseModel<TransferResultBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
