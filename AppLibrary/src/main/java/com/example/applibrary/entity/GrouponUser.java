package com.example.applibrary.entity;

import java.util.List;

public class GrouponUser {

    /**
     * id : 12
     * uid : 2
     * order_id : wx2019071217012210037
     * order_id_key : 327
     * total_num : 1
     * total_price : 5.94
     * cid : 12
     * pid : 12
     * people : 3
     * price : 6.00
     * add_time : 1562922082
     * stop_time : 1563008482
     * k_id : 0
     * is_tpl : 0
     * is_refund : 0
     * status : 1
     * nickname : -
     * avatar : https://wx.qlogo.cn/mmopen/vi_32/kyzgD3X679fXjNIavkWwKKMA7iac60uC138U4vP79wEJV9kYy3ZT6KgvGEIhUBBOVbGlmCuVGR2Wp3liayr3xq8A/132
     * count : 1
     * h : 17
     * i : 01
     * s : 22
     */

    private int id;
    private int uid;
    private int pink_id;
    private String order_id;
    private int order_id_key;
    private int total_num;
    private String total_price;
    private int cid;
    private int pid;
    private int people;
    private String price;
    private String add_time;
    private String stop_time;
    private int k_id;
    private int is_tpl;
    private int is_refund;
    private int status;
    private String nickname;
    private String avatar;
    private String count;
    private String h;
    private String i;
    private String s;
    private List<Bean> allPeople;
    private boolean isMyPink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPink_id() {
        return pink_id;
    }

    public void setPink_id(int pink_id) {
        this.pink_id = pink_id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getOrder_id_key() {
        return order_id_key;
    }

    public void setOrder_id_key(int order_id_key) {
        this.order_id_key = order_id_key;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }

    public int getK_id() {
        return k_id;
    }

    public void setK_id(int k_id) {
        this.k_id = k_id;
    }

    public int getIs_tpl() {
        return is_tpl;
    }

    public void setIs_tpl(int is_tpl) {
        this.is_tpl = is_tpl;
    }

    public int getIs_refund() {
        return is_refund;
    }

    public void setIs_refund(int is_refund) {
        this.is_refund = is_refund;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public boolean isMyPink() {
        return isMyPink;
    }

    public void setMyPink(boolean myPink) {
        isMyPink = myPink;
    }

    public List<Bean> getAllPeople() {
        return allPeople;
    }

    public void setAllPeople(List<Bean> allPeople) {
        this.allPeople = allPeople;
    }

    public static class Bean {
        int uid;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
