package com.xk.mall.model.impl;


import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.model.entity.MainBean;
import com.xk.mall.model.entity.TestBean;
import com.xk.mall.model.entity.UpdateAppBean;

import java.util.List;

/**
 * File descripition:
 *
 */

public interface MainViewImpl extends BaseViewListener {
    void onMainSuccess(BaseModel<List<MainBean>> o);
    void getAddressSuccess(BaseModel<List<AddressServerBean>> listBaseModel);
    /***
     * 刷新token成功
     */
    void onRefreshTokenSuccess(BaseModel<LoginBean> loginBean);

    //检测更新成功
    void checkVersionSuccess(BaseModel<UpdateAppBean> model);
}
