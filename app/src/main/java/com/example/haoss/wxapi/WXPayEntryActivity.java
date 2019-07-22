package com.example.haoss.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.Toast;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.utils.IntentUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    IWXAPI api;

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
//        tost("baseResp.getType():"+baseResp.getType());
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Intent intent = new Intent(IntentUtils.pay);
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
//                    tost("支付成功");
                    intent.putExtra("status", "0");
                    break;
                case BaseResp.ErrCode.ERR_COMM:
//                    tost("支付失败");
                    intent.putExtra("status", "1");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
//                    tost("支付取消");
                    intent.putExtra("status", "2");
                    break;
            }
            sendBroadcast(intent);

        } else {
            System.out.println("请求微信支付返回2》》：" + baseResp.getType());
        }
//        tost("errCode："+baseResp.errCode+"  errStr:"+baseResp.errStr+"   transaction:"+baseResp.transaction+"  openId:"+baseResp.openId);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册API 没有这个不会执行onResp
        api = WXAPIFactory.createWXAPI(this, ConfigVariate.weChatAppID);
        boolean b = api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(getIntent(), this);
    }

    private void tost(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
