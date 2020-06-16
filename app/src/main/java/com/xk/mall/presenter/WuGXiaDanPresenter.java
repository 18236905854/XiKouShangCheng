package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.GlobalBuyerOrderBean;
import com.xk.mall.model.entity.GlobalBuyerOrderResultBean;
import com.xk.mall.model.entity.WuGOrderBean;
import com.xk.mall.model.entity.WuGOrderResultBean;
import com.xk.mall.model.impl.WuGXiaDanViewImpl;
import com.xk.mall.utils.ActivityType;

import java.util.List;

/**
 * ClassName WuGXiaDanPresenter
 * Description 吾G下单页面请求的Presenter
 * Author 卿凯
 * Date 2019/7/15/015
 * Version V1.0
 */
public class WuGXiaDanPresenter extends BasePresenter<WuGXiaDanViewImpl> {
    public WuGXiaDanPresenter(WuGXiaDanViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据用户ID获取地址列表
     * @param userId
     */
    public void getAddress(String userId){
        addDisposable(apiServer.getUserAddressList(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetAddressListSuc((BaseModel<List<AddressBean>>) o);
            }

            @Override
            public void onError(String msg) {
                if (baseView != null) {
                    baseView.showError(msg);
                }
            }
        });
    }

    /**
     * 下单
     */
    public void order(int activityType, WuGOrderBean wuGOrderBean){
        if(activityType == ActivityType.ACTIVITY_WUG){
            addDisposable(apiServer.orderWuG(wuGOrderBean), new BaseObserver(baseView) {
                @Override
                public void onSuccess(Object o) {
                    baseView.onOrderSuccess((BaseModel<WuGOrderResultBean>) o);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }else if(activityType == ActivityType.ACTIVITY_NEW_PERSON){
            addDisposable(apiServer.orderNewPerson(wuGOrderBean), new BaseObserver(baseView) {
                @Override
                public void onSuccess(Object o) {
                    baseView.onOrderSuccess((BaseModel<WuGOrderResultBean>) o);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }
    }
}
