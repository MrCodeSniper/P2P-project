package com.example.mac.back.activity;

import android.content.Intent;
import android.os.Handler;

import com.example.mac.back.base.BaseActivity;
import com.example.mac.back.utils.SharedPreferencesUtils;

/**
 * Created by mac on 2018/2/28.
 */

public class SplashActivity extends BaseActivity {
    private final int SPLASH_DISPLAY_LENGHT = 2000;//启屏显示时间ms单位
    private Handler mhandler=new Handler();


    @Override
    protected void initUI() {

        if(SharedPreferencesUtils.getBoolean("cat","isLogin",false)){
            mhandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            },SPLASH_DISPLAY_LENGHT);
        }else {
            startActivity(new Intent(SplashActivity.this, FirstInActivity.class));
            finish();
        }





    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }




}
