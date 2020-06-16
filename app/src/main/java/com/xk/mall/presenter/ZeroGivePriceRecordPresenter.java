package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ZeroAuctionBean;
import com.xk.mall.model.impl.ZeroGivePriceRecordImpl;

import java.util.List;

/**
 * ClassName ZeroGivePriceRecordPresenter
 * Description 0元拍出价记录Presenter
 * Author 卿凯
 * Date 2019/7/6/006
 * Version V1.0
 */
public class ZeroGivePriceRecordPresenter extends BasePresenter<ZeroGivePriceRecordImpl> {
    public ZeroGivePriceRecordPresenter(ZeroGivePriceRecordImpl baseView) {
        super(baseView);
    }

    /**
     * 根据活动商品ID获取出价记录
     */
    public void getReocrdLog(String activityGoodsId,int limit){
        addDisposable(apiServer.getRecordByGoodsId(activityGoodsId ,limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetRecordSuccess((BaseModel<List<ZeroAuctionBean.RecordListBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
