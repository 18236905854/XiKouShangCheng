package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CutListBean;
import com.xk.mall.utils.OrderType;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName CutContinueActivity
 * Description 砍价成功页面
 * Author 卿凯
 * Date 2019/6/28/028
 * Version V1.0
 */
public class CutSuccessActivity extends BaseActivity {
    @BindView(R.id.iv_cut_recommend_logo)
    ImageView ivCutRecommendLogo;//图标
    @BindView(R.id.tv_cut_name)
    TextView tvCutName;//商品名
    @BindView(R.id.tv_cut_man)
    TextView tvCutMan;//8人助力得底价
    @BindView(R.id.tv_cut_now_price)
    TextView tvCutNowPrice;//当前价格
    @BindView(R.id.tv_cut_real_price)
    TextView tvCutRealPrice;//真实价格
    @BindView(R.id.pb_fight_group)
    ProgressBar pbFightGroup;//进度条
    @BindView(R.id.tv_cut_continue_cut_price)
    TextView tvCutPrice;//当前砍价金额
    @BindView(R.id.tv_cut_continue_share)
    TextView tvShare;//去支付
    @BindView(R.id.rv_cut_continue)
    RecyclerView rvCutContinue;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cut_success;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("砍价成功");
    }

    @Override
    protected void initData() {
        ivCutRecommendLogo.setImageResource(R.drawable.ic_activity_global_item_one);

        rvCutContinue.setLayoutManager(new LinearLayoutManager(mContext));
        List<CutListBean> cutListBeans = new ArrayList<>();
        cutListBeans.add(new CutListBean("", "刘江", "12分钟前", 135.0));
        cutListBeans.add(new CutListBean("", "刘江", "13分钟前", 135.0));
        cutListBeans.add(new CutListBean("", "刘江", "15分钟前", 135.0));
        cutListBeans.add(new CutListBean("", "刘江", "20分钟前", 135.0));
        CutListAdapter cutListAdapter = new CutListAdapter(mContext, R.layout.item_cut_list, cutListBeans);
        rvCutContinue.setAdapter(cutListAdapter);
    }

    /**
     * 砍价榜的Adapter
     */
    class CutListAdapter extends CommonAdapter<CutListBean>{

        public CutListAdapter(Context context, int layoutId, List<CutListBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, CutListBean cutListBean, int position) {

        }
    }

    @OnClick({R.id.tv_cut_continue_share})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.tv_cut_continue_share:
                //跳转支付页面
                goPay();
                break;

        }
    }

    /**
     * 跳转支付页面
     */
    private void goPay(){
        Intent intent = new Intent(mContext, PayOrderActivity.class);
        intent.putExtra(PayOrderActivity.GOODS_NAME,tvCutName.getText().toString());
        intent.putExtra(PayOrderActivity.TOTAL_PRICE,tvCutNowPrice.getText().toString());
        intent.putExtra(PayOrderActivity.ORDER_NUMBER,tvCutNowPrice.getText().toString());
        intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.CUT_TYPE);
        intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
        ActivityUtils.startActivity(intent);
    }

}
