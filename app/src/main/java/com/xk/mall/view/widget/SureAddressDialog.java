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
 * ClassName SureAddressDialog
 * Description 继续砍价页面确认收货地址对话框
 * Author 卿凯
 * Date 2019/6/28/028
 * Version V1.0
 */
public class SureAddressDialog extends Dialog implements View.OnClickListener{

    private Context mContext;
    private String name;//名字
    private String phone;//号码
    private String address;//地址
    private String addressBeanId;//地址id
    private OnCloseListener onCloseListener;
    private OnSureListener onSureListener;


    public SureAddressDialog(Context context, int theme, String addressBeanId,String name, String phone, String address, OnSureListener onSureListener,OnCloseListener listener) {
        super(context, theme);
        this.mContext = context;
        this.addressBeanId=addressBeanId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.onSureListener = onSureListener;
        this.onCloseListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sure_address);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        TextView tvName = findViewById(R.id.tv_dialog_sure_address_name);
        TextView tvPhone=findViewById(R.id.tv_dialog_sure_address_phone);
        TextView tvAddress=findViewById(R.id.tv_address);
        tvPhone.setText(phone);
        tvName.setText(name);
        tvAddress.setText(address);

        Button submitTxt =  findViewById(R.id.btn_submit);
        submitTxt.setOnClickListener(this);
        ImageView cancelTxt = findViewById(R.id.img_cha);
        cancelTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_cha:
                if(onCloseListener != null){
                    onCloseListener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.btn_submit:
                if(onSureListener != null){
                    onSureListener.onSure(this,name, phone, address);
                }
                break;
        }
    }

    public interface OnSureListener {
        void onSure(Dialog dialog, String name, String phone, String address);
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }

}
