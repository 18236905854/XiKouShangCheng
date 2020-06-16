package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.MaterialBean;
import com.xk.mall.model.entity.ResultPageBean;

/**
 * @ClassName: MaterialViewImpl
 * @Description: 喜扣素材页面接口
 * @Author: 卿凯
 * @Date: 2019/12/9/009 16:33
 * @Version: 1.0
 */
public interface MaterialViewImpl extends BaseViewListener {
    //获取素材成功
    void onGetMaterialSuccess(BaseModel<ResultPageBean<MaterialBean>> baseModel);
}
