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

#### 3.2.1 WebSettings类

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
//如果缓存可用优先 使用webview缓存，
webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//设置可以访问文件
webSettings.setAllowFileAccess(true);
//支持通过JS打开新窗口
webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//支持自动加载图片
webSettings.setLoadImagesAutomatically(true);
//设置编码格式
webSettings.setDefaultTextEncodingName("utf-8");
```

**常用用法：设置WebView缓存**

- 当加载html页面时，WebView会在/data/data/包名  目录下生成database与cache两个文件夹

- 请求的URL记录保存在WebViewCache.db,而URL的内容是保存在WebViewCache文件下

- 是否启用缓存：

  ```java
  //默认缓存使用模式，如果跳转不强加任何特定行为，缓存资源可用时，加载可用资源，否则加载网络资源
  public static final int LOAD_DEFAULT = -1;
  //当有缓存资源可用时，使用缓存资源，即使缓存已经过期。否则加载网络资源
  public static final int LOAD_CACHE_ELSE_NETWORK = 1;
  //不使用缓存资源，仅从网络加载
  public static final int LOAD_NO_CACHE = 2;
  //不使用网络资源，加载缓存资源
  public static final int LOAD_CACHE_ONLY = 3;
  WebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
  ```

- 综合使用(离线加载)

  ```java
  if(NetStatusUtil.isConnected(getApplicationContext())){
      //根据cache-control决定是否从网络上获取
      webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
  }else{
      webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
  }
  //开启DOM storage API 功能
  webSettings.setDomStorageEnabled(true);
  //开启database storage API功能
  webSettings.setDatabaseEnabled(true);
  //开启Application Caches 功能
  webSettings.setAppCacheEnabled(true);
  
  String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
  //设置Application Caches 缓存目录
  webSettings.setAppCachePath(cacheDirPath);
  ```

  注意: 每个Application只调用一次WebSettings.setApplicationCachePath()和WebSettings.setAppCacheMaxSize()

  #### 3.2.2 WebViewClient类

  - 作用：处理各种通知和请求事件

  - 常见方法:

    **1.shouldOverrideUrlLoading()**

    - 作用：打开网页时不调用系统浏览器，而是在WebView中显示；网页上的所有加载都经过这个方法，这个函数我们可以做很多操作。

      ```java
      //1.定义webview组件
      WebView webview = (WebView) findViewById(R.id.webview);
      //2.选择加载方式
      //方式1：加载一个网页
      webview.loadUrl("http://google.com/");
      //方式2：加载apk包中的html页面
      webview.loadUrl("file:///android_asset/test.html");
      //方式3：加载手机本地的html页面
      webview.loadUrl("content://com.android.htmlfileprovider/sdcard/test.html");
      //3.重写shouldOverrideUrlLoading()方法
      webview.setWebViewClient(new WebViewClient(){
         @Override
          public boolean shouldOverrideUrlLoading(WebView view,String url){
        		view.loadUrl(url);
              return true;
          }
      });
      ```

      

**2.onPageStarted()**

- 作用：开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。

  ```java
  webview.setWebView(new WebViewClient(){
      @Override
      public void onPageStarted(WebView view,String url,Bitmap bitmap){
          //设定加载开始的操作
      }
  });
  ```

**3.onPageFinished**()

- 作用:在页面加载结束时调用。我们可以关闭loading条，切换程序动作。

  ```java
  webview.setWebViewClient(new WebViewClient(){
      @Override
      public void onPageFinished(WebView view,String url){
          //设定加载结束的操作
      }
  });
  ```

  **4.onLoadResource**()

- 作用：在加载页面资源时会调用，每一个资源(比如图片)的加载都会调用一次。

  ```java
  webview.setWebViewClient(new WebViewClient(){
      @Override
      public void onLoadResource(WebView view,String url){
          //设定加载资源的操作
      }
  });
  ```

  **onReceiveError**()

- 作用:加载页面的服务器出现错误时(如404)调用。

  App里面使用WebView控件的时候遇到了诸如404这类的错误时，若也显示浏览器错误页面就显得丑，此时需要我们的app需要加载一个本地的错误提示页，即WebView如何加载一个本地的页面。

  ```java
  //步骤1:写一个html文件(error_handle.html)，用于出错时展示给用户看的提示页面
  //步骤2：将该html文件放置到代码根目录的assets文件下。
  //步骤3：复写webviewclient的onReceivedError方法
  webview.setWebClient(new WebViewClient(){
      @Override
      public void onReceivedError(WebView view,int errorCode, String description, String failingUrl){
          switch(errorCode){
              case HttpStatus.SC_NOT_FOUND:
                  view.loadUrl("file:///android_assets/error_handle.html");
                  break;
          }
      }
  });
  ```

- **onReceivedSslError**()

- 作用：处理https请求

  WebView默认是不处理https请求的,页面显示空白，需要进行如下设置:

  ```java
  webview.setWebViewClient(new WebViewClient(){
      @Override
      public void onReceivedSslError(WebView view,SslErrorHandler handler, SslError error){
          handler.proceed();//等待证书响应
          //handler.cancle();表示挂起连接，为默认方式
          //handler.handleMessage(null);可做其他处理
      }
  });
  ```

  #### 3.2.2 WebChromeClient类

  作用：辅助WebVIew处理JavaScript的对话框，网站图标，网站标题等等。

  - **onProgressChanged**()

  - 作用: 获得网页的加载进度并显示

    ```java
    
    webview.setWebChromeClient(new WebChromeClient(){
    
          @Override
          public void onProgressChanged(WebView view, int newProgress) {
              if (newProgress <= 100) {
                  String progress = newProgress + "%";
                  progress.setText(progress);
                } else {
                  
              }
            }
        });
    ```

    

  - **onReceivedTitle**()

  - 作用: 获取Web页的标题

    每个网页页面都有一个标题，如www.baidu.com页面标题是"百度一下，你就知道"

    ```java
    webview.setWebChromeClient(new WebChromeClkient(){
        @Override
        public void onReceivedTitle(WebView view,String title{
            titleView.setText(title);
        }
    });
    ```

    

### 3.3 与js交互

### 3.4 注意事项

如何避免内存泄漏

**3.4.1  不在xml中定义WebView,而是在需要的时候在Activity中创建，并且Context 使用getApplicationContext()**

```java
LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        mLayout.addView(mWebView);
```

3.4.2 在Activity销毁(WebView)的时候,先让WebView加载null内容，然后移除WebView，销毁WebView，最后置空。

```java
@Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
```



## 4.实例

## 5.总结
介绍了WebView的基本使用方法。WebSettings类的设置,WebViewClient类和WebChromeClient类的回调方法使用，WebView如何避免内存的泄漏需要注意的事项。

