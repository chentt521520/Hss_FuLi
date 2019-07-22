package com.example.haoss.indexpage.getcoupon;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//领券中心信息
public class GetCouponCentreInfo implements Serializable {

    /**
     * id : 20
     * cid : 10
     * start_time : 1561910400
     * end_time : 2019/08/31
     * total_count : 0
     * remain_count : 0
     * is_permanent : 1
     * status : 1
     * is_del : 0
     * add_time : 2019/07/08
     * coupon_price : 5
     * use_min_price : 99.00
     * category_name : 轻奢护肤
     * category_id : 72
     * title : 轻奢护肤满99元减5月
     * is_use : true
     */
    private int id; //优惠劵ID
    private int cid;    //
    private String start_time;
    private String end_time;
    private int total_count;
    private int remain_count;
    private int is_permanent;
    private int status; //状态
    private int is_del;
    private String add_time;
    private int coupon_price;
    private String use_min_price;
    private String category_name;
    private int category_id;
    private String title;
    private boolean is_use;//0 未领取；1已领取；2已抢光

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getRemain_count() {
        return remain_count;
    }

    public void setRemain_count(int remain_count) {
        this.remain_count = remain_count;
    }

    public int getIs_permanent() {
        return is_permanent;
    }

    public void setIs_permanent(int is_permanent) {
        this.is_permanent = is_permanent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(int coupon_price) {
        this.coupon_price = coupon_price;
    }

    public String getUse_min_price() {
        return use_min_price;
    }

    public void setUse_min_price(String use_min_price) {
        this.use_min_price = use_min_price;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIs_use() {
        return is_use;
    }

    public void setIs_use(boolean is_use) {
        this.is_use = is_use;
    }
}
