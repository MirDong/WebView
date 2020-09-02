package com.dzk.webview.webviewprocess.settings;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.dzk.webview.BuildConfig;

public class WebViewDefaultSettings {
    private static final String TAG = "WebViewDefaultSettings";
    private WebSettings mWebSettings;

    private WebViewDefaultSettings(){

    }

    public static WebViewDefaultSettings getInstance(){
        return new WebViewDefaultSettings();
    }

    public void setSettings(WebView webView){
        if (webView != null){
            mWebSettings = webView.getSettings();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                webView.enableSlowWholeDocumentDraw();
                mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }

            mWebSettings.setJavaScriptEnabled(true);
            mWebSettings.setSupportZoom(true);
            mWebSettings.setBuiltInZoomControls(false);
            if (isNetworkConnected(webView.getContext())){
                mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            }else {
                mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }

            mWebSettings.setTextZoom(100);
            mWebSettings.setDatabaseEnabled(true);
            mWebSettings.setAppCacheEnabled(true);
            mWebSettings.setLoadsImagesAutomatically(true);
            mWebSettings.setSupportMultipleWindows(true);
            //是否阻塞加載网络图片
            mWebSettings.setBlockNetworkImage(false);
            //允许加载本地文件html file协议
            mWebSettings.setAllowFileAccess(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                //通过file url加载的JavaScript读取其他的本地文件，建议关闭
                mWebSettings.setAllowFileAccessFromFileURLs(false);
                //允许通过file url 加载的JavaScript 可以访问其他的源，包括其他文件和http,https等
                mWebSettings.setAllowUniversalAccessFromFileURLs(false);
            }

            mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            }else {
                mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            }

            mWebSettings.setSavePassword(false);
            mWebSettings.setSaveFormData(false);
            mWebSettings.setLoadWithOverviewMode(true);
            mWebSettings.setUseWideViewPort(true);
            mWebSettings.setDomStorageEnabled(true);
            mWebSettings.setNeedInitialFocus(true);
            mWebSettings.setDefaultTextEncodingName("utf-8");
            mWebSettings.setDefaultFontSize(16);
            mWebSettings.setMinimumFontSize(10);//设置WebView支持的最小字体大小，默认为8
            mWebSettings.setGeolocationEnabled(true);

            String appCacheDir = webView.getContext().getDir("cache",Context.MODE_PRIVATE).getPath();
            Log.i(TAG, "WebView Cache dir:" + appCacheDir);
            mWebSettings.setDatabasePath(appCacheDir);
            mWebSettings.setAppCachePath(appCacheDir);
            mWebSettings.setAppCacheMaxSize(1024 * 1024 * 80);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                webView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
            }

        }else {
            Log.e(TAG, "WebView is null");
        }
    }

    private boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null){
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null){
                return networkInfo.isConnected();
            }
        }
        return false;
    }
}
