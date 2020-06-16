package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.CommentsBean;
import com.xk.mall.model.request.CommentRequestBody;

/**
 * 定制馆作品详情 view接口
 */
public interface WorkDetailViewImpl extends BaseViewListener {

    //点赞
    void onPraiseorSuc(BaseModel model);

    //取消点赞
    void onCancelPraiseSuc(BaseModel model);

    //关注
    void onAttentionSuc(BaseModel model);

//    //获取评论 回复列表
    void onGetCommentList(BaseModel<CommentsBean> baseModel);

    //发布评论成功
    void publishCommentSuc(CommentRequestBody model, int count);

}
