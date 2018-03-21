package com.example.mac.back.utils;

/**
 * Created by mac on 2018/3/19.
 */

public class PatchUtils {

    /**
     * native方法 使用路径为oldApkPath的apk与路径为patchPath的补丁包，合成新的apk，并存储于newApkPath
     *
     * 返回：0，说明操作成功
     *
     * @param oldApkPath 示例:/sdcard/old.apk
     * @param newApkPath 示例:/sdcard/new.apk
     * @param patchPath  示例:/sdcard/xx.patch
     * @return
     */
    public static native int patch(String oldApkPath, String newApkPath, String patchPath);

    static {
        System.loadLibrary("com_cundong_utils_PatchUtils");
    }
}