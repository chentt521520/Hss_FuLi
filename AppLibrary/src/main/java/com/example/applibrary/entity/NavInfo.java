package com.example.applibrary.entity;

import android.text.TextUtils;

import java.io.Serializable;

public class NavInfo implements Serializable {

    private int id; //id
    private String imageUrl = "";    //图片地址
    private String name = "";    //名称
    private String money;   //金额
    private int number; //人数
    private double sort;    //排序
    private String descript;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return TextUtils.isEmpty(name) ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return TextUtils.isEmpty(money) ? "" : money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getSort() {
        return sort;
    }

    public void setSort(double sort) {
        this.sort = sort;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
}

