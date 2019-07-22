package com.example.haoss.pay;

import java.io.Serializable;

//商品购买-订单确认页面-商品信息数据
public class GoodsBuyInfo implements Serializable {

    private int id; //商品ID
    private int product_id; //商品ID
    private String image = "";  //图片地址
    private String name = "";    //名称
    private String type = "";    //样式
    private double money;   //价格
    private String ot_price;   //价格
    private int number; //数量
    private String explain = ""; //说明
    private String uniqueId = "";    //
    private double postage; //邮费
    private String suk; //邮费
    private int ficti;//虚拟销量
    /**
     * stock : 186
     * sales : 0
     * price : 140.00
     * unique : 9daed7f2
     * cost : 0.00
     */

    private int stock;
    private int sales;
    private String price;
    private String unique;
    private String cost;


    public GoodsBuyInfo() {
    }

    public GoodsBuyInfo(int id, String image, String name, double money, int number, String uniqueId, double postage) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.money = money;
        this.number = number;
        this.uniqueId = uniqueId;
        this.postage = postage;
    }

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

    public int getFicti() {
        return ficti;
    }

    public void setFicti(int ficti) {
        this.ficti = ficti;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public double getPostage() {
        return postage;
    }

    public void setPostage(double postage) {
        this.postage = postage;
    }

    public void clear() {
        this.id = 0;
        this.image = null;
        this.name = null;
        this.type = null;
        this.money = 0;
        this.number = 0;
        this.explain = null;
        this.uniqueId = null;
        this.postage = 0d;
    }

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

    public String getOt_price() {
        return ot_price;
    }

    public void setOt_price(String ot_price) {
        this.ot_price = ot_price;
    }
}
