package com.example.haoss.person.msg;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//信息列表界面
public class PersonMsgActivity extends BaseActivity {

    TextView personmsgactivity_hint;    //提示
    ListView personmsgactivity_listview;    //数据控件
    List<PersonMsgInfo> list;   //数据
    PersonMsgAdapter personMsgAdapter;  //适配器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_message);
        init();
    }

    //初始化
    private void init() {
        this.getTitleView().setTitleText("消息列表");
        personmsgactivity_hint = findViewById(R.id.personmsgactivity_hint);
        personmsgactivity_listview = findViewById(R.id.personmsgactivity_listview);

        getHttp();
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    //消息列表获取请求
    private void getHttp() {
        String url = Netconfig.msgList;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(this, url, map, 1);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            //{"code":200,"msg":"ok","data":[{"id":17,"title":"asd","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":0},{"id":18,"title":"asd","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":0},{"id":19,"title":"asd","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":0},{"id":20,"title":"213","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":0},{"id":21,"title":"213","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":0},{"id":22,"title":"213","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":0},{"id":23,"title":"123","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":1}],"count":0}
//            System.out.println("请求》消息列表返回数据》：" + msg.obj.toString());
            ArrayList arrayList = jsonToList(msg.obj.toString());

            if (arrayList != null) {
                list = new ArrayList<>();
                for (int i = 0; i < arrayList.size(); i++) {
                    Map<String, Object> map = (Map<String, Object>) arrayList.get(i);
                    if (map == null)
                        continue;
                    PersonMsgInfo personMsgInfo = new PersonMsgInfo();
                    personMsgInfo.setId((int) getDouble(map, "id"));
                    personMsgInfo.setTitle(getString(map, "title"));
                    personMsgInfo.setDescription(getString(map, "description"));
                    personMsgInfo.setIsShow((int) getDouble(map, "is_show"));
                    personMsgInfo.setStatus((int) getDouble(map, "status"));
                    personMsgInfo.setAddTime((int) getDouble(map, "add_time"));
                    personMsgInfo.setIsRead((int) getDouble(map, "is_read"));
                    list.add(personMsgInfo);
                }
                if (list.size() > 0) {
                    personMsgAdapter = new PersonMsgAdapter(PersonMsgActivity.this, list);
                    personmsgactivity_listview.setAdapter(personMsgAdapter);
                } else
                    personmsgactivity_hint.setVisibility(View.VISIBLE);

            } else
                personmsgactivity_hint.setVisibility(View.VISIBLE);
        }
    };
}
