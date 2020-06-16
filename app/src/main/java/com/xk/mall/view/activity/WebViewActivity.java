package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.library.libbrower.X5WebView;
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
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webView)
    X5WebView webView;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
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
    protected void initData() {
        String url = getIntent().getStringExtra(Constant.INTENT_URL);
        if(!TextUtils.isEmpty(url)){
            webView.loadUrl(url);
        }
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                setTitle(s);
            }
        });
    }
}
