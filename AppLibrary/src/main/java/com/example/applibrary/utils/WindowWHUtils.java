package com.example.applibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

//窗口宽高设置
public class WindowWHUtils {
    //activity对话框宽高
    public static void setDisplay(Activity activity, double h, double w) {
        Window dialogWindow = activity.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = activity.getWindow().getAttributes();
        p.height = (int) (d.getHeight() * h);
        p.width = (int) (d.getWidth() * w);
        dialogWindow.setAttributes(p);
    }

    //dialog对话框宽高
    public static void setaDialogDisplay(Context context, Dialog dialog, double w, double h) {
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = ((Activity) context).getWindow().getAttributes(); // 获取对话框当前的参数值
        p.gravity = Gravity.BOTTOM;
        p.height = (int) (d.getHeight() * h); // 0.5
        p.width = (int) (d.getWidth() * w); // 0.8
        dialog.getWindow().setAttributes(p);
    }

    //dialog对话框宽高28显示，6.0不显示
    public static void setDialogDisplay(Context context, Dialog dialog, int gravity, double w, double h) {
        Window window = dialog.getWindow();
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * h); // 改变的是dialog框在屏幕中的位置而不是大小
        p.width = (int) (d.getWidth() * w); // 宽度设置为屏幕的
        p.gravity = gravity;    //在屏幕的位置
        window.setAttributes(p);
    }

    //获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
