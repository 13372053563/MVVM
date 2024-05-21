package com.zsh.mvvm.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zsh.mvvm.R;
import com.zsh.mvvm.databinding.ActivityWebBinding;
import com.zsh.mvvm.viewmodel.WebViewModel;

public class WebActivity extends BaseActivity {

    private final WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedHttpAuthRequest(com.tencent.smtt.sdk.WebView webview,
                                              com.tencent.smtt.export.external.interfaces.HttpAuthHandler httpAuthHandlerhost, String host,
                                              String realm) {
            boolean flag = httpAuthHandlerhost.useHttpAuthUsernamePassword();
        }

        @Override
        public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String s) {
            super.onPageFinished(webView, s);
        }

        @Override
        public void onReceivedError(com.tencent.smtt.sdk.WebView webView, int i, String s, String s1) {
            System.out.println("***********onReceivedError ************");
            super.onReceivedError(webView, i, s, s1);
        }

        @Override
        public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
            System.out.println("***********onReceivedHttpError ************");
            super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定布局
        ActivityWebBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        // 创建出ViewModel
        WebViewModel viewModel = new ViewModelProvider(this).get(WebViewModel.class);
        // 设置WebView的配置客户端
        binding.webView.setWebViewClient(client);
        // 设置状态栏
        setStatusBar(true);
        // 在调用TBS初始化、创建WebView之前进行如下配置
        String uniqueKey = getIntent().getStringExtra("uniqueKey");
        if (uniqueKey != null) {
            viewModel.getNewsDetail(uniqueKey);
            viewModel.newsDetail.observe(context, newsDetailResponse -> {
                binding.webView.loadUrl(newsDetailResponse.getResult().getDetail().getUrl());
            });
            viewModel.failed.observe(context, this::showMsg);
        }
    }
}