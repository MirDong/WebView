package com.dzk.webview;

import com.dzk.base.autoservice.BaseApplication;
import com.dzk.base.autoservice.loadsir.CustomCallback;
import com.dzk.base.autoservice.loadsir.EmptyCallBack;
import com.dzk.base.autoservice.loadsir.ErrorCallBack;
import com.dzk.base.autoservice.loadsir.LoadingCallBack;
import com.dzk.base.autoservice.loadsir.TimeoutCallback;
import com.kingja.loadsir.core.LoadSir;

public class WebViewApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        LoadSir.beginBuilder()
                .addCallback(new EmptyCallBack())
                .addCallback(new ErrorCallBack())
                .addCallback(new LoadingCallBack())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallBack.class)
                .commit();

    }
}
