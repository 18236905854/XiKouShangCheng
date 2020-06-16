package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.DesignerBean;
import com.xk.mall.model.entity.DesignerCirclBean;

import java.util.List;

/**
 * 设计师圈view 接口
 */
public interface DesignerCircleViewImpl extends BaseViewListener {
    //获取设计师圈数据
    void onGetDegisnerCircelSuc(BaseModel<DesignerCirclBean> baseModel);

}
