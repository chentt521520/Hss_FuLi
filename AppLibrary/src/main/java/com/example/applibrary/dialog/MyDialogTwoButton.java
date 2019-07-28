package com.example.applibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.applibrary.R;
import com.example.applibrary.dialog.interfac.DialogOnClick;

//2个按钮的自定义对话框
public class MyDialogTwoButton extends Dialog {

    public MyDialogTwoButton(Context context) {
        super(context);
    }

    Context context;
    String text;    //消息内容
    DialogOnClick dialogOnClick;    //对话框操作监听
    TextView textView;  //消息内容
    private String confirmText;
    private String cancelText;

    public MyDialogTwoButton(Context context, String text, DialogOnClick dialogOnClick) {
        super(context, R.style.dialog);
        this.context = context;
        this.text = text;
        this.dialogOnClick = dialogOnClick;
    }

    public MyDialogTwoButton(Context context, String text, String confirmText, String cancelText, DialogOnClick dialogOnClick) {
        super(context, R.style.dialog);
        this.context = context;
        this.text = text;
        this.confirmText = confirmText;
        this.cancelText = cancelText;
        this.dialogOnClick = dialogOnClick;
    }

    //更新消息内容
    public void setMsg(String text) {
        textView.setText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.dialog_buttontwo);
        textView = findViewById(R.id.dialog_buttontwo_text);
        TextView sureButton = findViewById(R.id.dialog_buttontwo_sure);
        TextView cancelButton = findViewById(R.id.dialog_buttontwo_cancel);
        setMsg(text);
        if (!TextUtils.isEmpty(confirmText)) {
            sureButton.setText(confirmText);
        }
        if (!TextUtils.isEmpty(cancelText)) {
            cancelButton.setText(cancelText);
        }
        sureButton.setOnClickListener(onClickListener);
        cancelButton.setOnClickListener(onClickListener);
    }

    //操作监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.dialog_buttontwo_sure) {
                dialogOnClick.operate();
            }
            if (v.getId() == R.id.dialog_buttontwo_cancel) {
                dialogOnClick.cancel();
            }
            dismiss();
        }
    };

}
