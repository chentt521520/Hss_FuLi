package com.example.applibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.R;

public class PasswordEditDialog extends Dialog {

    private ImageView delete_dialog;
    private TextView tv_password_title;
    private PasswordEditText password_edit_text;
    private CustomerKeyboard custom_key_board;

    public PasswordEditDialog(Context context) {
        super(context);
        Window window = this.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        View popupView = View.inflate(context, R.layout.dialog_customer_keyboard, null);
        window.setContentView(popupView);
        initViews(popupView);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.windowAnimations = R.style.FadeInPopWin;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        PasswordEditDialog.this.show();
    }

    /**
     * 初始化控件
     *
     * @param popupView
     */
    private void initViews(View popupView) {
        delete_dialog = (ImageView) popupView.findViewById(R.id.delete_dialog);
        tv_password_title = (TextView) popupView.findViewById(R.id.tv_password_title);
        password_edit_text = (PasswordEditText) popupView.findViewById(R.id.password_edit_text);
        custom_key_board = (CustomerKeyboard) popupView.findViewById(R.id.custom_key_board);
        delete_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordEditDialog.this.dismiss();
            }
        });

    }

    /**
     * 返回PasswordEditText
     *
     * @return
     */
    public PasswordEditText getPasswordEdit() {
        if (password_edit_text == null) {
            return null;
        }
        return password_edit_text;

    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_password_title.setText(title);
        }
    }

    /**
     * 软键盘的监听回调
     *
     * @param customerKeyboardClickListener
     */
    public void customKeyBoard(CustomerKeyboard.CustomerKeyboardClickListener customerKeyboardClickListener) {
        custom_key_board.setOnCustomerKeyboardClickListener(customerKeyboardClickListener);
    }

    /**
     * 输入框监听回调
     *
     * @param passwordFullListener
     */
    public void setPasswordClickListeners(PasswordEditText.PasswordFullListener passwordFullListener) {
        password_edit_text.setOnPasswordFullListener(passwordFullListener);
    }
}
