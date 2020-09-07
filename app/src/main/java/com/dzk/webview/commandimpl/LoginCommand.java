package com.dzk.webview.commandimpl;

import android.os.RemoteException;
import android.util.Log;

import com.dzk.base.autoservice.WebViewServiceLoader;
import com.dzk.common.autoservice.IUserCenterService;
import com.dzk.common.autoservice.eventbus.LoginEvent;
import com.dzk.webview.ICallbackFromMainProcessToWebViewProcessInterface;
import com.dzk.webview.command.Command;
import com.google.auto.service.AutoService;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jackie
 * @date 2020/9/5
 */
@AutoService(Command.class)
public class LoginCommand implements Command{
    private static final String TAG = "LoginCommand";
    public IUserCenterService iUserCenterService = WebViewServiceLoader.loadService(IUserCenterService.class);
    ICallbackFromMainProcessToWebViewProcessInterface callback;
    String callbackNameFromJs;

    public LoginCommand(){
        EventBus.getDefault().register(this);

    }
    @Override
    public String name() {
        return "login";
    }

    @Override
    public void execute(Map parameters, ICallbackFromMainProcessToWebViewProcessInterface callback) {
        if (callback != null){
            if (iUserCenterService != null && !iUserCenterService.isLogined()){
                iUserCenterService.login();
                this.callback = callback;
                callbackNameFromJs = parameters.get("callbackname").toString();
            }

        }else {
            Log.e(TAG,"ICallbackFromMainProcessToWebViewProcessInterface is null");
        }
    }

    @Subscribe
    public void onEventMessage(LoginEvent event){
        Log.d(TAG, "onEventMessage: " + event.userName);
        Map map = new HashMap();
        map.put("accountName",event.userName);
        if (this.callback != null){
            try {
                this.callback.onResult(callbackNameFromJs,new Gson().toJson(map));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
