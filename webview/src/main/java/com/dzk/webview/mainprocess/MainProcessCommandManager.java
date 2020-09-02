package com.dzk.webview.mainprocess;

import android.os.RemoteException;

import com.dzk.webview.ICallbackFromMainProcessToWebViewProcessInterface;
import com.dzk.webview.IWebViewProcessToMainProcessInterface;
import com.dzk.webview.command.Command;

import java.util.HashMap;
import java.util.ServiceLoader;

public class MainProcessCommandManager extends IWebViewProcessToMainProcessInterface.Stub {
    private static MainProcessCommandManager sInstance;
    private static final HashMap<String, Command> sCommands = new HashMap<>();
    public static MainProcessCommandManager getInstance(){
        if (sInstance == null){
            synchronized (MainProcessCommandManager.class){
                if (sInstance == null){
                    sInstance = new MainProcessCommandManager();
                }
            }
        }
        return sInstance;
    }

    private MainProcessCommandManager(){
//        ServiceLoader<Command>
//        if ()
    }
    @Override
    public void handleCommand(String commandName, String jsonParams, ICallbackFromMainProcessToWebViewProcessInterface callback) throws RemoteException {

    }
}
