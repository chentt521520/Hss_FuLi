package com.example.haoss.person.setting.systemsetting;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;

import java.util.HashMap;

//更换手机号码
public class ChangePhoneActivity extends BaseActivity {

    //账户设置
    EditText action_phone_input, action_phone_code;  //账户输入、验证码输入
    TextView action_phone_getcode;    //获取验证码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_phone_modify);
        init();
    }

    private void init() {
        this.getTitleView().setTitleText("更换手机号码");

        findViewById(R.id.action_phone_hint).setVisibility(View.INVISIBLE);
        action_phone_input = findViewById(R.id.action_phone_input);
        action_phone_code = findViewById(R.id.action_phone_code);
        action_phone_getcode = findViewById(R.id.action_phone_getcode);
        action_phone_getcode.setOnClickListener(onClickListener); //获取验证码
        findViewById(R.id.action_phone_next).setOnClickListener(onClickListener);    //提交账户验证

        action_phone_input.setHint("请输入新的手机号码");
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_phone_getcode:  //获取验证码
                    huoquCode();
                    break;
                case R.id.action_phone_next:  //提交
                    submit();
                    tost("提交");
                    break;
            }
        }
    };

    /**
     * 计时器
     */
    private CountDownTimer countTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long endTime) {
            action_phone_getcode.setText(endTime / 1000 + "秒后可重发");
            action_phone_getcode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            action_phone_getcode.setText("获取验证码");
            action_phone_getcode.setEnabled(true);
        }
    };

    //获取验证码
    private void huoquCode() {
        String phone = action_phone_input.getText().toString();
        if (judgePhone(phone)) {
            countTimer.start();
            String url = Netconfig.registerGetCode;
            HashMap<String, Object> map = new HashMap<>();
            map.put(ConfigHttpReqFields.sendAccount, phone);
            httpHander.okHttpMapPost(this, url, map, 1);
        }
    }

    //号码判断
    private boolean judgePhone(String phone) {
        if (phone.length() == 11) {
            if (StringUtils.validatePhoneNumber(phone)) {
                return true;
            } else {
                tost("请正确输入手机号码！");
                return false;
            }
        } else {
            tost("请输入11位手机号码！");
            return false;
        }
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1: //验证码
                    tost("验证码已发送");
                    break;
                case 2: //修改
                    break;
            }
        }
    };

    //提交
    private void submit() {

    }
}
