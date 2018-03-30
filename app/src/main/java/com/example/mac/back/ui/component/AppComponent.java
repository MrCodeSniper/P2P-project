package com.example.mac.back.ui.component;

import android.content.Context;

import com.example.mac.back.data.ProductApi;
import com.example.mac.back.ui.activity.MainActivity;
import com.example.mac.back.ui.module.AppModule;
import com.example.mac.back.ui.module.UserModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mac on 2018/3/30.
 */


@Component(modules = {AppModule.class,UserModel.class})
public interface AppComponent {
//    void inject(MainActivity activity);//将两个模块都注入到activity

    ProductApi getProductApi();
}
