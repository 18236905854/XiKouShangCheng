package com.xk.mall.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.ActivityUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.umeng.message.UTrack;
import com.umeng.message.entity.UMessage;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.activity.WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class NotificationBroadcast extends BroadcastReceiver {
    private static final String TAG = "NotificationBroadcast";

    public static final String EXTRA_KEY_ACTION = "ACTION";
    public static final String EXTRA_KEY_MSG = "MSG";
    public static final int ACTION_CLICK = 10;
    public static final int ACTION_DISMISS = 11;
    public static final int EXTRA_ACTION_NOT_EXIST = -1;

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(EXTRA_KEY_MSG);
        int action = intent.getIntExtra(EXTRA_KEY_ACTION,
                EXTRA_ACTION_NOT_EXIST);
        try {
            UMessage msg = (UMessage) new UMessage(new JSONObject(message));

            switch (action) {
                case ACTION_DISMISS:
                    Log.i(TAG, "dismiss notification");
                    UTrack.getInstance(context).setClearPrevMessage(true);
                    UTrack.getInstance(context).trackMsgDismissed(msg);
                    break;
                case ACTION_CLICK://点击
                    Log.i(TAG, "click notification");
                    if(null != msg.url && !TextUtils.isEmpty(msg.url)){//url不为空
                        //打开URL
                        Intent intentUrl = new Intent(context, WebViewActivity.class);
                        intentUrl.putExtra(Constant.INTENT_URL, msg.url);
                        ActivityUtils.startActivity(intentUrl);
                    }else if(null != msg.custom && !TextUtils.isEmpty(msg.custom) && TextUtils.isDigitsOnly(msg.custom)){
                        int activityType = Integer.parseInt(msg.custom);
                        ActivityType.goActivityByType(context, activityType);
                    }

                    UTrack.getInstance(context).setClearPrevMessage(true);
                    MyNotificationService.oldMessage = null;
                    UTrack.getInstance(context).trackMsgClick(msg);
                    break;
            }
            //
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
