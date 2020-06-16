package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.MessageBean;
import com.xk.mall.model.impl.MessageListImpl;

/**
 * ClassName MessageListPresenter
 * Description 消息列表请求的Presenter
 * Author 卿凯
 * Date 2019/7/4/004
 * Version V1.0
 */
public class MessageListPresenter extends BasePresenter<MessageListImpl> {

    public MessageListPresenter(MessageListImpl baseView) {
        super(baseView);
    }

    /**
     * 根据用户ID和消息类型ID获取消息列表
     */
    public void getMessageList(String userId, String typeId, int page, int limit){
        addDisposable(apiServer.getMessageList(userId, typeId, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetListSuccess((BaseModel<MessageBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
