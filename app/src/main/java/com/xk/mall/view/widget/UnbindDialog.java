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
 * 删除 确认是否 弹窗
 */
public class UnbindDialog extends Dialog implements View.OnClickListener{

    private Context mContext;
    private OnCloseListener listener;


    public UnbindDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public UnbindDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public UnbindDialog(Context context, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_unbind);
        setCanceledOnTouchOutside(false);
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
