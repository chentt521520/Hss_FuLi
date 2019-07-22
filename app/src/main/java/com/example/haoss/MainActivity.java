package com.example.haoss;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.ToastUtils;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.classification.ClassIficationFragment;
import com.example.haoss.indexpage.IndexPageFragment;
import com.example.haoss.person.PersonFragment;
import com.example.haoss.shopcat.ShopCatFragment;
import com.example.applibrary.utils.ActivityController;
import com.example.haoss.util.LhtTool;
import com.example.haoss.views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//首页界面
public class MainActivity extends BaseActivity {

    @BindView(R.id.linear_bar)
    LinearLayout linearBar;
    @BindView(R.id.guzhu_rb)
    RadioButton guzhuRb;
    @BindView(R.id.fuwushang_rb)
    RadioButton fuwushangRb;
    @BindView(R.id.xiaoxi_rb)
    RadioButton xiaoxiRb;
    @BindView(R.id.wode_rb)
    RadioButton wodeRb;
    @BindView(R.id.guzhu_rg)
    RadioGroup guzhuRg;
    @BindView(R.id.main_ll)
    LinearLayout mainLl;
    @BindView(R.id.main_vp)
    NoScrollViewPager mainVp;
    private List<Fragment> fraList = new ArrayList<>();
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setZhuangTaiLan();
        fragmentList();
        setView();
        viewListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1)
                fraList.get(0).onActivityResult(requestCode, resultCode, data);
            if (requestCode == 2)
                fraList.get(1).onActivityResult(requestCode, resultCode, data);
            if (requestCode == 3)
                fraList.get(2).onActivityResult(requestCode, resultCode, data);
            if (requestCode == 4)
                fraList.get(3).onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 设置状态栏
     */
    private void setZhuangTaiLan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏0
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            linearBar = findViewById(R.id.linear_bar);
            linearBar.setVisibility(View.VISIBLE);
            linearBar.setBackgroundColor(Color.parseColor("#ffffff"));
            int statusHeight = LhtTool.getStatusBarHeight(this);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearBar.getLayoutParams();
            params.height = statusHeight;
            linearBar.setLayoutParams(params);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置导航栏为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 102;
            String[] permissions = {Manifest.permission.READ_PHONE_STATE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }

    /**
     * 操作view
     */
    private void setView() {
        mainVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fraList.get(i);
            }

            @Override
            public int getCount() {
                return fraList.size();
            }
        });
        if (null != getIntent().getExtras() && getIntent().getExtras().equals("3")) {
            mainVp.setCurrentItem(2);
        }
    }

    /**
     * view监听事件
     */
    private void viewListener() {
        guzhuRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int x = 0;
                switch (checkedId) {

                    case R.id.guzhu_rb:
                        x = 0;
                        break;
                    case R.id.fuwushang_rb:
                        x = 1;
                        break;
                    case R.id.xiaoxi_rb:
                        x = 2;
                        break;
                    case R.id.wode_rb:
                        x = 3;
                        break;

                }
                mainVp.setCurrentItem(x);
                if (x == 3) {
                    // linear_bar.setBackgroundColor(Color.parseColor("#D27AA0"));
                    if (linearBar != null) {
                        linearBar.setVisibility(View.VISIBLE);
                        linearBar.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                } else if (x == 2) {
                    if (linearBar != null) {
                        linearBar.setVisibility(View.VISIBLE);
                        linearBar.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                } else if (x == 0) {
                    if (linearBar != null) {
                        linearBar.setVisibility(View.VISIBLE);
                        linearBar.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                } else {
                    if (linearBar != null) {
                        linearBar.setVisibility(View.VISIBLE);
                        linearBar.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }
            }
        });
    }

    /**
     * 主页fragment
     */
    private void fragmentList() {
        fraList.add(new IndexPageFragment());
        fraList.add(new ClassIficationFragment());
        fraList.add(new ShopCatFragment());
        fraList.add(new PersonFragment());
    }

    /**
     * back键点击两次弹出是否退出整个程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.getToastUtils().showToast(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
