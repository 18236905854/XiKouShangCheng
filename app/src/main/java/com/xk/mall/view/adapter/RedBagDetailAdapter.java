package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.HotGoodsBean;
import com.xk.mall.model.entity.RedBagDetailBean;
import com.xk.mall.model.entity.SettlementMxChildBean;
import com.xk.mall.utils.CouponUtil;
import com.xk.mall.utils.PriceUtil;

import java.util.List;

/**
 * 红包明细 adapter
 */
public class RedBagDetailAdapter extends  RvAdapter<SettlementMxChildBean>{
    private Context mContext;
    public RedBagDetailAdapter(Context context, List<SettlementMxChildBean> list, RvListener listener) {
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
        return new GoodsOneHolder(view,viewType,listener);
    }

    private class GoodsOneHolder extends RvHolder<SettlementMxChildBean>{
        private ImageView img_status;
        private TextView tv_title, tv_content,tv_time,tv_money;

        public GoodsOneHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);

            img_status=itemView.findViewById(R.id.img_status);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_content=itemView.findViewById(R.id.tv_content);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_money=itemView.findViewById(R.id.tv_money);

        }

        @Override
        public void bindHolder(SettlementMxChildBean bean, int position) {
            if(bean.getOperateType() == 1){
                img_status.setImageResource(R.mipmap.ic_redbag_shou);
                tv_money.setText("+" + PriceUtil.dividePrice(bean.getChangeValue()));
                tv_money.setTextColor(Color.parseColor("#F94119"));
            }else{
                tv_money.setTextColor(Color.parseColor("#444444"));
                img_status.setImageResource(R.mipmap.ic_redbag_zhi);
                tv_money.setText("-" + PriceUtil.dividePrice(bean.getChangeValue()));
            }
            //支持类型/收入类型：1 ：红包分润，2：使用红包，3：红包提现，4：提现手续费，5:拼团退款，6:购物，7:代付支出，8:代付收入
            tv_title.setText(bean.getBusinessName());
            tv_content.setText(bean.getModuleName());
            tv_time.setText(bean.getCreateTime());
        }
    }


}
