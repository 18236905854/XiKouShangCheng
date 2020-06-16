package com.xk.mall.view.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;
import com.xk.mall.BuildConfig;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.api.ApiRetrofit;
import com.xk.mall.base.BaseContent;
import com.xk.mall.model.entity.CutSuccessBean;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.InstallUtil;
import com.xk.mall.utils.ShareType;

/**
 * ClassName SureAddressDialog
 * Description 分享砍价对话框
 * Author 卿凯
 * Date 2019/6/28/028
 * Version V1.0
 */
public class ShareCutDialog extends Dialog implements View.OnClickListener, UMShareListener {
    private static final String TAG = "ShareCutDialog";
    private Context mContext;
    private String name;//名字
    private String phone;//号码
    private String address;//地址
    private OnCloseListener onCloseListener;
    private OnSureListener onSureListener;

    private CutSuccessBean cutSuccessBean;//商品对象
    public ShareCutDialog(Context context, int theme,CutSuccessBean cutSuccessBean) {
        super(context, theme);
        this.mContext = context;
        this.cutSuccessBean=cutSuccessBean;
        Log.e(TAG, "ShareCutDialog: "+cutSuccessBean.getCommodityName());
    }

    public ShareCutDialog(Context context, String name, String phone, String address, OnSureListener onSureListener, OnCloseListener listener) {
        super(context);
        this.mContext = context;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.onSureListener = onSureListener;
        this.onCloseListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share_cut);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        ImageView cancelTxt = findViewById(R.id.img_cha);
        TextView tvDialogHelpNum=findViewById(R.id.tv_dialog_help_num);
        cancelTxt.setOnClickListener(this);
        tvDialogHelpNum.setText("分享给好友，获得"+cutSuccessBean.getBargainNumber()+"人助力");

        LinearLayout llWechat = findViewById(R.id.ll_share_cut_wechat);
        LinearLayout llCircle = findViewById(R.id.ll_share_cut_circle);
        LinearLayout llSina = findViewById(R.id.ll_share_cut_sina);
        llWechat.setOnClickListener(this);
        llCircle.setOnClickListener(this);
        llSina.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_cha:
                if (onCloseListener != null) {
                    onCloseListener.onClick(this, false);
                }
                this.dismiss();
                break;

            case R.id.ll_share_cut_wechat://微信好友--小程序
                sharePlatform(SHARE_MEDIA.WEIXIN);
                dismiss();
                break;

            case R.id.ll_share_cut_circle://微信朋友圈--小程序
//                ToastUtils.showShortToast(mContext,"敬请期待~");
//                sharePlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                dismiss();
                break;

            case R.id.ll_share_cut_sina://新浪微博
                sharePlatform(SHARE_MEDIA.SINA);
                dismiss();
                break;
        }
    }

    /**
     * 分享到各个平台
     */
    private void sharePlatform(SHARE_MEDIA share_media) {
        MyApplication.shareType = ShareType.DETAIL_CUT;
        if (share_media.equals(SHARE_MEDIA.WEIXIN) || share_media.equals(SHARE_MEDIA.WEIXIN_CIRCLE)) {
            if (!InstallUtil.isWeChatAppInstalled(mContext)) {
                ToastUtils.showShortToast(mContext, "您未安装微信");
            } else {
                shareNoPublic(share_media);
//                shareWxMin(share_media);
//                new ShareAction((Activity) mContext).withMedia(new UMImage(mContext, "https://mobile.umeng.com/images/pic/home/social/img-1.png"))
//                        .setPlatform(share_media).setCallback(this).share();
            }
        } else if (share_media.equals(SHARE_MEDIA.SINA)) {
            if (InstallUtil.uninstallSoftware(mContext, "com.sina.weibo")) {
                new ShareAction((Activity) mContext).withMedia(new UMImage(mContext, "https://mobile.umeng.com/images/pic/home/social/img-1.png"))
                        .setPlatform(share_media).setCallback(this).share();
            } else {
                ToastUtils.showShortToast(mContext, "您未安装新浪微博");
            }
        }
    }

    private void shareNoPublic(SHARE_MEDIA share_media){
        String address = "";
        String baseUrl = SPUtils.getInstance().getString(Constant.SP_BASE_URL, ApiRetrofit.BASE_SERVER_URL);
        if(BaseContent.baseDevUrl.equals(baseUrl)){
            address = "https://wx-test.luluxk.com/BargainShare?BargainShare=BargainShare?";
        }else if(BaseContent.baseTestUrl.equals(baseUrl)){
            address = "https://wx-test.luluxk.com/BargainShare?BargainShare=BargainShare?";
        }else if(BaseContent.basePreUrl.equals(baseUrl)){
            address = "https://wx-pre.luluxk.com/BargainShare?BargainShare=BargainShare?";
        }else {
            address = "https://wx.luluxk.com/BargainShare?BargainShare=BargainShare?";
        }
        String url = address + "?activityId=" + cutSuccessBean.getActivityId() + "?commodityId=" + cutSuccessBean.getCommodityId() + "?userId=" + MyApplication.userId
                + "?extcode=" + SPUtils.getInstance().getString(Constant.USER_INVITE_CODE);
        UMWeb web = new UMWeb(url);
        web.setTitle("快来帮我砍砍~只差你一刀了!"+cutSuccessBean.getCommodityName());//标题
        if(!TextUtils.isEmpty(cutSuccessBean.getGoodsImageUrl())){
            web.setThumb(new UMImage(mContext, cutSuccessBean.getGoodsImageUrl()));  //缩略图
        }else {
            web.setThumb(new UMImage(mContext, R.mipmap.ic_launcher));  //缩略图
        }
        web.setDescription(cutSuccessBean.getCommodityName());//描述
        new ShareAction((Activity) mContext).withMedia(web)
                .setPlatform(share_media).setCallback(this).share();
    }

    /**
     * 分享到小程序
     */
    private void shareWxMin(SHARE_MEDIA share_media) {
        UMMin umMin = new UMMin(BaseContent.baseUrl);
        //兼容低版本的网页链接
        umMin.setThumb(new UMImage(mContext, cutSuccessBean.getGoodsImageUrl()));
        // 小程序消息封面图片
        umMin.setTitle("快来帮我砍砍~只差你一刀了!"+cutSuccessBean.getCommodityName());
        // 小程序消息title
        umMin.setDescription(cutSuccessBean.getCommodityName());
        // 小程序消息描述
        umMin.setPath("pages/activity/bargain/share/main?userId="+ SPUtils.getInstance().getString(Constant.SP_USER_ID)
                +"&activityId="+cutSuccessBean.getActivityId()+"&commodityId="+cutSuccessBean.getCommodityId()+"&" + Constant.BASE_CODE_KEY
                +SPUtils.getInstance().getString(Constant.USER_INVITE_CODE));
        //小程序页面路径
        umMin.setUserName(BaseContent.WX_PRIMEVAL_ID);
        if(!ApiRetrofit.BASE_SERVER_URL.equals("https://gateway.luluxk.com")){
            //设置成预览版
            Config.setMiniPreView();
        }
        // 小程序原始id,在微信平台查询
        new ShareAction((Activity) mContext)
                .withMedia(umMin)
                .setPlatform(share_media)
                .setCallback(this).share();
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }

    public interface OnSureListener {
        void onSure(String name, String phone, String address);
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }

}
