package com.example.haoss.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.person.login.LoginActivity;


/**
 * 初始化图片页面
 */

public class Splash extends BaseActivity {

    private final long SPLASH_DELAY_MILLONS = 3000;
    private final int TO_GUIDE = 0X111;
    private final int TO_MAIN = 0X123;
    private SharedPreferences sp;
    private Intent in;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        //实现全屏沉浸式
//        if (Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }


//        OkhttpTool.getOkhttpTool().get(UrlConfig.SPLASH_NEWS, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                String s = response.body().string();
//                LogUtils.getLogUtils().d("lht", "=============response:" + s);
//            }
//        });
        sp = getSharedPreferences("splash", MODE_PRIVATE);
        if (sp.getBoolean("FirstIn", true)) {
            hd.sendEmptyMessageDelayed(TO_GUIDE, SPLASH_DELAY_MILLONS);
            SharedPreferences.Editor et = sp.edit();
            et.putBoolean("FirstIn", false);
            et.commit();
        } else {
            hd.sendEmptyMessageDelayed(TO_MAIN, SPLASH_DELAY_MILLONS);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TO_GUIDE:
                    // in = new Intent(Splash.this,GuidePage.class);
                    break;
                case TO_MAIN:
                    in = new Intent(Splash.this, LoginActivity.class);
                    break;
            }
            if (in != null) {
                startActivity(in);
                finish();
            }
        }
    };
}
