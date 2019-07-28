package com.example.haoss.person.cardConvert;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardConvertRecordActivity extends BaseActivity {

    private ListView listView;
    private RefreshLayout refreshLayout;
    private ListConvertRecodAdapter adapter;
    private List<CardRecord> listData;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_card_convert_record);

        init();
        getList();
    }

    private void init() {
        listData = new ArrayList<>();

        this.getTitleView().setTitleText("兑换记录");
        listView = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
            }
        });

        adapter = new ListConvertRecodAdapter(this, listData);
        listView.setAdapter(adapter);

    }

    private void getList() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        map.put("page", page);
        map.put("limit", 20);
        httpHander.okHttpMapPost(this, Netconfig.cardOrderList, map, 1);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    analysisData(msg.obj.toString());
                    break;
            }
        }
    };

    private void analysisData(String json) {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
        /**
         * id : 1
         * uid : 37
         * price : 100.00
         * card_id : 17
         * card_num : 1bc3e331
         * add_time : 2019-07-23 14:19:11
         */

        if (page == 1) {
            listData.clear();
        }
        ArrayList<Object> list = httpHander.jsonToList(json);
        if (list == null || list.isEmpty()) {
            adapter.refresh(listData);
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> ret = (Map<String, Object>) list.get(i);
            String number = httpHander.getString(ret, "card_num");
            String price = httpHander.getString(ret, "price");
            String addTime = httpHander.getString(ret, "add_time");
            CardRecord record = new CardRecord(number, price, addTime);
            listData.add(record);
        }
        adapter.refresh(listData);
    }


}
