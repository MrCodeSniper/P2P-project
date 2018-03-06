package com.example.mac.back.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.itheima.systembartint.SystemBarTintManager;

/**
 * Created by mac on 2018/3/6.
 */

public class StatusBarUtils {

    private Activity mContext;
    public StatusBarUtils(Activity context) {
        this.mContext=context;
    }

    public void initStatuBar(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(mContext);
        tintManager.setStatusBarTintEnabled(true);
        //设置状态栏颜色
        tintManager.setStatusBarTintResource(id);
    }




    @TargetApi(19)
    private  void setTranslucentStatus(boolean on) {
        Window win = mContext.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
