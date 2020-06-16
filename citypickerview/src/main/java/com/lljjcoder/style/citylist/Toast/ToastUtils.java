package com.lljjcoder.style.citylist.Toast;

import android.content.Context;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * author：admin on 2016/5/4 10:49
 * email：fangkaijin@gmail.com
 */
public class ToastUtils {

    private static WeakReference<AlarmDailog> weakReference;

    public static
    void showShortToast (Context context, String showMsg ) {
        if ( null != weakReference ) {
            weakReference = null;
        }
        weakReference = new WeakReference<>(new AlarmDailog(context));
        AlarmDailog alarmDialog = weakReference.get();
        alarmDialog.setShowText(showMsg);
        alarmDialog.setDuration(Toast.LENGTH_SHORT);
        alarmDialog.show();

    }

    public static void showLongToast(Context context, String showMsg)
    {
        if (null != weakReference)
        {
            weakReference = null;
        }
        weakReference = new WeakReference<>(new AlarmDailog(context));
        AlarmDailog alarmDialog = new AlarmDailog(context);
        alarmDialog.setShowText(showMsg);
        alarmDialog.show();
    }
}
