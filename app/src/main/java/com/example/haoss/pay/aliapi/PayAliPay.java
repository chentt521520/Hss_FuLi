package com.example.haoss.pay.aliapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;

import java.util.HashMap;
import java.util.Map;

//支付宝支付
public class PayAliPay {
    private final int SDK_PAY_FLAG = 1;
    Activity activity;

    public PayAliPay(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Intent intent = new Intent(IntentUtils.pay);

                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    Log.e("PayAliPay", payResult.getMemo());

                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        intent.putExtra("status", "0");
                    } else {
                        //支付失败应该跳转到未付款页面
                        intent.putExtra("status", "1");
                    }
                    activity.sendBroadcast(intent);

                    break;
                }
                default:
                    break;
            }
            activity.finish();
        }

        ;
    };

    public void PayZFB(final String orderStr) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderStr, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
