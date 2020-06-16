package com.xk.mall.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.view.widget.CustomVideoView;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * ClassName Splash2Activity
 * Description 启动页面
 * Author 卿凯
 * Date 2019/7/3/003
 * Version V1.0
 */
public class Splash2Activity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    RelativeLayout rlHome;

    CustomVideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        fullscreen(true);
        setContentView(R.layout.activity_splash);
        rlHome = findViewById(R.id.rl_home);
        videoView = new CustomVideoView(MyApplication.getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        videoView.setLayoutParams(layoutParams);
        rlHome.addView(videoView);
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.launcher;
        videoView.setVideoURI(Uri.parse(uri));
//        videoView.start();
        videoView.setOnPreparedListener(mp -> {
            if(mp != null){
                mp.start();
                mp.setLooping(false);
            }
        });

        videoView.setOnCompletionListener(mp -> initPermissions());
    }

    /**
     * 防止videoView内存泄露重写的方法
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name))
                    return getApplicationContext().getSystemService(name);
                return super.getSystemService(name);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(videoView != null){
            videoView.setOnCompletionListener(null);
            videoView.pause();
            videoView.stopPlayback();
            rlHome.removeAllViews();
            videoView = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(videoView != null){
            videoView.start();
            videoView.setOnPreparedListener(mp -> {
                if(mp != null){
                    mp.start();
                    mp.setLooping(false);
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(videoView != null){
            videoView.pause();
        }
    }

    private void fullscreen(boolean enable) {
        if (enable) { //显示状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else { //隐藏状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }

    private void goMain(){
        MyApplication.imei = getIMEI(Splash2Activity.this);
        ActivityUtils.startActivity(MainActivity.class);
        finish();
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

    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    private static final String[] LOCATION_AND_CONTACTS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    private void startPermissionsTask() {
        //检查是否获取该权限
        if (hasPermissions()) {
            //具备权限 直接进行操作
            goMain();
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
                permissions, grantResults, Splash2Activity.this);
    }

    /**
     * 某些权限已被授予
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //某些权限已被授予
        LogUtils.d("权限", "onPermissionsGranted:" + requestCode + ":" + perms.size());
        goMain();
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
        if (EasyPermissions.somePermissionPermanentlyDenied(Splash2Activity.this, perms)) {
            AppSettingsDialog.Builder builder = new AppSettingsDialog.Builder(Splash2Activity.this);
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
            goMain();
        }
    }
}
