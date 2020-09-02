package com.dzk.webview.webviewprocess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.dzk.base.autoservice.BaseApplication;
import com.dzk.webview.mainprocess.MainProcessCommandService;

public class WebViewProcessCommandDispatcher implements ServiceConnection {

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
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    public void initAidlConnect() {
        Intent intent = new Intent(BaseApplication.sApplication, MainProcessCommandService.class);
        BaseApplication.sApplication.bindService(intent,this, Context.BIND_AUTO_CREATE);
    }
}
