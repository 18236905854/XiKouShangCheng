package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Fade;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.HomeSearchGoodsBean;
import com.xk.mall.model.entity.HomeSearchGoodsPageBean;
import com.xk.mall.model.impl.HomeSearchViewImpl;
import com.xk.mall.presenter.HomeSearchPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GridSpacingItemDecoration;
import com.xk.mall.view.adapter.SearchGoodsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName HomeSearchActivity
 * Description 首页搜索结果页
 * Author
 * Date
 * Version V1.0
 */
public class SearchResultActivity extends BaseActivity<HomeSearchPresenter> implements HomeSearchViewImpl {
    public static final String SEARCH_NAME = "search_name";
    @BindView(R.id.et_search)
    EditText editSearch;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.ll_zonghe)
    LinearLayout llZonghe;
    @BindView(R.id.tv_home_search_comprehensive)
    TextView tvComprehensive;//综合
    @BindView(R.id.iv_price)
    ImageView ivPrice;
    @BindView(R.id.tv_home_search_price)
    TextView tvPrice;//价格
    @BindView(R.id.ll_price)
    LinearLayout llPrice;
    @BindView(R.id.iv_sale)
    ImageView ivSale;
    @BindView(R.id.tv_home_search_sale)
    TextView tvSale;//销量
    @BindView(R.id.ll_sale)
    LinearLayout llSale;
    @BindView(R.id.iv_new)
    ImageView ivNew;
    @BindView(R.id.tv_home_search_new)
    TextView tvNew;//上新
    @BindView(R.id.ll_news)
    LinearLayout llNews;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.state_home_search)
    MultiStateView multiStateView;
    List<HomeSearchGoodsBean> listData;
    String commodityName;
    int salesNumFlag = 0;
    int priceFlag = 0;
    int newFlag = 0;
    int page = 1;
    int limit = 10;
    private boolean isSave = false;
    @BindView(R.id.iv_empty_order)
    ImageView ivEmpty;
    private SearchGoodsAdapter searchGoodsAdapter;


    @Override
    protected HomeSearchPresenter createPresenter() {
        return new HomeSearchPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_search_result;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        commodityName = getIntent().getStringExtra(SEARCH_NAME);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @Override
    protected void initData() {
        editSearch.setText(commodityName);
        ivEmpty.setImageResource(R.drawable.ic_empty_search);
        MyApplication.getInstance().addActivity(SearchResultActivity.this);
        //进入退出效果 注意这里 创建的效果对象是 Fade()
        getWindow().setEnterTransition(new Fade().setDuration(200));
        getWindow().setExitTransition(new Fade().setDuration(200));
        listData = new ArrayList<>();
        changeIv(0);
        mPresenter.homeSearch(commodityName, salesNumFlag, priceFlag, newFlag, page, limit);
        searchGoodsAdapter = new SearchGoodsAdapter(this, listData, (id, position) -> clickItem(listData.get(position)));
        recycleView.setLayoutManager(new GridLayoutManager(mContext,2));
        recycleView.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtils.dp2px(mContext,15), false));
        recycleView.setAdapter(searchGoodsAdapter);
        editSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(editSearch.getText().toString().trim())) {
                page = 1;
                salesNumFlag = 1;
                priceFlag = 1;
                newFlag = 1;
                commodityName = editSearch.getText().toString().trim();
                isSave = false;
                refreshLayout.setEnableLoadMore(true);
                changeIv(0);
                mPresenter.homeSearch(commodityName, salesNumFlag, priceFlag, newFlag, page, limit);
            }
            return false;
        });

        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.homeSearch(commodityName, salesNumFlag, priceFlag, newFlag, page, limit);
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.homeSearch(commodityName, salesNumFlag, priceFlag, newFlag, page, limit);
        });

    }

    private void clickItem(HomeSearchGoodsBean homeSearchGoodsBean){
        //活动类型(1:买一赠二(吾G)，2: 全球买手, 3：0元竞拍 4:多买多折，5:砍价得红包，6:定制拼团)
        if(homeSearchGoodsBean.getActivityType() == ActivityType.ACTIVITY_WUG){
            Intent intent = new Intent(mContext, WuGGoodsDetailActivity.class);
            intent.putExtra(WuGGoodsDetailActivity.ACTIVITY_GOODS_ID, homeSearchGoodsBean.getId());
            ActivityUtils.startActivity(intent);
        }else if(homeSearchGoodsBean.getActivityType() == ActivityType.ACTIVITY_GLOBAL_BUYER){
            Intent intent = new Intent(mContext, GlobalBuyerGoodsDetailActivity.class);
            intent.putExtra(GlobalBuyerGoodsDetailActivity.ACTIVITY_GOODS_ID, homeSearchGoodsBean.getId());
            ActivityUtils.startActivity(intent);
        }else if(homeSearchGoodsBean.getActivityType() == ActivityType.ACTIVITY_ZERO){
            Intent intent = new Intent(mContext, ZeroGoodsDetailActivity.class);
            intent.putExtra(ZeroGoodsDetailActivity.ACTIVITY_GOODS_ID, homeSearchGoodsBean.getId());
            ActivityUtils.startActivity(intent);
        }else if(homeSearchGoodsBean.getActivityType() == ActivityType.ACTIVITY_MANY_BUY){
            Intent intent = new Intent(mContext, ManyGoodsDetailActivity.class);
            intent.putExtra(ManyGoodsDetailActivity.ACTIVITY_GOODS_ID, homeSearchGoodsBean.getId());
            ActivityUtils.startActivity(intent);
        }else if(homeSearchGoodsBean.getActivityType() == ActivityType.ACTIVITY_CUT){
            Intent intent = new Intent(mContext, CutGoodsDetailActivity.class);
            intent.putExtra(CutGoodsDetailActivity.ACTIVITY_GOODS_ID, homeSearchGoodsBean.getId());
            ActivityUtils.startActivity(intent);
        }else if(homeSearchGoodsBean.getActivityType() == ActivityType.ACTIVITY_FIGHT_GROUP){
            Intent intent = new Intent(mContext, GroupGoodsDetailActivity.class);
            intent.putExtra(GroupGoodsDetailActivity.ACTIVITY_GOODS_ID, homeSearchGoodsBean.getId());
            ActivityUtils.startActivity(intent);
        }else if(homeSearchGoodsBean.getActivityType() == ActivityType.ACTIVITY_NEW_PERSON){
            Intent intent = new Intent(mContext, NewPersonGoodsDetailActivity.class);
            intent.putExtra(GroupGoodsDetailActivity.ACTIVITY_GOODS_ID, homeSearchGoodsBean.getId());
            ActivityUtils.startActivity(intent);
        }
    }

    @OnClick({R.id.tv_finish,R.id.ll_zonghe, R.id.ll_price, R.id.iv_sale, R.id.ll_sale, R.id.ll_news})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:
                if (Build.VERSION.SDK_INT >= 21) {
                    MyApplication.getInstance().closeActivity();
                    finishAfterTransition();
                } else {
                    MyApplication.getInstance().closeActivity();
                    finish();
                }
                break;
            case R.id.ll_zonghe:
                salesNumFlag = 0;
                priceFlag = 0;
                newFlag = 0;
                page = 1;
                changeIv(0);
                mPresenter.homeSearch(commodityName, salesNumFlag, priceFlag, newFlag, page, limit);
                break;
            case R.id.ll_price:
                salesNumFlag = 0;
                priceFlag = priceFlag == 1 ? 2 : 1;
                newFlag = 0;
                page = 1;
                changeIv(1);
                mPresenter.homeSearch(commodityName, salesNumFlag, priceFlag, newFlag, page, limit);
                break;
            case R.id.ll_sale:
                salesNumFlag = salesNumFlag == 1 ? 2 : 1;
                priceFlag = 0;
                newFlag = 0;
                changeIv(2);
                mPresenter.homeSearch(commodityName, salesNumFlag, priceFlag, newFlag, page, limit);
                break;
            case R.id.ll_news:
                salesNumFlag = 0;
                priceFlag = 0;
                newFlag = newFlag == 1 ? 2 : 1;
                page = 1;
                changeIv(3);
                mPresenter.homeSearch(commodityName, salesNumFlag, priceFlag, newFlag, page, limit);
                break;
        }
    }

    /**
     * 根据位置修改图片
     */
    private void changeIv(int position){
        if(position == 0){
            tvComprehensive.setSelected(true);
            tvPrice.setSelected(false);
            tvNew.setSelected(false);
            tvSale.setSelected(false);
            ivNew.setImageResource(R.drawable.near_order_default);
            ivSale.setImageResource(R.drawable.near_order_default);
            ivPrice.setImageResource(R.drawable.near_order_default);
        }else if(position == 1){
            tvComprehensive.setSelected(false);
            tvPrice.setSelected(true);
            tvNew.setSelected(false);
            tvSale.setSelected(false);
            if(priceFlag == 1){
                ivPrice.setImageResource(R.drawable.near_order_down);
            }else {
                ivPrice.setImageResource(R.drawable.near_order_up);
            }
            ivNew.setImageResource(R.drawable.near_order_default);
            ivSale.setImageResource(R.drawable.near_order_default);
        }else if(position == 2){
            tvComprehensive.setSelected(false);
            tvPrice.setSelected(false);
            tvNew.setSelected(false);
            tvSale.setSelected(true);
            if(salesNumFlag == 1){
                ivSale.setImageResource(R.drawable.near_order_down);
            }else {
                ivSale.setImageResource(R.drawable.near_order_up);
            }
            ivNew.setImageResource(R.drawable.near_order_default);
            ivPrice.setImageResource(R.drawable.near_order_default);
        }else if(position == 3){
            tvComprehensive.setSelected(false);
            tvPrice.setSelected(false);
            tvNew.setSelected(true);
            tvSale.setSelected(false);
            if(priceFlag == 1){
                ivNew.setImageResource(R.drawable.near_order_down);
            }else {
                ivNew.setImageResource(R.drawable.near_order_up);
            }
            ivSale.setImageResource(R.drawable.near_order_default);
            ivPrice.setImageResource(R.drawable.near_order_default);
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
    public void onGetSearchResult(BaseModel<HomeSearchGoodsPageBean> model) {
        if(!isSave){
            commodityName = editSearch.getText().toString().trim();
            String searchName = SPUtils.getInstance().getString(Constant.HOME_SEARCH_HISTORY, "");
            if(TextUtils.isEmpty(searchName)){
                SPUtils.getInstance().put(Constant.HOME_SEARCH_HISTORY, commodityName);
            }else {
                StringBuilder buffer = new StringBuilder();
                int pos = -1;
                if(searchName.contains("--")){
                    String[] newName = searchName.split("--");
                    List<String> newList = Arrays.asList(newName);
                    List<String> stringList = new ArrayList<>(newList);
                    for(int i = 0; i < newList.size(); i++){
                        String name = newList.get(i);
                        if(name.equals(commodityName)){
                            pos = i;
                        }
                    }
                    if(pos != -1){
                        stringList.remove(pos);
                    }
                    buffer.append(commodityName);
                    for(String s : stringList){
                        buffer.append("--").append(s);
                    }
                }else {
                    if(!searchName.equals(commodityName)){
                        buffer.append(commodityName).append("--").append(searchName);
                    }
                }
                searchName = buffer.toString();
                if(!TextUtils.isEmpty(searchName)){
                    SPUtils.getInstance().put(Constant.HOME_SEARCH_HISTORY, searchName);
                }
            }
            isSave = true;
        }
        if(model != null && model.getData() != null && model.getData().getResult() != null){
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            if(page == 1){
                listData.clear();
            }
            listData.addAll(model.getData().getResult());
            searchGoodsAdapter.notifyDataSetChanged();
            if(page == 1 && listData.size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
            if(model.getData().getResult().size() < limit){
                refreshLayout.setEnableLoadMore(false);
            }else {
                refreshLayout.setEnableLoadMore(true);
            }
        }else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }
}
