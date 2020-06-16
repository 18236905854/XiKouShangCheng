package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.TransferInfoBean;
import com.xk.mall.model.impl.TransferViewImpl;

/**
 * @ClassName: TransferPresenter
 * @Description: 转账页面请求Presenter
 * @Author: 卿凯
 * @Date: 2019/10/20/020 15:33
 * @Version: 1.0
 */
public class TransferPresenter extends BasePresenter<TransferViewImpl> {
    public TransferPresenter(TransferViewImpl baseView) {
        super(baseView);
    }

    /**
     * 通过手机号获取转账信息
     */
    public void getTransferInfoByPhone(String phone){
        addDisposable(apiServer.getUserByMobile(phone), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetDataByPhone((BaseModel<TransferInfoBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
