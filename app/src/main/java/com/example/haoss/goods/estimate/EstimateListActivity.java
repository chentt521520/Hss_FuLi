package com.example.haoss.goods.estimate;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.EstimateList;
import com.example.applibrary.entity.ReplyInfo;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.helper.DialogHelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//商品评价列表
public class EstimateListActivity extends BaseActivity {

    private int page = 1;
    private int goodId;
    private RefreshLayout refreshLayout;
    private ListView listview;
    private TextView all_estimate, high_estimate, middle_estimate, low_estimate, has_image_estimate;
    private EstimateList estimateList;
    private ListEstimateAdapter adapter;
    private List<ReplyInfo> replyInfoList;
    private int type = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.layout_goodsestimatelistactivity);

        goodId = getIntent().getIntExtra(IntentUtils.intentActivityFlag, -1);
        this.getTitleView().setTitleText("评价列表");

        initView();
        getEtimateList();
    }

    private void initView() {
        replyInfoList = new ArrayList<>();
        all_estimate = findViewById(R.id.all_estimate);
        high_estimate = findViewById(R.id.high_estimate);
        middle_estimate = findViewById(R.id.middle_estimate);
        low_estimate = findViewById(R.id.low_estimate);
        has_image_estimate = findViewById(R.id.has_image_estimate);

        all_estimate.setOnClickListener(listener);
        high_estimate.setOnClickListener(listener);
        middle_estimate.setOnClickListener(listener);
        low_estimate.setOnClickListener(listener);
        has_image_estimate.setOnClickListener(listener);

        refreshLayout = findViewById(R.id.refresh_layout);
        listview = findViewById(R.id.list_view);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getEtimateList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getEtimateList();
            }
        });

        adapter = new ListEstimateAdapter(this, replyInfoList);
        listview.setAdapter(adapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.all_estimate:
                    type = -1;
                    break;
                case R.id.high_estimate:
                    type = 5;
                    break;
                case R.id.middle_estimate:
                    type = 3;
                    break;
                case R.id.low_estimate:
                    type = 1;
                    break;
                case R.id.has_image_estimate:
                    type = 6;
                    break;
            }
            page = 1;
            setBtnStatus();
            getEtimateList();
        }
    };

    private void setBtnStatus() {
        all_estimate.setBackground(type == -1 ? getResources().getDrawable(R.drawable.bg_all_corner_btn_red) : getResources().getDrawable(R.drawable.bg_all_corner_btn_grey));
        all_estimate.setTextColor(type == -1 ? Color.parseColor("#ffffff") : Color.parseColor("#333333"));
        high_estimate.setBackground(type == 5 ? getResources().getDrawable(R.drawable.bg_all_corner_btn_red) : getResources().getDrawable(R.drawable.bg_all_corner_btn_grey));
        high_estimate.setTextColor(type == 5 ? Color.parseColor("#ffffff") : Color.parseColor("#333333"));
        middle_estimate.setBackground(type == 3 ? getResources().getDrawable(R.drawable.bg_all_corner_btn_red) : getResources().getDrawable(R.drawable.bg_all_corner_btn_grey));
        middle_estimate.setTextColor(type == 3 ? Color.parseColor("#ffffff") : Color.parseColor("#333333"));
        low_estimate.setBackground(type == 1 ? getResources().getDrawable(R.drawable.bg_all_corner_btn_red) : getResources().getDrawable(R.drawable.bg_all_corner_btn_grey));
        low_estimate.setTextColor(type == 1 ? Color.parseColor("#ffffff") : Color.parseColor("#333333"));
        has_image_estimate.setBackground(type == 6 ? getResources().getDrawable(R.drawable.bg_all_corner_btn_red) : getResources().getDrawable(R.drawable.bg_all_corner_btn_grey));
        has_image_estimate.setTextColor(type == 6 ? Color.parseColor("#ffffff") : Color.parseColor("#333333"));
    }

    public void getEtimateList() {
        DialogHelp.showLoading(EstimateListActivity.this);
        String url = Netconfig.evaluateList;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendProductId, goodId);
        map.put("page", page);
        map.put("limit", 20);
        if (type == 6) {
            map.put("is_img", 1);
        } else if (type == 1 || type == 3 || type == 5) {
            map.put("type", type);
        }
        httpHander.okHttpMapPost(this, url, map, 1);
    }


    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    analysisJson(msg.obj.toString());
                    break;
            }
        }
    };

    private void analysisJson(String s) {
        DialogHelp.hideLoading();
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
        if (page == 1) {
            replyInfoList.clear();
        }
        Map<String, Object> map = httpHander.jsonToMap(s);
        if (map == null || map.isEmpty()) {
            adapter.refresh(replyInfoList);
            return;
        }
        Map<String, Object> count = httpHander.getMap(map, "count");
        estimateList = new EstimateList();
        Map<String, Integer> integerMap = httpHander.getIntegerMap(count, "all", "good", "middle", "bad", "is_img");
        EstimateList.EstimateCount count1 = new EstimateList.EstimateCount(integerMap.get("all"), integerMap.get("good"), integerMap.get("middle"), integerMap.get("bad"), integerMap.get("is_img"));
        estimateList.setCount(count1);
        ArrayList<Object> list = httpHander.getList(map, "list");
        if (list == null || list.isEmpty()) {
            adapter.refresh(replyInfoList);
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> objectMap = (Map<String, Object>) list.get(i);
            /**
             * 	"product_score": 5,
             * 					"service_score": 5,
             * 					"comment": "价格很优惠，服务也很好，YY款式和面料都不错的，给个好评此次",
             * 					"merchant_reply_content": null,
             * 					"merchant_reply_time": "1970-01-01 08:00",
             * 					"pics": null,
             * 					"add_time": "2019-07-17 06:05",
             * 					"nickname": "*",
             * 					"avatar": "http://py.haoshusi.com/avatar/344e794e204204215d95ba49507926b7.jpg",
             * 					"suk": "1套",
             * 					"star": "5"
             */
            String name = httpHander.getString(objectMap, "nickname");
            String commont = httpHander.getString(objectMap, "comment");
            String avatar = httpHander.getString(objectMap, "avatar");
            String suk = httpHander.getString(objectMap, "suk");
            String add_time = httpHander.getString(objectMap, "add_time");
            ArrayList<Object> pics = httpHander.getList(objectMap, "pics");

            ReplyInfo replyInfo = new ReplyInfo();
            replyInfo.setNickname(name);
            replyInfo.setAvatar(avatar);
            replyInfo.setComment(commont);
            replyInfo.setAdd_time(add_time);
            replyInfo.setSuk(suk);
            replyInfo.setPics(pics);
            replyInfoList.add(replyInfo);
        }
        adapter.refresh(replyInfoList);
        setView();
    }

    private void setView() {
        all_estimate.setText("全部(" + estimateList.getCount().getAll() + ")");
        high_estimate.setText("好评(" + estimateList.getCount().getGood() + ")");
        middle_estimate.setText("中评(" + estimateList.getCount().getMiddle() + ")");
        low_estimate.setText("差评(" + estimateList.getCount().getBad() + ")");
        has_image_estimate.setText("有图(" + estimateList.getCount().getIs_img() + ")");
    }
}
