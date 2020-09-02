package com.dzk.webview.command;

import com.dzk.webview.ICallbackFromMainProcessToWebViewProcessInterface;

import java.util.Map;

public interface Command {
    String name();
    void execute(Map parameters, ICallbackFromMainProcessToWebViewProcessInterface callback);
}
