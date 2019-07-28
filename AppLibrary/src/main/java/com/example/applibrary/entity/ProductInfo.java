package com.example.applibrary.entity;

import java.util.List;

public class ProductInfo {

    /**
     * id : 749
     * image : http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg
     * slider_image : ["http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","http://py.haoshusi.com/python/a7bca44bf19b2f7f6690152356f20c1a1049.jpg","http://py.haoshusi.com/python/a049f4367d23c33f4625ea4cbfb827027111.jpg"]
     * price : 171.00
     * ot_price : 222.30
     * vip_price : 0.00
     * postage : 10.00
     * mer_id : 0
     * give_integral : 0.00
     * cate_id : 297
     * sales : 0
     * stock : 790
     * store_name : 澳洲爱他美Aptamil金装婴幼儿配方奶粉3段 900g 1-2岁适用（澳爱）
     * store_info :
     * unit_name :
     * is_show : 1
     * is_del : 0
     * is_postage : 0
     * cost : 0.00
     * attrInfo : {"product_id":749,"suk":"1罐装,2020年6月","stock":100,"sales":0,"price":"180.00","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"f771e304","cost":"0.00"}
     */

    private int id;
    private String image;
    private String price;
    private String ot_price;
    private String vip_price;
    private String postage;
    private int mer_id;
    private String give_integral;
    private String cate_id;
    private int sales;
    private int stock;
    private String store_name;
    private String store_info;
    private String unit_name;
    private int is_show;
    private int is_del;
    private int is_postage;
    private String cost;
    private AttrInfo attrInfo;
    private List<String> slider_image;

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

    public String getVip_price() {
        return vip_price;
    }

    public void setVip_price(String vip_price) {
        this.vip_price = vip_price;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public int getMer_id() {
        return mer_id;
    }

    public void setMer_id(int mer_id) {
        this.mer_id = mer_id;
    }

    public String getGive_integral() {
        return give_integral;
    }

    public void setGive_integral(String give_integral) {
        this.give_integral = give_integral;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
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

    public int getIs_postage() {
        return is_postage;
    }

    public void setIs_postage(int is_postage) {
        this.is_postage = is_postage;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public AttrInfo getAttrInfo() {
        return attrInfo;
    }

    public void setAttrInfo(AttrInfo attrInfo) {
        this.attrInfo = attrInfo;
    }

    public List<String> getSlider_image() {
        return slider_image;
    }

    public void setSlider_image(List<String> slider_image) {
        this.slider_image = slider_image;
    }
}
