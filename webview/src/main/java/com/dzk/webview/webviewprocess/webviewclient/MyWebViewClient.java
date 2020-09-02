package com.dzk.webview.webviewprocess.webviewclient;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dzk.webview.WebViewCallBack;

public class MyWebViewClient extends WebViewClient {
    private static final String TAG = "MyWebVIewClient";
    private WebViewCallBack callBack;

    public MyWebViewClient(WebViewCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (callBack != null){
            callBack.onPageStarted(url);
        }else {
            Log.e(TAG, "WebViewCallBack is null" );
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (callBack != null){
            callBack.onPageFinished(url);
        }else {
            Log.e(TAG, "WebViewCallBack is null" );
        }

    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        if (callBack != null){
            callBack.onError();
        }else {
            Log.e(TAG, "WebViewCallBack is null" );
        }
    }
}
