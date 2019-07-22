package com.example.applibrary.dialog.citychoosedialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.R;
import com.example.applibrary.dialog.citychoosedialog.adapter.AreaAdapter;
import com.example.applibrary.dialog.citychoosedialog.adapter.CityAdapter;
import com.example.applibrary.dialog.citychoosedialog.adapter.ProvinceAdapter;
import com.example.applibrary.dialog.citychoosedialog.entity.AreaInfo;
import com.example.applibrary.dialog.citychoosedialog.entity.CityInfo;
import com.example.applibrary.dialog.citychoosedialog.entity.ProvinceInfo;
import com.example.applibrary.dialog.interfac.DialogCityChooseOnClick;
import com.example.applibrary.utils.WindowWHUtils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//城市选中对话框
public class MyDialogCityChoose extends Dialog {
    public MyDialogCityChoose(Context context) {
        super(context);
    }

    Context context;
    String title;   //标题
    String json;    //json字符串数据,为空时是城市json
    DialogCityChooseOnClick dialogCityChooseOnClick;    //传值接口
  /*  public MyDialogCityChoose(Context context, String title, String json, DialogCityChooseOnClick dialogCityChooseOnClick) {
        super(context,R.style.dialogd);
        this.context = context;
        this.title = title;
        if(json == null)
            json = getCityData();
        this.json = json;
        this.dialogCityChooseOnClick = dialogCityChooseOnClick;
    }*/

    String chooseProvince, chooseCity, chooseCounty;  //已选中的省市县
    ProvinceAdapter provinceAdapter;    //省适配
    CityAdapter cityAdapter;    //市适配
    AreaAdapter areaAdapter;    //县适配
    List<ProvinceInfo> listProvince = new ArrayList<>();    //首页省数据
    int chooseP = -1, chooseC = -1, chooseA = -1;    //选中的省市县下标值，默认是-1

    public MyDialogCityChoose(Context context, String title,
                              String chooseProvince, String chooseCity, String chooseCounty, String json
            , DialogCityChooseOnClick dialogCityChooseOnClick) {
        super(context, R.style.ActionSheetDialogStyle2);
        this.context = context;
        this.title = title;
        setText(chooseProvince, chooseCity, chooseCounty);
        if (json == null)
            json = getCityData();
        this.json = json;
        this.dialogCityChooseOnClick = dialogCityChooseOnClick;
    }

    //更新数据
    public void setUpdata(String chooseProvince, String chooseCity, String chooseCounty) {
        setText(chooseProvince, chooseCity, chooseCounty);
        chooseCount();
    }

    //设置文字
    private void setText(String chooseProvince, String chooseCity, String chooseCounty) {
        this.chooseProvince = (chooseProvince == null ? "请选择" : (chooseProvince.equals("") ? "请选择" : chooseProvince));
        this.chooseCity = (chooseCity == null ? "请选择" : (chooseCity.equals("") ? "请选择" : chooseCity));
        this.chooseCounty = (chooseCounty == null ? "请选择" : (chooseCounty.equals("") ? "请选择" : chooseCounty));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_citychoose);
        init();
//        WindowWHUtils.setaDialogDisplay(context,this,1,0.5);
        WindowWHUtils.setDialogDisplay(context, this, Gravity.BOTTOM, 1, 0.5);
    }

    TextView citychoose_title;  //标题
    TextView citychoose_province, citychoose_city, citychoose_county; //省市县
    TextView citychoose_line_province, citychoose_line_city, citychoose_line_county;  //区域线
    ListView citychoose_provincelist, citychoose_citylist, citychoose_countylist;   //省市县数据列表

    private void init() {
        citychoose_title = findViewById(R.id.citychoose_title);
        //省
        citychoose_province = findViewById(R.id.citychoose_province);
        citychoose_line_province = findViewById(R.id.citychoose_line_province);
        //市
        citychoose_city = findViewById(R.id.citychoose_city);
        citychoose_line_city = findViewById(R.id.citychoose_line_city);
        //县
        citychoose_county = findViewById(R.id.citychoose_county);
        citychoose_line_county = findViewById(R.id.citychoose_line_county);
        //数据列表
        citychoose_provincelist = findViewById(R.id.citychoose_provincelist);
        citychoose_citylist = findViewById(R.id.citychoose_citylist);
        citychoose_countylist = findViewById(R.id.citychoose_countylist);

        //设置数据
        citychoose_title.setText(title);

        //监听
        citychoose_provincelist.setOnItemClickListener(provinceItemClickListener);
        citychoose_citylist.setOnItemClickListener(cityItemClickListener);
        citychoose_countylist.setOnItemClickListener(areaItemClickListener);
        citychoose_province.setOnClickListener(onClickListener);
        citychoose_city.setOnClickListener(onClickListener);
//        citychoose_county.setOnClickListener(onClickListener);

        analysisJson();
        chooseCount();
    }

    //获取城市json数据
    private String getCityData() {
        String s = "address.js";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = ((Activity) context).getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(s)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        return stringBuilder.toString().replace("var cityData=", "").replace(";", "");
    }

    //隐藏或显示线、列表、及执行适配刷新
    private void showAndHide() {
        citychoose_line_province.setVisibility(View.INVISIBLE);
        citychoose_line_city.setVisibility(View.INVISIBLE);
        citychoose_line_county.setVisibility(View.INVISIBLE);
        citychoose_province.setVisibility(View.INVISIBLE);
        citychoose_city.setVisibility(View.INVISIBLE);
        citychoose_county.setVisibility(View.INVISIBLE);
        citychoose_provincelist.setVisibility(View.GONE);
        citychoose_citylist.setVisibility(View.GONE);
        citychoose_countylist.setVisibility(View.GONE);
        if (!chooseCounty.equals("请选择") || !chooseCity.equals("请选择")) {   //选择县
            citychoose_province.setTextColor(Color.parseColor("#0F0F0F"));
            citychoose_city.setTextColor(Color.parseColor("#0F0F0F"));
            citychoose_county.setTextColor(Color.parseColor("#C22222"));
            citychoose_province.setVisibility(View.VISIBLE);
            citychoose_city.setVisibility(View.VISIBLE);
            citychoose_county.setVisibility(View.VISIBLE);
            citychoose_line_county.setVisibility(View.VISIBLE);
            citychoose_countylist.setVisibility(View.VISIBLE);
            adapterArea();
        } else if (!chooseProvince.equals("请选择")) {    //选择市
            citychoose_province.setTextColor(Color.parseColor("#0F0F0F"));
            citychoose_city.setTextColor(Color.parseColor("#C22222"));
            citychoose_province.setVisibility(View.VISIBLE);
            citychoose_city.setVisibility(View.VISIBLE);
            citychoose_line_city.setVisibility(View.VISIBLE);
            citychoose_citylist.setVisibility(View.VISIBLE);
            adapterCity();
        } else {  //选择省
            citychoose_province.setTextColor(Color.parseColor("#C22222"));
            citychoose_province.setVisibility(View.VISIBLE);
            citychoose_line_province.setVisibility(View.VISIBLE);
            citychoose_provincelist.setVisibility(View.VISIBLE);
            adapterProvince();
        }
        citychoose_province.setText(chooseProvince);
        citychoose_city.setText(chooseCity);
        citychoose_county.setText(chooseCounty);
    }

    //解析分装数据
    private void analysisJson() {
//        [{"code": "110000","name": "北京市",
//                "cityList": [{"code": "110000","name": "北京市",
//                    "areaList": [{"code": "110101","name": "东城区"},
        if (json == null)
            return;
        List<Object> jsonProvince = new Gson().fromJson(json, ArrayList.class);
        //解省
        for (int i = 0; i < jsonProvince.size(); i++) {
            Map<String, Object> mapProvince = (Map<String, Object>) jsonProvince.get(i);
            String codeProvince = (String) mapProvince.get("code");
            String nameProvince = (String) mapProvince.get("name");
            ProvinceInfo provinceInfo = new ProvinceInfo();
            provinceInfo.setCode(codeProvince);
            provinceInfo.setName(nameProvince);
            List<CityInfo> listCity = new ArrayList<>();
            //解市
            List<Object> jsonCity = (List<Object>) mapProvince.get("cityList");
            for (int j = 0; j < jsonCity.size(); j++) {
                Map<String, Object> mapCity = (Map<String, Object>) jsonCity.get(j);
                String codeCity = (String) mapCity.get("code");
                String nameCity = (String) mapCity.get("name");
                CityInfo cityInfo = new CityInfo();
                cityInfo.setCode(codeCity);
                cityInfo.setName(nameCity);
                List<AreaInfo> listArea = new ArrayList<>();
                //解县
                List<Object> jsonArea = (List<Object>) mapCity.get("areaList");
                for (int k = 0; k < jsonArea.size(); k++) {
                    Map<String, Object> mapArea = (Map<String, Object>) jsonArea.get(k);
                    String codeArea = (String) mapArea.get("code");
                    String nameArea = (String) mapArea.get("name");
                    AreaInfo areaInfo = new AreaInfo();
                    areaInfo.setCode(codeArea);
                    areaInfo.setName(nameArea);
                    listArea.add(areaInfo); //装区域
                }
                cityInfo.setAreaList(listArea);
                listCity.add(cityInfo); //张城市
            }
            provinceInfo.setCityList(listCity);
            listProvince.add(provinceInfo); //张省
        }
    }

    //省数据适配
    private void adapterProvince() {
        if (provinceAdapter == null) {
            provinceAdapter = new ProvinceAdapter(context, listProvince);
            citychoose_provincelist.setAdapter(provinceAdapter);
        }
        provinceAdapter.setChoose(chooseP);
    }

    //市数据适配
    private void adapterCity() {
        List<CityInfo> list = listProvince.get(chooseP).getCityList();
        if (cityAdapter == null) {
            cityAdapter = new CityAdapter(context, list);
            citychoose_citylist.setAdapter(cityAdapter);
        }
        cityAdapter.setChoose(list, chooseC);
    }

    //县数据适配
    private void adapterArea() {
        List<AreaInfo> list = listProvince.get(chooseP).getCityList().get(chooseC).getAreaList();
        if (areaAdapter == null) {
            areaAdapter = new AreaAdapter(context, list);
            citychoose_countylist.setAdapter(areaAdapter);
        }
        areaAdapter.setChoose(list, chooseA);
    }

    //省监听
    AdapterView.OnItemClickListener provinceItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            chooseP = position;
            chooseProvince = listProvince.get(chooseP).getName();
            showAndHide();
        }
    };

    //市监听
    AdapterView.OnItemClickListener cityItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            chooseC = position;
            chooseCity = listProvince.get(chooseP).getCityList().get(chooseC).getName();
            showAndHide();
        }
    };

    //区监听
    AdapterView.OnItemClickListener areaItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            chooseA = position;
            chooseCounty = listProvince.get(chooseP).getCityList().get(chooseC).getAreaList().get(chooseA).getName();
            showAndHide();
            dialogCityChooseOnClick.setData(chooseProvince, chooseCity, chooseCounty);
            dismiss();
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.citychoose_province) { //点击省还原市和县
                chooseProvince = "请选择";
                chooseCity = "请选择";
                chooseCounty = "请选择";
                chooseP = -1;
                chooseC = -1;
                chooseA = -1;
            } else if (v.getId() == R.id.citychoose_city) {   //点击市还原县
                chooseCity = "请选择";
                chooseCounty = "请选择";
                chooseC = -1;
                chooseA = -1;
            }
            showAndHide();
        }
    };


    //已选中计算
    private void chooseCount() {
        for (int i = 0; i < listProvince.size(); i++) {
            ProvinceInfo provinceInfo = listProvince.get(i);
            if (provinceInfo.getName().equals(chooseProvince)) {
                chooseP = i;
                for (int j = 0; j < provinceInfo.getCityList().size(); j++) {
                    CityInfo cityInfo = provinceInfo.getCityList().get(j);
                    if (cityInfo.getName().equals(chooseCity)) {
                        chooseC = j;
                        for (int k = 0; k < cityInfo.getAreaList().size(); k++) {
                            AreaInfo areaInfo = cityInfo.getAreaList().get(k);
                            if (areaInfo.getName().equals(chooseCounty)) {
                                chooseA = k;
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        showAndHide();
    }
}
