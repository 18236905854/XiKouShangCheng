package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.NearAddressBean;
import com.xk.mall.model.impl.NearSearchViewImpl;

/**
 * ClassName NearSearchPresenter
 * Description 搜索店铺Presenter
 * Author 卿凯
 * Date 2019/7/23/023
 * Version V1.0
 */
public class NearSearchPresenter extends BasePresenter<NearSearchViewImpl> {
    public NearSearchPresenter(NearSearchViewImpl baseView) {
        super(baseView);
    }

    /**
     * 搜索店铺
     * @param shopName 搜索的名字
     */
    public void getSearchResult(String shopName, double longitude, double latitude, int page, int limit){
        addDisposable(apiServer.searchShop(shopName,longitude, latitude, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetListSuccess((BaseModel<NearAddressBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
