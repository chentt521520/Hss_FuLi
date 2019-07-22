package com.example.applibrary.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.applibrary.R;

public class PasswordPopupWindow extends PopupWindow {


    private ImageView delete_dialog;
    private TextView tv_password_title;
    private PasswordEditText password_edit_text;
    private CustomerKeyboard custom_key_board;

    public PasswordPopupWindow(Context context) {
        super(context);

        View passwordView = View.inflate(context, R.layout.dialog_customer_keyboard, null);
        initViews(passwordView);
        // 设置SelectPicPopupWindow的View
        this.setContentView(passwordView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.FadeInPopWin);
    }

    /**
     * 初始化控件
     *
     * @param passwordView
     */
    private void initViews(View passwordView) {
        delete_dialog = (ImageView) passwordView.findViewById(R.id.delete_dialog);
        tv_password_title = (TextView) passwordView.findViewById(R.id.tv_password_title);
        password_edit_text = (PasswordEditText) passwordView.findViewById(R.id.password_edit_text);
        custom_key_board = (CustomerKeyboard) passwordView.findViewById(R.id.custom_key_board);
        delete_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordPopupWindow.this.dismiss();
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
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 显示popupWindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
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
