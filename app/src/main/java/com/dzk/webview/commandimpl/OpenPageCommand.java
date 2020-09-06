package com.dzk.webview.commandimpl;

import android.content.ComponentName;
import android.content.Intent;

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
public class OpenPageCommand implements Command{
    @Override
    public String name() {
        return "openPage";
    }

    @Override
    public void execute(Map parameters, ICallbackFromMainProcessToWebViewProcessInterface callback) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(BaseApplication.sApplication,String.valueOf(parameters.get("target_class")));
        intent.setComponent(componentName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.sApplication.startActivity(intent);
    }
}
