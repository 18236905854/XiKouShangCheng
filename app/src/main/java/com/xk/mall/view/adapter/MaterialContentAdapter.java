package com.xk.mall.view.adapter;

import android.content.Context;

import com.blankj.utilcode.util.TimeUtils;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvButtonListener;
import com.xk.mall.model.entity.MaterialBean;
import com.xk.mall.model.entity.PhotoInfo;
import com.xk.mall.utils.DateToolUtils;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.MultiImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;

/**
 * @ClassName: MaterialContentAdapter
 * @Description: 喜扣素材页面adapter
 * @Author: 卿凯
 * @Date: 2019/12/16/016 17:17
 * @Version: 1.0
 */
public class MaterialContentAdapter extends CommonAdapter<MaterialBean> {
    private RvButtonListener rvButtonListener;

    public void setRvButtonListener(RvButtonListener rvButtonListener) {
        this.rvButtonListener = rvButtonListener;
    }

    public MaterialContentAdapter(Context context, int layoutId, List<MaterialBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MaterialBean materialBean, int position) {
        holder.setText(R.id.tv_material_time, DateToolUtils.dateToNYR(materialBean.getUpdateTime()) + "  "
                + DateToolUtils.dateToWeek(materialBean.getUpdateTime()));
        holder.setText(R.id.tv_material_title, materialBean.getMaterialName());
        if(position == 0){
            holder.setVisible(R.id.rl_material_head, true);
        }else {
            holder.setVisible(R.id.rl_material_head, false);
        }
        if(position == mDatas.size() - 1){
            holder.setVisible(R.id.line_material, false);
        }else {
            holder.setVisible(R.id.line_material, true);
        }
        if(XiKouUtils.isNullOrEmpty(materialBean.getMaterialRemark())){
            holder.setVisible(R.id.tv_material_content, false);
        }else {
            holder.setVisible(R.id.tv_material_content, true);
            holder.setText(R.id.tv_material_content, materialBean.getMaterialRemark());
        }
        MultiImageView multiImageView = holder.getView(R.id.multiImageView);
        List<MaterialBean.AdvertImgModelsBean> list = materialBean.getAdvertImgModels();
        List<PhotoInfo> photoInfoList = new ArrayList<>();
        List<String> imageList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            imageList.add(list.get(i).getImgUrl());
            PhotoInfo info = new PhotoInfo(list.get(i).getImgUrl(),0,0);
            photoInfoList.add(info);
        }
        multiImageView.setList(photoInfoList);
        multiImageView.setOnItemClickListener((view, position1) -> {
            // 一行代码即可实现大部分需求，如需定制，可参考下面自定义的代码：
            ImagePreview.getInstance()
                    .setContext(mContext)
                    .setImageList(imageList)
                    .setIndex(position1)
                    .setEnableDragClose(true)//下拉关闭
                    .setShowDownButton(false)//下载按钮
                    // 设置失败时的占位图，默认为库中自带R.drawable.load_failed，设置为 0 时不显示
                    .setErrorPlaceHolder(R.drawable.ic_loading)
                    .start();
        });

        holder.setOnClickListener(R.id.ll_material_share, v -> {
            if(rvButtonListener != null){
                rvButtonListener.onItemClick(v,position);
            }
        });

        holder.setOnClickListener(R.id.ll_material_save, v -> {
            if(rvButtonListener != null){
                rvButtonListener.onItemClick(v,position);
            }
        });
    }
}
