package com.example.haoss.indexpage.specialoffer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.resp.RespTodaySales;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.details.GoodsDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//今日特价界面
public class NowSpecialOfferActivity extends BaseActivity {

    private ListView listView;  //数据控件
    private NowSpecialOfferAdapter adapter;  //列表适配器
    private List<RespTodaySales.TodaySales> listNow = new ArrayList<>();  //特价数据
    private RefreshLayout refreshLayout;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_sales);
        init();
    }

    private void init() {

        listView = findViewById(R.id.nowspecialofferactivity_listview);
        findViewById(R.id.page_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout = findViewById(R.id.refresh_layout);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                listNow.clear();
                getList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getList();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NowSpecialOfferActivity.this, GoodsDetailsActivity.class);
                intent.putExtra(IntentUtils.intentActivityFlag, listNow.get(position).getId());
                intent.putExtra("flag", 1);
                startActivity(intent);
            }
        });
        getList();
    }

    private void getList() {
        String url = Netconfig.seckillShopList;
        Map<String, Object> map = new HashMap<>();
        map.put("page", page + "");
        map.put("limit", "20");
        map.put("token", AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(NowSpecialOfferActivity.this, url, map, 1);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            getTodaySales(msg.obj.toString(), new OnHttpCallback<List<RespTodaySales.TodaySales>>() {
                @Override
                public void success(List<RespTodaySales.TodaySales> result) {
                    refreshLayout.finishRefreshing();
                    refreshLayout.finishLoadmore();
                    listNow = result;
                    if (adapter == null) {
                        adapter = new NowSpecialOfferAdapter(NowSpecialOfferActivity.this, listNow);
                        listView.setAdapter(adapter);
                    } else
                        adapter.notifyDataSetChanged();
                }

                @Override
                public void error(int code, String msg) {
                    refreshLayout.finishRefreshing();
                    refreshLayout.finishLoadmore();
                }
            });
        }
    };

}
