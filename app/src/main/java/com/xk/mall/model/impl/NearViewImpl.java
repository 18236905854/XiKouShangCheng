package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.IndustryBean;

import java.util.List;

/**
 * ClassName NearViewImpl
 * Description 附近页面view接口
 * Author 卿凯
 * Date 2019/7/19/019
 * Version V1.0
 */
public interface NearViewImpl extends BaseViewListener {
    //获取所有行业成功回调
    void onGetIndustrySuccess(BaseModel<List<IndustryBean>> model);
}
