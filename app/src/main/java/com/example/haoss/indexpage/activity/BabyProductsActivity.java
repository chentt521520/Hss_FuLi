package com.example.haoss.indexpage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.CustomerScrollView;
import com.example.applibrary.custom.viewfragment.FragmentDataInfo;
import com.example.applibrary.custom.viewfragment.FragmentView;
import com.example.applibrary.custom.viewfragment.OnclickFragmentView;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.ViewUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.goods.goodslist.GoodsListActivity;
import com.example.haoss.goods.search.GoodsSearchActivity;
import com.example.haoss.indexpage.adapter.BrandRecommondAdapter;
import com.example.haoss.indexpage.adapter.GridFavorAdapter;
import com.example.haoss.indexpage.adapter.GridSortNavAdapter;
import com.example.haoss.indexpage.entity.NavInfo;
import com.example.haoss.indexpage.entity.Recommond;
import com.example.haoss.views.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BabyProductsActivity extends BaseActivity {

    private FragmentView carousel;  //轮播
    private TextView action_search_ss;  //搜索
    private MyGridView gridNav, gridFavor;   //导航和推荐处
    private RecyclerView gridBrandRecommad;

    private ArrayList<FragmentDataInfo> listBanner; //轮播
    private List<NavInfo> listNav;
    private List<Recommond> listFavor;
    private List<NavInfo> listBrandRecommad;    //导航，礼包

    /**
     * 商品分类适配器
     */
    private GridSortNavAdapter gideNavAdapter;  //商品分类适配器
    /**
     * 品牌推荐适配器
     */
    private BrandRecommondAdapter brandRecommondAdapter;
    /**
     * 猜你喜欢适配器
     */
    private GridFavorAdapter gideFavorAdapter;  //礼包适配器
    //    private RefreshLayout refreshLayout;
    private String title;
    private int id;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_baby_product);
        initData();
        init();
    }

    private void initData() {
        listFavor = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        id = bundle.getInt("id");
    }

    //初始化
    private void init() {
        this.getTitleView().setTitleText(title);

        CustomerScrollView scrollView = findViewById(R.id.scroll_view);
        action_search_ss = findViewById(R.id.action_search_ss);
        carousel = findViewById(R.id.ui_bannar);
        gridNav = findViewById(R.id.ui_grid_nav);
        gridBrandRecommad = findViewById(R.id.ui_grid_brand_recommad);
        gridFavor = findViewById(R.id.ui_grid_favor);

        action_search_ss.setOnClickListener(onClickListener);
        gridNav.setOnItemClickListener(onNavClickListener);
        gridFavor.setOnItemClickListener(onFavorClickListener);

        gideFavorAdapter = new GridFavorAdapter(this, listFavor);
        gridFavor.setAdapter(gideFavorAdapter);

        //创建LinearLayoutManager 对象 这里使用 LinearLayoutManager 是线性布局的意思
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(0);
        //设置RecyclerView 布局
        gridBrandRecommad.setLayoutManager(layoutmanager);

        brandRecommondAdapter = new BrandRecommondAdapter(this, listBrandRecommad);
        gridBrandRecommad.setAdapter(brandRecommondAdapter);
//        refreshLayout = findViewById(R.id.refresh_layout);
//        refreshLayout.setEnableRefresh(false);
//        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
//
//            @Override
//            public void onLoadMore(RefreshLayout refreshLayout) {
//                super.onLoadMore(refreshLayout);
//                page++;
//                getRecommond();
//            }
//        });

        scrollView.setOnScrollListener(new CustomerScrollView.OnScrollListener() {
            @Override
            public void loadMore() {
                page++;
                getRecommond();
            }
        });
        brandRecommondAdapter.setOnViewClickListener(new BrandRecommondAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent(BabyProductsActivity.this, GoodsListActivity.class);
                intent.putExtra("searchType", listBrandRecommad.get(position).getId());
                intent.putExtra("searchName", listBrandRecommad.get(position).getName());
                startActivity(intent);
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

    private void analysisJson(String json) {
        Map<String, Object> mapJson = httpHander.jsonToMap(json);
        if (mapJson == null)
            return;
        ArrayList<Object> bannerList = (ArrayList<Object>) mapJson.get("banner");   //轮播图
        ArrayList<Object> navList = (ArrayList<Object>) mapJson.get("nav"); //导航
        ArrayList<Object> recommendList = (ArrayList<Object>) mapJson.get("brand_recommendation"); //推荐品牌
        ArrayList<Object> favorList = (ArrayList<Object>) mapJson.get("recommend"); //推荐商品
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

        //导航
        if (navList != null) {
            listNav = new ArrayList<>();
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
            if (gideNavAdapter == null) {
                gideNavAdapter = new GridSortNavAdapter(this, listNav);
                gridNav.setAdapter(gideNavAdapter);
            } else
                gideNavAdapter.notifyDataSetChanged();
        }

        //倾情推荐：品牌-->点击进入该品牌的分类
        if (recommendList != null) {
            listBrandRecommad = new ArrayList<>();
            for (int i = 0; i < recommendList.size(); i++) {
                Map<String, Object> recommendMap = (Map<String, Object>) recommendList.get(i);
                int id = (int) Double.parseDouble(recommendMap.get("id") + "");
                String image = recommendMap.get("pic") + "";
                String name = recommendMap.get("cate_name") + "";
                NavInfo info = new NavInfo();
                info.setId(id);
                info.setImageUrl(image);
                info.setName(name);
                listBrandRecommad.add(info);
            }
            brandRecommondAdapter.freshList(listBrandRecommad);
        }
    }


    private void getRecommond() {
        String url = Netconfig.recommend;
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id + "");
        map.put("page", page);
        map.put("limit", 20);
        httpHander.okHttpMapPost(BabyProductsActivity.this, url, map, 2);
    }

    private void recommondJson(String s) {
        ArrayList<Object> listLike = httpHander.jsonToList(s);   //猜您喜欢

//        refreshLayout.finishLoadmore();
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
                listFavor.add(recommond);
            }
        }
        gideFavorAdapter.refresh(listFavor);
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
                    IntentUtils.startIntent(BabyProductsActivity.this, GoodsSearchActivity.class);
                    break;
            }
        }
    };

    //导航监听
    AdapterView.OnItemClickListener onNavClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(BabyProductsActivity.this, GoodsListActivity.class);
            intent.putExtra("searchType", listNav.get(position).getId());
            intent.putExtra("searchName", listNav.get(position).getName());
            startActivity(intent);
        }
    };

    //猜你喜欢：具体商品-->点击进入该商品详情
    AdapterView.OnItemClickListener onFavorClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Intent intent = new Intent(BabyProductsActivity.this, GoodsDetailsActivity.class);
//            intent.putExtra("searchType", listFavor.get(position).getId());
//            startActivity(intent);

            IntentUtils.startIntent(listFavor.get(position).getId(), BabyProductsActivity.this, GoodsDetailsActivity.class);
        }
    };

}
