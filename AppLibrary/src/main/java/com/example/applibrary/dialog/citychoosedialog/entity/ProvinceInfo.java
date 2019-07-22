package com.example.applibrary.dialog.citychoosedialog.entity;

import java.io.Serializable;
import java.util.List;

//省
public class ProvinceInfo implements Serializable {
    //"code": "110000","name": "北京市",
    ////                "cityList":
    private String code = "";    //区域码
    private String name = "";    //名称
    private List<CityInfo> cityList;    //城市列表

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityInfo> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityInfo> cityList) {
        this.cityList = cityList;
    }
}
