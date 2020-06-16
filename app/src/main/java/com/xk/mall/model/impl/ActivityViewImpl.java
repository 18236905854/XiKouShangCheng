package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ActivityBean;
import com.xk.mall.model.entity.BannerBean;

import java.util.List;

/**
 * ClassName ActivityViewImpl
 * Description 活动页面view接口
 * Author 卿凯
 * Date 2019/7/18/018
 * Version V1.0
 */
public interface ActivityViewImpl extends BaseViewListener {
    void onGetActivityDataSuccess(BaseModel<ActivityBean> model);
    void onGetBannerSuc(BaseModel<List<BannerBean>> model);
}
