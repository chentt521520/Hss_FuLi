package com.example.haoss.indexpage.tourdiy;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.DensityUtil;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.ViewUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.indexpage.tourdiy.entity.GrouponGoodInfo;
import com.example.haoss.indexpage.tourdiy.entity.GrouponResult;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GrouponNoticeActivity extends BaseActivity {

    private int pinkId;
    private TextView remainCount;
    private ImageView goodImage;
    private TextView goodName;
    private TextView goodPrice;
    private TextView goodOtPrice;
    private GrouponResult grouponGood;
    private LinearLayout grouponTeamContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_groupon_notice);

        pinkId = getIntent().getIntExtra(IntentUtils.intentActivityFlag, -1);

        initView();
        initData();
    }

    private void initView() {
        this.getTitleView().setTitleText("拼团详情");

        goodImage = findViewById(R.id.groupon_good_image);
        goodName = findViewById(R.id.groupon_good_name);
        goodPrice = findViewById(R.id.groupon_good_price);
        goodOtPrice = findViewById(R.id.single_buy_price);
        remainCount = findViewById(R.id.group_remaining_people);

        grouponTeamContainer = findViewById(R.id.groupon_team_container);
        findViewById(R.id.btn_invite_partner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startIntent(GrouponNoticeActivity.this, GrouponListActivity.class);
            }
        });
    }

    //立即购买
    private void initData() {

        String url = Netconfig.kaiTuanActivity;
        HashMap<String, Object> map = new HashMap<>();

        map.put("id", pinkId);
        map.put("token", AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(this, url, map, 1);
    }


    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    Log.e("~~~~~~~~~~~", msg.obj.toString());
                    try {
                        Map ret = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (ret != null && getDouble(ret, "code") == 200) {
                            handleJson(getMap(ret, "data"));
                        } else {
                            tost(getString(ret, "msg"));
                        }
                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
            }
        }
    };

    private void handleJson(Map<String, Object> data) {

        if (data != null) {
            grouponGood = new GrouponResult();

            //是否拼团成功
            Map<String, Integer> integerMap = httpHander.getIntegerMap(data, "pinkBool", "is_ok", "userBool", "count");

            grouponGood.setPinkBool(integerMap.get("pinkBool"));
            grouponGood.setIs_ok(integerMap.get("is_ok"));
            grouponGood.setUserBool(integerMap.get("userBool"));
            grouponGood.setCount(integerMap.get("count"));
            grouponGood.setCurrent_pink_order(httpHander.getString(data, "current_pink_order"));

            Map<String, Object> store_combination = httpHander.getMap(data, "store_combination");
            GrouponGoodInfo goodInfo = new GrouponGoodInfo();

            Map<String, Integer> integerMap1 = httpHander.getIntegerMap(store_combination, "id", "product_id", "people", "combination_id");

            goodInfo.setId(integerMap1.get("id"));
            goodInfo.setProduct_id(integerMap1.get("product_id"));
            goodInfo.setCombination_id(integerMap1.get("combination_id"));
            goodInfo.setPeople(integerMap1.get("people"));
            goodInfo.setImage(httpHander.getString(store_combination, "image"));
            goodInfo.setTitle(httpHander.getString(store_combination, "title"));
            goodInfo.setPrice(httpHander.getString(store_combination, "price"));
            goodInfo.setProduct_price(httpHander.getString(store_combination, "product_price"));

            grouponGood.setStore_combination(goodInfo);

            ArrayList<Object> pinkAll = httpHander.getList(data, "pinkAll");
            List<GrouponResult.UserBean> pinkList = new ArrayList<>();
            for (int i = 0; i < pinkAll.size(); i++) {
                Map<String, Object> pinkMap = (Map<String, Object>) pinkAll.get(i);
                String image = httpHander.getString(pinkMap, "avatar");
                int id = (int) httpHander.getDouble(pinkMap, "k_id");
                GrouponResult.UserBean bean = new GrouponResult.UserBean(id, image);
                pinkList.add(bean);
            }
            grouponGood.setPinkAll(pinkList);

            setView();
        }

        /*       *
         * "pinkBool": false,
         *     "is_ok": 0,
         *     "userBool": 1,
         *     "store_combination": {
         *       "id": 30,
         *       "product_id": 749,
         *       "mer_id": 0,
         *       "image": "http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg",
         *       "title": "澳洲爱他美Aptamil金装婴幼儿配方奶粉3段 900g 1-2岁适用（澳爱）",
         *       "people": 3,
         *       "info": "爱他美",
         *       "price": "169.00",
         *       "sort": 0,
         *       "sales": 2111,
         *       "stock": 787,
         *       "add_time": "1563255118",
         *       "is_host": 1,
         *       "is_show": 1,
         *       "is_del": 0,
         *       "is_postage": 1,
         *       "postage": "10.00",
         *       "start_time": 1561910400,
         *       "stop_time": 1567180800,
         *       "cost": 0,
         *       "browse": 0,
         *       "unit_name": "",
         *       "product_price": "171.00",
         *       "combination_id": 30
         *     },
         *     "pinkAll": [
         *       {
         *         "avatar": "http://py.haoshusi.com/avatar/761a4d1eb8fddcc8d2f74b12d0d62215.jpg",
         *         "k_id": 0
         *       }
         *     ],
         *     "count": 2,
         *     "current_pink_order": "wx2019071711512010002"
         */

    }

    private void setView() {
        ImageUtils.imageLoad(GrouponNoticeActivity.this, grouponGood.getStore_combination().getImage(), goodImage);
        goodName.setText(grouponGood.getStore_combination().getTitle());
        goodPrice.setText(grouponGood.getStore_combination().getPrice());
        goodOtPrice.setText(grouponGood.getStore_combination().getProduct_price());
        goodOtPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        int num = grouponGood.getStore_combination().getPeople() - grouponGood.getPinkAll().size();
        remainCount.setText("还差" + grouponGood.getCount() + "人拼团成功，剩余时间");

        grouponTeamContainer.removeAllViews();
        int size = grouponGood.getPinkAll().size();
        for (int i = 0; i < grouponGood.getStore_combination().getPeople(); i++) {
            ImageView image = new ImageView(GrouponNoticeActivity.this);
            int width = DensityUtil.dip2px(GrouponNoticeActivity.this, 60);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            if (i != 0) {
                params.setMargins(DensityUtil.dip2px(GrouponNoticeActivity.this, 30), 0, 0, 0);
            }
            image.setLayoutParams(params);
            if (i < size) {
                ImageUtils.loadCirclePic(GrouponNoticeActivity.this, grouponGood.getPinkAll().get(i).getAvatar(), image);
            } else {
                image.setImageResource(R.mipmap.unknow_groupon_partner);
            }
            grouponTeamContainer.addView(image);
        }
    }


    /**
     * 倒数计时器
     */
    private CountDownTimer timer = new CountDownTimer(15 * 60 * 1000, 1000) {
        /**
         * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
//            tv_remaining_time.setText(formatTime(millisUntilFinished));
        }

        /**
         * 倒计时完成时被调用
         */
        @Override
        public void onFinish() {
//            tv_remaining_time.setText("00:00");
        }
    };


    /**
     * 开始倒计时
     */
    public void timerStart() {
        timer.start();
    }

    /**
     * 将毫秒转化为 分钟：秒 的格式
     *
     * @param millisecond 毫秒
     * @return
     */
    public String formatTime(long millisecond) {
        int minute;//分钟
        int second;//秒数
        minute = (int) ((millisecond / 1000) / 60);
        second = (int) ((millisecond / 1000) % 60);
        if (minute < 10) {
            if (second < 10) {
                return "0" + minute + ":" + "0" + second;
            } else {
                return "0" + minute + ":" + second;
            }
        } else {
            if (second < 10) {
                return minute + ":" + "0" + second;
            } else {
                return minute + ":" + second;
            }
        }
    }
}
