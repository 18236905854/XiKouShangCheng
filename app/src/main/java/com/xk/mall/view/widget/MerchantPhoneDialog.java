package com.xk.mall.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.PhoneUtils;
import com.xk.mall.R;
import com.xk.mall.utils.XiKouUtils;

/**
 * @ClassName: MerchantPhoneDialog
 * @Description: 联系商家客服对话框
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class MerchantPhoneDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private String phone;

    public MerchantPhoneDialog(Context context, int themeResId, String phone) {
        super(context, themeResId);
        this.phone = phone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_merchant_phone);
        LinearLayout llPhoneTwo = findViewById(R.id.ll_merchant_phone_two);
        TextView tvPhoneOne = findViewById(R.id.tv_merchant_phone_one);
        TextView tvPhoneTwo = findViewById(R.id.tv_merchant_phone_two);
        if(phone.contains(",")){
            llPhoneTwo.setVisibility(View.VISIBLE);
            String[] phones = phone.split(",");
            tvPhoneOne.setText(XiKouUtils.formatMerchantPhone(phones[0]));
            tvPhoneTwo.setText(XiKouUtils.formatMerchantPhone(phones[1]));
            tvPhoneOne.setOnClickListener(v -> PhoneUtils.dial(phones[0]));
            tvPhoneTwo.setOnClickListener(v -> PhoneUtils.dial(phones[1]));
        }else {
            tvPhoneOne.setText(XiKouUtils.formatMerchantPhone(phone));
            tvPhoneOne.setOnClickListener(v -> PhoneUtils.dial(phone));
            llPhoneTwo.setVisibility(View.GONE);
        }
        findViewById(R.id.img_cha).setOnClickListener(v -> dismiss());
    }

    @Override
    public void onClick(View v) {

    }
}
