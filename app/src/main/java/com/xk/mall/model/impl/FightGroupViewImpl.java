package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;

/**
 * ClassName FightGroupViewImpl
 * Description 定制拼团页面view接口
 * Author 卿凯
 * Date 2019/7/9/009
 * Version V1.0
 */
public interface FightGroupViewImpl extends BaseViewListener {
    //获取定制拼团数据成功的回调
    void onGetFightGroupDataSuccess(BaseModel<ActiveSectionGoodsPageBean> model);
}
