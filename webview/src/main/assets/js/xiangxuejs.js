var xiangxuejs = {}
xiangxuejs.os = {}
xiangxuejs.os.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
xiangxuejs.os.isAndroid = !xiangxuejs.os.isIOS;
xiangxuejs.callbacks = {}

xiangxuejs.callback = function(callbackname,response){
    var callbackObject = xiangxuejs.callbacks[callbackname];
    console.log("callbackname = " + callbackname);
    if(callbackObject !== undefined){
        if(callbackObject.callback !== undefined){
            console.log("response = " + response.accountName);
            var ret = callbackObject.callback(response);
            if(ret === false){
                return
            }
            delete xiangxuejs.callbacks[callbackname];
        }
    }
}
xiangxuejs.takeNativeAction = function(commandname,parameters){
    console.log("take native action");
    var request = {};
    request.name = commandname;
    request.param = parameters;
    if(window.xiangxuejs.os.isAndroid){
        console.log("android take native action" + JSON.stringify(request));
        window.xiangxuewebview.takeNativeAction(JSON.stringify(request));
    }else {
        window.webkit.messageHandlers.xiangxuewebview.postMessage(JSON.stringify(request));
    }
}

xiangxuejs.takeNativeActionWithCallback = function(commandname,parameters,callback){
    var callbackname = "native_to_js_callback_"+ new Date().getTime() + "_" + Math.floor(Math.random() * 10000);
    xiangxuejs.callbacks[callbackname] = {callback:callback};

    var request = {};
    request.name = commandname;
    request.param = parameters;
    request.param.callbackname = callbackname;
    if(window.xiangxuejs.os.isAndroid){
        window.xiangxuewebview.takeNativeAction(JSON.stringify(request));
    }else {
        window.webkit.messageHandlers.xiangxuewebview.postMessage(JSON.stringify(request));
    }

}

window.xiangxuejs = xiangxuejs

