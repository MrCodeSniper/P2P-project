package com.example.mac.back.utils;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.example.mac.back.activity.LoginActivity;
import com.example.mac.back.activity.WebViewActivity;
import com.orhanobut.logger.Logger;

/**
 * Created by mac on 2018/3/6.
 */

// 继承自Object类
public class AndroidtoJs extends Object {

    private Activity mcontext;


    public AndroidtoJs(Activity mcontext) {
        this.mcontext = mcontext;
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void register(String msg) {
        Logger.e("fasdfasfasf!!!!");
        IntentUtils.showIntent(mcontext,LoginActivity.class);
    }
}
