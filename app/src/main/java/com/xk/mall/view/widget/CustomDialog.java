package com.xk.mall.view.widget;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.xk.mall.R;

/**
 * @ClassName: CustomDialog
 * @Description: 自定义加载对话框
 * @Author: 卿凯
 * @Date: 2019/11/29/029 17:46
 * @Version: 1.0
 */
public class CustomDialog extends Dialog {
    public CustomDialog(Context context, int themeId) {
        super(context, themeId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        CircleProgressBar pbLoading = findViewById(R.id.pb_header_loading);
        ValueAnimator animator = ValueAnimator.ofInt(0, 100).setDuration(2000);
        animator.addUpdateListener(valueAnimator -> pbLoading.setProgress((int) valueAnimator.getAnimatedValue()));
        animator.start();
    }
}
