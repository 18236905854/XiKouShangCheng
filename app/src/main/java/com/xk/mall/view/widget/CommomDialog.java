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
 * 删除 历史 确认是否 弹窗
 */
public class CommomDialog extends Dialog implements View.OnClickListener{

    private Button submitTxt;
    private ImageView cancelTxt;
    private TextView tvContent;

    private Context mContext;
    private String content;
    private OnCloseListener listener;


    public CommomDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CommomDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public CommomDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    public CommomDialog(Context context, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_delete_history);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        tvContent=findViewById(R.id.tv_content);
        tvContent.setText(content);
        submitTxt =  findViewById(R.id.btn_submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = findViewById(R.id.img_cha);
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
