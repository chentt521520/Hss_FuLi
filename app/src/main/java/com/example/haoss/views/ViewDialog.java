package com.example.haoss.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Created by lht on 2017/3/2.
 */

public class ViewDialog extends Dialog {
    private ViewDialog dialog;

    public ViewDialog(Context context, int themeResId) {
        super(context, themeResId);
        dialog = this;
    }

    public void dismiss2() {
        handler.sendEmptyMessageDelayed(0, 1000);
//        dialog.dismiss();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dialog.dismiss();
        }
    };

    public ViewDialog show2() {
        dialog.show();
        return dialog;
    }

}
