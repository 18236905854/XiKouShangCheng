package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.NearAddressBean;
import com.xk.mall.model.entity.NearBean;

import java.util.List;

/**
 * ClassName NearChildViewImpl
 * Description 附近页面获取店铺列表信息的view实现类
 * Author 卿凯
 * Date 2019/6/21/021
 * Version V1.0
 */
public interface NearChildViewImpl extends BaseViewListener {
    //根据类型获取店铺列表成功回调
    void onShopListSuccess(BaseModel<NearAddressBean> baseModel, int type);
}
