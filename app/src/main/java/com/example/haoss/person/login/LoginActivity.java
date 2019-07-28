package com.example.haoss.person.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.resp.Resp;
import com.example.applibrary.resp.RespLogin;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.MD5Util;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.person.other.TermsOfService;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : HSS
 * time : 2019.5.10
 * blog : "好蔬食"
 * class : com.example.haoss.person.login
 * 登录
 */
public class LoginActivity extends BaseActivity {

    //账号
    @BindView(R.id.input_edit_name)
    EditText inputEditName;
    //密码隐藏
    @BindView(R.id.page_back)
    ImageView page_back;
    //密码隐藏
    @BindView(R.id.psw_yn_no_kejian)
    ImageView pswYnNoKejian;
    //密码
    @BindView(R.id.input_edit_psw)
    EditText inputEditPsw;
    //忘记密码
    @BindView(R.id.forget_psw_text)
    TextView forgetPswText;
    //
    @BindView(R.id.zhanghao_login_show)
    LinearLayout zhanghaoLoginShow;
    //账号/手机号输入
    @BindView(R.id.input_edit_phoneNum)
    EditText inputEditPhoneNum;
    //验证码输入
    @BindView(R.id.input_edit_code)
    EditText inputEditCode;
    //获取验证码
    @BindView(R.id.obtain_code)
    TextView obtainCode;
    //
    @BindView(R.id.phone_login_show)
    LinearLayout phoneLoginShow;
    //登录
    @BindView(R.id.login_button)
    Button loginButton;
    //验证码登录按钮
    @BindView(R.id.login_code_btn)
    TextView loginCodeBtn;
    @BindView(R.id.login_psw_btn)
    TextView loginPswBtn;
    //注册
    @BindView(R.id.login_registered)
    TextView loginRegistered;
    //微信登录
    @BindView(R.id.btn_wechat_login)
    Button btnWechatLogin;
    //qq登录
    @BindView(R.id.btn_qq_login)
    Button btnQqLogin;

    private Context mContext;
    private Timer timer;
    private static final int TIMECODE = 0X123;
    private long currentTime = 60 * 1000;
    boolean isCodeLogin = false;    //是否验证码登录 ==true：是
    String account, password; //账号/密码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        ButterKnife.bind(this);
        mContext = this;

        account = (String) SharedPreferenceUtils.getPreference(this, ConfigVariate.sPdbAccount, "S");
        password = (String) SharedPreferenceUtils.getPreference(this, ConfigVariate.sPdbPassword, "S");
        inputEditName.setText(account);
        inputEditPsw.setText(password);
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }

    @OnClick({R.id.page_back, R.id.input_edit_name, R.id.input_edit_psw, R.id.forget_psw_text, R.id.zhanghao_login_show,
            R.id.input_edit_phoneNum, R.id.input_edit_code, R.id.obtain_code, R.id.phone_login_show, R.id.login_button, R.id.login_code_btn,
            R.id.login_psw_btn, R.id.login_registered, R.id.btn_wechat_login, R.id.btn_qq_login, R.id.psw_yn_no_kejian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.page_back:
                finish();
                break;
            case R.id.input_edit_name:
                break;
            case R.id.psw_yn_no_kejian:
                if (inputEditPsw.getInputType() == 128) {
                    inputEditPsw.setInputType(129); //隐藏密码
                    pswYnNoKejian.setImageResource(R.mipmap.psw_bukejian_img);
                } else {
                    pswYnNoKejian.setImageResource(R.mipmap.psw_kejian_img);
                    inputEditPsw.setInputType(128); //显示密码
                    pswYnNoKejian.setVisibility(View.GONE);
                }
                break;
            case R.id.input_edit_psw:
                break;
            case R.id.forget_psw_text://忘记密码
                IntentUtils.startIntent(mContext, BackPswActivity.class);
                break;
            case R.id.zhanghao_login_show:
                break;
            case R.id.input_edit_phoneNum:
                break;
            case R.id.input_edit_code:
                break;
            case R.id.obtain_code:  //获取验证码
                huoquCode();
                break;
            case R.id.phone_login_show:
                break;
            case R.id.login_button: //登录
                startLogin();
                break;
            case R.id.login_code_btn://验证码登录
                isCodeLogin = true;
                phoneLoginShow.setVisibility(View.VISIBLE);
                zhanghaoLoginShow.setVisibility(View.GONE);
                loginPswBtn.setVisibility(View.VISIBLE);
                loginCodeBtn.setVisibility(View.GONE);
                break;
            case R.id.login_psw_btn://密码登录
                isCodeLogin = false;
                phoneLoginShow.setVisibility(View.GONE);
                zhanghaoLoginShow.setVisibility(View.VISIBLE);
                loginPswBtn.setVisibility(View.GONE);
                loginCodeBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.login_registered://注册 (线同意条款)
                IntentUtils.startIntent(1, mContext, TermsOfService.class);
                break;
            case R.id.btn_wechat_login: //微信登录
                IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, ConfigVariate.weChatAppID, true);
                // 将该app注册到微信
                mWxApi.registerApp(ConfigVariate.weChatAppID);
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo";//wechat_sdk_demo
                mWxApi.sendReq(req);
                break;
            case R.id.btn_qq_login: //qq登录
                break;
        }
    }


    //号码判断
    private boolean judgePhone(String phone) {
        if (phone.length() == 11) {
            if (StringUtils.validatePhoneNumber(phone)) {
                return true;
            } else {
                showToast("请正确输入手机号码！");
                return false;
            }
        } else {
            showToast("请输入11位手机号码！");
            return false;
        }
    }

    /**
     * 获取验证码
     */
    private void huoquCode() {
        String phone = inputEditPhoneNum.getText().toString();
        if (judgePhone(phone)) {
            String url = Netconfig.phoneLoginGetCode;
            HashMap<String, Object> map = new HashMap<>();
            map.put(ConfigHttpReqFields.sendAccount, phone);
            httpHander.okHttpMapPost(this, url, map, 1);
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
                    obtainCode.setText(currentTime / 1000 + "秒后重新获取");
                    obtainCode.setEnabled(false);
                    if (currentTime <= 0) {
                        currentTime = 60 * 1000;
                        timer.cancel();
                        obtainCode.setText("获取验证码");
                        obtainCode.setEnabled(true);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    //登录
    private void startLogin() {
        if (isCodeLogin) {    //验证码登录
            String phone = inputEditPhoneNum.getText().toString();
            String code = inputEditCode.getText().toString();
            if (!judgePhone(phone))
                return;
            if (!code.equals("")) {
                String url = Netconfig.phoneCodeLogin;
                HashMap<String, Object> map = new HashMap<>();
                map.put(ConfigHttpReqFields.sendAccount, phone);
                map.put("code", code);
                httpHander.okHttpMapPost(this, url, map, 2);
            } else
                showToast("验证码不能为空！");
        } else {  //账号登录
            account = inputEditName.getText().toString();
            password = inputEditPsw.getText().toString();
            if (!judgePhone(account))
                return;
            if (password != null && !password.equals("")) {
                String url = Netconfig.phoneLogin;
                HashMap<String, Object> map = new HashMap<>();
                map.put(ConfigHttpReqFields.sendAccount, account);
                map.put(ConfigHttpReqFields.sendPwd, MD5Util.getMD5String(password));
                httpHander.okHttpMapPost(this, url, map, 2);
            } else
                showToast("密码输入不能为空！");
        }
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1: //获取验证码
                    getResultStatus(msg.obj.toString(), new OnHttpCallback<Resp>() {
                        @Override
                        public void success(Resp result) {
                            tost("已发送");
                            obtainCode.setEnabled(false);
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    mHd.sendEmptyMessage(TIMECODE);
                                }
                            }, 0, 1000);
                        }

                        @Override
                        public void error(int code, String msg) {
                            tost(code + "," + msg);
                        }
                    });
                    break;

                case 2: //密码登录/验证码登录
                    Log.e("loginResult", msg + "");
                    getLogin(msg.obj.toString(), new OnHttpCallback<RespLogin.LoginInfo>() {
                        @Override
                        public void success(RespLogin.LoginInfo result) {
                            loginOk(result);
                        }

                        @Override
                        public void error(int code, String msg) {
                            tost(code + "," + msg);
                        }
                    });
                    break;

                case 3:
                    break;
            }
        }
    };

    //成功登录数据设置
    private void loginOk(RespLogin.LoginInfo result) {
        if (result != null) {

            SharedPreferenceUtils.setPreference(this, ConfigVariate.sPdbAccount, account, "S");
            SharedPreferenceUtils.setPreference(this, ConfigVariate.sPdbPassword, password, "S");
            SharedPreferenceUtils.setPreference(this, ConfigVariate.sPdbToken, result.getToken(), "S");
            SharedPreferenceUtils.setPreference(this, ConfigVariate.nickname, result.getNickname(), "S");
            SharedPreferenceUtils.setPreference(this, ConfigVariate.avatar, result.getAvatar(), "S");
            SharedPreferenceUtils.setPreference(this, ConfigVariate.uid, result.getUid(), "I");
            SharedPreferenceUtils.setPreference(this, ConfigVariate.status, result.getStatus(), "I");
            SharedPreferenceUtils.setPreference(this, ConfigVariate.level, result.getLevel(), "I");
            SharedPreferenceUtils.setPreference(this, ConfigVariate.now_money, result.getNow_money(), "S");
            SharedPreferenceUtils.setPreference(this, ConfigVariate.integral, result.getIntegral(), "S");
            SharedPreferenceUtils.setPreference(this, ConfigVariate.login, true, "B");

            tost("登录成功！");
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        } else
            tost("登录失败！");
    }
}
