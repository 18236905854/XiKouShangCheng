package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.RedBagDetailBean;
import com.xk.mall.model.entity.SettlementMxChildBean;
import com.xk.mall.model.entity.WithDrawDetailBean;
import com.xk.mall.model.entity.WithDrawMxChildBean;
import com.xk.mall.utils.PriceUtil;

import java.util.List;

/**
 * 提现明细 adapter
 */
public class WithDrawDetailAdapter extends  RvAdapter<WithDrawMxChildBean>{
    private Context mContext;
    public WithDrawDetailAdapter(Context context, List<WithDrawMxChildBean> list, RvListener listener) {
        super(context, list, listener);
        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_withdraw_detail;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new WithDrawDetailHolder(view,viewType,listener);
    }

    private class WithDrawDetailHolder extends RvHolder<WithDrawMxChildBean>{
        private ImageView img_status;
        private TextView tv_title, tv_content,tv_time,tv_withdraw_status;

        public WithDrawDetailHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);

            img_status=itemView.findViewById(R.id.img_status);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_content=itemView.findViewById(R.id.tv_content);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_withdraw_status=itemView.findViewById(R.id.tv_withdraw_status);

        }

        @Override
        public void bindHolder(WithDrawMxChildBean bean, int position) {
            if(bean.getState() == 1){
                tv_withdraw_status.setText("到账中");
                tv_withdraw_status.setTextColor(Color.parseColor("#444444"));
            }else if(bean.getState() == 2){
                tv_withdraw_status.setText("到账中");
                tv_withdraw_status.setTextColor(Color.parseColor("#444444"));
            }else if(bean.getState() == 3){
                tv_withdraw_status.setText("已到账");
                tv_withdraw_status.setTextColor(Color.parseColor("#CCCCCC"));
            }else if(bean.getState() == 4){
                tv_withdraw_status.setText("提现失败");
                tv_withdraw_status.setTextColor(Color.parseColor("#F94119"));
            }

            tv_title.setText("提现金额" + PriceUtil.dividePrice(bean.getCashAmount()));
            if(bean.getCashBankCard().length() > 4){
                tv_content.setText("银行卡尾号 " + bean.getCashBankCard().substring(bean.getCashBankCard().length() - 4));
            }else {
                tv_content.setText("银行卡尾号 " + bean.getCashBankCard());
            }
            tv_time.setText(bean.getCashTime());

        }
    }


}
