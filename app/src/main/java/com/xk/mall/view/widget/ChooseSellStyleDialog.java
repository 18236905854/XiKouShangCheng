package com.xk.mall.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xk.mall.R;

/**
 * @ClassName: ChooseSellStyleDialog
 * @Description: 选择寄卖方式对话框
 * @Author: 卿凯
 * @Date: 2019/10/8/008 15:41
 * @Version: 1.0
 */
public class ChooseSellStyleDialog extends Dialog implements View.OnClickListener{

    CheckBox cbChooseWug;//选择寄卖吾G
    CheckBox cbChooseShare;//选择分享
    LinearLayout llSellTips;//超出寄卖限额的提示布局
    TextView tvFaHuoTips;//商品寄卖后不能再提货的提示
    OnSubmitClickListener listener;
    private int isDirect;//全球买手寄卖能否选择定向(1：可定向 2：不可定向)
    private int whetherToAllow;//全球买手寄卖能否选择寄卖(1：可以寄卖 2：不可寄卖)

    public ChooseSellStyleDialog(Context context) {
        super(context);
    }

    public ChooseSellStyleDialog(Context context, int themeResId, int whetherToAllow,int isDirect, OnSubmitClickListener onSubmitClickListener) {
        super(context, themeResId);
        this.whetherToAllow = whetherToAllow;
        this.isDirect = isDirect;
        this.listener = onSubmitClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_sell_style);
        ImageView ivClose = findViewById(R.id.img_cha);
        TextView tvChooseSell = findViewById(R.id.tv_choose_sell);
        TextView tvChooseShare = findViewById(R.id.tv_choose_share);
        llSellTips = findViewById(R.id.ll_sell_tips);
        tvFaHuoTips = findViewById(R.id.tv_sell_tip);
        cbChooseWug = findViewById(R.id.cb_choose_wug);
        cbChooseShare = findViewById(R.id.cb_choose_share);
        if(whetherToAllow == 2){
            cbChooseWug.setEnabled(false);
            cbChooseWug.setButtonDrawable(R.drawable.ic_cb_default);
//            cbChooseShare.setChecked(true);
            tvFaHuoTips.setVisibility(View.GONE);
            llSellTips.setVisibility(View.VISIBLE);
        }
        if(isDirect == 2){
            cbChooseShare.setEnabled(false);
            cbChooseShare.setButtonDrawable(R.drawable.ic_cb_default);
        }
//        if(consignmentType == 2){
//            cbChooseShare.setEnabled(false);
//            cbChooseWug.setChecked(true);
//            tvChooseShare.setTextColor(Color.parseColor("#9B9B9B"));
//        }else if(consignmentType == 1){
//            cbChooseWug.setEnabled(false);
//            cbChooseShare.setChecked(true);
//            tvChooseSell.setTextColor(Color.parseColor("#9B9B9B"));
//        }else {
//            cbChooseWug.setChecked(true);
//        }
//        if(mustChooseWug == 1){
//            cbChooseWug.setButtonDrawable(R.drawable.ic_cb_disable);
//            cbChooseWug.setEnabled(false);
//        }else {
//            cbChooseWug.setEnabled(true);
//        }
        Button btnSure = findViewById(R.id.btn_submit);
        if(isDirect == 2 && whetherToAllow == 2){
            btnSure.setEnabled(false);
        }
        cbChooseShare.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                cbChooseWug.setChecked(false);
            }
            if(!isChecked){
                btnSure.setEnabled(false);
            }else {
                btnSure.setEnabled(true);
            }
        });

        cbChooseWug.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbChooseShare.setChecked(false);
                }
                if(!isChecked){
                    btnSure.setEnabled(false);
                }else {
                    btnSure.setEnabled(true);
                }
            }
        });
        ivClose.setOnClickListener(this::onClick);
        btnSure.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_cha){
            dismiss();
        }else if(v.getId() == R.id.btn_submit){
            //提交
            if(listener != null){
                listener.onSubmit(cbChooseWug.isChecked(), cbChooseShare.isChecked());
                dismiss();
            }
        }
    }

    public interface OnSubmitClickListener {
        void onSubmit(boolean isWug, boolean isShare);
    }
}
