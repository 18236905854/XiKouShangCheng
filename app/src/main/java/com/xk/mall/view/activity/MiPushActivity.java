package com.xk.mall.view.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.umeng.message.UmengNotifyClickActivity;
import com.xk.mall.R;
import com.xk.mall.service.MyNotificationService;

import org.android.agoo.common.AgooConstants;

/**
 * ClassName MiPushActivity
 * Description 友盟推送厂商弹窗功能页面
 * Author 卿凯
 * Date 2019/8/13/013
 * Version V1.0
 */
public class MiPushActivity extends UmengNotifyClickActivity {
    private static String TAG = MiPushActivity.class.getName();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mipush);
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Log.i(TAG, body);
        Context context = MiPushActivity.this;
        Intent intent1 = new Intent();
        intent1.setClass(context, MyNotificationService.class);
        intent1.putExtra("UmengMsg", body);
        context.startService(intent1);
    }
}
