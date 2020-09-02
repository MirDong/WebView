package com.dzk.webview;

import android.content.Context;
import android.content.Intent;

import com.dzk.common.autoservice.IWebViewService;
import com.dzk.webview.util.Constants;
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
        intent.putExtra(Constants.URL,url);
        intent.putExtra(Constants.TITLE,title);
        intent.putExtra(Constants.IS_SHOW_ACTION_BAR,isShowActionBar);
        context.startActivity(intent);
    }

    @Override
    public void openDemoHtml(Context context) {
        Intent intent = new Intent(context,WebViewActivity.class);
        intent.putExtra(Constants.URL,Constants.LOCAL_DEMO_HTML + "demo.html");
        intent.putExtra(Constants.TITLE,"demo html");
        context.startActivity(intent);
    }
}
