package com.example.applibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.TextView;

public class TextViewUtils {

    /**
     * textview设置图片
     *
     * @param context
     * @param textView
     * @param imageId
     * @param flag     1~4：左上右下
     */
    public static void setImage(Context context, TextView textView, int imageId, int flag) {
        Drawable drawable = context.getResources().getDrawable(imageId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (flag) {
            case 1:
                textView.setCompoundDrawables(drawable, null, null, null);
                break;
            case 2:
                textView.setCompoundDrawables(null, drawable, null, null);
                break;
            case 3:
                textView.setCompoundDrawables(null, null, drawable, null);
                break;
            case 4:
                textView.setCompoundDrawables(null, null, null, drawable);
                break;
            default:
                textView.setCompoundDrawables(null, null, null, null);
                break;
        }
    }

    /**
     * textview设置图片
     *
     * @param context
     * @param textView
     * @param imageId
     * @param flag     1~4：左上右下
     * @param space    间距
     */
    public static void setImage(Context context, TextView textView, int imageId, int flag, int space) {
        Drawable drawable = context.getResources().getDrawable(imageId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (flag) {
            case 1:
                textView.setCompoundDrawables(drawable, null, null, null);
                break;
            case 2:
                textView.setCompoundDrawables(null, drawable, null, null);
                break;
            case 3:
                textView.setCompoundDrawables(null, null, drawable, null);
                break;
            case 4:
                textView.setCompoundDrawables(null, null, null, drawable);
                break;
        }
        textView.setCompoundDrawablePadding(space);
    }

    /**
     * textview设置图片
     *
     * @param context
     * @param textView
     * @param imageIdLeft   左图片ID
     * @param imageIdTop    上图片ID
     * @param imageIdRight  右图片ID
     * @param imageIdBottom 下图片ID
     */
    public static void setImage(Context context, TextView textView, int imageIdLeft, int imageIdTop, int imageIdRight, int imageIdBottom) {
        Drawable drawableLeft = imageIdLeft == 0 ? null : context.getResources().getDrawable(imageIdLeft);  //左
        Drawable drawableTop = imageIdTop == 0 ? null : context.getResources().getDrawable(imageIdTop); //上
        Drawable drawableRight = imageIdRight == 0 ? null : context.getResources().getDrawable(imageIdRight);   //右
        Drawable drawableBottom = imageIdBottom == 0 ? null : context.getResources().getDrawable(imageIdBottom);    //下

        if (drawableLeft != null)
            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        if (drawableTop != null)
            drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());
        if (drawableRight != null)
            drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        if (drawableBottom != null)
            drawableBottom.setBounds(0, 0, drawableBottom.getMinimumWidth(), drawableBottom.getMinimumHeight());

        textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }

    //textview取消图片
    public static void setImage(TextView textView) {
        textView.setCompoundDrawables(null, null, null, null);
    }

    /**
     * 设置字体粗体
     *
     * @param textView
     * @param b        ==true设置加粗
     */
    public static void setTextBold(TextView textView, boolean b) {
        textView.setTypeface(b == true ? Typeface.defaultFromStyle(Typeface.BOLD) : Typeface.defaultFromStyle(Typeface.NORMAL));
    }

    /**
     * 设置字体大小
     *
     * @param textView
     * @param b
     * @param tSize    b==tuue时的大小sp
     * @param fSize    b==false时的大小sp
     */
    public static void setTextSize(TextView textView, boolean b, float tSize, float fSize) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, b == true ? tSize : fSize);
    }

    /**
     * 设置字体颜色
     *
     * @param textView
     * @param b
     * @param tColor   b == true时的颜色
     * @param fColor   b == false时的颜色
     */
    public static void setTextColor(TextView textView, boolean b, String tColor, String fColor) {
        textView.setTextColor(Color.parseColor(b == true ? tColor : fColor));
    }

    /**
     * 设置字体加删除线
     *
     * @param textView
     */
    public static void setTextAddLine(TextView textView) {
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
