package com.example.mac.back.base;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity管理类
 */
public class ActivityManager {


    public static ActivityManager mActivityManager;
    /**
     * 存放Activity的map
     */
    private List<Activity> mActivities = new ArrayList<Activity>();

    //将构造方法私有化，所以不能通构造方法来初始化ActivityManager
    private ActivityManager() {
    }

    

    //采用单例模式初始化ActivityManager，使只初始化一次
    public static ActivityManager getInstance() {
        if (mActivityManager == null) {
            mActivityManager = new ActivityManager();
        }
        return mActivityManager;
    }

    //添加activity
    public void addActivity(Activity activity) {
        if (!mActivities.contains(activity)) {
            mActivities.add(activity);
        }
    }

    //关闭指定的Activity
    public void removeActivity(Activity activity) {
        if (activity != null) {
            if (mActivities.contains(activity)) {
                mActivities.remove(activity);
            }
            activity.finish();
            activity = null;
        }
    }

    //将activity全部关闭掉
    public void clearAll() {
        for (Activity activity : mActivities) {
            activity.finish();
        }
    }

    //将activity全部关闭掉,除掉MainAcitiy
    public void clearOther() {

        for (Activity activity : mActivities) {
            Log.e("eerrree", activity.getClass().getSimpleName());

            if (activity.getClass().getSimpleName().equals("MainActivity")) {

                continue;
            }
            activity.finish();
        }
    }


}
