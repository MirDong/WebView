package com.dzk.webview;

public interface WebViewCallBack {
    void onPageStarted(String url);
    void onPageFinished(String url);
    void onError();
    void updateTitle(String title);
}
