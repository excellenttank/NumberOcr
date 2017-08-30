package com.excellent_tank.numberocr;

import android.app.Application;

import net.ralphpina.permissionsmanager.PermissionsManager;

/**
 * 作者：WangBinBin on 8/30 14:21
 * 邮箱：1205998131@qq.com
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PermissionsManager.init(this);
    }
}
