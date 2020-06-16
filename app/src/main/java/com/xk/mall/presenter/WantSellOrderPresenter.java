package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.impl.WantSellOrderViewImpl;
import com.xk.mall.model.request.ModifyOrderTypeRequestBody;

/**
 * ClassName WantSellOrderPresenter
 * Description 我要寄卖订单列表请求Presenter
 * Author 卿凯
 * Date 2019/7/17/017
 * Version V1.0
 */
public class WantSellOrderPresenter extends BasePresenter<WantSellOrderViewImpl> {
    public WantSellOrderPresenter(WantSellOrderViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取全球买手我要寄卖的订单列表
     */
    public void getOrderList(String buyerAccount, int page, int limit){
        addDisposable(apiServer.getWantSell(buyerAccount, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetWantSellOrderListSuccess((BaseModel<OrderPageBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 修改全球买手订单处理方式
     * @param userId 用户ID
     * @param orderNo 订单号
     * @param type 处理方式;1-自提;2-寄卖
     * @param shareModel 分享模式: 1-分享给好友;2-寄卖;3-两者都有
     */
    public void modifyOrderType(String userId, String orderNo, int shareModel, int type){
        ModifyOrderTypeRequestBody requestBody = new ModifyOrderTypeRequestBody();
        requestBody.setBuyerId(userId);
        requestBody.setOrderNo(orderNo);
        requestBody.setProcessingMethod(type);
        requestBody.setShareModel(shareModel);
        addDisposable(apiServer.modifyOrderType(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onModifyOrderTypeSuccess((BaseModel<ShareBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
