// ICallbackFromMainProcessToWebViewProcessInterface.aidl
package com.dzk.webview;

// Declare any non-default types here with import statements

interface ICallbackFromMainProcessToWebViewProcessInterface {
    void onResult(String callbackName,String response);
}
