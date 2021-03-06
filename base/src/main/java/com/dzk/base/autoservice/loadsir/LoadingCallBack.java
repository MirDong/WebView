package com.dzk.base.autoservice.loadsir;

import android.content.Context;
import android.view.View;

import com.dzk.base.R;
import com.kingja.loadsir.callback.Callback;

public class LoadingCallBack extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }

    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
