package com.dzk.base.autoservice.loadsir;


import com.dzk.base.R;
import com.kingja.loadsir.callback.Callback;

public class EmptyCallBack extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }
}
