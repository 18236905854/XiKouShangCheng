package com.xk.mall.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseModel;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.gen.AddressServerBeanDao;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.entity.ShopBean;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.ShopViewImpl;
import com.xk.mall.model.request.AttentionRequestBody;
import com.xk.mall.presenter.ShopPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.IndustryUtil;
import com.xk.mall.utils.ShareType;
import com.xk.mall.view.widget.DialogShare;
import com.xk.mall.view.widget.MyRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jaydenxiao.com.expandabletextview.ExpandableTextView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * ClassName ShopActivity
 * Description 附近店铺页面
 * Author 卿凯
 * Date 2019/6/18/018
 * Version V1.0
 */
public class ShopActivity extends BaseActivity<ShopPresenter> implements ShopViewImpl, EasyPermissions.PermissionCallbacks {
    private static final int RC_CALL_PHONE = 125;
    @BindView(R.id.scroll_shop)
    NestedScrollView scrollShop;//滚动页面
    @BindView(R.id.iv_shop_qr)
    ImageView ivQRCode;//二维码
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;//店铺名称
    @BindView(R.id.iv_shop_logo)
    ImageView ivLogo;//店铺logo
    @BindView(R.id.tv_shop_type)
    TextView tvType;//美食餐饮
    @BindView(R.id.tv_shop_area)
    TextView tvArea;//岳麓区
    @BindView(R.id.tv_shop_distance)
    TextView tvDistance;//9.12km
    @BindView(R.id.tv_shop_time)
    TextView tvOpenTime;//营业时间
    @BindView(R.id.tv_shop_address)
    TextView tvAddress;//商铺地址
    @BindView(R.id.tv_shop_fans)
    TextView tvFans;//粉丝数
    @BindView(R.id.tv_shop_popularity)
    TextView tvPopularity;//人气数
    @BindView(R.id.rv_shop_service)
    RecyclerView rvService;//特色服务
    @BindView(R.id.btn_shop_add_attention)
    Button btnAddAttention;//关注按钮
    @BindView(R.id.tv_shop_about)
    ExpandableTextView tvAbout;//商家介绍
    @BindView(R.id.rv_shop_img)
    MyRecyclerView rvShopImg;//商家图片
    @BindView(R.id.tv_shop_phone)
    TextView tvPhone;//联系商家
    @BindView(R.id.btn_shop_pay)
    Button btnShopPay;//买单按钮
    int scrollHeight = 375;//滚动的距离
    @BindView(R.id.rl_shop_about)
    RelativeLayout rlShopAbout;
    private boolean isAttention;//是否关注
    private String shopId;//店铺id
    private String shopDistance;//店铺距离
    private String shopMobile;//电话
    private String merchantId = "";//商家ID

    List<ShopBean.ServiceListBean> serviceList;
    private ServiceAdapter serviceAdapter;
    List<String> zhanshiImages;
    private ZhanShiImgAdapter zhanShiImgAdapter;
    private int discountRate = 0;//优惠买单比例
    public static String SHOP_ID = "shop_id";//intent传递过的商铺ID的key
    public static String MERCHANT_ID = "merchant_id";//intent传递过的商家ID的key
    public static String SHOP_DISTANCE = "shop_distance";//intent传递过的商铺距离的key

    @Override
    protected ShopPresenter createPresenter() {
        return new ShopPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("");
        setStatusBar(getResources().getColor(R.color.colorMe));
        setDarkStatusIcon(false);
        setOnRightIconClickListener(v -> {
            mPresenter.getShareContent(MyApplication.userId, shopId, ShareType.SHOP_HOME);
        });
    }

    @Override
    protected void initData() {
//        LatLng latLng1=new LatLng(28.209693,112.941831);
//        LatLng latLng2=new LatLng(28.207315,112.886491);
//        float distance = AMapUtils.calculateLineDistance(latLng1,latLng2);
//        Log.e(TAG, "initData:距离 "+distance+"米" );
        //根据开关控制确定按钮
        if(MyApplication.switchBean != null && MyApplication.switchBean.getOto() == 1){
            btnShopPay.setEnabled(true);
            btnShopPay.setBackgroundResource(R.drawable.bg_login_btn);
        }else{
            btnShopPay.setEnabled(false);
            btnShopPay.setBackgroundResource(R.drawable.bg_login_btn_disable);
        }
        rvService.setLayoutManager(new GridLayoutManager(mContext, 4));
        serviceList = new ArrayList<>();
        serviceAdapter = new ServiceAdapter(mContext, R.layout.shop_service_item, serviceList);
        rvService.setAdapter(serviceAdapter);
        zhanshiImages = new ArrayList<>();
        zhanShiImgAdapter = new ZhanShiImgAdapter(mContext, R.layout.shop_img_item, zhanshiImages);

        zhanShiImgAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                GlideUtil.lookBigImage(mContext, zhanshiImages, position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvShopImg.setLayoutManager(new LinearLayoutManager(mContext));
        rvShopImg.setAdapter(zhanShiImgAdapter);

        shopId = getIntent().getStringExtra(SHOP_ID);
        merchantId = getIntent().getStringExtra(MERCHANT_ID);
        shopDistance = getIntent().getStringExtra(SHOP_DISTANCE);

        mPresenter.getShopDetail(shopId, MyApplication.userId);

        scrollShop.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, i, y, i2, i3) -> {
            if (y <= 0) {
                ivLeftBack.setImageResource(R.drawable.ic_back_white);
                ivRight.setImageResource(R.drawable.ic_activity_share_white);
                toolbar.setBackgroundColor(Color.TRANSPARENT);
                setStatusBar(getResources().getColor(R.color.colorMe));
                setDarkStatusIcon(false);
            } else if (y > 0 && y <= scrollHeight) {
                ivLeftBack.setImageResource(R.drawable.ic_back);
                ivRight.setImageResource(R.drawable.ic_activity_share);
                setDarkStatusIcon(true);
                float scale = (float) y / scrollHeight;
                float alpha = (255 * scale);
                // 只是layout背景透明(仿知乎滑动效果)白色透明
                toolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                setStatusBar(Color.argb((int) alpha, 255, 255, 255));
                setTitle("");
            } else {
                setStatusBar(Color.WHITE);
                ivLeftBack.setImageResource(R.drawable.ic_back);
                ivRight.setImageResource(R.drawable.ic_activity_share);
                setTitle(tvShopName.getText().toString());
                setDarkStatusIcon(true);
                toolbar.setBackgroundColor(Color.WHITE);
            }
        });

    }

    @Keep
    @LoginFilter
    @OnClick({R.id.btn_shop_pay, R.id.btn_shop_add_attention})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_shop_pay://买单按钮
                Intent intent = new Intent(mContext, ShopPayActivity.class);
                intent.putExtra(Constant.INTENT_TITLE, tvShopName.getText().toString());
                intent.putExtra(ShopPayActivity.DISCOUNT_RATE, discountRate);
                intent.putExtra(ShopPayActivity.SHOP_ID, shopId);
                intent.putExtra(ShopPayActivity.MERCHANT_ID, merchantId);
                ActivityUtils.startActivity(intent);
                break;

            case R.id.btn_shop_add_attention://添加关注  5d0c940016831b0001ff4e2c
                AttentionRequestBody requestBody = new AttentionRequestBody();
                if (isAttention) {
                    requestBody.setUserId(MyApplication.userId);
                    requestBody.setShopId(shopId);
                    requestBody.setOperationType(Constant.CANCEL);
                    Log.e(TAG, "onClickView: " + requestBody.toString());
                    mPresenter.cancelAttentionShop(requestBody);
                } else {
                    requestBody.setUserId(MyApplication.userId);
                    requestBody.setShopId(shopId);
                    requestBody.setOperationType(Constant.FOLLOW);
                    mPresenter.addAttentionShop(requestBody);
                }

                break;

//            case R.id.tv_shop_phone://联系商家
//                if(!TextUtils.isEmpty(shopMobile)){
//                    startPermissionsCallPhone();
//                }
//                break;
        }
    }

    @OnClick(R.id.tv_shop_phone)
    public void onClick() {
        if (!TextUtils.isEmpty(shopMobile)) {
            startPermissionsCallPhone();
        }
    }

    //获取数据成功回调
    @Override
    public void onGetShopDataSuc(BaseModel<ShopBean> shopBean) {
        ShopBean entity = shopBean.getData();
        bindData(entity);
    }

    /**
     * 绑定数据
     *
     * @param shopBean
     */
    private void bindData(ShopBean shopBean) {
        tvAddress.setText(shopBean.getAddress());
        if(TextUtils.isEmpty(merchantId)){
           merchantId = shopBean.getMerchantId();
        }
        List<ShopBean.ImageListBean> imageList = shopBean.getImageList();
        discountRate = shopBean.getDiscountRate();
        for (int i = 0; i < imageList.size(); i++) {
            if (imageList.get(i).getType() == 1) {//LOGO
                GlideUtil.show(mContext, imageList.get(i).getImageUrl(), ivLogo);
            } else if (imageList.get(i).getType() == 3) {//展示
                zhanshiImages.add(imageList.get(i).getImageUrl());
            }
        }

        zhanShiImgAdapter.notifyDataSetChanged();
        tvShopName.setText(shopBean.getShopName());
        tvType.setText(IndustryUtil.getIndustry(shopBean.getIndustry1()));

//        tvDistance.setText(TextUtils.isEmpty(shopDistance) ? shopBean.getDistance() + "km" : shopDistance + "km");
        if(TextUtils.isEmpty(shopBean.getDistance())){
            tvDistance.setText(shopDistance+"km");
        }else{
            tvDistance.setText(shopBean.getDistance() + "km");
        }
        tvOpenTime.setText(shopBean.getStartTime() + "-" + shopBean.getEndTime());
        tvAddress.setText(shopBean.getAddress());
        tvFans.setText(shopBean.getFanCnt() + "粉丝");
        tvPopularity.setText(shopBean.getPopCnt() + "人气");
        bindServiceData(shopBean.getServiceList());
        tvAbout.setText(shopBean.getDescription());
        if (shopBean.getIsFollow() == 1) {
            isAttention = true;
            btnAddAttention.setText("取消关注");
        } else {
            isAttention = false;
            btnAddAttention.setText("+ 关注");
        }
        btnShopPay.setText(shopBean.getDiscountRate() + "%优惠买单");
        shopMobile = shopBean.getMobile();
        String area = "";
        AddressServerBean addressServerBean = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().queryBuilder()
                .where(AddressServerBeanDao.Properties.AreaId.eq(shopBean.getArea())).build().unique();
        if (addressServerBean == null) {
            area = "";
        } else {
            area = addressServerBean.name;
        }
        //区域
        tvArea.setText(area);
    }

    private void bindServiceData(List<ShopBean.ServiceListBean> serviceList) {
        this.serviceList.addAll(serviceList);
        serviceAdapter.notifyDataSetChanged();
    }

    //关注成功回调
    @Override
    public void onAttentionShopSuc(BaseModel baseModel) {
//        Log.e(TAG, "onAttentionShopSuc: " );
        btnAddAttention.setText("取消关注");
        isAttention = true;
    }

    //关注失败回调
    @Override
    public void onCancelShopSuc(BaseModel baseModel) {
//        Log.e(TAG, "onCancelShopSuc: " );
        btnAddAttention.setText("+ 关注");
        isAttention = false;
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        //显示分享对话框
        ShareBean shareBean = model.getData();
        if(shareBean != null){
            DialogShare dialogShare = new DialogShare(mContext, shareBean);
            MyApplication.shareType = ShareType.SHOP_HOME;
            dialogShare.show();
        }
    }

    @Override
    public void onShareCallback(BaseModel model) {

    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareCallback(ShareSuccessEvent event){
        if(event.shareType == ShareType.SHOP_HOME){
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.SHOP_HOME);
        }
    }


    @AfterPermissionGranted(RC_CALL_PHONE)
    private void startPermissionsCallPhone() {
        //检查是否获取该权限
        if (hasPermissions()) {
            //具备权限 直接进行操作
            callPhone(shopMobile);
        } else {
            //权限拒绝 申请权限
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限

            EasyPermissions.requestPermissions(this,
                    getResources().getString(R.string.call_phone_permission),
                    RC_CALL_PHONE, Manifest.permission.CALL_PHONE
            );
        }
    }

    /**
     * 判断是否添加了权限
     *
     * @return true
     */
    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE);
    }

    //权限同意
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        callPhone(shopMobile);
    }

    //权限不同意
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(ShopActivity.this, perms)) {
            AppSettingsDialog.Builder builder = new AppSettingsDialog.Builder(ShopActivity.this);
            builder.setTitle("允许权限")
                    .setRationale("没有该权限，此应用程序部分功能可能无法正常工作。打开应用设置界面以修改应用权限")
                    .setPositiveButton("去设置")
                    .setNegativeButton("取消")
                    .setRequestCode(125)
                    .build()
                    .show();
        }
    }

    /**
     * 将结果转发到EasyPermissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode,
                permissions, grantResults, ShopActivity.this);
    }

    /**
     * 店铺页面图片的adapter
     */
    private class ZhanShiImgAdapter extends CommonAdapter<String> {

        public ZhanShiImgAdapter(Context context, int layoutId, List<String> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder viewHolder, String item, int position) {
            ImageView imageView = viewHolder.getView(R.id.iv_shop_item_img);
            GlideUtil.showRadius(mContext, item, 2, imageView);
        }
    }

    /**
     * 店铺页面特色服务的adapter
     */
    private class ServiceAdapter extends CommonAdapter<ShopBean.ServiceListBean> {

        public ServiceAdapter(Context context, int layoutId, List<ShopBean.ServiceListBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ShopBean.ServiceListBean userItem, int position) {
            if (TextUtils.isEmpty(userItem.getServiceName())) {
                holder.setText(R.id.tv_shop_service_item_name, getServiceNameByServiceId(userItem.getServiceId()));
            } else {
                holder.setText(R.id.tv_shop_service_item_name, userItem.getServiceName());
            }
            holder.setImageResource(R.id.iv_shop_service_item_icon, getImgByServiceId(userItem.getServiceId()));
        }

        private String getServiceNameByServiceId(int serviceId) {
            String result;
            switch (serviceId) {
                case 12:
                    result = "好停车";
                    break;

                case 13:
                    result = "免预约";
                    break;

                case 14:
                    result = "24小时服务";
                    break;

                case 15:
                    result = "其他服务";
                    break;

                default:
                    result = "其他服务";
                    break;
            }
            return result;
        }

        private int getImgByServiceId(int serviceId) {
            int resourceId;
            switch (serviceId) {
                case 12:
                    resourceId = R.drawable.shop_service_stop_car;
                    break;

                case 13:
                    resourceId = R.drawable.shop_service_not_reservation;
                    break;

                case 14:
                    resourceId = R.drawable.shop_service_hours;
                    break;

                case 15:
                    resourceId = R.drawable.shop_service_not_reservation;
                    break;

                default:
                    resourceId = R.drawable.shop_service_not_reservation;
                    break;
            }
            return resourceId;
        }
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
