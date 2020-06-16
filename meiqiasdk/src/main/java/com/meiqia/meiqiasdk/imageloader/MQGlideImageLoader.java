package com.meiqia.meiqiasdk.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/6/28 下午6:51
 * 描述:
 */
public class MQGlideImageLoader extends MQImageLoader {

    @Override
    public void displayImage(Activity activity, final ImageView imageView, String path, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, final MQDisplayImageListener listener) {
        final String finalPath = getPath(path);
        Glide.with(activity).load(finalPath).placeholder(loadingResId).error(failResId).override(width, height)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (listener != null) {
                            listener.onSuccess(imageView, finalPath);
                        }
                        return false;
                    }
                }).into(imageView);
    }

    @Override
    public void downloadImage(Context context, String path, final MQDownloadImageListener listener) {
        final String finalPath = getPath(path);
        Glide.with(context.getApplicationContext()).load(finalPath).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (listener != null) {
                    listener.onSuccess(finalPath, drawableToBitmap(resource));
                }
            }
        });
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}