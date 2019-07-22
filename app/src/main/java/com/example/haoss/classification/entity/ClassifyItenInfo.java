package com.example.haoss.classification.entity;

import java.io.Serializable;

//分类item 数据
public class ClassifyItenInfo implements Serializable {
    //{"id":29,
    // "cate_name":"111",
    // "pic":"http://api.haoshusi.com/uploads/attach/2019/05/20/5ce26e8f8c849.jpg"}
    private int id; //ID
    private String name = "";    //名称
    private String pic = ""; //图片地址

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
