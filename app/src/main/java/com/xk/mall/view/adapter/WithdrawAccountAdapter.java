package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvButtonListener;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.HotGoodsBean;
import com.xk.mall.model.entity.WithdrawAccountBean;
import com.xk.mall.utils.GlideUtil;

import java.util.List;

/**
 * 提现账号 adapter
 */
public class WithdrawAccountAdapter extends  RvAdapter<WithdrawAccountBean>{
    private Context mContext;
    private RvButtonListener rvButtonListener;
    private OnMainClickListener onMainClickListener;

    public void setOnMainClickListener(OnMainClickListener onMainClickListener) {
        this.onMainClickListener = onMainClickListener;
    }

    public void setRvButtonListener(RvButtonListener rvButtonListener) {
        this.rvButtonListener = rvButtonListener;
    }

    public WithdrawAccountAdapter(Context context, List<WithdrawAccountBean> list, RvListener listener) {
        super(context, list, listener);
        this.mContext=context;
    }

    public WithdrawAccountAdapter(Context context, List<WithdrawAccountBean> list) {
        super(context, list);
        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_withdraw_account;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new WithdrawAccountHolder(view,viewType,listener);
    }

    private class WithdrawAccountHolder extends RvHolder<WithdrawAccountBean>{
        private ImageView img_icon;
        private TextView tv_account_name, tv_account_type,tv_account_num;
        private Button deltete;
        private LinearLayout ll_withdraw_main;

        public WithdrawAccountHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            img_icon=itemView.findViewById(R.id.img_icon);
            tv_account_name=itemView.findViewById(R.id.tv_account_name);
            tv_account_type=itemView.findViewById(R.id.tv_account_type);
            tv_account_num=itemView.findViewById(R.id.tv_account_num);
            deltete=itemView.findViewById(R.id.deltete);
            ll_withdraw_main=itemView.findViewById(R.id.ll_withdraw_main);
        }

        @Override
        public void bindHolder(WithdrawAccountBean bean, int position) {
            if(bean.getChannel()==1){//微信
                img_icon.setImageResource(R.mipmap.ic_pay_wechat);
                tv_account_type.setText("实名认证");

            }else if(bean.getChannel()==2) {//支付宝
                img_icon.setImageResource(R.mipmap.ic_pay_zfb);
                tv_account_type.setText("实名认证");

            }else if(bean.getChannel() == 4){
                img_icon.setImageResource(R.mipmap.ic_bank_moren);
                tv_account_type.setText("信用卡");
            }else{//银行卡
                tv_account_type.setText("储蓄卡");
                if(bean.getImage() != null && !TextUtils.isEmpty(bean.getImage())){
                    GlideUtil.show(mContext, bean.getImage(), img_icon);
                }else {
                    if(bean.getBankName().contains("建设")){
                        img_icon.setImageResource(R.mipmap.ic_bank_jianhang);
                    }else if(bean.getBankName().contains("光大")){
                        img_icon.setImageResource(R.mipmap.ic_bank_gda);
                    }else if(bean.getBankName().contains("交通")){
                        img_icon.setImageResource(R.mipmap.ic_bank_jiaotong);
                    }else if(bean.getBankName().contains("农业")){
                        img_icon.setImageResource(R.mipmap.ic_bank_nongye);
                    }else if(bean.getBankName().contains("浦发")){
                        img_icon.setImageResource(R.mipmap.ic_bank_pufa);
                    }else if(bean.getBankName().contains("邮政")){
                        img_icon.setImageResource(R.mipmap.ic_bank_youzhen);
                    }else if(bean.getBankName().contains("招商")){
                        img_icon.setImageResource(R.mipmap.ic_bank_zhaoshang);
                    }else if(bean.getBankName().contains("工商")){
                        img_icon.setImageResource(R.mipmap.ic_bank_gs);
                    }else if(bean.getBankName().contains("中国")){
                        img_icon.setImageResource(R.mipmap.ic_bank_china);
                    }else {
                        img_icon.setImageResource(R.mipmap.ic_bank_moren);
                    }
                }
            }

             tv_account_name.setText(bean.getBankName());
             tv_account_num.setText(getHideBankCardNum(bean.getAccount()));//卡号

             deltete.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     rvButtonListener.onItemClick(v,position);
                 }
             });

            ll_withdraw_main.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     onMainClickListener.onMainClick(position);
                 }
             });

        }
    }

    /**
     * 将银行卡中间八个字符隐藏为*
     */
    public String getHideBankCardNum(String bankCardNum) {
        if (bankCardNum == null) return "未绑定";

        int length = bankCardNum.length();
        String result = "";
        StringBuilder stringBuilder = new StringBuilder();
        if(length > 4){
            String endNum = bankCardNum.substring(length - 4);
            String lastStr = bankCardNum.substring(0, length - 4);
            lastStr = lastStr.replaceAll(".", "*");
            String formatStr = format(stringBuilder, lastStr);
            result = formatStr + " " + endNum;
        }else {
            result = bankCardNum;
        }
        return result;
    }

    private String format(StringBuilder builder, String card){
        if(card.length() > 4){
            String last = card.substring(4);
            builder.append(card.substring(0, 4)).append(" ");
            format(builder, last);
        }else {
            builder.append(card);
        }
        return builder.toString();
    }


    public interface OnMainClickListener{
        void onMainClick(int position);
    }

}
