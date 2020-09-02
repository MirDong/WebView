package com.dzk.webview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dzk.base.autoservice.WebViewServiceLoader;
import com.dzk.common.autoservice.IWebViewService;
import com.dzk.webview.databinding.ActivityMainBinding;

import java.util.ServiceLoader;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding =  DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainBinding.toWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*IWebViewService webViewService = ServiceLoader.load(IWebViewService.class).iterator().next();
                if (webViewService != null){
                    webViewService.startWebViewActivity(MainActivity.this,"https://www.baidu.com","百度",false);
                }*/

                IWebViewService webViewService = WebViewServiceLoader.loadService(IWebViewService.class);
                if (webViewService != null){
//                    webViewService.startWebViewActivity(MainActivity.this,"https://www.baidu.com","百度",true);
                    webViewService.openDemoHtml(MainActivity.this);
                }
            }
        });
    }
}