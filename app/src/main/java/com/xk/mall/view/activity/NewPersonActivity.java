package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.entity.NewPersonBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.WuGConfigBean;
import com.xk.mall.model.entity.WuGOrderMoneyBean;
import com.xk.mall.model.impl.NewPersonViewImpl;
import com.xk.mall.model.impl.WuGMainImpl;
import com.xk.mall.presenter.NewPersonPresenter;
import com.xk.mall.presenter.WuGMainPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.GridSpacingItemDecoration;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareType;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 新人专区
 */
public class NewPersonActivity extends BaseActivity<WuGMainPresenter> implements WuGMainImpl {

    @BindView(R.id.iv_new_person_head)
    ImageView ivHead;//头部图片
    @BindView(R.id.rv_new_person)
    RecyclerView rvNewProduct;//布局
    @BindView(R.id.btn_vip)
    Button btnNewPerson;//即刻领取会员权益
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    int page = 1;
    private String activityId;//活动ID
    private String sectionId;//版块ID
    List<ActiveSectionGoodsBean> data = new ArrayList<>();
    private NewProductAdapter newProductAdapter;


    @Override
    protected WuGMainPresenter createPresenter() {
        return new WuGMainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_person;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("新人专区");
        toolbar_title.setTextColor(Color.parseColor("#444444"));
        setRightDrawable(R.drawable.ic_activity_share);
        setOnRightIconClickListener(v -> {
            getShareContent();
        });
    }

    @Override
    protected void initData() {
        mPresenter.getActiveSectionData(ActivityType.ACTIVITY_NEW_PERSON);

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getActiveSectionData(ActivityType.ACTIVITY_NEW_PERSON);
        });

        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            getSectionData(sectionId);
        });

        rvNewProduct.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvNewProduct.addItemDecoration(new GridSpacingItemDecoration(2, 30, true));
        newProductAdapter = new NewProductAdapter(mContext, R.layout.item_new_person, data);
        rvNewProduct.setAdapter(newProductAdapter);
        newProductAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(mContext, NewPersonGoodsDetailActivity.class);
                intent.putExtra(NewPersonGoodsDetailActivity.ACTIVITY_GOODS_ID, data.get(position).getActivityGoodsId());
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        btnNewPerson.setOnClickListener(v -> ActivityUtils.startActivity(LoginActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isLogin = SPUtils.getInstance().getBoolean(Constant.IS_LOGIN);
        if(isLogin){
            btnNewPerson.setVisibility(View.GONE);
        }
    }

    /**
     * 获取分享内容
     */
    private void getShareContent(){
        if(!TextUtils.isEmpty(activityId)){
            mPresenter.getShareContent(MyApplication.userId, activityId, ShareType.ACTIVITY_NEW_PERSON);
        }
    }

    /**
     * 获取版块成功
     */
    @Override
    public void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model) {
        smartRefreshLayout.finishRefresh();
        if(model.getData() != null){
            bindBanner(model.getData().getBannerList());
            bindTitle(model.getData().getSectionList());
        }
    }

    /***
     * 绑定版块信息
     */
    private void bindTitle(List<ActiveSectionBean.SectionListBean> sectionList) {
        if(sectionList != null && sectionList.size() != 0){
            sectionId = sectionList.get(0).getId();
            getSectionData(sectionId);
        }
    }

    /**
     * 根据版块ID获取版块数据
     */
    private void getSectionData(String sectionId){
        if(null != sectionId && !TextUtils.isEmpty(sectionId)){
            mPresenter.getActiveSectionGoods(sectionId, ActivityType.ACTIVITY_NEW_PERSON, MyApplication.userId, page, Constant.limit);
        }
    }

    /**
     * 绑定banner
     */
    private void bindBanner(List<BannerBean> bannerList) {
        if(bannerList == null || bannerList.size() == 0){
            ivHead.setVisibility(View.GONE);
        }else {
            ivHead.setVisibility(View.VISIBLE);
            ivHead.setImageResource(R.drawable.ic_loading);
            //获取图片真正的宽高
            Glide.with(mContext).asBitmap().load(bannerList.get(0).getImageUrl()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    float bitWidth = bitmap.getWidth();
                    float bitHeight = bitmap.getHeight();
                    float ratio = bitWidth / bitHeight;//真实图片宽高比
                    // 获得屏幕宽高
                    DisplayMetrics displayMetrics = DensityUtils.getDisplayMetrics(mContext);
                    int screenWidth = displayMetrics.widthPixels;
                    float height=(screenWidth/ratio);
//                    Logger.e(position + ",img宽：" + screenWidth + ",img高:" + height);
                    //动态设置ImageView 高度
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ivHead.getLayoutParams();
                    layoutParams.width = screenWidth;
                    layoutParams.height = Float.valueOf(height).intValue();
                    // ImageView 控件设置
                    ivHead.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ivHead.setAdjustViewBounds(true);
                    ivHead.setLayoutParams(layoutParams);
                    ivHead.setImageBitmap(bitmap);
                }
            });
        }
    }

    /**
     * 获取版块中商品成功
     */
    @Override
    public void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
        if(model.getData() != null && model.getData().getResult() != null && model.getData().getResult().size() != 0){
            //在有商品时允许向上滑动。
            AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) ivHead.getLayoutParams();
            mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
            activityId = model.getData().getResult().get(0).getActivityId();
            if(page == 1){
                data.clear();
            }
            data.addAll(model.getData().getResult());
            newProductAdapter.notifyDataSetChanged();
            if(model.getData().getResult().size() < Constant.limit){
                smartRefreshLayout.setEnableLoadMore(false);
            }else {
                smartRefreshLayout.setEnableLoadMore(true);
            }
        }else {
            //在没有商品时不允许向上滑动。
            if(data.size() == 0){
                AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) ivHead.getLayoutParams();
                mParams.setScrollFlags(0);
            }

            smartRefreshLayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void onGetScheduleConfigSuccess(BaseModel<WuGConfigBean> model) {

    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {

    }

    @Override
    public void onShareCallback(BaseModel model) {

    }

    @Override
    public void onGetMoneySuccess(BaseModel<WuGOrderMoneyBean> moneyBeanBaseModel) {

    }

    class NewProductAdapter extends CommonAdapter<ActiveSectionGoodsBean> {

        public NewProductAdapter(Context context, int layoutId, List<ActiveSectionGoodsBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ActiveSectionGoodsBean newPersonBean, int position) {
            ImageView imgView = holder.getView(R.id.img_wug_goods);
            holder.setText(R.id.tv_goodsName, newPersonBean.getCommodityName());
            holder.setText(R.id.tv_sale_price, PriceUtil.dividePrice(newPersonBean.getCommodityPrice()));
            TextView tvLinePrice = holder.getView(R.id.tv_line_price);
            holder.setText(R.id.tv_line_price, getResources().getString(R.string.money) + PriceUtil.dividePrice(newPersonBean.getSalePrice()));
            tvLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            GlideUtil.showRadius(mContext, newPersonBean.getGoodsImageUrl(), 2, imgView);

            //设置图片为正方形
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imgView.getLayoutParams();
            int deviceWidth = DensityUtils.getDeviceWidth(mContext);
            layoutParams.height = (deviceWidth - DensityUtils.dp2px(mContext, 30)) / 2;
            imgView.setLayoutParams(layoutParams);
            if(newPersonBean.getStock() <= 0){
                holder.setVisible(R.id.iv_goods_empty, true);
            }else {
                holder.setVisible(R.id.iv_goods_empty, false);
            }
        }
    }
}
