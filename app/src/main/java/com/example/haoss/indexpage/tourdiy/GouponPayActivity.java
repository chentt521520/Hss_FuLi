package com.example.haoss.indexpage.tourdiy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.CustomerScrollView;
import com.example.applibrary.custom.MyListView;
import com.example.applibrary.entity.GrouponListInfo;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.resp.RespGrouponList;
import com.example.applibrary.resp.RespOrderDetail;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.base.Constants;
import com.example.haoss.indexpage.tourdiy.adapter.GrouponAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GouponPayActivity extends BaseActivity {

    private int id;
    private String orderId;
    private int page = 1;
    private int pinkId;
    private MyListView listview;
    private GrouponAdapter adapter;
    private List<RespGrouponList.GrouponList> listData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_goupon_pay);

        id = getIntent().getIntExtra("id", -1);
        orderId = getIntent().getStringExtra("orderId");

        initView();
        getGrouponList();
        getOrderDetail();
        getPinkStatus();
    }

    private void initView() {
        this.getTitleView().setTitleText("支付成功");

        ((TextView) findViewById(R.id.order_form_number)).setText(orderId);
        listview = findViewById(R.id.list_view);

        adapter = new GrouponAdapter(GouponPayActivity.this, listData);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GouponPayActivity.this, GrouponDetail.class);
                intent.putExtra("id", listData.get(position).getId());
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_groupon_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startIntent(pinkId, GouponPayActivity.this, GrouponNoticeActivity.class);
            }
        });

        ((CustomerScrollView) findViewById(R.id.scroll_view)).setOnScrollListener(new CustomerScrollView.OnScrollListener() {
            @Override
            public void loadMore() {
                page++;
                getGrouponList();
            }
        });
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    analysisJson(msg.obj.toString());
                    break;
                case 2://获取订单详情
                    getOrderDetail(msg.obj.toString(), new OnHttpCallback<RespOrderDetail.OrderDetail>() {
                        @Override
                        public void success(RespOrderDetail.OrderDetail result) {
                            ((TextView) findViewById(R.id.order_form_number)).setText(orderId);
                            ((TextView) findViewById(R.id.order_form_time)).setText(result.getPay_time());
                            switch (result.getPay_type()) {
                                case Constants.WEIXIN:
                                    ((TextView) findViewById(R.id.order_form_pay_mode)).setText("微信");
                                    break;
                                case Constants.ALI:
                                    ((TextView) findViewById(R.id.order_form_pay_mode)).setText("支付宝");
                                    break;
                                case Constants.YUE:
                                    ((TextView) findViewById(R.id.order_form_pay_mode)).setText("余额");
                                    break;
                            }
                            ((TextView) findViewById(R.id.order_form_price)).setText(result.getPay_price());
                        }

                        @Override
                        public void error(int code, String msg) {

                        }
                    });
                    break;
                case 3:
                    /**
                     * pink_id	number
                     * 非必须
                     * 拼团id
                     * add_time	number
                     * 非必须
                     * 创建时间
                     * paid
                     */
                    try {
                        Map<String, Object> map = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (map == null) {
                            tost("解析出错");
                        } else if (getDouble(map, "code") == 200) {
                            Map<String, Object> ret = (Map<String, Object>) map.get("data");
                            pinkId = (int) getDouble(ret, "pink_id");
                        } else {
                            tost(getString(map, "msg"));
                        }

                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
            }
        }
    };

    //解析数据
    private void analysisJson(String json) {
        ArrayList<Object> result = httpHander.jsonToList(json);

        if (result == null || result.isEmpty()) {
            return;
        }

        for (int i = 0; i < result.size(); i++) {
            Map<String, Object> map = (Map<String, Object>) result.get(i);
            String name = httpHander.getString(map, "title");
            String imageUrl = httpHander.getString(map, "image");
            String price = httpHander.getString(map, "price");
            String product_price = httpHander.getString(map, "product_price");
            Map<String, Integer> mapInteger = httpHander.getIntegerMap(map, "id");
            RespGrouponList.GrouponList tourdiy = new RespGrouponList.GrouponList();
            tourdiy.setId(mapInteger.get("id"));
            tourdiy.setPrice(price);
            tourdiy.setPeople(3);
            tourdiy.setImage(imageUrl);
            tourdiy.setTitle(name);
            tourdiy.setProduct_price(product_price);
            listData.add(tourdiy);
        }
        adapter.freshList(listData);
    }

    private void getGrouponList() {
        listData = new ArrayList<>();
        String url = Netconfig.pinTuanList;
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", 20);
        httpHander.okHttpMapPost(GouponPayActivity.this, url, map, 1);
    }

    private void getOrderDetail() {
        String url = Netconfig.orderDetails;
        Map<String, Object> map = new HashMap<>();
        map.put("uni", orderId);
        map.put("token", AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(this, url, map, 2);
    }

    private void getPinkStatus() {
        String url = Netconfig.getPinkStatus;
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", orderId);
        map.put("token", AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(this, url, map, 3);
    }

}
