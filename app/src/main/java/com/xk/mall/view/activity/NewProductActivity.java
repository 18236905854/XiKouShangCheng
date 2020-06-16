package com.xk.mall.view.activity;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.NewProductBean;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GridSpacingItemDecoration;
import com.xk.mall.utils.PriceUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 新品价到
 */
public class NewProductActivity extends BaseActivity {

    @BindView(R.id.tv_new_product_title)
    TextView tvTitle;//今日推荐
    @BindView(R.id.iv_new_product_head)
    ImageView ivHead;//头部图片
    @BindView(R.id.rv_new_product)
    RecyclerView rvNewProduct;//布局

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_product;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("新品价到");
    }

    @Override
    protected void initData() {
        List<NewProductBean> data = new ArrayList<>();
        data.add(new NewProductBean(R.drawable.new_list_one, "Converse/匡威联名红心帆布鞋 白色高帮 150205C", 36000, 72000, 5));
        data.add(new NewProductBean(R.drawable.new_list_two, "DW男织纹40mm手表石英女表36mm 情侣表 DW00100003", 117000, 234000, 2));
        data.add(new NewProductBean(R.drawable.new_list_three, "Dior/迪奥全新烈艳蓝金唇膏口红520 3.5g", 14250, 28500, 1));
        data.add(new NewProductBean(R.drawable.new_list_four, "NIKE/耐克男鞋减震防滑舒适透气运动休闲跑步鞋", 67800, 135600, 4));
        data.add(new NewProductBean(R.drawable.new_list_five, "MICHAEL KORS/迈克 科尔斯 MK女包 CYNTHIA系列黑色…", 116550, 233100, 0));
        data.add(new NewProductBean(R.drawable.new_list_six, "TISSOT/天梭瑞士手表 俊雅系列石英男士手表", 114750, 229500, 7));
        data.add(new NewProductBean(R.drawable.new_list_seven, "LV/路易威登POCHETTE老花单肩手提包邮差包", 648300, 1296600, 7));
        data.add(new NewProductBean(R.drawable.new_list_eight, "YSL/圣罗兰莹亮纯魅唇膏 （圆管口红）4.5g", 6450, 12900, 7));
        rvNewProduct.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvNewProduct.addItemDecoration(new GridSpacingItemDecoration(2, 30, true));
        NewProductAdapter newProductAdapter = new NewProductAdapter(mContext, R.layout.item_new_product, data);
        rvNewProduct.setAdapter(newProductAdapter);
        newProductAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ToastUtils.showShortToast(mContext, "敬请期待");
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    class NewProductAdapter extends CommonAdapter<NewProductBean> {

        public NewProductAdapter(Context context, int layoutId, List<NewProductBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, NewProductBean newPersonBean, int position) {
            holder.setText(R.id.tv_goodsName, newPersonBean.getName());
            holder.setText(R.id.tv_sale_price, PriceUtil.dividePrice(newPersonBean.getNewPrice()));
            holder.setText(R.id.tv_global_coupon, PriceUtil.dividePrice(newPersonBean.getLinePrice()));
            TextView tvLinePrice = holder.getView(R.id.tv_line_price);
            holder.setText(R.id.tv_line_price, getResources().getString(R.string.money) + PriceUtil.dividePrice(newPersonBean.getLinePrice()));
            tvLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.setImageResource(R.id.img_wug_goods, newPersonBean.getLogo());

            ImageView imgView=holder.getView(R.id.img_wug_goods);
            //设置图片为正方形
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imgView.getLayoutParams();
            int deviceWidth = DensityUtils.getDeviceWidth(mContext);
            layoutParams.height = (deviceWidth - DensityUtils.dp2px(mContext, 30)) / 2;
            imgView.setLayoutParams(layoutParams);
        }
    }
}
