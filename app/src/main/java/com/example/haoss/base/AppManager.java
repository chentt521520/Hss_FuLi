package com.example.haoss.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by gr on 2018/4/14.
 */

public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }

        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack();
        }

        activityStack.add(activity);
    }

    public Activity currentActivity() {
        Activity activity = (Activity) activityStack.lastElement();
        return activity;
    }

    public void finishActivity() {
        Activity activity = (Activity) activityStack.lastElement();
        this.finishActivity(activity);
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }

    }

    public void finishActivity(Class<?> cls) {
        Iterator var3 = activityStack.iterator();

        while (var3.hasNext()) {
            Activity activity = (Activity) var3.next();
            if (activity.getClass().equals(cls)) {
                this.finishActivity(activity);
            }
        }

    }

    public void finishAllActivity() {
        int i = 0;

        for (int size = activityStack.size(); i < size; ++i) {
            if (activityStack.get(i) != null) {
                ((Activity) activityStack.get(i)).finish();
            }
        }

        activityStack.clear();
    }

    public void AppExit(Context context) {
        try {
            this.finishAllActivity();
//            ActivityManager activityMgr = (ActivityManager)context.getSystemService("activity");
//            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception var3) {
            ;
        }

    }
}
