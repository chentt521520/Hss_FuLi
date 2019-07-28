package com.example.applibrary.entity;

import java.io.Serializable;

//猜您喜欢
public class LikeInfo implements Serializable {
    //{"id":2,
    // "image":"http://datong.crmeb.net/public/uploads/attach/2019/01/15/5c3dbc27c69c7.jpg",
    // "store_name":"智能马桶盖 AI版",
    // "sort":0,
    // "is_benefit":1,
    // "is_show":1}
    private int id; //ID
    private String image = "";   //图片地址
    private String name = "";    //名称
    private int sort;   //排序
    private int isShow; //是否显示
    private String price; //价格

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
