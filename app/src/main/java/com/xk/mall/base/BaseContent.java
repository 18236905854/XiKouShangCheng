package com.xk.mall.base;

import com.xk.mall.BuildConfig;

/**
 * File descripition:一些常量
 */

public class BaseContent {
    //base Ip
    public static String baseUrl = BuildConfig.BASE_URL;//测试环境地址
//    public static String baseUrl = "https://gateway.luluxk.com";//测试环境地址
    public static String baseProductUrl = "https://gateway.luluxk.com";//生产环境地址
    public static String baseTestUrl = "https://gateway-test.luluxk.com";//测试环境地址
    public static String baseDevUrl = "http://gateway-dev.luluxk.com";//dev环境地址
    public static String basePreUrl = "https://gateway-pre.luluxk.com";//pre环境地址

    //服务器返回成功的 cdoe
    public static int basecode = 0;

    //友盟SDK的AppKey
    public static String UM_APPKEY = "5cee6bfe4ca357fcac000f84";
    //友盟SDK需要配置的channel
    public static String UM_CHANNEL = "XK_CHANNEL";
    //友盟推送需要配置的secret,Umeng Message Secret
    public static String UM_SECRET = "8bffa40fd5c6bf17db9f1cba0ec5cb47";

    //QQ分享配置的Key 待修改
    public static String UM_QQ_KEY = "1109332998";
    //QQ分享配置的SECRET
    public static String UM_QQ_SECRET = "8p5q5rMVJgwjd12a";
    //微信分享配置的key
    public static String UM_WECHAT_KEY = "wx059b60568a69e0a7";
    //微信分享配置的secret
    public static String UM_WECHAT_SECRET = "a8a3e262423f84880f6e006959e73687";
    //微博分享配置的Key
    public static String UM_SINA_KEY = "2618401123";
    //微博分享配置的secret
    public static String UM_SINA_SECRET = "5fe01e087caec22c576c52af7c315047";

    //美洽客服配置的Key
    public static String MQ_KEY = "aff6dcf8d4d1b2feb9ef302018e08756";
    //美洽客服配置的secret
    public static String MQ_SECRET = "$2a$12$sVdPwa5DBmTA56Z9W2zAWuQxrNItVKKX.sTE0E1YDwJOhJGItihPC";

    //小米推送配置
    public static String XM_KEY = "5321811387922";
    public static String XM_ID = "2882303761518113922";

    //OPPO推送配置
    public static String OPPO_KEY = "536f4d5b6ffc4cf7b3a3d949800329b3";
    public static String OPPO_SECRET = "4be62a936a724a06824d6c6566609a1f";

    //OPPO推送配置
    public static String MEIZU_ID = "123249";
    public static String MEIZU_KEY = "27ab68e8a0cd46dda8e5e11d44803f20";

    //微信小程序原始id
    public static final String WX_PRIMEVAL_ID="gh_ddff50ba8161";

}
