package com.example.haoss.goods.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.GoodList;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.DensityUtil;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.applibrary.widget.WordWrapView;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.details.GoodsDetailsActivity;

import java.util.List;

//商品搜索
public class GoodsSearchActivity extends BaseActivity {

    EditText goodssearchactivity_input; //搜索输入
    ImageView goodssearchactivity_go;    //返回
    TextView goodssearchactivity_hint;  //提示
    ListView goodssearchactivity_list;  //数据

    List<GoodList> listGoods; //商品数据
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
        goodssearchactivity_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    startSearch();
                    // 在这里编写自己想要实现的功能
                }
                return false;
            }
        });
        showAndHide(0);
//        setSearchTag();
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

    //flag==1：无数据，==2：显示数据
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
                    showInput(goodssearchactivity_input, false);
                    startSearch();
                    break;
            }
        }
    };


    private void startSearch() {
        searchText = goodssearchactivity_input.getText().toString();
        if (TextUtils.isEmpty(searchText)) {
            tost("请输入要搜索的内容");
            goodssearchactivity_go.setEnabled(false);
        } else {
            goodssearchactivity_go.setEnabled(true);
            goSearch();
        }
    }

    //加入商品详情
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            IntentUtils.startIntent(listGoods.get(position).getId(), GoodsSearchActivity.this, GoodsDetailsActivity.class);
        }
    };

    //开始搜索
    private void goSearch() {
//        String SearchTag = (String) SharedPreferenceUtils.getPreference(GoodsSearchActivity.this, "SearchTag", "S");
//        if (!searchText.contains(searchText)){
//            SharedPreferenceUtils.setPreference(this, "SearchTag", searchText, "S");
//        }

        String url = Netconfig.commoditySearch + Netconfig.assemble(true, ConfigHttpReqFields.sendKeyword, searchText);
        httpHander.getHttpGson(this, url, "", 1);
    }


    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            searchGoodList(msg.obj.toString(), new OnHttpCallback<List<GoodList>>() {
                @Override
                public void success(List<GoodList> result) {
                    listGoods = result;
                    if (listGoods == null || listGoods.isEmpty()) {
                        showAndHide(1);
                    } else {
                        showAndHide(2);
                    }
                    if (goodsSearchAdapter == null) {
                        goodsSearchAdapter = new GoodsSearchAdapter(GoodsSearchActivity.this, listGoods);
                        goodssearchactivity_list.setAdapter(goodsSearchAdapter);
                    } else
                        goodsSearchAdapter.setRefresh(listGoods);
                }

                @Override
                public void error(int code, String msg) {

                }
            });
        }
    };
}
