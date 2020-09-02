package com.dzk.webview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dzk.base.autoservice.loadsir.ErrorCallBack;
import com.dzk.base.autoservice.loadsir.LoadingCallBack;
import com.dzk.webview.databinding.FragmentWebViewBinding;
import com.dzk.webview.util.Constants;
import com.dzk.webview.webviewprocess.settings.WebViewDefaultSettings;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.time.chrono.IsoChronology;

public class WebViewFragment extends Fragment implements WebViewCallBack, OnRefreshListener {
    private static final String TAG = "WebViewFragment";
    private boolean mIsError = false;
    private String mUrl;
    private boolean mCanNativeRefresh;
    private LoadService mLoadService;
    FragmentWebViewBinding mBinding;
    public static WebViewFragment getInstance(String url,boolean canNativeRefresh){
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL,url);
        bundle.putBoolean(Constants.CAN_NATIVE_REFRESH,canNativeRefresh);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            mUrl = bundle.getString(Constants.URL);
            mCanNativeRefresh = bundle.getBoolean(Constants.CAN_NATIVE_REFRESH,false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_web_view,container,false);
        mBinding.webView.registerWebViewCallback(this);
        mBinding.webView.loadUrl(mUrl);
        mLoadService = LoadSir.getDefault().register(mBinding.smartRefreshLayout, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mLoadService.showCallback(LoadingCallBack.class);
                mBinding.webView.reload();
            }
        });

        mBinding.smartRefreshLayout.setOnRefreshListener(this);
        mBinding.smartRefreshLayout.setEnableRefresh(mCanNativeRefresh);
        mBinding.smartRefreshLayout.setEnableLoadMore(false);
        return mLoadService.getLoadLayout();
    }

    @Override
    public void onPageStarted(String url) {
        Log.d(TAG, "onPageStarted");
        if (mLoadService != null){
            mLoadService.showCallback(LoadingCallBack.class);
        }
    }

    @Override
    public void onPageFinished(String url) {
        Log.d(TAG, "onPageFinished");
        if(mIsError){
            mBinding.smartRefreshLayout.setEnableRefresh(true);
        }else {
            mBinding.smartRefreshLayout.setEnableRefresh(mCanNativeRefresh);
        }
        mBinding.smartRefreshLayout.finishRefresh();
        if (mLoadService != null){
            if (mIsError){
                mLoadService.showCallback(ErrorCallBack.class);
            }else {
                mLoadService.showSuccess();
            }
        }
        mIsError = false;
    }

    @Override
    public void onError() {
        mBinding.smartRefreshLayout.finishRefresh();
        mIsError = true;
    }

    @Override
    public void updateTitle(String title) {
        if (getActivity() instanceof  WebViewActivity){
            ((WebViewActivity) getActivity()).updateTitle(title);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mBinding.webView.reload();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.webView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
        mBinding.webView.clearHistory();
        ViewGroup parent = (ViewGroup) mBinding.webView.getParent();
        parent.removeView(mBinding.webView);
        mBinding.webView.destroy();
    }
}
