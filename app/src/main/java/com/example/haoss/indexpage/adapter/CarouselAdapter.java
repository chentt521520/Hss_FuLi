package com.example.haoss.indexpage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

//轮播图适配器
public class CarouselAdapter extends FragmentPagerAdapter {
    List<Fragment> list;

    public CarouselAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }
}
