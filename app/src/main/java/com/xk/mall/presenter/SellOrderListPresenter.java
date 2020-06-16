package com.xk.mall.presenter;

import com.xk.mall.MyApplication;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.entity.RankResultBean;
import com.xk.mall.model.entity.SellOrderPageBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.TaskBean;
import com.xk.mall.model.impl.SellOrderListViewImpl;
import com.xk.mall.model.request.ModifyOrderTypeRequestBody;
import com.xk.mall.utils.OrderState;

/**
 * ClassName SellOrderListPresenter
 * Description 我的寄卖订单请求Presenter
 * Author 卿凯
 * Date 2019/7/18/018
 * Version V1.0
 */
public class SellOrderListPresenter extends BasePresenter<SellOrderListViewImpl> {
    public SellOrderListPresenter(SellOrderListViewImpl baseView) {
        super(baseView);
    }

    /***
     * 获取我的寄卖订单数据
     */
    public void getSellOrderList(String userId, int page, int limit){
        addDisposable(apiServer.getSelledOrderList(userId, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetSellOrderListSuccess((BaseModel<SellOrderPageBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 修改全球买手订单处理方式
     * @param userId 用户ID
     * @param orderNo 订单号
     * @param type 处理方式;1-自提;2-寄卖;3-分享
     * @param shareModel 分享模式: 1-分享给好友;2-寄卖;3-两者都有
     */
    public void modifyOrderType(String userId, String orderNo, int shareModel, int type){
        ModifyOrderTypeRequestBody requestBody = new ModifyOrderTypeRequestBody();
        requestBody.setBuyerId(userId);
        requestBody.setOrderNo(orderNo);
//        requestBody.setShareModel(shareModel);
        requestBody.setProcessingMethod(type);
        addDisposable(apiServer.modifySellOrderType(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onModifyOrderTypeSuccess((BaseModel<ShareBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 根据ID提升排名
     * @param id 订单Bean中返回的ID
     */
//    public void increaseRanking(String id){
//        addDisposable(apiServer.increaseRanking(id), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(Object o) {
//                baseView.onIncreaseRankingSuccess((BaseModel<RankResultBean>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//
//            }
//        });
//    }

    /**
     * 获取任务中心列表
     * @param userId
     */
    public void getTaskList(String userId){
        addDisposable(apiServer.getTaskList(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetTaskListSuccess((BaseModel<TaskBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 给好友购买，寄卖到吾G
     * @param commodityId
     * @param shareModel
     */
//    public void shareFriendOrAll(String commodityId, int shareModel){
//        addDisposable(apiServer.shareFriendOrAll(commodityId, shareModel, MyApplication.userId), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(Object o) {
//                baseView.onShareFriendOrAll((BaseModel<ShareBean>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//
//            }
//        });
//    }
}
