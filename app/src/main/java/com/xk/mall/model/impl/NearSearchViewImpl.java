package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.NearAddressBean;

import java.util.List;

/**
 * ClassName NearSearchViewImpl
 * Description 搜索店铺view接口
 * Author 卿凯
 * Date 2019/7/23/023
 * Version V1.0
 */
public interface NearSearchViewImpl extends BaseViewListener {
    void onGetListSuccess(BaseModel<NearAddressBean> model);
}
