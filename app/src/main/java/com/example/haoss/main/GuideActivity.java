package com.example.haoss.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.applibrary.entity.BannerInfo;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.MainActivity;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.indexpage.adapter.CarouselAdapter;
import com.example.applibrary.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


//引导页
public class GuideActivity extends BaseActivity {
    private ViewPager carousel;    //轮播
    ArrayList<BannerInfo> listBannerInfo = new ArrayList<>();   //轮播图
    private List<Fragment> listCarousel = new ArrayList<>();    //轮播界面
    Timer timer;    //启动页跳转定时器
    final long time = 3300;  //等待时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        carousel = findViewById(R.id.layout_guideactivity_carousel);
        int num = (int) SharedPreferenceUtils.getPreference(this, "Number", "I");
        if (num != 0) {
            findViewById(R.id.layout_guideactivity_start).setVisibility(View.VISIBLE);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, time);
            return;
        }
        SharedPreferenceUtils.setPreference(this, "Number", 1, "I");
        carousel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        carousel = null;
        listBannerInfo = null;
        listCarousel = null;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            IntentUtils.startIntent(GuideActivity.this, MainActivity.class);
            finish();
        }
    };

    //引导轮播图加载
    private void carousel() {
        int[] images = {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};
        for (int i = 0; i < images.length; i++) {
            BannerInfo bannerInfo = new BannerInfo();
            bannerInfo.setId(images[i]);
            bannerInfo.setTitle("");
            bannerInfo.setImgUrl("");
            bannerInfo.setOrder(i);
            listBannerInfo.add(bannerInfo);
        }
        addFragment(listBannerInfo);
    }

    //添加轮播fagment
    private void addFragment(ArrayList<BannerInfo> listBannerInfo) {
        for (BannerInfo bannerInfo : listBannerInfo) {
            listCarousel.add(new GuideFragment(bannerInfo.getId(), bannerInfo.getOrder()));
        }
        CarouselAdapter carouselAdapter = new CarouselAdapter(getSupportFragmentManager(), listCarousel);
        carousel.setAdapter(carouselAdapter);
        //滑动监听
        carousel.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
