package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.MonthAccountBean;
import com.xk.mall.model.entity.RealNameInfoBean;
import com.xk.mall.model.entity.RedBagMxBean;
import com.xk.mall.model.entity.RedChooseBean;
import com.xk.mall.model.impl.AddBankCardViewImpl;
import com.xk.mall.model.impl.RedBagMXViewImpl;
import com.xk.mall.model.request.AddBankRequestBody;
import com.xk.mall.model.request.RedSearchRequestBody;
import com.xk.mall.utils.XiKouUtils;

/**
 * Description 红包明细 Presenter
 */
public class RedBagMXPresenter extends BasePresenter<RedBagMXViewImpl> {
    public RedBagMXPresenter(RedBagMXViewImpl baseView) {
        super(baseView);
    }

    /**
     * 查询实名认证信息
     * @param userId
     *
     */
    public void getRedBagMXList(String userId,String searchTime,int page,int limit, int businessType){
        if(businessType != -1){
            addDisposable(apiServer.getRedPagDetailListByType(userId,searchTime,page,limit, "" + businessType), new BaseObserver(baseView) {
                @Override
                public void onSuccess(Object o) {
                    baseView.onGetListDataSuc((BaseModel<RedBagMxBean>) o);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }else {
            addDisposable(apiServer.getRedPagDetailList(userId,searchTime,page,limit), new BaseObserver(baseView) {
                @Override
                public void onSuccess(Object o) {
                    baseView.onGetListDataSuc((BaseModel<RedBagMxBean>) o);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }
    }


    /**
     * 查询本月收支统计
     * @param userId 用户ID
     * @param searchTime 查询时间
     */
    public void getMonthAccount(String userId, String searchTime, int businessType){
        if(businessType == 0 || businessType == -1){
            addDisposable(apiServer.queryMonthAccountCount(searchTime, userId), new BaseObserver(baseView) {
                @Override
                public void onSuccess(Object o) {
                    baseView.onGetMonthAccountSuccess((BaseModel<MonthAccountBean>) o);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }else {
            addDisposable(apiServer.queryMonthAccountCountByType(searchTime, userId, "" + businessType), new BaseObserver(baseView) {
                @Override
                public void onSuccess(Object o) {
                    baseView.onGetMonthAccountSuccess((BaseModel<MonthAccountBean>) o);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }
    }

    /**
     * 帐户操作类型查询接口
     */
    public void getAccountType(){
        addDisposable(apiServer.getAccountType(), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetAccountTypeSuccess((BaseModel<RedChooseBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
