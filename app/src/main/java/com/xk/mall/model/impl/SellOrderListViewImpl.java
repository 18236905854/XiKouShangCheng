package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.entity.RankResultBean;
import com.xk.mall.model.entity.SellOrderPageBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.TaskBean;

/**
 * ClassName SellOrderListViewImpl
 * Description 我的寄卖订单view接口
 * Author 卿凯
 * Date 2019/7/18/018
 * Version V1.0
 */
public interface SellOrderListViewImpl extends BaseViewListener {
    //获取我的寄卖订单成功回调
    void onGetSellOrderListSuccess(BaseModel<SellOrderPageBean> model);
    //提升排名成功回调
//    void onIncreaseRankingSuccess(BaseModel<RankResultBean> model);
    //获取任务中心数据成功回调
    void onGetTaskListSuccess(BaseModel<TaskBean> model);
    //分享或者寄卖
//    void onShareFriendOrAll(BaseModel<ShareBean> model);
    //修改全收买手订单处理方式成功的回调
    void onModifyOrderTypeSuccess(BaseModel<ShareBean> model);
}
