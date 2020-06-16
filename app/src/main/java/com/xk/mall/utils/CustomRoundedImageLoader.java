package com.xk.mall.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xk.mall.R;
import com.youth.banner.loader.ImageLoaderInterface;

/**
 * @ClassName: CustomRoundedImageLoader
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/12/3/003 18:19
 * @Version: 1.0
 */
public class CustomRoundedImageLoader implements ImageLoaderInterface {

    @Override
    public void displayImage(Context context, Object path, View imageView) {
        Glide.with(context).load(path).placeholder(R.drawable.ic_loading).error(R.drawable.ic_loading)
                .centerCrop().into((ImageView) imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        RoundedImageView roundedImg = new RoundedImageView(context, null);
        roundedImg.setCornerRadius(DensityUtils.dp2px(context, 5));
        return roundedImg;
    }
}
