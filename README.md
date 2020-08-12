# WebView

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

## 4.实例

## 5.总结