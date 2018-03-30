package com.example.mac.back.ui.module;

import android.app.Application;

import com.example.mac.back.application.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 提供application依赖
 * Created by mac on 2018/3/30.
 */


@Module
public class AppModule {

    MyApplication mApplication;

    public AppModule(MyApplication mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton //提供单例模式
    Application providesApplication() {
        return mApplication;
    }
}
