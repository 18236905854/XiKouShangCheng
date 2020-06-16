package com.xk.mall.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.umeng.message.UTrack;
import com.umeng.message.entity.UMessage;
import com.xk.mall.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * 自定义通知服务
 */
public class MyNotificationService extends Service{
    private static final String TAG = "MyNotificationService";
    public static UMessage oldMessage = null;

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: ");
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        String message = intent.getStringExtra("UmengMsg");
        try {
            UMessage msg = new UMessage(new JSONObject(message));
            if (oldMessage != null) {
                UTrack.getInstance(getApplicationContext()).setClearPrevMessage(true);
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(oldMessage);
            }
            showNotification(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    private void showNotification(UMessage msg) {
        int id = new Random(System.nanoTime()).nextInt();
        oldMessage = msg;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle(msg.title)
                .setContentText(msg.text)
                .setTicker(msg.ticker)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            //builder的channelId需和下面channel的保持一致；
            mBuilder.setChannelId("channel_id");
            NotificationChannel channel = new
                    NotificationChannel("channel_id","channel_name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setBypassDnd(true);//设置可以绕过请勿打扰模式
            channel.canBypassDnd();//可否绕过请勿打扰模式
            //锁屏显示通知
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            channel.shouldShowLights();//是否会闪光
            channel.enableLights(true);//闪光
            //指定闪光时的灯光颜色，为了兼容低版本在上面builder上通过setLights方法设置了
            //channel.setLightColor(Color.RED);
            channel.canShowBadge();//桌面launcher消息角标
            channel.enableVibration(true);//是否允许震动
            //震动模式，第一次100ms，第二次100ms，第三次200ms，为了兼容低版本在上面builder上设置了
            //channel.setVibrationPattern(new long[]{100,100,200});
            channel.getAudioAttributes();//获取系统通知响铃声音的配置
            channel.getGroup();//获取通知渠道组
            //绑定通知渠道
            manager.createNotificationChannel(channel);
        }

        Notification notification = mBuilder.build();
        PendingIntent clickPendingIntent = getClickPendingIntent(this, msg);
        PendingIntent dismissPendingIntent = getDismissPendingIntent(this, msg);
        notification.deleteIntent = dismissPendingIntent;
        notification.contentIntent = clickPendingIntent;
        manager.notify(id, notification);
    }

    public PendingIntent getClickPendingIntent(Context context, UMessage msg) {
        Intent clickIntent = new Intent();
        clickIntent.setClass(context, NotificationBroadcast.class);
        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_MSG,
                msg.getRaw().toString());
        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_ACTION,
                NotificationBroadcast.ACTION_CLICK);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,
                (int) (System.currentTimeMillis()),
                clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        return clickPendingIntent;
    }


    public PendingIntent getDismissPendingIntent(Context context, UMessage msg) {
        Intent deleteIntent = new Intent();
        deleteIntent.setClass(context, NotificationBroadcast.class);
        deleteIntent.putExtra(NotificationBroadcast.EXTRA_KEY_MSG,
                msg.getRaw().toString());
        deleteIntent.putExtra(
                NotificationBroadcast.EXTRA_KEY_ACTION,
                NotificationBroadcast.ACTION_DISMISS);
        PendingIntent deletePendingIntent = PendingIntent.getBroadcast(context,
                (int) (System.currentTimeMillis() + 1),
                deleteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return deletePendingIntent;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
