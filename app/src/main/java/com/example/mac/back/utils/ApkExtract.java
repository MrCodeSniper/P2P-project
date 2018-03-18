package com.example.mac.back.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 * Created by mac on 2018/3/18.
 */

public class ApkExtract {
    public static String extract(Context context) {
        context = context.getApplicationContext();
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String apkPath = applicationInfo.sourceDir;
        Log.d("hongyang", apkPath);
        return apkPath;
    }
}
