package com.xk.mall.view.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;
import com.xk.mall.R;
import com.xk.mall.base.BaseContent;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.utils.InstallUtil;

/**
 * ClassName DialogShare
 * Description 分享对话框
 * Author 卿凯
 * Date 2019/6/28/028
 * Version V1.0
 */
public class DialogSellShare extends BottomSheetDialog implements View.OnClickListener, UMShareListener {

    private String title = "";//分享的标题
    private String content = "";//分享的内容
    private String imgUrl = "";//分享的图片地址
    private String url = "";//分享的跳转地址
    private ShareBean shareBean;
    private Context mContext;
    private TextView tvTips;//提示信息

    private boolean isShowTip;//是否显示提示

    public boolean isShowTip() {
        return isShowTip;
    }

    public void setShowTip(boolean showTip) {
        isShowTip = showTip;
        if(isShowTip){
            tvTips.setVisibility(View.VISIBLE);
        }else {
            tvTips.setVisibility(View.GONE);
        }
    }

    public DialogSellShare(@NonNull Context context) {
        super(context);
        this.mContext = context;
        setContentView(R.layout.dialog_sell_share);

        ImageView ivClose = findViewById(R.id.iv_share_close);
        ivClose.setOnClickListener(v -> {
            dismiss();
        });
        tvTips = findViewById(R.id.tv_sell_share_tip);
        LinearLayout llWeixin = findViewById(R.id.ll_share_cut_wechat);
        LinearLayout llCircle = findViewById(R.id.ll_share_cut_circle);
        llWeixin.setOnClickListener(this);
        llCircle.setOnClickListener(this);
    }

    public DialogSellShare(@NonNull Context context, ShareBean shareBean) {
        super(context);
        this.mContext = context;
        this.shareBean = shareBean;
        this.title = shareBean.getTitle();
        this.content = shareBean.getContent();
        this.imgUrl = shareBean.getImageUrl();
        this.url = shareBean.getParams().getUrl();
        setContentView(R.layout.dialog_sell_share);
        tvTips = findViewById(R.id.tv_sell_share_tip);
        ImageView ivClose = findViewById(R.id.iv_share_close);
        ivClose.setOnClickListener(v -> {
            dismiss();
        });

        LinearLayout llWeixin = findViewById(R.id.ll_share_cut_wechat);
        LinearLayout llCircle = findViewById(R.id.ll_share_cut_circle);
        llWeixin.setOnClickListener(this);
        llCircle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_share_cut_wechat:
                sharePlatform(SHARE_MEDIA.WEIXIN);
                dismiss();
                break;

            case R.id.ll_share_cut_circle:
                sharePlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                dismiss();
                break;
        }
    }

    /**
     * 分享到各个平台
     */
    private void sharePlatform(SHARE_MEDIA share_media){
        if(share_media.equals(SHARE_MEDIA.WEIXIN) || share_media.equals(SHARE_MEDIA.WEIXIN_CIRCLE)){
            if(!InstallUtil.isWeChatAppInstalled(mContext)){
                ToastUtils.showShortToast(mContext, "您未安装微信");
            }else {
                if(shareBean != null && shareBean.getIsSmallRoutine() == 1){
                    //兼容低版本的网页链接
                    UMMin umMin = new UMMin(BaseContent.baseUrl);
                    // 小程序消息封面图片
                    if(!TextUtils.isEmpty(imgUrl)){
                        umMin.setThumb(new UMImage(mContext, imgUrl));  //缩略图
                    }else {
                        umMin.setThumb(new UMImage(mContext, R.mipmap.ic_launcher));  //缩略图
                    }
                    // 小程序消息title
                    umMin.setTitle(title);
                    // 小程序消息描述
                    umMin.setDescription(content);
                    //小程序页面路径
                    umMin.setPath(shareBean.getAppletUrl());
                    // 小程序原始id,在微信平台查询
                    umMin.setUserName(shareBean.getAppletId());
                    new ShareAction((Activity) mContext)
                            .withMedia(umMin)
                            .setPlatform(share_media)
                            .setCallback(this).share();
                }else {
                    UMWeb web = new UMWeb(url);
                    web.setTitle(title);//标题
                    if(!TextUtils.isEmpty(imgUrl)){
                        web.setThumb(new UMImage(mContext, imgUrl));  //缩略图
                    }else {
                        web.setThumb(new UMImage(mContext, R.mipmap.ic_launcher));  //缩略图
                    }
                    web.setDescription(content);//描述
                    new ShareAction((Activity) mContext).withMedia(web)
                            .setPlatform(share_media).setCallback(this).share();
                }
            }
        }else if(share_media.equals(SHARE_MEDIA.SINA)){
            if(InstallUtil.uninstallSoftware(mContext, "com.sina.weibo")){
                UMWeb web = new UMWeb(url);
                web.setTitle(title);//标题
                if(!TextUtils.isEmpty(imgUrl)){
                    web.setThumb(new UMImage(mContext, imgUrl));  //缩略图
                }else {
                    web.setThumb(new UMImage(mContext, R.mipmap.ic_launcher));  //缩略图
                }
                web.setDescription(content);//描述
                new ShareAction((Activity) mContext).withMedia(web)
                        .setPlatform(share_media).setCallback(this).share();
            }else {
                ToastUtils.showShortToast(mContext, "您未安装新浪微博");
            }
        }
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

    /**
     * 海报的点击接口
     */
    public interface OnPageClickListener {
        void onClick();
    }
}
