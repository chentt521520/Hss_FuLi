package com.example.haoss.indexpage.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.ViewUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.indexpage.adapter.ListExceltBrandAdapter;
import com.example.haoss.indexpage.entity.GoodInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcellentBrandActivity extends BaseActivity {

    ListView listView;  //数据列表控件
    private List<GoodInfo> listData;
    private ListExceltBrandAdapter adapter;
    private RefreshLayout refreshLayout;
    private int page = 1;
    private int status = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_excellent_brand);
        init();
        getList();
    }

    private void init() {
        listData = new ArrayList<>();
        this.getTitleView().setTitleText("品牌精选");
        listView = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        adapter = new ListExceltBrandAdapter(ExcellentBrandActivity.this, listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(ExcellentBrandActivity.this, GrouponDetail.class);
//                intent.putExtra("brandInfo", listData.get(position));
//                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                listData.clear();
                getList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getList();
            }
        });


    }

    private void getList() {
        String url = Netconfig.brandSiftList + Netconfig.headers;
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", "20");
        httpHander.okHttpMapPost(ExcellentBrandActivity.this, url, map, 1);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            Log.e("TAG", msg.obj.toString());
            analysisJson(msg.obj.toString());
        }
    };

    //解析数据
    private void analysisJson(String json) {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();

        ArrayList<Object> result = httpHander.jsonToList(json);
        if (result == null || result.isEmpty()) {
            return;
        }

        for (int i = 0; i < result.size(); i++) {
            /**
             * "id": 60,
             *       "store_type": 0,
             *       "image": "http://qiniu.haoshusi.com/images/b9150df9e76e2bacc35ef80ca2b84e2f.png",
             *       "store_name": "《当日精选鲜货》埃及橙带包装5-10斤装",
             *       "price": "35.49",
             *       "stock": 2000,
             *       "ficti": 1365
             *       "ot_price": "1365"
             */
            Map<String, Object> map = (Map<String, Object>) result.get(i);
            String name = httpHander.getString(map, "store_name");
            String imageUrl = httpHander.getString(map, "image");
            String price = httpHander.getString(map, "price");
            String ot_price = httpHander.getString(map, "ot_price");
            Map<String, Integer> mapInteger = httpHander.getIntegerMap(map, "id", "store_type", "stock");
            int id = mapInteger.get("id");
            int store_type = mapInteger.get("store_type");
            int stock = mapInteger.get("stock");
            GoodInfo tourdiy = new GoodInfo();
            tourdiy.setId(id);
            tourdiy.setPrice(price);
            tourdiy.setImage(imageUrl);
            tourdiy.setOt_price(ot_price);
            tourdiy.setStore_name(name);
            tourdiy.setStore_type(store_type);
            tourdiy.setStock(stock);
            listData.add(tourdiy);
        }
        adapter.freshList(listData);
    }

}
