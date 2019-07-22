package com.example.applibrary.custom.viewfragment;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.applibrary.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//轮播图View
public class FragmentView extends RelativeLayout {
    Context context;
    View view;
    ViewPager viewPager;    //轮播
    RadioGroup dot; //点
    private List<Fragment> listCarousel = new ArrayList<>();    //轮播界面
    Timer timer;  //轮播定时器
    OnclickFragmentView onclickFragmentView;    //监听

    public FragmentView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public FragmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.layout_fragmentview, null);
        viewPager = view.findViewById(R.id.fragmentview_viewpager);
        dot = view.findViewById(R.id.fragmentview_dot);
        addView(view);
    }

    /**
     * 添加轮播图
     *
     * @param fm   管理
     * @param list f数据
     * @param time 时间
     */
    public void addFragment(FragmentManager fm, ArrayList<FragmentDataInfo> list, long time, OnclickFragmentView onclickFragmentView) {
        this.onclickFragmentView = onclickFragmentView;
        if (list == null || list.size() == 0)
            return;
        Collections.sort(list, new SortFragmentDataInfo());
        cancel();
        for (FragmentDataInfo dataInfo : list) {
            listCarousel.add(new CarouselFragment(dataInfo, onclickFragmentView));
            RadioButton radioButton = new RadioButton(context);
            radioButton.setButtonDrawable(null);
            radioButton.setWidth(16);
            radioButton.setHeight(16);
            radioButton.setBackgroundResource(R.drawable.radiobutton_checked_on_off);
            dot.addView(radioButton);
        }
        FragmentAdapter carouselAdapter = new FragmentAdapter(fm, listCarousel);
        viewPager.setAdapter(carouselAdapter);
        timer = new Timer();
        index = 1;
        dot.getChildAt(0).performClick();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handlerBanner.sendEmptyMessage(index % listCarousel.size());
                index++;
            }
        }, time, time);
        //滑动监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                dot.getChildAt(i).performClick();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

//        //RadioGroup点击事件
//        dot.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                viewPager.setCurrentItem(checkedId);
//            }
//        });
    }

    //关闭
    public void cancel() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        listCarousel.clear();
    }

    //换图
    int index = 1;    //循环次数
    Handler handlerBanner = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(msg.what);
        }
    };


}
