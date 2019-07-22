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

    ListView listView;  //数据控件
    NowSpecialOfferAdapter adapter;  //列表适配器
    List<NowSpecialOfferInfo> listNow = new ArrayList<>();  //特价数据
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
            analysisJson(msg.obj.toString());
        }
    };

    private void analysisJson(String s) {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();

        ArrayList<Object> arrayList = httpHander.jsonToList(s);

        if (arrayList == null) {
            return;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            Map<String, Object> map = (Map<String, Object>) arrayList.get(i);
            String name = httpHander.getString(map, "title");
            String imageUrl = httpHander.getString(map, "image");
            String price = httpHander.getString(map, "price");
            String ot_price = httpHander.getString(map, "ot_price");
            Map<String, Integer> mapInteger = httpHander.getIntegerMap(map, "id", "product_id", "percent", "sales");
            int id = mapInteger.get("id");
            int product_id = mapInteger.get("product_id");
            int percent = mapInteger.get("percent");
            int sales = mapInteger.get("sales");
            NowSpecialOfferInfo tourdiy = new NowSpecialOfferInfo();
            tourdiy.setId(id);
            tourdiy.setProduct_id(product_id);
            tourdiy.setMoney(price);
            tourdiy.setOriginalCost(ot_price);
            tourdiy.setImage(imageUrl);
            tourdiy.setName(name);
            tourdiy.setPercent(percent);
            tourdiy.setSales(sales);
            listNow.add(tourdiy);
        }
        if (adapter == null) {
            adapter = new NowSpecialOfferAdapter(this, listNow);
            listView.setAdapter(adapter);
        } else
            adapter.notifyDataSetChanged();
    }

}
