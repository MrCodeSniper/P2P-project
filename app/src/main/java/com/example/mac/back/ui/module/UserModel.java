package com.example.mac.back.ui.module;

import android.content.Context;

import com.example.mac.back.bean.Product;
import com.example.mac.back.data.ProductApi;
import com.example.mac.back.ui.presenter.UpdatePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by mac on 2018/3/30.
 */

@Module
public class UserModel {



    @Provides
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true);// 失败重发
//                .addInterceptor(new HeaderInterceptor())
//                .addInterceptor(logging);
        return builder.build();
    }


    @Provides
    protected ProductApi provideapiService(OkHttpClient okHttpClient) {
        return ProductApi.getInstance(okHttpClient);
    }







}
