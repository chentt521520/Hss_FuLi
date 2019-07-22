package com.example.haoss.integralshop.entity;

import java.io.Serializable;

//积分列表信息
public class IntegralInfo implements Serializable {

    private int id; //id
    private int merId;  //
    private String image;   //图片

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMerId() {
        return merId;
    }

    public void setMerId(int merId) {
        this.merId = merId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
