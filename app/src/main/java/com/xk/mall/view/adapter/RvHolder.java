package com.xk.mall.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xk.mall.interfaces.RvListener;

public abstract class RvHolder<T> extends RecyclerView.ViewHolder {
    protected RvListener mListener;

    public RvHolder(View itemView, int type, RvListener listener) {
        super(itemView);
        this.mListener = listener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onItemClick(v.getId(), getAdapterPosition());
                }
            }
        });

    }

    public abstract void bindHolder(T t, int position);

}
