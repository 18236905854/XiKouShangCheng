package com.xk.mall.utils;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.meiqia.core.MQManager;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.imageloader.MQImage;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.umeng.analytics.MobclickAgent;
import com.xk.mall.BuildConfig;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseContent;

import java.util.HashMap;

import cc.shinichi.library.tool.ui.ToastUtil;

/**
 * ClassName MeiQiaUtil
 * Description 启动美洽客服工具类
 * Author 卿凯
 * Date 2019/7/25/025
 * Version V1.0
 */
public class MeiQiaUtil {

    @LoginFilter
    public static void initMeiqiaSDK(Context mContext) {
        try {
            MQManager.setDebugMode(BuildConfig.DEBUG);

            MQConfig.init(mContext, BaseContent.MQ_KEY, new OnInitCallback() {
                @Override
                public void onSuccess(String clientId) {
                    //跳转页面
                    HashMap<String, String> clientInfo = new HashMap<>();
                    clientInfo.put("avatar", SPUtils.getInstance().getString(Constant.HEAD_URL));
                    //跳转页面
                    MQImage.setImageLoader(new MQGlideImageLoaderFour());
                    Intent intent = new MQIntentBuilder(mContext).setClientInfo(clientInfo)
                            .setCustomizedId(MyApplication.userId).build();
                    mContext.startActivity(intent);
                }

                @Override
                public void onFailure(int code, String message) {
                    //启动失败
                    ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                    ToastUtils.showShort("启动客服失败，请重试");
                    if(!XiKouUtils.isNullOrEmpty(message)){
                        MobclickAgent.reportError(mContext, message);
                    }
                }
            });
            //改成跟美洽SDK一样
            MQConfig.isShowClientAvatar = true;
            MQConfig.ui.backArrowIconResId = R.drawable.ic_back;
        }catch (Exception e){
            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
            ToastUtils.showShort("启动客服失败，请重试");
        }
    }
}
