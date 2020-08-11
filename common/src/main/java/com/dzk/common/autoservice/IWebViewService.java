package com.dzk.common.autoservice;

import android.content.Context;

/**
 * @author jackie
 * @date 2020/8/11
 */
public interface IWebViewService {
    /**
     *
     * @param context
     * @param url  load的url
     * @param title  url 页面title
     * @param isShowActionBar  是否显示ActionBar
     */
    void startWebViewActivity(Context context,String url,String title,boolean isShowActionBar);
}
