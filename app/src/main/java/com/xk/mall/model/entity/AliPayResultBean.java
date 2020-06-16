package com.xk.mall.model.entity;

/**
 * ClassName AliPayResultBean
 * Description 支付宝支付接口返回数据Bean
 * Author 卿凯
 * Date 2019/7/24/024
 * Version V1.0
 */
public class AliPayResultBean {

    /**
     * aliPay : alipay_sdk=alipay-sdk-java-3.7.110.ALL&app_id=2019072065957104&biz_content=%7B%22body%22%3A%22Test-%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%953%22%2C%22out_trade_no%22%3A%22GB20190724110000000015%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E9%99%86%E9%99%86%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8-%E5%95%86%E5%9F%8E%E8%B4%AD%E7%89%A9%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.45%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=%24%7Bapp.aliPay.notifyUrl&sign=jEA5BHaH60rakBY6bQDehID19SOWSbA%2FAD80pZbEN%2BPhYH7isOEVOizIP%2Fk%2BlFw3Wb35K5wFT%2FmadgsSI5GsvnWnvegwVNwW8IWepNju43it%2BsHmYWYpUogdom09gRbqdlGT1EQYmyW%2F7wh2hRS2uDG72QJzZcMhUY9XjwwqI3%2FaPosA7q9LAfFITURCN7ngPI62bhJpZiKiAgGZnZM4xz469bk7thspZbphdG1HH5WKvTcjqm7xekzcIkxbXo4vrEDCdIW8JKVvg%2FXC2B%2BIFKcyAyrZfJRBhpUEclY9qjHhpwu%2FBd%2BqUsl3GZvMFpKDWxpcc5VvTgZ3GHQZZYhvXg%3D%3D&sign_type=RSA2&timestamp=2019-07-24+11%3A57%3A59&version=1.0
     */

    private String aliPay;

    public String getAliPay() {
        return aliPay;
    }

    public void setAliPay(String aliPay) {
        this.aliPay = aliPay;
    }
}
