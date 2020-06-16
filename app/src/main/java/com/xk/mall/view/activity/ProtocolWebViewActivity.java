package com.xk.mall.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.library.libbrower.X5WebView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.utils.Constant;

import butterknife.BindView;

/**
 * ClassName WebViewActivity
 * Description 加载网页的activity
 * Author 卿凯
 * Date 2019/6/13/013
 * Version V1.0
 */
public class ProtocolWebViewActivity extends BaseActivity {

    @BindView(R.id.webView)
    X5WebView webView;
    @BindView(R.id.ll_protocol_bottom)
    LinearLayout llBottom;
    @BindView(R.id.cb_protocol)
    CheckBox checkBox;
    @BindView(R.id.tv_protocol_content)
    TextView tvContent;
    public static String IS_SHOW = "is_show";
    private boolean isShowCb;//是否显示选择

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_protocol_webview;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String title = intent.getStringExtra(Constant.INTENT_TITLE);
        if(!TextUtils.isEmpty(title)){
            setTitle(title);
        }

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if(isShowCb && !checkBox.isChecked()){
            ToastUtils.showShortToast(mContext, "请选择同意协议");
        }else {
            if(webView != null && webView.canGoBack()){
                webView.goBack();
            }else {
                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void initData() {
        isShowCb = getIntent().getBooleanExtra(IS_SHOW, false);
        if(isShowCb){
            llBottom.setVisibility(View.VISIBLE);
        }else {
            llBottom.setVisibility(View.GONE);
        }
        tvContent.setText("我已阅读并同意喜扣用户协议");
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        String url = getIntent().getStringExtra(Constant.INTENT_URL);
//        if(url == null || TextUtils.isEmpty(url)){
//            url = "https://www.baidu.com";
//        }
        Logger.e("获取到的地址:" + url);
        if(!TextUtils.isEmpty(url)){
            webView.loadUrl(url);
        }
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                setTitle(s);
            }
        });
    }
}
