package com.xk.mall.utils;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 唤起微信支付公共类
 */
public class WXPayUtils {
    private IWXAPI iwxapi; //微信支付api

    private WXPayBuilder builder;

    private WXPayUtils(WXPayBuilder builder) {
        this.builder = builder;
    }
    /**
     * //假装请求了服务器 获取到了所有的数据,注意参数不能少
     *                 WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
     *                 builder.setAppId("123")
     *                         .setPartnerId("56465")
     *                         .setPrepayId("41515")
     *                         .setPackageValue("5153")
     *                         .setNonceStr("5645")
     *                         .setTimeStamp("56512")
     *                         .setSign("54615")
     *                         .build().toWXPayNotSign(MainActivity.this);
     */
    /**
     * 调起微信支付的方法,不需要在客户端签名
     **/
    public void toWXPayNotSign(Context context) {
        iwxapi = WXAPIFactory.createWXAPI(context, null); //初始化微信api
        iwxapi.registerApp(builder.getAppid()); //注册appid  appid可以在开发平台获取

        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId = builder.getAppid();
                request.partnerId = builder.getPartnerid();
                request.prepayId = builder.getPrepayid();
                request.packageValue = builder.getPackageName();
                request.nonceStr = builder.getNoncestr();
                request.timeStamp =builder.getTimestamp();
                request.sign = builder.getSign();
                iwxapi.sendReq(request);//发送调起微信的请求
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    public static class WXPayBuilder {

        /**
         * appid : wx33bda4593214121a
         * noncestr : af62e01cfe075295a5f5c274079ef42f
         * package : Sign=WXPay
         * partnerid : 1541603991
         * prepayid : wx201030012935289d9ff0eb361022991600
         * sign : 1D4B197C5394D83E49CBE5EA67DCE2B9
         * timestamp : 1563589801
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageName() {
            return packageX;
        }

        public void setPackageName(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }


}
