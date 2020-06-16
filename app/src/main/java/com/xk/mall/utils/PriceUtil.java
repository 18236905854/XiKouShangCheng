package com.xk.mall.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.Gravity;

import com.blankj.utilcode.util.ToastUtils;

import java.text.DecimalFormat;

/**
 * ClassName PriceUtil
 * Description 价格处理工具类
 * Author 卿凯
 * Date 2019/7/11/011
 * Version V1.0
 */
public class PriceUtil {
    /**
     * 将所有的价格除100操作
     */
    public static String dividePrice(int price){
//        double result = 0;
        if(price != 0){
            double num = price / 100.0;
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数 ， 其实这里还可以这样写 DecimalFormat("0.00%");   这样就不用在最后输出时还要加。
            String s = df.format(num);
            return s;
        }
        return "0.00";
    }

    /**
     * 将所有的优惠券除100操作
     */
    public static String divideCoupon(int price){
        if(price != 0){
            double result = price / 100.0;
            result = Math.ceil(result);
            return String.valueOf(Double.valueOf(result).intValue());
        }
        return "0";
    }

    /**
     * 将所有的价格除100操作
     */
    public static String divideDoublePrice(double price){
        if(price != 0){
            double num = price / 100.0;
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数 ， 其实这里还可以这样写 DecimalFormat("0.00%");   这样就不用在最后输出时还要加。
            String s = df.format(num);
            return s;
        }
        return "0.00";
    }

    /**
     * 复制操作
     * @param context
     * @param copyText
     */
    public static void copyOperation(Context context,String copyText){
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(copyText);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.showShort("复制成功");
    }

    /**
     * 复制物流单号
     */
    public static void copyOperationLogistics(Context context,String copyText){
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(copyText);
    }

    /**
     * 将double转为字符串
     * @param discount
     * @return
     */
    public static String changeDoubleToStr(double discount){
        String bottomDiscount = String.valueOf(discount);
        String result = "";
        if(bottomDiscount.contains(".")){
            int pointIndex=bottomDiscount.indexOf(".");
            String startString=bottomDiscount.substring(0,pointIndex);
            String endString = bottomDiscount.substring(pointIndex+1);
            if(endString.equals("0")){
                result = startString;
            }else{
                result = bottomDiscount;
            }
        }else{
            result = bottomDiscount;
        }
        return result;
    }
}
