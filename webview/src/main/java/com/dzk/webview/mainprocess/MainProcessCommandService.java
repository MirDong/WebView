package com.dzk.webview.mainprocess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @author Administrator
 */
public class MainProcessCommandService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return MainProcessCommandManager.getInstance();
    }
}
