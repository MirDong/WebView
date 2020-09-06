package com.dzk.webview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;

import com.dzk.webview.databinding.ActivityWebViewBinding;
import com.dzk.webview.util.Constants;

public class WebViewActivity extends AppCompatActivity {
    ActivityWebViewBinding mBinding;
    private String url;
    private String title;
    private boolean isShowActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding =  DataBindingUtil.setContentView(this,R.layout.activity_web_view);
        Intent intent = getIntent();
        if (intent != null){
            url = intent.getStringExtra(Constants.URL);
            title = intent.getStringExtra(Constants.TITLE);
            isShowActionBar = intent.getBooleanExtra(Constants.IS_SHOW_ACTION_BAR,false);
        }
        mBinding.title.setText(title);
        mBinding.actionBar.setVisibility(isShowActionBar? View.VISIBLE:View.GONE);
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.web_view_fragment,WebViewFragment.getInstance(url,true)).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void updateTitle(String title){
        mBinding.title.setText(title);
    }
}