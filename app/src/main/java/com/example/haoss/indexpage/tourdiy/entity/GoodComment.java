package com.example.haoss.indexpage.tourdiy.entity;

import java.util.List;

public class GoodComment {

    /**
     * product_score : 5
     * service_score : 5
     * comment : 阿斯顿撒多
     * merchant_reply_content : null
     * merchant_reply_time : 1970-01-01 08:00
     * pics : []
     * add_time : 2019-07-11 11:41
     * nickname : *
     * avatar : https://wx.qlogo.cn/mmopen/vi_32/kyzgD3X679fXjNIavkWwKKMA7iac60uC138U4vP79wEJV9kYy3ZT6KgvGEIhUBBOVbGlmCuVGR2Wp3liayr3xq8A/132
     * suk :
     * star : 5
     */

    private int product_score;
    private int service_score;
    private String comment;
    private Object merchant_reply_content;
    private String merchant_reply_time;
    private String add_time;
    private String nickname;
    private String avatar;
    private String suk;
    private String star;
    private List<?> pics;

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

    public List<?> getPics() {
        return pics;
    }

    public void setPics(List<?> pics) {
        this.pics = pics;
    }
}
