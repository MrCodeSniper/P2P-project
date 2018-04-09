package com.example.mac.back.application;

import android.Manifest;
import android.content.Context;
import android.util.Log;

import com.example.mac.back.config.AppConfig;

import com.example.mac.back.ui.component.AppComponent;
import com.example.mac.back.ui.component.DaggerAppComponent;
import com.example.mac.back.ui.module.AppModule;
import com.example.mac.back.ui.module.UserModel;
import com.example.mac.back.utils.SharedPreferencesUtils;
import com.itheima.retrofitutils.ItheimaHttp;
import com.qihoo360.mobilesafe.core.BuildConfig;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginApplication;
import com.qihoo360.replugin.RePluginConfig;
import com.qihoo360.replugin.RePluginEventCallbacks;

/**
 * Created by mac on 2018/3/2.
 */

public class MyApplication extends RePluginApplication {

    //dagger component组件
    private AppComponent mAppComponent;
    private static MyApplication sInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        RePlugin.enableDebugger(base, BuildConfig.DEBUG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
        ItheimaHttp.init(this, AppConfig.BASEURL);

        sInstance=this;

        mAppComponent= DaggerAppComponent.builder()
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .userModel(new UserModel())
                .build();

//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                .build();
//        OkHttpUtils.initClient(okHttpClient);

        SharedPreferencesUtils.init(this);
    }

    //让其他类直接关联application
    public static MyApplication getsInstance() {
        return sInstance;
    }



    public AppComponent getAppComponent() {
        return mAppComponent;
    }



    private class HostEventCallbacks extends RePluginEventCallbacks {

        private static final String TAG = "HostEventCallbacks";

        public HostEventCallbacks(Context context) {
            super(context);
        }

        @Override
        public void onInstallPluginFailed(String path, InstallResult code) {
            // FIXME 当插件安装失败时触发此逻辑。您可以在此处做“打点统计”，也可以针对安装失败情况做“特殊处理”
            // 大部分可以通过RePlugin.install的返回值来判断是否成功
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onInstallPluginFailed: Failed! path=" + path + "; r=" + code);
            }
            super.onInstallPluginFailed(path, code);
        }

        @Override
        public void onStartActivityCompleted(String plugin, String activity, boolean result) {
            // FIXME 当打开Activity成功时触发此逻辑，可在这里做一些APM、打点统计等相关工作
            super.onStartActivityCompleted(plugin, activity, result);
        }
    }

    @Override
    protected RePluginConfig createConfig() {
        RePluginConfig c = new RePluginConfig();

        // 允许“插件使用宿主类”。默认为“关闭”
        c.setUseHostClassIfNotFound(false);

        // FIXME RePlugin默认会对安装的外置插件进行签名校验，这里先关掉，避免调试时出现签名错误
        c.setVerifySign(!BuildConfig.DEBUG);

        // 针对“安装失败”等情况来做进一步的事件处理
        c.setEventCallbacks(new HostEventCallbacks(this));

        // FIXME 若宿主为Release，则此处应加上您认为"合法"的插件的签名，例如，可以写上"宿主"自己的。
        // RePlugin.addCertSignature("AAAAAAAAA");

        // 在Art上，优化第一次loadDex的速度
        // c.setOptimizeArtLoadDex(true);
        return c;
    }


}
