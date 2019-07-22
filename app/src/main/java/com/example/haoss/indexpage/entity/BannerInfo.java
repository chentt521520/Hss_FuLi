package com.example.haoss.indexpage.entity;

import java.io.Serializable;

//轮播图
public class BannerInfo implements Serializable {

    /**
     * id : 1
     * category_id : 40
     * product_id : 0
     * target : 0
     * type : 1
     * title : 1
     * imgUrl : http://qiniu.haoshusi.com/images/ad24c20190702140031842.png
     * order : 123
     * add_time : 1
     * status : 1
     * is_show : 1
     */

    private int id;
    private String category_id;
    private String product_id;
    private String target;
    private int type;
    private String title;
    private String imgUrl;
    private int order;
    private int add_time;
    private int status;
    private int is_show;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }
}
