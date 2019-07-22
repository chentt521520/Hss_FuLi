package com.example.haoss.helper;

import android.app.Dialog;
import android.content.Context;

import com.example.haoss.R;


/**
 * Created by Renjy on 2018/3/6.
 * 加载中的对话框
 */

public class LoadingDialog {
    private Dialog mDialog;
    private Context mContext;
    private LoadingView mLoadingView;

    public LoadingDialog(Context context) {
        this(context, R.drawable.loading, "加载中");
    }

    public LoadingDialog(Context context, int icon, String message) {
        this.mContext = context;
        init(icon, message);
    }

    /**
     * 初始化加载中的对话框
     *
     * @param icon    图标
     * @param message 提示信息
     */
    private void init(int icon, String message) {
        mDialog = new Dialog(mContext, R.style.myDialogTheme);
        mLoadingView = new LoadingView(mContext);
        mLoadingView.setIcon(icon);
        mLoadingView.setText(message);
        mDialog.setContentView(mLoadingView);
    }

    /**
     * 设置加载中对话框旋转的图标
     *
     * @param icon 图标对应的资源id
     */
    public void setIcon(int icon) {
        mLoadingView.setIcon(icon);
    }

    /**
     * 设置加载中对话框的提示信息
     *
     * @param message 提示信息
     */
    public void setMessage(String message) {
        mLoadingView.setText(message);
    }

    /**
     * 显示对话框
     */
    public void show() {
        mLoadingView.startAnimation();
        mDialog.show();
    }

    /**
     * 隐藏对话框
     */
    public void dismiss() {
        if (null != mDialog && isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 当前对话框是否还在显示
     *
     * @return true 显示中  不显示
     */
    public boolean isShowing() {
        return mDialog.isShowing();
    }

    /**
     * 点击外部的时候是否取消对话框
     *
     * @param outsideTouchable true： 取消   false：不取消
     */
    public void setOutsideTouchable(boolean outsideTouchable) {
        mDialog.setCanceledOnTouchOutside(outsideTouchable);
    }

}
