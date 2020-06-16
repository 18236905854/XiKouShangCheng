package com.xk.mall.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.kennyc.view.MultiStateView;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityPicker;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.gen.AddressServerBeanDao;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.model.entity.IndustryBean;
import com.xk.mall.model.entity.LocationBean;
import com.xk.mall.model.eventbean.LocationPointBean;
import com.xk.mall.model.impl.NearViewImpl;
import com.xk.mall.presenter.NearPresenter;
import com.xk.mall.promptdialog.PromptDialog;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.activity.NearShopSearchActivity;
import com.xk.mall.view.adapter.NearViewPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * ClassName NearFragment
 * Description 附近页面
 * Author 卿凯
 * Date 2019/6/15/015
 * Version V1.0
 */
public class NearFragment extends BaseFragment<NearPresenter> implements EasyPermissions.PermissionCallbacks,
        GeocodeSearch.OnGeocodeSearchListener, NearViewImpl {
    private static final String TAG = "NearFragment";

    @BindView(R.id.tv_near_location)
    TextView tvNearLocation;//当前定位的位置
    @BindView(R.id.tab_near)
    SlidingTabLayout tabNear;//标题栏
    @BindView(R.id.vp_near)
    ViewPager vpNear;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.et_near_search)
    EditText etSearch;//搜索框
    @BindView(R.id.stateView)
    MultiStateView stateView;

    private double longitude, latitude;//经纬度

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private final int PERMISSION_CODE_LOCATION = 101;
    private static final String[] LOCATION_PERMISSION = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private GeocodeSearch geocoderSearch;
    private LocationBean locationBean;//定位成功的Bean
    private String locationCity;//定位的城市
    private PromptDialog promptDialog;
    private TextView tvLoadingText;
    private Button btnReplay;

    public static NearFragment getInstance() {
        return new NearFragment();
    }

    @Override
    protected NearPresenter createPresenter() {
        return new NearPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_near;
    }

    @Override
    protected void initData() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f)
                .init();
        ImmersionBar.with(this)
                .titleBar(llTitle) //可以为任意view，如果是自定义xml实现标题栏的话，标题栏根节点不能为RelativeLayout或者ConstraintLayout，以及其子类
                .init();
        //地理编码
        geocoderSearch = new GeocodeSearch(mContext);
        geocoderSearch.setOnGeocodeSearchListener(this);
        //申请定位权限获取定位信息
        tvNearLocation.setText("北京");
        btnReplay=stateView.findViewById(R.id.btn_replay);

        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getIndustry();
            }
        });

        etSearch.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, NearShopSearchActivity.class);
            intent.putExtra(Constant.INTENT_LONGITUDE, MyApplication.longitude);
            intent.putExtra(Constant.INTENT_LATITUDE, MyApplication.latitude);
            ActivityUtils.startActivity(intent);
        });
    }

    @Override
    protected void loadLazyData() {
//        showLoadingDialog();
        Log.e(TAG, "loadLazyData: ===");
        tvLoadingText=stateView.findViewById(R.id.tv_loading_text);
        tvLoadingText.setText("定位中...");

        initLocationPermission();
        tvNearLocation.setOnClickListener(v -> {
            String titleCity = "";
            if (TextUtils.isEmpty(locationCity)) {
                titleCity = tvNearLocation.getText().toString();
            } else {
                titleCity = locationCity;
            }
            //点击选择地址
            JDCityPicker cityPicker = new JDCityPicker(titleCity);
            cityPicker.init(mContext);
            cityPicker.setProvinceList(getProvinceData());
            cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                @Override
                public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                    GeocodeQuery query = null;
                    if(province != null && city != null){
                        query = new GeocodeQuery(province.getName() + city.getName(), province.getName() + city.getName());
                    }else if(province != null){
                        query = new GeocodeQuery(province.getName(), province.getName());
                    }
                    geocoderSearch.getFromLocationNameAsyn(query);
                    tvNearLocation.setText(city.getName());
                }

                @Override
                public void onCancel() {
                }
            });
            cityPicker.showCityPicker();
        });

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
        promptDialog.showLoading("加载中...", true);
    }

    /**
     * 从数据库中初始化对话框的数据
     */
    private List<ProvinceBean> getProvinceData() {
        List<AddressServerBean> serverBeans = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao()
                .queryBuilder().where(AddressServerBeanDao.Properties.Level.eq(1)).build().list();
        Logger.e("取出来的地址:" + serverBeans.size());
        List<ProvinceBean> provinceBeanList = new ArrayList<>();
        for (AddressServerBean addressServerBean : serverBeans) {
            ProvinceBean provinceBean = new ProvinceBean();
            provinceBean.setName(addressServerBean.name);
            provinceBean.setId("" + addressServerBean.areaId);
            ArrayList<CityBean> cityBeans = new ArrayList<>();
            List<AddressServerBean> cityServer = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao()
                    .queryBuilder().where(AddressServerBeanDao.Properties.ParentId.eq(addressServerBean.areaId)).build().list();
            for (AddressServerBean cityBean : cityServer) {
                //数据处理--（例如--北京市--北京市）
                if (cityServer.size() < 5) {
                    CityBean cityBean2 = new CityBean();
                    cityBean2.setId("" + cityBean.areaId);
                    cityBean2.setName(addressServerBean.name);
                    cityBeans.add(cityBean2);
                    break;
                } else {
                    CityBean cityBean1 = new CityBean();
                    cityBean1.setId("" + cityBean.areaId);
                    cityBean1.setName(cityBean.name);
                    cityBeans.add(cityBean1);
                }

            }
            provinceBean.setCityList(cityBeans);
            provinceBeanList.add(provinceBean);
        }
        return provinceBeanList;
    }

    @AfterPermissionGranted(PERMISSION_CODE_LOCATION)
    private void initLocationPermission() {
        if (!EasyPermissions.hasPermissions(mContext, LOCATION_PERMISSION)) {
            EasyPermissions.requestPermissions(this, getString(R.string.easy_permissions_location),
                    PERMISSION_CODE_LOCATION, LOCATION_PERMISSION);
        } else {
            startLocation();
        }
    }

    private void startLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(mContext);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);
        //设置配置信息
        mLocationClient.setLocationOption(mLocationOption);
        //设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);
        //启动定位
        mLocationClient.startLocation();
    }

    //异步获取定位结果
    AMapLocationListener mAMapLocationListener = amapLocation -> {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                tvLoadingText.setText("加载中...");
                //解析定位结果
                String province = amapLocation.getProvince();//获取省份
                locationCity = amapLocation.getCity();//获取城市
                String area = amapLocation.getDistrict();//获取城区，岳麓区
                Logger.e("纬度:" + amapLocation.getLatitude() + ";经度:" + amapLocation.getLongitude());
                SPUtils.getInstance().put(Constant.LOCATION, locationCity);
                tvNearLocation.setText(locationCity);
                locationBean = new LocationBean(amapLocation.getLongitude(), amapLocation.getLatitude());
                mPresenter.getIndustry();
//                initCategory(new LocationBean(amapLocation.getLongitude(),amapLocation.getLatitude()));
            }else{
                tvLoadingText.setText("加载中...");
                mPresenter.getIndustry();
//                Log.e(TAG, ": 定位失败"+amapLocation.getErrorCode() );
//                Log.e(TAG, ": 定位失败"+amapLocation.getErrorInfo() );
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode,
                permissions, grantResults, mContext);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //启动定位
        startLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        mPresenter.getIndustry();
    }


    //------------------------坐标转地址/坐标转地址的监听回调---
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        Log.e(TAG, "onRegeocodeSearched: " + regeocodeResult.getRegeocodeAddress());
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        if (i == 1000) {
            if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null &&
                    geocodeResult.getGeocodeAddressList().size() > 0) {

                GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
                longitude = geocodeAddress.getLatLonPoint().getLongitude();//经度
                Log.e(TAG, "onGeocodeSearched:经度== " + longitude);
                Log.e(TAG, "onGeocodeSearched:纬度== " + latitude);
                MyApplication.longitude = longitude;
                MyApplication.latitude = latitude;
                //请求数据---通知childFragment 请求数据
                EventBus.getDefault().post(new LocationPointBean(longitude, latitude));
            }
        }
    }

    @Override
    public void onGetIndustrySuccess(BaseModel<List<IndustryBean>> model) {
        stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
//        closeLoadingDialog();
        if (model.getData() != null) {
            bindViewPager(model.getData());
        }
    }

    private void bindViewPager(List<IndustryBean> industryBeans) {
        List<Fragment> fragments = new ArrayList<>();
        if (locationBean == null) {
            locationBean = new LocationBean(0.0, 0.0);
        }
        for (IndustryBean titleBean : industryBeans) {
            fragments.add(NearChildChildFragment.getInstance(titleBean.getIndustry1(), locationBean.getLongitude(),
                    locationBean.getLatitude(), titleBean.getBannerImageList()));
        }
        if (industryBeans.size() <= 6) {
            tabNear.setTabSpaceEqual(true);
        } else {
            tabNear.setTabSpaceEqual(false);
        }
        NearViewPagerAdapter viewPagerAdapter = new NearViewPagerAdapter(getChildFragmentManager(), fragments, industryBeans);
        vpNear.setAdapter(viewPagerAdapter);
        vpNear.setOffscreenPageLimit(industryBeans.size());
        tabNear.setViewPager(vpNear);
        tabNear.setCurrentTab(0);
    }

    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        stateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }
}
