var xiangxuejs = {}
xiangxuejs.os = {}
xiangxuejs.os.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
xiangxuejs.os.isAndroid = !xiangxuejs.os.isIOS;
xiangxuejs.callbacks = {}

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