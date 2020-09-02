package com.dzk.webview.webviewprocess.webchromeclient;

import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.dzk.webview.WebViewCallBack;

public class MyWebViewChromeClient extends WebChromeClient {
    private static final String TAG = "MyWebViewChromeClient";
    private WebViewCallBack mWebViewCallback;

    public MyWebViewChromeClient(WebViewCallBack mWebViewCallback) {
        this.mWebViewCallback = mWebViewCallback;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (mWebViewCallback != null){
            mWebViewCallback.updateTitle(title);
        }else {
            Log.e(TAG, "mWebViewCallback is null");
        }
    }

    /**
     * Report a JavaScript console message to the host application. The ChromeClient
     * should override this to process the log message as they see fit.
     * @param consoleMessage Object containing details of the console message.
     * @return {@code true} if the message is handled by the client.
     */
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        // Call the old version of this function for backwards compatability.
        Log.d(TAG, consoleMessage.message());
        return super.onConsoleMessage(consoleMessage);
    }
}
