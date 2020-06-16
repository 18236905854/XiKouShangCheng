package com.xk.mall.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.gen.AddressServerBeanDao;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.view.activity.EditorAddressActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName ChooseAddressDialog
 * Description 地址选择框
 * Author 卿凯
 * Date 2019/6/29/029
 * Version V1.0
 */
public class ChooseAddressDialog extends BottomSheetDialog {

    private List<AddressBean> addressBeanList;
    private OnRVItemClickListener listener;
    private CutChooseAddressAdapter cutChooseAddressAdapter;

    public CutChooseAddressAdapter getCutChooseAddressAdapter() {
        return cutChooseAddressAdapter;
    }


    public void setListener(OnRVItemClickListener listener) {
        this.listener = listener;
    }

    public ChooseAddressDialog(@NonNull Context context,List<AddressBean> listData) {
        super(context);
        setContentView(R.layout.layout_cut_choose_address);
        this.addressBeanList=listData;

        RecyclerView rvCutChooseAddress = findViewById(R.id.rv_cut_choose_address);
        ImageView ivClose = findViewById(R.id.tv_sku_close);
        ivClose.setOnClickListener(v -> {
            dismiss();
        });
        TextView tvAddAddress = findViewById(R.id.tv_cut_choose_address_add);
        tvAddAddress.setOnClickListener(v -> {
            dismiss();
            //跳转新增地址页面
            ActivityUtils.startActivity(EditorAddressActivity.class);
        });
        rvCutChooseAddress.setLayoutManager(new LinearLayoutManager(context));


         cutChooseAddressAdapter = new CutChooseAddressAdapter(context, R.layout.item_cut_choose_address, addressBeanList);
        rvCutChooseAddress.setAdapter(cutChooseAddressAdapter);
        cutChooseAddressAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //点击选择地址之后跳转到继续砍价页面
                if(listener != null){
                    TextView viewById = holder.itemView.findViewById(R.id.tv_cut_choose_address_address);
                    Log.e("fufu", "onItemClick: "+viewById.getText().toString() );
                    listener.onItemClick(addressBeanList.get(position),viewById.getText().toString());
                }
                dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    public interface OnRVItemClickListener {
        void onItemClick(AddressBean addressBean,String allAddress);
    }

    /**
     * 选择地址的adapter
     */
    class CutChooseAddressAdapter extends CommonAdapter<AddressBean> {

        public CutChooseAddressAdapter(Context context, int layoutId, List<AddressBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, AddressBean addressBean, int position) {
            holder.setText(R.id.tv_cut_address_name, addressBean.consigneeName);
            holder.setText(R.id.tv_cut_address_phone, addressBean.consigneeMobile);
            TextView tvDetailAddress=holder.getView(R.id.tv_cut_choose_address_address);

            String province = "";
            AddressServerBean provinceBean = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().queryBuilder()
                    .where(AddressServerBeanDao.Properties.AreaId.eq(addressBean.provinceId)).build().unique();
            if(provinceBean != null){
                province = provinceBean.name;
            }
            String city = "";
            AddressServerBean cityBean = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().queryBuilder()
                    .where(AddressServerBeanDao.Properties.AreaId.eq(addressBean.cityId)).build().unique();
            if(cityBean != null){
                city = cityBean.name;
            }
            String area = "";
            AddressServerBean areaBean = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().queryBuilder()
                    .where(AddressServerBeanDao.Properties.AreaId.eq(addressBean.areaId)).build().unique();
            if(areaBean != null){
                area = areaBean.name;
            }
            String allAddress=new StringBuffer().append(province).append(city).append(area)
                    .append(addressBean.address).toString();
            tvDetailAddress.setText(allAddress);
        }
    }
}
