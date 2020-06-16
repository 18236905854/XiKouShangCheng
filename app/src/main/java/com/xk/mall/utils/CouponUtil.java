package com.xk.mall.utils;

/**
 * ClassName CouponUtil
 * Description 优惠券使用记录工具类
 * Author 卿凯
 * Date 2019/8/12/012
 * Version V1.0
 */
public class CouponUtil {
    /**
     * 根据moduleId 获取活动的类型名称
     * 业务类型：1:吾G限时购，2: 全球买手, 3：0元竞拍 4:多买多折，5:喜立得，6:定制拼团，7:OTO线下 8:代付订单  9：新人专区
     */
    public static String getActivityNameByMouldId(String moduleId){
        String activity = "";
        if(moduleId.equals("1")){
            activity = "吾G购";
        } else if(moduleId.equals("2")){
            activity = "全球买手";
        } else if(moduleId.equals("3")){
            activity = "0元抢";
        } else if(moduleId.equals("4")){
            activity = "多买多折";
        } else if(moduleId.equals("5")){
            activity = "喜立得";
        } else if(moduleId.equals("6")){
            activity = "定制拼团";
        }else if(moduleId.equals("7")){
            activity = "O2O线下";
        }else if(moduleId.equals("8")){
            activity = "代付订单";
        }else if(moduleId.equals("9")){
            activity = "新人专区";
        }
        return activity;
    }

    /**
     * 根据类型获取红包来源类型
     * 业务类型：1 ：红包分润，2：使用红包，3：管理费，4：红包提现，5:提现手续费，6:服务费转账，7:用户转账，8:代付支出，9:代付收入，10:喜立得红包，11:寄卖收入
     */
    public static String getBusinessByType(int businessType){
        String typeStr = "";
        if(businessType == 1){
            typeStr = "平台分润";
        }else if(businessType == 2){
            typeStr = "使用钱包";
        }else if(businessType == 3){
            typeStr = "管理费";
        }else if(businessType == 4){
            typeStr = "钱包提现";
        }else if(businessType == 5) {
            typeStr = "提现手续费";
        }else if(businessType == 6){
            typeStr = "服务费转账";
        }else if(businessType == 7){
            typeStr = "用户转账";
        }else if(businessType == 8){
            typeStr = "代付支出";
        }else if(businessType == 9){
            typeStr = "代付收入";
        }else if(businessType == 10){
            typeStr = "喜立得红包";
        }else if(businessType == 11){
            typeStr = "寄卖收入";
        }
        return typeStr;
    }
}
