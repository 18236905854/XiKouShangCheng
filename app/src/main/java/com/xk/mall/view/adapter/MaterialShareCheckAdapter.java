package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvButtonListener;
import com.xk.mall.model.entity.PhotoInfo;
import com.xk.mall.model.entity.ShareCheckBean;
import com.xk.mall.model.entity.XiKouSuCaiBean;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.view.widget.MultiImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;

/**
 * ClassName MaterialShareCheckAdapter
 * Description 分享审核页面adapter
 * Author
 * Date
 * Version
 */
public class MaterialShareCheckAdapter extends CommonAdapter<ShareCheckBean> {
    private static final String TAG = "MaterialShareCheckAdapter";
    private RvButtonListener rvButtonListener;

    public void setRvButtonListener(RvButtonListener rvButtonListener) {
        this.rvButtonListener = rvButtonListener;
    }

    public MaterialShareCheckAdapter(Context context, int layoutId, List<ShareCheckBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ShareCheckBean xiKouSuCaiBean, int position) {
        holder.setText(R.id.tv_share_time, xiKouSuCaiBean.getCreateTime());
        TextView tvState = holder.getView(R.id.tv_share_state);
        TextView tvReason = holder.getView(R.id.tv_share_reason);//原因
        TextView tvNum = holder.getView(R.id.tv_share_num);//贡献值
        if("NONE".equals(xiKouSuCaiBean.getAuditStatus())){//审核中
            tvState.setText("待审核");
            tvState.setTextColor(mContext.getResources().getColor(R.color.color_text));
            tvReason.setVisibility(View.GONE);
            tvNum.setVisibility(View.GONE);
        }else if("PASS".equals(xiKouSuCaiBean.getAuditStatus())){//已通过
            tvState.setText("已通过");
            tvState.setTextColor(Color.parseColor("#999999"));
            tvReason.setVisibility(View.GONE);
            tvNum.setVisibility(View.VISIBLE);
            tvNum.setText("贡献值+" + xiKouSuCaiBean.getAuditIntegral());
        }else if("FAILED".equals(xiKouSuCaiBean.getAuditStatus())){//已失败
            tvState.setText("不通过");
            tvState.setTextColor(Color.parseColor("#F2641E"));
            tvReason.setVisibility(View.VISIBLE);
            tvReason.setText(xiKouSuCaiBean.getAuditReson());
            tvNum.setVisibility(View.GONE);
        }

        RecyclerView rvImg = holder.getView(R.id.rv_img);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImg.setLayoutManager(layoutManager);
        ImageAdapter imageAdapter = new ImageAdapter(mContext, R.layout.item_share_check_img, xiKouSuCaiBean.getAdvertUserImgModels());
        rvImg.setAdapter(imageAdapter);
        List<String> images = new ArrayList<>();
        for(ShareCheckBean.AdvertUserImgModelsBean bean : xiKouSuCaiBean.getAdvertUserImgModels()){
            images.add(bean.getAuditImgUrl());
        }
        imageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                GlideUtil.lookBigImage(mContext, images, position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    class ImageAdapter extends CommonAdapter<ShareCheckBean.AdvertUserImgModelsBean>{

        public ImageAdapter(Context context, int layoutId, List<ShareCheckBean.AdvertUserImgModelsBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ShareCheckBean.AdvertUserImgModelsBean s, int position) {
            ImageView imageView = holder.getView(R.id.iv_share_check_img);
            GlideUtil.show(mContext, s.getAuditImgUrl(), imageView);
        }
    }
}
