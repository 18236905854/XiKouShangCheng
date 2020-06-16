package com.xk.mall.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xk.mall.R;

/**
 * 优惠券不足 弹窗
 */
public class CouponDialog extends Dialog implements View.OnClickListener{

    private Context mContext;
    private String content;
    private OnCloseListener listener;


    public CouponDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CouponDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public CouponDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    public CouponDialog(Context context, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_coupon);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        Button submitTxt = findViewById(R.id.btn_submit);
        submitTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                if(listener != null){
                    listener.onClick(this, true);
                }
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }

}
