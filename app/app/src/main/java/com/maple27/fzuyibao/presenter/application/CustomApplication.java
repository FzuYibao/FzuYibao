package com.maple27.fzuyibao.presenter.application;

import android.app.Application;
import android.os.Handler;

import cn.jpush.im.android.api.JMessageClient;

public class CustomApplication extends Application {

    private static final String VALUE = "Maple27";

    private String value;
    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);
        setValue(VALUE);
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public void setHandler( Handler handler) {
        this.mHandler = handler;
    }

    public Handler getHandler() {
        return mHandler;
    }
}