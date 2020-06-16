package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.CommentsBean;
import com.xk.mall.model.entity.CustomGuanBean;
import com.xk.mall.model.request.CommentRequestBody;

import java.util.List;

/**
 * 定制馆页面的view接口
 */
public interface CustomViewImpl extends BaseViewListener {
    //获取列表成功
    void onGetCustomSuc(BaseModel<CustomGuanBean> baseModel);

    //点赞
    void onPraiseorSuc(BaseModel model);

    //取消点赞
    void onCancelPraiseSuc(BaseModel model);

    //关注
    void onAttentionSuc(BaseModel model);

//    //获取评论 回复列表
    void onGetCommentList(BaseModel<CommentsBean> baseModel);

    //发布评论成功
    void publishCommentSuc(CommentRequestBody model,int count);

}
