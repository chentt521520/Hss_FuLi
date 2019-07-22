package com.example.haoss.integralshop.entity;

import java.io.Serializable;

//5功能选项信息
public class FuncInfo implements Serializable {
    private int image;  //图片id
    private String imageUrl;   //图片
    private String title;   //文字

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
