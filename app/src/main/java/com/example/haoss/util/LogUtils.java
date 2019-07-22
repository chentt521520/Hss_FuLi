package com.example.haoss.util;

import android.util.Log;

/**
 * Created by lht on 2017/2/28.
 */

public class LogUtils {

    private static LogUtils logUtils;
    public static boolean showLog;

    public static LogUtils getLogUtils() {
        if (null == logUtils) {
            synchronized (LogUtils.class) {
                if (null == logUtils) {
                    logUtils = new LogUtils();
                }
            }
        }
        return logUtils;
    }


    public void v(String tag, String msg) {
        if (showLog) {
            Log.v(tag, msg);
        }
    }

    public void d(String tag, String msg) {
        if (showLog) {
            Log.d(tag, msg);
        }
    }

    public void i(String tag, String msg) {
        if (showLog) {
            Log.i(tag, msg);
        }
    }

    public void w(String tag, String msg) {
        if (showLog) {
            Log.w(tag, msg);
        }
    }

    public void e(String tag, String msg) {
        if (showLog) {
            Log.e(tag, msg);
        }
    }


}
