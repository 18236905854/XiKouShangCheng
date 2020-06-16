package com.xk.mall.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityPicker;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.gen.AddressServerBeanDao;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.model.eventbean.RefreshAddressBean;
import com.xk.mall.model.impl.EditorAddressViewImpl;
import com.xk.mall.model.request.UserAddressRequestBody;
import com.xk.mall.presenter.EditorAddressPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.XiKouUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * ClassName EditorAddressActivity
 * Description 编辑地址页面
 * Author 卿凯
 * Date 2019/6/10/010
 * Version V1.0
 */
public class EditorAddressActivity extends BaseActivity<EditorAddressPresenter> implements EasyPermissions.PermissionCallbacks, EditorAddressViewImpl {

    @BindView(R.id.et_editor_address_name)
    EditText etEditorAddressName;//姓名输入框
    @BindView(R.id.et_editor_address_phone)
    EditText etEditorAddressPhone;//电话号码输入框
    @BindView(R.id.tv_editor_address_address)
    TextView tvEditorAddressAddress;
    @BindView(R.id.et_editor_address_detail)
    EditText etEditorAddressDetail;//详细地址输入框
    @BindView(R.id.cb_editor_default)
    CheckBox checkBoxDefalut;//设置为默认收货地址
    @BindView(R.id.rl_editor_address_address)
    RelativeLayout rlAddress;//显示地址
    private boolean isDefault = false;//是否要设置默认
    /**传递过来的地址ID的Key*/
    public static String ADDRESS_ID = "address_id";
    /**传递过来的是否设置默认地址的Key*/
    public static String IS_SET_DEFAULT = "is_set_default";
    private String addressId = "";//传递过来的地址ID
    private int provinceId = -1;//省份ID
    private int cityId = -1;//城市ID
    private int areaId = -1;//区域ID
    private List<ProvinceBean> listData;//本地数据库中省市区数据

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private final int PERMISSION_CODE_LOCATION = 101;
    private static final String[] LOCATION_PERMISSION = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private int level = 1;

    @Override
    protected EditorAddressPresenter createPresenter() {
        return new EditorAddressPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editor_adress;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        AddressBean addressBean = (AddressBean) intent.getSerializableExtra(Constant.INTENT_ADDRESS_BEAN);
        addressId = intent.getStringExtra(ADDRESS_ID);
        listData = getProvinceData();
        long lastRefreshTime = SPUtils.getInstance().getLong(Constant.SP_REFRESH_TOKEN_TIME);
        long subRefreshTime = (System.currentTimeMillis() - lastRefreshTime) / 1000;
        long monthTime = Long.valueOf(7 * 24 * 3600);
        long isRefreshTime = subRefreshTime - monthTime;
        Logger.e(subRefreshTime + "请求开始2:" + isRefreshTime);
        long lastTime = SPUtils.getInstance().getLong(Constant.CONDITION_AREA);
        Logger.e(lastTime + "请求开始:" + System.currentTimeMillis());
        long subTime = (System.currentTimeMillis() - lastTime) / 1000;
        long isTime = subTime - monthTime;
        Logger.e(subTime + "请求开始2:" + isTime);
        //三个月之内不更换行政区域地址
        if (lastTime == 0 || isTime >= 0) {
            MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().deleteAll();
            mPresenter.getServerAddress(level);
        }
        if(addressBean != null){
            //设置数据
            etEditorAddressName.setText(addressBean.consigneeName);
            etEditorAddressPhone.setText(addressBean.consigneeMobile);
            etEditorAddressDetail.setText(addressBean.address);
            provinceId = addressBean.provinceId;
            cityId = addressBean.cityId;
            areaId = addressBean.areaId;
            tvEditorAddressAddress.setText(XiKouUtils.getAddressWithNoAddress(mContext, addressBean));
            if(addressBean.defaultId == 1){
                checkBoxDefalut.setChecked(true);
                isDefault = true;
            }
            setTitle("编辑地址");
        }else {
            setTitle("新增地址");
            //没有传递，表示是新增,开启定位
            isDefault = getIntent().getBooleanExtra(IS_SET_DEFAULT, false);
            if(isDefault){
                checkBoxDefalut.setChecked(true);
            }
            initLocationPermission();
        }

        //选择框的选中事件
        checkBoxDefalut.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isDefault = isChecked;
        });
    }

    @AfterPermissionGranted(PERMISSION_CODE_LOCATION)
    private void initLocationPermission(){
        if(!EasyPermissions.hasPermissions(this, LOCATION_PERMISSION)){
            EasyPermissions.requestPermissions(this, getString(R.string.easy_permissions_location),
                    PERMISSION_CODE_LOCATION, LOCATION_PERMISSION);
        }else {
            startLocation();
        }
    }

    private void startLocation(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocationClient != null && mAMapLocationListener != null){
            mLocationClient.unRegisterLocationListener(mAMapLocationListener);
        }
    }

    //异步获取定位结果
    AMapLocationListener mAMapLocationListener = amapLocation -> {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //解析定位结果
                String province = amapLocation.getProvince();//获取省份
                String city = amapLocation.getCity();//获取城市
                String area = amapLocation.getDistrict();//获取城区，岳麓区
                SPUtils.getInstance().put(Constant.LOCATION, city);
                provinceId = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().queryBuilder()
                        .where(AddressServerBeanDao.Properties.Name.like(province)).build().unique().areaId;
                cityId = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().queryBuilder()
                        .where(AddressServerBeanDao.Properties.Name.like(city)).build().unique().areaId;
                areaId = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().queryBuilder()
                        .where(AddressServerBeanDao.Properties.Name.like(area)).build().unique().areaId;
                tvEditorAddressAddress.setText(new StringBuffer().append(province).append(city).append(area));
            }
        }
    };

    /**
     * 从数据库中初始化对话框的数据
     */
    private List<ProvinceBean> getProvinceData(){
        List<AddressServerBean> serverBeans = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao()
                .queryBuilder().where(AddressServerBeanDao.Properties.Level.eq(1)).build().list();
        Logger.e("取出来的地址:" + serverBeans.size());
        List<ProvinceBean> provinceBeanList = new ArrayList<>();
        for(AddressServerBean addressServerBean : serverBeans){
            ProvinceBean provinceBean = new ProvinceBean();
            provinceBean.setName(addressServerBean.name);
            provinceBean.setId("" + addressServerBean.areaId);
            ArrayList<CityBean> cityBeans = new ArrayList<>();
            List<AddressServerBean> cityServer = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao()
                    .queryBuilder().where(AddressServerBeanDao.Properties.ParentId.eq(addressServerBean.areaId)).build().list();
            for(AddressServerBean cityBean : cityServer){
                CityBean cityBean1 = new CityBean();
                cityBean1.setId("" + cityBean.areaId);
                cityBean1.setName(cityBean.name);
                ArrayList<DistrictBean> districtBeans = new ArrayList<>();
                List<AddressServerBean> areaServer = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao()
                        .queryBuilder().where(AddressServerBeanDao.Properties.ParentId.eq(cityBean.areaId)).build().list();
                for(AddressServerBean addressServerBean1 : areaServer){
                    DistrictBean districtBean = new DistrictBean();
                    districtBean.setId("" + addressServerBean1.areaId);
                    districtBean.setName(addressServerBean1.name);
                    districtBeans.add(districtBean);
                }
                cityBean1.setCityList(districtBeans);
                cityBeans.add(cityBean1);
            }
            provinceBean.setCityList(cityBeans);
            provinceBeanList.add(provinceBean);
        }
        return provinceBeanList;
    }

    /**
     * 点击选择地址
     */
    @OnClick(R.id.rl_editor_address_address)
    public void clickChooseAddress(){
        JDCityPicker cityPicker = new JDCityPicker(tvEditorAddressAddress.getText().toString());
        cityPicker.init(this);
        if(listData != null && listData.size() != 0){
            cityPicker.setProvinceList(listData);
        }
        cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuffer stringBuffer = new StringBuffer();
                if(province != null){
                    provinceId = Integer.parseInt(province.getId());
                    stringBuffer.append(province.getName());
                }else {
                    provinceId = 0;
                }
                if(city != null){
                    cityId = Integer.parseInt(city.getId());
                    stringBuffer.append(city.getName());
                }else {
                    cityId = 0;
                }
                if(district != null){
                    areaId = Integer.parseInt(district.getId());
                    stringBuffer.append(district.getName());
                }else {
                    areaId = 0;
                }
                tvEditorAddressAddress.setText(stringBuffer.toString());
            }

            @Override
            public void onCancel() {
            }
        });
        cityPicker.showCityPicker();
    }

    /**
     * 保存按钮的点击事件
     */
    @OnClick(R.id.btn_editor_save)
    public void clickSave(){
        //检查数据格式并且请求网络保存数据
        String name = etEditorAddressName.getText().toString();
        String phone = etEditorAddressPhone.getText().toString();
        String address = etEditorAddressDetail.getText().toString();
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        if(TextUtils.isEmpty(name)){
            ToastUtils.showShort("请输入收货人姓名");
            return;
        }
        if(TextUtils.isEmpty(phone)){
            ToastUtils.showShort("请输入收货人号码");
            return;
        }
        if(phone.length() != 11){
            ToastUtils.showShort("收货人号码格式有误，请重新修改");
            return;
        }
        if(areaId == -1 || cityId == -1 || provinceId == -1){
            ToastUtils.showShort("请选择地址");
            return;
        }
        if(TextUtils.isEmpty(address)){
            ToastUtils.showShort("请输入收货人详细地址");
            return;
        }
        UserAddressRequestBody addressRequestBody = new UserAddressRequestBody();
        addressRequestBody.setAddress(address);
        if(areaId != -1 && areaId != 0){
            addressRequestBody.setAreaId(areaId);
        }
        addressRequestBody.setCityId(cityId);
        addressRequestBody.setProvinceId(provinceId);
        addressRequestBody.setConsigneeMobile(phone);
        addressRequestBody.setConsigneeName(name);
        addressRequestBody.setUserId(MyApplication.userId);
        addressRequestBody.setDefaultId(isDefault? 1 : 0);
        if(TextUtils.isEmpty(addressId)){
            //新增用户地址
            mPresenter.addAddress(addressRequestBody);
        }else {
            //修改用户地址
            addressRequestBody.setId(addressId);
            mPresenter.saveAddress(addressId, addressRequestBody);
        }
    }

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

    }

    /**
     * 更新地址成功的回调
     */
    @Override
    public void onUpdateAddressSuccess(BaseModel baseModel) {
        ToastUtils.showShort(baseModel.getMsg());
        finish();
        EventBus.getDefault().post(new RefreshAddressBean(true));
    }

    /**
     * 新增地址成功的回调
     */
    @Override
    public void onAddAddressSuccess(BaseModel baseModel) {
        ToastUtils.showShort(baseModel.getMsg());
        finish();
        EventBus.getDefault().post(new RefreshAddressBean(true));
    }

    @Override
    public void getAddressSuccess(BaseModel<List<AddressServerBean>> listBaseModel) {
        if (listBaseModel.getData() != null) {
            Logger.e("返回的size=" + listBaseModel.getData().size());
            level += 1;
            if (level <= 3) {
                mPresenter.getServerAddress(level);
                SPUtils.getInstance().put(Constant.CONDITION_AREA, System.currentTimeMillis());
            }
            Observable.create((ObservableOnSubscribe<String>) emitter -> {
                for (AddressServerBean serverBean : listBaseModel.getData()) {
                    MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao().insertOrReplace(serverBean);
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        Logger.e("请求结束:" + System.currentTimeMillis());
                    });
        }
    }
}
