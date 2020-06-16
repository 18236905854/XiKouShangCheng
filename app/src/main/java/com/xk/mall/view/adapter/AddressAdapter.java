package com.xk.mall.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.gen.AddressServerBeanDao;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.activity.EditorAddressActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * ClassName AddressAdapter
 * Description 我的地址页面的adapter
 * Author Kay
 * Date 2019/6/10/010
 * Version V1.0
 */
public class AddressAdapter extends CommonAdapter<AddressBean> {

    private OnDeleteListener deleteListener;
    private OnMainClickListener onMainClickListener;
    private boolean isXiaDan;

    public void setXiaDan(boolean xiaDan) {
        isXiaDan = xiaDan;
    }

    public void setOnMainClickListener(OnMainClickListener onMainClickListener) {
        this.onMainClickListener = onMainClickListener;
    }

    public void setDeleteListener(OnDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }


    public AddressAdapter(Context context, int layoutId, List<AddressBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, AddressBean addressBean, int position) {
        holder.setText(R.id.tv_address_item_name, addressBean.consigneeName);
        holder.setText(R.id.tv_address_item_phone, addressBean.consigneeMobile);
        holder.setText(R.id.tv_address_item_address, XiKouUtils.getAddress(mContext, addressBean));
        TextView tvDefault = holder.getView(R.id.tv_address_default);
        if(addressBean.defaultId == 1){
            tvDefault.setVisibility(View.VISIBLE);
        }else {
            tvDefault.setVisibility(View.GONE);
        }
        if(isXiaDan && !addressBean.outRange){
            holder.setTextColor(R.id.tv_address_item_name, Color.parseColor("#CCCCCC"));
            holder.setTextColor(R.id.tv_address_item_phone, Color.parseColor("#CCCCCC"));
            holder.setTextColor(R.id.tv_address_item_address, Color.parseColor("#CCCCCC"));
            holder.setVisible(R.id.ll_address_out_range, true);
        }else {
            holder.setTextColor(R.id.tv_address_item_name, Color.parseColor("#444444"));
            holder.setTextColor(R.id.tv_address_item_phone, Color.parseColor("#444444"));
            holder.setTextColor(R.id.tv_address_item_address, Color.parseColor("#999999"));
            holder.setVisible(R.id.ll_address_out_range, false);
        }
        holder.setOnClickListener(R.id.iv_address_item_editor, v -> {
         //编辑按钮的点击事件
            Intent intent = new Intent(mContext, EditorAddressActivity.class);
            intent.putExtra(Constant.INTENT_ADDRESS_BEAN, addressBean);
            intent.putExtra(EditorAddressActivity.ADDRESS_ID, addressBean.id);
            ActivityUtils.startActivity(intent);
        });
        RelativeLayout relativeLayout=holder.getView(R.id.rl_main);
        relativeLayout.setOnClickListener(v -> {
            onMainClickListener.onMainClick(position);
        });


        Button btnDelete = holder.getView(R.id.btn_delete_address);
        btnDelete.setOnClickListener(v -> {
            if(deleteListener != null){
                deleteListener.onDelete(addressBean, position);
            }
        });
    }

    public interface OnDeleteListener{
        void onDelete(AddressBean addressBean, int position);
    }

    public interface OnMainClickListener{
        void onMainClick(int position);
    }
}
