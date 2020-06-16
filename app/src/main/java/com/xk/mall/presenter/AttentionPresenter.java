package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.AttentionBean;
import com.xk.mall.model.entity.AttentionListBean;
import com.xk.mall.model.entity.DesignerBean;
import com.xk.mall.model.impl.AttentionViewImpl;
import com.xk.mall.model.request.AttentionRequestBody;

import java.util.List;

/**
 * ClassName AttentionPresenter
 * Description 我的关注P层接口
 * Author 卿凯
 * Date 2019/6/11/011
 * Version V1.0
 */
public class AttentionPresenter extends BasePresenter<AttentionViewImpl> {
    public AttentionPresenter(AttentionViewImpl baseView) {
        super(baseView);
    }

    /**
     * 调用接口获取数据
     * @param userId
     */
    public void getAttentionDesignerList(String userId,int page, int limit){
        addDisposable(apiServer.getAttentionListDataByID(userId,page,limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onAttentionSuccess((BaseModel<DesignerBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 添加或取消 关注
     */
    public void addAndCancelAttention(AttentionRequestBody requestBody){
        addDisposable(apiServer.addAttentionDesigner(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.cancelAttentionSuccess((BaseModel)o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
