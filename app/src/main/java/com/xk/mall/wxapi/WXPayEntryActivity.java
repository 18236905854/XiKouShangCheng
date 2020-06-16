package com.xk.mall.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xk.mall.MyApplication;
import com.xk.mall.base.BaseContent;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.activity.CutOrderResultActivity;
import com.xk.mall.view.activity.PayOrderActivity;
import com.xk.mall.view.activity.PayOrderResultActivity;

/**
 * ClassName WXPayEntryActivity
 * Description 微信支付回调页面
 * Author 卿凯
 * Date 2019/7/19/019
 * Version V1.0
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;
    private Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(WXPayEntryActivity.this);
//        setContentView(R.layout.pay_result);
        context = this;
        api = WXAPIFactory.createWXAPI(this, BaseContent.UM_WECHAT_KEY);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("WXPayEntryActivity onActivityResult");
    }

    @Override
    public void onReq(BaseReq req) {
        Logger.e("微信支付请求");
    }

    @Override
    public void onResp(final BaseResp resp) {
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
////            Intent intent = new Intent(WXPayEntryActivity.this, ActivityPay.class);
////            intent.putExtra("payState", JsonParseUtil.toJson(resp));
////            startActivity(intent);
//            Logger.e("onPayFinish,errCode="+ resp.errCode);
//            Logger.e("支付回调成功");
//            //TODO 支付成功之后需要做处理
//            Intent intent = new Intent(context, PayOrderResultActivity.class);
//            if(resp.errCode == 0){
//                //支付成功
//                intent.putExtra(PayOrderResultActivity.PAY_STATUS, true);
//            }
//            ActivityUtils.startActivity(intent);
////            MyApplication.getInstance().closeActivity();
//        }
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if(resp.errCode==0){
                ToastUtils.showShortToast(WXPayEntryActivity.this,"支付成功");
                String orderNum= SPUtils.getInstance().getString(Constant.PAY_ORDER_NO);
                String cutId= SPUtils.getInstance().getString(Constant.PAY_ORDER_CUT);
                int orderType=SPUtils.getInstance().getInt(Constant.PAY_ORDER_TPYE);
                int coupon = SPUtils.getInstance().getInt(Constant.PAY_ORDER_COUPON);
                int mount = SPUtils.getInstance().getInt(Constant.PAY_ORDER_MOUNT);
                //订单支付   TODO 支付成功之后需要做处理
                if(!TextUtils.isEmpty(orderNum)){
                    SPUtils.getInstance().remove(Constant.PAY_ORDER_NO);
                    SPUtils.getInstance().remove(Constant.PAY_ORDER_CUT);
                    SPUtils.getInstance().remove(Constant.PAY_ORDER_TPYE);
                    SPUtils.getInstance().remove(Constant.PAY_ORDER_COUPON);
                    SPUtils.getInstance().remove(Constant.PAY_ORDER_MOUNT);
                    if(!XiKouUtils.isNullOrEmpty(cutId)){
                        Intent intent=new Intent(context, CutOrderResultActivity.class);
                        intent.putExtra(CutOrderResultActivity.PAY_STATUS,true);
                        intent.putExtra(CutOrderResultActivity.CUT_ID,cutId);
                        intent.putExtra(CutOrderResultActivity.PAY_ORDER_NO,orderNum);
                        intent.putExtra(CutOrderResultActivity.PAY_ORDER_TYPE,orderType);
                        intent.putExtra(CutOrderResultActivity.PAY_COUPON,coupon);
                        intent.putExtra(CutOrderResultActivity.PAY_MOUNT,mount);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(context, PayOrderResultActivity.class);
                        intent.putExtra(PayOrderResultActivity.PAY_STATUS,true);
                        intent.putExtra(PayOrderResultActivity.PAY_ORDER_NO,orderNum);
                        intent.putExtra(PayOrderResultActivity.PAY_ORDER_TYPE,orderType);
                        intent.putExtra(PayOrderResultActivity.PAY_COUPON,coupon);
                        intent.putExtra(PayOrderResultActivity.PAY_MOUNT,mount);
                        startActivity(intent);
                    }
                    MyApplication.getInstance().closeActivity();
                    return;
                }
            }else if(resp.errCode==-2){
                ToastUtils.showShortToast(WXPayEntryActivity.this,"已取消");
                finish();
            }else {
                ToastUtils.showShortToast(WXPayEntryActivity.this,"支付失败...");
                Intent intent = new Intent(context, PayOrderResultActivity.class);
                intent.putExtra(PayOrderResultActivity.PAY_STATUS,false);
                startActivity(intent);
                finish();
            }
        }
    }
}
