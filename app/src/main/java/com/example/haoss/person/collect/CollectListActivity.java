package com.example.haoss.person.collect;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.ViewUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.person.collect.adapter.CollectAdapter;
import com.example.haoss.person.collect.entity.CollectionInfo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//收藏界面
public class CollectListActivity extends BaseActivity {

    private List<CollectionInfo> collectList = new ArrayList<>();   //状态列表
    private CollectAdapter adapter;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_collect);
        init();
    }

    //初始化
    private void init() {
        this.getTitleView().setTitleText("我的收藏");

        ListView collectactivity_listview = findViewById(R.id.collectactivity_listview);

        adapter = new CollectAdapter(this, collectList);
        collectactivity_listview.setAdapter(adapter);

        collectactivity_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentUtils.startIntent(collectList.get(position).getProduct_id(), CollectListActivity.this, GoodsDetailsActivity.class);
            }
        });

        collectactivity_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialogExitLogin(collectList.get(position).getProduct_id());
                index = position;
                return true;
            }
        });

        getCollectList();
    }

    //退出登录对话框
    private void dialogExitLogin(final int id) {
        MyDialogTwoButton myDialogExitLogin = new MyDialogTwoButton(this, "确定删除该商品？", new DialogOnClick() {
            @Override
            public void operate() {
                String url = Netconfig.cancleShoppingCollect;
                Map<String, Object> map = new HashMap<>();
                map.put(ConfigHttpReqFields.sendProductId, id);
                map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
                httpHander.okHttpMapPost(CollectListActivity.this, url, map, 2);
            }

            @Override
            public void cancel() {

            }
        });
        myDialogExitLogin.show();
    }

    //获取收藏列表
    private void getCollectList() {
        String url = Netconfig.collectShoppingList;
        Map<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendPage, "1");
        map.put(ConfigHttpReqFields.sendLimit, "20");
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(this, url, map, 1);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    analysis(msg.obj.toString());
                    break;
                case 2:
                    cancelCollect(msg.obj.toString());
                    break;
            }
        }
    };

    //解析
    private void analysis(String msg) {
        List arrayList = httpHander.jsonToList(msg);
        if (arrayList == null) {
            return;
        }
/**
 * "store_name": "月饼",
 * 		"store_info": "",
 * 		"image": "http://qiniu.haoshusi.com/images/5f084201907051856395970.jpg",
 * 		"is_show": 0,
 * 		"is_del": 1,
 * 		"store_type": 0,
 * 		"ficti": 0,
 * 		"uid": 37,
 * 		"product_id": 2719,
 * 		"type": "collect",
 * 		"category": "product",
 * 		"add_time": 1562761800
 */
        if (!arrayList.isEmpty()) {
            for (int i = 0; i < arrayList.size(); i++) {
                Map<String, Object> map = (Map<String, Object>) arrayList.get(i);
                Map<String, String> mapString = httpHander.getStringMap(map, "store_name", "image", "price");
                Map<String, Integer> mapInteger = httpHander.getIntegerMap(map, "product_id", "is_show", "ficti", "is_show");

                CollectionInfo collectInfo = new CollectionInfo();
                collectInfo.setProduct_id(mapInteger.get("product_id"));
                collectInfo.setStore_name(mapString.get("store_name"));
                collectInfo.setFicti(mapInteger.get("ficti"));
                collectInfo.setIs_show(mapInteger.get("is_show"));
                collectInfo.setPrice(mapString.get("price"));
                collectInfo.setImage(mapString.get("image"));
                collectList.add(collectInfo);
            }
        }
        adapter.setRefresh(collectList);
    }

    private void cancelCollect(String json) {
        Map<String, Object> map = new Gson().fromJson(json, HashMap.class);
        if (map == null) {
            tost("取消收藏失败");
            return;
        }
        if (Double.parseDouble(map.get("code") + "") != 200) {
            tost(httpHander.getString(map, "msg"));
        } else {
            tost("取消收藏成功");
            collectList.remove(index);
        }
        adapter.setRefresh(collectList);
    }

}
