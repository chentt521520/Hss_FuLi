package com.example.haoss.indexpage.getcoupon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.goodslist.GoodsListActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//领券中心界面
public class GetCouponCentreActivity extends BaseActivity {

    ListView listView;  //列表控件
    RefreshLayout refreshLayout;
    GetCouponCentreAdapter adapter;  //列表适配器
    List<CouponInfo> listCoupon = new ArrayList<>();    //数据
    private int id;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_coupon_centre);
        init();

    }

    private void init() {
        getTitleView().setTitleText("领券中心"); //标题
        listView = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                listCoupon.clear();
                queryCouponList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                queryCouponList();
            }
        });


        adapter = new GetCouponCentreAdapter(this, listCoupon, new GetCouponCentreAdapter.OClick() {
            @Override
            public void onClick(CouponInfo info) {
                if (info.isIs_use()) {//已领取
                    Intent intent = new Intent(GetCouponCentreActivity.this, GoodsListActivity.class);
                    intent.putExtra("searchType", info.getCategory_id());
                    startActivity(intent);
                } else {//未领取
                    getCoupon(info);
                }
                id = info.getId();

            }
        });
        listView.setAdapter(adapter);
        queryCouponList();
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    //查询可以领券的优惠劵列表
    private void queryCouponList() {
        if (AppLibLication.getInstance().isLogin()) {//已登录
            String url = Netconfig.couponsOkGet;
            Map<String, Object> map = new HashMap<>();
            map.put("page", page);
            map.put("limit", 20);
            map.put("token", AppLibLication.getInstance().getToken());
            httpHander.okHttpMapPost(this, url, map, 1);
        }
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);

            switch (msg.arg1) {
                case 1:
                    analysis(jsonToList(msg.obj.toString()));
                    break;

                case 2:
                    analysis2(msg.obj.toString());
                    break;
            }
        }
    };

    private void analysis2(String json) {
        try {
            HashMap<String, Object> map = new Gson().fromJson(json, HashMap.class);
            double code = httpHander.getDouble(map, "code");
            if (code == 200) {
                tost("领取成功");//领取成功
                setList();
            } else {
                tost(httpHander.getString(map, "msg"));
            }
        } catch (Exception e) {
            tost(e.getMessage());
        }
    }

    public void setList() {
        for (CouponInfo centerInfo : listCoupon) {
            if (centerInfo.getId() == id) {
                centerInfo.setIs_use(true);
            }
        }
        adapter.fresh(listCoupon);
    }

    //解析
    private void analysis(ArrayList<Object> arrayList) {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            Map<String, Object> map = (Map<String, Object>) arrayList.get(i);
            if (map != null) {
                CouponInfo couponInfo = new CouponInfo();
                couponInfo.setId((int) httpHander.getDouble(map, "id"));
                couponInfo.setTitle(httpHander.getString(map, "title"));
                couponInfo.setCid((int) httpHander.getDouble(map, "cid"));
                couponInfo.setStart_time(httpHander.getString(map, "add_time"));
                couponInfo.setEnd_time(httpHander.getString(map, "end_time"));
                couponInfo.setStatus((int) httpHander.getDouble(map, "status"));
                couponInfo.setCoupon_price(httpHander.getString(map, "coupon_price"));
                couponInfo.setTotal_count((int) httpHander.getDouble(map, "total_count"));
                couponInfo.setRemain_count((int) httpHander.getDouble(map, "remain_count"));
                couponInfo.setIs_permanent((int) httpHander.getDouble(map, "is_permanent"));
                couponInfo.setIs_use((Boolean) map.get("is_use"));
                couponInfo.setCategory_id((int) httpHander.getDouble(map, "category_id"));
                couponInfo.setCategory_name(httpHander.getString(map, "category_name"));
                listCoupon.add(couponInfo);
            }
        }
        adapter.fresh(listCoupon);
    }

    //领取、逛逛、使用优惠劵
    private void getCoupon(CouponInfo info) {
        if (info.getStatus() == 1) {
            if (info.getIs_permanent() == 1) {
                String url = Netconfig.AccessCoupon;
                Map<String, Object> map = new HashMap<>();
                map.put("couponId", info.getId());
                map.put("token", AppLibLication.getInstance().getToken());
                httpHander.okHttpMapPost(this, url, map, 2);
            } else {
                int residue = info.getTotal_count() - info.getRemain_count();
                if (residue > 0) {
                    String url = Netconfig.AccessCoupon;
                    Map<String, Object> map = new HashMap<>();
                    map.put("couponId", info.getId());
                    map.put("token", AppLibLication.getInstance().getToken());
                    httpHander.okHttpMapPost(this, url, map, 2);
                } else {
                    tost("去逛逛");//你来晚了
                }
            }
        } else
            tost("去使用");
    }


}
