package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.impl.PayBackSellViewImpl;

/**
 * @ClassName: PayBackSellPresenter
 * @Description: 我的寄卖订单列表请求类
 * @Author: 卿凯
 * @Date: 2019/12/10/010 10:17
 * @Version: 1.0
 */
public class PayBackSellPresenter extends BasePresenter<PayBackSellViewImpl> {

    public PayBackSellPresenter(PayBackSellViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取我是卖家退款订单列表
     * @param phone 电话号码
     * @param page 页数
     * @param limit 每页的条数
     */
    public void getSellPayBackList(String phone, int page, int limit){
        addDisposable(apiServer.getSellPayBackList(phone, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetPayBackSellListSuccess((BaseModel<OrderPageBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
