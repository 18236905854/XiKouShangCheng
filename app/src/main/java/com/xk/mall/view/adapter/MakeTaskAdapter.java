package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.interfaces.RvButtonListener;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.TaskBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 做任务 adapter
 */
public class MakeTaskAdapter extends CommonAdapter<TaskBean.ListBean> {

    public MakeTaskAdapter(Context context, int layoutId, List<TaskBean.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, TaskBean.ListBean makeTaskBean, int position) {
        ImageView iv_category = holder.getView(R.id.iv_category);
        TextView tv_task_title = holder.getView(R.id.tv_task_title);
        TextView tv_task_value = holder.getView(R.id.tv_task_value);
        TextView tv_status = holder.getView(R.id.tv_status);
        TextView tv_task_num = holder.getView(R.id.tv_task_num);
        View viewBottom=holder.getView(R.id.view_bottom);
        tv_task_title.setText(makeTaskBean.getTaskExplain());
        tv_task_value.setText("+" + makeTaskBean.getAward());
        if(makeTaskBean.getCompleteStatus() == 0){//未完成
            tv_status.setText("去完成");
            tv_status.setBackgroundResource(R.drawable.bg_task_uncomplete);
            tv_status.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
            tv_status.setText("已完成");
            tv_status.setBackgroundResource(R.drawable.bg_task_complete);
            tv_status.setTextColor(Color.parseColor("#999999"));
        }

        if(makeTaskBean.getMaxTimes() <= 0){
            tv_task_num.setVisibility(View.GONE);
        }else {
            tv_task_num.setVisibility(View.VISIBLE);
            tv_task_num.setText(makeTaskBean.getCurTimes() + "/" + makeTaskBean.getMaxTimes());
        }
        //类型：1: 分享、2 获客、3 消费、4 促活、5 用户数据
        if(makeTaskBean.getType() == 1){
            iv_category.setImageResource(R.mipmap.ic_make_task);
        }else if(makeTaskBean.getType() == 2){
            iv_category.setImageResource(R.mipmap.ic_task_new);
        }else if(makeTaskBean.getType() == 3){
            iv_category.setImageResource(R.mipmap.ic_task_pay);
        }else if(makeTaskBean.getType() == 4){
            iv_category.setImageResource(R.mipmap.ic_task_comment);
        }else if(makeTaskBean.getType() == 5){
            iv_category.setImageResource(R.mipmap.ic_task_authen);
        }

        if(position == mDatas.size()-1){
            viewBottom.setVisibility(View.INVISIBLE);
        }else {
            viewBottom.setVisibility(View.VISIBLE);
        }
    }

}
