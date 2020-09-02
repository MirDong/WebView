// IWebViewProcessToMainProcessInterface.aidl
package com.dzk.webview;

// Declare any non-default types here with import statements
import com.dzk.webview.ICallbackFromMainProcessToWebViewProcessInterface;
interface IWebViewProcessToMainProcessInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void handleCommand(String commandName,String jsonParams,in ICallbackFromMainProcessToWebViewProcessInterface callback);
}
