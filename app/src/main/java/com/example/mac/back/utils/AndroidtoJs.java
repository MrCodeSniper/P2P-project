package com.example.mac.back.utils;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.example.mac.back.ui.activity.LoginActivity;
import com.orhanobut.logger.Logger;

/**
 * Created by mac on 2018/3/6.
 */

// 继承自Object类
public class AndroidtoJs extends Object {

    private Activity mcontext;
    private WebView view;


    public AndroidtoJs(Activity mcontext, WebView view) {
        this.mcontext = mcontext;
        this.view=view;
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @JavascriptInterface
    public void register(String msg) {
        if (SharedPreferencesUtils.getBoolean("cat", "logined", false)) {

            //请求url成功后返回

            view.post(new Runnable() {
                @Override
                public void run() {
                    view.loadUrl("javascript:setToken(" + "'hello world'" + ")");
                }
            });



        }else {
            IntentUtils.showIntent(mcontext,LoginActivity.class);
        }
    }
}
