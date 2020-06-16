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
 * 寄卖中商品订单提示对话框
 */
public class SellOrderTipDialog extends Dialog implements View.OnClickListener{

    private Context mContext;
    private OnCloseListener listener;
    private String btnStr;//确定按钮的文字
    private String btnStrCancel;//取消按钮的文字
    private String title;//标题
    private String content;//提示的内容

    public SellOrderTipDialog(Context context, int themeResId, String title, String content, String btnStr, String btnStrCancel, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
        this.title = title;
        this.content = content;
        this.btnStr = btnStr;
        this.btnStrCancel = btnStrCancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sell_order);
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
        Button btnCancel = findViewById(R.id.btn_cancel);
        if(!TextUtils.isEmpty(btnStrCancel)){
            btnCancel.setText(btnStrCancel);
        }
        btnCancel.setOnClickListener(this);
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

            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }

}
