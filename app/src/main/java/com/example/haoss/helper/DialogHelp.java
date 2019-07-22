package com.example.haoss.helper;

import android.app.Activity;

/**
 * alert confirm dialog
 *
 * @author likoha
 */
public class DialogHelp {

    private static LoadingDialog topLoadingDialog;


    /**
     * 显示加载中对话框
     *
     * @param context 上下文
     * @param message 提示信息
     */
    public static void showLoading(Activity context, String... message) {
        if (null == context || context.isFinishing()) {
            return;
        }

        if (null != topLoadingDialog && topLoadingDialog.isShowing()) {
            topLoadingDialog.dismiss();
        }
        topLoadingDialog = new LoadingDialog(context);
        if (message != null && message.length > 0) {//设置提示信息
            topLoadingDialog.setMessage(message[0]);
        }
        topLoadingDialog.setOutsideTouchable(false);
        topLoadingDialog.show();
    }

    /**
     * 隐藏对话框
     */
    public static void hideLoading() {
        if (topLoadingDialog != null) {
            topLoadingDialog.dismiss();
            topLoadingDialog = null;
        }
    }


    /**
     * 隐藏对话框
     */
    public static void hideLoading(Activity activity) {
        if (activity.isFinishing()) {
            return;
        }
        if (topLoadingDialog != null) {
            topLoadingDialog.dismiss();
            topLoadingDialog = null;
        }
    }

    public static boolean isShow() {
        return topLoadingDialog != null && topLoadingDialog.isShowing();
    }
}
