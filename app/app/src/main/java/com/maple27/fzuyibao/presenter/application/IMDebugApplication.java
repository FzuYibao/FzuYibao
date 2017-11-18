package com.maple27.fzuyibao.presenter.application;

import android.app.Application;

import cn.jpush.im.android.api.JMessageClient;

public class IMDebugApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);
    }
}