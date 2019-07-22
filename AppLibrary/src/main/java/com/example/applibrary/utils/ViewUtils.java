package com.example.applibrary.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.R;

//view 设置
public class ViewUtils {

    /**
     * 设置LinearLayout背景颜色
     *
     * @param activity
     * @param viewId
     * @param color    颜色
     * @return
     */
    public static LinearLayout setLinearLayout(Activity activity, int viewId, String color) {
        LinearLayout linearLayout = activity.findViewById(viewId);
        linearLayout.setBackgroundColor(Color.parseColor(color));
        return linearLayout;
    }

    public static RelativeLayout setRelativeLayout(Activity activity, int viewId, String color) {
        RelativeLayout relativeLayout = activity.findViewById(viewId);
        relativeLayout.setBackgroundColor(Color.parseColor(color));
        return relativeLayout;
    }


    /**
     * 设置Textview 内容
     *
     * @param activity
     * @param viewId
     * @param text     内容
     * @return
     */
    public static TextView setTextView(Activity activity, int viewId, String text) {
        TextView titleText = activity.findViewById(viewId);
        titleText.setText(text);
        return titleText;
    }

    /**
     * 设置textview 内容和是否可见
     *
     * @param activity
     * @param viewId
     * @param text       内容
     * @param visibility 是否显示
     * @return
     */
    public static TextView setTextView(Activity activity, int viewId, String text, int visibility) {
        TextView titleText = activity.findViewById(viewId);
        titleText.setText(text);
        titleText.setVisibility(visibility);
        return titleText;
    }

    /**
     * 设置imageview 是否可见
     *
     * @param activity
     * @param viewId
     * @param visibility
     * @return
     */
    public static ImageView setImageView(Activity activity, int viewId, int visibility) {
        ImageView imageView = activity.findViewById(viewId);
        imageView.setVisibility(visibility);
        return imageView;
    }

    /**
     * 设置imageview 是否可见和初始图片
     *
     * @param activity
     * @param viewId
     * @param visibility 是否可见
     * @param imageId    初始图片id
     * @return
     */
    public static ImageView setImageView(Activity activity, int viewId, int visibility, int imageId) {
        ImageView imageView = activity.findViewById(viewId);
        imageView.setImageResource(imageId);
        imageView.setVisibility(visibility);
        return imageView;
    }
}
