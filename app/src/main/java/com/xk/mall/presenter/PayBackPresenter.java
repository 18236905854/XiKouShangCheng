package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.impl.PayBackViewImpl;

/**
 * @ClassName: PayBackPresenter
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/12/9/009 19:50
 * @Version: 1.0
 */
public class PayBackPresenter extends BasePresenter<PayBackViewImpl> {
    public PayBackPresenter(PayBackViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取退款退货订单列表
     * @param phone 用户账号
     * @param orderType  订单类型;1-0元竞拍订单;2-多买多折订单;3-砍立得订单;4-吾G订单;5-全球买手订单;6-定制拼团订单;7:OTO订单;8:代付订单;9:新人专区订单
     * @param page 页数
     * @param limit 每页条数
     */
    public void getPayBackList(String phone, int orderType, int page, int limit){
        addDisposable(apiServer.getPayBackOrderList(phone, orderType, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetPayBackListSuccess((BaseModel<OrderPageBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 取消退款
     * @param refundOrderNo 退款订单
     */
    public void cancelPayBack(String refundOrderNo){
        addDisposable(apiServer.cancelSalesReturn(refundOrderNo), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.cancelSalesReturnSuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
