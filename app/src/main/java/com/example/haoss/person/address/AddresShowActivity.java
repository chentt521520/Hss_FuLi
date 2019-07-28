package com.example.haoss.person.address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.example.applibrary.entity.AddreInfo;
import com.example.applibrary.widget.CustomTitleView;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.google.gson.Gson;

/**
 * 地址显示
 */import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//收货地址
public class AddresShowActivity extends BaseActivity {

    //地址列表
    ListView listview;
    RefreshLayout refreshLayout;

    private Context mContext;

    List<AddreInfo> listAddreInfo = new ArrayList<>();//所有收货地址
    private ListViewAddressAdapter adapter;
    int flag;
    int index = 0;
    int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_address_list);
        mContext = this;
        initTitle();
        setView();
        getHttp();
    }

    private void initTitle() {
        flag = getIntent().getIntExtra(IntentUtils.intentActivityFlag, -1);

        CustomTitleView titleView = this.getTitleView();
        titleView.setTitleText(getResources().getString(R.string.shopping_address));
        if (flag == 1) {
            titleView.setRightImage(R.mipmap.dele_img);
        } else {
            titleView.setRightText(getResources().getString(R.string.btn_confirm));
        }
        titleView.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    delectData();
                } else {
                    Intent intent = new Intent();
                    AddreInfo addressInfo = listAddreInfo.get(index);
                    intent.putExtra("addressInfo", addressInfo);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //添加完地址后回调
            page = 1;
            getHttp();
        }
    }

    //控件view
    private void setView() {

        listview = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getHttp();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getHttp();
            }
        });
        findViewById(R.id.address_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddresShowActivity.this, AddressEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 2);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });   //添加收货地址

        adapter = new ListViewAddressAdapter(this, listAddreInfo);
        listview.setAdapter(adapter);

        adapter.setOnItemClickListener(new ListViewAddressAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int i) {

                index = i;
                Intent intent = new Intent(AddresShowActivity.this, AddressEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("addressInfo", listAddreInfo.get(i));
                bundle.putInt("flag", 1);
                bundle.putInt("index", i);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onRadioCheck(int i) {
                for (AddreInfo addreInfo : listAddreInfo) {
                    addreInfo.setChecked(false);
                }
                listAddreInfo.get(i).setChecked(true);
                adapter.notifyDataSetChanged();
                index = i;
            }
        });
    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.address_add:    //添加新地址
                    IntentUtils.startIntentForResult(2, mContext, AddressEditActivity.class);
                    break;
            }
        }
    };

    //删除地址
    private void delectData() {
        final MyDialogTwoButton myDialogTwoButton = new MyDialogTwoButton(this, "是否删除选中的收货地址？", new DialogOnClick() {
            @Override
            public void operate() {
                delectAdress(listAddreInfo.get(index).getId());
            }

            @Override
            public void cancel() {

            }
        });
        myDialogTwoButton.show();
    }

    /**
     * 地址列表获取
     */
    private void getHttp() {
        String url = Netconfig.userAddressList;
        Map<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendPage, page);
        map.put(ConfigHttpReqFields.sendLimit, 20);
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(this, url, map, 1);
    }

    //删除地址
    private void delectAdress(int id) {
        String url = Netconfig.delectAddress;
        Map<String, Object> map = new HashMap<>();
        map.put("addressId", id);
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(this, url, map, 2);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1: //列表
                    getData(msg.obj.toString());
                    break;
                case 2: //删除
                    try {
                        Map<String, Object> map = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (map == null)
                            tost("解析出错");
                        if (getDouble(map, "code") == 200) {
                            tost("已删除");
                            page = 1;
                            getHttp();
                        } else {
                            tost(getString(map, "msg"));
                        }
                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
            }
        }
    };

    //获取数据
    private void getData(String json) {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
        ArrayList<Object> list = httpHander.jsonToList(json);
        if (page == 1) {
            listAddreInfo.clear();
        }
        if (list == null || list.isEmpty()) {
            adapter.notifyDataSetChanged();
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = (Map<String, Object>) list.get(i);
            AddreInfo info = new AddreInfo();
            info.setId((int) httpHander.getDouble(map, "id"));
            info.setName(httpHander.getString(map, "real_name"));
            info.setPhone(httpHander.getString(map, "phone"));
            info.setProvince(httpHander.getString(map, "province"));
            info.setCity(httpHander.getString(map, "city"));
            info.setCounty(httpHander.getString(map, "district"));
            info.setAddre(httpHander.getString(map, "detail"));
            double isDefault = httpHander.getDouble(map, "is_default");
            info.setDefault(isDefault == 1);

            listAddreInfo.add(info);
        }
        adapter.notifyDataSetChanged();
    }
}
