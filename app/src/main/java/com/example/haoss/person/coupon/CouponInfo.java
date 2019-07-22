package com.example.haoss.person.coupon;

import java.io.Serializable;

//优惠劵信息
public class CouponInfo implements Serializable {
    /**
     * id : 60   优惠劵ID
     * cid : 16
     * uid : 15
     * coupon_title : 婴儿奶粉满99元减5月
     * coupon_price : 5.00
     * use_min_price : 99.00
     * add_time : 2019/07/09
     * end_time : 2022/04/04
     * use_time : 0  //使用时间
     * type : get
     * status : 0  //使用状态 0：未使用，1：已使用, 2:已过期
     * is_fail : 0
     * _add_time : 2019/07/09
     * _end_time : 2022/04/04
     * _type : 1
     * _msg : 可使用
     * category_id : 58  使用商品类型id
     * category_name : 婴儿奶粉  使用商品类型名称
     */
    private int id;
    private int cid;
    private int uid;
    private String coupon_title;
    private String coupon_price;
    private String use_min_price;
    private String add_time;
    private String end_time;
    private long use_time;
    private String type;
    private int status;
    private int is_fail;
    private String _add_time;
    private String _end_time;
    private int _type;
    private String _msg;
    private int category_id;
    private String category_name;
    private boolean check;
    private boolean use;


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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCoupon_title() {
        return coupon_title;
    }

    public void setCoupon_title(String coupon_title) {
        this.coupon_title = coupon_title;
    }

    public String getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(String coupon_price) {
        this.coupon_price = coupon_price;
    }

    public String getUse_min_price() {
        return use_min_price;
    }

    public void setUse_min_price(String use_min_price) {
        this.use_min_price = use_min_price;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public long getUse_time() {
        return use_time;
    }

    public void setUse_time(long use_time) {
        this.use_time = use_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_fail() {
        return is_fail;
    }

    public void setIs_fail(int is_fail) {
        this.is_fail = is_fail;
    }

    public String get_add_time() {
        return _add_time;
    }

    public void set_add_time(String _add_time) {
        this._add_time = _add_time;
    }

    public String get_end_time() {
        return _end_time;
    }

    public void set_end_time(String _end_time) {
        this._end_time = _end_time;
    }

    public int get_type() {
        return _type;
    }

    public void set_type(int _type) {
        this._type = _type;
    }

    public String get_msg() {
        return _msg;
    }

    public void set_msg(String _msg) {
        this._msg = _msg;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }
}
