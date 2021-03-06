package com.xk.mall.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xk.mall.interfaces.RvListener;

import java.util.List;

/**
 * 适配器
 */

@SuppressWarnings("unchecked")
public abstract class RvAdapter<T> extends RecyclerView.Adapter<RvHolder> {
    private static final String TAG = "RvAdapter";
    protected List<T> list;
    protected Context mContext;
    protected RvListener listener;
    protected LayoutInflater mInflater;
    private RecyclerView mRecyclerView;

    public RvAdapter(Context context, List<T> list, RvListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.listener = listener;
    }

    public RvAdapter(Context context, List<T> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(getLayoutId(viewType), parent, false);
        return getHolder(view, viewType);
    }

    protected abstract int getLayoutId(int viewType);

    @Override
    public void onBindViewHolder(RvHolder holder, int position) {
        if(position==list.size()){//添加foot 底部
            return;
        }else{
            holder.bindHolder(list.get(position), position);
        }
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    protected abstract RvHolder getHolder(View view, int viewType);

}