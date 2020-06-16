package com.xk.mall.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xk.mall.R;

/**
 * 没有优惠券在全球买手页面显示的对话框
 */
public class NoCouponDialog extends Dialog implements View.OnClickListener{

    private Context mContext;
    private OnCloseListener listener;


    public NoCouponDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public NoCouponDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public NoCouponDialog(Context context, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_no_coupon);
        setCanceledOnTouchOutside(false);
        setOnKeyListener((dialog, keyCode, event) -> {
            if(event.getRepeatCount()==0 && keyCode == KeyEvent.KEYCODE_BACK){
                dismiss();
                if(listener != null){
                    listener.onClick(this, false);
                }
            }
            return false;
        });
        initView();
    }

    private void initView(){
        Button submitTxt = findViewById(R.id.btn_submit);
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
                }
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }

}
