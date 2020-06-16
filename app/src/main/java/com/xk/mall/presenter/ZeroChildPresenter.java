package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ZeroCurrentPriceBean;
import com.xk.mall.model.entity.ZeroGoodsBean;
import com.xk.mall.model.impl.ZeroChildImpl;

import java.util.List;

/**
 * ClassName ZeroChildPresenter
 * Description 0元竞拍页面的轮次的请求Presenter
 * Author 卿凯
 * Date 2019/7/5/005
 * Version V1.0
 */
public class ZeroChildPresenter extends BasePresenter<ZeroChildImpl> {

    public ZeroChildPresenter(ZeroChildImpl baseView) {
        super(baseView);
    }

    /**
     * 根据轮次ID获取该轮次下的活动商品
     * @param roundId  轮次ID
     */
    public void getGoodsByRoundId(String roundId){
        addDisposable(apiServer.getGoodsByRoundId(roundId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                if(baseView != null){
                    baseView.onGetGoodsByRoundIdSuccess((BaseModel<List<ZeroGoodsBean>>) o);
                }
            }

            @Override
            public void onError(String msg) {

            }
        });
    }


    /**
     * 根据轮次ID获取该轮次下的活动商品 当前竞拍价 剩余时间
     * @param roundId  轮次ID
     */
    public void getGoodsCurrentPriceByRoundId(String roundId){
        addDisposable(apiServer.getZeroFragGoodsInfo(roundId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                if(baseView != null){
                    baseView.onGetGoodsCurrentPrice((BaseModel<List<ZeroCurrentPriceBean>>) o);
                }
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
