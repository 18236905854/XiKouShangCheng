package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.NearAddressBean;
import com.xk.mall.model.impl.NearSearchViewImpl;
import com.xk.mall.presenter.NearSearchPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.view.widget.ClearEditText;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName NearSearchResultActivity
 * Description 附近店铺搜索结果页面
 * Author 卿凯
 * Date 2019/7/23/023
 * Version V1.0
 */
public class NearSearchResultActivity extends BaseActivity<NearSearchPresenter> implements NearSearchViewImpl {
    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.state_view_order)
    MultiStateView stateView;
    private String shopName = "";//需要搜索的店铺名称
    /**intent传递过来的店铺名称的key*/
    public static String SHOP_NAME = "shop_name";
    private MultiItemTypeAdapter multiItemTypeAdapter;
    private List<NearAddressBean.ResultBean> listData = new ArrayList<>();
    private int page = 1;//当前页数
    private int limit = 10;//每页显示的条数
    private double longitude;
    private double latitude;

    @Override
    protected NearSearchPresenter createPresenter() {
        return new NearSearchPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_search_result;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @Override
    protected void initData() {
        initStateView();
        shopName = getIntent().getStringExtra(SHOP_NAME);
        longitude = getIntent().getDoubleExtra(Constant.INTENT_LONGITUDE, 0);
        latitude = getIntent().getDoubleExtra(Constant.INTENT_LATITUDE, 0);
        etSearch.setText(shopName);
        MyApplication.getInstance().addActivity(NearSearchResultActivity.this);
        //进入退出效果 注意这里 创建的效果对象是 Fade()
        getWindow().setEnterTransition(new Fade().setDuration(200));
        getWindow().setExitTransition(new Fade().setDuration(200));
        mPresenter.getSearchResult(shopName,longitude, latitude, page, limit);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        multiItemTypeAdapter = new MultiItemTypeAdapter(mContext, listData);
        multiItemTypeAdapter.addItemViewDelegate(new LargeImgItemDelegate());
        multiItemTypeAdapter.addItemViewDelegate(new SampleImgItemDelegate());
        recyclerView.setAdapter(multiItemTypeAdapter);
        multiItemTypeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent toDetail=new Intent(mContext,ShopActivity.class);
                toDetail.putExtra(ShopActivity.SHOP_ID,listData.get(position).getId());
                toDetail.putExtra(ShopActivity.MERCHANT_ID,listData.get(position).getMerchantId());
                toDetail.putExtra(ShopActivity.SHOP_DISTANCE, listData.get(position).getDistance());
                //点击跳转页面
                ActivityUtils.startActivity(toDetail);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(etSearch.getText().toString().trim())) {
                mPresenter.getSearchResult(shopName,longitude, latitude, page, limit);
            }
            return false;
        });
    }

    private void initStateView(){
       ImageView imageView= stateView.findViewById(R.id.iv_empty_order);
       TextView textView=stateView.findViewById(R.id.tv_empty_text);
       imageView.setImageResource(R.mipmap.ic_no_search);
       textView.setText("没有找到相关店铺");
    }

    @OnClick({R.id.tv_finish})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_finish) {
            if (Build.VERSION.SDK_INT >= 21) {
                MyApplication.getInstance().closeActivity();
                finishAfterTransition();
            } else {
                MyApplication.getInstance().closeActivity();
                finish();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishAfterTransition();
            MyApplication.getInstance().closeActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onGetListSuccess(BaseModel<NearAddressBean> model) {
        String searchName = SPUtils.getInstance().getString(Constant.SHOP_SEARCH_HISTORY, "");
        if(TextUtils.isEmpty(searchName)){
            SPUtils.getInstance().put(Constant.SHOP_SEARCH_HISTORY, shopName);
        }else {
            searchName = searchName + "--" + shopName;
            SPUtils.getInstance().put(Constant.SHOP_SEARCH_HISTORY, searchName);
        }
        if (page == 1) {
            listData.clear();
            if (model.getData() == null || model.getData().getResult() == null || model.getData().getResult().size() == 0) {
                //显示空布局
                stateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            } else {
                listData.addAll(model.getData().getResult());
            }
        } else {
            listData.addAll(model.getData().getResult());
        }

        if (model.getData() != null && model.getData().getResult() != null &&
                model.getData().getResult().size() < limit) {//当返回的数据小于请求的条数时，说明没有更多数据了
            refreshLayout.setEnableLoadMore(false);
        }else {
            refreshLayout.setEnableLoadMore(true);
        }

        multiItemTypeAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh(1500);
    }

    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        //显示空布局
        stateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    public class LargeImgItemDelegate implements ItemViewDelegate<NearAddressBean.ResultBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.near_item_one;
        }

        @Override
        public boolean isForViewType(NearAddressBean.ResultBean item, int position) {
            return item.getStyle() == 0;
        }

        @Override
        public void convert(ViewHolder holder, NearAddressBean.ResultBean nearBean, int position) {
            holder.setText(R.id.tv_near_item_title, nearBean.getShopName());
            holder.setText(R.id.tv_near_item_discount, "优惠券买单最高抵扣" + nearBean.getDiscountRate() + "%");
            holder.setText(R.id.tv_near_item_distance, "小于" + nearBean.getDistance() + "km");

            ImageView ivLarge = holder.getView(R.id.iv_near_item_large);
            ImageView ivTop = holder.getView(R.id.iv_near_item_top);
            ImageView ivDown = holder.getView(R.id.iv_near_item_down);
            int size=nearBean.getImageList().size();
            if(size>=3){
                GlideUtil.showRadius(mContext, nearBean.getImageList().get(0).getImageUrl(), 2, ivLarge);
                GlideUtil.showRadius(mContext, nearBean.getImageList().get(1).getImageUrl(), 2, ivTop);
                GlideUtil.showRadius(mContext, nearBean.getImageList().get(2).getImageUrl(), 2, ivDown);
            }else if(size>=2){
                GlideUtil.showRadius(mContext, nearBean.getImageList().get(0).getImageUrl(), 2, ivLarge);
                GlideUtil.showRadius(mContext, nearBean.getImageList().get(1).getImageUrl(), 2, ivTop);
            }
        }
    }

    public class SampleImgItemDelegate implements ItemViewDelegate<NearAddressBean.ResultBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.near_item_two;
        }

        @Override
        public boolean isForViewType(NearAddressBean.ResultBean item, int position) {
            return item.getStyle() == 1;
        }

        @Override
        public void convert(ViewHolder holder, NearAddressBean.ResultBean nearBean, int position) {
            holder.setText(R.id.tv_near_item_title, nearBean.getShopName());
            holder.setText(R.id.tv_near_item_discount, "优惠券买单最高抵扣" + nearBean.getDiscountRate() + "%");
            holder.setText(R.id.tv_near_item_distance, "小于" + nearBean.getDistance() + "km");

            ImageView itemOne = holder.getView(R.id.iv_near_item_one);
            ImageView itemTwo = holder.getView(R.id.iv_near_item_two);
            ImageView itemThree = holder.getView(R.id.iv_near_item_three);

            int size=nearBean.getImageList().size();
            if(size>=3){
                GlideUtil.showRadius(mContext, nearBean.getImageList().get(0).getImageUrl(), 2, itemOne);
                GlideUtil.showRadius(mContext, nearBean.getImageList().get(1).getImageUrl(), 2, itemTwo);
                GlideUtil.showRadius(mContext, nearBean.getImageList().get(2).getImageUrl(), 2, itemThree);
            }else if(size>=2){
                GlideUtil.showRadius(mContext, nearBean.getImageList().get(0).getImageUrl(), 2, itemOne);
                GlideUtil.showRadius(mContext, nearBean.getImageList().get(1).getImageUrl(), 2, itemTwo);
            }

//            List<NearAddressBean.ResultBean.ImageListBean> imageList = nearBean.getImageList();
//            List<String> listImages = new ArrayList<>();
//            for (int i = 0; i > imageList.size(); i++) {
//                if (imageList.get(i).getType() == 3) {//店铺展示页面
//                    listImages.add(imageList.get(i).getImageUrl());
//                }
//            }
//
//            for (int j = 0; j < listImages.size(); j++) {
//                if (listImages.size() >= 3) {
//                    if (j == 0) {
//                        GlideUtil.showRadius(mContext, listImages.get(j), 2, itemOne);
//                    } else if (j == 1) {
//                        GlideUtil.showRadius(mContext, listImages.get(j), 2, itemTwo);
//                    } else if (j == 2) {
//                        GlideUtil.showRadius(mContext, listImages.get(j), 2, itemThree);
//                    }
//                }
//            }

        }
    }

}
