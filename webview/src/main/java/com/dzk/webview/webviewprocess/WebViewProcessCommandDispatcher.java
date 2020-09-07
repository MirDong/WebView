package com.dzk.webview.webviewprocess;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.dzk.base.autoservice.BaseApplication;
import com.dzk.webview.ICallbackFromMainProcessToWebViewProcessInterface;
import com.dzk.webview.IWebViewProcessToMainProcessInterface;
import com.dzk.webview.mainprocess.MainProcessCommandService;

/**
 * @author jackie
 */
public class WebViewProcessCommandDispatcher implements ServiceConnection {
    private static final String TAG = "WebViewCommandDispatcher";
    private IWebViewProcessToMainProcessInterface webViewProcessToMainProcessInterface;
    private static WebViewProcessCommandDispatcher sWebViewProcessCommandDispatcher;
    public static WebViewProcessCommandDispatcher getInstance(){
        if (sWebViewProcessCommandDispatcher == null){
            synchronized (WebViewProcessCommandDispatcher.class){
                if (sWebViewProcessCommandDispatcher == null){
                    sWebViewProcessCommandDispatcher = new WebViewProcessCommandDispatcher();
                }
            }
        }
        return sWebViewProcessCommandDispatcher;
    }
    @SuppressLint("LongLogTag")
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.d("WebViewCommandDispatcher", "onServiceConnected:");
        webViewProcessToMainProcessInterface = IWebViewProcessToMainProcessInterface.Stub.asInterface(iBinder);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d("WebViewCommandDispatcher", "onServiceDisconnected:");
        webViewProcessToMainProcessInterface = null;
        initAidlConnect();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindingDied(ComponentName name) {
        Log.d("WebViewCommandDispatcher", "onBindingDied:");
        webViewProcessToMainProcessInterface = null;
        initAidlConnect();
    }

    public void initAidlConnect() {
        Intent intent = new Intent(BaseApplication.sApplication, MainProcessCommandService.class);
        BaseApplication.sApplication.bindService(intent,this, Context.BIND_AUTO_CREATE);
    }

    public void executeCommand(String command,String paramters,BaseWebView webView){
        if (webViewProcessToMainProcessInterface != null){
            try {
                webViewProcessToMainProcessInterface.handleCommand(command, paramters, new ICallbackFromMainProcessToWebViewProcessInterface.Stub() {
                    @Override
                    public void onResult(String callbackName, String response) throws RemoteException {
                            webView.handleCallback(callbackName, response);
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
