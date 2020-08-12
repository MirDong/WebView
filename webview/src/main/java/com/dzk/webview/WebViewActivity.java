package com.dzk.webview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.dzk.webview.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {
    ActivityWebViewBinding mBinding;
    private String url;
    private String title;
    private boolean isShowActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding =  DataBindingUtil.setContentView(this,R.layout.activity_web_view);
        mBinding.webview.getSettings().setJavaScriptEnabled(true);
        Intent intent = getIntent();
        if (intent != null){
            url = intent.getStringExtra("url");
            title = intent.getStringExtra("title");
            isShowActionBar = intent.getBooleanExtra("isShowActionBar",false);
        }
        mBinding.title.setText(title);
        mBinding.actionBar.setVisibility(isShowActionBar? View.VISIBLE:View.GONE);
        mBinding.webview.loadUrl(url);
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}