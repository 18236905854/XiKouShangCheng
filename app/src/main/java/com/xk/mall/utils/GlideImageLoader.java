package com.xk.mall.utils;

import android.content.Context;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.xk.mall.R;
import com.youth.banner.loader.ImageLoader;

/**
 * ClassName GlideImageLoader
 * Description Banner加载图片的loader
 * Author 卿凯
 * Date 2019/6/15/015
 * Version V1.0
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        RequestOptions options = RequestOptions.bitmapTransform(new RoundedCorners(ConvertUtils.dp2px(5)));
        Glide.with(context).load(path).apply(options).placeholder(R.drawable.ic_loading).error(R.drawable.ic_loading)
                .centerCrop().into(imageView);
    }
}
