package com.example.haoss.person.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.haoss.base.AppLibLication;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.utils.FileUtils;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.MainActivity;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.person.setting.systemsetting.ChangePasswardActivity;
import com.example.haoss.person.setting.systemsetting.ChangePhoneActivity;
import com.example.haoss.person.setting.systemsetting.PaySettingActivity;

//系统设置
public class SystemSettingActivity extends BaseActivity {

    TextView systemsettingactivity_phone, systemsettingactivity_cache;   //手机号码，清除缓存
    MyDialogTwoButton myDialogExitLogin, myDialogClearCache;    //退出登录和清除缓存对话框

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_system_setting);
        init();
    }

    //初始化
    private void init() {
        this.getTitleView().setTitleText("系统设置");
        systemsettingactivity_phone = findViewById(R.id.systemsettingactivity_phone);
        systemsettingactivity_cache = findViewById(R.id.systemsettingactivity_cache);

        systemsettingactivity_phone.setOnClickListener(onClickListener);
        systemsettingactivity_cache.setOnClickListener(onClickListener);
        findViewById(R.id.systemsettingactivity_pay).setOnClickListener(onClickListener);   //支付设置
        findViewById(R.id.systemsettingactivity_password).setOnClickListener(onClickListener);   //账号密码
        findViewById(R.id.systemsettingactivity_guanyu).setOnClickListener(onClickListener);    //关于我
        findViewById(R.id.systemsettingactivity_versions).setOnClickListener(onClickListener);  //版本说明
        findViewById(R.id.systemsettingactivity_exitlogin).setOnClickListener(onClickListener); //退出登录
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.systemsettingactivity_pay:  //支付设置
                    IntentUtils.startIntent(SystemSettingActivity.this, PaySettingActivity.class);
                    break;
                case R.id.systemsettingactivity_password:  //账号密码
                    IntentUtils.startIntent(SystemSettingActivity.this, ChangePasswardActivity.class);
                    break;
                case R.id.systemsettingactivity_guanyu:  //关于我们
                    tost("关于我们");
                    break;
                case R.id.systemsettingactivity_versions:  //版本说明
                    tost("版本说明");
                    break;
                case R.id.systemsettingactivity_exitlogin:  //退出登录
                    dialogExitLogin();
                    break;
                case R.id.systemsettingactivity_phone:  //电话号码
                    IntentUtils.startIntent(SystemSettingActivity.this, ChangePhoneActivity.class);
                    break;
                case R.id.systemsettingactivity_cache:  //清除缓存
                    dialogClearCache();
                    break;
            }
        }
    };

    //退出登录对话框
    private void dialogExitLogin() {
        if (myDialogExitLogin == null) {
            myDialogExitLogin = new MyDialogTwoButton(this, "您是否确定退出当前登录？", new DialogOnClick() {
                @Override
                public void operate() {
                    tost("退出成功");
                    IntentUtils.startIntentFrist(SystemSettingActivity.this, MainActivity.class);
                    AppLibLication.getInstance().logout();
                }

                @Override
                public void cancel() {

                }
            });
        }
        myDialogExitLogin.show();
    }

    //清除缓存对话框
    private void dialogClearCache() {
        if (myDialogClearCache == null) {
            myDialogClearCache = new MyDialogTwoButton(this, "您是否要清除该应用所有缓存？", new DialogOnClick() {
                @Override
                public void operate() {
                    if (FileUtils.delAllFile(""))
                        tost("清除成功");
                }

                @Override
                public void cancel() {

                }
            });
        }
        myDialogClearCache.show();
    }
}
