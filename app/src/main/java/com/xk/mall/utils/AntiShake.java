package com.xk.mall.utils;

/**
 * ClassName AntiShake
 * Description 防止多次点击的工具类
 * Author 卿凯
 * Date 2019/6/12/012
 * Version V1.0
 */
public class AntiShake {

    private static LimitQueue<OneClick> queue = new LimitQueue<>(20);

    public static boolean check(Object o) {
        String flag;
        if(o == null) {
            flag = Thread.currentThread().getStackTrace()[2].getMethodName();
        } else {
            flag = o.toString();
        }
        for (OneClick util : queue.getArrayList()) {
            if (util.getMethodName().equals(flag)) {
                return util.check();
            }
        }
        OneClick clickUtil = new OneClick(flag);
        queue.offer(clickUtil);
        return clickUtil.check();
    }
}
