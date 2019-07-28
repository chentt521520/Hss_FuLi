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
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.resp.RespGrouponResult;
import com.example.applibrary.utils.DateTimeUtils;
import com.example.applibrary.utils.DensityUtil;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;


public class GrouponNoticeActivity extends BaseActivity {

    private int pinkId;
    private TextView remainCount;
    private ImageView goodImage;
    private TextView goodName;
    private TextView goodPrice;
    private TextView goodOtPrice;
    private TextView hour_1;
    private TextView hour_2;
    private TextView minute_1;
    private TextView minute_2;
    private TextView second_1;
    private TextView second_2;
    private RespGrouponResult.GrouponResult grouponGood;
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
        grouponTeamContainer = findViewById(R.id.group_remaining_people_container);
        //付款成功，等待拼团
        findViewById(R.id.groupon_waitting).setVisibility(View.GONE);
        hour_1 = findViewById(R.id.timer_view_hour_1);
        hour_2 = findViewById(R.id.timer_view_hour_2);
        minute_1 = findViewById(R.id.timer_view_minute_1);
        minute_2 = findViewById(R.id.timer_view_minute_2);
        second_1 = findViewById(R.id.timer_view_second_1);
        second_2 = findViewById(R.id.timer_view_second_2);

        //付款成功，拼团
        findViewById(R.id.groupon_result_view).setVisibility(View.VISIBLE);

        findViewById(R.id.btn_groupon_more).setOnClickListener(listener);
        findViewById(R.id.btn_invite_partner).setOnClickListener(listener);


    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IntentUtils.startIntent(GrouponNoticeActivity.this, GrouponListActivity.class);
        }
    };

    //立即购买
    private void initData() {

        String url = Netconfig.kaiTuanActivity;
        Map<String, Object> map = new HashMap<>();

        map.put("id", pinkId + "");
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

                    getGrouponResult(msg.obj.toString(), new OnHttpCallback<RespGrouponResult.GrouponResult>() {
                        @Override
                        public void success(RespGrouponResult.GrouponResult result) {
                            grouponGood = result;
                            setView();
                        }

                        @Override
                        public void error(int code, String msg) {
                            tost(code + msg);
                        }
                    });
                    break;
            }
        }
    };

    private void setView() {
        Log.e("~~~~~~~~~", "size:" + grouponGood.getPinkAll().size());
        ImageUtils.imageLoad(GrouponNoticeActivity.this, grouponGood.getStore_combination().getImage(), goodImage);
        goodName.setText(grouponGood.getStore_combination().getTitle());
        goodPrice.setText(grouponGood.getStore_combination().getPrice());
        goodOtPrice.setText(grouponGood.getStore_combination().getProduct_price());
        goodOtPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        int num = grouponGood.getStore_combination().getPeople() - grouponGood.getPinkAll().size();
        remainCount.setText("还差" + grouponGood.getCount() + "人拼团成功，剩余时间");

        long currentTime = System.currentTimeMillis() / 1000;
        long remainTime = grouponGood.getStore_combination().getStop_time() - currentTime;
        Log.e("~~~~~~~~~", "startTime：" + DateTimeUtils.timeStampToDate(grouponGood.getStore_combination().getStart_time() * 1000L));
        Log.e("~~~~~~~~~", "stoptime：" + DateTimeUtils.timeStampToDate(grouponGood.getStore_combination().getStop_time() * 1000L));
        Log.e("~~~~~~~~~", "currentTime：" + currentTime);
        Log.e("~~~~~~~~~", "people：" + grouponGood.getStore_combination().getPeople());

        String timeStr = DateTimeUtils.timeStampToDate(grouponGood.getStore_combination().getStop_time() * 1000L);

        String[] split = timeStr.substring(11, timeStr.length()).split(":");
        Log.e("~~~~~~~~~", "timeStr：" + timeStr.substring(11, timeStr.length()));
        hour_1.setText(split[0].substring(0, 1));
        hour_2.setText(split[0].substring(1, 2));
        minute_1.setText(split[1].substring(0, 1));
        minute_2.setText(split[1].substring(1, 2));
        second_1.setText(split[2].substring(0, 1));
        second_2.setText(split[2].substring(1, 2));

        grouponTeamContainer.removeAllViews();
        if (grouponGood.getPinkAll() == null) {
            return;
        }
        int size = grouponGood.getPinkAll().size();
        for (int i = 0; i < 3; i++) {
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
