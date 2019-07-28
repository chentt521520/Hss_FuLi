package com.example.applibrary.entity;

import java.io.Serializable;

public class GoodList implements Serializable {

    /**
     * id : 45
     * store_name : 伊利 味可滋香蕉牛奶 240ml*12盒/箱 常温果味香蕉牛奶饮品
     * cate_id : 39
     * image : http://qiniu.haoshusi.com/images/6adba13cae9ce1bed9bb67140cad01bf.png
     * sales : 665
     * price : 49.00
     * stock : 1000
     * percentage : 1000
     */

    /**
     * {"id":1506,
     * "store_name":"Givenchy/纪梵希 高级定制系列 小羊皮唇膏口红 3.4G",
     * "cate_id":"128",
     * "image":"http://py.haoshusi.com/python/2ba728a0a5fc78c60039c63e876142f14761.jpg",
     * "sales":2054,
     * "price":"230.56",
     * "stock":137,
     * "is_show":1,
     * "is_del":0,
     * "store_type":0}
     */

    private int id;
    private String store_name;
    private String cate_id;
    private String image;
    private String sales;
    private String price;
    private int stock;
    private int percentage;
    private int is_show;
    private int is_del;
    private int store_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
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

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public int getStore_type() {
        return store_type;
    }

    public void setStore_type(int store_type) {
        this.store_type = store_type;
    }
}

