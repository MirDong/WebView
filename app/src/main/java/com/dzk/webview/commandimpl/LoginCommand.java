package com.dzk.webview.commandimpl;

import android.util.Log;

import com.dzk.webview.ICallbackFromMainProcessToWebViewProcessInterface;
import com.dzk.webview.command.Command;
import com.google.auto.service.AutoService;

import java.util.Map;

/**
 * @author jackie
 * @date 2020/9/5
 */
@AutoService(Command.class)
public class LoginCommand implements Command{
    private static final String TAG = "LoginCommand";
    @Override
    public String name() {
        return "login";
    }

    @Override
    public void execute(Map parameters, ICallbackFromMainProcessToWebViewProcessInterface callback) {
        if (callback != null){
//            callback.onResult(name(),);
        }else {
            Log.e(TAG,"ICallbackFromMainProcessToWebViewProcessInterface is null");
        }
    }
}
