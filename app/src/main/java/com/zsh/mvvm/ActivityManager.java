package com.zsh.mvvm;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：created by zsh19 on 2024/5/21 10:15
 * 邮箱：zsh1980794141@126.com
 */
public class ActivityManager {
    // 保存所有创建的Activity
    private final List<Activity> activityList = new ArrayList<>();
    public static ActivityManager mInstance;

    public static ActivityManager getInstance() {
        if (mInstance == null) {
            synchronized (ActivityManager.class) {
                if (mInstance == null) {
                    mInstance = new ActivityManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 移除Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityList.remove(activity);
        }
    }

    /**
     * 关闭所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
