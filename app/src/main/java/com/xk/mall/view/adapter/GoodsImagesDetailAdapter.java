package com.xk.mall.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.HotGoodsBean;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.ImageUtil;

import java.util.List;

/**
 * 商品详情图 adapter
 */
public class GoodsImagesDetailAdapter extends  RvAdapter<String>{
    private Context mContext;
    public GoodsImagesDetailAdapter(Context context, List<String> list, RvListener listener) {
        super(context, list, listener);

        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_goods_detail_img;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new GoodsOneHolder(view,viewType,listener);
    }

    private class GoodsOneHolder extends RvHolder<String>{
        private ImageView ivLogo;


        public GoodsOneHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            ivLogo=itemView.findViewById(R.id.iv_goods_detail_img);

        }

        @Override
        public void bindHolder(String url, int position) {
            ivLogo.setImageResource(R.drawable.ic_loading);
            //获取图片真正的宽高
            Glide.with(mContext).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    float bitWidth = bitmap.getWidth();
                    float bitHeight = bitmap.getHeight();
                    float ratio = bitWidth / bitHeight;//真实图片宽高比
                    //图片超出GPU对于openglRender最大限制缩放处理
                    if(bitHeight > ImageUtil.getOpenglRenderLimitValue()){
//                        bitWidth = bitWidth * ImageUtil.getOpenglRenderLimitValue() / bitHeight;
                        bitHeight = ImageUtil.getOpenglRenderLimitValue();
                    }
                    bitmap.setWidth((int) bitWidth);
                    bitmap.setHeight((int) bitHeight);
                    // 获得屏幕宽高
                    DisplayMetrics displayMetrics = DensityUtils.getDisplayMetrics(mContext);
                    int screenWidth = displayMetrics.widthPixels - DensityUtils.dp2px(mContext,30);
                    int screenHeiht = displayMetrics.heightPixels;
                    int height = (int)(screenWidth/ratio);  //屏幕宽度 / 宽高比 得到图片应设置的高度
                    //动态设置ImageView 高度
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ivLogo.getLayoutParams();
                    layoutParams.width = screenWidth;
                    layoutParams.height= height;
                    // ImageView 控件设置
                    ivLogo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ivLogo.setAdjustViewBounds(true);
                    ivLogo.setLayoutParams(layoutParams);
                    ivLogo.setImageBitmap(bitmap);

                }
            });
        }
    }


}
