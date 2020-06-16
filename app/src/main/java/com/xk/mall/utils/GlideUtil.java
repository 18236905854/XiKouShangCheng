package com.xk.mall.utils;

import android.content.Context;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.xk.mall.R;

import java.util.List;

import cc.shinichi.library.ImagePreview;

/**
 * ClassName GlideUtil
 * Description Glide加载图片的工具类
 * Author 卿凯
 * Date 2019/6/24/024
 * Version V1.0
 */
public class GlideUtil {
    /**
     * 加载原始图片
     */
    public static void show(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).error(R.drawable.ic_loading)
                .placeholder(R.drawable.ic_loading).into(imageView);
    }

    /**
     * 显示圆形图片
     */
    public static void showCircleLoading(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).circleCrop().error(R.drawable.ic_loading)
                .placeholder(R.drawable.ic_loading).into(imageView);
    }

    /**
     * 显示圆角图片
     */
    public static void showRadius(Context context, String url, int radius, ImageView imageView){
        RequestOptions options = RequestOptions.bitmapTransform(new RoundedCorners(ConvertUtils.dp2px(radius)));
        Glide.with(context).load(url).apply(options).error(R.drawable.ic_loading)
                .placeholder(R.drawable.ic_loading).into(imageView);
    }

    /**
     * 显示用户头像
     */
    public static void showUserCircle(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).circleCrop().error(R.drawable.mq_me_head_icon)
                .placeholder(R.drawable.mq_me_head_icon).into(imageView);
    }

    // 一行代码即可实现大部分需求，如需定制，可参考下面自定义的代码：
    public static void lookBigImage(Context mContext,List<String> list, int position){
        if(list == null || list.size() == 0){
            return;
        }
        ImagePreview.getInstance()
                .setContext(mContext)
                .setImageList(list)
                .setIndex(position)
                .setEnableDragClose(true)//下拉关闭
                .setShowDownButton(false)//下载按钮
                // 设置失败时的占位图，默认为库中自带R.drawable.load_failed，设置为 0 时不显示
                .setErrorPlaceHolder(R.drawable.ic_loading)
                .start();
    }

    // 一行代码即可实现大部分需求，如需定制，可参考下面自定义的代码：
    public static void lookBigImageShowLoad(Context mContext,List<String> list, int position){
        if(list == null || list.size() == 0){
            return;
        }
        ImagePreview.getInstance()
                .setContext(mContext)
                .setImageList(list)
                .setIndex(position)
                .setEnableDragClose(true)//下拉关闭
                .setShowDownButton(true)//下载按钮
                .setDownIconResId(R.drawable.ic_img_download)
                // 设置失败时的占位图，默认为库中自带R.drawable.load_failed，设置为 0 时不显示
                .setErrorPlaceHolder(R.drawable.ic_loading)
                .start();
    }

}
