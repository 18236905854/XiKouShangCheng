package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AttentionBean;
import com.xk.mall.model.entity.AttentionListBean;
import com.xk.mall.model.entity.DesignerBean;

import java.util.List;

/**
 * ClassName AttentionViewImpl
 * Description 我的关注的view层接口
 * Author 卿凯
 * Date 2019/6/11/011
 * Version V1.0
 */
public interface AttentionViewImpl extends BaseViewListener {
    //获取关注列表成功
    void onAttentionSuccess(BaseModel<DesignerBean> addressBeanList);

    //取消关注成功
    void cancelAttentionSuccess(BaseModel baseModel);

}
