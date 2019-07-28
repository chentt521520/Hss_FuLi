package com.example.applibrary.entity;

public class Recommond {

    /**
     * id : 3127
     * image : http://qiniu.haoshusi.com/images/1a5b7201907111655178463.png
     * store_name : 100元电子购物券
     * store_info :
     * sort : 99
     * is_benefit : 0
     * is_show : 1
     * price : 98.00
     * ot_price : 127.40
     * ficti : 2323
     * is_index : 0
     */

    private int id;
    private String image;
    private String store_name;
    private String price;
    private int ficti;

    /**
     * 暂不需要
     */
    private String store_info;
    private int sort;
    private int is_benefit;
    private int is_show;
    private String ot_price;
    private int is_index;

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

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_info() {
        return store_info;
    }

    public void setStore_info(String store_info) {
        this.store_info = store_info;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIs_benefit() {
        return is_benefit;
    }

    public void setIs_benefit(int is_benefit) {
        this.is_benefit = is_benefit;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOt_price() {
        return ot_price;
    }

    public void setOt_price(String ot_price) {
        this.ot_price = ot_price;
    }

    public int getFicti() {
        return ficti;
    }

    public void setFicti(int ficti) {
        this.ficti = ficti;
    }

    public int getIs_index() {
        return is_index;
    }

    public void setIs_index(int is_index) {
        this.is_index = is_index;
    }
}
