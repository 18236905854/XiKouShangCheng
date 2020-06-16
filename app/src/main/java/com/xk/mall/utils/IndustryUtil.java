package com.xk.mall.utils;

/**
 * ClassName IndustryUtil
 * Description 行业分类工具类
 * Author 卿凯
 * Date 2019/7/10/010
 * Version V1.0
 */
public class IndustryUtil {
    public static String getIndustry(int type){
        String industry = "";
        switch (type){
            case 1:
                industry = "美食餐饮";
                break;

            case 2:
                industry = "休闲娱乐";
                break;

            case 3:
                industry = "酒店住宿";
                break;

            case 4:
                industry = "超市便利";
                break;

            case 5:
                industry = "果蔬零食";
                break;

            case 6:
                industry = "教育培训";
                break;

            case 7:
                industry = "交通出行";
                break;

            case 8:
                industry = "美容健身";
                break;

            case 9:
                industry = "保健养生";
                break;

            case 10:
                industry = "电子数码";
                break;

            case 11:
                industry = "家居家具";
                break;

            case 12:
                industry = "珠宝首饰";
                break;

            case 13:
                industry = "服饰零售";
                break;

            case 14:
                industry = "生鲜零售";
                break;

            case 15:
                industry = "母婴玩具";
                break;

            case 16:
                industry = "浪漫鲜花";
                break;

            case 17:
                industry = "衣物干洗";
                break;

            case 18:
                industry = "甜蜜蛋糕";
                break;

            default:
                industry = "其它行业";
                break;

        }
        return industry;
    }
}
