package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.blankj.utilcode.util.TimeUtils;
import com.xk.mall.R;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.gen.AddressServerBeanDao;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.model.entity.OtoOrderBean;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.IndustryUtil;
import com.xk.mall.utils.PriceUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * ClassName OtoLianMAdapter
 * Description OTO联盟适配器
 * Author
 * Date
 * Version
 */
public class OtoLianMAdapter extends CommonAdapter<OtoOrderBean> {

    public OtoLianMAdapter(Context context, int layoutId, List<OtoOrderBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, OtoOrderBean otoOrderBean, int position) {
        holder.setText(R.id.tv_shop_name, otoOrderBean.getShopName());
        if(otoOrderBean.getState() == 1){
            holder.setText(R.id.tv_status, "待支付");
            holder.setTextColor(R.id.tv_status, Color.parseColor("#BB9445"));
        }else if(otoOrderBean.getState() == 5){
            holder.setText(R.id.tv_status, "已完成");
            holder.setTextColor(R.id.tv_status, Color.parseColor("#999999"));
        }
        holder.setText(R.id.tv_xiaofei_money,"消费金额: ¥"+ PriceUtil.dividePrice(otoOrderBean.getTotalAmount()));
        holder.setText(R.id.tv_coupon_money,"优惠券支付: "+ PriceUtil.divideCoupon(otoOrderBean.getOfferAmount()));
        holder.setText(R.id.tv_real_money,"实付金额: ¥"+ PriceUtil.dividePrice(otoOrderBean.getPayAmount()));
        ImageView ivLogo = holder.getView(R.id.iv_shop);
        GlideUtil.showRadius(mContext, otoOrderBean.getImageUrl(), 2, ivLogo);
//        String area = "";
//        AddressServerBean areaBean = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().queryBuilder()
//                .where(AddressServerBeanDao.Properties.AreaId.eq(otoOrderBean.getAreaId())).build().unique();
//        if(areaBean != null){
//            area = areaBean.name;
//        }
        holder.setText(R.id.tv_address, otoOrderBean.getAreaName());
        holder.setText(R.id.tv_shop_type, otoOrderBean.getIndustryName());
        holder.setText(R.id.tv_date, otoOrderBean.getPayTime());


    }
}
