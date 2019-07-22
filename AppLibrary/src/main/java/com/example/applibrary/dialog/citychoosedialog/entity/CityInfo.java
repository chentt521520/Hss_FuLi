package com.example.applibrary.dialog.citychoosedialog.entity;

import java.io.Serializable;
import java.util.List;

//市
public class CityInfo implements Serializable {
    //{"code": "110000","name": "北京市",
    ////                    "areaList"
    private String code = "";    //区域码
    private String name = "";    //名称
    private List<AreaInfo> areaList;    //县列表

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

    public List<AreaInfo> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaInfo> areaList) {
        this.areaList = areaList;
    }
}
