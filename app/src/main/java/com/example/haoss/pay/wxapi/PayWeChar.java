package com.example.haoss.pay.wxapi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.utils.IntentUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

//开启微信支付
public class PayWeChar {

    Context context;
    IWXAPI iwxapi;

    String appId, partnerId, prepayId, packageValue, nonceStr, timeStamp, sign;

    public PayWeChar(Context context, String partnerId, String prepayId, String nonceStr, String timeStamp, String sign) {
        this.context = context;
        this.appId = ConfigVariate.weChatAppID;
        this.partnerId = partnerId;
        this.prepayId = prepayId;
        this.packageValue = "Sign=WXPay";
        this.nonceStr = nonceStr;
        this.timeStamp = timeStamp;
        this.sign = sign;
    }

    /**
     * 调起微信支付的方法
     **/
    public void toWXPay() {

        iwxapi = WXAPIFactory.createWXAPI(context, appId);
        iwxapi.registerApp(appId); //注册appid

        if (!isWXAppInstalled()) {
            Intent intent = new Intent(IntentUtils.pay);
            intent.putExtra("status", "-1");
            context.sendBroadcast(intent);
            Log.e("TAG", "您没有安装微信");
        }

        //一定注意要放在子线程
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信的对象
                //这几个参数的值，正是上面我们说的统一下单接口后返回来的字段，我们对应填上去即可
                request.appId = appId;
                request.partnerId = partnerId;
                request.prepayId = prepayId;
                request.packageValue = packageValue;
                request.nonceStr = nonceStr;
                request.timeStamp = timeStamp;
//                request.sign = ConfigVariate.weChatSign;
                request.sign = sign;

                iwxapi.sendReq(request);//发送调起微信的请求
                System.out.println("请求支付方式：request.appId《" + request.appId + "》request.partnerId《：" + request.partnerId + "》request.prepayId《：" + request.prepayId
                        + "》request.packageValue《：" + request.packageValue + "》request.nonceStr《：" + request.nonceStr + "》request.timeStamp《：" + request.timeStamp
                        + "》request.sign《：" + request.sign + "》完");
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    //检查微信是否安装
    public boolean isWXAppInstalled() {
        return iwxapi.isWXAppInstalled();
    }
}
