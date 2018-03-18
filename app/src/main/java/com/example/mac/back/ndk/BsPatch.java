package com.example.mac.back.ndk;

/**
 * 将本地apk和patch补丁合并
 * Created by mac on 2018/3/18.
 */

public class BsPatch {

    static {
        System.loadLibrary("bsdiff");
    }

    public static native int bspatch(String oldApk, String newApk, String patch);

}