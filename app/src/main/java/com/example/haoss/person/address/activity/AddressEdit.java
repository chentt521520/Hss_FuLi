package com.example.haoss.person.address.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.widget.CustomTitleView;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.citychoosedialog.MyDialogCityChoose;
import com.example.applibrary.dialog.interfac.DialogCityChooseOnClick;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.person.address.entity.AddreInfo;

import java.util.HashMap;
import java.util.Map;

//收货地址编辑
public class AddressEdit extends BaseActivity {

    int flag;   //1:编辑，2：添加
    EditText addressedit_ed_shr, addressedit_ed_phone, addressedit_ed_address;    //收货人，电话，地址
    TextView addressedit_ed_site;   //地区选择
    ImageView addressedit_ed_defaultaddress;    //默认
    AddreInfo addreInfo;    //数据
    int index;  //传入数据的下标
    String province, city, county;    //省、市、县
    MyDialogCityChoose myDialogCityChoose;  //省市县选择对话框

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_address_edit);
        getIntentData();
        initTitle();
        init();
    }

    private void initTitle() {
        CustomTitleView titleView = this.getTitleView();
        if (flag == 1) {
            getTitleView().setTitleText("编辑收货地址");
        } else if (flag == 2) {
            getTitleView().setTitleText("添加收货地址");
        }
        titleView.setRightText("完成");
        titleView.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHttp();
            }
        });
    }

    //获取跳转数据
    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        addreInfo = (AddreInfo) bundle.getSerializable("addressInfo");
        index = bundle.getInt("index");
        flag = bundle.getInt("flag");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myDialogCityChoose != null)
            myDialogCityChoose.cancel();
        myDialogCityChoose = null;
    }

    //初始化
    private void init() {
        addressedit_ed_shr = findViewById(R.id.addressedit_ed_shr);
        addressedit_ed_phone = findViewById(R.id.addressedit_ed_phone);
        addressedit_ed_address = findViewById(R.id.addressedit_ed_address);
        addressedit_ed_site = findViewById(R.id.addressedit_ed_site);
        addressedit_ed_defaultaddress = findViewById(R.id.addressedit_ed_defaultaddress);

        addressedit_ed_site.setOnClickListener(onClickListener);
        addressedit_ed_defaultaddress.setOnClickListener(onClickListener);
        if (flag == 1) {//编辑地址
            addressedit_ed_shr.setText(addreInfo.getName());
            addressedit_ed_phone.setText(addreInfo.getPhone());
            province = addreInfo.getProvince();
            city = addreInfo.getCity();
            county = addreInfo.getCounty();
            addressedit_ed_site.setText(province + "  " + city + "  " + county);
            addressedit_ed_address.setText(addreInfo.getAddre());
            if (addreInfo.isDefault())
                addressedit_ed_defaultaddress.setImageResource(R.mipmap.defaultaddress_on);
            else
                addressedit_ed_defaultaddress.setImageResource(R.mipmap.defaultaddress_off);
        } else if (flag == 2) {//添加新地址
            addreInfo = new AddreInfo();
        }

    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addressedit_ed_site:  //地区选择
                    if (myDialogCityChoose == null)
                        myDialogCityChoose = new MyDialogCityChoose(AddressEdit.this, "设置地区",
                                addreInfo.getProvince(), addreInfo.getCity(), addreInfo.getCounty(), null,
                                new DialogCityChooseOnClick() {
                                    @Override
                                    public void setData(String p, String c, String area) {
                                        province = p;
                                        city = c;
                                        county = area;
                                        addressedit_ed_site.setText(province + "  " + city + "  " + county);
                                    }
                                });
                    else
                        myDialogCityChoose.setUpdata(addreInfo.getProvince(), addreInfo.getCity(), addreInfo.getCounty());
                    myDialogCityChoose.show();
                    break;
                case R.id.addressedit_ed_defaultaddress:    //设置默认
                    if (addreInfo.isDefault()) {
                        addreInfo.setDefault(false);
                        addressedit_ed_defaultaddress.setImageResource(R.mipmap.defaultaddress_off);
                    } else {
                        addreInfo.setDefault(true);
                        addressedit_ed_defaultaddress.setImageResource(R.mipmap.defaultaddress_on);
                    }
                    break;
            }
        }
    };

    //请求b==true：修改
    private void getHttp() {
        String name = addressedit_ed_shr.getText().toString();
        String phone = addressedit_ed_phone.getText().toString();
        String site = addressedit_ed_address.getText().toString();
        if (TextUtils.isEmpty(name)) {
            tost("请输入收件人名称！");
            return;
        }
        if (!StringUtils.validatePhoneNumber(phone)) {
            tost("请输入正确的手机号码！");
            return;
        }
        if (TextUtils.isEmpty(site)) {
            tost("请输入详细收货地址！");
            return;
        }
        if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city) || TextUtils.isEmpty(county)) {
            tost("请选择所属城市！");
            return;
        }
        int isDefault = 0;
        if (addreInfo.isDefault())
            isDefault = 1;
        String url = Netconfig.addAndEditAddress;
        Map<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendRealName, name);
        map.put(ConfigHttpReqFields.sendPhone, phone);
        map.put(ConfigHttpReqFields.sendDetail, site);
        map.put(ConfigHttpReqFields.sendIsDefault, isDefault);
        if (flag == 1) {  //编辑
            map.put(ConfigHttpReqFields.sendId, addreInfo.getId());
        }
        Map<String, String> mapString = new HashMap<>();
        mapString.put(ConfigHttpReqFields.sendProvince, province);
        mapString.put(ConfigHttpReqFields.sendCity, city);
        mapString.put(ConfigHttpReqFields.sendDistrict, county);
        String address = StringUtils.mapToJson(mapString);
        map.put(ConfigHttpReqFields.sendAddress, address);
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(AddressEdit.this, url, map, 1);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            Map<String, Object> map = jsontoMap(msg.obj.toString());
            if (map != null) {
                setResult(RESULT_OK);
                finish();
            }
        }
    };
}
