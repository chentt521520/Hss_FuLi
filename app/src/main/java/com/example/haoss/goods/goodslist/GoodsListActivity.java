package com.example.haoss.goods.goodslist;

import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.base.Constants;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.goods.search.GoodsSearchAdapter;
import com.example.haoss.goods.search.GoodsSearchInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//商品列表显示
public class GoodsListActivity extends BaseActivity {

    EditText goodslistactivity_input;   //搜索框
    ImageView goodslistactivity_go;  //搜索按钮
    TextView goodslistactivity_hint;    //提示
    LinearLayout goodslistactivity_selectionbar;    //选择框
    LinearLayout goodslistactivity_synthesize, goodslistactivity_sales, goodslistactivity_price;  //综合按钮、销量按钮、价格按钮、筛选按钮
    TextView synthesize_text, sales_text, price_text;  //综合按钮、销量按钮、价格按钮、筛选按钮
    ImageView synthesize_image, sales_image, price_image;  //综合按钮、销量按钮、价格按钮、筛选按钮
    ListView listView;    //商品列表
    private RefreshLayout refreshLayout;

    List<GoodsSearchInfo> listGoods; //商品数据
    GoodsSearchAdapter goodsSearchAdapter;  //商品促销适配器
    int searchType = -1;  //商品类型标记(分类ID)
    String searchText = "";  //要搜索的内容
    private int page = 1;
    private int status = 0;//是否为刷新
    private String priceOrder = "";//asc正序；desc倒序
    private String saleOrder = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_goodslistactivity);
        init();

        listGoods = new ArrayList<>();
        searchType = getIntent().getIntExtra("searchType", -1);
        searchText = getIntent().getStringExtra("searchName");
        if (TextUtils.isEmpty(searchText)) {
            searchText = "";
        }
        goSearch();
    }

    private void init() {
        goodslistactivity_input = findViewById(R.id.goodslistactivity_input);
        goodslistactivity_go = findViewById(R.id.goodslistactivity_go);
        goodslistactivity_hint = findViewById(R.id.goodslistactivity_hint);
        goodslistactivity_selectionbar = findViewById(R.id.goodslistactivity_selectionbar);
        goodslistactivity_synthesize = findViewById(R.id.goodslistactivity_synthesize);
        goodslistactivity_sales = findViewById(R.id.goodslistactivity_sales);
        goodslistactivity_price = findViewById(R.id.goodslistactivity_price);
        synthesize_text = findViewById(R.id.goodslistactivity_synthesize_text);
        sales_text = findViewById(R.id.goodslistactivity_sales_text);
        price_text = findViewById(R.id.goodslistactivity_price_text);
        synthesize_image = findViewById(R.id.goodslistactivity_synthesize_image);
        sales_image = findViewById(R.id.goodslistactivity_sales_image);
        price_image = findViewById(R.id.goodslistactivity_price_image);
        refreshLayout = findViewById(R.id.refresh_layout);
        listView = findViewById(R.id.list_view);

        findViewById(R.id.page_back).setOnClickListener(onClickListener);
        goodslistactivity_go.setOnClickListener(onClickListener);
        goodslistactivity_synthesize.setOnClickListener(onClickListener);
        goodslistactivity_sales.setOnClickListener(onClickListener);
        goodslistactivity_price.setOnClickListener(onClickListener);
        listView.setOnItemClickListener(onItemClickListener);

        goodsSearchAdapter = new GoodsSearchAdapter(this, listGoods);
        listView.setAdapter(goodsSearchAdapter);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                status = 0;
                goSearch();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                status = 1;
                goSearch();
            }
        });

        showAndHide(0);
        setEdtext();
    }

    //flag==0,全部隐藏，==1：显示提示，==2：显示数据
    private void showAndHide(int flag) {
        goodslistactivity_hint.setVisibility(flag == 1 ? View.VISIBLE : View.GONE);
        goodslistactivity_selectionbar.setVisibility(flag == 2 ? View.VISIBLE : View.GONE);
        listView.setVisibility(flag == 2 ? View.VISIBLE : View.GONE);
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
                    searchText = goodslistactivity_input.getText().toString();
                    if (TextUtils.isEmpty(searchText)) {
                        goodslistactivity_go.setEnabled(false);
                    } else {
                        goodslistactivity_go.setEnabled(true);
                        goSearch();
                    }
                    break;
                case R.id.goodslistactivity_synthesize:
                    priceOrder = "";
                    saleOrder = "";
                    page = 1;
                    goSearch();
                    textUp(1);
                    break;
                case R.id.goodslistactivity_sales:
                    if (TextUtils.equals(saleOrder, Constants.ASC)) {
                        saleOrder = Constants.DESC;
                    } else if (TextUtils.equals(saleOrder, Constants.DESC)) {
                        saleOrder = Constants.ASC;
                    } else {
                        saleOrder = Constants.DESC;
                    }
                    priceOrder = "";
                    page = 1;
                    goSearch();
                    textUp(2);
                    break;
                case R.id.goodslistactivity_price:
                    if (TextUtils.equals(priceOrder, Constants.ASC)) {
                        priceOrder = Constants.DESC;
                    } else if (TextUtils.equals(priceOrder, Constants.DESC)) {
                        priceOrder = Constants.ASC;
                    } else {
                        priceOrder = Constants.DESC;
                    }
                    saleOrder = "";
                    page = 1;
                    goSearch();
                    textUp(3);
                    break;
            }
        }
    };

    //加入商品详情
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            IntentUtils.startIntent(listGoods.get(position).getId(), GoodsListActivity.this, GoodsDetailsActivity.class);
        }
    };

    //设置编辑框
    private void setEdtext() {
        goodslistactivity_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    //开始搜索
    private void goSearch() {
        String url = Netconfig.categoryList;
        Map<String, Object> map = new HashMap<>();
        map.put("sid", searchType);
        map.put("keyword", searchText);
        map.put("page", page);
        map.put("limit", 20);
        map.put("salesOrder", saleOrder);
        map.put("priceOrder", priceOrder);
        httpHander.okHttpMapPost(this, url, map, 1);
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
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
        //解析
        Map<String, Object> map1 = httpHander.jsonToMap(json);
        if (map1 == null) {
            return;
        }
        ArrayList<Object> list = httpHander.getList(map1, "list");
        if (status == 0) {
            if (list == null || list.size() == 0) {
                showAndHide(1);
            } else {
                showAndHide(2);
            }
            listGoods.clear();
        }

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = (Map<String, Object>) list.get(i);
            Map<String, String> mapString = httpHander.getStringMap(map, "store_name", "image", "cate_id");
            Map<String, Double> mapDouble = httpHander.getDoubleMap(map, "price", "vip_price");
            Map<String, Integer> mapInteger = httpHander.getIntegerMap(map, "id", "stock", "sales", "store_type");
            GoodsSearchInfo goodsSearchInfo = new GoodsSearchInfo();
            goodsSearchInfo.setImage(mapString.get("image"));
            goodsSearchInfo.setName(mapString.get("store_name"));
            goodsSearchInfo.setCateId(mapString.get("cate_id"));
            goodsSearchInfo.setSaies(mapInteger.get("sales"));
            goodsSearchInfo.setId(mapInteger.get("id"));
            goodsSearchInfo.setStock(mapInteger.get("stock"));
            goodsSearchInfo.setPrice(mapDouble.get("price"));
            goodsSearchInfo.setVipPrice(mapDouble.get("vip_price"));
            goodsSearchInfo.setStore_type(mapInteger.get("store_type"));
            listGoods.add(goodsSearchInfo);
        }
        goodsSearchAdapter.setRefresh(listGoods);
        if (page == 1) {
            listView.setSelection(0);
        }
    }

    //更新textview    flag:选中的标记
    private void textUp(int flag) {
        //更改字体颜色
        String colorRed = "#c22222";
        String colorDark = "#0f0f0f";
        synthesize_text.setTextColor(Color.parseColor(flag == 1 ? colorRed : colorDark));
        synthesize_image.setVisibility(flag == 1 ? View.VISIBLE : View.INVISIBLE);
        synthesize_image.setImageResource(R.drawable.choose_true);

        sales_text.setTextColor(Color.parseColor(flag == 2 ? colorRed : colorDark));
        sales_image.setVisibility(flag == 2 ? View.VISIBLE : View.INVISIBLE);
        price_text.setTextColor(Color.parseColor(flag == 3 ? colorRed : colorDark));
        price_image.setVisibility(flag == 3 ? View.VISIBLE : View.INVISIBLE);

        if (TextUtils.equals(saleOrder, Constants.ASC)) {
            sales_image.setImageResource(R.drawable.choose_false);
        } else if (TextUtils.equals(saleOrder, Constants.DESC)) {
            sales_image.setImageResource(R.drawable.choose_true);
        }

        if (TextUtils.equals(priceOrder, Constants.ASC)) {
            price_image.setImageResource(R.drawable.choose_false);
        } else if (TextUtils.equals(priceOrder, Constants.DESC)) {
            price_image.setImageResource(R.drawable.choose_true);
        }
    }
}
