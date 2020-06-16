package com.xk.mall.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.orhanobut.logger.Logger;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.ImagePickerImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * ClassName ScanActivity
 * Description 扫码页面
 * Author 卿凯
 * Date 2019/8/8/008
 * Version V1.0
 */
public class ScanActivity extends BaseActivity implements QRCodeView.Delegate{
    @BindView(R.id.zxingview)
    ZXingView mZXingView;
    private final int REQUEST_IMAGE = 100;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("扫描二维码");
        setRightText("相册");
        showRight(true);
        setOnRightClickListener(v -> {
            //点击打开相册
            Intent intent1 = new Intent(mContext, ImageGridActivity.class);
            startActivityForResult(intent1, REQUEST_IMAGE);
        });
    }

    @Override
    protected void initData() {
        mZXingView.setDelegate(this);
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        initImagePicker();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagePickerImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(false);                   //是否按矩形区域保存
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(800);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(800);                            //保存文件的高度。单位像素
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
//            final String picturePath = BGAPhotoPickerActivity.getSelectedPhotos(data).get(0);
//            // 本来就用到 QRCodeView 时可直接调 QRCodeView 的方法，走通用的回调
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if(images != null && !TextUtils.isEmpty(images.get(0).path)){
                Logger.e("图片地址:" + images.get(0).path);
                String picturePath = images.get(0).path;
                mZXingView.decodeQRCode(picturePath);
            }else {
                ToastUtils.showShortToast(mContext, "没有获取到图片");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.e(TAG, "相册图片result:" + result);
        vibrate();
        Intent intent = new Intent();
        intent.putExtra(Constant.SCAN_RESULT, result);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = mZXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZXingView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

}
