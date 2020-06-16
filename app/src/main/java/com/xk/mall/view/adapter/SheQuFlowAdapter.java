package com.xk.mall.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.CommunityFlowBean;
import com.xk.mall.model.entity.FlowBean;

import java.util.List;

/**
 * 社区流量 adapter
 */
public class SheQuFlowAdapter extends  RvAdapter<CommunityFlowBean.GrowUpsBean>{

    private Context mContext;

    public SheQuFlowAdapter(Context context, List<CommunityFlowBean.GrowUpsBean> list, RvListener listener) {
        super(context, list, listener);
        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_shequ_flow;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new GoodsOneHolder(view,viewType,listener);
    }

    private class GoodsOneHolder extends RvHolder<CommunityFlowBean.GrowUpsBean>{
        private View view_top;
        private ImageView img_point;
        private TextView  tv_title,tv_time;

        public GoodsOneHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            view_top=itemView.findViewById(R.id.view_top);
            img_point=itemView.findViewById(R.id.img_point);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_time=itemView.findViewById(R.id.tv_time);
        }

        @Override
        public void bindHolder(CommunityFlowBean.GrowUpsBean flowBean, int position) {
            if(position==0){
                view_top.setVisibility(View.INVISIBLE);
            }else {
                view_top.setVisibility(View.VISIBLE);
            }
            if(position == 0){
                img_point.setImageResource(R.drawable.flow_circle_select);
            }else{
                img_point.setImageResource(R.drawable.flow_circle_unselect);
            }
            tv_time.setText(flowBean.getDate());
            tv_title.setText(flowBean.getName());
        }
    }
}
