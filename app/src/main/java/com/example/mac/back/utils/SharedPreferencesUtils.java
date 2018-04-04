package com.example.mac.back.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.example.mac.back.application.MyApplication;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by mac on 2018/3/4.
 */

public class SharedPreferencesUtils {

    /**
     * 上下文
     */
    private static Context mContext;

    /**
     * 初始化SharedPreferences，必须使用该方法
     */
    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 抛出异常
     */
    private static void throwInit(){
        if (mContext == null) {
            throw new NullPointerException("在使用该方法前，需要使用init()方法，推荐将init()放入Application中");
        }
    }

    /**
     * 插入字符串
     * @param name 文件名
     * @param key key
     * @param value 值
     */
    public static void putString(String name, String key, String value) {
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    /**
     * 获取字符串
     * @param name 文件名
     * @param key key
     * @param defaultValue 没获取到时的默认值
     * @return 字符串
     */
    public static String getString(String name, String key, String defaultValue) {
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * 插入整型
     * @param name 文件名
     * @param key key
     * @param value 值
     */
    public static void putInt(String name, String key, int value) {
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    /**
     * 获取整型
     * @param name 文件名
     * @param key key
     * @param defaultValue 没获取到时的默认值
     * @return 整型
     */
    public static int getInt(String name, String key, int defaultValue) {
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    /**
     * 插入浮点型
     * @param name 文件名
     * @param key key
     * @param value 值
     */
    public static void putFloat(String name, String key, float value) {
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat(key, value);
        edit.apply();
    }

    /**
     * 获取浮点型
     * @param name 文件名
     * @param key key
     * @param defaultValue 没获取到时的默认值
     * @return 浮点型
     */
    public static float getFloat(String name, String key, float defaultValue) {
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    /**
     * 插入Long型
     * @param name 文件名
     * @param key key
     * @param value 值
     */
    public static void putLong(String name, String key, long value) {
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(key, value);
        edit.apply();
    }

    /**
     * 获取长整型
     * @param name 文件名
     * @param key key
     * @param defaultValue 没获取到时的默认值
     * @return 长整型
     */
    public static float getLong(String name, String key, long defaultValue) {
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    /**
     * 插入boolean型
     * @param name 文件名
     * @param key key
     * @param value 值
     */
    public static void putBoolean(String name, String key, boolean value) {
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    /**
     * 获取布尔型
     * @param name 文件名
     * @param key key
     * @param defaultValue 没获取到时的默认值
     * @return 布尔型
     */
    public static boolean getBoolean(String name, String key, boolean defaultValue) {
        throwInit();
        SharedPreferences sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }


    /** debug 环境下允许修改 sp文件的路径 */
    public static final boolean isDebug = true;

    public static final String FILE_PATH = MyApplication.getsInstance().getExternalFilesDir(null).getAbsolutePath();
    /**
     * @param context
     * @param fileName
     * @return isDebug = 返回修改路径(路径不存在会自动创建)以后的 SharedPreferences :%FILE_PATH%/%fileName%.xml<br/>
     * !isDebug = 返回默认路径下的 SharedPreferences : /data/data/%package_name%/shared_prefs/%fileName%.xml
     */
    private static SharedPreferences getSharedPreferences(Context context, String fileName) {
        if (isDebug) {
            try {
                // 获取ContextWrapper对象中的mBase变量。该变量保存了ContextImpl对象
                Field field = ContextWrapper.class.getDeclaredField("mBase");
                field.setAccessible(true);
                // 获取mBase变量
                Object obj = field.get(context);
                // 获取ContextImpl。mPreferencesDir变量，该变量保存了数据文件的保存路径
                field = obj.getClass().getDeclaredField("mPreferencesDir");
                field.setAccessible(true);
                // 创建自定义路径
                File file = new File(FILE_PATH);
                // 修改mPreferencesDir变量的值
                field.set(obj, file);
                // 返回修改路径以后的 SharedPreferences :%FILE_PATH%/%fileName%.xml
                return context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 返回默认路径下的 SharedPreferences : /data/data/%package_name%/shared_prefs/%fileName%.xml
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }
}