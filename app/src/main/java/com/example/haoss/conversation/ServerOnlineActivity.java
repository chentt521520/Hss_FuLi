package com.example.haoss.conversation;

import android.os.Bundle;
import android.widget.Toast;

import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.CSCustomServiceInfo;

public class ServerOnlineActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);

        getRongYunToken();
    }

    private void startChat() {
        //首先需要构造使用客服者的用户信息
        CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
        CSCustomServiceInfo csInfo = csBuilder.nickName("融云").build();

/**
 * 启动客户服聊天界面。
 *
 * @param context           应用上下文。
 * @param customerServiceId 要与之聊天的客服 Id。
 * @param title             聊天的标题，如果传入空值，则默认显示与之聊天的客服名称。
 * @param customServiceInfo 当前使用客服者的用户信息。{@link io.rong.imlib.model.CSCustomServiceInfo}
 */
        String id = "KEFU155843267757477";
        RongIM.getInstance().startCustomerServiceChat(ServerOnlineActivity.this, id, "在线客服", csInfo);

        finish();
    }

    private void getRongYunToken() {
        String token = "7AJxVMf4ckQODv86O0gggviLWvOdinLl/ifewpGjVVKzko0LjXTUB7OpRs4lM466QbZotCnJLEw=";

        /**
         * IMKit SDK调用第二步
         *
         * 建立与服务器的连接
         *
         */
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Toast.makeText(ServerOnlineActivity.this, "onTokenIncorrect", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String userId) {
                startChat();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Toast.makeText(ServerOnlineActivity.this, "onError: " + errorCode, Toast.LENGTH_SHORT).show();
            }
        });
    }
}