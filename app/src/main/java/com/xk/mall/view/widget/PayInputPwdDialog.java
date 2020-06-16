package com.xk.mall.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.xk.mall.R;

public class PayInputPwdDialog  extends Dialog {
    private Context context;
    public PayInputPwdDialog(@NonNull Context context) {
        this(context, R.style.CommonBottomDialogStyle);
    }

    public PayInputPwdDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_pwd);

    }


}
