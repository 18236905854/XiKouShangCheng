package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.CustomGuanBean;
import com.xk.mall.model.entity.DesignerInfoBean;
import com.xk.mall.model.entity.ShareBean;

import java.util.List;

/**
 * ClassName CustomViewImpl
 * Description 定制馆页面的view接口
 * Author 卿凯
 * Date 2019/6/20/020
 * Version V1.0
 */
public interface DesignerViewImpl extends BaseViewListener {
    //获取设计师创意数据列表成功
    //获取列表成功
    void onGetCustomSuc(BaseModel<CustomGuanBean> baseModel);

    //获取设计师主页个人信息
    void onGetDesignerInfoSuc(BaseModel<DesignerInfoBean> baseModel);

    //关注 成功
    void onAttentionDesignerSuc(BaseModel baseModel);

    //取消关注
    void onCancelDegisnerSuc(BaseModel baseModel);

    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);
}
