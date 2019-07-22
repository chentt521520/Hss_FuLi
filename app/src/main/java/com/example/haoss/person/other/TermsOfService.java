package com.example.haoss.person.other;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.person.login.RegisteredActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//服务条款界面
public class TermsOfService extends BaseActivity {

    TextView layout_termsofservice_text;    //内容
    LinearLayout layout_termsofservice_linear;  //按钮控件
    TextView layout_termsofservice_no, layout_termsofservice_ok; //拒绝、同意
    int flagIntent = 0; //0：个人中心进入，1：注册进入

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_register_agreement);

        init();
        setData();
    }

    private void init() {
        this.getTitleView().setTitleText("服务条款");
        layout_termsofservice_text = findViewById(R.id.layout_termsofservice_text);
        layout_termsofservice_linear = findViewById(R.id.layout_termsofservice_linear);
        layout_termsofservice_no = findViewById(R.id.layout_termsofservice_no);
        layout_termsofservice_ok = findViewById(R.id.layout_termsofservice_ok);

        layout_termsofservice_no.setOnClickListener(onClickListener);
        layout_termsofservice_ok.setOnClickListener(onClickListener);

        layout_termsofservice_text.setMovementMethod(ScrollingMovementMethod.getInstance());

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_termsofservice_no:  //拒绝
                    break;
                case R.id.layout_termsofservice_ok:  //同意
                    IntentUtils.startIntent(TermsOfService.this, RegisteredActivity.class); //注册
                    break;
            }
            finish();
        }
    };

    //获取隐私政策
    private String getPrivacyPolicy() {
        String s = "privacypolicy.js";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(s)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        return stringBuilder.toString().replace("var privacyPolicyData=", "");
    }

    //设置数据
    private void setData() {
        flagIntent = getIntent().getIntExtra(IntentUtils.intentActivityFlag, 0);
        String text = getPrivacyPolicy();
        if (text != null) {
            String[] texts = text.split("/n");
            if (texts.length > 0) {
                String ts = "";
                for (int i = 0; i < texts.length; i++) {
                    ts += texts[i] + "\n";
                }
                layout_termsofservice_text.setText(ts);
            }
        }
        if (flagIntent != 0)
            layout_termsofservice_linear.setVisibility(View.VISIBLE);
    }
}
