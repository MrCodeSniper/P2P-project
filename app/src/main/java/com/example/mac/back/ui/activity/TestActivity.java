package com.example.mac.back.ui.activity;

import android.util.LruCache;

import com.example.mac.back.R;
import com.example.mac.back.base.BaseActivity;
import com.example.mac.back.config.AppConfig;
import com.example.mac.back.utils.SSLSocketClient;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.security.cert.X509Certificate;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.internal.Util.verifyAsIpAddress;

/**
 * Created by mac on 2018/3/24.
 */

public class TestActivity extends BaseActivity {

    private OkHttpClient mHttpClient;


    @Override
    protected void initUI() {
        setContentView(R.layout.blank);
    }

    @Override
    protected void initData() {




    }

    @Override
    protected void initListener() {

    }



}
