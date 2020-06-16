package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.NearAddressBean;
import com.xk.mall.model.impl.NearChildViewImpl;

/**
 * ClassName NearChildPresenter
 * Description 附近页面获取店铺列表的Presenter
 * Author 卿凯
 * Date 2019/6/21/021
 * Version V1.0
 */
public class NearChildPresenter extends BasePresenter<NearChildViewImpl> {

    public NearChildPresenter(NearChildViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据类型获取店铺列表数据
     * @param rate  折扣最优排序;1:升序；2：降序
     * @param pop   人气最旺排序;1:升序；2：降序
     * @param longitude 经度
     * @param latitude 纬度
     * @param page 页数
     * @param limit 每页的size
     */
    public void getNearList(int type, int rate, int pop, double longitude, double latitude, int page, int limit){
        addDisposable(apiServer.getNearList(rate, pop, page, limit, longitude, latitude), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onShopListSuccess((BaseModel<NearAddressBean>) o, type);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 根据类型获取店铺列表数据
     * @param rate  折扣最优排序;1:升序；2：降序
     * @param pop   人气最旺排序;1:升序；2：降序
     * @param longitude 经度
     * @param latitude 纬度
     * @param industry1 行业ID
     * @param page 页数
     * @param limit 每页的size
     */
    public void getNearListByType(int type, int rate, int pop, int industry1, double longitude, double latitude, int page, int limit){
        addDisposable(apiServer.getNearListByType(rate, pop, page, limit, industry1, longitude, latitude), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onShopListSuccess((BaseModel<NearAddressBean>) o, type);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
