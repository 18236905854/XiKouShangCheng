package com.xk.mall.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.utils.PriceUtil;

/**
 * 订单提示对话框
 */
public class WuGOrderTipDialog extends Dialog implements View.OnClickListener{

    private Context mContext;
    private OnCloseListener listener;
    private String btnStr;//确定按钮的文字
    private String title;//标题
    private String content;//提示的内容
    private int couponValue;//优惠券

    public WuGOrderTipDialog(Context context, int themeResId, OnCloseListener listener){
        super(context, themeResId);
        this.listener = listener;
    }

    public WuGOrderTipDialog(Context context, String title, String content, String btnStr) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.content = content;
        this.btnStr = btnStr;
    }

    public WuGOrderTipDialog(Context context, int themeResId, String title, String content, String btnStr) {
        super(context, themeResId);
        this.mContext = context;
        this.title = title;
        this.content = content;
        this.btnStr = btnStr;
    }

    public WuGOrderTipDialog(Context context, int themeResId, String title, String content, int couponValue, String btnStr, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
        this.title = title;
        this.content = content;
        this.couponValue = couponValue;
        this.btnStr = btnStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_wug_order_tip);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        TextView tvTitle = findViewById(R.id.tv_dialog_title);
        if(!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }
        TextView tvContent = findViewById(R.id.tv_content);
        if(!TextUtils.isEmpty(content)){
            tvContent.setText(content);
        }
        Button submitTxt = findViewById(R.id.btn_submit);
        if(!TextUtils.isEmpty(btnStr)){
            submitTxt.setText(btnStr);
        }
        TextView tvCoupon = findViewById(R.id.tv_coupon);
        SpannableString spannableString = new SpannableString("确认后将获得 " + PriceUtil.divideCoupon(couponValue) + " 优惠券");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#F94119"));
        spannableString.setSpan(colorSpan,  7, spannableString.length() - 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvCoupon.setText(spannableString);

        submitTxt.setOnClickListener(this);
        ImageView cancelTxt = findViewById(R.id.img_cha);
        cancelTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_cha:
                if(listener != null){
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.btn_submit:
                if(listener != null){
                    listener.onClick(this, true);
                    dismiss();
                }
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }

}
