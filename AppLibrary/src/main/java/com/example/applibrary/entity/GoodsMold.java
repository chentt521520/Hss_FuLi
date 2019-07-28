package com.example.applibrary.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//商品类型 尺寸、颜色
public class GoodsMold {
    private String moldName;    //类型名称
    private List<String> moldValues = new ArrayList<>();    //类型值
    private List<Map<String, Boolean>> moldValue = new ArrayList<>();   //值型

    public String getMoldName() {
        return moldName;
    }

    public void setMoldName(String moldName) {
        this.moldName = moldName;
    }

    public List<String> getMoldValues() {
        return moldValues;
    }

    public void setMoldValues(List<String> moldValues) {
        this.moldValues = moldValues;
    }

    public List<Map<String, Boolean>> getMoldValue() {
        return moldValue;
    }

    public void setMoldValue(List<Map<String, Boolean>> moldValue) {
        this.moldValue = moldValue;
    }

    //加入
    public void addMoldValues(String s) {
        if (this.moldValues == null)
            this.moldValues = new ArrayList<>();
        this.moldValues.add(s);
    }
}
