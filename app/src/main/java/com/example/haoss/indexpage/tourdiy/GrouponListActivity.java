package com.example.haoss.indexpage.tourdiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.GrouponListInfo;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.resp.RespGrouponList;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.indexpage.tourdiy.adapter.GrouponAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//拼团界面
public class GrouponListActivity extends BaseActivity {

    ListView tourdiyactivity_listview;  //数据列表控件
    private List<RespGrouponList.GrouponList> listData;
    private GrouponAdapter adapter;
    private int page = 1;
    private RefreshLayout refreshLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_groupon);
        iniData();
        init();
    }

    private void iniData() {
        String url = Netconfig.pinTuanList;
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", 20);
        httpHander.okHttpMapPost(GrouponListActivity.this, url, map, 1);
    }

    private void init() {
        listData = new ArrayList<>();
        this.getTitleView().setTitleText("别样拼团");

        tourdiyactivity_listview = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                listData.clear();
                iniData();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                iniData();
            }
        });

        adapter = new GrouponAdapter(GrouponListActivity.this, listData);
        tourdiyactivity_listview.setAdapter(adapter);

        tourdiyactivity_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GrouponListActivity.this, GrouponDetail.class);
                intent.putExtra("id", listData.get(position).getId());
                startActivity(intent);
            }
        });
    }


    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            getGrouponList(msg.obj.toString(), new OnHttpCallback<List<RespGrouponList.GrouponList>>() {
                @Override
                public void success(List<RespGrouponList.GrouponList> result) {
                    refreshLayout.finishRefreshing();
                    refreshLayout.finishLoadmore();

                    if (page == 1) {
                        listData.clear();
                    }
                    listData.addAll(result);
                    adapter.freshList(listData);
                }

                @Override
                public void error(int code, String msg) {
                    refreshLayout.finishRefreshing();
                    refreshLayout.finishLoadmore();
                    tost(code + "," + msg);
                }
            });
        }
    };
}
