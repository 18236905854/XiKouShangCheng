package com.xk.mall.wxapi;

import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;

import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseContent;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.utils.EventBusMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * @ClassName WXEntryActivity
 * @Description  微信分享回调Activity
 * @Author 卿凯
 * @Date 2019/5/30/030 14:47
 * @Version  1.0
 */
public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    @Override
    protected void onDestroy() {
        super.onDestroy();

        hintKeyboard();
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        IWXAPI api = WXAPIFactory.createWXAPI(this, BaseContent.UM_WECHAT_KEY, true);

        // 将应用的appId注册到微信
        api.registerApp(BaseContent.UM_WECHAT_KEY);
        //如果没回调onResp，八成是这句没有写
        api.handleIntent(getIntent(), this);
    }

    private void hintKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

        if(baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            Logger.d("onPayFinish,errCode="+ baseResp.errCode);
        }

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_BAN:
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:

                if (RETURN_MSG_TYPE_SHARE == baseResp.getType()) {
                    Logger.e("分享失败");
                } else {
                    Logger.e("登录失败");
                }
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) baseResp).code;
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
                        Logger.e("code=" + code);
                        EventBus.getDefault().post(new EventBusMessage(code));
                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        Logger.e("分享成功");
                        EventBus.getDefault().post(new ShareSuccessEvent(MyApplication.shareType));
                        break;
                }
                break;
        }

//        EventBus.getDefault().post(event);
    }

    //该方法执行umeng登陆的回调的处理
    @Override
    public void a(com.umeng.weixin.umengwx.b b) {
//        super.a(b);
    }

    @Override
    protected void a(Intent intent) {
        super.a(intent);
    }

    //在onResume中处理从微信授权通过以后不会自动跳转的问题，手动结束该页面
    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}
