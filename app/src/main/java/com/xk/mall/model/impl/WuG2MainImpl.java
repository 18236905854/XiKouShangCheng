package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.WuGPageBean;

/**
 * @ClassName: WuG2MainImpl
 * @Description: 新版吾G页面的view接口
 * @Author: 卿凯
 * @Date: 2019/9/20/020 10:03
 * @Version: 1.0
 */
public interface WuG2MainImpl extends BaseViewListener {
    //获取版块信息成功的回调
    void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model);

    //获取吾G商品数据成功
    void onGetWuGDataSuccess(BaseModel<WuGPageBean> model);

    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);
}
