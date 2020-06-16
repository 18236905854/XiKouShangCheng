package com.xk.mall.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xk.mall.R;

/**
 * @ClassName: ChooseSellStyleDialog
 * @Description: 寄卖成功的对话框
 * @Author: 卿凯
 * @Date: 2019/10/8/008 15:41
 * @Version: 1.0
 */
public class SellSuccessDialog extends Dialog implements View.OnClickListener{
    ImageView ivClose;
    Button btnWG;
    Button btnOK;
    OnWuGClickListener listener;
    public SellSuccessDialog(Context context) {
        super(context);
    }

    public SellSuccessDialog(Context context, int themeResId, OnWuGClickListener onWuGClickListener) {
        super(context, themeResId);
        this.listener = onWuGClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sell_success);
        ivClose = findViewById(R.id.img_cha);
        btnWG = findViewById(R.id.btn_cancel);
        btnOK = findViewById(R.id.btn_submit);
        ivClose.setOnClickListener(this::onClick);
        btnWG.setOnClickListener(this::onClick);
        btnOK.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_cancel){
            if(listener != null){
                listener.onClick();
            }
        }
        dismiss();
    }

    public interface OnWuGClickListener {
        void onClick();
    }
}
