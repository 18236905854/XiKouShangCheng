package com.xk.mall.view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.xk.mall.R;

/**
 * ClassName CustomHeader
 * Description 自定义下拉刷新头部
 * Author 卿凯
 * Date 2019/7/3/003
 * Version V1.0
 */
public class CustomHeader extends LinearLayout implements RefreshHeader {
    private ImageView ivLogo;//图标
    private TextView tvContent;//内容
    private CircleProgressBar pbLoading;//加载圈

    public CustomHeader(Context context) {
        super(context);
    }

    public CustomHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }
    public CustomHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    /**
     * 初始化view
     */
    public void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_refresh_header, null);
        addView(view);
        ivLogo = view.findViewById(R.id.iv_header_logo);
        tvContent = view.findViewById(R.id.tv_header_content);
        pbLoading = view.findViewById(R.id.pb_header_loading);
        pbLoading.setProgress(0);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        //开始动画
        //动画效果参数直接定义
        pbLoading.setProgressWithAnimation(100);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 0.1f, 1.0f);
        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatCount(0);
        valueAnimator.addUpdateListener(animation -> {
            float currentValue = (Float) animation.getAnimatedValue();
            // 获得改变后的值
            ivLogo.setAlpha(currentValue);
        });
        valueAnimator.start();
//        Animation animation = new AlphaAnimation(0.1f, 1.0f);
//        animation.setInterpolator(new FastOutSlowInInterpolator());
//        animation.setDuration(5000);
//        ivLogo.startAnimation(animation);
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if (success){
            tvContent.setText("刷新完成");
        } else {
            tvContent.setText("刷新失败");
        }
        pbLoading.setProgress(0);
        return 500;//延迟500毫秒之后再弹回
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                pbLoading.setProgress(0);
                tvContent.setText("下拉开始刷新");
                break;
            case Refreshing:
                tvContent.setText("正在刷新");
                break;
            case ReleaseToRefresh:
                tvContent.setText("释放立即刷新");
                break;
        }
    }
}
