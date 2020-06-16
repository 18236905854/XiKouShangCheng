package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.impl.EnterLogisticsViewImpl;
import com.xk.mall.model.request.WriteLogisticsRequestBody;

/**
 * @ClassName: EnterLogisticsPresenter
 * @Description: 填写物流信息页面请求类
 * @Author: 卿凯
 * @Date: 2019/12/10/010 14:29
 * @Version: 1.0
 */
public class EnterLogisticsPresenter extends BasePresenter<EnterLogisticsViewImpl> {

    public EnterLogisticsPresenter(EnterLogisticsViewImpl baseView) {
        super(baseView);
    }

    /**
     * 提交物流信息
     * @param expressCompany  物流公司
     * @param logisticsNo  物流单号
     * @param orderNo  订单号
     */
    public void postLogisticInfo(String expressCompany, String logisticsNo, String orderNo){
        WriteLogisticsRequestBody requestBody = new WriteLogisticsRequestBody();
        requestBody.setExpressCompany(expressCompany);
        requestBody.setLogisticsNo(logisticsNo);
        requestBody.setRefundOrderNo(orderNo);
        addDisposable(apiServer.writeLogisticsInfo(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onPostSuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
