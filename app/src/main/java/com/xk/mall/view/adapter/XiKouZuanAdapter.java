package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.interfaces.RvButtonListener;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.MakeTaskBean;
import com.xk.mall.model.entity.XiKouZuanBean;

import java.util.List;

/**
 * 喜扣赚 adapter
 */
public class XiKouZuanAdapter extends  RvAdapter<XiKouZuanBean>{
    private static final String TAG = "XiKouZuanAdapter";

    private Context mContext;


    public XiKouZuanAdapter(Context context, List<XiKouZuanBean> list, RvListener listener) {
        super(context, list, listener);
        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_xikouzuan;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new GoodsOneHolder(view,viewType,listener);
    }

    private class GoodsOneHolder extends RvHolder<XiKouZuanBean>{
        private ImageView iv_category;
        private TextView  tv_task_title,tv_task_value;

        public GoodsOneHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);

            iv_category=itemView.findViewById(R.id.iv_category);
            tv_task_title=itemView.findViewById(R.id.tv_task_title);
            tv_task_value=itemView.findViewById(R.id.tv_task_value);


        }

        @Override
        public void bindHolder(XiKouZuanBean makeTaskBean, int position) {
            iv_category.setImageResource(makeTaskBean.getResourceId());
            tv_task_title.setText(makeTaskBean.getTaskTitle());
            tv_task_value.setText(makeTaskBean.getTaskValue());

        }
    }
}
