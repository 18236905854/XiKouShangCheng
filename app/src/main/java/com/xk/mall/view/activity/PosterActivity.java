package com.xk.mall.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
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

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.PromotionShareBean;
import com.xk.mall.model.impl.MyPromotionViewImpl;
import com.xk.mall.presenter.MyPromotionPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.InstallUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ScreenShotUtils;
import com.xk.mall.utils.ShareType;
import com.xk.mall.view.widget.CommonPopupWindow;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName PosterActivity
 * Description 海报页面
 * Author 卿凯
 * Date 2019/8/14/014
 * Version V1.0
 */
public class PosterActivity extends BaseActivity<MyPromotionPresenter> implements CommonPopupWindow.ViewInterface {
    @BindView(R.id.iv_poster_qrcode)
    ImageView ivPosterQrcode;//二维码
    @BindView(R.id.tv_poster_content)
    TextView tvPosterContent;//二维码
    @BindView(R.id.tv_poster_service)
    TextView tvPosterService;//服务
    @BindView(R.id.tv_poster_code)
    TextView tvPosterCode;//ID
    @BindView(R.id.tv_poster_price)
    TextView tvPosterPrice;//销售价
    @BindView(R.id.tv_poster_line_price)
    TextView tvPosterLinePrice;//市场价
    @BindView(R.id.tv_poster_goods_name)
    TextView tvPosterGoodsName;//商品名称
    @BindView(R.id.iv_poster_goods)
    ImageView ivPosterGoods;//商品图片
    @BindView(R.id.tv_poster_copy)
    TextView tvPosterCopy;//复制
    @BindView(R.id.rl_poster_goods)
    RelativeLayout rlPosterGoods;//商品总布局
    @BindView(R.id.rl_poster_copy)
    RelativeLayout rlPosterCopy;
    @BindView(R.id.tv_poster_save)
    TextView tvPosterSave;//保存图片
    @BindView(R.id.tv_poster_share)
    TextView tvPosterShare;//分享
    @BindView(R.id.ll_poster_bottom)
    LinearLayout llPosterBottom;
    private CommonPopupWindow popupWindow;

    String goodsName = "";//商品名称
    String goodsImg = "";//商品图片
    int goodsPrice = 0;//商品价格
    int goodsLinePrice = 0;//商品原价
    int activityType = 0;//活动类型
    String activityGoodsId = "";//活动商品ID

    /**传递过来的商品名称*/
    public static String GOODS_NAME = "goods_name";
    /**传递过来的商品图片*/
    public static String GOODS_IMG = "goods_img";
    /**传递过来的商品价格*/
    public static String GOODS_PRICE = "goods_price";
    /**传递过来的商品原价*/
    public static String GOODS_LINE_PRICE = "goods_line_price";
    /**传递过来的商品活动类型*/
    public static String GOODS_ACTIVITY_TYPE = "goods_activity_type";
    /**传递过来的商品活动类型*/
    public static String GOODS_ACTIVITY_ID = "goods_activity_id";
    /**传递过来的分享地址*/
    public static String SHARE_ADDRESS = "share_address";
    private String localImagePath;//图片地址
    private String code;//用户邀请码
    private boolean isResult = false;//是否保存图片成功
    private String shareAddress = "";//分享地址

    @Override
    protected MyPromotionPresenter createPresenter() {
        return new MyPromotionPresenter(null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_poster;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("生成海报");
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        goodsName = intent.getStringExtra(GOODS_NAME);
        goodsImg = intent.getStringExtra(GOODS_IMG);
        goodsPrice = intent.getIntExtra(GOODS_PRICE, 0);
        goodsLinePrice = intent.getIntExtra(GOODS_LINE_PRICE, 0);
        activityType = intent.getIntExtra(GOODS_ACTIVITY_TYPE, 0);
        activityGoodsId = intent.getStringExtra(GOODS_ACTIVITY_ID);
        shareAddress = intent.getStringExtra(SHARE_ADDRESS);
        GlideUtil.showRadius(mContext, goodsImg, 2, ivPosterGoods);
        tvPosterGoodsName.setText(goodsName);
        tvPosterPrice.setText(getResources().getString(R.string.money) + PriceUtil.dividePrice(goodsPrice));
        tvPosterLinePrice.setText(getResources().getString(R.string.money) + PriceUtil.dividePrice(goodsLinePrice));
        tvPosterLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        code = SPUtils.getInstance().getString(Constant.USER_INVITE_CODE);
        if(activityType == ActivityType.ACTIVITY_WUG){
            tvPosterLinePrice.setVisibility(View.GONE);
        }else {
            tvPosterLinePrice.setVisibility(View.VISIBLE);
        }
        String activityName = ActivityType.getNameByType(activityType);
        if(null != code){
            tvPosterCode.setText("喜扣达人ID:" + code);
        }
        tvPosterContent.setText(String.format(getResources().getString(R.string.poster_content), activityName));
        LogUtils.e("分享二维码地址:" + shareAddress);
        crateQrLogo(shareAddress);
//        mPresenter.getPromotionData();
    }

    /**
     * 保存图片
     */
    private void saveImg(boolean isShare) {
        String path = Environment.getExternalStorageDirectory() + "/xikou/" + activityGoodsId;
        localImagePath = path+".jpg";
        File file = new File(localImagePath);
        if(file.exists()){//存在删除
            file.delete();
        }
        if (!file.exists()) {
            Bitmap viewBitmap = ScreenShotUtils.getCacheBitmapFromView(rlPosterGoods);
            isResult = ScreenShotUtils.saveBitmapToSdCard(mContext, viewBitmap, activityGoodsId);
        } else {
            isResult = true;
        }
        if(isShare && isResult){
            shareImg();
        }else if(!isShare && isResult){
            ToastUtils.showShortToast(mContext, "保存成功");
        }
    }

    @OnClick({R.id.tv_poster_copy, R.id.tv_poster_save, R.id.tv_poster_share})
    public void clickView(View view){
        switch (view.getId()){
            case R.id.tv_poster_copy://复制
                String text = tvPosterContent.getText().toString();
                String address = text + " 【" + goodsName + "】 " + shareAddress;
                PriceUtil.copyOperation(mContext, address);
                break;

            case R.id.tv_poster_save://保存
                saveImg(false);
                break;

            case R.id.tv_poster_share://分享
                saveImg(true);
                break;
        }
    }

    private void shareImg(){
        //直接分享
        showSharePop();
    }

    /**
     * 分享弹窗
     */
    private void showSharePop() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(mContext).inflate(R.layout.dialog_share_tuiguan, null);
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

    @Override
    public void getChildView(View view, int layoutResId) {
        ImageView ivClose = view.findViewById(R.id.iv_share_close);
        TextView tvTitle = view.findViewById(R.id.tv_share_title);
        tvTitle.setText("文案已自动复制");
        LinearLayout llShareWeChat = view.findViewById(R.id.ll_share_cut_wechat);
        LinearLayout llShareCircle = view.findViewById(R.id.ll_share_cut_circle);
        LinearLayout llShareCutSina = view.findViewById(R.id.ll_share_cut_sina);
        File file = new File(localImagePath);
        ivClose.setOnClickListener(v -> {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
        });

        llShareWeChat.setOnClickListener(v -> {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
            sharePlatForm(SHARE_MEDIA.WEIXIN, file);
        });

        llShareCircle.setOnClickListener(v -> {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
            sharePlatForm(SHARE_MEDIA.WEIXIN_CIRCLE, file);
        });

        llShareCutSina.setOnClickListener(v -> {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
            sharePlatForm(SHARE_MEDIA.SINA, file);
        });
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
//            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(PosterActivity.this,"成功了",Toast.LENGTH_LONG).show();
//            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(PosterActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(PosterActivity.this,"取消了",Toast.LENGTH_LONG).show();

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
            }else{
                UMImage image = new UMImage(mContext, filePath);//本地文件
                new ShareAction(PosterActivity.this).setPlatform(type).withMedia(image).share();
            }

        }else if(type.equals(SHARE_MEDIA.SINA)){
            if(!InstallUtil.uninstallSoftware(mContext, "com.sina.weibo")){
                ToastUtils.showShortToast(mContext, "您未安装新浪微博");

            }else{
                UMImage imagelocal = new UMImage(this, filePath);
                imagelocal.setThumb(new UMImage(this,filePath));
                new ShareAction(PosterActivity.this).withMedia(imagelocal )
                        .setPlatform(type)
                        .setCallback(shareListener).share();
            }
        }

    }

//    @Override
//    public void onGetPromotionSuc(BaseModel<PromotionShareBean> model) {
//        PromotionShareBean data = model.getData();
//        if (data != null) {
//            String bgImageUrl = data.getImageUrl();
//            String qRcodeUrl = data.getJumpUrl();
//            String qrText;
//            if (null != qRcodeUrl && !TextUtils.isEmpty(qRcodeUrl) && qRcodeUrl.startsWith("http")
//                    && qRcodeUrl.contains("luluxk")) {
//                if (!qRcodeUrl.contains(Constant.BASE_CODE_KEY)) {
//                    qrText = qRcodeUrl + "?" + Constant.BASE_CODE_KEY + SPUtils.getInstance().getString(Constant.USER_INVITE_CODE);
//                } else if (qRcodeUrl.endsWith(Constant.BASE_CODE_KEY)) {
//                    qrText = qRcodeUrl + SPUtils.getInstance().getString(Constant.USER_INVITE_CODE);
//                } else {
//                    qrText = qRcodeUrl;
//                }
//            } else {
//                qrText = "https://wx.luluxk.com/index.html?extcode=" + SPUtils.getInstance().getString(Constant.USER_INVITE_CODE);
//            }
//            Log.e(TAG, "qrText===== " + qrText);
////            GlideUtil.show(mContext, bgImageUrl, ivMain);
////            crateQrLogo(qRcodeUrl);
//            crateQrLogo(qrText);
//        }
//    }

    @SuppressLint("StaticFieldLeak")
    private void crateQrLogo(String qrText) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_promotion);
                return QRCodeEncoder.syncEncodeQRCode(qrText, ConvertUtils.dp2px(60), Color.BLACK, logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    ivPosterQrcode.setImageBitmap(bitmap);
                }
            }
        }.execute();
    }

}
