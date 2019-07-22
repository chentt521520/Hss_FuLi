package com.example.haoss.pay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.utils.MD5Util;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.applibrary.widget.CustomerKeyboard;
import com.example.applibrary.widget.PasswordEditDialog;
import com.example.applibrary.widget.PasswordEditText;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.pay.aliapi.PayAliPay;
import com.example.haoss.pay.wxapi.PayWeChar;
import com.example.haoss.person.dingdan.OrderListActivity;
import com.example.haoss.person.setting.systemsetting.PaySettingActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

//商品支付界面
public class GoodsPayActivity extends BaseActivity {

    TextView goodspayactivity_money;    //金额
    //微信支付图片、支付宝支付图片、余额支付图片
    ImageView goodspayactivity_wechatimage, goodspayactivity_alipayimage, goodspayactivity_balanceimage;
    String payType = ""; //支付类型 weixin、ali、yue、offline
    final String weiXin = "weixin", aliPay = "ali", yuEPay = "yue";
    String money = "";  //金额
    private AppLibLication application;
    private String orderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_payment);

        registerReceiver(mReceiver, new IntentFilter(IntentUtils.pay));
        application = AppLibLication.getInstance();
        init();
        getIntentData();
    }

    //获取跳转数据
    private void getIntentData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        money = intent.getStringExtra("price");
        payType = intent.getStringExtra("payType");
        setType();
    }

    private void init() {
        this.getTitleView().setTitleText("在线支付");

        goodspayactivity_money = findViewById(R.id.goodspayactivity_money);
        goodspayactivity_wechatimage = findViewById(R.id.goodspayactivity_wechatimage);
        goodspayactivity_alipayimage = findViewById(R.id.goodspayactivity_alipayimage);
        goodspayactivity_balanceimage = findViewById(R.id.goodspayactivity_balanceimage);

        goodspayactivity_wechatimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = weiXin;
                setType();
            }
        });
        goodspayactivity_alipayimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = aliPay;
                setType();
            }
        });
        goodspayactivity_balanceimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = yuEPay;
                setType();
            }
        });

        findViewById(R.id.goodspayactivity_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPay();
            }
        });  //立即支付
    }

    //设置选中 flag==1:微信，==2：支付宝，==3：余额
    private void setType() {
        goodspayactivity_wechatimage.setImageResource(TextUtils.equals(payType, weiXin) ? R.mipmap.checked_true : R.mipmap.checked_false);
        goodspayactivity_alipayimage.setImageResource(TextUtils.equals(payType, aliPay) ? R.mipmap.checked_true : R.mipmap.checked_false);
        goodspayactivity_balanceimage.setImageResource(TextUtils.equals(payType, yuEPay) ? R.mipmap.checked_true : R.mipmap.checked_false);
        goodspayactivity_money.setText(money);
    }

    //立即支付
    private void goPay() {
        String url = null;

        switch (payType) {
            case yuEPay:
                checkYE();
                return;
            case weiXin:
                url = Netconfig.payWeChat;
                break;
            case aliPay:
                url = Netconfig.payAliPay;
                break;

        }
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        httpHander.okHttpMapPost(this, url, map, 1);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    //提交订单含支付类型信息
                    //{"code":200,"msg":"微信支付错误返回：JSAPI支付必须传openid","data":{"status":"PAY_ERROR","result":{"orderId":"wx2019061117490710001","key":"9fed043736bede22094d2db29412870e"}},"count":0}
                    //微信{"code":200,"msg":"ok","data":{"appid":"wxf82e7cb39cd3de8d","partnerid":"1518247781","prepayid":"wx12175942121455e67805fb861670962100","package":"Sign=WXPay","noncestr":"6OCZ7jH5cuT46A4iIss1eSP4l1f46ZIf","timestamp":1560333582,"sign":"EACE856A2E57390057F8325D45ADB681"},"count":0}
                    //阿里{"code":200,"msg":"ok","data":"alipay_sdk=alipay-sdk-php-20180705&app_id=2018103061953354&biz_content=%7B%22body%22%3A%22%5Cu8ba2%5Cu5355%5Cu652f%5Cu4ed8%22%2C%22subject%22%3A%22%5Cu8ba2%5Cu5355%5Cu652f%5Cu4ed8%22%2C%22out_trade_no%22%3A%22wx2019061217594110018%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A10.01%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fapi.haoshusi.com%2Fapi%2FCallback%2FaliPayBack&sign_type=RSA2&timestamp=2019-06-12+18%3A04%3A21&version=1.0&sign=fUW9D8ftqUFBhg5yb0p6lV3KqOKa2MtqcOn28PxTOT8NQoCAuYhHEJbNvL9T%2FiZ8UqJJ0iPl507r1vFPpFpGEvuRAqmeAHAWMmE8AbuIaxIDfi%2FEJtIjqpE7YPTb%2F6GGA3hY1pkiSZMY5EtN6z13IWpwVOxjN%2F8169b3jbC1bFlKVFe%2FuPn2%2BisEGMQPfNqSsmrv6T%2BEB6DSgfEQPMotFE6cek%2FZ3ztnjBFQ0XYFkPLUYb3wS0C0snvc0PsknYUrtI0hEqFhR7jm4j49HZ1m9%2FN2vtyljeq9fTIPLReT2xh15j1XwiTogSVdpQj60FfwzziIDJDJldsZfmbMbwK%2FCg%3D%3D","count":0}
                    //余额{"code":200,"msg":"余额不足10.01","data":{"status":"PAY_DEFICIENCY","result":{"key":"ce0ad917ccb05cf1e27369eeb11fe8bd"}},"count":0}
                    //{"code":200,"msg":"ok","data":{"appid":"wxf82e7cb39cd3de8d","partnerid":"1518247781","prepayid":"wx17105002427861569af7ffcb1276945300","package":"Sign=WXPay","noncestr":"be1vuVDoC9BlgjQvru5Ht6n88OmoKMhY","timestamp":1560739802,"sign":"642E022EB1098367347D178B85C331EA"},"count":0}

                    if (payType.equals(weiXin)) {
                        try {
                            JSONObject jsonObject = new JSONObject(msg.obj.toString());
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            if (jsonObject1 != null) {
                                String sign = jsonObject1.get("sign") + "";
                                String partnerid = jsonObject1.get("partnerid") + "";
                                String prepayid = jsonObject1.get("prepayid") + "";
                                String noncestr = jsonObject1.get("noncestr") + "";
                                String timestamp = jsonObject1.get("timestamp") + "";
                                new PayWeChar(GoodsPayActivity.this, partnerid,
                                        prepayid, noncestr,
                                        timestamp, sign).toWXPay();
                            } else
                                tost("请求失败，重新尝试");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (payType.equals(aliPay)) {
                        String sAli = jsonToString(msg.obj.toString());
                        if (sAli == null) {
                            return;
                        } else {
                            new PayAliPay(GoodsPayActivity.this).PayZFB(sAli);
                        }
                    } else if (payType.equals(yuEPay)) {
                        try {
                            Map<String, Object> mapYuE = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                            if (mapYuE != null) {
                                if (getDouble(mapYuE, "code") == 200) {
                                    tost(getString(mapYuE, "msg"));
                                    toOrderList(1);
                                } else
                                    tost(getString(mapYuE, "msg"));
                            }
                        } catch (Exception e) {
                            tost(e.getMessage());
                        }
                    }
                    //跳至微信支付/支付宝支付
                    break;

                case 2:
                    try {
                        Map map = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (map == null) {
                            tost("解析出错！");
                        } else if (getDouble(map, "code") == 200) {
                            Map<String, Object> data = getMap(map, "data");
                            boolean is_pass = (boolean) data.get("is_pass");
                            SharedPreferenceUtils.setPreference(GoodsPayActivity.this, "PASSWORD", is_pass, "B");
                            if (is_pass) {
                                showPwd();
                            } else {
                                setPwd();
                            }
                        } else {
                            tost(getString(map, "msg"));
                        }
                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
            }

        }
    };

    /**
     * 设置支付密码
     */
    private void setPwd() {
        MyDialogTwoButton myDialogTwoButton = new MyDialogTwoButton(GoodsPayActivity.this, "检测到您没有设置支付密码，是否继续继续购买？", new DialogOnClick() {
            @Override
            public void operate() {
                IntentUtils.startIntent(GoodsPayActivity.this, PaySettingActivity.class);
            }

            @Override
            public void cancel() {

            }
        });
        myDialogTwoButton.show();
    }

    private void toOrderList(final int flag) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                IntentUtils.startIntent(flag, GoodsPayActivity.this, OrderListActivity.class);
                finish();
            }
        };
        timer.schedule(task, 2500);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String status = intent.getStringExtra("status");
            if (TextUtils.isEmpty(status)) {
                return;
            }
            if (action.equals(IntentUtils.pay)) {//微信
                finish();
                if (TextUtils.equals(status, "0")) {
                    tost("支付成功");
                    toOrderList(1);
                } else {
                    if (TextUtils.equals(status, "-1")) {
                        tost("检测到您没有安装微信");
                    } else {
                        tost("支付失败");
                    }
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void checkYE() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", application.getToken());
        httpHander.okHttpMapPost(GoodsPayActivity.this, Netconfig.checkYuE, map, 2);
    }


    private void showPwd() {
        final PasswordEditDialog passwordEditDialog = new PasswordEditDialog(GoodsPayActivity.this);
        passwordEditDialog.setTitle("请输入密码");
        final PasswordEditText password_edit_text = passwordEditDialog.getPasswordEdit();
        passwordEditDialog.setPasswordClickListeners(new PasswordEditText.PasswordFullListener() {
            @Override
            public void passwordFull(String password) {
                Map<String, Object> map = new HashMap<>();
                map.put("payPass", MD5Util.getMD5String(password));
                map.put("orderId", orderId);
                map.put("token", application.getToken());
                httpHander.okHttpMapPost(GoodsPayActivity.this, Netconfig.yuePay, map, 1);
            }
        });
        passwordEditDialog.customKeyBoard(new CustomerKeyboard.CustomerKeyboardClickListener() {
            @Override
            public void click(String number) {
                password_edit_text.addpassword(number);
            }

            @Override
            public void delete() {
                password_edit_text.deleteLastPassword();
            }
        });

    }

}
