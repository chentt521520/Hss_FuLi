package com.example.haoss.goods.details.entity;

import java.io.Serializable;

//商品规格类型
public class GoodsType implements Serializable {

    /**
     * product_id : 60
     * suk : 5斤装
     * stock : 996
     * sales : 4
     * price : 35.49
     * image : http://qiniu.haoshusi.com/images/b9150df9e76e2bacc35ef80ca2b84e2f.png
     * unique : 74c726e8
     * cost : 0.00
     */

    private int product_id;
    private String suk;
    private int stock;
    private int sales;
    private String price;
    private String image;
    private String unique;
    private String cost;
    private boolean isChecked = false;  //是否选中

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getSuk() {
        return suk;
    }

    public void setSuk(String suk) {
        this.suk = suk;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
