package com.xk.mall.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.model.entity.CommentsBean;
import com.xk.mall.utils.GlideUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * ClassName WorksDetailCommentAdapter
 * Description 作品详情页面评论的adapter
 * Author 卿凯
 * Date 2019/6/21/021
 * Version V1.0
 */
public class WorksDetailCommentAdapter extends CommonAdapter<CommentsBean.ResultBean> {
    public WorksDetailCommentAdapter(Context context, int layoutId, List<CommentsBean.ResultBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CommentsBean.ResultBean commentBean, int position) {
        ImageView ivHead = holder.getView(R.id.iv_comment_head);
        if(commentBean.getHeadUrl() == null || TextUtils.isEmpty(commentBean.getHeadUrl())){
            ivHead.setImageResource(R.drawable.mq_me_head_icon);
        }else {
            GlideUtil.showCircleLoading(mContext,commentBean.getHeadUrl(),ivHead);
        }
//        Glide.with(mContext).load(commentBean.getHeadUrl()).circleCrop().into(ivHead);
        holder.setText(R.id.tv_comment_name, commentBean.getUserName());
        holder.setText(R.id.tv_comment_time, commentBean.getCommentTime());
        holder.setText(R.id.tv_comment_content, commentBean.getCommentContent());
        TextView tvReply = holder.getView(R.id.tv_comment_reply);
        if(TextUtils.isEmpty(commentBean.getReplayContent())){
            tvReply.setVisibility(View.GONE);
        }else {
            tvReply.setVisibility(View.VISIBLE);
            tvReply.setText(commentBean.getName()+"回复："+commentBean.getReplayContent());//设计师名称（魏菲回复：真的吗？就是按照她的风格打造）
        }
    }
}
