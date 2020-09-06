package com.dzk.webview.mainprocess;

import android.os.RemoteException;

import com.dzk.webview.ICallbackFromMainProcessToWebViewProcessInterface;
import com.dzk.webview.IWebViewProcessToMainProcessInterface;
import com.dzk.webview.command.Command;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author Administrator
 */
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
        ServiceLoader<Command> serviceLoader = ServiceLoader.load(Command.class);
        for (Command command : serviceLoader) {
            if (!sCommands.containsKey(command.name())){
                sCommands.put(command.name(),command);
            }
        }
    }

    public void executeCommand(String commandName, Map parameter, ICallbackFromMainProcessToWebViewProcessInterface callback){
        sCommands.get(commandName).execute(parameter,callback);
    }

    @Override
    public void handleCommand(String commandName, String jsonParams, ICallbackFromMainProcessToWebViewProcessInterface callback) throws RemoteException {
        MainProcessCommandManager.getInstance().executeCommand(commandName,new Gson().fromJson(jsonParams, Map.class),callback);
    }
}
