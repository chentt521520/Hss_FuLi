package com.example.haoss.person.collect.entity;

public class CollectionInfo {

    /**
     * store_name : 月饼
     * store_info :
     * image : http://qiniu.haoshusi.com/images/5f084201907051856395970.jpg
     * is_show : 0 失效；1 未失效
     * is_del : 1
     * store_type : 0
     * ficti : 0
     * uid : 37
     * product_id : 2719
     * type : collect
     * category : product
     * add_time : 1562761800
     */

    private String store_name;
    private String store_info;
    private String image;
    private int is_show;
    private int is_del;
    private int store_type;
    private int ficti;
    private int uid;
    private int product_id;
    private String type;
    private String category;
    private int add_time;
    private String price;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getFicti() {
        return ficti;
    }

    public void setFicti(int ficti) {
        this.ficti = ficti;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
