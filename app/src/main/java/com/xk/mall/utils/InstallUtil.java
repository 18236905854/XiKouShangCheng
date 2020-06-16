package com.xk.mall.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xk.mall.base.BaseContent;

import java.util.List;

/**
 * ClassName InstallUtil
 * Description 检查是否安装工具类
 * Author 卿凯
 * Date 2019/7/2/002
 * Version V1.0
 */
public class InstallUtil {


    /**
     * 判断微信客户端是否存在
     *
     * @return true安装, false未安装
     */
    public static boolean isWeChatAppInstalled(Context context) {

        IWXAPI api = WXAPIFactory.createWXAPI(context, BaseContent.UM_WECHAT_KEY);
        if(api.isWXAppInstalled()) {
            return true;
        } else {
            final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    if (pn.equalsIgnoreCase("com.tencent.mm")) {
                        return true;
                    }
                }
            }
            return false;
        }
    }


    /**
     * 传入包名判断应用是否安装
     */
    public static boolean uninstallSoftware(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            if (packageInfo != null) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
