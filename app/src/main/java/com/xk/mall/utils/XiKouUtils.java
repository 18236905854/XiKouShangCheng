package com.xk.mall.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.gen.AddressServerBeanDao;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.view.activity.CutGoodsDetailActivity;
import com.xk.mall.view.activity.CutMainActivity;
import com.xk.mall.view.activity.GlobalBuyerActivity;
import com.xk.mall.view.activity.GlobalBuyerGoodsDetailActivity;
import com.xk.mall.view.activity.GroupGoodsDetailActivity;
import com.xk.mall.view.activity.GroupMainActivity;
import com.xk.mall.view.activity.LoginActivity;
import com.xk.mall.view.activity.ManyBuyActivity;
import com.xk.mall.view.activity.ManyGoodsDetailActivity;
import com.xk.mall.view.activity.WebViewActivity;
import com.xk.mall.view.activity.WuGGoodsDetailActivity;
import com.xk.mall.view.activity.WuGMainActivity;
import com.xk.mall.view.activity.ZeroGoodsDetailActivity;
import com.xk.mall.view.activity.ZeroMainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

/**
 * 喜扣 公共类
 */
public class XiKouUtils {
    /**
     * 根据省 id 市 id 区 id 获取地址
     * @param mContext
     * @return
     */
    public static  String getAddress(Context mContext, AddressBean addressBean){
        StringBuilder stringBuilder = new StringBuilder();
        if(addressBean != null){
            if(!XiKouUtils.isNullOrEmpty(addressBean.provinceName)){
                stringBuilder.append(addressBean.provinceName);
            }
            if(!XiKouUtils.isNullOrEmpty(addressBean.cityName)){
                stringBuilder.append(addressBean.cityName);
            }
            if(!XiKouUtils.isNullOrEmpty(addressBean.areaName)){
                stringBuilder.append(addressBean.areaName);
            }
            if(!XiKouUtils.isNullOrEmpty(addressBean.address)){
                stringBuilder.append(addressBean.address);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 根据省 id 市 id 区 id 获取地址
     * @param mContext
     * @return
     */
    public static  String getAddressWithNoAddress(Context mContext, AddressBean addressBean){
        StringBuilder stringBuilder = new StringBuilder();
        if(addressBean != null){
            if(!XiKouUtils.isNullOrEmpty(addressBean.provinceName)){
                stringBuilder.append(addressBean.provinceName);
            }
            if(!XiKouUtils.isNullOrEmpty(addressBean.cityName)){
                stringBuilder.append(addressBean.cityName);
            }
            if(!XiKouUtils.isNullOrEmpty(addressBean.areaName)){
                stringBuilder.append(addressBean.areaName);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 轮播图 解析公共类
     */
    public static void parseBannner(Context mContext,String skipType,String targetParams,String webUrl){
            if (skipType.equals("goods") && !TextUtils.isEmpty(targetParams)) {//商品
                try {
                    JSONObject params = new JSONObject(targetParams);
                    JSONObject object = params.getJSONObject("params");
                    if(object != null){
                        String activityGoodsId = object.getString("activityGoodsId");
                        if(TextUtils.isEmpty(activityGoodsId)){
                            return;
                        }
                        int activityType = object.getInt("activityType");
                        if (activityType == ActivityType.ACTIVITY_MANY_BUY) {
                            Intent intent = new Intent(mContext, ManyGoodsDetailActivity.class);
                            intent.putExtra(ManyGoodsDetailActivity.ACTIVITY_GOODS_ID, activityGoodsId);
                            ActivityUtils.startActivity(intent);
                        }else if(activityType == ActivityType.ACTIVITY_GLOBAL_BUYER){
                            Intent intent = new Intent(mContext, GlobalBuyerGoodsDetailActivity.class);
                            intent.putExtra(GlobalBuyerGoodsDetailActivity.ACTIVITY_GOODS_ID, activityGoodsId);
                            ActivityUtils.startActivity(intent);
                        }else if(activityType == ActivityType.ACTIVITY_FIGHT_GROUP){
                            Intent intent = new Intent(mContext, GroupGoodsDetailActivity.class);
                            intent.putExtra(GroupGoodsDetailActivity.ACTIVITY_GOODS_ID, activityGoodsId);
                            ActivityUtils.startActivity(intent);
                        }else if(activityType == ActivityType.ACTIVITY_WUG){
                            Intent intent = new Intent(mContext, WuGGoodsDetailActivity.class);
                            intent.putExtra(WuGGoodsDetailActivity.ACTIVITY_GOODS_ID, activityGoodsId);
                            ActivityUtils.startActivity(intent);
                        }else if(activityType == ActivityType.ACTIVITY_ZERO){
                            Intent intent = new Intent(mContext, ZeroGoodsDetailActivity.class);
                            intent.putExtra(ZeroGoodsDetailActivity.ACTIVITY_GOODS_ID, activityGoodsId);
                            ActivityUtils.startActivity(intent);
                        }else if(activityType == ActivityType.ACTIVITY_CUT){
                            Intent intent = new Intent(mContext, CutGoodsDetailActivity.class);
                            intent.putExtra(CutGoodsDetailActivity.ACTIVITY_GOODS_ID, activityGoodsId);
                            ActivityUtils.startActivity(intent);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if (skipType.equals("activity") && !TextUtils.isEmpty(targetParams)) {//活动
                try {
                    Intent typeIntent=new Intent();
                    JSONObject parms = new JSONObject(targetParams);
                    JSONObject object = parms.getJSONObject("params");
                    if(object != null){
                        int activityType = object.getInt("activityType");
                        if(activityType==ActivityType.ACTIVITY_WUG){
                            typeIntent.setClass(mContext, WuGMainActivity.class);
                        }else if(activityType==ActivityType.ACTIVITY_GLOBAL_BUYER){
                            typeIntent.setClass(mContext, GlobalBuyerActivity.class);
                        }else if(activityType==ActivityType.ACTIVITY_ZERO){
                            typeIntent.setClass(mContext, ZeroMainActivity.class);
                        }else if(activityType==ActivityType.ACTIVITY_MANY_BUY){
                            typeIntent.setClass(mContext, ManyBuyActivity.class);
                        }else if(activityType==ActivityType.ACTIVITY_CUT){
                            typeIntent.setClass(mContext, CutMainActivity.class);
                        }else if(activityType == ActivityType.ACTIVITY_FIGHT_GROUP){
                            typeIntent.setClass(mContext, GroupMainActivity.class);
                        }
                        mContext.startActivity(typeIntent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if (skipType.equals("url")) {//链接
                if(!TextUtils.isEmpty(webUrl) && webUrl.startsWith("http")){
                    Intent webIntent=new Intent(mContext, WebViewActivity.class);
                    webIntent.putExtra(Constant.INTENT_URL,webUrl);
                    mContext.startActivity(webIntent);
                }else if(!TextUtils.isEmpty(webUrl) && webUrl.startsWith("xk")){
                    if(webUrl.contains("user") && !SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)){
                        ActivityUtils.startActivity(LoginActivity.class);
                    }else {
                        Intent it = new Intent();
                        it.setAction(Intent.ACTION_VIEW);
                        it.setData(Uri.parse(webUrl));
                        mContext.startActivity(it);
                    }
                }
            }
        }


    /**
     * 活动屏幕信息
     */
    private static WindowManager wm;
    /**
     * 获取真实屏幕高度
     *
     * @return
     */
    public static int getRealHeight() {
        if (null == wm) {
            wm = (WindowManager)
                    MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }


    /**
     * 是否有导航栏
      * @param context
     * @return
     */
    public static boolean isShowNavBar(Context context) {
        if (null == context) {
            return false;
        }
        /**
         * 获取应用区域高度
         */
        Rect outRect1 = new Rect();
        try {
            ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return false;
        }
        int activityHeight = outRect1.height();
        /**
         * 获取状态栏高度
         */
        int statuBarHeight = getStatusBarHeight();
        /**
         * 屏幕物理高度 减去 状态栏高度
         */
        int remainHeight = getRealHeight() - statuBarHeight;
        /**
         * 剩余高度跟应用区域高度相等 说明导航栏没有显示 否则相反
         */
        if (activityHeight == remainHeight) {
            return false;
        } else {
            return true;
        }

    }


    /**
     * 判断虚拟导航栏是否显示
     *
     * @param context 上下文对象
     * @param window  当前窗口
     * @return true(显示虚拟导航栏)，false(不显示或不支持虚拟导航栏)
     */
    public static boolean checkNavigationBarShow(@NonNull Context context, @NonNull Window window) {
        boolean show;
        Display display = window.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getRealSize(point);

        View decorView = window.getDecorView();
        Configuration conf = context.getResources().getConfiguration();
        if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation) {
            View contentView = decorView.findViewById(android.R.id.content);
            show = (point.x != contentView.getWidth());
        } else {
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            show = (rect.bottom != point.y);
        }
        return show;
    }


    /**
     * 判断字符串是否为空或者null
     * @param content
     * @return
     */
    public static boolean isNullOrEmpty(String content){
        return content == null || TextUtils.isEmpty(content);
    }

    /**
     * 根据类型获得寄卖方式
     * @param type 寄卖类型
     */
    public static String getSellTypeByType(int type){
        String result = "";
        if(type == 2){
            result = "支持商品寄卖到吾G";
        }else if(type == 1){
            result = "支持商品分享给好友";
        }else {
            result = "支持所有寄卖方式";
        }
        return result;
    }

    /**
     * 格式化吾G限额字符串
     * @param time
     * @return
     */
    public static String formartTime(String time){
        if(isNullOrEmpty(time)){
            return "";
        }else{
            if(time.contains(" ")){
                String[] result = time.split(" ");
                return result[0];
            }else {
                return time;
            }
        }
    }

    /**
     * 格式化额度
     * @param limitAmount
     * @return
     */
    public static String formatLimit(int limitAmount){
        String result = "";
        if(limitAmount < 1000000){
            result = PriceUtil.dividePrice(limitAmount);
        }else {
            //返回多少万元
            result = PriceUtil.dividePrice(limitAmount / 10000) + "万";
        }
        return result;
    }

    /**
     * 格式化商家电话号码
     * @param phone
     * @return
     */
    public static String formatMerchantPhone(String phone){
        String merchantPhone = "";
        if(isNullOrEmpty(phone)){
            return merchantPhone;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < phone.length(); i++){
            if(i == 3 || i == 7){
                stringBuilder.append("-");
                stringBuilder.append(phone.charAt(i));
            }else {
                stringBuilder.append(phone.charAt(i));
            }
        }
        return stringBuilder.toString();
    }

}
