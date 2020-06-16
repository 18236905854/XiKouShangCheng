package com.xk.mall.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 防止内存泄露
 */

public class MyHandler extends Handler {
    WeakReference<Activity> mWeakReference;

    public MyHandler(Activity activity) {
        mWeakReference = new WeakReference<Activity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Activity activity = mWeakReference.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }

    }
}