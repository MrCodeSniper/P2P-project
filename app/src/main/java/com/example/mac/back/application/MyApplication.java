package com.example.mac.back.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.mac.back.config.AppConfig;

import com.example.mac.back.ui.component.AppComponent;
import com.example.mac.back.ui.component.DaggerAppComponent;
import com.example.mac.back.ui.module.AppModule;
import com.example.mac.back.ui.module.UserModel;
import com.example.mac.back.utils.SharedPreferencesUtils;
import com.itheima.retrofitutils.ItheimaHttp;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by mac on 2018/3/2.
 */

public class MyApplication extends Application {

    private AppComponent mAppComponent;
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        ItheimaHttp.init(this, AppConfig.BASEURL);
        SharedPreferencesUtils.init(this);
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());

        sInstance=this;

        mAppComponent= DaggerAppComponent.builder()
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .userModel(new UserModel())
                .build();


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);




    }

    public static MyApplication getsInstance() {
        return sInstance;
    }

    public static Context getContext(){
        return getContext();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }






}
