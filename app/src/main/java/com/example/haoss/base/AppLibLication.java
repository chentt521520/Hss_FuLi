package com.example.haoss.base;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.haoss.pay.GoodsBuyActivity;
import com.example.haoss.person.setting.systemsetting.PaySettingActivity;
import com.mob.MobSDK;
import com.pgyersdk.crash.PgyCrashManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.rong.imkit.RongIM;


public class AppLibLication extends Application {

    private String TAG = "AppLibLication";
    private IWXAPI api;

    public static AppLibLication appLibLication;

    @Override
    public void onCreate() {
        super.onCreate();
        appLibLication = this;
        Thread.setDefaultUncaughtExceptionHandler(onBlooey);
        PgyCrashManager.register(this);
        MobSDK.init(this);
        MultiDex.install(this);
        wechatLogin();
        RongIM.init(this);
    }

    private Thread.UncaughtExceptionHandler onBlooey = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.e(TAG, "Uncaught exception  ", ex);
            Log.e(TAG, ex.getCause().toString());
            goBlooey(ex);
        }
    };

    void goBlooey(Throwable t) {
        MyDialogTwoButton myDialogTwoButton = new MyDialogTwoButton(this, "客户端异常", new DialogOnClick() {
            @Override
            public void operate() {
            }

            @Override
            public void cancel() {
            }
        });
        myDialogTwoButton.show();
    }

    public void wechatLogin() {
//        IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, ConfigVariate.weChatAppID, true);
//        // 将该app注册到微信
//        mWxApi.registerApp(ConfigVariate.weChatAppID);
//        SendAuth.Req req = new SendAuth.Req();
//        req.scope = "snsapi_userinfo";
//        req.state = "diandi_wx_login";//wechat_sdk_demo
//        mWxApi.sendReq(req);

        // APP_ID 替换为你的应用从官方网站申请到的合法appId
// IWXAPI 是第三方app和微信通信的openapi接口
// 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, ConfigVariate.weChatAppID, false);
        api.registerApp(ConfigVariate.weChatAppID);
    }

    public static AppLibLication getInstance() {
        return appLibLication;
    }

    public String getToken() {
        String token;
        try {
            token = (String) SharedPreferenceUtils.getPreference(this, ConfigVariate.sPdbToken, "S");
        } catch (Exception e) {
            token = null;
        }
        return token;
    }

    public boolean isLogin() {
        boolean acc = (boolean) SharedPreferenceUtils.getPreference(this, ConfigVariate.login, "B");
        return acc;
    }

    public void logout() {
        SharedPreferenceUtils.setPreference(this, ConfigVariate.login, false, "B");
        SharedPreferenceUtils.setPreference(this, ConfigVariate.sPdbAccount, "", "S");
        SharedPreferenceUtils.setPreference(this, ConfigVariate.sPdbPassword, "", "S");
        SharedPreferenceUtils.setPreference(this, ConfigVariate.sPdbToken, "", "S");
        SharedPreferenceUtils.setPreference(this, ConfigVariate.nickname, "", "S");
        SharedPreferenceUtils.setPreference(this, ConfigVariate.avatar, "", "S");
        SharedPreferenceUtils.setPreference(this, ConfigVariate.uid, 0, "I");
        SharedPreferenceUtils.setPreference(this, ConfigVariate.status, -1, "I");
        SharedPreferenceUtils.setPreference(this, ConfigVariate.level, -1, "I");
        SharedPreferenceUtils.setPreference(this, ConfigVariate.now_money, 0, "D");
        SharedPreferenceUtils.setPreference(this, ConfigVariate.integral, 0, "D");
    }

}
