package com.xk.mall.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ZeroAuctionBean;
import com.xk.mall.model.impl.ZeroGivePriceRecordImpl;
import com.xk.mall.presenter.ZeroGivePriceRecordPresenter;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName ZeroGivePriceRecordActivity
 * Description 0元拍所有出价记录页面
 * Author 卿凯
 * Date 2019/7/5/005
 * Version V1.0
 */
public class ZeroGivePriceRecordActivity extends BaseActivity<ZeroGivePriceRecordPresenter> implements ZeroGivePriceRecordImpl {

    @BindView(R.id.rv_give_price_record)
    RecyclerView rvGivePriceRecord;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    /**传递过来的商品ID的Key*/
    public static String GOODS_ID = "goods_id";
    /**传递过来的加价优惠券数量key*/
    public static String EXPANDNUMBER_ID = "expandnumber_id";
    List<ZeroAuctionBean.RecordListBean> givePriceBeans;
    private GivePriceAdapter adapter;
    private String expendNumber;//出价费

    @Override
    protected ZeroGivePriceRecordPresenter createPresenter() {
        return new ZeroGivePriceRecordPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_give_price_record;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarColor(R.color.color_title).titleBar(myToolbar).init();
//        myToolbar.setBackgroundColor(getResources().getColor(R.color.color_title));
        myToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initData() {
        String goodsId = getIntent().getStringExtra(GOODS_ID);
        Log.e(TAG, "initData: "+goodsId );
        expendNumber=getIntent().getStringExtra(EXPANDNUMBER_ID);
        mPresenter.getReocrdLog(goodsId,100);
        rvGivePriceRecord.setLayoutManager(new LinearLayoutManager(mContext));

        givePriceBeans = new ArrayList<>();
        adapter = new GivePriceAdapter(mContext, R.layout.zero_give_price_log_item, givePriceBeans);
        rvGivePriceRecord.setAdapter(adapter);
    }

    @Override
    public void onGetRecordSuccess(BaseModel<List<ZeroAuctionBean.RecordListBean>> listBaseModel) {
        givePriceBeans.addAll(listBaseModel.getData());
        adapter.notifyDataSetChanged();
    }

    /**
     * 出价记录的adapter
     */
    class GivePriceAdapter extends CommonAdapter<ZeroAuctionBean.RecordListBean> {

        public GivePriceAdapter(Context context, int layoutId, List<ZeroAuctionBean.RecordListBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ZeroAuctionBean.RecordListBean givePriceBean, int position) {
            ImageView ivType = holder.getView(R.id.iv_type);
            holder.setText(R.id.tv_give_price_item_name, givePriceBean.getUserName());
            holder.setText(R.id.tv_give_price_item_address, givePriceBean.getArea());
            holder.setText(R.id.tv_give_price_item_price,  "+"+expendNumber+"优惠券");
            if(position == 0){
                ivType.setImageResource(R.mipmap.ic_jingjia_win);
                holder.setText(R.id.tv_give_price_item_state, "领先");
                holder.setTextColor(R.id.tv_give_price_item_name, Color.parseColor("#BB9445"));
                holder.setTextColor(R.id.tv_give_price_item_state, Color.parseColor("#BB9445"));
                holder.setTextColor(R.id.tv_give_price_item_address, Color.parseColor("#BB9445"));
                holder.setTextColor(R.id.tv_give_price_item_price, Color.parseColor("#BB9445"));
            }else {
                ivType.setImageResource(R.mipmap.ic_jingjia_normal);
                holder.setText(R.id.tv_give_price_item_state, "出局");
                holder.setTextColor(R.id.tv_give_price_item_name, Color.parseColor("#999999"));
                holder.setTextColor(R.id.tv_give_price_item_state, Color.parseColor("#999999"));
                holder.setTextColor(R.id.tv_give_price_item_address, Color.parseColor("#999999"));
                holder.setTextColor(R.id.tv_give_price_item_price, Color.parseColor("#999999"));
            }
        }
    }
}
