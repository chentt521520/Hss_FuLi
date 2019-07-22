package com.example.applibrary.utils;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 窗口类工具
 */
public class ActivityController {
    public static List<Activity> act_list = new ArrayList<>();

    public static void addActivity(Activity activity) {
        act_list.add(activity);
    }

    public static void removeActivity(Activity activity) {
        act_list.remove(activity);
    }

    static int i;

    public static void finishAll() {

        for (Activity activity : act_list) {
            if (!activity.isFinishing()) {
                Log.d("controller", "=============次数：" + i++);
                activity.finish();
            }
        }


    }
}
