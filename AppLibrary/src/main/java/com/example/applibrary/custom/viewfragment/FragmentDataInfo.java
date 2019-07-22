package com.example.applibrary.custom.viewfragment;

//传入fragment的数据
public class FragmentDataInfo {

    private int id; //id
    private String imageUrl = ""; //图片URL
    private String skipUrl = ""; //跳至url
    private double order;  //排序

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

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public double getOrder() {
        return order;
    }

    public void setOrder(double order) {
        this.order = order;
    }
}
