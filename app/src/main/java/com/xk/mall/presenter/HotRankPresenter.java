package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.BaoPinGoodsBean;
import com.xk.mall.model.entity.CutPageBean;
import com.xk.mall.model.entity.CutShangJiaBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.impl.CutViewImpl;
import com.xk.mall.model.impl.HotRankViewImpl;
import com.xk.mall.model.request.ShareRequestBody;

import java.util.List;

/**
 *
 * Description 热排行请求Presenter
 *
 *
 *
 */
public class HotRankPresenter extends BasePresenter<HotRankViewImpl> {
    public HotRankPresenter(HotRankViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取爆品榜数据
     */
    public void getBaoPinData(){
        addDisposable(apiServer.getBaoPinRankList(), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetBaoPinSuccess((BaseModel<List<BaoPinGoodsBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 获取热推榜数据
     */
    public void getHotPushData(){
        addDisposable(apiServer.getHotPushList(), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetHotPushSuccess((BaseModel<List<BaoPinGoodsBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 获取喜赚榜数据
     */
    public void getXiZuanData(){
        addDisposable(apiServer.getXiZuanList(), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetXiZuanSuccess((BaseModel<List<BaoPinGoodsBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
