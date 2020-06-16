package com.xk.mall.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xk.mall.R;

/**
 * 订单提示对话框
 */
public class PosterTipDialog extends Dialog implements View.OnClickListener{

    private Context mContext;
    private OnCloseListener listener;
    private String btnStr;//确定按钮的文字
    private String title;//标题
    private String content;//提示的内容

    public PosterTipDialog(Context context) {
        super(context, R.style.mydialog);
        this.mContext = context;
    }

    public PosterTipDialog(Context context, int themeResId, OnCloseListener listener){
        super(context, themeResId);
        this.listener = listener;
    }

    public PosterTipDialog(Context context, String title, String content, String btnStr) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.content = content;
        this.btnStr = btnStr;
    }

    public PosterTipDialog(Context context, int themeResId, String title, String content, String btnStr) {
        super(context, themeResId);
        this.mContext = context;
        this.title = title;
        this.content = content;
        this.btnStr = btnStr;
    }

    public PosterTipDialog(Context context, int themeResId, String title, String content, String btnStr, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
        this.title = title;
        this.content = content;
        this.btnStr = btnStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_poster_tip);
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
                }else {
                    dismiss();
                }
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }

}
