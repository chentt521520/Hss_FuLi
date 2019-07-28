package com.example.haoss.indexpage.getcoupon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.httpUtils.RespList;
import com.example.applibrary.resp.RespCoupon;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.goodslist.GoodsListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//领券中心界面
public class CouponCentreActivity extends BaseActivity {

    ListView listView;  //列表控件
    RefreshLayout refreshLayout;
    CouponCentreAdapter adapter;  //列表适配器
    List<RespCoupon.Coupon> listCoupon = new ArrayList<>();    //数据
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


        adapter = new CouponCentreAdapter(this, listCoupon, new CouponCentreAdapter.OClick() {
            @Override
            public void onClick(RespCoupon.Coupon info) {
                if (info.isIs_use()) {//已领取
                    Intent intent = new Intent(CouponCentreActivity.this, GoodsListActivity.class);
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
                    getCoupon(msg.obj.toString(), new OnHttpCallback<List<RespCoupon.Coupon>>() {
                        @Override
                        public void success(List<RespCoupon.Coupon> result) {
                            refreshLayout.finishRefreshing();
                            refreshLayout.finishLoadmore();
                            if (page == 1) {
                                listCoupon.clear();
                            }
                            listCoupon.addAll(result);
                            adapter.fresh(listCoupon);
                        }

                        @Override
                        public void error(int code, String msg) {
                            refreshLayout.finishRefreshing();
                            refreshLayout.finishLoadmore();
                            tost(code + "," + msg);
                        }
                    });
                    break;

                case 2:
                    getListResult(msg.obj.toString(), new OnHttpCallback<List>() {
                        @Override
                        public void success(List result) {
                            tost("领取成功");//领取成功
                            setList();
                        }

                        @Override
                        public void error(int code, String msg) {
                            tost(code + "," + msg);//领取成功
                        }
                    });
                    break;
            }
        }
    };


    public void setList() {
        for (RespCoupon.Coupon centerInfo : listCoupon) {
            if (centerInfo.getId() == id) {
                centerInfo.setIs_use(true);
            }
        }
        adapter.fresh(listCoupon);
    }

    //领取、逛逛、使用优惠劵
    private void getCoupon(RespCoupon.Coupon info) {
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
