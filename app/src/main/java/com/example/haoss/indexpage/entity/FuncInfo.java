package com.example.haoss.indexpage.entity;

import java.io.Serializable;

//5选项
public class FuncInfo implements Serializable {
    //{"title":"优选生活",
    // "imgUrl":"http://qiniu.haoshusi.com/youxuan.png",
    // "jump":0,
    // "status":1}
    private String title = "";   //标题
    private String imgUrl = "";  //图片地址
    private int jump;   //跳转方式

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getJump() {
        return jump;
    }

    public void setJump(int jump) {
        this.jump = jump;
    }
}
