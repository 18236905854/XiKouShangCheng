package com.xk.mall.view.widget;

import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * @ClassName: UIHandler
 * @Description: 弱引用Handler
 * @Author: 卿凯
 * @Date: 2019/11/21/021 17:49
 * @Version: 1.0
 */
public class UIHandler<T> extends Handler {
    public WeakReference<T> weakReference;

    public UIHandler(T activity){
        weakReference = new WeakReference<T>(activity);
    }

    public T getRef(){
        return weakReference == null ? weakReference.get() : null;
    }

}
