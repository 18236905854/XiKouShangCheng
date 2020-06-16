package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.HomeSearchGoodsPageBean;

/**
 * ClassName HomeSearchViewImpl
 * Description 首页搜索view接口
 * Author 卿凯
 * Date 2019/8/2/002
 * Version V1.0
 */
public interface HomeSearchViewImpl extends BaseViewListener {

    //获取搜索数据成功回调
    void onGetSearchResult(BaseModel<HomeSearchGoodsPageBean> model);
}
