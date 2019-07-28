package com.example.applibrary.entity;

import java.io.Serializable;

//购物车商品信息
public class ShoppingCartInfo implements Serializable {

    /**
     * id : 286
     * uid : 37
     * type : product
     * product_id : 1836
     * product_attr_unique : 01b67059
     * cart_num : 1
     * add_time : 1564047388
     * is_pay : 0
     * is_del : 0
     * is_new : 1
     * combination_id : 0
     * seckill_id : 0
     * bargain_id : 0
     * admin_id : 9
     * productInfo : {"id":1836,"image":"http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","slider_image":["http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","http://py.haoshusi.com/python/3c76e483518218037743f95e9ea3e54b2880.jpg","http://qiniu.haoshusi.com/images/aa4fd201907251640369227.png"],"price":"306.00","ot_price":"397.80","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"144","sales":0,"stock":50,"store_name":"【新品推荐】【香港直邮】YSL/圣罗兰2018小金条哑光方管细管唇膏口红 16#浅豆沙粉色","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1836,"suk":"16#","stock":50,"sales":0,"price":"306.00","image":"http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","unique":"01b67059","cost":"0.00"}}
     * truePrice : 306
     * vip_truePrice : 0
     * trueStock : 50
     * costPrice : 0.00
     * unique : ecebb82657a4cacb4e5a66a6b5cf094b
     * is_reply : 0
     */


    /**
     * id : 449
     * uid : 37
     * type : product
     * product_id : 60
     * product_attr_unique : 74c726e8
     * cart_num : 1
     * add_time : 1562554979
     * is_pay : 0
     * is_del : 0
     * is_new : 0
     * combination_id : 0
     * seckill_id : 0
     * bargain_id : 0
     * admin_id : 0
     * "truePrice": 35.49,
     * 			"vip_truePrice": 0,
     * 			"trueStock": 996,
     * 			"costPrice": "0.00"
     */

    private int id;
    private int uid;
    private String type;
    private int product_id;
    private String product_attr_unique;
    private int cart_num;
    private long add_time;
    private int is_pay;
    private int is_del;
    private int is_new;
    private int combination_id;
    private int seckill_id;
    private int bargain_id;
    private int admin_id;
    private double truePrice;
    private double vip_truePrice;
    private int trueStock;
    private String costPrice;
    private ProductInfo productInfo;
    private String unique;
    private int is_reply;

    private boolean isCheck;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_attr_unique() {
        return product_attr_unique;
    }

    public void setProduct_attr_unique(String product_attr_unique) {
        this.product_attr_unique = product_attr_unique;
    }

    public int getCart_num() {
        return cart_num;
    }

    public void setCart_num(int cart_num) {
        this.cart_num = cart_num;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public int getIs_new() {
        return is_new;
    }

    public void setIs_new(int is_new) {
        this.is_new = is_new;
    }

    public int getCombination_id() {
        return combination_id;
    }

    public void setCombination_id(int combination_id) {
        this.combination_id = combination_id;
    }

    public int getSeckill_id() {
        return seckill_id;
    }

    public void setSeckill_id(int seckill_id) {
        this.seckill_id = seckill_id;
    }

    public int getBargain_id() {
        return bargain_id;
    }

    public void setBargain_id(int bargain_id) {
        this.bargain_id = bargain_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public double getTruePrice() {
        return truePrice;
    }

    public void setTruePrice(double truePrice) {
        this.truePrice = truePrice;
    }

    public double getVip_truePrice() {
        return vip_truePrice;
    }

    public void setVip_truePrice(double vip_truePrice) {
        this.vip_truePrice = vip_truePrice;
    }

    public int getTrueStock() {
        return trueStock;
    }

    public void setTrueStock(int trueStock) {
        this.trueStock = trueStock;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public int getIs_reply() {
        return is_reply;
    }

    public void setIs_reply(int is_reply) {
        this.is_reply = is_reply;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
