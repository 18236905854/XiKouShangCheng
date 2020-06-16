package com.xk.mall.utils;

import java.util.Calendar;

/**
 * ClassName OneClick
 * Description 判断下两次点击的时间
 * Author 卿凯
 * Date 2019/6/12/012
 * Version V1.0
 */
public class OneClick {
    private String methodName;
    private static final int CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    public OneClick(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean check() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return false;
        } else {
            return true;
        }
    }
}
