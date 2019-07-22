package com.example.haoss.integralshop;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.MyGridView;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.integralshop.adapter.FuncAdapter;
import com.example.haoss.integralshop.adapter.IntegralSListAdapter;
import com.example.haoss.integralshop.entity.FuncInfo;
import com.example.haoss.integralshop.entity.IntegralInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//积分商城
public class IntegralShopActivity extends BaseActivity {

    ImageView integralshop_image;   //头像
    TextView integralshop_nickname, integralshop_integralnumber;   //昵称、积分数量
    MyGridView integralshop_func;   //5功能选项
    FuncAdapter funcAdapter;    //5选项适配器
    List<FuncInfo> listFunc = new ArrayList<>();    //5选项数据

    ListView integralshop_listview; //数据控件
    List<IntegralInfo> listIntegralInfo = new ArrayList<>();    //列表数据
    IntegralSListAdapter integralSListAdapter;  //列表适配器
    private AppLibLication application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_integralshopactivity);
        application = AppLibLication.getInstance();
        init();
    }

    private void init() {
        findViewById(R.id.integralshop_return).setOnClickListener(onClickListener); //返回
        findViewById(R.id.integralshop_record).setOnClickListener(onClickListener); //兑换记录
        integralshop_image = findViewById(R.id.integralshop_image);
        integralshop_nickname = findViewById(R.id.integralshop_nickname);
        integralshop_integralnumber = findViewById(R.id.integralshop_integralnumber);
        integralshop_func = findViewById(R.id.integralshop_func);
        integralshop_listview = findViewById(R.id.integralshop_listview);

        funcChoose();
        getLikeList();
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.integralshop_return)  //返回
                finish();

        }
    };

    //5选项
    private void funcChoose() {
        listFunc.clear();
        int[] images = {R.drawable.integralshop_yhsh, R.drawable.integralshop_jksh, R.drawable.integralshop_njlb,
                R.drawable.integralshop_lywl, R.drawable.integralshop_srh};
        String[] titles = {"优选生活", "健康生活", "年节礼包", "旅游玩乐", "生日汇"};
        for (int i = 0; i < 5; i++) {
            FuncInfo funcInfo = new FuncInfo();
            funcInfo.setImage(images[i]);
            funcInfo.setTitle(titles[i]);
            listFunc.add(funcInfo);
        }

        if (funcAdapter == null) {
            funcAdapter = new FuncAdapter(this, listFunc);
            integralshop_func.setAdapter(funcAdapter);
        } else
            funcAdapter.notifyDataSetChanged();
    }

    //获取猜您喜欢列表
    private void getLikeList() {
        if (application.isLogin()) {
            String url = Netconfig.integralShopList;
            Map<String, Object> map = new HashMap<>();
            map.put("token", application.getToken());
            httpHander.okHttpMapPost(this, url, map, 1);
        }
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            ArrayList<Object> arrayList = jsonToList(msg.obj.toString());
            analysis(arrayList);

        }
    };

    //解析
    private void analysis(ArrayList<Object> arrayList) {
        if (arrayList != null) {
            listIntegralInfo.clear();
            for (int i = 0; i < arrayList.size(); i++) {
                Map<String, Object> map = (Map<String, Object>) arrayList.get(i);
                if (map != null) {
                    IntegralInfo integralInfo = new IntegralInfo();
                    integralInfo.setId((int) httpHander.getDouble(map, "id"));
                    integralInfo.setMerId((int) httpHander.getDouble(map, "mer_id"));
                    integralInfo.setImage(httpHander.getString(map, "image"));
                    listIntegralInfo.add(integralInfo);
                }
            }

//            SharedPreferenceUtils.setPreference(IntegralShopActivity.this, ConfigVariate.integral, info.getIntegral(), "S");

            if (integralSListAdapter == null) {
                integralSListAdapter = new IntegralSListAdapter(this, listIntegralInfo);
                integralshop_listview.setAdapter(integralSListAdapter);
            } else
                integralSListAdapter.notifyDataSetChanged();
        }
    }
}
