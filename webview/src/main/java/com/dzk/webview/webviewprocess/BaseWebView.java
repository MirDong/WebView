package com.dzk.webview.webviewprocess;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dzk.webview.WebViewCallBack;
import com.dzk.webview.bean.JsParam;
import com.dzk.webview.webviewprocess.settings.WebViewDefaultSettings;
import com.dzk.webview.webviewprocess.webchromeclient.MyWebViewChromeClient;
import com.dzk.webview.webviewprocess.webviewclient.MyWebViewClient;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.Map;

public class BaseWebView extends WebView {
    private static final String TAG = "BaseWebView";
    public BaseWebView(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BaseWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        WebViewProcessCommandDispatcher.getInstance().initAidlConnect();
        WebViewDefaultSettings.getInstance().setSettings(this);
        addJavascriptInterface(this,"xiangxuewebview");
    }

    public void registerWebViewCallback(WebViewCallBack callBack){
        setWebViewClient(new MyWebViewClient(callBack));
        setWebChromeClient(new MyWebViewChromeClient(callBack));
    }

    @JavascriptInterface
    public void takeNativeAction(final String jsParams){
        Log.d(TAG, jsParams);
        if (!TextUtils.isEmpty(jsParams)){
            final JsParam jsParamObject = new Gson().fromJson(jsParams,JsParam.class);
            if (jsParamObject != null){
                WebViewProcessCommandDispatcher.getInstance().executeCommand(jsParamObject.name,new Gson().toJson(jsParamObject.param),this);
            }
        }
    }
}
