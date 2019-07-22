package com.example.haoss.classification.entity;

import java.io.Serializable;
import java.util.List;

//分类信息数据
public class ClassifyInfo implements Serializable {
    //{"id":17,
    // "cate_name":"出行交通",
    // "child":[{"id":29,"cate_name":"111","pic":"http://api.haoshusi.com/uploads/attach/2019/05/20/5ce26e8f8c849.jpg"}]}
    private int id; //ID
    private String name = "";    //类型名称
    private List<ClassifyItenInfo> childList;   //各类信息联播

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

    public List<ClassifyItenInfo> getChildList() {
        return childList;
    }

    public void setChildList(List<ClassifyItenInfo> childList) {
        this.childList = childList;
    }
}
