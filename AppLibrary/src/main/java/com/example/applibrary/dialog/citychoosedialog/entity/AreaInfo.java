package com.example.applibrary.dialog.citychoosedialog.entity;

import java.io.Serializable;

//地区/县
public class AreaInfo implements Serializable {
    //}"code": "110101",
    //            "name": "东城区"

    private String code = "";    //区域码
    private String name = "";    //名称

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
}