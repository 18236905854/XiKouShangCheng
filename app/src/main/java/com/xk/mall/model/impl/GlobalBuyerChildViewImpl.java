package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.GlobalBuyerChildPageBean;

/**
 * ClassName GlobalBuyerChildViewImpl
 * Description 全球买手分类子页面的view接口
 * Author 卿凯
 * Date 2019/7/7/007
 * Version V1.0
 */
public interface GlobalBuyerChildViewImpl extends BaseViewListener {
    //获取数据成功的回调
    void onGetDataSuccess(BaseModel<GlobalBuyerChildPageBean> model);
}
