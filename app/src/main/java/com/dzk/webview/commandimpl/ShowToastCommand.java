package com.dzk.webview.commandimpl;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.dzk.base.autoservice.BaseApplication;
import com.dzk.webview.ICallbackFromMainProcessToWebViewProcessInterface;
import com.dzk.webview.command.Command;
import com.google.auto.service.AutoService;

import java.util.Map;

/**
 * @author jackie
 * @date 2020/9/5
 */
@AutoService(Command.class)
public class ShowToastCommand implements Command{

    @Override
    public String name() {
        return "showToast";
    }

    @Override
    public void execute(Map parameters, ICallbackFromMainProcessToWebViewProcessInterface callback) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseApplication.sApplication,String.valueOf(parameters.get("message")),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
