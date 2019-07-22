package com.example.applibrary.custom;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applibrary.R;

//吐司
public class ToastUtils {

    private static ToastUtils toastUtils;
    private static Toast mToast;
    Context context;
    View view;
    int flag;

    public static ToastUtils getToastUtils() {
        if (toastUtils == null)
            toastUtils = new ToastUtils();
        return toastUtils;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (mToast != null) {
                mToast.cancel();
                mToast = null;
            }
            mToast = new Toast(context);
            if (flag == 0) {
                mToast.setDuration(Toast.LENGTH_SHORT);
            } else {
                mToast.setDuration(Toast.LENGTH_LONG);
            }
//            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setView(view);
            mToast.show();
        }

        ;
    };

    public void showToast(Context context, String message) {
        showToast(context, message, 0);
    }

    public void showToast(Context context, String message, int flag) {
        this.context = context;
        this.flag = flag;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dialog_toast, null);
        TextView text = (TextView) view.findViewById(R.id.dialog_toast_message);
        text.setText(message);
        mHandler.sendEmptyMessage(0);
    }

    public void s(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void l(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
