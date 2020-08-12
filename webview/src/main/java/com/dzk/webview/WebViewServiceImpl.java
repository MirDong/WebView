package com.dzk.webview;

import android.content.Context;
import android.content.Intent;

import com.dzk.common.autoservice.IWebViewService;
import com.google.auto.service.AutoService;

/**
 * @author jackie
 * @date 2020/8/12
 */
@AutoService(IWebViewService.class)
public class WebViewServiceImpl implements IWebViewService {
   /* public WebViewServiceImpl() {

    }*/

    @Override
    public void startWebViewActivity(Context context, String url, String title, boolean isShowActionBar) {
        Intent intent = new Intent(context,WebViewActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        intent.putExtra("isShowActionBar",isShowActionBar);
        context.startActivity(intent);
    }
}
