package com.xk.mall;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.RomUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.meiqia.core.MQManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.xk.mall.api.ApiRetrofit;
import com.xk.mall.base.BaseContent;
import com.xk.mall.core.ILogin;
import com.xk.mall.core.LoginSDK;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.model.entity.PaySwitchBean;
import com.xk.mall.service.UmengNotificationService;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.CrashHandler;
import com.xk.mall.view.activity.LoginActivity;
import com.xk.mall.view.activity.MainActivity;

import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

import java.util.ArrayList;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * File descripition:
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks{
    private static final String TAG = "MyApplication";
    private static Context mContext;
    public static String token = "";//用户登录成功的token
    public static String refreshToken = "";//需要刷新token时的refreshToken
    public static String userId = "";
    public static String imei = "";
    public static PaySwitchBean switchBean;
    public static int shareType = 0;//分享类型,分享成功回调会用到
    public static boolean isPaySuccess = false;//是否支付成功，当用户支付成功之后修改变量，重新获取用户信息
    private ArrayList<Activity> allActivities;
    private static MyApplication instance ;
    public static double longitude, latitude;//经纬度
    public static double rateOne;//最高折扣
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//       CrashUtil.getInstance().init(mContext);
        Utils.init(this);
        CrashHandler.getInstance().init(this);
        token = SPUtils.getInstance().getString(Constant.SP_TOKEN);
        Logger.e("token=" + MyApplication.token);
        refreshToken = SPUtils.getInstance().getString(Constant.SP_REFRESH_TOKEN);
        userId = SPUtils.getInstance().getString(Constant.SP_USER_ID);
        if(!"product".equals(BuildConfig.FLAVOR)){
            String baseUrl = SPUtils.getInstance().getString(Constant.SP_BASE_URL);
            if(null == baseUrl || TextUtils.isEmpty(baseUrl)){
                baseUrl = BaseContent.baseUrl;
            }
            ApiRetrofit.BASE_SERVER_URL = baseUrl;
        }
        initUM();
        initLogger();
        initDB();
        //扫一扫
        BGASwipeBackHelper.init(this, null);
        LoginSDK.getInstance().init(this,iLogin);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //异常处理，使用接口将数据上传到服务端
            }
        });
    }

    ILogin iLogin = new ILogin() {
        @Override
        public void login(Context applicationContext, int userDefine) {
            switch (userDefine) {
                case 0:
                    Intent intent = new Intent(applicationContext, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case 1:
                    Toast.makeText(applicationContext, "您还没有登录，请登陆后执行", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(applicationContext, "执行失败，因为您还没有登录！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public boolean isLogin(Context applicationContext) {
            return SPUtils.getInstance().getBoolean(Constant.IS_LOGIN,false);
        }

        @Override
        public void clearLoginStatus(Context applicationContext) {
            SPUtils.getInstance().put(Constant.IS_LOGIN,false);
        }
    };

    public synchronized static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new ArrayList<>();
        }
        allActivities.add(act);
    }

    public void closeActivity(){
        try {
            for (Activity activity : allActivities) {

                Log.e("ffw", "closeActivity: "+activity.getLocalClassName());
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeActivityNoPayOrderActivty(){
        try {
            for (Activity activity : allActivities) {
                if (activity != null && !activity.getClass().getName().equals("com.xk.mall.view.activity.PayOrderActivity"))
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    /**
     * 初始化友盟SDK
     */
    private void initUM() {
        UMConfigure.init(this, BaseContent.UM_APPKEY, BaseContent.UM_CHANNEL,
                UMConfigure.DEVICE_TYPE_PHONE, BaseContent.UM_SECRET);
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        PlatformConfig.setQQZone(BaseContent.UM_QQ_KEY, BaseContent.UM_QQ_SECRET);
        PlatformConfig.setWeixin(BaseContent.UM_WECHAT_KEY, BaseContent.UM_WECHAT_SECRET);
        //新浪微博第三个参数为友盟sdk内部写死的网址
        PlatformConfig.setSinaWeibo(BaseContent.UM_SINA_KEY, BaseContent.UM_SINA_SECRET, "http://sns.whalecloud.com");
        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.addAlias("xk", userId, (b, s12) -> {
            Logger.e("添加成功");
        });
        pushAgent.setPushIntentServiceClass(UmengNotificationService.class);
        pushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {
                Logger.d("deviceToken" + s);
            }

            @Override
            public void onFailure(String s, String s1) {
                Logger.d(s, s1);
            }
        });
        if(RomUtils.isXiaomi()){
            MiPushRegistar.register(this, BaseContent.XM_ID, BaseContent.XM_KEY);
        }
        if(RomUtils.isOppo()){
            //OPPO通道，参数1为app key，参数2为app secret
            OppoRegister.register(this, BaseContent.OPPO_KEY, BaseContent.OPPO_SECRET);
        }
        if(RomUtils.isMeizu()){
            MeizuRegister.register(this, BaseContent.MEIZU_ID, BaseContent.MEIZU_KEY);
        }
    }

    /**
     * 低内存的时候执行
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }


    /**
     * HOME键退出应用程序
     * 程序在内存清理的时候执行
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN){
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    /**
     * 分包方法
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化数据库
     */
    private void initDB() {
        MySQLiteOpenHelper.getDaoMaster(this);
    }

    public static Context getContext() {
        return mContext;
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 是否显示线程信息 默认显示 上图Thread Infrom的位置
                .methodCount(0)         // 展示方法的行数 默认是2  上图Method的行数
                .methodOffset(7)        // 内部方法调用向上偏移的行数 默认是0
//                .logStrategy(customLog) // 改变log打印的策略一种是写本地，一种是logcat显示 默认是后者（当然也可以自己定义）
                .tag("My custom tag")   // 自定义全局tag 默认：PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;//当测试的时候打开log
            }
        });
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if(activity instanceof MainActivity){
            try {
                MQManager.getInstance(activity).closeMeiqiaService();
            }catch (Exception e){

            }
        }
    }

    /**
     * 我们需要确保至少对主进程跟patch进程初始化 TinkerPatch
     */
//    private void initTinkerPatch() {
//        // 我们可以从这里获得Tinker加载过程的信息
//        if (BuildConfig.TINKER_ENABLE) {
//            tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
//            // 初始化TinkerPatch SDK
//            TinkerPatch.init(
//                    tinkerApplicationLike
////                new TinkerPatch.Builder(tinkerApplicationLike)
////                    .requestLoader(new OkHttp3Loader())
////                    .build()
//            )
//                    .reflectPatchLibrary()
//                    .setPatchRollbackOnScreenOff(true)
//                    .setPatchRestartOnSrceenOff(true)
//                    .setFetchPatchIntervalByHours(3)
//            ;
//            // 获取当前的补丁版本
//            Log.d(TAG, "Current patch version is " + TinkerPatch.with().getPatchVersion());
//
//            // fetchPatchUpdateAndPollWithInterval 与 fetchPatchUpdate(false)
//            // 不同的是，会通过handler的方式去轮询
//            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
//        }
//    }
}
