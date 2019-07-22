package com.example.haoss.goods.estimate;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.indexpage.tourdiy.entity.GrouponGood;

import java.util.HashMap;
import java.util.List;

//商品评价列表
public class EstimateListActivity extends BaseActivity {

    private int page = 1;
    private int goodId;
    private RefreshLayout refreshLayout;
    private ListView listview;

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
        refreshLayout = findViewById(R.id.refresh_layout);
        listview = findViewById(R.id.list_view);

        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);
    }

    public void getEtimateList() {
        String url = Netconfig.evaluateList;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendProductId, goodId);
        map.put("page", page);
        map.put("limit", 20);
        map.put("type", 5);
        httpHander.okHttpMapPost(this, url, map, 1);

    }


    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    Log.e("~~~~~~~", msg.obj.toString());
                    analysisJson(msg.obj.toString());
                    break;

            }
        }
    };

    private void analysisJson(String s) {
//        httpHander.jsonToList(s);

    }
}
