package com.example.haoss.person.login;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.MyDialogOneButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.httpUtils.OkhttpTool;
import com.example.applibrary.utils.MD5Util;
import com.example.applibrary.utils.MapUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.base.BaseStatusBarActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author : HSS
 * time : 2019.5.13
 * blog : "好蔬食"
 * class : com.example.haoss.person.login
 * 注册
 */
public class RegisteredActivity extends BaseActivity {


    @BindView(R.id.page_back)
    ImageView back;
    @BindView(R.id.reg_input_edit_phone)
    EditText regInputEditPhone;
    //输入验证码
    @BindView(R.id.reg_input_edit_code)
    EditText regInputEditCode;
    //获取验证码
    @BindView(R.id.reg_huoqu_code)
    TextView regHuoquCode;
    //注册按钮
    @BindView(R.id.reg_btn_reg)
    Button regBtnReg;
    private Timer timer;
    private static final int TIMECODE = 0X123;
    private long currentTime = 60 * 1000;
    EditText reg_input_edit_password, reg_input_edit_password2;  //密码，确认密码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg_page);
        ButterKnife.bind(this);

        reg_input_edit_password = findViewById(R.id.reg_input_edit_password);
        reg_input_edit_password2 = findViewById(R.id.reg_input_edit_password);
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

    /**
     * 获取验证码
     */
    private void huoquCode() {
        String phone = regInputEditPhone.getText().toString();
        if (judgePhone(phone)) {
            regHuoquCode.setEnabled(false);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mHd.sendEmptyMessage(TIMECODE);
                }
            }, 0, 1000);
            String url = Netconfig.registerGetCode;
            HashMap<String, Object> map = new HashMap<>();
            map.put(ConfigHttpReqFields.sendAccount, phone);
            httpHander.okHttpMapPost(this, url, map, 1);
        }
    }

    //注册
    private void startRegister() {
        String code = regInputEditCode.getText().toString();
        String phone = regInputEditPhone.getText().toString();
        if (!judgePhone(phone)) {
            return;
        }
        if (code != null && !code.equals("")) {
            String psw = reg_input_edit_password.getText().toString();
            if (psw.length() < 6) {
                tost("密码长度不能低于6位");
                return;
            }
            String psw2 = reg_input_edit_password2.getText().toString();
            if (!psw2.equals(psw)) {
                tost("两次密码输入不一致");
                return;
            }
            String url = Netconfig.register;
            HashMap<String, Object> map = new HashMap<>();
            map.put(ConfigHttpReqFields.sendAccount, phone);
            map.put(ConfigHttpReqFields.sendCode, code);
            String ppwd = MD5Util.getMD5String("");
            map.put(ConfigHttpReqFields.sendPwd, "hss" + code);
            httpHander.okHttpMapPost(this, url, map, 2);
        } else
            tost("验证码不能为空！");
    }

    @OnClick({R.id.reg_huoqu_code, R.id.reg_btn_reg, R.id.page_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reg_huoqu_code:   //获取验证码
                huoquCode();
                break;
            case R.id.reg_btn_reg:  //注册
                startRegister();
                break;
            case R.id.page_back:
                finish();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIMECODE:
                    currentTime -= 1000;
                    regHuoquCode.setText(currentTime / 1000 + "秒后重新获取");
                    regHuoquCode.setEnabled(false);
                    if (currentTime <= 0) {
                        currentTime = 60 * 1000;
                        timer.cancel();
                        regHuoquCode.setText("获取验证码");
                        regHuoquCode.setEnabled(true);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    //{"code":200,"msg":"短信发送成功","data":[],"count":0}
                    Map<String, Object> mapLogin = jsontoMap(msg.obj.toString());
                    if (mapLogin != null)
                        tost(mapLogin.get("msg") + "");
                    break;
                case 2:
                    Map<String, Object> map = jsontoMap(msg.obj.toString());
                    if (map != null) {
                        okDialog("恭喜您，注册成功！");
                    }
                    break;
                case 3:
                    break;
            }
        }
    };

    //注册成功对话框
    private void okDialog(String text) {
        MyDialogOneButton myDialogOneButton = new MyDialogOneButton(this, text, new DialogOnClick() {
            @Override
            public void operate() {
                finish();
            }

            @Override
            public void cancel() {

            }
        });
        myDialogOneButton.show();
    }
}

