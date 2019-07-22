package com.example.haoss.person.footprint;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.TextViewUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//足迹
public class FootprintActivity extends BaseActivity {

    TextView action_title_other;  //编辑
    RelativeLayout footprintactivity_linear;  //操作栏
    TextView footprintactivity_checkall, footprintactivity_delete;    // 全选、删除
    ListView listView;    //数据控件

    List<FootprintInfo> listFootprint;   //足迹数据
    FootprintAdapter footprintAdapter;  //足迹适配器
    boolean isOk = true;   //是否点击完成，==false：编辑
    private RefreshLayout refreshLayout;
    private int page = 1;
    private int status = 0;//是否为刷新
    private AppLibLication appLibLication;
    private String id[];
    private boolean isCheckAll = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_footprint);
        appLibLication = AppLibLication.getInstance();
        init();
    }

    private void init() {
        listFootprint = new ArrayList<>();
        this.getTitleView().setTitleText("我的足迹");

        footprintactivity_linear = findViewById(R.id.footprintactivity_linear);
        footprintactivity_checkall = findViewById(R.id.footprintactivity_checkall);
        footprintactivity_delete = findViewById(R.id.footprintactivity_delete);
        listView = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        action_title_other.setOnClickListener(onClickListener);
        footprintactivity_checkall.setOnClickListener(onClickListener);
        footprintactivity_delete.setOnClickListener(onClickListener);
        findViewById(R.id.footprintactivity_delele_all).setOnClickListener(onClickListener);

        footprintAdapter = new FootprintAdapter(this, listFootprint, isOk);
        listView.setAdapter(footprintAdapter);

        footprintAdapter.setListener(new FootprintAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int pos) {
                IntentUtils.startIntent(listFootprint.get(pos).getProduct_id(), FootprintActivity.this, GoodsDetailsActivity.class);
            }

            @Override
            public void setOnItemCheckListener(int pos) {
                FootprintInfo footprintIfo = listFootprint.get(pos);
                if (footprintIfo.isCheck()) {
                    footprintIfo.setCheck(false);
                } else {
                    footprintIfo.setCheck(true);
                }
                footprintAdapter.setRefresh(listFootprint, isOk);
            }
        });

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                status = 0;
                getPrintList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                status = 1;
                getPrintList();
            }
        });
        getPrintList();

    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_title_goback:  //返回
                    finish();
                    break;
                case R.id.action_title_other:   //编辑
                    isOk = !isOk;
                    setIsOk();
                    break;
                case R.id.footprintactivity_checkall:   //全选
                    isCheckAll = !isCheckAll;
                    allChoose();
                    break;
                case R.id.footprintactivity_delete: //删除
                    MyDialogTwoButton myDialogTwoButton1 = new MyDialogTwoButton(FootprintActivity.this, "您是否要删除选中数据？", new DialogOnClick() {
                        @Override
                        public void operate() {
                            delectPrint();
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                    myDialogTwoButton1.show();
                    break;
                case R.id.footprintactivity_delele_all: //清空
                    MyDialogTwoButton myDialogTwoButton2 = new MyDialogTwoButton(FootprintActivity.this, "是否要清空所有足迹？", new DialogOnClick() {
                        @Override
                        public void operate() {
                            deleteAll();
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                    myDialogTwoButton2.show();
                    break;
            }
        }
    };


    private void getPrintList() {
        String url = Netconfig.footPrint;
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", 20);
        map.put("token", appLibLication.getToken());
        httpHander.okHttpMapPost(this, url, map, 1);
    }

    private void delectPrint() {
        StringBuilder builder = new StringBuilder();
        for (FootprintInfo info : listFootprint) {
            if (info.isCheck()) {
                builder.append(info.getId()).append(",");
            }
        }

        id = (builder.toString().split(","));

        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
            String url = Netconfig.delFootPrint;
            Map<String, Object> map = new HashMap<>();
            map.put("id", builder.toString());
            map.put("token", appLibLication.getToken());
            httpHander.okHttpMapPost(this, url, map, 2);
        }
    }

    private void deleteAll() {
        String url = Netconfig.delAllFootPrint;
        Map<String, Object> map = new HashMap<>();
        map.put("token", appLibLication.getToken());
        httpHander.okHttpMapPost(this, url, map, 3);
    }


    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    analysisJson(msg.obj.toString());
                    break;
                case 2:
                    try {
                        Map<String, Object> map = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (map != null && Double.parseDouble(map.get("code") + "") == 200) {
                            for (int i = 0; i < id.length; i++) {
                                for (int j = 0; j < listFootprint.size(); j++) {
                                    if (listFootprint.get(j).getId() == Integer.parseInt(id[i])) {
                                        listFootprint.remove(j);
                                    }
                                }
                            }
                            footprintAdapter.setRefresh(listFootprint, false);
                            tost("删除成功");
                        } else {
                            tost(map.get("msg").toString());
                        }
                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        Map<String, Object> map = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (map != null && Double.parseDouble(map.get("code") + "") == 200) {
                            listFootprint.clear();
                            footprintAdapter.setRefresh(listFootprint, false);
                            tost("已全部删除");
                        } else {
                            tost(map.get("msg").toString());
                        }
                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
            }
        }
    };

    private void analysisJson(String json) {
        if (status == 0) {
            listFootprint.clear();
            refreshLayout.finishRefreshing();
        } else {
            refreshLayout.finishLoadmore();
        }

        List<Object> list = httpHander.jsonToList(json);
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = (Map<String, Object>) list.get(i);
            FootprintInfo info = new FootprintInfo();
            info.setImage(httpHander.getString(map, "image"));
            info.setAdd_time(httpHander.getString(map, "add_time"));
            info.setFicti((int) httpHander.getDouble(map, "ficti"));
            info.setId((int) httpHander.getDouble(map, "id"));
            info.setIs_del((int) httpHander.getDouble(map, "is_del"));
            info.setPrice(httpHander.getString(map, "price"));
            info.setProduct_id((int) httpHander.getDouble(map, "product_id"));
            info.setStore_name(httpHander.getString(map, "store_name"));

            listFootprint.add(info);
        }
        footprintAdapter.setRefresh(listFootprint, isOk);
    }


    //全选
    private void allChoose() {
        TextViewUtils.setImage(this, footprintactivity_checkall, isCheckAll ? R.mipmap.checked_true : R.mipmap.checked_false, 1);

        for (FootprintInfo footprintIfo : listFootprint) {
            footprintIfo.setCheck(isCheckAll);
        }
        footprintAdapter.setRefresh(listFootprint, isOk);
    }

    //设置是否显示选项框
    private void setIsOk() {
        action_title_other.setText(isOk ? "编辑" : "完成");
        footprintactivity_linear.setVisibility(isOk ? View.GONE : View.VISIBLE);    //显示完成则显示操作
        for (FootprintInfo footprintIfo : listFootprint) {
        }
        footprintAdapter.setRefresh(listFootprint, isOk);
    }

}
