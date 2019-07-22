package com.example.haoss.person.aftersale;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.TextViewUtils;
import com.example.applibrary.utils.ViewUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.person.aftersale.adapter.AfterSaleAdapter;
import com.example.haoss.person.aftersale.entity.AfterSaleInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//售后界面
public class AfterSaleActivity extends BaseActivity {

    TextView aftersaleactivity_applyfor, aftersaleactivity_inhand, aftersaleactivity_record;    //售后申请、处理中、申请记录
    ListView aftersaleactivity_listview;    //数据控件

    Map<Integer, List<AfterSaleInfo>> mapAfterSale = new HashMap<>();    //各类型数据
    AfterSaleAdapter afterSaleAdapter;  //售后情况适配器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_aftersale);
        init();
        getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == IntentUtils.intentActivityRequestCode && resultCode == RESULT_OK) {
            tost("回调执行");
            getData();
        }
    }

    private void init() {
        getTitleView().setTitleText("售后服务");

        aftersaleactivity_applyfor = findViewById(R.id.aftersaleactivity_applyfor);
        aftersaleactivity_inhand = findViewById(R.id.aftersaleactivity_inhand);
        aftersaleactivity_record = findViewById(R.id.aftersaleactivity_record);
        aftersaleactivity_listview = findViewById(R.id.aftersaleactivity_listview);

        aftersaleactivity_applyfor.setOnClickListener(onClickListener);
        aftersaleactivity_inhand.setOnClickListener(onClickListener);
        aftersaleactivity_record.setOnClickListener(onClickListener);
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_title_goback:  //返回
                    finish();
                    break;
                case R.id.aftersaleactivity_applyfor:  //申请
                    setTextData(0);
                    break;
                case R.id.aftersaleactivity_inhand:  //处理
                    setTextData(1);
                    break;
                case R.id.aftersaleactivity_record:  //记录
                    setTextData(2);
                    break;
            }
        }
    };

    /**
     * 设置选中控件颜色样式数据
     *
     * @param flag 0:申请、1：处理、2：记录
     */
    private void setTextData(int flag) {
        //申请
        TextViewUtils.setTextBold(aftersaleactivity_applyfor, flag == 0 ? true : false);
        TextViewUtils.setTextSize(aftersaleactivity_applyfor, flag == 0 ? true : false, 14, 12);
        TextViewUtils.setTextColor(aftersaleactivity_applyfor, flag == 0 ? true : false, "#4d4d4d", "#0f0f0f");
        //处理
        TextViewUtils.setTextBold(aftersaleactivity_inhand, flag == 1 ? true : false);
        TextViewUtils.setTextSize(aftersaleactivity_inhand, flag == 1 ? true : false, 14, 12);
        TextViewUtils.setTextColor(aftersaleactivity_inhand, flag == 1 ? true : false, "#4d4d4d", "#0f0f0f");
        //记录
        TextViewUtils.setTextBold(aftersaleactivity_record, flag == 2 ? true : false);
        TextViewUtils.setTextSize(aftersaleactivity_record, flag == 2 ? true : false, 14, 12);
        TextViewUtils.setTextColor(aftersaleactivity_record, flag == 2 ? true : false, "#4d4d4d", "#0f0f0f");
        setAdapter(mapAfterSale.get(flag));
    }

    //设置数据适配
    private void setAdapter(List<AfterSaleInfo> list) {
        if (afterSaleAdapter == null) {
            afterSaleAdapter = new AfterSaleAdapter(this, list);
            aftersaleactivity_listview.setAdapter(afterSaleAdapter);
        } else if (list == null || list.size() == 0)
            tost("暂无该类型数据！");
        afterSaleAdapter.setRefresh(list);
    }

    //获取数据
    private void getData() {
        mapAfterSale.clear();
        for (int i = 0; i < 20; i++) {
            AfterSaleInfo afterSaleInfo = new AfterSaleInfo();
            if (i % 3 == 0)
                afterSaleInfo.setType(0);
            else if (i % 3 == 1)
                afterSaleInfo.setType(1);
            else
                afterSaleInfo.setType(2);
            afterSaleInfo.setApplyForType((int) (Math.random() * 3) + 1);
            afterSaleInfo.setStatus((int) (Math.random() * 3) + 1);
            afterSaleInfo.setExplain("我只是个说明");
            afterSaleInfo.setName(i + "  测试");
            afterSaleInfo.setSpecification("750CL");
            afterSaleInfo.setMoney((int) (Math.random() * 200) + 1);
            afterSaleInfo.setNumber((int) (Math.random() * 20) + 1);
            afterSaleInfo.setTimeout(i % 5 == 0 ? true : false);
            afterSaleInfo.setImage("http://datong.crmeb.net/public/uploads/attach/2019/01/15/5c3dc0ef27068.jpg");
            if (mapAfterSale.get(afterSaleInfo.getType()) == null)
                mapAfterSale.put(afterSaleInfo.getType(), new ArrayList<AfterSaleInfo>());
            mapAfterSale.get(afterSaleInfo.getType()).add(afterSaleInfo);
        }
        setTextData(0);
    }
}
