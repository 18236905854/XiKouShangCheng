package com.xk.mall.utils;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.BuildConfig;
import com.xk.mall.MyApplication;
import com.xk.mall.view.activity.CutMainActivity;
import com.xk.mall.view.activity.GlobalBuyerActivity;
import com.xk.mall.view.activity.GroupMainActivity;
import com.xk.mall.view.activity.ManyBuyActivity;
import com.xk.mall.view.activity.NewPersonActivity;
import com.xk.mall.view.activity.WuGMainActivity;
import com.xk.mall.view.activity.ZeroMainActivity;

/**
 * ClassName ActivityType
 * Description 活动类型变量类
 * Author 卿凯
 * Date 2019/7/10/010
 * Version V1.0
 */
public class ActivityType {
    //活动类型(1:买一赠二(吾G)，2: 全球买手, 3：0元竞拍 4:多买多折，5:砍价得红包，6:定制拼团)
    /**吾G专区(买一赠二)*/
    public static final int ACTIVITY_WUG = 1;
    /**全球买手*/
    public static final int ACTIVITY_GLOBAL_BUYER = 2;
    /**0元竞拍*/
    public static final int ACTIVITY_ZERO = 3;
    /**多买多折*/
    public static final int ACTIVITY_MANY_BUY = 4;
    /**砍价得红包(喜立得)*/
    public static final int ACTIVITY_CUT = 5;
    /**定制拼团*/
    public static final int ACTIVITY_FIGHT_GROUP = 6;
    /**新人专区*/
    public static final int ACTIVITY_NEW_PERSON = 8;

    /**
     * 根据活动类型获取活动名称
     * @param activityType 活动类型
     * @return 活动名称
     */
    public static String getNameByType(int activityType){
        String activity = "";
        if(activityType == ACTIVITY_WUG){
            activity = "吾G购";
        }else if(activityType == ACTIVITY_GLOBAL_BUYER){
            activity = "全球买手";
        }else if(activityType == ACTIVITY_ZERO){
            activity = "0元抢";
        }else if(activityType == ACTIVITY_MANY_BUY){
            activity = "多买多折";
        }else if(activityType == ACTIVITY_CUT) {
            activity = "喜立得";
        }else if(activityType == ACTIVITY_FIGHT_GROUP){
            activity = "定制拼团";
        }else if(activityType == ACTIVITY_NEW_PERSON){
            activity = "新人专区";
        }
        return activity;
    }

    /**
     * 根据活动类型跳转页面
     */
    public static void goActivityByType(Context context, int activityType){
        Intent intent = new Intent();
        if(activityType == ACTIVITY_WUG){
            intent.setClass(context, WuGMainActivity.class);
        }else if(activityType == ACTIVITY_GLOBAL_BUYER){
            intent.setClass(context, GlobalBuyerActivity.class);
        }else if(activityType == ACTIVITY_ZERO){
            intent.setClass(context, ZeroMainActivity.class);
        }else if(activityType == ACTIVITY_MANY_BUY) {
            intent.setClass(context, ManyBuyActivity.class);
        }else if(activityType == ACTIVITY_CUT){
            intent.setClass(context, CutMainActivity.class);
        }else if(activityType == ACTIVITY_FIGHT_GROUP){
            intent.setClass(context, GroupMainActivity.class);
        }else if(activityType == ACTIVITY_NEW_PERSON){
            intent.setClass(context, NewPersonActivity.class);
        }
        ActivityUtils.startActivity(intent);
    }

//    /**
//     * 获取分享地址
//     */
//    public static String getShareAddress(int activityType, String text, String goodsName, String activityGoodsId, String code){
//        String result = "";
//        if("local".equals(BuildConfig.FLAVOR)){
//            if(activityType == ActivityType.ACTIVITY_WUG){
//                result = text + " " + goodsName + " " + Constant.WUG_SHARE_LOCAL_ADDRESS + activityGoodsId +
//                        "?userId=" + MyApplication.userId + "?extcode=" + code + "?popularizeId=" + ShareType.DETAIL_WUG;
//            }else if(activityType == ActivityType.ACTIVITY_CUT){
//                result = text + " " + goodsName + " " + Constant.CUT_SHARE_LOCAL_ADDRESS + activityGoodsId +
//                        "?userId=" + MyApplication.userId + "?extcode=" + code + "?popularizeId=" + ShareType.DETAIL_CUT;
//            }else if(activityType == ActivityType.ACTIVITY_ZERO){
//                result = text + " " + goodsName + " " + Constant.ZERO_SHARE_LOCAL_ADDRESS + activityGoodsId +
//                        "?userId=" + MyApplication.userId + "?extcode=" + code + "?popularizeId=" + ShareType.DETAIL_ZERO;
//            }else if(activityType == ActivityType.ACTIVITY_GLOBAL_BUYER){
//                result = text + " " + goodsName + " " + Constant.GLOBAL_SHARE_LOCAL_ADDRESS + activityGoodsId +
//                        "?userId=" + MyApplication.userId + "?extcode=" + code + "?popularizeId=" + ShareType.DETAIL_GLOBAL_BUY;
//            }else if(activityType == ActivityType.ACTIVITY_MANY_BUY){
//                result = text + " " + goodsName + " " + Constant.MANY_SHARE_LOCAL_ADDRESS + activityGoodsId +
//                        "?userId=" + MyApplication.userId + "?extcode=" + code + "?popularizeId=" + ShareType.DETAIL_MANY_BUY;
//            }else if(activityType == ActivityType.ACTIVITY_NEW_PERSON){
//                result = text + " " + goodsName + " " + Constant.NEW_SHARE_LOCAL_ADDRESS + activityGoodsId +
//                        "?userId=" + MyApplication.userId + "?extcode=" + code + "?popularizeId=" + ShareType.DETAIL_NEW_PERSON;
//            }
//        }else {
//            if(activityType == ActivityType.ACTIVITY_WUG){
//                result = Constant.WUG_SHARE_PRODUCT_ADDRESS;
//                result = text + " " + goodsName + " " + Constant.WUG_SHARE_PRODUCT_ADDRESS + activityGoodsId +
//                        "?userId=" + MyApplication.userId + "?extcode=" + code + "?popularizeId=" + ShareType.DETAIL_WUG;
//            }else if(activityType == ActivityType.ACTIVITY_CUT){
//                result = text + " " + goodsName + " " + Constant.CUT_SHARE_PRODUCT_ADDRESS + activityGoodsId +
//                        "?userId=" + MyApplication.userId + "?extcode=" + code + "?popularizeId=" + ShareType.DETAIL_CUT;
//            }else if(activityType == ActivityType.ACTIVITY_ZERO){
//                result = Constant.ZERO_SHARE_PRODUCT_ADDRESS;
//                result = text + " " + goodsName + " " + Constant.ZERO_SHARE_PRODUCT_ADDRESS + activityGoodsId +
//                        "?userId=" + MyApplication.userId + "?extcode=" + code + "?popularizeId=" + ShareType.DETAIL_ZERO;
//            }else if(activityType == ActivityType.ACTIVITY_GLOBAL_BUYER){
//                result = text + " " + goodsName + " " + Constant.GLOBAL_SHARE_PRODUCT_ADDRESS + activityGoodsId +
//                        "?userId=" + MyApplication.userId + "?extcode=" + code + "?popularizeId=" + ShareType.DETAIL_GLOBAL_BUY;
//            }else if(activityType == ActivityType.ACTIVITY_MANY_BUY){
//                result = text + " " + goodsName + " " + Constant.MANY_SHARE_PRODUCT_ADDRESS + activityGoodsId +
//                        "?userId=" + MyApplication.userId + "?extcode=" + code + "?popularizeId=" + ShareType.DETAIL_MANY_BUY;
//            }else if(activityType == ActivityType.ACTIVITY_NEW_PERSON){
//                result = text + " " + goodsName + " " + Constant.NEW_SHARE_PRODUCT_ADDRESS + activityGoodsId +
//                        "?userId=" + MyApplication.userId + "?extcode=" + code + "?popularizeId=" + ShareType.DETAIL_NEW_PERSON;
//            }
//        }
//        return result;
//    }
}
