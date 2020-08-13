# WebView

[TOC]



## 1.简介

基于WebKit引擎，用于展现H5页面的视图控件。详情可参考https://mp.weixin.qq.com/s/5Hs9Bg93IbY2uRUMeIqAJQ

通常情况下，H5页面包含有js交互，所以使用webView.loadUrl(url)之前，需要调用对js交互的支持

即webView.getSettings().setJavaScriptEnabled(true),否则加载的页面可能为空页面

## 2.作用

- 显示和渲染Web页面

- 直接使用html文件（网络或本地assets中）做布局

- 可以和JavaScript交互调用

  WebView控件功能强大，除了具有一般的View属性和设置外，还可以对url请求，页面加载、渲染、页面交互进行强大的处理。

## 3.使用介绍

### 3.1 常用方法

#### 3.1.1 WebView的状态

1.webView.onResume()  

激活webview为活跃状态，能正常执行网页的响应。

2.webview.onPause()

当页面失去焦点或切换到后台不可见状态时，需要执行onPause().onPause通知内核暂停所有的工作，比如DOM的解析、Plugin的执行、JavaScript的执行。针对的不仅仅是当前的WebView,而是全应用(存在WebView)的WebView

3.webview.pauseTimers()

暂停所有webview的layout,parsing,javascripttimer.降低CPU功耗.

4.webview.resumeTimers()

恢复pauseTimers状态

5.rootLayout.removeView(webView)

销毁前，需要先从父容器中移除webview,然后再销毁webview

6.webview.destroy()

在关闭activity时，如果webview的音乐或视频还在播放，就必须销毁webview.值得注意的是，webview调用destroy时,webview仍绑定在Activity上。这是由于自定义webview构建时传入了该Activity的context对象。因此首先执行removeView()

#### 3.1.2 关于前进/后退网页

1. webview.canGoBack()   是否可以后退

2. webview.goBack()  后退网页

3. webview.canGoForward()   是否可以前进

4. webview.goForward()   前进网页

5. webview.goBackOrForward(intsteps)  

   以当前的index为起始点前进或者后退到历史记录中指定的steps,如果steps为负数则为后退，正数则为前进。

   常见用法：Back键控制网页后退

   - 问题：在不做任何处理前提下，浏览网页时点击系统的"Back"键，整个Browser会调用finish()而结束自身
   - 目标：点击返回后，是网页回退er不是退出浏览器
   - 解决方案:在当前Activity中处理并消费掉该Back事件

   ```java
   public boolean onKeyDown(int keyCode,KeyEvent event){
       if(keyCode == KEYCODE_BACK && mWeView.canGoBack()){
           mWebView.goBack();
           return true;
       }
       return super.onKeyDown(keyCode,event);
   }
   ```

   

#### 3.1.3 清除缓存数据

```java
/**
*清除网页访问留下的缓存
*由于内核缓存是全局的，因此这个方法不仅仅针对webview,而是针对整个应用程序
**/
webview.clearCache(true);
/**
*清除当前webview访问的历史记录中的所有记录，不包括当前的访问记录
**/
webview.clearHistory();

/**
*仅清除自动完成填充的表单数据，并不会清除webview存储到本地的数据
**/
webview.clearFormData();
```



### 3.2 常用类

3.2.1 WebSettings类

- 作用：对WebView进行配置和管理

- 配置步骤和常见方法:

  配置步骤1：添加网络访问权限

  ```java
  <uses-permission android:name="android.permission.INTERNET"/>
  ```

配置步骤2：生成一个WebView组件：



```java
//方式一：Activity中直接创建
WebView webview = new WebView(this);
//方法2：在Activity的layout文件里添加webview控件
WebView webview = (WebView) findViewById(R.id.webView1);
```





配置步骤3： 进行配置WebSettings类
```java
//声明webSettings子类
WebSettings webSettings = webView.getSettings();
//如果访问的页面中要与javascript交互，则webview必须设置支持javascript
webSettings.setJavaScriptEnabled(true);
//支持插件
webSettings.setPluginEnabled(true);

//设置自适应屏幕
webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
webSettings.setLoadWithOverviewMode(true);//缩放至屏幕大小
////支持缩放，默认为true。是下面那个的前提。webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
webSettings.setSupportZoom(true);

webViewSetting
```

### 3.3 与js交互

### 3.4 注意事项

## 4.实例

## 5.总结

