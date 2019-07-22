package com.example.applibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

//跳转
public class IntentUtils {

    //activity跳转标记 所有标记key或者作为一个int参数传递时可用此默认而不用intentActivityInt
    public static final String intentActivityFlag = "activityFlag";
    //activity跳转带参返回标记 默认RequestCode统一使用，非默认无用
    public static final int intentActivityRequestCode = 0x889;
    //携带对象数据跳转标记 仅做bundle一个对象参数key
    public static final String intentActivityInfo = "dataInfo";
    //携带int数据跳转标记 仅做一个int参数key
    public static final String intentActivityInt = "dataInt";
    //携带String参数标记 加数字用于集合中，否则仅做一个字符串参数key
    public static final String intentActivityString = "dataString";
    //特定跳转标记 通常用作指定跳转
    public static final int intentActivityAssign = 9999;

    public static final String pay = "pay.action";

    /**
     * 无携带跳转
     *
     * @param context
     * @param c
     */
    public static void startIntent(Context context, Class c) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        context.startActivity(intent);
    }

    /**
     * 带一个int标记的跳转
     *
     * @param flag    标记
     * @param context
     * @param c       to class
     */
    public static void startIntent(int flag, Context context, Class c) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        intent.putExtra(intentActivityFlag, flag);
        context.startActivity(intent);
    }

    /**
     * 带一个int标记并有参数返回的跳转
     *
     * @param flag
     * @param context
     * @param c
     */
    public static void startIntentForResult(int flag, Context context, Class c) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        intent.putExtra(intentActivityFlag, flag);
        ((Activity) context).startActivityForResult(intent, intentActivityRequestCode);
    }

    /**
     * 携带一个字符串跳转
     *
     * @param context
     * @param c
     */
    public static void startIntent(Context context, Class c, String s) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        intent.putExtra(intentActivityString, s);
        context.startActivity(intent);
    }

    /**
     * 携带一个int标记和字符串跳转
     *
     * @param context
     * @param c
     */
    public static void startIntent(int flag, Context context, Class c, String s) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        intent.putExtra(intentActivityFlag, flag);
        intent.putExtra(intentActivityString, s);
        context.startActivity(intent);
    }

    /**
     * 携带一个int标记和字符串并有参数返回的跳转
     *
     * @param context
     * @param c
     */
    public static void startIntentForResult(int flag, Context context, Class c, String s) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        intent.putExtra(intentActivityFlag, flag);
        intent.putExtra(intentActivityString, s);
        ((Activity) context).startActivityForResult(intent, intentActivityRequestCode);
    }

    /**
     * 带多个字符串参数的跳转
     *
     * @param context
     * @param c       to class
     * @param strings 一个key，一个value成对出现
     */
    public static void startIntent(Context context, Class c, String... strings) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        if (strings != null && strings.length > 0) {
            for (int i = 0; i < strings.length; i += 2) {
                if (i + 2 <= strings.length) {
                    String key = strings[i];
                    String value = strings[i + 1];
                    intent.putExtra(key == null ? "" : key, value == null ? "" : value);
                }
            }
        }
        context.startActivity(intent);
    }

    /**
     * 带一个int标记和多个字符串参数的跳转
     *
     * @param flag    标记
     * @param context
     * @param c       to class 下标从0 开始
     */
    public static void startIntent(int flag, Context context, Class c, String... strings) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        intent.putExtra(intentActivityFlag, flag);
        if (strings != null && strings.length > 0) {
            for (int i = 0; i < strings.length; i++) {
                String item = strings[i];
                intent.putExtra(intentActivityString + i, item == null ? "" : item);
            }
        }
        context.startActivity(intent);
    }

    /**
     * 带一个int标记和多个字符串并有参数返回的跳转
     *
     * @param flag    标记
     * @param context
     * @param c       to class 下标从0 开始
     */
    public static void startIntentForResult(int flag, Context context, Class c, String... strings) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        intent.putExtra(intentActivityFlag, flag);
        if (strings != null && strings.length > 0) {
            for (int i = 0; i < strings.length; i++) {
                String item = strings[i];
                intent.putExtra(intentActivityString + i, item == null ? "" : item);
            }
        }
        ((Activity) context).startActivityForResult(intent, intentActivityRequestCode);
    }

    /**
     * 带一个Bundle参数的跳转
     *
     * @param context
     * @param c
     * @param bundle  参数
     */
    public static void startIntent(Context context, Class c, Bundle bundle) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 带一个int标记和Bundle参数的跳转
     *
     * @param flag    标记
     * @param context
     * @param c
     * @param bundle  参数
     */
    public static void startIntent(int flag, Context context, Class c, Bundle bundle) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        intent.putExtra(intentActivityFlag, flag);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 带一个int标记和Bundle参数并有参数返回的跳转，无默认设置
     *
     * @param flag
     * @param context
     * @param c
     * @param bundle
     * @param requestCode
     */
    public static void startIntentForResult(int flag, Context context, Class c, Bundle bundle, int requestCode) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        intent.putExtra(intentActivityFlag, flag);
        if (bundle != null)
            intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    /**
     * 带一个int标记和Bundle参数并有参数返回的跳转,有默认设置
     *
     * @param flag
     * @param context
     * @param c
     * @param bundle
     */
    public static void startIntentForResult(int flag, Context context, Class c, Bundle bundle) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        intent.putExtra(intentActivityFlag, flag);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, intentActivityRequestCode);
    }

    /**
     * 携带一个标记参数跳转
     *
     * @param context
     * @param intent
     * @param flag
     */
    public static void startIntent(Context context, Intent intent, int flag) {
        if (intent != null) {
            intent.putExtra(intentActivityFlag, flag);
            context.startActivity(intent);
        }
    }

    /**
     * 携带一个标记和bundle参数跳转
     *
     * @param context
     * @param intent
     * @param flag
     * @param bundle
     */
    public static void startIntent(Context context, Intent intent, int flag, Bundle bundle) {
        if (intent != null) {
            intent.putExtra(intentActivityFlag, flag);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    /**
     * 携带一个标记和bundle并有参数返回的跳转
     *
     * @param context
     * @param intent
     * @param flag
     * @param bundle
     */
    public static void startIntentForResult(Context context, Intent intent, int flag, Bundle bundle) {
        if (intent != null) {
            intent.putExtra(intentActivityFlag, flag);
            intent.putExtras(bundle);
            ((Activity) context).startActivityForResult(intent, intentActivityRequestCode);
        }
    }

    /**
     * 映射intent
     *
     * @param context
     * @param className class name
     * @return
     */
    public static Intent getIntent(Context context, String className) {
        if (className == null)
            return null;
        try {
            Class c = Class.forName(className);
            return new Intent(context, c);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * 映射intent
     *
     * @param className class name
     * @return
     */
    public static Class getIntentClass(Context context, String className) {
        if (className == null)
            return null;
        try {
            Class c = Class.forName(className);
            return c;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * 跳转到首次启动
     *
     * @param context
     * @param c
     */
    public static void startIntentFrist(Context context, Class c) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 多次跳转
     *
     * @param flag
     * @param context
     * @param c
     */
    public static void startIntentRepeatedly(int flag, Context context, Class c) {
        if (c == null)
            return;
        Intent intent = new Intent(context, c);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(intentActivityFlag, flag);
        context.startActivity(intent);
    }

    /**
     * 多次跳转
     *
     * @param flag
     * @param context
     * @param intent
     */
    public static void startIntentRepeatedly(int flag, Context context, Intent intent) {
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(intentActivityFlag, flag);
            context.startActivity(intent);
        }
    }
}
