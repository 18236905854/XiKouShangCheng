package com.xk.mall.model.entity;

/**
 * ClassName CouponDetailRecordBean
 * Description 优惠券使用记录Bean
 * Author 卿凯
 * Date 2019/6/28/028
 * Version V1.0
 */
public class CouponDetailRecordBean {
    public String primaryKey = "";//订单号
    public String createTime = "";//使用时间
    public String moduleId = "";//来源（1:买一赠二(吾G)，2: 全球买手, 3：0元竞拍 4:多买多折，5:砍价得红包，6:定制拼团）
    public int cost = 0;//消费的金额
    public int operateType;//操作类型：1：消费，2：获取,3为转出
    public String transferToUser = "";//转赠接收人
    public int transferFee;//转赠服务费

}
