package com.example.mac.back.application;

import android.app.Activity;
import android.app.Application;

import com.example.mac.back.config.AppConfig;
import com.example.mac.back.config.CrashHandler;
import com.example.mac.back.data.ProductApi;
import com.example.mac.back.ui.presenter.UpdatePresenter;
import com.example.mac.back.ui.presenter.UserPresenter;
import com.example.mac.back.utils.SharedPreferencesUtils;
import com.itheima.retrofitutils.ItheimaHttp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * Created by mac on 2018/3/2.
 */

public class MyApplication extends Application {

    private List<Activity> activities = new ArrayList<Activity>();



    @Override
    public void onCreate() {
        super.onCreate();
        ItheimaHttp.init(this, AppConfig.BASEURL);
        SharedPreferencesUtils.init(this);
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());









    }







}
