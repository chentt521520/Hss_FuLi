package com.example.applibrary.entity;

import java.io.Serializable;

//用户登录信息
public class LoginInfo implements Serializable {

    /**
     * uid : 37
     * account : 15667074017
     * nickname : 用户10013
     * avatar : http://qiniu.haoshusi.com/user.jpg
     * phone : null
     * sex : 0
     * birthday : null
     * add_time : 1561967625
     * add_ip : 119.4.253.188
     * last_time : 1562895939
     * last_ip : 110.184.182.213
     * now_money : 9999.00
     * integral : 0.00
     * sign_num : 0
     * status : 1
     * level : 0
     * spread_uid : 0
     * spread_time : 0
     * user_type : 手机号
     * is_promoter : 0
     * pay_count : 2
     * spread_count : 0
     * is_birthday : 0
     * is_integral_shop : 1
     * integral_shop_money : 0
     * company_id : null
     * company_name : null
     * withdrawal : 0
     * like : 7
     * couponCount : 8
     * footprintCount : 0
     * alert : 1
     * alertInfo : {"coin":"2"}
     * token : 用户唯一识别
     */

    private int uid;
    private String account;
    private String nickname;
    private String avatar;
    private String phone;
    private int sex;
    private String birthday;
    private int add_time;
    private String add_ip;
    private long last_time;
    private String last_ip;
    private String now_money;
    private String integral;
    private int sign_num;
    private int status;
    private int level;
    private int spread_uid;
    private int spread_time;
    private String user_type;
    private int is_promoter;
    private int pay_count;
    private int spread_count;
    private int is_birthday;
    private int is_integral_shop;
    private int integral_shop_money;
    private String company_id;
    private String company_name;
    private int withdrawal;
    private int like;
    private int couponCount;
    private int footprintCount;
    private int alert;
    private AlertInfoBean alertInfo;
    private String token;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public String getAdd_ip() {
        return add_ip;
    }

    public void setAdd_ip(String add_ip) {
        this.add_ip = add_ip;
    }

    public long getLast_time() {
        return last_time;
    }

    public void setLast_time(long last_time) {
        this.last_time = last_time;
    }

    public String getLast_ip() {
        return last_ip;
    }

    public void setLast_ip(String last_ip) {
        this.last_ip = last_ip;
    }

    public String getNow_money() {
        return now_money;
    }

    public void setNow_money(String now_money) {
        this.now_money = now_money;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public int getSign_num() {
        return sign_num;
    }

    public void setSign_num(int sign_num) {
        this.sign_num = sign_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSpread_uid() {
        return spread_uid;
    }

    public void setSpread_uid(int spread_uid) {
        this.spread_uid = spread_uid;
    }

    public int getSpread_time() {
        return spread_time;
    }

    public void setSpread_time(int spread_time) {
        this.spread_time = spread_time;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public int getIs_promoter() {
        return is_promoter;
    }

    public void setIs_promoter(int is_promoter) {
        this.is_promoter = is_promoter;
    }

    public int getPay_count() {
        return pay_count;
    }

    public void setPay_count(int pay_count) {
        this.pay_count = pay_count;
    }

    public int getSpread_count() {
        return spread_count;
    }

    public void setSpread_count(int spread_count) {
        this.spread_count = spread_count;
    }

    public int getIs_birthday() {
        return is_birthday;
    }

    public void setIs_birthday(int is_birthday) {
        this.is_birthday = is_birthday;
    }

    public int getIs_integral_shop() {
        return is_integral_shop;
    }

    public void setIs_integral_shop(int is_integral_shop) {
        this.is_integral_shop = is_integral_shop;
    }

    public int getIntegral_shop_money() {
        return integral_shop_money;
    }

    public void setIntegral_shop_money(int integral_shop_money) {
        this.integral_shop_money = integral_shop_money;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(int withdrawal) {
        this.withdrawal = withdrawal;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public int getFootprintCount() {
        return footprintCount;
    }

    public void setFootprintCount(int footprintCount) {
        this.footprintCount = footprintCount;
    }

    public int getAlert() {
        return alert;
    }

    public void setAlert(int alert) {
        this.alert = alert;
    }

    public AlertInfoBean getAlertInfo() {
        return alertInfo;
    }

    public void setAlertInfo(AlertInfoBean alertInfo) {
        this.alertInfo = alertInfo;
    }

    public static class AlertInfoBean {
        /**
         * coin : 2
         */

        private String coin;

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
