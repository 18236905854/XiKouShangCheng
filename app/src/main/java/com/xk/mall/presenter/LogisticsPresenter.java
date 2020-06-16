package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.LogisticsResultBean;
import com.xk.mall.model.impl.LogisticsViewImpl;

/**
 * @ClassName: LogisticsPresenter
 * @Description: 物流请求Presenter
 * @Author: 卿凯
 * @Date: 2019/9/10/010 19:44
 * @Version: 1.0
 */
public class LogisticsPresenter extends BasePresenter<LogisticsViewImpl> {
    public LogisticsPresenter(LogisticsViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取物流信息
     */
    public void getLogistics(String orderNo, int orderType){
        addDisposable(apiServer.getLogistics(orderNo, orderType), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetLogisticsSuccess((BaseModel<LogisticsResultBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
