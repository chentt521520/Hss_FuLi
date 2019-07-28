package com.example.haoss.person.wallet;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
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

public class ConsumeRecordActivity extends BaseActivity {

    private RefreshLayout refreshLayout;
    private ListView listView;
    private ConsumeRecordAdapter adapter;
    private int page = 1;
    private List<BalanceRecord> listData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_consume_record);
        init();
        getList();
    }

    private void init() {
        listData = new ArrayList<>();
        this.getTitleView().setTitleText("消费记录");
        refreshLayout = findViewById(R.id.refresh_layout);
        listView = findViewById(R.id.list_view);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getList();
            }
        });

        adapter = new ConsumeRecordAdapter(this, listData);
        listView.setAdapter(adapter);
    }

    private void getList() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        map.put("page", page);
        map.put("limit", 20);
        httpHander.okHttpMapPost(this, Netconfig.balanceUseRecord, map, 1);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    Log.e("~~~~~~~~~~~", msg.obj.toString());
                    analysisData(msg.obj.toString());
                    break;
            }
        }
    };

    private void analysisData(String json) {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
        /**
         * mark : 余额支付10.01元购买商品
         * pm : 0
         * number : 10.01
         * add_time : 2019/07/24 10:32
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
            String mark = httpHander.getString(ret, "mark");
            double number = httpHander.getDouble(ret, "number");
            int pm = (int) httpHander.getDouble(ret, "pm");
            String addTime = httpHander.getString(ret, "add_time");
            BalanceRecord record = new BalanceRecord(mark, pm, number, addTime);
            listData.add(record);
        }
        adapter.refresh(listData);
    }

}
