package com.xk.mall.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.HomeFuncationBean;

import java.util.List;

/**
 * 首页功能清单adapter
 */
public class HomeFunctionAdapter  extends  RvAdapter<HomeFuncationBean>{
    private Context mContext;
    public HomeFunctionAdapter(Context context, List<HomeFuncationBean> list, RvListener listener) {
        super(context, list, listener);
        this.mContext=context;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.home_function_item;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new HomeFunctionHolder(view,viewType,listener);
    }

    private class HomeFunctionHolder extends RvHolder<HomeFuncationBean>{
        private ImageView imgFuncation;
        private TextView  tvFunctionName;

        public HomeFunctionHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);

            imgFuncation=itemView.findViewById(R.id.img_function);
            tvFunctionName=itemView.findViewById(R.id.tv_function_name);

        }

        @Override
        public void bindHolder(HomeFuncationBean bean, int position) {
             tvFunctionName.setText(bean.getTitle());
             imgFuncation.setImageResource(bean.getResourceId());
        }
    }


}
