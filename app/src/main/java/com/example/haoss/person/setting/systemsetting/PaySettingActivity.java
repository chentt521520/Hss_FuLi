package com.example.haoss.person.setting.systemsetting;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.MD5Util;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.CustomTitleView;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.pay.GoodsPayActivity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

//支付设置
public class PaySettingActivity extends BaseActivity {
    int flagActivity;   //0：支付设置主页，1：设置支付密码，2：验证账号，3：设置成功
    LinearLayout paysettingactivity_payset, paysettingactivity_ok; //支付密码设置主页、设置成功页
    RelativeLayout paysettingactivity_paysetpass, paysettingactivity_acc;   //设置支付密码页、账户设置页
    //设置支付密码
    TextView paysettingactivity_paysetpass_one, paysettingactivity_paysetpass_two, paysettingactivity_paysetpass_three,
            paysettingactivity_paysetpass_four, paysettingactivity_paysetpass_five, paysettingactivity_paysetpass_six; //1~6
    EditText paysettingactivity_paysetpass_input;   //支付密码输入

    //账户设置
    EditText action_phone_input, action_phone_code;  //账户输入、验证码输入
    TextView action_phone_getcode, setPwd;    //获取验证码
    private CustomTitleView titleView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_setting_payment);
        init();
    }

    private void init() {
        titleView = this.getTitleView();
        titleView.setTitleText("支付设置");

        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (flagActivity) {
                    case 0:
                    case 3:
                        finish();
                        break;
                    case 1:
                        flagActivity = 0;
                        break;
                    case 2:
                        flagActivity = 0;
                        break;
                }
            }
        });

        boolean hasPwd = (boolean) SharedPreferenceUtils.getPreference(this, "PASSWORD", "B");
        paysettingactivity_payset = findViewById(R.id.paysettingactivity_payset);
        setPwd = findViewById(R.id.paysettingactivity_setpaypas);
        setPwd.setOnClickListener(onClickListener);    //设置支付密码
        if (hasPwd) {
            setPwd.setText("修改支付密码");
        } else {
            setPwd.setText("设置支付密码");
        }


        //flag == 1
        paysettingactivity_paysetpass = findViewById(R.id.paysettingactivity_paysetpass);
        paysettingactivity_paysetpass_one = findViewById(R.id.paysettingactivity_paysetpass_one);
        paysettingactivity_paysetpass_two = findViewById(R.id.paysettingactivity_paysetpass_two);
        paysettingactivity_paysetpass_three = findViewById(R.id.paysettingactivity_paysetpass_three);
        paysettingactivity_paysetpass_four = findViewById(R.id.paysettingactivity_paysetpass_four);
        paysettingactivity_paysetpass_five = findViewById(R.id.paysettingactivity_paysetpass_five);
        paysettingactivity_paysetpass_six = findViewById(R.id.paysettingactivity_paysetpass_six);
        paysettingactivity_paysetpass_input = findViewById(R.id.paysettingactivity_paysetpass_input);
        findViewById(R.id.paysettingactivity_paysetpass_sure).setOnClickListener(onClickListener);    //提交密码设置

        //flag == 2
        paysettingactivity_acc = findViewById(R.id.paysettingactivity_acc);
        action_phone_input = findViewById(R.id.action_phone_input);
        action_phone_code = findViewById(R.id.action_phone_code);
        action_phone_getcode = findViewById(R.id.action_phone_getcode);
        action_phone_getcode.setOnClickListener(onClickListener); //获取验证码
        findViewById(R.id.action_phone_next).setOnClickListener(onClickListener);    //提交账户验证

        //FLAG == 3
        paysettingactivity_ok = findViewById(R.id.paysettingactivity_ok);
        linearShowAndHide();
        payPasswordInput();
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //除返回其余要走网络
            switch (v.getId()) {
                case R.id.paysettingactivity_setpaypas:  //设置支付密码
                    flagActivity = 2;
                    titleView.setTitleText("设置支付密码");
                    linearShowAndHide();
                    break;
                case R.id.paysettingactivity_paysetpass_sure:  //设置提交
                    flagActivity = 3;
                    setPwd();
                    linearShowAndHide();
                    break;
                case R.id.action_phone_next:  //提交账户验证，下一步
                    if (TextUtils.isEmpty(action_phone_input.getText())) {
                        tost("请输入手机号");
                        return;
                    }
                    if (TextUtils.isEmpty(action_phone_code.getText())) {
                        tost("验证码");
                        return;
                    }
                    flagActivity = 1;
                    titleView.setTitleText("设置支付密码");
                    linearShowAndHide();
                    break;
                case R.id.action_phone_getcode:  //获取验证码
                    huoquCode();
                    break;
            }
        }
    };

    //界面显示和隐藏
    private void linearShowAndHide() {
        paysettingactivity_payset.setVisibility(flagActivity == 0 ? View.VISIBLE : View.GONE);
        paysettingactivity_paysetpass.setVisibility(flagActivity == 1 ? View.VISIBLE : View.GONE);
        paysettingactivity_acc.setVisibility(flagActivity == 2 ? View.VISIBLE : View.GONE);
        paysettingactivity_ok.setVisibility(flagActivity == 3 ? View.VISIBLE : View.GONE);
    }

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

    //支付密码输入
    private void payPasswordInput() {
        paysettingactivity_paysetpass_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //设置支付密码输入
    private void setText(CharSequence s) {
        paysettingactivity_paysetpass_one.setText(s.length() < 1 ? "" : s.charAt(0) + "");
        paysettingactivity_paysetpass_two.setText(s.length() < 2 ? "" : s.charAt(1) + "");
        paysettingactivity_paysetpass_three.setText(s.length() < 3 ? "" : s.charAt(2) + "");
        paysettingactivity_paysetpass_four.setText(s.length() < 4 ? "" : s.charAt(3) + "");
        paysettingactivity_paysetpass_five.setText(s.length() < 5 ? "" : s.charAt(4) + "");
        paysettingactivity_paysetpass_six.setText(s.length() < 6 ? "" : s.charAt(5) + "");
    }

    /**
     * 获取验证码
     */
    private void huoquCode() {
        String phone = action_phone_input.getText().toString();
        if (judgePhone(phone)) {
            String url = Netconfig.payPassCode;
            HashMap<String, Object> map = new HashMap<>();
            map.put("token", AppLibLication.getInstance().getToken());
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

    private void setPwd() {
        String code = action_phone_code.getText().toString();
        String pwd = paysettingactivity_paysetpass_input.getText().toString();

        if (TextUtils.isEmpty(code)) {
            tost("验证码不能为空！");
        } else {
            String url = Netconfig.addPayPass;
            HashMap<String, Object> map = new HashMap<>();
            map.put("token", AppLibLication.getInstance().getToken());
            map.put("code", code);
            map.put("pwd", MD5Util.getMD5String(pwd));
            httpHander.okHttpMapPost(this, url, map, 2);
        }
    }


    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1: //获取验证码
                    Map<String, Object> mapLogin = jsontoMap(msg.obj.toString());
                    if (mapLogin != null) {
                        if (getDouble(mapLogin, "code") == 200) {
                            countTimer.start();
                            tost("验证码已发送");
                        } else {
                            tost(mapLogin.get("msg") + "");
                        }
                    }
                    break;

                case 2: //密码设置成功
                    Map<String, Object> map = jsontoMap(msg.obj.toString());
                    if (map != null) {
                        if (getDouble(map, "code") == 200) {
                            tost("密码设置成功");
                        } else {
                            tost(map.get("msg") + "");
                        }
                    }
                    break;
            }
        }
    };
}
