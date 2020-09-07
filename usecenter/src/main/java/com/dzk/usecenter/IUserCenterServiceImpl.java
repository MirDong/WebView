package com.dzk.usecenter;

import android.content.Intent;

import com.dzk.base.autoservice.BaseApplication;
import com.dzk.common.autoservice.IUserCenterService;
import com.google.auto.service.AutoService;

@AutoService(IUserCenterService.class)
public class IUserCenterServiceImpl implements IUserCenterService {
    @Override
    public boolean isLogined() {
        return false;
    }

    @Override
    public void login() {
        Intent intent = new Intent(BaseApplication.sApplication,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.sApplication.startActivity(intent);
    }
}
