package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.RedBagDetailBean;
import com.xk.mall.model.entity.TransactionDetailBean;

import java.util.List;

/**
 * 交易明细 adapter
 */
public class TransactionDetailAdapter extends  RvAdapter<TransactionDetailBean>{
    private Context mContext;
    public TransactionDetailAdapter(Context context, List<TransactionDetailBean> list, RvListener listener) {
        super(context, list, listener);
        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_redbag_detail;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new TransactionHolder(view,viewType,listener);
    }

    private class TransactionHolder extends RvHolder<TransactionDetailBean>{
        private ImageView img_status;
        private TextView tv_title, tv_content,tv_time,tv_money;

        public TransactionHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);

            img_status=itemView.findViewById(R.id.img_status);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_content=itemView.findViewById(R.id.tv_content);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_money=itemView.findViewById(R.id.tv_money);

        }

        @Override
        public void bindHolder(TransactionDetailBean bean, int position) {
//            if(bean.getType()==0){//商城订单
//                img_status.setImageResource(R.mipmap.ic_shop_order);
//            }else{
//                img_status.setImageResource(R.mipmap.ic_xianxia_order);
//            }

            tv_money.setTextColor(Color.parseColor("#444444"));
            tv_title.setText(bean.getTitle());
            tv_content.setText(bean.getContent());
            tv_money.setText(bean.getMoney());

        }
    }


}
