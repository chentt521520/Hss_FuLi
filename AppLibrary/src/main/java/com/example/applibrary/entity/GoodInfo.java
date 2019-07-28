package com.example.applibrary.entity;

import java.io.Serializable;

public class GoodInfo implements Serializable {


    /**
     * id : 60
     * store_type : 0
     * image : http://qiniu.haoshusi.com/images/b9150df9e76e2bacc35ef80ca2b84e2f.png
     * store_name : 《当日精选鲜货》埃及橙带包装5-10斤装
     * price : 35.49
     * stock : 2000
     * ficti : 1365
     */

    private int id;
    private int store_type;
    private String image;
    private String store_name;
    private String price;
    private int stock;
    private int ficti;
    private String ot_price;

    public String getOt_price() {
        return ot_price;
    }

    public void setOt_price(String ot_price) {
        this.ot_price = ot_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStore_type() {
        return store_type;
    }

    public void setStore_type(int store_type) {
        this.store_type = store_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getFicti() {
        return ficti;
    }

    public void setFicti(int ficti) {
        this.ficti = ficti;
    }
}
