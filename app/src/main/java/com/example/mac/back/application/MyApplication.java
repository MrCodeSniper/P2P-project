package com.example.mac.back.application;

import android.app.Application;

import com.example.mac.back.config.AppConfig;
import com.example.mac.back.utils.SharedPreferencesUtils;
import com.itheima.retrofitutils.ItheimaHttp;

/**
 * Created by mac on 2018/3/2.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ItheimaHttp.init(this, AppConfig.BASEURL);
        SharedPreferencesUtils.init(this);
    }
}
