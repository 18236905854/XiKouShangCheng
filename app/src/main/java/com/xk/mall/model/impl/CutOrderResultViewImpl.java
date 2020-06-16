package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.CutRedBean;

import java.util.List;

/**
 * @ClassName: CutOrderResultViewImpl
 * @Description: 喜立得支付完成页面接口
 * @Author: 卿凯
 * @Version: ${VERSION}
 */
public interface CutOrderResultViewImpl extends BaseViewListener {
    //获取喜立得红包数据成功
    void onGetCutRedSuccess(BaseModel<List<CutRedBean>> baseModel);
}
