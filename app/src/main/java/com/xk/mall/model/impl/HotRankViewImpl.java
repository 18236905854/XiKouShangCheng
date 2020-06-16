package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.BaoPinGoodsBean;
import com.xk.mall.model.entity.CutPageBean;
import com.xk.mall.model.entity.CutShangJiaBean;
import com.xk.mall.model.entity.ShareBean;

import java.util.List;


/**
 *
 * Description 热排行view接口
 *
 *
 *
 */
public interface HotRankViewImpl extends BaseViewListener {
    //获取爆品数据成功回调
    void onGetBaoPinSuccess(BaseModel<List<BaoPinGoodsBean>> model);
    //获取热推数据成功回调
    void onGetHotPushSuccess(BaseModel<List<BaoPinGoodsBean>> model);
    //获取喜赚榜数据成功回调
    void onGetXiZuanSuccess(BaseModel<List<BaoPinGoodsBean>> model);

}
