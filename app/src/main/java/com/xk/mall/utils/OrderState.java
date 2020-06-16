package com.xk.mall.utils;

/**
 * ClassName OrderState
 * Description 订单状态类
 * Author 卿凯
 * Date 2019/7/13/013
 * Version V1.0
 */
public class OrderState {
    //(订单状态；不传数据-全部;1-待支付;2-待发货;3-待收货;4-已取消;5-已完成;6-已关闭;7-待确认;8-待成团;9-成团成功;10-拼团失败;11-已寄卖
    public static final int ALL = 0;
    public static final int WAIT_PAY = 1;
    public static final int WAIT_SEND = 2;
    public static final int WAIT_RECEIVER = 3;
    public static final int CANCELED = 4;
    public static final int FINISHED = 5;
    public static final int CLOSED = 6;
    public static final int WAIT_SURE = 7;
    public static final int WAIT_FIGHT_GROUP = 8;
    public static final int FINISHED_FIGHT_GROUP = 9;
    public static final int FALILED_FIGHT_GRROUP = 10;
    public static final int GLOBAL_SELLED = 11;

}
