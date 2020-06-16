package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.MessageBean;
import com.xk.mall.model.impl.MessageDetailImpl;

/**
 * ClassName MessageDetailPresenter
 * Description 消息详情页面获取数据的Presenter
 * Author 卿凯
 * Date 2019/7/4/004
 * Version V1.0
 */
public class MessageDetailPresenter extends BasePresenter<MessageDetailImpl> {
    public MessageDetailPresenter(MessageDetailImpl baseView) {
        super(baseView);
    }

    /**
     * 查看消息详情
     * @param typeId 消息类型ID
     */
    public void getMsgDetail(String typeId, String msgId){
        addDisposable(apiServer.getMessageDetail(msgId, typeId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onMessageDetailSuccess((BaseModel<MessageBean.MessageChildBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
