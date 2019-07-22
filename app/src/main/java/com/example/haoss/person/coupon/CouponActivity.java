package com.example.haoss.person.coupon;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.ViewUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.goodslist.GoodsListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//优惠劵界面
public class CouponActivity extends BaseActivity {

    TextView couponactivity_unused, couponactivity_hassed, couponactivity_out;    //使用状态
    ListView couponactivity_listview;   //数据列表
    Map<Integer, List<CouponInfo>> mapCoupon;    //优惠劵所有类型数据：0：未使用，1：已使用, 2:已过期
    List<CouponInfo> listCoupon;    //当前选择的数据
    CouponAdapter couponAdapter;    //优惠劵列表适配器
    private int type = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_my_coupon);
        init();
        initData();
    }

    private void initData() {
        listCoupon = new ArrayList<>();
        mapCoupon = new HashMap<>();
        couponAdapter = new CouponAdapter(this, listCoupon, 1);
        couponactivity_listview.setAdapter(couponAdapter);
    }

    //初始化
    private void init() {
        this.getTitleView().setTitleText("我的优惠劵");
        couponactivity_unused = findViewById(R.id.couponactivity_unused);
        couponactivity_hassed = findViewById(R.id.couponactivity_hassed);
        couponactivity_out = findViewById(R.id.couponactivity_out);
        couponactivity_listview = findViewById(R.id.couponactivity_listview);

        couponactivity_unused.setOnClickListener(onClickListener);
        couponactivity_hassed.setOnClickListener(onClickListener);
        couponactivity_out.setOnClickListener(onClickListener);

        couponactivity_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CouponInfo item = (CouponInfo) couponAdapter.getItem(position);
                if (item.getStatus() == 0) {
                    Intent intent = new Intent(CouponActivity.this, GoodsListActivity.class);
                    intent.putExtra("searchType", item.getCategory_id());
                    startActivity(intent);
                }
            }
        });

        getCouponList();
    }

    //获取优惠劵列表
    private void getCouponList() {
        String url = Netconfig.couponsList;
        Map<String, Object> map = new HashMap<>();
        map.put("types", type);//（0：未使用，1：已使用, 2:已过期 ）不传或传其它为全部
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(this, url, map, 1);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            ArrayList<Object> list = jsonToList(msg.obj.toString());
            if (list != null)
                analysis(list);
        }
    };

    //解析
    private void analysis(ArrayList<Object> list) {
        listCoupon.clear();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = (Map<String, Object>) list.get(i);
            if (map != null) {
                int type = (int) httpHander.getDouble(map, "status");    //状态（0：未使用，1：已使用, 2:已过期）
                CouponInfo info = new CouponInfo();
                info.setStatus(type);
                info.setId((int) httpHander.getDouble(map, "id"));
                info.setCid((int) httpHander.getDouble(map, "cid"));
                info.setCoupon_title(httpHander.getString(map, "coupon_title"));
                info.setCoupon_price(httpHander.getString(map, "coupon_price"));
                info.setUse_min_price(httpHander.getString(map, "use_min_price"));
                info.setAdd_time(httpHander.getString(map, "add_time"));
                info.setEnd_time(httpHander.getString(map, "end_time"));
                info.setCategory_name(httpHander.getString(map, "category_name"));
                info.setCategory_id((int) httpHander.getDouble(map, "category_id"));
                info.setUse_time((long) httpHander.getDouble(map, "use_time"));
                listCoupon.add(info);

            }
        }
        mapCoupon.put(type, listCoupon);
        couponAdapter.setRefresh(listCoupon);
    }


    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_title_goback:    //返回
                    finish();
                    return;
                case R.id.couponactivity_unused:    //未使用
                    type = 0;
                    break;
                case R.id.couponactivity_hassed:    //已使用
                    type = 1;
                    break;
                case R.id.couponactivity_out:   //已过期
                    type = 2;
                    break;
            }
            setData(type);
//            if (mapCoupon != null && mapCoupon.get(type) != null) {
//                listCoupon = mapCoupon.get(type);
//            } else {
            getCouponList();
//            }
//            couponAdapter.setRefresh(listCoupon);
        }
    };

    //设置数据
    private void setData(int choose) {

        switch (choose) {
            case 0:
                setTextView(true, false, false);
                break;
            case 1:
                setTextView(false, true, false);
                break;
            case 2:
                setTextView(false, false, true);
                break;
        }
    }

    //设置字体
    private void setTextView(boolean b0, boolean b1, boolean b2) {
        couponactivity_unused.setTextColor(Color.parseColor(b0 ? "#4d4d4d" : "#0f0f0f"));
        couponactivity_unused.setTextSize(TypedValue.COMPLEX_UNIT_SP, b0 ? 14 : 12);
        couponactivity_unused.setTypeface(b0 ? Typeface.defaultFromStyle(Typeface.BOLD) : Typeface.defaultFromStyle(Typeface.NORMAL));

        couponactivity_hassed.setTextColor(Color.parseColor(b1 ? "#4d4d4d" : "#0f0f0f"));
        couponactivity_hassed.setTextSize(TypedValue.COMPLEX_UNIT_SP, b1 ? 14 : 12);
        couponactivity_hassed.setTypeface(b1 ? Typeface.defaultFromStyle(Typeface.BOLD) : Typeface.defaultFromStyle(Typeface.NORMAL));

        couponactivity_out.setTextColor(Color.parseColor(b2 ? "#4d4d4d" : "#0f0f0f"));
        couponactivity_out.setTextSize(TypedValue.COMPLEX_UNIT_SP, b2 ? 14 : 12);
        couponactivity_out.setTypeface(b2 ? Typeface.defaultFromStyle(Typeface.BOLD) : Typeface.defaultFromStyle(Typeface.NORMAL));
    }
}
