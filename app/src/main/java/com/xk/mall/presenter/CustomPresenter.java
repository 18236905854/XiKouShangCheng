package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CommentsBean;
import com.xk.mall.model.entity.CustomGuanBean;
import com.xk.mall.model.impl.CustomViewImpl;
import com.xk.mall.model.request.AttentionRequestBody;
import com.xk.mall.model.request.CommentRequestBody;

import java.util.List;

/**
 * ClassName CustomPresenter
 * Description 定制馆页面请求的presenter
 * Author 卿凯
 * Date 2019/6/20/020
 * Version V1.0
 */
public class CustomPresenter extends BasePresenter<CustomViewImpl> {
    public CustomPresenter(CustomViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取定制馆数据
     */
    public void getCustomerGuanData(String userId,int page,int limit){
        addDisposable(apiServer.getCustomGuanData(userId,page,limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetCustomSuc((BaseModel<CustomGuanBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 添加点赞
     */
    public void addOnPraiseorSuc(AttentionRequestBody requestBody){
        addDisposable(apiServer.addDianZanOrCancel(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onPraiseorSuc((BaseModel)o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
    // 取消点赞
    public void cancelPraiseorSuc(AttentionRequestBody requestBody){
        addDisposable(apiServer.addDianZanOrCancel(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onCancelPraiseSuc((BaseModel)o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 添加关注
     */
    public void addAttentionDesigner(AttentionRequestBody requestBody){
        addDisposable(apiServer.addAttentionDesigner(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onAttentionSuc((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
    //获取作品 评论列表 根据workId, 设计师id
    public void getCommentList(String wordId,String designerId,int page, int limit){
        addDisposable(apiServer.getWorkCommentList(wordId,designerId,page,limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetCommentList((BaseModel<CommentsBean>) o);
            }
            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 发布评论
     * @param requestBody  请求参数
     * @count  评论数量
     */
    public void publishComment(CommentRequestBody requestBody,int count){
        addDisposable(apiServer.addComments(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.publishCommentSuc(requestBody, count);
            }
            @Override
            public void onError(String msg) {

            }
        });
    }

}
