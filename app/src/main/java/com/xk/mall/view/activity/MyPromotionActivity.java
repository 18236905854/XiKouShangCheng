package com.xk.mall.view.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.RomUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.xk.mall.BuildConfig;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseContent;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.PromotionShareBean;
import com.xk.mall.model.impl.MyPromotionViewImpl;
import com.xk.mall.presenter.MyPromotionPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.InstallUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ScreenShotUtils;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.CommonPopupWindow;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * 我的推广
 */
public class MyPromotionActivity extends BaseActivity<MyPromotionPresenter> implements MyPromotionViewImpl, CommonPopupWindow.ViewInterface {
    private static final String TAG = "MyPromotionActivity";

    @BindView(R.id.iv_main)
    ImageView ivMain;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.rl_share_page)
    RelativeLayout rl_share_page;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    @BindView(R.id.iv_header_logo)
    ImageView ivHeadLogo;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_user_id)
    TextView tvUserId;
    private CommonPopupWindow popupWindow;

    private String bgImageUrl;
    private String qRcodeUrl;
    private String extCode;
    private String headUrl;
    private String nickName;
    private String localImagePath;//本地图片地址

    private final String DIMEN_CLASS = "com.android.internal.R$dimen";
    //参照宽高
    public final float STANDARD_WIDTH = 750;
    public final float STANDARD_HEIGHT = 1334;

    @Override
    protected MyPromotionPresenter createPresenter() {
        return new MyPromotionPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_promotion;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar_title.setTextColor(Color.parseColor("#444444"));
        toolbar_title.setText("我的推广");
//        setRightDrawable(R.drawable.ic_activity_share);
//        setOnRightIconClickListener(v -> {
//            rightButtonClick();
//        });
    }


    @Override
    protected void initData() {
        extCode = SPUtils.getInstance().getString(Constant.USER_INVITE_CODE);
        headUrl = SPUtils.getInstance().getString(Constant.HEAD_URL);
        nickName = SPUtils.getInstance().getString(Constant.NICK_NAME);
        GlideUtil.show(mContext, headUrl, ivHeadLogo);
        tvNickName.setText(nickName);
        tvUserId.setText(extCode);
        if(RomUtils.isOppo() || RomUtils.isVivo()){
            tvUserId.setTextSize(45);
        }
        mPresenter.getPromotionData();
        ivMain.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int imgHeight = ivMain.getMeasuredHeight();
        rlMain.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(extCode);
            ToastUtils.showShortToast(mContext,"复制邀请码成功");
        });

//        mPresenter.getQRCode(MyApplication.userId);

        int heightPixels = ScreenUtils.getScreenHeight();
        Logger.e("屏幕高度:" + heightPixels);
        int imgBottom = heightPixels - imgHeight;
        int half = ConvertUtils.dp2px(250) / 2;
        rlUserInfo.measure(0, 0);
        int userInfoHeight = rlUserInfo.getMeasuredHeight();
        Logger.e("个人信息高度:" + userInfoHeight);
        if(userInfoHeight != 0 && userInfoHeight >= half){
            Logger.e("修改文字大小");
            tvUserId.setTextSize(40);
        }
//        //2222  --2340    1920  1792   imgHeight 1734
//        float scale=heightPixels/2222;
//        boolean isHaveNavBar = XiKouUtils.isShowNavBar(MyPromotionActivity.this);
//        if (heightPixels <= 1920) {
////            Log.e(TAG, "initData: "+heightPixels );
//            if (isHaveNavBar) {
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlUserInfo.getLayoutParams();
//                int bootom = ConvertUtils.dp2px(280);
//                layoutParams.setMargins(0, 0, 0, bootom);
//                rlUserInfo.setLayoutParams(layoutParams);
//                RelativeLayout.LayoutParams layoutIvParams = (RelativeLayout.LayoutParams) ivQrCode.getLayoutParams();
//                int ivBottom = ConvertUtils.dp2px(70);
//                layoutIvParams.setMargins(0, 0, 0, Math.abs(ivBottom));
//                ivQrCode.setLayoutParams(layoutIvParams);
//            } else {
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlUserInfo.getLayoutParams();
//                int bootom = ConvertUtils.dp2px(320);
//                layoutParams.setMargins(0, 0, 0, bootom);
//                rlUserInfo.setLayoutParams(layoutParams);
//                RelativeLayout.LayoutParams layoutIvParams = (RelativeLayout.LayoutParams) ivQrCode.getLayoutParams();
//                int ivBottom = ConvertUtils.dp2px(80);
//                layoutIvParams.setMargins(0, 0, 0, Math.abs(ivBottom));
//                ivQrCode.setLayoutParams(layoutIvParams);
//            }
//        } else {
//            // 针对小米手机
//            if (RomUtils.isXiaomi()) {
//                Log.e(TAG, "initData: 小米手机" );
//                if ((Settings.Global.getInt(mContext.getContentResolver(), "force_fsg_nav_bar", 0) != 0)) {
//                    Log.e(TAG, "initData: 如是是true 的话就是隐藏的虚拟导航栏，反之是显示导航栏");
//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlUserInfo.getLayoutParams();
//                    int bootom = ConvertUtils.dp2px(380);
//                    layoutParams.setMargins(0, 0, 0, bootom);
//                    rlUserInfo.setLayoutParams(layoutParams);
//                    RelativeLayout.LayoutParams layoutIvParams = (RelativeLayout.LayoutParams) ivQrCode.getLayoutParams();
//                    int ivBottom = ConvertUtils.dp2px(140);
//                    layoutIvParams.setMargins(0, 0, 0, Math.abs(ivBottom));
//                    ivQrCode.setLayoutParams(layoutIvParams);
//                } else {
//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlUserInfo.getLayoutParams();
//                    int bootom = ConvertUtils.dp2px(340);
//                    layoutParams.setMargins(0, 0, 0, bootom);
//                    rlUserInfo.setLayoutParams(layoutParams);
//                    RelativeLayout.LayoutParams layoutIvParams = (RelativeLayout.LayoutParams) ivQrCode.getLayoutParams();
//                    int ivBottom = ConvertUtils.dp2px(100);
//                    layoutIvParams.setMargins(0, 0, 0, Math.abs(ivBottom));
//                    ivQrCode.setLayoutParams(layoutIvParams);
//                }
//
//            } else {
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlUserInfo.getLayoutParams();
//                int bootom = ConvertUtils.dp2px(340);
//                layoutParams.setMargins(0, 0, 0, bootom);
//                rlUserInfo.setLayoutParams(layoutParams);
//                RelativeLayout.LayoutParams layoutIvParams = (RelativeLayout.LayoutParams) ivQrCode.getLayoutParams();
//                int ivBottom = ConvertUtils.dp2px(100);
//                layoutIvParams.setMargins(0, 0, 0, Math.abs(ivBottom));
//                ivQrCode.setLayoutParams(layoutIvParams);
//            }
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取接口数据成功
     *
     * @param model
     */
    @Override
    public void onGetPromotionSuc(BaseModel<PromotionShareBean> model) {
        PromotionShareBean data = model.getData();
        if (data != null) {
            bgImageUrl = data.getImageUrl();
            qRcodeUrl = data.getJumpUrl();
            String qrText;
            if (null != qRcodeUrl && !TextUtils.isEmpty(qRcodeUrl) && qRcodeUrl.startsWith("http")
                    && qRcodeUrl.contains("luluxk")) {
                if (!qRcodeUrl.contains(Constant.BASE_CODE_KEY)) {
                    qrText = qRcodeUrl + "?" + Constant.BASE_CODE_KEY + SPUtils.getInstance().getString(Constant.USER_INVITE_CODE);
                } else if (qRcodeUrl.endsWith(Constant.BASE_CODE_KEY)) {
                    qrText = qRcodeUrl + SPUtils.getInstance().getString(Constant.USER_INVITE_CODE);
                } else {
                    qrText = qRcodeUrl;
                }
            } else {
                qrText = "https://wx.luluxk.com/index.html?extcode=" + SPUtils.getInstance().getString(Constant.USER_INVITE_CODE);
            }
            Log.e(TAG, "qrText===== " + qrText);
//            GlideUtil.show(mContext, bgImageUrl, ivMain);
//            crateQrLogo(qRcodeUrl);
            crateQrLogo(qrText);
        }

    }

    /**
     * 分享弹窗
     */
    @OnClick({R.id.iv_promotion_bottom})
    public void rightButtonClick() {
        boolean isResult = false;
        String path = Environment.getExternalStorageDirectory() + "/xikou/" + extCode;
        localImagePath = path + ".jpg";
        File file = new File(localImagePath);
        if(file.exists()){//存在删除
            file.delete();
        }
        if (!file.exists()) {
            Bitmap viewBitmap = ScreenShotUtils.getCacheBitmapFromView(rl_share_page);
            isResult = ScreenShotUtils.saveBitmapToSdCard(mContext, viewBitmap, extCode);
        } else {
            isResult = true;
        }
        if (isResult) {
            showSharePop();
        }
    }

    /**
     * 分享弹窗
     */
    private void showSharePop() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(MyPromotionActivity.this).inflate(R.layout.dialog_share_tuiguan, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.dialog_share_tuiguan)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        //防止PopupWindow被软件盘挡住
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 创建二维码
     *
     * @param url
     */
    @SuppressLint("StaticFieldLeak")
    private void crateQrLogo(String url) {
        DisplayMetrics displayMetrics = DensityUtils.getDisplayMetrics(mContext);
        float screenWidth = displayMetrics.widthPixels;
        int imgWidth = (int) (screenWidth * 90 / 375);
        Logger.e("屏幕宽度:" + screenWidth);
        Logger.e("缩放宽度:" + imgWidth);
//        Bitmap mBitmap = CodeUtils.createImage(url, 240, 240, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
//        ivQrCode.setImageBitmap(mBitmap);
//        ViewGroup.LayoutParams layoutParams = ivQrCode.getLayoutParams();
//        Log.e(TAG, "crateQrLogo: "+layoutParams.height );
//        Log.e(TAG, "crateQrLogo: "+layoutParams.width );
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_promotion);
                return QRCodeEncoder.syncEncodeQRCode(url, imgWidth, Color.BLACK, logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    ivQrCode.setImageBitmap(bitmap);
                    if(RomUtils.isVivo() || RomUtils.isOppo()){
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivQrCode.getLayoutParams();
                        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + 3, layoutParams.rightMargin, layoutParams.bottomMargin);
                        ivQrCode.setLayoutParams(layoutParams);
                    }
                }
            }
        }.execute();
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        ImageView ivClose = view.findViewById(R.id.iv_share_close);
        LinearLayout llShareWeChat = view.findViewById(R.id.ll_share_cut_wechat);
        LinearLayout llShareCircle = view.findViewById(R.id.ll_share_cut_circle);
        LinearLayout llShareCutSina = view.findViewById(R.id.ll_share_cut_sina);
        File file = new File(localImagePath);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });

        llShareWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                sharePlatForm(SHARE_MEDIA.WEIXIN, file);
            }
        });

        llShareCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                sharePlatForm(SHARE_MEDIA.WEIXIN_CIRCLE, file);
            }
        });

        llShareCutSina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                sharePlatForm(SHARE_MEDIA.SINA, file);
            }
        });


    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
//            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(MyPromotionActivity.this, "成功了", Toast.LENGTH_LONG).show();
//            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(MyPromotionActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(MyPromotionActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    /**
     * 分享图片到个个平台
     *
     * @param type     平台类型
     * @param filePath 本地文件路径
     */
    private void sharePlatForm(SHARE_MEDIA type, File filePath) {
        if (type.equals(SHARE_MEDIA.WEIXIN) || type.equals(SHARE_MEDIA.WEIXIN_CIRCLE)) {
            if (!InstallUtil.isWeChatAppInstalled(mContext)) {
                ToastUtils.showShortToast(mContext, "您未安装微信");
            } else {
                UMImage image = new UMImage(MyPromotionActivity.this, filePath);//本地文件
                new ShareAction(MyPromotionActivity.this).setPlatform(type).withMedia(image).share();
            }

        } else if (type.equals(SHARE_MEDIA.SINA)) {
            if (!InstallUtil.uninstallSoftware(mContext, "com.sina.weibo")) {
                ToastUtils.showShortToast(mContext, "您未安装新浪微博");

            } else {
                UMImage imagelocal = new UMImage(this, filePath);
                imagelocal.setThumb(new UMImage(this, filePath));
                new ShareAction(MyPromotionActivity.this).withMedia(imagelocal)
                        .setPlatform(type)
                        .setCallback(shareListener).share();
//                new ShareAction(MyPromotionActivity.this).withText("22323232323")
//                        .setPlatform(type)
//                        .setCallback(shareListener).share();
            }
        }

    }


    public int getValue(Context context, String systemid, int defValue) {

        try {
            Class<?> clazz = Class.forName(DIMEN_CLASS);
            Object r = clazz.newInstance();
            Field field = clazz.getField(systemid);
            int x = (int) field.get(r);
            return context.getResources().getDimensionPixelOffset(x);

        } catch (Exception e) {
            return defValue;
        }
    }

}
