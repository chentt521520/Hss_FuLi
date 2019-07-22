package com.example.haoss.goods.search;

import java.io.Serializable;

//搜索商品数据
public class GoodsSearchInfo implements Serializable {

    private int id; //商品id
    private String name = "";    //商品名称
    private String cateId = ""; //分类ID
    private String image = "";   //图片地址
    private int saies;   //销量
    private int stock;   //库存
    private double price;   //价格
    private double vipPrice;    //会员价格
    private int store_type;    //会员价格

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

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSaies() {
        return saies;
    }

    public void setSaies(int saies) {
        this.saies = saies;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public int getStore_type() {
        return store_type;
    }

    public void setStore_type(int store_type) {
        this.store_type = store_type;
    }
}
