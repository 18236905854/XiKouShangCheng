package com.xk.mall.utils;

import android.util.Log;

/**
 * 字符串 公共类
 */
public class StringUtils {
    private static final String TAG = "StringUtils";
    public static String  replaceString(String text){
        String resul="";

        if(text.contains("")){

        }
        return  resul;
    }
    //2019年08月---转 2019-08
    public static String timeConvert(String time){
        String result="";
        result=time.replace("年","-");
        result=result.replace("月","");
        Log.e(TAG, "timeConvert: "+result );
        return  result;
    }
}
