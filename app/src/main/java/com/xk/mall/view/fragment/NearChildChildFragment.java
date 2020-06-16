package com.xk.mall.view.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.LocationBean;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.model.entity.NearAddressBean;
import com.xk.mall.model.eventbean.LocationPointBean;
import com.xk.mall.model.impl.NearChildViewImpl;
import com.xk.mall.presenter.NearChildPresenter;
import com.xk.mall.promptdialog.PromptDialog;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.view.activity.ShopActivity;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * ClassName NearFragment
 * Description 附近页面
 * Author 卿凯
 * Date 2019/6/15/015
 * Version V1.0
 */
public class NearChildChildFragment extends BaseFragment<NearChildPresenter> implements NearChildViewImpl {
    private static final String TAG = "NearChildChildFragment";
    public static final String LOCATION_KEY = "location";
    @BindView(R.id.rv_near)
    RecyclerView recyclerView;
    @BindView(R.id.banner_near_child)
    Banner nearBanner;
    @BindView(R.id.iv_rate)
    ImageView ivRate;
    @BindView(R.id.ll_rate)
    LinearLayout llRate;
    @BindView(R.id.iv_pop)
    ImageView ivPop;
    @BindView(R.id.ll_pop)
    LinearLayout llPop;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ll_middle_content)
    LinearLayout llMiddleContent;
    private int dataId = 0;//用来请求数据的行业ID
    private double longitude, latitude;//经纬度
    private List<NearAddressBean.ResultBean> listData = new ArrayList<>();
    private MultiItemTypeAdapter multiItemTypeAdapter;

    private int rate = 0;//折扣最优排序;1:升序；2：降序
    private int pop = 0;//人气最旺排序;1:升序；2：降序
    private int page = 1;
    private int limit = Constant.limit;
    private PromptDialog promptDialog;
    private List<String> banners;

    public static NearChildChildFragment getInstance(int id, double longitude, double latitude, List<String> banner) {
        NearChildChildFragment fragment = new NearChildChildFragment();
        fragment.dataId = id;
        fragment.longitude = longitude;
        fragment.latitude = latitude;
        fragment.banners = banner;
        return fragment;
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    protected NearChildPresenter createPresenter() {
        return new NearChildPresenter(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_child_near;
    }

    @Override
    protected void initData() {

    }

    /**
     * eventBus接收回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void switchCity(LocationPointBean bean) {
        Logger.e("接收切换城市");
        Log.e(TAG, "switchCity: "+bean.getLongitude() );
        Log.e(TAG, "switchCity: "+bean.getLatitude() );
        longitude=bean.getLongitude();
        latitude=bean.getLatitude();
        if(isVisibleToUser){
            requestNearShopData();
        }
    }

    /**
     * 进度款消失
     */
    public void closeLoadingDialog() {
        if (promptDialog != null) {
            promptDialog.dismiss();
        }
    }
    /**
     * 加载中...
     */
    public void showLoadingDialog() {
        if (promptDialog == null) {
            promptDialog = new PromptDialog(getActivity());
        }
        promptDialog.showLoading("加载中...",true);
    }

    @Override
    protected void loadLazyData() {
        Log.e(TAG, "onFirstVisibleInitData: 经度" + longitude);
        Log.e(TAG, "onFirstVisibleInitData: 纬度" + latitude);
        MyApplication.longitude = longitude;
        MyApplication.latitude = latitude;
        rate = 0;
        pop = 0;
        page = 1;
        requestNearShopData();
        bindBanner();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        multiItemTypeAdapter = new MultiItemTypeAdapter(mContext, listData);
        multiItemTypeAdapter.addItemViewDelegate(new LargeImgItemDelegate());
        multiItemTypeAdapter.addItemViewDelegate(new SampleImgItemDelegate());
        recyclerView.setAdapter(multiItemTypeAdapter);

        mRefreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            listData.clear();
            page = 1;
            requestNearShopData();
        });

        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page = page + 1;
            requestNearShopData();
        });

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
    }

    /**
     * 设置banner的数据
     */
    private void bindBanner() {
        if(banners != null && banners.size() != 0){
            Log.e(TAG, "onFragmentFirstVisible: 第一次可见");
            //设置图片加载器
            nearBanner.setImageLoader(new GlideImageLoader());
            nearBanner.setImages(banners);
            //设置nearBanner动画效果
            nearBanner.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            nearBanner.isAutoPlay(true);
            //设置轮播时间
            nearBanner.setDelayTime(3000);
            //banner设置方法全部调用完毕时最后调用
            nearBanner.start();
        }else {
            nearBanner.setVisibility(View.GONE);
        }
    }


    /**
     * 请求附近店铺数据
     */
    private void requestNearShopData() {
        showLoadingDialog();
        if(dataId == 0){
            mPresenter.getNearList(0, rate, pop, longitude, latitude, page, limit);
        }else {

            mPresenter.getNearListByType(0, rate, pop, dataId, longitude, latitude, page, limit);
        }
    }

    /**
     * 获取商铺列表成功的回调
     */
    @Override
    public void onShopListSuccess(BaseModel<NearAddressBean> entity, int type) {
        Log.e(TAG, "onShopListSuccess:---- " );
        closeLoadingDialog();
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
        llMiddleContent.setVisibility(View.VISIBLE);
        if (page == 1) {
            listData.clear();
            if (entity.getData() == null || entity.getData().getResult() == null || entity.getData().getResult().size() == 0) {
                //显示空布局
                mRefreshLayout.setEnableLoadMore(false);
            } else {
                listData.addAll(entity.getData().getResult());
            }
        } else {
            if(entity.getData() != null && entity.getData().getResult() != null){
                listData.addAll(entity.getData().getResult());
            }
        }

        if (entity.getData() == null || entity.getData().getResult() == null || entity.getData().getResult().size() < limit) {//当返回的数据小于请求的条数时，说明没有更多数据了
            mRefreshLayout.setEnableLoadMore(false);
        }else {
            mRefreshLayout.setEnableLoadMore(true);
        }
//        //分配布局
//        //index+1 求2的余数
//        for (int i=0;i<listData.size();i++){
//            int index= i+1;
//            if(index % 2==1){//布局图 0
//                listData.get(i).setViewType(0);
//            }else {//1  3张小图
//                listData.get(i).setViewType(1);
//            }
//        }

        multiItemTypeAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh(1000);
        mRefreshLayout.finishLoadMore();

    }

    @OnClick({R.id.ll_rate, R.id.ll_pop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_rate://折扣最优
                page = 1;
                pop = 0;
                if (rate == 1) {
                    rate = 2;
                    ivRate.setImageResource(R.drawable.near_order_up);
                } else {
                    rate = 1;
                    ivRate.setImageResource(R.drawable.near_order_down);
                }
                requestNearShopData();
                break;
            case R.id.ll_pop://人气
                page = 1;
                rate = 0;
                if (pop == 1) {
                    pop = 2;
                    ivPop.setImageResource(R.drawable.near_order_up);
                } else {
                    pop = 1;
                    ivPop.setImageResource(R.drawable.near_order_down);
                }
                requestNearShopData();
                break;
        }
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
            holder.setText(R.id.tv_near_item_discount, "优惠券直减" + nearBean.getDiscountRate() + "%");
            if(!TextUtils.isEmpty(nearBean.getDistance())){
                holder.setText(R.id.tv_near_item_distance, "小于" + nearBean.getDistance() + "km");
            }else{
                holder.setText(R.id.tv_near_item_distance, "小于0km");
            }
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
            holder.setText(R.id.tv_near_item_discount, "优惠券直减" + nearBean.getDiscountRate() + "%");
            if(!TextUtils.isEmpty(nearBean.getDistance())){
                holder.setText(R.id.tv_near_item_distance, "小于" + nearBean.getDistance() + "km");
            }else{
                holder.setText(R.id.tv_near_item_distance, "小于0km");
            }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode,
                permissions, grantResults, mContext);
    }

}
