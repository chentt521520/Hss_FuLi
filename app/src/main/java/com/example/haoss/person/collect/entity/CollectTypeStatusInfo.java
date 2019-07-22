package com.example.haoss.person.collect.entity;

import java.io.Serializable;

//收藏类型和状态数据，用于类型和状态选择
public class CollectTypeStatusInfo implements Serializable {
    private int type;   //类型标记
    private String name = "";    //名称
    private int number; //数量
    private boolean isChecked;  //是否选中

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
