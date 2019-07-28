package com.example.applibrary.entity;

import java.util.List;

public class ReplyInfo {

    /**
     * product_score : 5
     * service_score : 5
     * comment : 价格很优惠，服务也很好，YY款式和面料都不错的，给个好评此次
     * merchant_reply_content : null//商家回复内容
     * merchant_reply_time : 1970-01-01 08:00//商家回复时间
     * pics : null
     * add_time : 2019-07-17 06:05
     * nickname : *
     * avatar : http://py.haoshusi.com/avatar/344e794e204204215d95ba49507926b7.jpg
     * suk : 1套
     * star : 5
     */

    private int product_score;
    private int service_score;
    private String comment;
    private Object merchant_reply_content;
    private String merchant_reply_time;
    private List<Object> pics;
    private String add_time;
    private String nickname;
    private String avatar;
    private String suk;
    private String star;

    public ReplyInfo() {
    }

    public ReplyInfo(String nickname, String avatar, String comment) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.comment = comment;
    }

    public int getProduct_score() {
        return product_score;
    }

    public void setProduct_score(int product_score) {
        this.product_score = product_score;
    }

    public int getService_score() {
        return service_score;
    }

    public void setService_score(int service_score) {
        this.service_score = service_score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Object getMerchant_reply_content() {
        return merchant_reply_content;
    }

    public void setMerchant_reply_content(Object merchant_reply_content) {
        this.merchant_reply_content = merchant_reply_content;
    }

    public String getMerchant_reply_time() {
        return merchant_reply_time;
    }

    public void setMerchant_reply_time(String merchant_reply_time) {
        this.merchant_reply_time = merchant_reply_time;
    }

    public List<Object> getPics() {
        return pics;
    }

    public void setPics(List<Object> pics) {
        this.pics = pics;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
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

    public String getSuk() {
        return suk;
    }

    public void setSuk(String suk) {
        this.suk = suk;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}
