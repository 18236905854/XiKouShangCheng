package com.xk.mall.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 *     desc  : 启动页面
 *     revise: 针对启动页闪屏优化处理，可以看这篇博客：https://www.jianshu.com/p/4dcc8e0e6966
 */
public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //将window的背景图设置为空
        getWindow().setBackgroundDrawable(null);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        initPermissions();
    }

    /**
     * 屏蔽物理返回按钮
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    private static final String[] LOCATION_AND_CONTACTS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    private void startPermissionsTask() {
        //检查是否获取该权限
        if (hasPermissions()) {
            //具备权限 直接进行操作
            //获取IMEI
            MyApplication.imei = getIMEI(mContext);
            ActivityUtils.startActivity(MainActivity.class);
            finish();
        } else {
            //权限拒绝 申请权限
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限

            EasyPermissions.requestPermissions(this,
                    this.getResources().getString(R.string.easy_permissions_sd),
                    RC_LOCATION_CONTACTS_PERM, LOCATION_AND_CONTACTS);
        }
    }

    private String getIMEI(Context context){
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return DeviceUtils.getAndroidID();
        }
    }


    /**
     * 判断是否添加了权限
     * @return true
     */
    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(this, LOCATION_AND_CONTACTS);
    }


    /**
     * 初始化权限
     */
    private void initPermissions() {
        startPermissionsTask();
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
                permissions, grantResults, SplashActivity.this);
    }

    /**
     * 某些权限已被授予
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //某些权限已被授予
        LogUtils.d("权限", "onPermissionsGranted:" + requestCode + ":" + perms.size());
        MyApplication.imei = getIMEI(mContext);
        ActivityUtils.startActivity(MainActivity.class);
        finish();
    }

    /**
     * 某些权限已被拒绝
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //某些权限已被拒绝
        LogUtils.d("权限", "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(SplashActivity.this, perms)) {
            AppSettingsDialog.Builder builder = new AppSettingsDialog.Builder(SplashActivity.this);
            builder.setTitle("允许权限")
                    .setRationale("没有该权限，此应用程序部分功能可能无法正常工作。打开应用设置界面以修改应用权限")
                    .setPositiveButton("去设置")
                    .setNegativeButton("取消")
                    .setRequestCode(124)
                    .build()
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            // 当用户从应用设置界面返回的时候，可以做一些事情，比如弹出一个土司。
            LogUtils.d("权限", "onPermissionsDenied:" + requestCode + ":");
            ActivityUtils.startActivity(MainActivity.class);
            finish();
        }
    }


}
