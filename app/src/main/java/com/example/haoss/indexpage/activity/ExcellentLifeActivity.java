package com.example.haoss.indexpage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.CustomerScrollView;
import com.example.applibrary.custom.viewfragment.FragmentDataInfo;
import com.example.applibrary.custom.viewfragment.FragmentView;
import com.example.applibrary.custom.viewfragment.OnclickFragmentView;
import com.example.applibrary.entity.NavInfo;
import com.example.applibrary.entity.Recommond;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.goods.goodslist.GoodsListActivity;
import com.example.haoss.goods.search.GoodsSearchActivity;
import com.example.haoss.indexpage.adapter.GridFavorAdapter;
import com.example.haoss.indexpage.adapter.GridSortNavAdapter;
import com.example.haoss.views.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//优选生活
public class ExcellentLifeActivity extends BaseActivity {

    FragmentView carousel;  //轮播
    ArrayList<FragmentDataInfo> listBanner; //轮播
    List<NavInfo> listNav;
    List<Recommond> listCommend;    //导航，推荐
    GridSortNavAdapter navAdapter;    //导航适配器
    GridFavorAdapter recommendAdapter;    //推荐适配器
    private String title;
    private int id;
    //    private RefreshLayout reafreshLayout;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_health_life);

        initData();
        init();
    }

    private void initData() {
        listNav = new ArrayList<>();
        listCommend = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        id = bundle.getInt("id");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        carousel.cancel();
    }

    private void init() {

        this.getTitleView().setTitleText(title);

        CustomerScrollView scrollView = findViewById(R.id.scroll_view);
        carousel = findViewById(R.id.ui_bannar);

        MyGridView gridNav = findViewById(R.id.ui_grid_nav);
        MyGridView gridFavor = findViewById(R.id.ui_grid_favor);

        TextView good_recommond_title = findViewById(R.id.ui_good_favor_title);
        good_recommond_title.setText("每日优选");

        findViewById(R.id.action_search_ss).setOnClickListener(onClickListener);
        gridNav.setOnItemClickListener(onNavClickListener);
        gridFavor.setOnItemClickListener(onRecommendClickListener);

        navAdapter = new GridSortNavAdapter(this, listNav);
        gridNav.setAdapter(navAdapter);

        recommendAdapter = new GridFavorAdapter(this, listCommend);
        gridFavor.setAdapter(recommendAdapter);

        scrollView.setOnScrollListener(new CustomerScrollView.OnScrollListener() {
            @Override
            public void loadMore() {
                page++;
                getRecommond();
            }
        });

        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id + "");
        String url = Netconfig.indexNav + Netconfig.headers;
        httpHander.okHttpMapPost(this, url, map, 1);
        getRecommond();

    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    analysisJson(msg.obj.toString());
                    break;
                case 2:
                    recommondJson(msg.obj.toString());
                    break;
            }
        }
    };

    //优选生活解析
    private void analysisJson(String json) {
        Map<String, Object> mapJson = httpHander.jsonToMap(json);
        if (mapJson == null)
            return;
        ArrayList<Object> bannerList = (ArrayList<Object>) mapJson.get("banner");   //轮播图
        ArrayList<Object> navList = (ArrayList<Object>) mapJson.get("nav"); //导航
        //装轮播
        if (bannerList != null) {
            listBanner = new ArrayList<>();
            for (int i = 0; i < bannerList.size(); i++) {
                Map<String, Object> bannerMap = (Map<String, Object>) bannerList.get(i);
                int id = (int) Double.parseDouble(bannerMap.get("id") + "");
                String imageUrl = bannerMap.get("imgUrl") + "";
                double order = Double.parseDouble(bannerMap.get("order") + "");
                FragmentDataInfo fragmentDataInfo = new FragmentDataInfo();
                fragmentDataInfo.setId(id);
                fragmentDataInfo.setImageUrl(imageUrl);
                fragmentDataInfo.setOrder(order);
                listBanner.add(fragmentDataInfo);
            }
            setCarousel();
        }

        //装导航
        if (navList != null) {
            for (int i = 0; i < navList.size(); i++) {
                Map<String, Object> navMap = (Map<String, Object>) navList.get(i);
                int id = (int) Double.parseDouble(navMap.get("id") + "");
                String name = navMap.get("cate_name") + "";
                String image = navMap.get("pic") + "";
                NavInfo info = new NavInfo();
                info.setId(id);
                info.setName(name);
                info.setImageUrl(image);
                listNav.add(info);
            }
            navAdapter.refresh(listNav);
        }
    }


    private void getRecommond() {
        String url = Netconfig.recommend;
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id + "");
        map.put("page", page);
        map.put("limit", 20);
        httpHander.okHttpMapPost(ExcellentLifeActivity.this, url, map, 2);
    }

    private void recommondJson(String s) {
//        refreshLayout.finishLoadmore();

        ArrayList<Object> listLike = httpHander.jsonToList(s);   //猜您喜欢
        if (listLike == null || listLike.isEmpty()) {
            return;
        }

        for (int i = 0; i < listLike.size(); i++) {
            Map<String, Object> mapLike = (Map<String, Object>) listLike.get(i);
            if (mapLike != null) {
                Recommond recommond = new Recommond();
                recommond.setId((int) httpHander.getDouble(mapLike, "id"));
                recommond.setImage(httpHander.getString(mapLike, "image"));
                recommond.setStore_name(httpHander.getString(mapLike, "store_name"));
                recommond.setPrice(httpHander.getString(mapLike, "price"));
                recommond.setFicti((int) httpHander.getDouble(mapLike, "ficti"));
                listCommend.add(recommond);
            }
        }
        recommendAdapter.refresh(listCommend);
    }

    //设置轮播数据
    private void setCarousel() {
        carousel.addFragment(getSupportFragmentManager(), listBanner, 3000, new OnclickFragmentView() {
            @Override
            public void onItemclick(int id, String url) {
                //轮播图点击操作
            }
        });
    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_search_ss:  //搜索
                    IntentUtils.startIntent(ExcellentLifeActivity.this, GoodsSearchActivity.class);
                    break;
            }
        }
    };

    //导航监听
    AdapterView.OnItemClickListener onNavClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ExcellentLifeActivity.this, GoodsListActivity.class);
            intent.putExtra("searchType", listNav.get(position).getId());
            startActivity(intent);
        }
    };

    //推荐监听(每日优选)
    AdapterView.OnItemClickListener onRecommendClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            IntentUtils.startIntent(listCommend.get(position).getId(), ExcellentLifeActivity.this, GoodsDetailsActivity.class);
        }
    };
}
