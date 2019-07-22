package com.example.haoss.goods.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.DensityUtil;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.applibrary.widget.WordWrapView;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.helper.DialogHelp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//商品搜索
public class GoodsSearchActivity extends BaseActivity {

    EditText goodssearchactivity_input; //搜索输入
    ImageView goodssearchactivity_go;    //返回
    TextView goodssearchactivity_hint;  //提示
    ListView goodssearchactivity_list;  //数据

    List<GoodsSearchInfo> listGoods; //商品数据
    GoodsSearchAdapter goodsSearchAdapter;  //商品促销适配器
    String searchText = "";  //要搜索的内容
    private WordWrapView wordWrapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_goodssearchactivity);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    }

    private void init() {
        goodssearchactivity_go = findViewById(R.id.goodslistactivity_go);
        goodssearchactivity_input = findViewById(R.id.goodslistactivity_input);
        goodssearchactivity_hint = findViewById(R.id.goodssearchactivity_hint);
        goodssearchactivity_list = findViewById(R.id.goodssearchactivity_list);
        wordWrapView = findViewById(R.id.search_good_tag);
        goodssearchactivity_go.setOnClickListener(onClickListener);  //搜索
        goodssearchactivity_list.setOnItemClickListener(onItemClickListener);

        goodssearchactivity_go.setOnClickListener(onClickListener);
        findViewById(R.id.page_back).setOnClickListener(onClickListener);
        showAndHide(0);
        setEdtext();
        setSearchTag();
    }

    private void setSearchTag() {
        String SearchTag = (String) SharedPreferenceUtils.getPreference(GoodsSearchActivity.this, "SearchTag", "S");
        if (TextUtils.isEmpty(SearchTag)) {
            return;
        }
        final String[] tag = SearchTag.split(",");
        for (int i = 0; i < tag.length; i++) {
            final TextView textview = new TextView(GoodsSearchActivity.this);
            textview.setBackground(getResources().getDrawable(R.drawable.bk_hui_04));
            textview.setPadding(DensityUtil.dip2px(GoodsSearchActivity.this, 20), DensityUtil.dip2px(GoodsSearchActivity.this, 10), DensityUtil.dip2px(GoodsSearchActivity.this, 20), DensityUtil.dip2px(GoodsSearchActivity.this, 10));
            textview.setTextSize(14);
            textview.setText(tag[i]);
            final int finalI = i;
            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodssearchactivity_input.setText(tag[finalI]);
                    searchText = tag[finalI];
                }
            });
            wordWrapView.addView(textview);
        }
    }

    //flag==0,全部隐藏，==1：显示提示，==2：显示数据
    private void showAndHide(int flag) {
        goodssearchactivity_hint.setVisibility(flag == 1 ? View.VISIBLE : View.GONE);
        goodssearchactivity_list.setVisibility(flag == 2 ? View.VISIBLE : View.GONE);
        wordWrapView.setVisibility(flag == 0 ? View.VISIBLE : View.GONE);
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.page_back:
                    finish();
                    break;
                case R.id.goodslistactivity_go:
                    searchText = goodssearchactivity_input.getText().toString();
                    if (TextUtils.isEmpty(searchText)) {
                        goodssearchactivity_go.setEnabled(false);
                    } else {
                        goodssearchactivity_go.setEnabled(true);
                        goSearch();
                    }
                    break;
            }
        }
    };

    //加入商品详情
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            IntentUtils.startIntent(listGoods.get(position).getId(), GoodsSearchActivity.this, GoodsDetailsActivity.class);
        }
    };

    //设置编辑框
    private void setEdtext() {
        goodssearchactivity_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showAndHide(0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //开始搜索
    private void goSearch() {
        DialogHelp.showLoading(this);
        SharedPreferenceUtils.setPreference(this, "SearchTag", searchText, "S");
        String url = Netconfig.commoditySearch + Netconfig.assemble(true, ConfigHttpReqFields.sendKeyword, searchText);
        httpHander.getHttpGson(this, url, "", 1);
    }


    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            analysisJson(msg.obj.toString());
        }
    };

    //解析
    private void analysisJson(String json) {
        DialogHelp.hideLoading();
        //解析
        ArrayList<Object> list = httpHander.jsonToList(json);
        if (list == null || list.size() == 0) {
            showAndHide(1);
        } else {  //有数据
            showAndHide(2);
            if (listGoods == null)
                listGoods = new ArrayList<>();
            listGoods.clear();
            setData(list);
            if (goodsSearchAdapter == null) {
                goodsSearchAdapter = new GoodsSearchAdapter(this, listGoods);
                goodssearchactivity_list.setAdapter(goodsSearchAdapter);
            } else
                goodsSearchAdapter.setRefresh(listGoods);
        }
    }

    //设置解析数据
    private void setData(ArrayList<Object> list) {
        //{id=4.0, store_name=互联网电热水器1A, cate_id=3, image=http://datong.crmeb.net/public/uploads/attach/2019/01/15/5c3dc23646fff.jpg, sales=97, price=999.00, stock=413.0, vip_price=989.01}
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = (Map<String, Object>) list.get(i);
            Map<String, String> mapString = httpHander.getStringMap(map, "store_name", "image", "cate_id");
            Map<String, Double> mapDouble = httpHander.getDoubleMap(map, "price", "vip_price");
            Map<String, Integer> mapInteger = httpHander.getIntegerMap(map, "id", "stock", "sales");
            GoodsSearchInfo goodsSearchInfo = new GoodsSearchInfo();
            goodsSearchInfo.setImage(mapString.get("image"));
            goodsSearchInfo.setName(mapString.get("store_name"));
            goodsSearchInfo.setCateId(mapString.get("cate_id"));
            goodsSearchInfo.setSaies(mapInteger.get("sales"));
            goodsSearchInfo.setId(mapInteger.get("id"));
            goodsSearchInfo.setStock(mapInteger.get("stock"));
            goodsSearchInfo.setPrice(mapDouble.get("price"));
            goodsSearchInfo.setVipPrice(mapDouble.get("vip_price"));
            listGoods.add(goodsSearchInfo);
        }
    }
}
