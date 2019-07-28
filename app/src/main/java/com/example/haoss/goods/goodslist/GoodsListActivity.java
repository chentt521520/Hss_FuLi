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

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.GoodList;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.resp.RespGoodSortList;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.base.Constants;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.goods.search.GoodsSearchActivity;
import com.example.haoss.goods.search.GoodsSearchAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//商品列表显示
public class GoodsListActivity extends BaseActivity {

    TextView goodslistactivity_hint;    //提示
    LinearLayout goodslistactivity_selectionbar;    //选择框
    LinearLayout goodslistactivity_synthesize, goodslistactivity_sales, goodslistactivity_price;  //综合按钮、销量按钮、价格按钮、筛选按钮
    TextView synthesize_text, sales_text, price_text;  //综合按钮、销量按钮、价格按钮、筛选按钮
    ImageView synthesize_image, sales_image, price_image;  //综合按钮、销量按钮、价格按钮、筛选按钮
    ListView listView;    //商品列表
    private RefreshLayout refreshLayout;

    List<GoodList> listGoods; //商品数据
    GoodsSearchAdapter goodsSearchAdapter;  //商品促销适配器
    int searchType = -1;  //商品类型标记(分类ID)
    String searchText = "";  //要搜索的内容
    private int page = 1;
    private String priceOrder = "";//asc正序；desc倒序
    private String saleOrder = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_goodslistactivity);
        init();

        listGoods = new ArrayList<>();
        searchType = getIntent().getIntExtra("searchType", -1);

        goodList();//初始化列表
    }

    private void init() {
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

        findViewById(R.id.ui_good_list_search).setOnClickListener(onClickListener);
        findViewById(R.id.page_back).setOnClickListener(onClickListener);
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
                goodList();//刷新列表
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                goodList();//上拉加载
            }
        });
    }

    //flag==0,全部隐藏，==1：显示提示，==2：搜索有数据
    private void showAndHide(int flag) {
        if (flag == 0) {//无数据
            goodslistactivity_hint.setVisibility(View.VISIBLE);
            goodslistactivity_selectionbar.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        } else if (flag == 1) {//商品列表 有数据
            goodslistactivity_hint.setVisibility(View.GONE);
            goodslistactivity_selectionbar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.page_back:
                    finish();
                    return;
                case R.id.ui_good_list_search:
                    IntentUtils.startIntent(GoodsListActivity.this, GoodsSearchActivity.class);
                    return;
                case R.id.goodslistactivity_synthesize:
                    priceOrder = "";
                    saleOrder = "";
                    textUp(1);
                    break;
                case R.id.goodslistactivity_sales:
                    saleOrder = changeOrder(saleOrder);
                    textUp(2);
                    priceOrder = "";
                    break;
                case R.id.goodslistactivity_price:
                    saleOrder = "";
                    priceOrder = changeOrder(priceOrder);
                    textUp(3);
                    break;
            }
            page = 1;
            goodList();//按类型排序
        }
    };

    //加入商品详情
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            IntentUtils.startIntent(listGoods.get(position).getId(), GoodsListActivity.this, GoodsDetailsActivity.class);
        }
    };

    //按类型获取商品列表
    private void goodList() {
        String url = Netconfig.categoryList;
        Map<String, Object> map = new HashMap<>();
        map.put("sid", searchType);
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
            getGoodList(msg.obj.toString(), new OnHttpCallback<RespGoodSortList.GoodSort>() {
                @Override
                public void success(RespGoodSortList.GoodSort result) {
                    refreshLayout.finishRefreshing();
                    refreshLayout.finishLoadmore();
                    if (page == 1) {
                        listGoods.clear();
                    }
                    listGoods.addAll(result.getList());
                    if (listGoods == null || listGoods.isEmpty()) {
                        showAndHide(0);
                    } else {
                        showAndHide(1);
                        goodsSearchAdapter.setRefresh(listGoods);
                    }
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

    private String changeOrder(String order) {
        if (TextUtils.equals(order, Constants.ASC)) {
            return Constants.DESC;
        } else if (TextUtils.equals(order, Constants.DESC)) {
            return Constants.ASC;
        } else {
            return Constants.DESC;
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
