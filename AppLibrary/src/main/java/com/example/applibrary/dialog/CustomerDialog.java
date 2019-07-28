package com.example.applibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.applibrary.R;

public class CustomerDialog extends Dialog {

    private String msg;
    private String confirmText;
    private String cancelText;
    private TextView confirmBtn;
    private TextView cancelBtn;
    private TextView MessageContent;

    public CustomerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
    }


}
