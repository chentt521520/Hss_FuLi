package com.example.haoss.shopcat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.applibrary.custom.viewfragment.FragmentAdapter;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

//购物车activity界面
public class ShopCatActivity extends BaseActivity {

    ViewPager viewPager;
    private List<Fragment> listCarousel = new ArrayList<>();    //轮播界面

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);
        viewPager = findViewById(R.id.shopcatactivity_viewpage);
        listCarousel.add(new ShopCatFragment(IntentUtils.intentActivityAssign));
        FragmentAdapter carouselAdapter = new FragmentAdapter(getSupportFragmentManager(), listCarousel);
        viewPager.setAdapter(carouselAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        viewPager.notifyAll();
    }
}
