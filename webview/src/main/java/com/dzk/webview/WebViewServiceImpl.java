package com.dzk.webview;

import android.content.Context;

import com.dzk.common.autoservice.IWebViewService;
import com.google.auto.service.AutoService;

/**
 * @author jackie
 * @date 2020/8/12
 */
@AutoService({IWebViewService.class})
class WebViewServiceImpl implements IWebViewService {
    @Override
    public void startWebViewActivity(Context context, String url, String title, boolean isShowActionBar) {

    }
}
