package com.example.applibrary.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * <p>风辨率转换工具类</p>
 *
 * @author guor
 * @version 2017-6-20 18:19
 */
public class DensityUtil {

    /**
     * <p>根据手机的分辨率从 dp 的单位转成为 px(像素)</p>
     *
     * @param context 上下文环境
     * @param dpValue dp值
     * @return px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * <p>根据手机的分辨率从 px(像素) 的单位转成为 dp</p>
     *
     * @param context 上下文环境
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * <p>根据手机的分辨率从 px(像素) 的单位转成为 sp</p>
     *
     * @param context 上下文环境
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * <p>根据手机的分辨率从 sp 的单位转成为 px(像素)</p>
     *
     * @param context 上下文环境
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * <p>获取屏幕宽度（像素值）</p>
     *
     * @param context 上下文环境
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * <p>获取屏幕高度（像素值）</p>
     *
     * @param context 上下文环境
     * @return 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
